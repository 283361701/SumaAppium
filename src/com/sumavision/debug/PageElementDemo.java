package com.sumavision.debug;

import com.sumavision.base.PageAppium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class PageElementDemo extends PageAppium{
	public final String SETTING_ICON_ID = "Settings";
	public final String SETTING_ICON_NAME = "Settings";
	public final String DISPLAY_BTN_ID = "Display";
	public final String DISPLAY_BTN_NAME = "Display";
	public final int[] LIVE_BTN_FOCUS ={400,500};
	public final String SETTING_TEXT_ID= "setting_advanced_password_input_text";
	public final String PSETTING_TEXT_ID= "service_authentication_business_account";
	public PageElementDemo(AndroidDriver driver){
		super(driver);
	}
	public AndroidElement clickText(){
		return findById(SETTING_TEXT_ID);
	}
	public AndroidElement clickPText(){
		return findById(PSETTING_TEXT_ID);
	}
}
