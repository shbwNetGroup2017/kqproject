package com.shbw.core;

import java.text.DecimalFormat;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.shbw.util.PageData;

public class KpCheckUtils {
	private static final String str0="^[0-9]{1,20}$";//1-20数字
	private static final String str1="^[0-9\\-]+(.[0-9]{0,2})?$";//数字最多保留两位小数
	private static final String str2="^[0-9\\-]+(.[0-9]{0,6})?$";//数字最多保留六位小数
	private static final String str3="^[0-9]+(.[0-9]{2,6})?$";//数字最多保留六位小数，最少保留两位
	private static final String str4="^[0-9\\-]+(.[0-9]{2})?$";//数字只保留两位小数
	private static final String str5="^[A-Za-z0-9]{1,20}$";//1-20字符
	private static final String str6="^[A-Za-z0-9]+$";//任意字符串
	private static final String str7="[0-9]*";//数字
	private DecimalFormat format = new DecimalFormat("0.00");
	
	/**
	 * 未含税盘信息的发票信息校验
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PageData kpCheck(PageData map)throws Exception{
		/**
		 * 价税分离
		 */
		Jsfl js = new Jsfl(6);
		

		/**
		 * 第一部分主信息校验
		 */
		PageData zxx;
		/**
		 * 明细信息校验
		 */
		PageData mx;
		/**
		 * 第二部分主信息校验
		 */
		PageData zxx2;

