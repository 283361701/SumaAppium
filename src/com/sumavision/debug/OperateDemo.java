package com.sumavision.debug;

import com.sumavision.base.ComparePicture;
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
	public boolean pressFocus() {
		boolean reg = true;
		sleep(1000);
		sendRemoteKey(BACK);
//		takeScreenShot();
//		takeScreenShot(736,481,74,74);
		for (int i = 0; i < 2; i++) {
			sleep(5000);
			takeScreenShot(120,570,160,150);
			print(i);
		}
		//reg = pageElementDemo.isComImage(736,224,74,74,CONTRAST_PATH+"\\3.png",1);
		
		print(reg);
		return reg;
		
	}

}
