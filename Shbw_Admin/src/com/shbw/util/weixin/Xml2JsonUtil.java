package com.shbw.util.weixin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

public class Xml2JsonUtil {
	
	/**
	 * 将流中的xml节点读出
	 * @param is
	 * @return
	 */
	 public static  JSONObject xml2JSON(InputStream is) {
         JSONObject obj = new JSONObject();  
         try {  
             SAXReader sb = new SAXReader();  
             Document doc = sb.read(is);
             Element root = doc.getRootElement();  
             obj.put(root.getName(), iterateElement(root));  
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
         List<Object> list = null;  
         for (int i = 0; i < jiedian.size(); i++) {  
             list = new LinkedList();  
             et = (Element) jiedian.get(i);  
             if (et.getTextTrim().equals("")) {  //当前节点下有子节点的情况下
                 if (et.elements().size() == 0)  
                     continue;  
                 if (obj.containsKey(et.getName())) {  
                     list = (List) obj.get(et.getName());  
                 }  
                 list.add(iterateElement(et));  
                 obj.put(et.getName(), list);  
             } else {  
                 if (obj.containsKey(et.getName())) { 
                     list = (List) obj.get(et.getName());  
                 }  
                 list.add(et.getTextTrim());  
                 obj.put(et.getName(), list);  
             }  
         }  
         return obj;  
     } 

}
