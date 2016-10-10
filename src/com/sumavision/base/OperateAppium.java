package com.sumavision.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.http.util.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import mx4j.log.Log;

import static com.sumavision.base.InitAppium.appPackage;


/**
 * 操作逻辑父类
 * @author chenxunlie
 * */

public class OperateAppium {
	AndroidDriver driver;
		
	public static int WAIT_TIME = 10;
	public static int TIME_INTERVAL = 500;
	private Log log;
	
	public OperateAppium (AndroidDriver androidDriver){
		this.driver = androidDriver;
	}
	
	public <T> void print(T str){
		if(!TextUtils.isEmpty(String.valueOf(str))){
			System.out.println(str);
		} else {
			System.out.println("输入空字符串");
		}
	}
	
	public void sleep(int s){
		try {
			Thread.sleep(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public AndroidElement waitAuto(By by, int time) {
		try {
            return new AndroidDriverWait(driver, time)
                    .until(new ExpectedCondition<AndroidElement>() {
                        @Override
                        public AndroidElement apply(AndroidDriver androidDriver) {
                            return (AndroidElement) androidDriver.findElement(by);
                        }
                    });
        } catch (TimeoutException e) {
            print("查找元素超时!! 未找到 [" + by.toString() + "]");
            return null;
        }
    }

	public AndroidElement waitAutoById(String id){
		return waitAutoById(id,WAIT_TIME);
	}
	
	public AndroidElement waitAutoById(String id,int time){
		return waitAuto(By.id(id), WAIT_TIME);
	}
	
	public AndroidElement waitAutoByName(String name) {
        return waitAutoByName(name, WAIT_TIME);
    }
	
    public AndroidElement waitAutoByName(String name, int time) {
        return waitAuto(By.name(name), time);
    }
        
	public void waitAuto() {
		waitAuto(WAIT_TIME);
	}
	
	public void waitAuto(int time){
		//隐性等待 全局等待时间
		driver.manage().timeouts()
		.implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	public boolean clickView(AndroidElement ae){
		return clickView(ae,"");
	}
	
	public boolean clickView(AndroidElement ae,String str){
		if(ae != null){
			ae.click();
			return true;
		}else{
			print(str+"不存在");
		}
		return false;
	}
	
	public String getCurrActivity() {
		String str = driver.currentActivity();
		return str.substring(str.lastIndexOf(".") + 1);
	}
		
	public void input(AndroidElement ae,String str){
		if(ae == null){
			print("输入框不存在，无法输入字符串！！");
		}else{
			ae.sendKeys(str);
		}
	}
	
	public void startActivity(String ActivityName){
		driver.startActivity(appPackage, ActivityName);
		sleep(TIME_INTERVAL);
	}
	
	public void sendRemoteKey(int keyNum){
		sendRemoteKey(keyNum,1);
		sleep(TIME_INTERVAL);
	}
	public void sendRemoteKey(int keyNum,int count){
		for (int i = 0; i < count; i++) {
			driver.pressKeyCode(keyNum);
			sleep(TIME_INTERVAL);
		}
	}
	public void seedRemoteKey(int keyNum,int count,int time){
		for (int i = 0; i < count; i++) {
			driver.pressKeyCode(keyNum);
			sleep(time);
		}
	}
	
	public void takeScreenShot() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        String dateStr = sf.format(date);
        String path = this.getClass().getSimpleName() + "_" + dateStr + ".png";
        takeScreenShot((TakesScreenshot) this.driver, path);
    }
	
	 public void takeScreenShot(TakesScreenshot drivername, String path) {
	        String currentPath = System.getProperty("user.dir")+"\\Result"; 
	        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
	        try {
	            print("save snapshot path is:" + currentPath + path);
	            FileUtils.copyFile(scrFile, new File(currentPath + "\\" + path));
	        } catch (Exception e) {
	            print("Can't save screenshot");
	            e.printStackTrace();
	        } finally {
	            print("screen shot finished");
	        }
	    }

}
