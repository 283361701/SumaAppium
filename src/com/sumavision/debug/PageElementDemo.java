package com.sumavision.debug;

import com.sumavision.base.PageAppium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class PageElementDemo extends PageAppium{
	public final String SETTING_ICON = "Settings";
	public final String DISPLAY_BTN_NAME = "Display";
	public final String STORAGE_BTN_NAME = "Storage";
	public final String APPS_BTN_NAME = "Apps";
	public PageElementDemo(AndroidDriver driver){
		super(driver);
	}
	public boolean isSettings() {
		return isNameElementExist(SETTING_ICON, 3, true);
	}
	public boolean isDisplay() {
		return isNameElementExist(DISPLAY_BTN_NAME, 3, true);
	}
	public AndroidElement getDisplayBTN() {
		return findByName(DISPLAY_BTN_NAME);
	}
	public AndroidElement getStorageBTN() {
		return findByName(STORAGE_BTN_NAME);
	}
	public AndroidElement getAppsBTN() {
		return findByName(APPS_BTN_NAME);
	}
}
