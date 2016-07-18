package com.uiautomator.executer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.uiautomator.bean.Configure;
import com.uiautomator.bean.DeviceInfo;
import com.uiautomator.bean.ReportCss;
import com.uiautomator.bean.ResultBean;
import com.uiautomator.logger.Logger;
import com.uiautomator.util.Command;
import com.uiautomator.util.Command.CommandResult;
import com.uiautomator.util.DeviceUtil;
import com.uiautomator.util.FileUtil;

/** 
 * @author  linhong: 
 * @date 2016年7月12日 下午2:21:53 
 * @Description: TODO
 * @version 1.0  
 */
public class CaseExecuter {
	private final String EXECUTE_COMMAND = "adb shell uiautomator runtest UiAutoMatorTestRunner.jar -e file \"/data/local/tmp/%s\" -c com.uiautomator.caserunner.UiAutoTestCase";
	private final String PUSH_COMMAND = "adb push %s data/local/tmp/%s";
	private final String PULL_COMMAND = "adb pull /data/local/tmp/TestResult.xml TestResult.xml";
	private final String PUSH_TESTRUNNER_COMMAND = "adb push server/UiAutoMatorTestRunner.jar data/local/tmp/UiAutoMatorTestRunner.jar";
	private final String TEST_RESULT = "TestResult.xml";
	private final String TEST_REPORT = "TestReport.html";
	private Configure configure = null;
	private Command command = null;
	private List<String> filelist = new ArrayList<String>();
	private List<ResultBean> result = new ArrayList<ResultBean>();
	private StringBuilder sb = new StringBuilder();
	private String config;
	private DeviceInfo deviceInfo = null;
	public CaseExecuter(String config){
		this.config = config;
		init();
	}
	
	
	
	/**
	 * 初始化变量
	 */
	void init(){
		command = new Command();
		configure = new Configure();
		String scriptDir = FileUtil.getConfigValue(config, "script");
		configure.setScriptDir(scriptDir);
	}
	
	
	
