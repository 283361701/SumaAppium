package com.sumavision.base;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 结果断言
 * @author chenxunlie
 * 20161009
 */

public class Assertion {

    public static boolean flag = true;                      

    public static List<Error> errors = new ArrayList<>();    

    public static void verifyEquals(Object actual, Object expected){
        try {
            Assert.assertEquals(actual, expected);
        } catch(Error e) {
            errors.add(e);
            flag = false;
        }
    }

    public static void verifyEquals(Object actual, Object expected, String message){
        try {
            Assert.assertEquals(actual, expected, message);
        } catch(Error e) {
            errors.add(e);
            flag = false;
        }
    }
    
}
