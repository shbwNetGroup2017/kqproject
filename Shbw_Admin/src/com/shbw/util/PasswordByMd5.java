package com.shbw.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5��������м���
 * @author Administrator
 *
 */
public class PasswordByMd5 {
	
	public static String getPassword(String password) throws UnsupportedEncodingException{
		String str="";
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(password.getBytes("UTF-8"));
			   byte b[] = md.digest();
			   int x;
			   StringBuffer buf = new StringBuffer("");
			   for (int i = 0; i < b.length; i++) {
			    x = b[i];
			    if (x < 0)
			       x += 256;
			    if (x < 16)
			       buf.append("0");
			    buf.append(Integer.toHexString(x));
			   }
			   str = buf.toString();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		return str;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getPassword("appid=wxac1e64f35ff2dc31&body=上海百旺金赋-服务费的缴纳&device_info=WEB&mch_id=1350144201&nonce_str=b3c03083-3911-42ed-a399-3bd60c2c&notify_url=http://www.shbwjf.iego.net/FHORACLE/WeChatPay/appPaySuccess/&openid=orVj2jkrVkAkPMkWKQgyasf7PXLg&out_trade_no=14694133441057482180231884458010&spbill_create_ip=172.20.10.2&total_fee=1&trade_type=JSAPI&key=shanghaibaiwangjinfukejiyouxiang"));
	}
}
