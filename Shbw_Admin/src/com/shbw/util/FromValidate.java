package com.shbw.util;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class FromValidate {
	public static final String FORM_PHONE = "^[1]+[3,5]+\\d{9}$"; // 不对匹配手机号（正则）
	public static final String FORM_EMAIL = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"; // 不对匹配邮箱（正则）
	public static final String DATA_DECIMAL_2= "^\\d*[.]\\d{2}$";//两位小数
	public static final String DATA_DECIMAL_26= "^\\d*[.]\\d{2,6}$";//2-6位小数
	public static final String DATA_NUMBER="[0-9]*";//数字
    /**
     * 字段信息的验证，判断字段的非空和长度
     * @param name 字段值
     * @param length 合法长度
     * @return
     */
	public Boolean judgeLength(String name,int length){
		boolean bool=false;
		if(StringUtils.isNotBlank(name) && name.length()<=length){
			bool=true;
		}
		return bool;		
	}
	
	/**
	 * 判断接口数据
	 * @param name
	 * @param length
	 * @return
	 */
	public String judgeDataLength(String name,int length){
		if(!StringUtils.isNotBlank(name) || name.length()>length){
			return "不为空或长度不能超过"+length;	
		}
		return "";		
	}
	
	/**
	 * 判断接口数据为数字且不超过一定的长度
	 * @param name
	 * @param length
	 * @return
	 */
	public String judgeDataLengthAndNum(String name,int length){
		if(StringUtils.isNotBlank(name)&&name.length()<=length){
			   Pattern pattern = Pattern.compile(DATA_NUMBER); 
			   Matcher isNum = pattern.matcher(name);
			   if( !isNum.matches() ){
				   return "必须为数字类型！";
			   } else{
				   return "";
			   }
		}else{
			return "不能为空或长度不能超过"+length;
		}
	}
	
	/**
	 * 判断2位小数
	 * @param name
	 * @return
	 */
	public String judgeDataFloat2(String name){
		Pattern pattern = Pattern.compile(DATA_DECIMAL_2); 
		 Matcher isNum = pattern.matcher(name);
		if (!isNum.matches()){
	       return "必须精确到两位小数";
	    }
		return "";
	}
	
	public static void main(String[] args) {
		//System.out.println(judgeDataFloat2("1111.00"));
	}
	
	/**
	 * 判断2-6位小数
	 * @param name
	 * @return
	 */
	public String judgeDataFloat3(String name,int length){
		Pattern pattern = Pattern.compile(DATA_DECIMAL_26); 
		 Matcher isNum = pattern.matcher(name);
		if (!isNum.matches()){
	       return "必须保留2-6位小数";
	    }
		return "";
	}
	
	/**
	 * 验证时间格式
	 * @param s
	 * @return
	 */
	public String validDate(String s){  
		try {  
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		    dateFormat.setLenient(false);  
		    dateFormat.parse(s);  
		    return "";  
		 }catch (Exception e) {
		    return "格式不正确";  
		 }  
   }  
	
	/**
	 * 字段信息的验证，对字段在非空的前提下进行验证长度
	 * @param name 字段值
	 * @param length 合法长度
	 * @return
	 */
	public Boolean judgeUnNullLength(String name,int length){
		boolean bool=true;
		if(StringUtils.isNotBlank(name)){
			if(name.length()<=length){
				bool=true;
			}else{
				bool=false;
			}
		}
		return bool;
	}

}
