package com.uiautomator.util;

import com.uiautomator.bean.DeviceInfo;

/** 
 * @author  linhong: 
 * @date 2016年7月18日 上午10:49:00 
 * @Description: TODO
 * @version 1.0  
 */
public class DeviceUtil {
	private static final String getSystemVersion = "adb shell getprop ro.build.version.release";
	private static final String getSystemModel = "adb shell getprop ro.product.model";
	private static final String getSystemMemmory = "adb shell \"cat /proc/meminfo|grep MemTotal:\"";
	private static final String getSystemHeapSize = "adb shell getprop dalvik.vm.heapsize";
	private static final String getSystemLimitSize = "adb shell getprop dalvik.vm.heapgrowthlimit";
	private static final String getDisplay = "adb shell wm size";
	public static DeviceInfo getDeviceInfo(){
		Command command = new Command();
		DeviceInfo dinfo = new DeviceInfo();
		String systemVersion = command.executeCommand(getSystemVersion, null).successMsg;
		String model = command.executeCommand(getSystemModel, null).successMsg;
		String tmp = command.executeCommand(getSystemMemmory, null).successMsg;
		String systemMemmory = Integer.parseInt(tmp.split("\\s+")[1])/1024+"M";
		String heapSize = command.executeCommand(getSystemHeapSize, null).successMsg;
		String limitSize = command.executeCommand(getSystemLimitSize, null).successMsg;
		String display = command.executeCommand(getDisplay, null).successMsg.split("\\s+")[2];
		dinfo.setDeviceName(model);
		dinfo.setSystemVersion(systemVersion);
		dinfo.setSystemMemmory(systemMemmory);
		dinfo.setHeapSize(heapSize);
		dinfo.setLimitSize(limitSize);
		dinfo.setDisplay(display);
		return dinfo;
	}
	
	public static void main(String[] args) {
		getDeviceInfo();
	}
	
}
