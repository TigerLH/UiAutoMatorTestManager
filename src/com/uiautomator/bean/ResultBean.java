package com.uiautomator.bean;
/** 
 * @author  linhong: 
 * @date 2016��7��15�� ����9:58:51 
 * @Description: TODO
 * @version 1.0  
 */
public class ResultBean {
	/**
	 * ��������
	 */
	private String casename;
	/**
	 * ����״̬
	 */
	private String status;
	/**
	 * ������Ϣ
	 */
	private String message;
	/**
	 * ������ʱ
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