	public void run() throws Exception{
		Logger.debug("Test Start");
		pushScript();
		deviceInfo = this.getDeviceInfo();
		try {
			runCase();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		productReport();
	}
	
	
	/**
	 * 获取设备信息
	 * @return
	 */
	public DeviceInfo getDeviceInfo(){
		return DeviceUtil.getDeviceInfo();
	}
	
	
	/**
	 * 执行case收集结果
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	void runCase() throws Exception{
		for(String name : filelist){
			String cmd = String.format(EXECUTE_COMMAND, name);
			command.executeCommand(cmd, null);
			pullResult();
		}
	}
	
	
	/**
	 * Push脚本到设备中
	 * @param command
	 * @throws Exception 
	 */
	void pushScript() throws Exception{
		Logger.debug("PUSH TESTRUNNER TO DEVICE:data/local/tmp/UiAutoMatorTestRunner.jar");
		CommandResult crt = command.executeCommand(PUSH_TESTRUNNER_COMMAND, null);
		if(crt.result!=0){
			throw new Exception("TESTRUNNER PUSH ERROR");
		}
		Logger.debug("PUSH SCRIPT TO DEVICE:data/local/tmp");
		File dir = new File(configure.getScriptDir());
		if(!dir.exists()){
			throw new Exception("Script dir is not found");
		}
		File[] list = dir.listFiles();
		for(File file:list){
			filelist.add(file.getName());
			CommandResult cr = command.executeCommand(String.format(PUSH_COMMAND, file.getAbsolutePath(),file.getName()), null);
			if(cr.result!=0){
				throw new Exception("Script push error");
			}
		}
	}
	
	
	/**
	 * 生成测试报告
	 */
	void productReport(){
		Logger.debug("Generate TestReport:TestReport.html");
		reportStart();
		report_DeviceInfo();
		outputResult(result, TEST_REPORT);
		Logger.debug("TestFinish");
	}
	
	
	
	/**
	 * 导出测试结果
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws InterruptedException 
	 */
	void pullResult() throws SAXException, IOException, ParserConfigurationException, InterruptedException{
		command.executeCommand(PULL_COMMAND, null);
		Logger.debug("PULL RESULT:data/local/tmp/TestResult.xml");
		Thread.sleep(1000);
		ResultBean rb  = parseResult(TEST_RESULT);
		result.add(rb);
	}
	
	
	/**
	 * 解析测试结果
	 * @param resultfile
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	 ResultBean parseResult(String resultfile) throws SAXException, IOException, ParserConfigurationException{
			ResultBean rb = new ResultBean();
			DocumentBuilderFactory Totaldbf=DocumentBuilderFactory.newInstance();  
			DocumentBuilder db=Totaldbf.newDocumentBuilder();    
			Document doc=db.parse(resultfile);
			String name = doc.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
			String status = doc.getElementsByTagName("status").item(0).getFirstChild().getNodeValue();
			String message = doc.getElementsByTagName("message").item(0).getFirstChild().getNodeValue();
			String time = doc.getElementsByTagName("time").item(0).getFirstChild().getNodeValue();
			rb.setCasename(name);
			rb.setStatus(status);
			rb.setMessage(message);
			rb.setCosttime(time);
			return rb;
	}
	
	
	 
	 
	 
	 
		/**
		 * 报告头
		 */
		void reportStart(){
			SimpleDateFormat  formatter   =  new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
			Date  curDate = new  Date(System.currentTimeMillis());
			String CurrentTime = formatter.format(curDate);  
			sb.append("<html>");
	    	sb.append("<head>");
	    	sb.append("<Meta http-equiv=Content-Type content=text/html charset=gb2312>");
	    	sb.append("<title>QA Test Report</title>");
	    	sb.append("<style>");
	    	sb.append(ReportCss.css);
	    	sb.append("</style>");
	    	sb.append("<DIV align='left'><a><img src=\"data:image/jpg;base64,");
	    	sb.append("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");
	    	sb.append("alt=\"Base64 encoded image\" width=144 height=44 /></a>");
	    	sb.append("<DIV style=\"float:right;\">");
	    	sb.append("<SPAN><font color=\"#808080\" size=\"2\" style=\"\">");
	    	sb.append(CurrentTime);
	    	sb.append("</font></SPAN>");
	    	sb.append("</DIV>");
	    	sb.append("</DIV>");
	    	sb.append("<DIV align='center' ><font color=\"#808080\" size=\"6\" style=\"\"><b>QA Test Report</b></font>");
	    	sb.append("</DIV>");
	    	sb.append("</DIV>");
	    	sb.append("</head>");
	    	sb.append("<body>");
		}
	 
		
		/**
		 * 生成设备信息报告
		 */
		void report_DeviceInfo(){
			sb.append("<h2>");
	    	sb.append("设备信息");
	    	sb.append("</h2>");
	    	sb.append("<table class=\"bordered\">");
	    	sb.append("<thread>");
	    	sb.append("<Tr>");
	    	sb.append("<Th>Properties</th>");
	    	sb.append("<Th>Value</th>");
	    	sb.append("</tr>");
	    	sb.append("</thread>");
	    	sb.append("<tbody>");
	    	sb.append("<tr>");
	    	sb.append("<td>");
	    	sb.append("设备名称");
	    	sb.append("</td>");
	    	sb.append("<td>");
	    	sb.append(deviceInfo.getDeviceName());
	    	sb.append("</td>");
	    	sb.append("</tr>");
	    	
	    	sb.append("<tr>");
	    	sb.append("<td>");
	    	sb.append("系统版本");
	    	sb.append("</td>");
	    	sb.append("<td>");
	    	sb.append(deviceInfo.getSystemVersion());
	    	sb.append("</td>");
	    	sb.append("</tr>");
	    	
	    	sb.append("<tr>");
	    	sb.append("<td>");
	    	sb.append("系统内存");
	    	sb.append("</td>");
	    	sb.append("<td>");
	    	sb.append(deviceInfo.getSystemMemmory());
	    	sb.append("</td>");
	    	sb.append("</tr>");
	    	
	    	sb.append("<tr>");
	    	sb.append("<td>");
	    	sb.append("HeapSize");
	    	sb.append("</td>");
	    	sb.append("<td>");
	    	sb.append(deviceInfo.getHeapSize());
	    	sb.append("</td>");
	    	sb.append("</tr>");
	    	
	    	sb.append("<tr>");
	    	sb.append("<td>");
	    	sb.append("分辨率");
	    	sb.append("</td>");
	    	sb.append("<td>");
	    	sb.append(deviceInfo.getDisplay());
	    	sb.append("</td>");
	    	sb.append("</tr>");
	    	sb.append("</tbody>");
	    	sb.append("</table>");
	    	sb.append("<br>");
	    	sb.append("<br>");
		}
	 
		
	/**
	 * 生成测试报告
	 */
	void outputResult(List<ResultBean> list, String filename){      
        try {
        	sb.append("<h2>");
	    	sb.append("TestResult");
	    	sb.append("</h2>");
	    	sb.append("<table class=\"bordered\">");
	    	sb.append("<thread>");
	    	sb.append("<Tr>");
	    	sb.append("<Th>用例名称</th>");
	    	sb.append("<Th>结果</th>");
	    	sb.append("<Th>耗时</th>");
	    	sb.append("<Th>错误信息</th>");
	    	sb.append("</tr>");
	    	sb.append("</thread>");
            BufferedWriter output = new BufferedWriter(new FileWriter(new File(filename)));
            for (ResultBean result : list) {
             	sb.append("<tbody>");
    	    	sb.append("<td>");
    	    	sb.append(result.getCasename());
    	    	sb.append("</td>");
    	    	sb.append("<td>");
    	    	sb.append(result.getStatus());
    	    	sb.append("</td>");
    	    	sb.append("<td>");
    	    	sb.append(result.getCosttime());
    	    	sb.append("</td>");
    	    	sb.append("<td>");
    	    	sb.append(result.getMessage());
    	    	sb.append("</td>");
    	    	sb.append("</tbody>");
            }
	    	sb.append("</table>");
          	sb.append("</body>");
          	sb.append("</html>");
            output.write(sb.toString());
            output.flush();
            output.close();
        } catch (IOException e) {          
            e.printStackTrace();
        }
         
    }	
	
	
}
