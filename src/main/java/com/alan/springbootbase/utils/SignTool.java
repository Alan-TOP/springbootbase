package com.alan.springbootbase.utils;

import java.security.MessageDigest;

/**
 * 用于接口加密
 * 客户方与服务方约定好加密串，设定加密安全校验字段sign
 */
public class SignTool {
	
	private static final String KEY = "Alan@2019";

	/**
	 * 计算加密sign
	 * @param timestamp
	 * @param data
	 * @return
	 */
	public static String Sign(String timestamp, String data) {
		String dataStr = data.replace(" ", "").replace("\t", "").replace("\"", "");
		String timestampStr = timestamp.replace(" ", "").replace("\t", "").replace("\"", "");
		String temp = KEY + timestampStr + dataStr;
		System.out.println("dataStr*********:"+dataStr);
		return md5(temp);
	}
	
	private static String md5(String s) {
		String[] HexCode ={ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };  
        MessageDigest  md = null;  
        try {  
            md = MessageDigest.getInstance("MD5"); 
            md.update(s.getBytes("utf-8"));  
            byte[] temp =md.digest();

            String result = "";  
	        for (int i = 0; i < temp.length; i++) { 
	        	int n = temp[i];  
		        if (n < 0) {  
		            n = 256 + n;  
		        }  
		        int d1 = n / 16;  
		        int d2 = n % 16;  
		        result = result+HexCode[d1] + HexCode[d2];  
	        }  
	        System.out.println(result);
	        return result;
        } catch (Exception e) {
        	e.printStackTrace();
            return "";  
        }  
    }

	/**
	 * 判断sign加密串是否准确
	 * @param timestamp
	 * @param data
	 * @param sign
	 * @return
	 */
	public static boolean isEqualsSign(String timestamp, String data,String sign) {

		if (sign == null || sign.length() <= 0){
			return false;
		}
		return sign.equals(Sign(timestamp,data));
	}

}