			zxx=zxxCheck(map);
			if(zxx==null)
			{
				List<PageData> mxList=(List) map.get("mx");
				for(int x=0;x<mxList.size();x++)
				{
					PageData mxMap=mxList.get(x);
					mx=mxxxCheck(mxMap,js,x);
					if(mx==null)
					{
						continue;
						
					}
					else
					{
						return mx;
					}
				}
				zxx2=zxxCheck2(map,js);
				if(zxx2==null)
				{
					return null;
				}
				else
				{
					return zxx2;
				}
				
			}
			else
			{
				return zxx;
			}
		
		
		
	}
	
	/**
	 * 针对含有税盘信息的发票信息校验
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PageData kpCheckAndSP(PageData map) throws Exception{
		
		/**
		 * 价税分离
		 */
		Jsfl js = new Jsfl(6);
		
		/**
		 * 税控盘信息校验
		 */
		PageData skpxx=skpxxCheck(map);
		/**
		 * 第一部分主信息校验
		 */
		PageData zxx;
		/**
		 * 明细信息校验
		 */
		PageData mx;
		/**
		 * 第二部分主信息校验
		 */
		PageData zxx2;
		if(skpxx==null)
		{
			zxx=zxxCheck(map);
			if(zxx==null)
			{
				List<PageData> mxList=(List) map.get("mx");
				for(int x=0;x<mxList.size();x++)
				{
					mx=mxxxCheck(map,js,x);
					if(mx==null)
					{
						zxx2=zxxCheck2(map,js);
						if(zxx2==null)
						{
							return null;
						}
						else
						{
							return zxx2;
						}
						
					}
					else
					{
						return mx;
					}
				}
				
			}
			else
			{
				return zxx;
			}
		}
		else
		{
			return skpxx;
		}
			
		
		
		return null;
	}
	
	/**
	 * 发票主信息校验1
	 * @param map
	 * @return
	 */
	public PageData zxxCheck(PageData map){

		if(map.getString("fplx").length()!=3) 
		{
			return returnMessage("发票类型有误",map.getString("fplx"));
		}
		if(map.getString("kpbz").length()!=1)
		{
			return returnMessage("开票类型有误",map.getString("kpbz"));
		}

		
		/**
		 * 销方信息校验
		 */
		if(StringUtils.isBlank(map.getString("xfsh"))||!map.getString("xfsh").matches(str5)) 
		{
			return returnMessage("销方税号有误",map.getString("xfsh"));
		}
		
		if(StringUtils.isBlank(map.getString("xfmc"))||map.getString("xfmc").length()>100) 
		{
			return returnMessage("销方名称有误",map.getString("xfmc"));
		}
		if(StringUtils.isBlank(map.getString("xfdz"))||((map.getString("xfdz")).length()>60))
		{
			return returnMessage("销方地址有误",map.getString("xsfdzdh"));
		}
		if(StringUtils.isBlank(map.getString("xfdh"))||((map.getString("xfdh")).length()>20))
		{
			return returnMessage("销方电话有误",map.getString("xfdh"));
		}
		if(StringUtils.isBlank(map.getString("xfyh"))||((map.getString("xfyh")).length()>60))
		{
			return returnMessage("销方银行",map.getString("xfyh"));
		}
		
		if(StringUtils.isBlank(map.getString("xfyhzh"))||map.getString("xfyhzh").length()>20||!map.getString("xfyhzh").matches(str7))
		{
			return returnMessage("销方银行账号",map.getString("xfyhzh"));
		}
		String bz="0";//0:个人，1：企业
		if("0".equals(bz)){
			/**
			 * 购方信息校验(企业)
			 */
			if(StringUtils.isBlank(map.getString("gfsh"))||!map.getString("gfsh").matches(str5)) 
			{
				return returnMessage("购方税号有误",map.getString("gfsh"));
			}
			
			if(StringUtils.isBlank(map.getString("gfmc"))||map.getString("gfmc").length()>100) 
			{
				return returnMessage("购方名称有误",map.getString("gfmc"));
			}
			if(StringUtils.isBlank(map.getString("gfdz"))||((map.getString("gfdz")).length()>60))
			{
				return returnMessage("购方地址有误",map.getString("gfdz"));
			}
			if(StringUtils.isBlank(map.getString("gfdh"))||((map.getString("gfdh")).length()>20))
			{
				return returnMessage("购方电话有误",map.getString("gfdh"));
			}
			if(StringUtils.isBlank(map.getString("gfyh"))||((map.getString("gfyh")).length()>60))
			{
				return returnMessage("购方银行",map.getString("gfyh"));
			}
			
			if(StringUtils.isBlank(map.getString("gfyhzh"))||map.getString("gfyhzh").length()>20||!map.getString("gfyhzh").matches(str7))
			{
				return returnMessage("购方银行账号",map.getString("gfyhzh"));
			}
			
		}
		else
		{
			/**
			 * 购方信息校验(个人)
			 */
			if(StringUtils.isBlank(map.getString("gfsh"))||!map.getString("gfsh").matches(str5)) 
			{
				return returnMessage("购方税号有误",map.getString("gfsh"));
			}
			
			if(StringUtils.isBlank(map.getString("gfmc"))||map.getString("gfmc").length()>100) 
			{
				return returnMessage("购方名称有误",map.getString("gfmc"));
			}
			if(StringUtils.isBlank(map.getString("gfdz"))||((map.getString("gfdz")).length()>60))
			{
				return returnMessage("购方地址有误",map.getString("gfdz"));
			}
			if(StringUtils.isBlank(map.getString("gfdh"))||((map.getString("gfdh")).length()>20))
			{
				return returnMessage("购方电话有误",map.getString("gfdh"));
			}
			if(StringUtils.isBlank(map.getString("gfyh"))||((map.getString("gfyh")).length()>60))
			{
				return returnMessage("购方银行",map.getString("gfyh"));
			}
			
			if(StringUtils.isBlank(map.getString("gfyhzh"))||(map.getString("gfyhzh").length()>20||!map.getString("gfyhzh").matches(str7)))
			{
				return returnMessage("购方银行账号",map.getString("gfyhzh"));
			}

		}
		
			if (StringUtils.isBlank(map.getString("bbh"))|| map.getString("bbh").length() > 20)
			{
				return returnMessage("编码表版本号有误",map.getString("bbh"));
			}
		
			if (StringUtils.isBlank(map.getString("hsslbs"))|| map.getString("hsslbs").length() !=1)
			{
				return returnMessage("含税税率标识有误",map.getString("hsslbs"));
			}
		
		
		
		return null;
	}
	
	/**
	 * 发票主信息校验2
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public PageData zxxCheck2(PageData map,Jsfl js) throws Exception{
		
		if(!js.getJshj().equals(format.format(map.get("jshj"))))
		{
			return returnMessage("价税合计有误",js.getJshj());
		}
		if(!js.getJehj().equals(format.format(map.get("hjje"))))
		{
			return returnMessage("合计金额有误",js.getJehj());
		}
		if(!js.getSehj().equals(format.format(map.get("hjse"))))
		{
			return returnMessage("合计税额有误",js.getSehj());
		}
		if(StringUtils.isNotBlank(map.getString("bz")) && map.getString("bz").length()>184)
		{
			return returnMessage("备注有误",map.getString("bz"));
		}
		if(StringUtils.isNotBlank(map.getString("skr")) && map.getString("skr").length()>16)
		{
			return returnMessage("收款人有误",map.getString("skr"));
		}
		/*if(StringUtils.isNotBlank(map.getString("fhr")) &&map.getString("fhr").length()>16)
		{
			return returnMessage("复核人有误",map.getString("fhr"));
		}
		if(StringUtils.isBlank(map.getString("kpr")) || map.getString("kpr").length()>16)
		{
			return returnMessage("开票人有误",map.getString("kpr"));
		}*/
		
		if(StringUtils.isBlank(map.getString("qdbz")) ||map.getString("qdbz").length()!=1)
		{
			return returnMessage("清单标志有误",map.getString("qdbz"));
		}
		if("1".equals(map.getString("kpbz")) &&(StringUtils.isBlank(map.getString("tzdbh"))||map.getString("tzdbh").length()>20))
		{
			return returnMessage("通知单编号有误",map.getString("tzdbh"));
		}
		if("1".equals(map.getString("kpbz")) &&(StringUtils.isBlank(map.getString("yfpdm"))||map.getString("yfpdm").length()>12))
		{
			return returnMessage("原发票代码有误",map.getString("yfpdm"));
		}
		if("1".equals(map.getString("kpbz")) &&(StringUtils.isBlank(map.getString("yfphm"))||map.getString("yfphm").length()>8))
		{
			return returnMessage("原发票号码有误",map.getString("yfphm"));
		}
		if(!"0000004282000000".equals(map.getString("qmcs")))
		{
			return returnMessage("签名参数有误",map.getString("yfphm"));
		}
		
		

		return null;
	}
	/**
	 * 税控盘信息校验
	 * @param skph 税控盘号
	 * @param skpkl 税控盘口令
	 * @param skpmm 税控盘密码
	 * @return
	 */
	public PageData  skpxxCheck(PageData map){
		//1.税控盘编号
		if(StringUtils.isBlank(map.getString("skpbh")) || map.getString("skpbh").length()!=12 || !map.getString("skpbh").matches(str7)){
			return returnMessage("税控盘编号有误",map.getString("skpbh"));
		}
		
		//2.税控盘口令
		if(StringUtils.isBlank(map.getString("skpkl")) || map.getString("skpkl").length()!=8 || !map.getString("skpkl").matches(str7)){
			return returnMessage("税控盘口令有误",map.getString("skpkl"));
		}
		
		//3.数字证书密码
		if(StringUtils.isBlank(map.getString("skpmm")) || !(map.getString("skpmm").length()>6 && map.getString("skpmm").length()<16) || !map.getString("skpmm").matches(str7)){
			return returnMessage("数字证书密码有误",map.getString("skpmm"));
		}

		return null;
	}
	
	/**
	 * 明细信息校验
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	public PageData mxxxCheck(PageData map,Jsfl js,int x) throws Exception{
		

		if(StringUtils.isBlank(map.getString("fphxz"))||map.getString("fphxz").length()>1)
		{
			return returnMessage("第"+(x+1)+"发票行性质有误",map.getString("fphxz"));
		}
		if(StringUtils.isBlank(map.getString("spmc"))||map.getString("spmc").length()>72)
		{
			return returnMessage("第"+(x+1)+"商品名称有误",map.getString("spmc"));
		}
		if(StringUtils.isNotBlank(map.getString("ggxh"))&&map.getString("ggxh").length()>30)
		{
			return returnMessage("第"+(x+1)+"规格型号有误",map.getString("ggxh"));
		}
		if(StringUtils.isNotBlank(map.getString("dw"))&&map.getString("dw").length()>20)
		{
			return returnMessage("第"+(x+1)+"单位有误",map.getString("dw"));
		}
		if(StringUtils.isBlank(map.getString("spsl"))||map.getString("spsl").length()>18||!map.getString("spsl").matches(str2))
		{
			return returnMessage("第"+(x+1)+"商品数量有误",map.getString("spsl"));
		}
		if(StringUtils.isBlank(map.getString("bhsdj"))||map.getString("bhsdj").length()>18||!map.getString("bhsdj").matches(str3))
		{
			return returnMessage("第"+(x+1)+"单价有误",map.getString("bhsdj"));
		}
		if(StringUtils.isBlank(map.getString("sl"))||!map.getString("sl").matches(str2))
		{
			return returnMessage("第"+(x+1)+"组税率有误",map.getString("sl"));
		}

		if(StringUtils.isBlank(map.getString("hsbz"))||map.getString("hsbz").length()!=1)
		{
			return returnMessage("第"+(x+1)+"含税标志有误",map.getString("hsbz"));
		}
		
		/**
		 * 根据单价、数量、税率计算对应的金额和税额
		 */
		js.addMx(map.getString("bhsdj"), map.getString("spsl"), map.getString("sl"), false);

		if(StringUtils.isBlank(format.format(map.get("bhsspje")))||!js.getMxJe().equals(format.format(map.get("bhsspje"))))
		{
			return returnMessage("第"+(x+1)+"金额有误",format.format(map.get("bhsspje")));
		}
		if(StringUtils.isBlank(format.format(map.get("spse")))||!js.getMxSe().equals(format.format(map.get("spse"))))
		{
			return returnMessage("第"+(x+1)+"税额有误",format.format(map.get("spse")));
		}
		if(StringUtils.isBlank(map.getString("spbm"))||map.getString("spbm").length()>10)
		{
			return returnMessage("第"+(x+1)+"商品编码有误",map.getString("spbm"));
		}


		return null;
	}
	
	
	
	/**
	 * 校验返回信息
	 * @param RETURNCODE
	 * @param RETURNMSG
	 * @return
	 */
	public static PageData returnMessage(String cwmx,String cwvalue){
		PageData model=new PageData();
		model.put("msg", "数据有误");
		model.put("cwmx", cwmx);
		model.put("cwvalue", cwvalue);
		

		return model;
	}
	
	public static void main(String[] args) {
	/*	String s="0.234";
		if(!s.matches(str2)){
			System.out.println("0.234");
		}*/
		String kplx=null;
		String tzdbh="";
		if("1".equals(kplx) &&(StringUtils.isBlank(tzdbh)||tzdbh.length()>20)){
			System.out.println("1111");
		}
	 }

}
