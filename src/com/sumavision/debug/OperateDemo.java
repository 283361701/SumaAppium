package com.sumavision.debug;

import com.sumavision.base.OperateAppium;

import io.appium.java_client.android.AndroidDriver;

import static io.appium.java_client.android.AndroidKeyCode.*;


public class OperateDemo extends OperateAppium {
	private PageElementDemo pageElementDemo;
	AndroidDriver driver;
	public OperateDemo(AndroidDriver driver){
		super(driver);
		pageElementDemo = new PageElementDemo(driver);
		this.driver = driver;
	}
	public boolean checkSetting() {
		sleep(500);
		if(pageElementDemo.isSettings()==true){
			return true;
		}else{
			print("INFO：元素不存在");
			return false;
		}
	}
	public boolean clickDisplay(){
		sleep(500);
		clickView(pageElementDemo.getDisplayBTN());
		sendRemoteKey(BACK,1);
		if(pageElementDemo.isDisplay()==true){
			return true;
		}else{
			print("INFO：元素不存在");
			return false;
		}
	}
}
