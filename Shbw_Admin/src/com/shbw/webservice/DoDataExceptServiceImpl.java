package com.shbw.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.shbw.service.webservice.DataSynService;
import com.shbw.util.InterfaceDataValidate;
import com.shbw.util.JsonTools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 *同步数据接口
 * @author cyl
 *
 */
@Service
public class DoDataExceptServiceImpl implements DoDataExceptService {

	@Resource(name = "dataSynService")
	private DataSynService dataSynService;

	/**
	 * 接口数据——非订单信息数据入库，完结
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String saveUnOrderDataInfo(String dataInfo) {
		JSONObject jsonObject=JSONObject.fromObject(dataInfo);
		String flag=jsonObject.getString("flag");
		JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("content"));
		Iterator<Object> it = jsonArray.iterator();
		//数据返回
		JSONObject returnJsonMsg=new JSONObject();
		JSONArray returnArrayMsg=new JSONArray();
		int yes=0;
		int no=0;
		if(flag.equals("I")||flag.equals("U")){//同步插入和同步更新信息
			try{
				while (it.hasNext()) {
					//数据的校验和入库
					String errorMsg="";
					JSONObject jsonObj = (JSONObject) it.next();
					Map<Object, Object> map = this.getMap(jsonObj.toString());
					errorMsg=InterfaceDataValidate.validateUnOrderDataInfo(map);	
					JSONObject json=new JSONObject();
					json.put("id", map.get("id"));
					if(StringUtils.isNotBlank(errorMsg)){
						no++;
						json.put("state", "1");
						json.put("error", errorMsg);
					}else{
						try {
							dataSynService.insertUnDdjy(map,flag);
							yes++;
							json.put("state", "0");
						} catch (Exception e) {
							e.printStackTrace();
							no++;
							json.put("state", "1");
							json.put("error", "数据入库异常！");
						}
					}
					returnArrayMsg.add(json);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(flag.equals("D")){
			while(it.hasNext()){
				JSONObject jsonObj = (JSONObject) it.next();
				JSONObject json=new JSONObject();
				Map<Object,Object> map=new HashMap<Object,Object>();
				try {
					map.put("id", jsonObj.getString("id"));
					map.put("sjly", jsonObj.getString("sjly"));
					dataSynService.deleteUnDdjy(map);
					yes++;
					json.put("state", "0");
				} catch (Exception e) {
					e.printStackTrace();
					no++;
					json.put("state", "1");
					json.put("error", "数据库操作异常！");
				}
				returnArrayMsg.add(json);
			}
		}
		returnJsonMsg.put("success",yes);
		returnJsonMsg.put("fail",no);
		returnJsonMsg.put("mxInfo", returnArrayMsg);
		return returnJsonMsg.toString();
	}

	/**
	 * 接口数据——订单信息数据入库，完结
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String saveOrderDataInfo(String dataInfo) {
		JSONObject jsonObject=JSONObject.fromObject(dataInfo);
		String flag=jsonObject.getString("flag");
		JSONObject returnJsonMsg=new JSONObject();
		JSONArray returnArrayMsg=new JSONArray();
		int yes=0;
		int no=0;
		JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("content"));
		Iterator<Object> it = jsonArray.iterator();
		if(flag.equals("I")||flag.equals("U")){//同步插入和同步更新信息
			while(it.hasNext()){
				String errorMsg="";
				JSONObject jsonObj = (JSONObject) it.next();
				JSONArray arrayMx=JSONArray.fromObject(jsonObj.get("jymx").toString());
				jsonObj.remove("jymx");
				//第一步，提取主信息和明细信息
				Map<Object, Object> zMap = this.getMap(jsonObj.toString()); //主信息
				List<Map<Object,Object>> mxListMap=new ArrayList<Map<Object,Object>>();//明细信息
				Iterator<Object> itMx = arrayMx.iterator();
				while(itMx.hasNext()){
					JSONObject json=(JSONObject)itMx.next();
					json.put("sjly", zMap.get("jylsly"));
					mxListMap.add(this.getMap(json.toString()));
				}
				//第二步，主信息和明细信息的数据验证
				errorMsg=InterfaceDataValidate.validateOrderDataInfo(zMap, mxListMap);
				//第三步，数据入库及返回信息
				JSONObject json=new JSONObject();
				json.put("id", zMap.get("id"));
				if(StringUtils.isNotBlank(errorMsg)){
					no++;
					json.put("state", "1");
					json.put("error", errorMsg);
				}else{
					try {
						dataSynService.insertDdjyls(flag,zMap, mxListMap);
						yes++;
						json.put("state", "0");
					} catch (Exception e) {
						e.printStackTrace();
						no++;
						json.put("state", "1");
						json.put("error", "数据入库异常！");
					}
				}
				returnArrayMsg.add(json);
			}
		}else if(flag.equals("DZ")){//删除主表信息
			while(it.hasNext()){
				JSONObject jsonObj = (JSONObject) it.next();
				JSONObject json=new JSONObject();
				Map<Object,Object> map=new HashMap<Object,Object>();
				try {
					map.put("jylsh", jsonObj.getString("jylsh"));
					map.put("sjly", jsonObj.getString("sjly"));
					dataSynService.deleteDdjylsZ(map);
					yes++;
					json.put("state", "0");
				} catch (Exception e) {
					e.printStackTrace();
					no++;
					json.put("state", "1");
					json.put("error", "数据库操作异常！");
				}
				returnArrayMsg.add(json);
			}
		}else if(flag.equals("DM")){//删除明细表信息
			while(it.hasNext()){
				JSONObject json=new JSONObject();
				JSONObject jsonObj = (JSONObject) it.next();
				Map<Object,Object> map=new HashMap<Object,Object>();
				try {
					map.put("id", jsonObj.getString("id"));
					map.put("sjly", jsonObj.getString("sjly"));
					dataSynService.deleteDdjylsM(map);
					yes++;
					json.put("state", "0");
				} catch (Exception e) {
					e.printStackTrace();
					no++;
					json.put("state", "1");
					json.put("error", "数据库操作异常！");
				}
			}
		}
		returnJsonMsg.put("success",yes);
		returnJsonMsg.put("fail",no);
		returnJsonMsg.put("mxInfo", returnArrayMsg);
		return returnJsonMsg.toString();
	}

	/**
	 * 接口数据——客户信息数据入库
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String saveCustomDataInfo(String dataInfo) {
		JSONObject jsonObject=JSONObject.fromObject(dataInfo);
		String flag=jsonObject.getString("flag");
		JSONArray jsonArray=JSONArray.fromObject(jsonObject.get("content"));
		Iterator<Object> it = jsonArray.iterator();
		//数据返回
		JSONObject returnJsonMsg=new JSONObject();
		JSONArray returnArrayMsg=new JSONArray();
		int yes=0;
		int no=0;
		if(flag.equals("I")||flag.equals("U")){//同步插入和同步更新信息
			try{
				while (it.hasNext()) {
					//数据的校验和入库
					String errorMsg="";
					JSONObject jsonObj = (JSONObject) it.next();
					Map<Object, Object> map = this.getMap(jsonObj.toString());
					errorMsg=InterfaceDataValidate.validateCustomDataInfo(map);	
					JSONObject json=new JSONObject();
					json.put("id", map.get("id"));
					if(StringUtils.isNotBlank(errorMsg)){
						no++;
						json.put("state", "1");
						json.put("error", errorMsg);
					}else{
						try {
							dataSynService.insertCustom(map, flag);
							yes++;
							json.put("state", "0");
						} catch (Exception e) {
							e.printStackTrace();
							no++;
							json.put("state", "1");
							json.put("error", "数据入库异常！");
						}
					}
					returnArrayMsg.add(json);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(flag.equals("D")){//删除客户信息数据
			while(it.hasNext()){
				JSONObject jsonObj = (JSONObject) it.next();
				JSONObject json=new JSONObject();
				Map<Object,Object> map=new HashMap<Object,Object>();
				try {
					map.put("id", jsonObj.getString("id"));
					map.put("sjly", jsonObj.getString("sjly"));
					dataSynService.deleteCustom(map);
					yes++;
					json.put("state", "0");
				} catch (Exception e) {
					e.printStackTrace();
					no++;
					json.put("state", "1");
					json.put("error", "数据库操作异常！");
				}
				returnArrayMsg.add(json);
			}
			
		}
		returnJsonMsg.put("success",yes);
		returnJsonMsg.put("fail",no);
		returnJsonMsg.put("mxInfo", returnArrayMsg);
		return returnJsonMsg.toString();
	}
	
	
	public Map<Object,Object> getMap(String map){
		return JsonTools.parseJSON2Map2(map.toString());
	}
}
