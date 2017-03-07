package com.shbw.util.weixin;

import com.shbw.util.PasswordByMd5;

public class MD5SignUtil {
	
	public static String sign(String content, String key)
            throws Exception{
		String str="";
		if(!key.equals("")){
			str="&key="+key;
		}
		content+=str;
        return MD5Util.MD5(content).toUpperCase();
    }
	
	public static void main(String[] args) {
		System.out.println(MD5Util.MD5("cyl1225"));
	}
}
