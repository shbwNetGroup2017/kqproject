package com.shbw.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 接口传输数据的验证
 * @author Administrator
 *
 */
public class InterfaceDataValidate {

	/**
	 * 同步订单类数据的验证
	 * @param zMap
	 * @param mxListMap
	 * @return
	 */
	public static String validateOrderDataInfo(Map<Object, Object> zMap, List<Map<Object, Object>> mxListMap) {
		String errorMsg = "";
		String jylsh = zMap.get("jylsh").toString();
		errorMsg += validateZmap(zMap);
		for (Map<Object, Object> map : mxListMap) {
			errorMsg += validateMmap(map, jylsh);
		}
		return errorMsg;
	}
	
	/**
	 * 同步信息数据的验证
	 * @param map
	 * @return
	 */
	public static String validateCustomDataInfo(Map<Object,Object> map){
		FromValidate fv = new FromValidate();
		String infoId=fv.judgeDataLength(map.get("id").toString(), 20);
		if(StringUtils.isNotBlank(infoId)){
			return "数据ID字段"+infoId;
		}
		String infoGfmc=fv.judgeDataLength(map.get("gfmc").toString(), 20);
		if(StringUtils.isNotBlank(infoGfmc)){
			return "购方名称字段"+infoGfmc;
		}
		String infoGflx=fv.judgeDataLength(map.get("gflx").toString(), 20);
		if(StringUtils.isNotBlank(infoGflx)){
			return "购方类型字段"+infoGflx;
		}
		return "";
	}
	
	/**
	 * 同步非订单类数据的验证
	 * @param map
	 * @return
	 */
	public static String validateUnOrderDataInfo(Map<Object,Object> map){
		FromValidate fv = new FromValidate();
		String infoId=fv.judgeDataLength(map.get("id").toString(), 20);
		if(StringUtils.isNotBlank(infoId)){
			return "数据ID字段"+infoId;
		}
		String infoXtId=fv.judgeDataLength(map.get("gf_yxtid").toString(), 20);
		if(StringUtils.isNotBlank(infoXtId)){
			return "购方客户号字段"+infoXtId;
		}
		String infoJylsly=fv.judgeDataLengthAndNum(map.get("jylsly").toString(), 1);
		if(StringUtils.isNotBlank(infoJylsly)){
			return "交易流水来源字段"+infoJylsly;
		}
		String infoYwlx=fv.judgeDataLengthAndNum(map.get("ywlx").toString(),20);
		if(StringUtils.isNotBlank(infoYwlx)){
			return "业务类型字段"+infoYwlx;
		}
		String infoJshj=fv.judgeDataFloat2(map.get("je").toString());
		if(StringUtils.isNotBlank(infoJshj)){
			return "价税合计字段"+infoJshj;
		}
		String hsbz=map.get("hsbz").toString();
		if(!hsbz.equals("0")&&!hsbz.equals("1")){
			return "含税标识字段只能是0或1";
		}
		String sjbs=map.get("sjbs").toString();
		if(!sjbs.equals("0")&&!sjbs.equals("1")){
			return "数据标识字段只能是0或1";
		}
		String jslx=map.get("jslx").toString();
		if(!jslx.equals("0")&&!jslx.equals("1")&&!jslx.equals("2")&&!jslx.equals("3")){
			return "结算类型字段只能是0,1,2,3";
		}
		return "";
	}
	
	/**
	 * 主信息的校验
	 * 
	 * @param zMap
	 * @return
	 */
	public static String validateZmap(Map<Object, Object> map) {
		// 流水号
		String jylsh = map.get("jylsh").toString();
		if (jylsh.equals("") || jylsh.length() >= 20) {
			return "交易流水号不能为空或不能超过20个字符！";
		}
		FromValidate fv = new FromValidate();
		// 交易流水时间
		String sjMsg = fv.validDate(map.get("jylssj").toString());
		if (!sjMsg.equals("")) {
			return "交易流水时间" + sjMsg;
		}
		// 购方原系统ID
		String gfMsg = fv.judgeDataLength(map.get("gf_yxtid").toString(), 20);
		if (!gfMsg.equals("")) {
			return "购方原系统ID" + gfMsg;
		}
		// 数据来源
		String lyMsg = map.get("jylsly").toString();
		if (!lyMsg.equals("0") && !lyMsg.equals("1") && !lyMsg.equals("2") && !lyMsg.equals("3")) {
			return "数据来源必须为0,1,2,3";
		}
		// 销方原系统ID
		String xtMsg = fv.judgeDataLength(map.get("xf_yxtid").toString(), 20);
		if (!xtMsg.equals("")) {
			return "销方原系统ID" + xtMsg;
		}
		// 价税合计
		String jsMsg = fv.judgeDataFloat2(map.get("jshj").toString());
		if (!jsMsg.equals("")) {
			return "价税合计" + jsMsg;
		}
		// 含税标识
		String hsbz = map.get("hsbz").toString();
		if (!hsbz.equals("")) {
			if (!hsbz.equals("0") && !hsbz.equals("1")) {
				return "含税标识格式不合法!";
			}
		}
		// 收款人
		if (map.get("skr").equals("")) {
			return "收款人不能为空!";
		}
		// 开票人
		if (map.get("kpr").equals("")) {
			return "审核人不能为空!";
		}
		// 复核人
		if (map.get("fhr").equals("")) {
			return "复核人不能为空!";
		}
		return "";
	}

	/**
	 * 明细数据的校验
	 * 
	 * @param map
	 * @param jylsh
	 * @return
	 */
	public static String validateMmap(Map<Object, Object> map, String jylsh) {
		if (!map.get("jylsh").equals(jylsh)) {
			return "主表和明细表中的交易流水号不一致";
		}
		FromValidate fv = new FromValidate();
		// 商品编码
		if (!map.get("spbm").equals("")) {
			String xhMsg = fv.judgeDataLengthAndNum(map.get("spbm").toString(), 20);
			if (!xhMsg.equals("")) {
				return "商品编码" + xhMsg;
			}
		}
		// 商品金额
		String jeMsg = fv.judgeDataFloat2(map.get("spje").toString());
		if (!jeMsg.equals("")) {
			return "商品金额" + jeMsg;
		}
		// 商品税额
		if (!map.get("spse").equals("")) {
			String seMsg = fv.judgeDataFloat2(map.get("spse").toString());
			if (!seMsg.equals("")) {
				return "商品税额" + seMsg;
			}
		}
		// 商品单位
		String dw = map.get("dw").toString();
		if (dw.equals("") || dw.length() >= 20) {
			return "单位不能为空或长度不能超过20！";
		}
		// 商品单价
		String djMsg = fv.judgeDataFloat3(map.get("dj").toString(), 6);
		if (!djMsg.equals("")) {
			return "商品单价" + djMsg;
		}
		// 商品数量
		String slMsg = fv.judgeDataFloat3(map.get("spsl").toString(), 6);
		if (!slMsg.equals("")) {
			return "商品数量" + slMsg;
		}
		// 商品税率
		String sMsg = fv.judgeDataFloat2(map.get("sl").toString());
		if (!sMsg.equals("")) {
			return "商品税率" + sMsg;
		}
		// 原商品编码
		if (map.get("yspbm").equals("")) {
			return "原商品编码不为空！";
		}
		return "";
	}
}