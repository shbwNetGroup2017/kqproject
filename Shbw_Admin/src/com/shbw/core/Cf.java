package com.shbw.core;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.shbw.util.PropertiesUtil;

/**
 * 拆分
 * @author Administrator
 *
 */
public class Cf {	

	private static final String str1="^[0-9\\-]+(.[0-9]{2})?$";//数字只保留两位小数
	private static final String str2="^[0-9\\-]+(.[0-9]{0,6})?$";//数字最多保留六位小数
	/**
	 * 根据最大金额拆分
	 * @return
	 * @throws Exception 
	 */
	public static String[]  cfje(String jshj,String maxKpje) throws Exception
	{
		if(!jshj.matches(str1)){
			throw new Exception("价税合计格式有误");
		}
		/**
		 * 开票最大金额
		 */
		//String kpzdje=PropertiesUtil.getString("kpzdje");
		String kpzdje=maxKpje;
		/**
		 * 拆分次数
		 */
		int cfcs=(int) Math.floor(Double.parseDouble(jshj)/Double.parseDouble(kpzdje));

		/**
		 * 返回数组
		 */
		String args[] = new String[cfcs+1];
		for(int x=0;x<cfcs;x++)
		{
			args[x]=kpzdje;
		}
		args[cfcs]=sub(jshj,mul(cfcs+"",kpzdje));
		return args;
	}
   
	/**
	 * 价税合计拆分成 税额合计和金额合计
	 * @param jshj 价税合计
	 * @param sl   税率
	 * @return
	 * @throws Exception 
	 */
	public static String[]  cfjs(String jshj,String sl) throws Exception{
		if(!jshj.matches(str1)){
			throw new Exception("价税合计格式有误");
		}
		
		if(!sl.matches(str2)){
			throw new Exception("税率格式有误");
		}
		String [] args=new String[2];
		try {
			args[0]=div(jshj,1+Double.parseDouble(sl)+"");//金额合计
			args[1]=mul(args[0],sl);//税额合计
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return args;
	}
	/**
	* 乘法运算的mul方法
	* @param value1 被乘数
	* @param value2 乘数
	* @return 两个参数的积
	*/
	public static String mul(String value1,String value2){
	      BigDecimal b1 = new BigDecimal(value1);
	      BigDecimal b2 = new BigDecimal(value2);
	      return b1.multiply(b2).setScale(2,   BigDecimal.ROUND_HALF_UP)+"";
	 }
	/**
	 * 减法运算的sub方法
	 * @param value1 被减数
	 * @param value2 减数
	 * @return
	 */
	public static String sub(String value1,String value2){
		 BigDecimal b1 = new BigDecimal(value1);
		 BigDecimal b2 = new BigDecimal(value2);
		 return b1.subtract(b2)+"";
	}
	/**
	 * 加法运算的add方法
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static String add(String value1,String value2){
		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		return b1.add(b2)+"";
	}
	/**
	 * 除法运算
	 * @param value1
	 * @param value2
	 * @param scale
	 * @return
	 * @throws IllegalAccessException
	 */
	 public static String div(String value1,String value2) throws IllegalAccessException{

		BigDecimal b1 = new BigDecimal(value1);
		BigDecimal b2 = new BigDecimal(value2);
		
		return b1.divide(b2,5).setScale(2,   BigDecimal.ROUND_HALF_UP)+""; 
		}
	public static void main(String[] args) {
	}
}
