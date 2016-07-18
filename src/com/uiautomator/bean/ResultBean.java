package com.uiautomator.bean;
/** 
 * @author  linhong: 
 * @date 2016年7月15日 上午9:58:51 
 * @Description: TODO
 * @version 1.0  
 */
public class ResultBean {
	/**
	 * 用例名称
	 */
	private String casename;
	/**
	 * 用例状态
	 */
	private String status;
	/**
	 * 错误信息
	 */
	private String message;
	/**
	 * 用例耗时
	 */
	private String costtime;
	public String getCasename() {
		return casename;
	}
	public void setCasename(String casename) {
		this.casename = casename;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCosttime() {
		return costtime;
	}
	public void setCosttime(String costtime) {
		this.costtime = costtime;
	}
	@Override
	public String toString() {
		return "ResultBean [casename=" + casename + ", status=" + status
				+ ", message=" + message + ", costtime=" + costtime + "]";
	}
	
}
