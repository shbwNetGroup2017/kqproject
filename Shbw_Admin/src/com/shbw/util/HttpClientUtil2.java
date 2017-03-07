package com.shbw.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.shbw.util.weixin.Xml2JsonUtil;

public class HttpClientUtil2 {
	/**
	 * 
	 * @param url
	 * @param xml
	 * @return
	 */
	public static  JSONObject getPrepayJson(String url, String xml){
        HttpClient httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true) ); 
        InputStream is = null;
        PostMethod method=null;
        try {
             method = new PostMethod(url);
             method.setRequestEntity(new StringRequestEntity(xml,"text/xml","utf-8"));
             httpClient.executeMethod(method);
            //读取响应
            is = method.getResponseBodyAsStream();
            JSONObject o =xml2JSON(is);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(method!=null){
                method.releaseConnection();
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

	public static void main(String[] args) {
		String url="http://www.hnfapiao.com/fpserver/FpServlet";
		String xml1="<?xml version='1.0' encoding='gbk'?><business id='YHYZ' comment='用户验证'>"+
	                "<user lxdm='用户类型'><name>310104057643445</name><sn>8BB14444C89D3B12FB6339931CD5B6A9B3388B08732AB3E4FC9A6429AC696EC218BF35DE985EC2D3C8C72CEB4FC03576BC73EE36D6AC0336A7CE26F42E50D087B634902AEF33072C</sn></user></business>";
		JSONObject json1=getPrepayJson(url,xml1);
		System.out.println(json1.get("access_token"));
	}
	
	 public static  JSONObject xml2JSON(InputStream is) {
         JSONObject obj = new JSONObject();  
         try {  
             SAXReader sb = new SAXReader();  
             Document doc = sb.read(is);
             Element root = doc.getRootElement();  
             obj.putAll(iterateElement(root));  
             return obj;  
         } catch (Exception e) {  
             return null;  
         }  
     }  
	  
	 @SuppressWarnings({ "rawtypes", "unchecked" })
     public static Map iterateElement(Element element) {  
         List jiedian = element.elements() ;
         Element et = null;  
		 Map obj = new HashMap();  
         List list = null;  
         for (int i = 0; i < jiedian.size(); i++) {  
             list = new LinkedList();  
             et = (Element) jiedian.get(i);  
             if (et.getTextTrim().equals("")) {  //当前节点下有子节点的情况下
            	  obj.putAll(iterateElement(et));
             } else {  
                 if (obj.containsKey(et.getName())) { 
                     list = (List) obj.get(et.getName());  
                 }  
                 list.add(et.getTextTrim());  
                 obj.put(et.getName(), et.getTextTrim());  
             }  
         }  
         return obj;  
     } 
}
