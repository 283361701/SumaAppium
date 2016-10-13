package com.sumavision.base;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.util.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;

import bsh.This;
//import android.graphics.Bitmap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import static com.sumavision.base.InitAppium.appPackage;
import static io.appium.java_client.android.AndroidKeyCode.KEYCODE_MOVE_END;
import static io.appium.java_client.android.AndroidKeyCode.BACKSPACE;

/**
 * 操作逻辑父类
 * @author chenxunlie
 * */

public class OperateAppium {
	AndroidDriver driver;
		
	public static int WAIT_TIME = 10;
	public static int TIME_INTERVAL = 500;
	public static String RESULT_PATH = System.getProperty("user.dir")+"\\Result";
	public static String CONTRAST_PATH = System.getProperty("user.dir")+"\\Contrast";
	
	public OperateAppium (AndroidDriver androidDriver){
		this.driver = androidDriver;
	}
	
	//文字输入
	public <T> void print(T str){
		if(!TextUtils.isEmpty(String.valueOf(str))){
			System.out.println(str);
		} else {
			System.out.println("输入空字符串");
		}
	}
	
	//等待时间
	public void sleep(int ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	//元素等待
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
	//元素点击
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
	//获取当前apk的activity
	public String getCurrActivity() {
		String str = driver.currentActivity();
		return str.substring(str.lastIndexOf(".") + 1);
	}
	//文本输入	
	public void input(AndroidElement ae,String str){
		if(ae == null){
			print("输入框不存在，无法输入字符串！！");
		}else{
			ae.sendKeys(str);
		}
	}
	//启动应用
	public void startActivity(String ActivityName){
		driver.startActivity(appPackage, ActivityName);
		sleep(TIME_INTERVAL);
	}
	//按键发送
	public void sendRemoteKey(int keyNum){
		sendRemoteKey(keyNum,1);
	}
	
	public void sendRemoteKey(int keyNum,int count){
		sendRemoteKey(keyNum,count,TIME_INTERVAL);
	}
	
	public void sendRemoteKey(int keyNum,int count,int time){
		for (int i = 0; i < count; i++) {
			driver.pressKeyCode(keyNum);
			sleep(time);
		}
	}
	//全屏截图
	public String takeScreenShot() {       
        return takeScreenShot(0,0,1,1,2,(TakesScreenshot) this.driver);        
    }
	//裁剪
	public String takeScreenShot(int x,int y,int w,int h) {
		return takeScreenShot (x,y,w,h,1,(TakesScreenshot) this.driver);
	}
	
	public String takeScreenShot(int x,int y,int w,int h,int tag,TakesScreenshot drivername) {
		 
		SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Calendar cal = Calendar.getInstance();
	    Date date = cal.getTime();
	    String dateStr = sf.format(date);
	    String path = this.getClass().getSimpleName() + "_" + dateStr + ".png";	 
	    String currentPath = RESULT_PATH; 
	        
	    File scrFile = drivername.getScreenshotAs(OutputType.FILE);
	    switch (tag) {
		case 1:				
			try {
				Iterator iterator = ImageIO.getImageReadersByFormatName("PNG");
				ImageReader reader = (ImageReader)iterator.next();
				InputStream inputStream = new FileInputStream(scrFile);
				ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
				reader.setInput(iis, true);  
				ImageReadParam param = reader.getDefaultReadParam();
				Rectangle rectangle = new Rectangle(x,y,w,h);
				param.setSourceRegion(rectangle);
				BufferedImage bi = reader.read(0,param); 
				ImageIO.write(bi, "PNG", new File(currentPath + "\\" + path));		
				print("截图保存路径:" + currentPath + path);
				
			} catch (IOException e) {
				print("截图未成功！！");
				e.printStackTrace();
			}
			break;
		case 2:	
			try {
		        print("截屏保存路径:" + currentPath + path);
		        FileUtils.copyFile(scrFile, new File(currentPath + "\\" + path));
		     
			} catch (Exception e) {
		        print("截图未成功！！");
		        e.printStackTrace();
			}
				break;
	    }
	    return currentPath + "\\" + path;
	}
	 //坐标点击
	 public void pressFocus(int[] x){
		 try {
			driver.tap(1, x[0], x[1], 500);
			print("点击坐标："+x[0]+" "+x[1]);
		} catch (Exception e) {
			print("点击元素位置异常" + e.getMessage());
            e.printStackTrace();		
            }
	 }
	 //清楚文本框
	 public void clearText(AndroidElement element) {
	        String text = element.getText();
	        clickView(element);
	        driver.pressKeyCode(KEYCODE_MOVE_END);
	        for (int i = 0; i < text.length(); i++) {
	            driver.pressKeyCode(BACKSPACE);
	        }
	        print("清除完成！");
	 } 
	 
}
