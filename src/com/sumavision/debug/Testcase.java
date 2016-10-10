package com.sumavision.debug;

import com.sumavision.base.*;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
		operateDemo.print(driver.getContextHandles());
		operateDemo.print(driver.getContext());
		operateDemo.sleep(5);
	}
	@Test
	public void testSettings(){
		boolean flag = operateDemo.checkSetting();
		Assertion.verifyEquals(flag, false);
		operateDemo.print("testcase 1 pass");
	}
	@Test
	public void testDisplay(){
		boolean flag = operateDemo.clickDisplay();
		Assertion.verifyEquals(flag, true);
		operateDemo.print("testcase 2 pass");
	}
	@Test
	public void testActivity() {
		driver.startActivity("com.android.browser", 
				"BrowserActivity");
		operateDemo.sleep(1000);
		operateDemo.takeScreenShot();
	}
	
}
