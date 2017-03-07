package com.shbw.webservice;


import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class test {
	//商品信息的测试
/*	public static void main(String[] args) throws Exception { 
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://192.168.1.145:8080/Shbw_Admin/service/dataService?wsdl");
		QName qname = new QName("http://webservice.shbw.com/", "saveCustomDataInfo");
		
		JSONObject json1=new JSONObject();
		json1.put("flag", "I");
		
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("id", "1");
		jsonObj.put("gfsh", "310104205507586");
		jsonObj.put("gfmc", "上海百旺金赋科技有限公司");
		jsonObj.put("gfyh", "中国工商银行");
		jsonObj.put("gfyhzh", "62220213087652456");
		jsonObj.put("gfdz", "上海市徐汇区");
		jsonObj.put("gfdh", "18121296517");
		jsonObj.put("gflx", "1");
		jsonObj.put("sjly", "0");
		jsonObj.put("gf_yxtid", "110202");
		jsonArray.add(jsonObj);
		
		JSONObject jsonObj1=new JSONObject();
		jsonObj1.put("id", "2");
		jsonObj1.put("gfsh", "91011520172709");
		jsonObj1.put("gfmc", "北京百旺金赋科技有限公司");
		jsonObj1.put("gfyh", "中国农业银行");
		jsonObj1.put("gfyhzh", "622848508887652761");
		jsonObj1.put("gfdz", "北京市东城区");
		jsonObj1.put("gfdh", "18121296520");
		jsonObj1.put("gflx", "0");
		jsonObj1.put("sjly", "1");
		jsonObj1.put("gf_yxtid", "111111");
		jsonArray.add(jsonObj1);
		
		json1.put("content", jsonArray);
		Object[] result = client.invoke(qname, json1.toString());
		JSONObject json=JSONObject.fromObject(result[0].toString());
	
        System.out.println(json.toString());
	}*/
	
	//非订单信息的测试
	public static void main(String[] args) throws Exception { 
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://192.168.1.145:8080/Shbw_Admin/service/dataService?wsdl");
		QName qname = new QName("http://webservice.shbw.com/", "saveUnOrderDataInfo");
		
		JSONObject json1=new JSONObject();
		json1.put("flag", "I");
		
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("id", "1");
		jsonObj.put("gf_yxtid", "1");
		jsonObj.put("jylsly", "0");
		jsonObj.put("xf_yxtid", "1");
		jsonObj.put("ywlx", "1");
		jsonObj.put("je", "120.80");
		jsonObj.put("terminal_id", "121212");
		jsonObj.put("jszh", "");
		jsonObj.put("kqzh", "");
		jsonObj.put("merchant_id", "");
		jsonObj.put("hsbz", "0");
		jsonObj.put("sjbs", "0");
		jsonObj.put("jslx", "3");
		jsonArray.add(jsonObj);
		
		JSONObject jsonObj1=new JSONObject();
		jsonObj1.put("id", "2");
		jsonObj1.put("gf_yxtid", "1");
		jsonObj1.put("jylsly", "0");
		jsonObj1.put("xf_yxtid", "1");
		jsonObj1.put("ywlx", "1");
		jsonObj1.put("je", "200.00");
		jsonObj1.put("terminal_id", "");
		jsonObj1.put("jszh", "120801");
		jsonObj1.put("kqzh", "");
		jsonObj1.put("merchant_id", "");
		jsonObj1.put("hsbz", "0");
		jsonObj1.put("sjbs", "0");
		jsonObj1.put("jslx", "3");
		jsonArray.add(jsonObj1);
		
		json1.put("content", jsonArray);
		Object[] result = client.invoke(qname, json1.toString());
		JSONObject json=JSONObject.fromObject(result[0].toString());
	
        System.out.println(json.toString());
	}
	
	//订单数据信息
/*	public static void main(String[] args) throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://192.168.1.145:8080/Shbw_Admin/service/dataService?wsdl");
		QName qname = new QName("http://webservice.shbw.com/", "saveOrderDataInfo");
		
		JSONObject json1=new JSONObject();
		json1.put("flag", "I");
		
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("id", "1");
		jsonObj.put("jylsh", "1");//
		jsonObj.put("jylssj", "2017-01-11 12:20:00");//
		jsonObj.put("gf_yxtid", "001");//
		jsonObj.put("jylsly", "0");//
		jsonObj.put("xf_yxtid", "0");//
		jsonObj.put("jshj", "424.00");//
		jsonObj.put("hsbz", "0");//
		jsonObj.put("bz", "0");
		jsonObj.put("skr", "张三");
		jsonObj.put("kpr", "李四");
		jsonObj.put("fhr", "王五");
		jsonObj.put("hsslbs", "0");
		jsonObj.put("kcje", "0");
		//1.明细信息
		JSONArray jsonArrayMx=new JSONArray();
		JSONObject jsonMx=new JSONObject();
		jsonMx.put("jylsh", "1");
		jsonMx.put("spbm", "123");
		jsonMx.put("spje", "100.00");
		jsonMx.put("spse", "6.00");
		jsonMx.put("dw", "个");
		jsonMx.put("ggxh", "112091-1");
		jsonMx.put("dj", "50.00");
		jsonMx.put("spsl", "2.00");
		jsonMx.put("sl", "0.06");
		jsonMx.put("yspbm", "123");
		jsonMx.put("yspmc", "电脑");
		jsonArrayMx.add(jsonMx);
		
		JSONObject jsonMx1=new JSONObject();
		jsonMx1.put("jylsh", "1");
		jsonMx1.put("spbm", "123");
		jsonMx1.put("spje", "300.00");
		jsonMx1.put("spse", "18.00");
		jsonMx1.put("dw", "个");
		jsonMx1.put("ggxh", "112091-1");
		jsonMx1.put("dj", "100.00");
		jsonMx1.put("spsl", "3.00");
		jsonMx1.put("sl", "0.06");
		jsonMx1.put("yspbm", "123");
		jsonMx1.put("yspmc", "手机");
		jsonArrayMx.add(jsonMx1);
		jsonObj.put("jymx", jsonArrayMx);
		jsonArray.add(jsonObj);
		
		JSONObject jsonObj1=new JSONObject();
		jsonObj1.put("id", "2");
		jsonObj1.put("jylsh", "2");
		jsonObj1.put("jylssj", "2017-01-11 09:00:20");
		jsonObj1.put("gf_yxtid", "001");
		jsonObj1.put("jylsly", "0");
		jsonObj1.put("jshj", "128.70");
		jsonObj1.put("xf_yxtid", "0");
		jsonObj1.put("hsbz", "0");
		jsonObj1.put("bz", "0");
		jsonObj1.put("skr", "0");
		jsonObj1.put("kpr", "0");
		jsonObj1.put("fhr", "0");
		jsonObj1.put("hsslbs", "0");
		jsonObj1.put("kcje", "0");
		
		JSONArray jsonArrayMx2=new JSONArray();
		//2.明细数据
		JSONObject jsonMx3=new JSONObject();
		jsonMx3.put("jylsh", "2");//Y
		jsonMx3.put("spbm", "123");//N
		jsonMx3.put("spje", "110.00");//N
		jsonMx3.put("spse", "18.70");//N
		jsonMx3.put("dw", "个");//Y
		jsonMx3.put("ggxh", "112091-2");
		jsonMx3.put("dj", "55.00");//Y
		jsonMx3.put("spsl", "2.00");//N
		jsonMx3.put("sl", "0.17");//Y
		jsonMx3.put("yspbm", "124");
		jsonMx3.put("yspmc", "手机");
		jsonArrayMx2.add(jsonMx3);
		jsonObj1.put("jymx", jsonArrayMx2);
		jsonArray.add(jsonObj1);
		
		json1.put("content", jsonArray);
		Object[] result = client.invoke(qname, json1.toString());
		JSONObject json=JSONObject.fromObject(result[0].toString());
		System.out.println(json.toString());
	}*/

}
