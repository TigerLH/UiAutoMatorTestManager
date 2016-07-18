package com.uiautomator.main;

import com.uiautomator.executer.CaseExecuter;

/** 
 * @author  linhong: 
 * @date 2016年7月15日 上午11:15:06 
 * @Description: TODO
 * @version 1.0  
 */
public class RunTestCase {
	public static void main(String[] args){
		new RunTestCase().run(new String[]{"config.properties"});
	}
	
	public void run(String[] args){
		if(args.length<1){
			showUsage();
            return;
		}
		String configure = args[0];
		try {
			new CaseExecuter(configure).run();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
    private void showUsage() {
        System.err.println("usage:java -jar YLTT.jar [ConfigPath]");
        System.err.println("If any issue,please contact me:linghong@56qq.com");
    }
}
