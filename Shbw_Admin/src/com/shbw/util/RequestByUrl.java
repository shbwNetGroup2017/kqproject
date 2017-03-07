package com.shbw.util;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class RequestByUrl {
	
	/**
	 * 通过URL获取JSON数据
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static JSONObject getJsonByUrl(String url) throws HttpException, IOException{
		HttpClient httpClient=new HttpClient();
		GetMethod getMethod=new GetMethod(url);
		JSONObject json=new JSONObject();
		int statusCode=httpClient.executeMethod(getMethod);
		if(statusCode==HttpStatus.SC_OK){
			byte[] rb=getMethod.getResponseBody();
			String result= new String(rb,"UTF-8");
			json=JSONObject.fromObject(result);
		}
		return json;
	}

}
