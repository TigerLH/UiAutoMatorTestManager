package com.uiautomator.bean;
/** 
 * @author  linhong: 
 * @date 2016年7月18日 上午10:43:50 
 * @Description: TODO
 * @version 1.0  
 */
public class DeviceInfo {
	/**
	 * 设备名称
	 */
	private String deviceName;
	
	/**
	 * 系统版本
	 */
	private String systemVersion;
	
	/**
	 * 系统内存
	 */
	private String systemMemmory;
	
	/**
	 * 设备分辨率
	 */
	private String display;
	
	/**
	 * 最大可分配内存
	 */
	private String heapSize;
	
	/**
	 * 最小可分配内存
	 */
	private String limitSize;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getSystemMemmory() {
		return systemMemmory;
	}

	public void setSystemMemmory(String systemMemmory) {
		this.systemMemmory = systemMemmory;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getHeapSize() {
		return heapSize;
	}

	public void setHeapSize(String heapSize) {
		this.heapSize = heapSize;
	}

	public String getLimitSize() {
		return limitSize;
	}

	public void setLimitSize(String limitSize) {
		this.limitSize = limitSize;
	}

	@Override
	public String toString() {
		return "DeviceInfo [deviceName=" + deviceName + ", systemVersion="
				+ systemVersion + ", systemMemmory=" + systemMemmory
				+ ", display=" + display + ", heapSize=" + heapSize
				+ ", limitSize=" + limitSize + "]";
	}
	
	
}
