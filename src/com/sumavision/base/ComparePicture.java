package com.sumavision.base;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图像识别基类
 * @author chenxunlie
 * */
public class ComparePicture {
	
	public static String RESULT_PATH = System.getProperty("user.dir")+"\\Result";
	public static String CONTRAST_PATH = System.getProperty("user.dir")+"\\Contrast";
		
	/*public static String[][] getPX(String args) { 
        int[] rgb = new int[3];  
        File file = new File(args);  
        BufferedImage bi = null; 
        
        try {  
            bi = ImageIO.read(file);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        int width = bi.getWidth();  
        int height = bi.getHeight();  
        int minx = bi.getMinX();  
        int miny = bi.getMinY();  
        String[][] list = new String[width][height];  
        for (int i = minx; i < width; i++) {  
            for (int j = miny; j < height; j++) {  
                int pixel = bi.getRGB(i, j);  
                rgb[0] = (pixel & 0xff0000) >> 16;  
                rgb[1] = (pixel & 0xff00) >> 8;  
                rgb[2] = (pixel & 0xff);  
                list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];  
  
            }  
        }
        rgb = null;
        return list;  
  
    }  
    *//**
     * RGB像素点对比识别
     * @author chenxunlie
     * @param imgPtah1
     * @param imgPtah2
     * *//*  
    public static boolean isCompareImage(String imgPath1, String imgPath2){  
        String[] images = {imgPath1, imgPath2};  
        if (images.length == 0) {  
            System.exit(0);  
        }  
  
        // 分析图片相似度 begin  
        String[][] list1 = getPX(images[0]);  
        String[][] list2 = getPX(images[1]);  
        int numLike = 0;  
        int numUnlike = 0;  
        int i = 0, j = 0;  
        for (String[] strings : list1) {  
            if ((i + 1) == list1.length) {  
                continue;  
            }  
            for (int m=0; m<strings.length; m++) {  
                try {  
                    String[] value1 = list1[i][j].toString().split(",");  
                    String[] value2 = list2[i][j].toString().split(",");  
                    int k = 0;  
                    for (int n=0; n<value2.length; n++) {  
                        if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {  
                        	numLike++;  
                        } else {  
                        	numUnlike++;  
                        }  
                    }  
                } catch (RuntimeException e) {  
                    continue;  
                }  
                j++;  
            }  
            i++;  
        }  
  
        list1 = getPX(images[1]);  
        list2 = getPX(images[0]);  
        i = 0;  
        j = 0;  
        for (String[] strings : list1) {  
            if ((i + 1) == list1.length) {  
                continue;  
            }  
            for (int m=0; m<strings.length; m++) {  
                try {  
                    String[] value1 = list1[i][j].toString().split(",");  
                    String[] value2 = list2[i][j].toString().split(",");  
                    int k = 0;  
                    for (int n=0; n<value2.length; n++) {  
                        if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 5) {  
                        	numLike++;  
                        } else {  
                        	numUnlike++;  
                        }  
                    }  
                } catch (RuntimeException e) {  
                    continue;  
                }  
                j++;  
            }  
            i++;  
        }
        
        String percent ="";
        try {  
        	percent = ((Double.parseDouble(numLike + "") / Double  
                    .parseDouble((numUnlike + numLike) + "")) + "");  
        	percent = percent.substring(percent.indexOf(".") + 1, percent  
                    .indexOf(".") + 3);  
        } catch (Exception e) {  
        	percent = "0";  
        }  
        if (percent.length() <= 0) {  
        	percent = "0";  
        }  
        if (numUnlike == 0) {  
        	percent = "100";  
        }  
          
        list1 = null;  
        list2 = null;  
        
        System.out.println("相似的元素数量："+numLike);
        System.out.println("不相似的元素数量："+numUnlike);        
        if (!percent.equals("100")) {  
            return false;  
        } else {  
            return true;  
        }          
    }*/
	
    /**
     * 感知哈希算法
     * @author chenxunlie
     * @param imgPath1 图片1
     * @param imgPath2 图片2
     * @param threshold 相似度阀值
     * */
    public static boolean isHummingComImage(String imgPath1, String imgPath2, double threshold) throws IOException {
    	int[] pixels1 = hummingResult(imgPath1);
    	int[] pixels2 = hummingResult(imgPath2);
    	int hammingDistance = getHammingDistance(pixels1,pixels2);
    	double similarity = calSimilarity(hammingDistance);
    	System.out.println("图片相似度："+similarity);
    	if (similarity>=threshold){
    		return true;    		
    	} else {
    		return false;
    	}
    }
    
    public static int[] hummingResult(String imgPath) throws IOException {
    	File imageFile = new File(imgPath);
    	Image image = ImageIO.read(imageFile);
    	image = toGrayscale(image);
    	image = scale(image);
    	int[] pixels = getPixels(image);
    	int averageColor = getAverageOfPixelArray(pixels);
    	return pixels = getPixelDeviateWeightsArray(pixels, averageColor);
    }
    
    //将图片转化为BufferedImage类型
    public static BufferedImage convertToBufferedFrom(Image srcImage) {
    	BufferedImage bufferedImage = new BufferedImage(srcImage.getWidth(null),
    			srcImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g = bufferedImage.createGraphics();
    	g.drawImage(srcImage, null, null);
    	g.dispose();
    	return bufferedImage;
    }
    
    //转化为灰度图
    public static BufferedImage toGrayscale(Image image) {
    	BufferedImage sourceBuffered = convertToBufferedFrom(image);
    	ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
    	ColorConvertOp op = new ColorConvertOp(cs, null);
    	BufferedImage grayBuffered = op.filter(sourceBuffered, null);
    	return grayBuffered;
    }
    
    //压缩图片为32*32
    public static Image scale(Image image) {
    	image = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    	return image;
    }
    
    //将像素点存放至数组中  
    public static int[] getPixels(Image image) {
    	int width = image.getWidth(null);
    	int height = image.getHeight(null);
    	int[] pixels = convertToBufferedFrom(image).getRGB(0, 0, width, height,
    			null, 0, width);
    	return pixels;
    }
    
    //去灰度图的平均像素值
    public static int getAverageOfPixelArray(int[] pixels) {
    	 Color color;
    	 long sumRed = 0;
    	 for (int i = 0; i < pixels.length; i++) {
    		 color = new Color(pixels[i], true);
    		 sumRed += color.getRed();    		 
    	 }
    	 int averageRed = (int) (sumRed / pixels.length);
    	 return averageRed;
    }
    
    //获取灰度图的像素点对比数组
    public static int[] getPixelDeviateWeightsArray(int[] pixels,final int averageColor) {
    	 Color color;
    	 int[] dest = new int[pixels.length];
    	 
    	 for (int i = 0; i < pixels.length; i++) {
    		 color = new Color(pixels[i], true);
    		 dest[i] = color.getRed() - averageColor > 0 ? 1 : 0;
    	 }
    	 return dest;
    	 
    }
    
    //通过汉明距离计算相似度
    public static int getHammingDistance(int[] a, int[] b) {
    	int sum = 0;
    	for (int i = 0; i < a.length; i++) {
    		sum += a[i] == b[i] ? 0 : 1;
    	}
    	return sum;
    }
    
    //通过汉明距离算法计算相似度
    public static double calSimilarity(int hammingDistance){
    	int length = 32*32;
    	double similarity = (length - hammingDistance) / (double) length;
    	similarity = java.lang.Math.pow(similarity, 2); //使用指数曲线调整结果
    	return similarity;
    }
   
}
