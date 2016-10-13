package com.sumavision.debug;

import com.sumavision.base.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.appium.java_client.android.AndroidKeyCode.*;


public class Testcase extends InitAppium {
	//设置初始driver参数值；
	/*
	public Testcase(){
		super(new Builder()
				.setAppPackage("com.android.mms")
				.setAppActivity(".ui.ConversationList"));
	}
	 * */
	
	private OperateDemo operateDemo;
	@BeforeClass
	public void initDriver(){
		Assert.assertNotNull(driver);
		operateDemo = new OperateDemo(driver);
	}
	@Test
	public void startIPTV() {
		boolean flag = operateDemo.pressFocus();
		Assertion.verifyEquals(flag, true);
		
	}
	
}
