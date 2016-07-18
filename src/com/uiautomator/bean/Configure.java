package com.uiautomator.bean;
/** 
 * @author  linhong: 
 * @date 2016年7月12日 下午2:16:40 
 * @Description: TODO
 * @version 1.0  
 */
public class Configure {
	/**
	 * 脚本指向文件夹
	 */
	private String scriptDir;

	public String getScriptDir() {
		return scriptDir;
	}

	public void setScriptDir(String scriptDir) {
		this.scriptDir = scriptDir;
	}

	@Override
	public String toString() {
		return "Configure [scriptDir=" + scriptDir + "]";
	}
	
	
}
