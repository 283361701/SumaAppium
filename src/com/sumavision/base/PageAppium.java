package com.sumavision.base;

import org.apache.http.util.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import static com.sumavision.base.InitAppium.appPackage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class PageAppium {
	AndroidDriver driver;
	private static int WAIT_TIME = 3;
	
	public PageAppium(AndroidDriver androidDriver){
		this.driver = androidDriver;
		waitAuto(WAIT_TIME);
	}
	
	public <T> void print(T str) {
        if (!TextUtils.isEmpty(String.valueOf(str))) {
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
            return null;
        }
    }

	public void waitAuto() {
		waitAuto(WAIT_TIME);
	}
	
	public void waitAuto(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
    public AndroidElement waitAutoById(String id) {
        return waitAutoById(id, WAIT_TIME);
    }

    public AndroidElement waitAutoById(String id, int time) {
        return waitAuto(By.id(id), time);
    }

    public AndroidElement waitAutoByName(String name) {
        return waitAutoByName(name, WAIT_TIME);
    }

    public AndroidElement waitAutoByName(String name, int time) {
        return waitAuto(By.name(name), time);
    }

	
	public boolean isIdElementExist(String id){
		return isIdElementExist(id,0);
	}
	
	public boolean isIdElementExist(String id,int timeOut){
		return isIdElementExist(id, timeOut,false);
	}
	
	public boolean isIdElementExist(String id,int timeOut,boolean isShow){
		return isElementExist(By.id(appPackage + ":id/" +id),timeOut,isShow);
	}
	 public boolean isNameElementExist(String name) {
	        return isNameElementExist(name, 0);
	}

	public boolean isNameElementExist(String name, int timeOut) {
	        return isNameElementExist(name, timeOut,false);
	}

	public boolean isNameElementExist(String name, int timeOut, boolean isShow) {
	        return isElementExist(By.name(name),timeOut, isShow);
	}
	
	public boolean isClassNameElementExist(String className) {
		return isClassNameElementExist(className,0);		
	}
	
	public boolean isClassNameElementExist(String className,int timeOut) {
		return isClassNameElementExist(className, timeOut,false);
	}
	
	public boolean isClassNameElementExist(String className,int timeOut,boolean isShow) {
		return isElementExist(By.className(className), timeOut, isShow);
	}

	private boolean isElementExist(By by,int timeOut,boolean isShow){
		try {
			AndroidElement element = waitAuto(by,timeOut);
			if(element == null){
				return false;
			}else{
				if(isShow){
                    return element.isDisplayed();
                }
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isTextExist(String text) {
        String str = driver.getPageSource();
        print(str);
        return str.contains(text);
    }
	
	public AndroidElement findById(String id,String desc){
		return findElementBy(By.id(id),desc);
	}
	
	public AndroidElement findById(String id){
		return findById(id,"");
	}
	
	public AndroidElement findByName(String name,String desc){
		return findElementBy(By.name(name), desc);
	}
	
	public AndroidElement findByName(String name){
		return findByName(name,"");
	}
	
	public AndroidElement findByClassName(String className,String desc){
		return findElementBy(By.className(className), desc);
	}
	
	public AndroidElement findByClassName(String className){
		return findByClassName(className,"");
	}
	
	private AndroidElement findElementBy(By by,String str){
		try {
			if(driver != null){
				return (AndroidElement) driver.findElement(by);
			}else{
				print("未识别到设备！");
			}
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
		return null;
	}
//  RGB识别方法引用
	/*public boolean isComImage(int x,int y,int w,int h,String contrastPtah){
		OperateAppium operateAppium = new OperateAppium(this.driver);
		String resultPath = operateAppium.takeScreenShot(x, y, w, h);
		return ComparePicture.isCompareImage(resultPath, contrastPtah);		
	}*/
	
	public boolean isComImage(int x,int y,int w,int h,String contrastPath){
		return isComImage(x, y, w, h, contrastPath, 1);
	}
	public boolean isComImage(int x,int y,int w,int h,String contrastPath,double threshold) {
		OperateAppium operateAppium = new OperateAppium(this.driver);
		String resultPath = operateAppium.takeScreenShot(x, y, w, h);
		try {
			return ComparePicture.isHummingComImage(resultPath, contrastPath, threshold);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	 
}
