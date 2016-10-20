package com.sumavision.base;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Driver;

/**
 * 设备初始化父类
 * @author chenxunlie
 * 20161009
 * */

@Listeners({com.sumavision.base.AssertionListener.class}) 
public class InitAppium {
	
	public static String deviceName = "10.10.30.202:5555";
	public static String platformVersion = "4.4.2";
	public static String platformName = "Android";
	//安装apk的存放路径
	public static String appPath = System.getProperty("user.dir")+"/apps/xxx.apk";
	public static String appPackage = "com.sumavision.iptv";
	public static String appActivity = ".MainActivity";
	
	public static String noReset = "True";
	public static String noSign = "True";
	public static String unicodeKeyboard = "True";
	public static String resetKeyboard = "True";
	
	public static AndroidDriver <AndroidElement> driver = null;
	
	public InitAppium() {
		this (new Builder());
	}
	
	public InitAppium(Builder builder) {
		appActivity = builder.appActivity;
		appPackage = builder.appPackage;
		appPath = builder.appPath;
		deviceName = builder.deviceName;
		platformName = builder.platformName;
		platformVersion = builder.platformVersion;
		noReset = builder.noReset;
		noSign = builder.noSign;
		unicodeKeyboard = builder.unicodeKeyboard;
		resetKeyboard = builder.resetKeyboard;	
	}
	
	@BeforeSuite
	public void beforeSuite() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("platformName", platformName);
		
		//如果不需要安装则不需要指定app
//		capabilities.setCapability("app", new File(appPath).getAbsolutePath());
		//应用Package和Activity名称
		capabilities.setCapability("appPackage", appPackage);
		capabilities.setCapability("appActivity", appActivity);		
		//重现安装和重现签名，默认ture
		
		capabilities.setCapability("noReset", noReset);
		capabilities.setCapability("noSign", noSign);
		//选择中文输入
		capabilities.setCapability("unicodeKeyboard", unicodeKeyboard);
		capabilities.setCapability("resetKeyboard", resetKeyboard);
		
		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
}
