package com.shbw.core;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.shbw.util.PageData;
import com.shbw.util.PropertiesUtil;

/**
 * 
 * 拼接清单与非清单xml
 * len 开票明细的数量
 * pd  开票清单参数
 * fplx  发票类型  （004:专票007:普票,026:电子发票）
 * kpbz  开票标志（0：正常开票，1：红冲，2：作废，3：重打）
 * qdbz  清单标志（0 无清单 1 有清单）
 * @author Administrator
 */
public class PjXml {
	
	public String pjxml(String len,PageData pd,List<PageData> list,String fplx,String kpbz,String qdbz){
		
		String err="";	
		
		//科学计数法转数字
		DecimalFormat format = new DecimalFormat("0.00");
					
		//获取编码表版本号
		String bmbbh=PropertiesUtil.getString("bmbbh");
		
		//判断 通知单编号（专票红冲必填,否则为空）
		String pdtzdbh="";
		String pdkplx="";
		if(StringUtils.isNotBlank(fplx) && StringUtils.isNotBlank(kpbz)){
			if(fplx.equals("004") && kpbz.equals("1")){
				System.out.println(pd.getString("tzdbh"));
				pdtzdbh="<tzdbh>"+pd.getString("tzdbh")+"</tzdbh>";
				pdkplx="<kplx>1</kplx>";
			}else{
				pdtzdbh="<tzdbh></tzdbh>";
				pdkplx="<kplx>0</kplx>";
			}
		}else{
			err="error";
		}
		

		//判断 清单、非清单
		if(StringUtils.isNotBlank(qdbz) && !err.equals("error")){
			  if(qdbz.equals("1")){//开具清单发票xml
				  String qdmx="";
				  int i=1;
				  for(PageData pa:list){
					  qdmx=qdmx+"<group xh='"+i+"'>"+
									"<fphxz>"+pa.getString("fphxz")+"</fphxz>"+
									"<spmc>"+pa.getString("spmc")+"</spmc>"+
									"<spsm>"+pa.getString("spsm")+"</spsm>"+
									"<ggxh>"+pa.getString("ggxh")+"</ggxh>"+
									"<dw>"+pa.getString("dw")+"</dw>"+
									"<spsl>"+pa.getString("spsl")+"</spsl>"+
									"<dj>"+pa.getString("bhsdj")+"</dj>"+
									"<je>"+format.format(pa.get("bhsspje"))+"</je>"+
									"<sl>"+pa.getString("sl")+"</sl>"+
									"<se>"+format.format(pa.get("spse"))+"</se>"+
									"<hsbz>0</hsbz>"+
									"<spbm>3040801000000000000</spbm>"+
									"<zxbm></zxbm>"+
									"<yhzcbs>"+pa.getString("yhzcbs")+"</yhzcbs>"+
									"<slbs>"+pa.getString("slbs")+"</slbs>"+
									"<zzstsgl>"+pa.getString("zzstsgl")+"</zzstsgl>"+
								"</group>";
					  i++;
				  }
				  String qxml="<?xml version='1.0' encoding='gbk'?>"+
								"<business comment='发票开具' id='FPKJ'>"+
								"<body yylxdm='1'>"+
								"<input>"+
										"<skpbh>"+pd.getString("skph")+"</skpbh>"+
										"<skpkl>"+pd.getString("skpkl")+"</skpkl>"+
										"<keypwd>"+pd.getString("skpmm")+"</keypwd>"+
										"<fplxdm>"+pd.getString("fplx")+"</fplxdm>"+
										pdkplx+
										"<tspz>"+pd.getString("tspz")+"</tspz>"+
										"<xhdwsbh>"+pd.getString("xfsh")+"</xhdwsbh>"+
										"<xhdwmc>"+pd.getString("xfmc")+"</xhdwmc>"+
										"<xhdwdzdh>"+pd.getString("xfdz")+pd.getString("xfdh")+"</xhdwdzdh>"+
										"<xhdwyhzh>"+pd.getString("xfyh")+pd.getString("xfyhzh")+"</xhdwyhzh>"+
										"<ghdwsbh>"+pd.getString("gfsh")+"</ghdwsbh>"+
										"<ghdwmc>"+pd.getString("gfmc")+"</ghdwmc>"+
										"<ghdwdzdh>"+pd.getString("gfdz")+pd.getString("gfdh")+"</ghdwdzdh>"+
										"<ghdwyhzh>"+pd.getString("gfyh")+pd.getString("gfyhzh")+"</ghdwyhzh>"+
										"<bmbbbh>"+bmbbh+"</bmbbbh>"+
										"<hsslbs>"+pd.getString("hsslbs")+"</hsslbs>"+
										"<qdxm count='"+len+"'>"+
												qdmx+
										"</qdxm>"+
										"<zhsl>"+pd.getString("zhsl")+"</zhsl>"+
										"<hjje>"+format.format(pd.get("hjje"))+"</hjje>"+
										"<hjse>"+format.format(pd.get("hjse"))+"</hjse>"+
										"<jshj>"+format.format(pd.get("jshj"))+"</jshj>"+
										"<bz>"+pd.getString("bz")+"</bz>"+
										"<skr>"+pd.getString("skr")+"</skr>"+
										"<fhr>"+pd.getString("fhr")+"</fhr>"+
										"<kpr>"+pd.getString("kpr")+"</kpr>"+
										"<jmbbh>"+pd.getString("jmbbh")+"</jmbbh>"+
										"<zyspmc>"+pd.getString("zyspmc")+"</zyspmc>"+
										"<spsm>"+pd.getString("spsm")+"</spsm>"+
										"<qdbz>"+pd.getString("qdbz")+"</qdbz>"+
										"<ssyf>"+pd.getString("ssyf")+"</ssyf>"+
										"<kpjh>"+pd.getString("kpjh")+"</kpjh>"+
										pdtzdbh+
										"<yfpdm>"+pd.getString("yfpdm")+"</yfpdm>"+
										"<yfphm>"+pd.getString("yfphm")+"</yfphm>"+
										"<qmcs>"+pd.getString("qmcs")+"</qmcs>"+
								"</input>"+
								"</body>"+
								"</business>";
					err=qxml;
			}else{//开具非清单发票xml
				String fqxml="";
				  if("1".equals(kpbz)){//发票红冲xml拼接
					  //红冲xml begin					  
					  String fqdmx="";
						 int i=1;
						 for(PageData pa:list){						 
							  fqdmx=fqdmx+"<group xh='"+i+"'>"+
											"<fphxz>"+pa.getString("fphxz")+"</fphxz>"+
											"<spmc>"+pa.getString("spmc")+"</spmc>"+
											"<spsm>"+pa.getString("spsm")+"</spsm>"+
											"<ggxh>"+pa.getString("ggxh")+"</ggxh>"+
											"<dw>"+pa.getString("dw")+"</dw>"+
											"<spsl>"+pa.getString("spsl")+"</spsl>"+
											"<dj>"+pa.getString("bhsdj")+"</dj>"+
											"<je>"+format.format(pa.get("bhsspje"))+"</je>"+
											"<sl>"+pa.getString("sl")+"</sl>"+
											"<se>"+format.format(pa.get("spse"))+"</se>"+
											"<hsbz>0</hsbz>"+
											"<spbm>3040801000000000000</spbm>"+
											"<zxbm></zxbm>"+
											"<yhzcbs>"+pa.getString("yhzcbs")+"</yhzcbs>"+
											"<slbs>"+pa.getString("slbs")+"</slbs>"+
											"<zzstsgl>"+pa.getString("zzstsgl")+"</zzstsgl>"+
										  "</group>";
							  i++;
						}
						 fqxml="<?xml version='1.0' encoding='gbk'?>"+
									"<business comment='发票开具' id='FPKJ'>"+
									"<body yylxdm='1'>"+
									"<input>"+
										"<skpbh>"+pd.getString("skph")+"</skpbh>"+
										"<skpkl>"+pd.getString("skpkl")+"</skpkl>"+
										"<keypwd>"+pd.getString("skpmm")+"</keypwd>"+
										"<fplxdm>"+pd.getString("fplx")+"</fplxdm>"+
										pdkplx+
										"<tspz>"+pd.getString("tspz")+"</tspz>"+
										"<xhdwsbh>"+pd.getString("xfsh")+"</xhdwsbh>"+
										"<xhdwmc>"+pd.getString("xfmc")+"</xhdwmc>"+
										"<xhdwdzdh>"+pd.getString("xfdz")+pd.getString("xfdh")+"</xhdwdzdh>"+
										"<xhdwyhzh>"+pd.getString("xfyhzh")+"</xhdwyhzh>"+
										"<ghdwsbh>"+pd.getString("gfsh")+"</ghdwsbh>"+
										"<ghdwmc>"+pd.getString("gfmc")+"</ghdwmc>"+
										"<ghdwdzdh>"+pd.getString("gfdz")+pd.getString("gfdh")+"</ghdwdzdh>"+
										"<ghdwyhzh>"+pd.getString("gfyh")+pd.getString("gfyhzh")+"</ghdwyhzh>"+
										"<bmbbbh>"+bmbbh+"</bmbbbh>"+
										"<hsslbs>"+pd.getString("hsslbs")+"</hsslbs>"+
										"<fyxm count='"+len+"'>"+
												fqdmx+
										"</fyxm>"+
										"<zhsl>"+pd.getString("zhsl")+"</zhsl>"+
										"<hjje>"+format.format(pd.get("hjje"))+"</hjje>"+
										"<hjse>"+format.format(pd.get("hjse"))+"</hjse>"+
										"<jshj>"+format.format(pd.get("jshj"))+"</jshj>"+
										"<bz>"+pd.getString("bz")+"</bz>"+
										"<skr>"+pd.getString("skr")+"</skr>"+
										"<fhr>"+pd.getString("fhr")+"</fhr>"+
										"<kpr>"+pd.getString("kpr")+"</kpr>"+
										"<jmbbh>"+pd.getString("jmbbh")+"</jmbbh>"+
										"<zyspmc>"+pd.getString("zyspmc")+"</zyspmc>"+
										"<spsm>"+pd.getString("spsm")+"</spsm>"+
										"<qdbz>"+pd.getString("qdbz")+"</qdbz>"+
										"<ssyf>"+pd.getString("ssyf")+"</ssyf>"+
										"<kpjh>"+pd.getString("kpjh")+"</kpjh>"+
										pdtzdbh+
										"<yfpdm>"+pd.getString("yfpdm")+"</yfpdm>"+
										"<yfphm>"+pd.getString("yfphm")+"</yfphm>"+
										"<qmcs>"+pd.getString("qmcs")+"</qmcs>"+
									"</input>"+
									"</body>"+
									"</business>";
					  //红冲xml End
					  
				  }else if("2".equals(kpbz)){ //发票作废拼接
					 fqxml= "<?xml version='1.0' encoding='gbk'?>"+
					  "<business comment='发票作废' id='FPZF'>"+
					  "<body yylxdm='1'>"+
					  "<input>"+
					  "<nsrsbh>"+pd.getString("xfsh")+"</nsrsbh>"+
					  "<skpbh>"+pd.getString("skph")+"</skpbh>"+
					  "<skpkl>"+pd.getString("skpkl")+"</skpkl>"+
					  "<keypwd>"+pd.getString("skpmm")+"</keypwd>"+
					  "<fplxdm>"+pd.getString("fplx")+"</fplxdm>"+
					  "<zflx>1</zflx>"+//作废类型 0：空白发票作废 1：已开发票作废
					  "<yfpdm>"+pd.getString("yfpdm")+"</yfpdm>"+
					  "<yfphm>"+pd.getString("yfphm")+"</yfphm>"+					  
					  "<hjje>"+format.format(pd.get("hjje"))+"</hjje>"+
					  "<zfr>"+pd.getString("zfr")+"</zfr>"+
					  "<qmcs>"+pd.getString("qmcs")+"</qmcs>"+
					  "</input>"+
					  "</body>"+
					  "</business>";
				  }else if("3".equals(kpbz)){
					  
				  }else{					  
					 String fqdmx="";
					 int i=1;
					 for(PageData pa:list){						 
						  fqdmx=fqdmx+"<group xh='"+i+"'>"+
										"<fphxz>"+pa.getString("fphxz")+"</fphxz>"+
										"<spmc>"+pa.getString("spmc")+"</spmc>"+
										"<spsm>"+pa.getString("spsm")+"</spsm>"+
										"<ggxh>"+pa.getString("ggxh")+"</ggxh>"+
										"<dw>"+pa.getString("dw")+"</dw>"+
										"<spsl>"+pa.getString("spsl")+"</spsl>"+
										"<dj>"+pa.getString("bhsdj")+"</dj>"+
										"<je>"+format.format(pa.get("bhsspje"))+"</je>"+
										"<sl>"+pa.getString("sl")+"</sl>"+
										"<se>"+format.format(pa.get("spse"))+"</se>"+
										"<hsbz>0</hsbz>"+
										"<spbm>3040801000000000000</spbm>"+
										"<zxbm></zxbm>"+
										"<yhzcbs>"+pa.getString("yhzcbs")+"</yhzcbs>"+
										"<slbs>"+pa.getString("slbs")+"</slbs>"+
										"<zzstsgl>"+pa.getString("zzstsgl")+"</zzstsgl>"+
									  "</group>";
						  i++;
					}
					 fqxml="<?xml version='1.0' encoding='gbk'?>"+
								"<business comment='发票开具' id='FPKJ'>"+
								"<body yylxdm='1'>"+
								"<input>"+
									"<skpbh>"+pd.getString("skph")+"</skpbh>"+
									"<skpkl>"+pd.getString("skpkl")+"</skpkl>"+
									"<keypwd>"+pd.getString("skpmm")+"</keypwd>"+
									"<fplxdm>"+pd.getString("fplx")+"</fplxdm>"+
									pdkplx+
									"<tspz>"+pd.getString("tspz")+"</tspz>"+
									"<xhdwsbh>"+pd.getString("xfsh")+"</xhdwsbh>"+
									"<xhdwmc>"+pd.getString("xfmc")+"</xhdwmc>"+
									"<xhdwdzdh>"+pd.getString("xfdz")+pd.getString("xfdh")+"</xhdwdzdh>"+
									"<xhdwyhzh>"+pd.getString("xfyhzh")+"</xhdwyhzh>"+
									"<ghdwsbh>"+pd.getString("gfsh")+"</ghdwsbh>"+
									"<ghdwmc>"+pd.getString("gfmc")+"</ghdwmc>"+
									"<ghdwdzdh>"+pd.getString("gfdz")+pd.getString("gfdh")+"</ghdwdzdh>"+
									"<ghdwyhzh>"+pd.getString("gfyh")+pd.getString("gfyhzh")+"</ghdwyhzh>"+
									"<bmbbbh>"+bmbbh+"</bmbbbh>"+
									"<hsslbs>"+pd.getString("hsslbs")+"</hsslbs>"+
									"<fyxm count='"+len+"'>"+
											fqdmx+
									"</fyxm>"+
									"<zhsl>"+pd.getString("zhsl")+"</zhsl>"+
									"<hjje>"+format.format(pd.get("hjje"))+"</hjje>"+
									"<hjse>"+format.format(pd.get("hjse"))+"</hjse>"+
									"<jshj>"+format.format(pd.get("jshj"))+"</jshj>"+
									"<bz>"+pd.getString("bz")+"</bz>"+
									"<skr>"+pd.getString("skr")+"</skr>"+
									"<fhr>"+pd.getString("fhr")+"</fhr>"+
									"<kpr>"+pd.getString("kpr")+"</kpr>"+
									"<jmbbh>"+pd.getString("jmbbh")+"</jmbbh>"+
									"<zyspmc>"+pd.getString("zyspmc")+"</zyspmc>"+
									"<spsm>"+pd.getString("spsm")+"</spsm>"+
									"<qdbz>"+pd.getString("qdbz")+"</qdbz>"+
									"<ssyf>"+pd.getString("ssyf")+"</ssyf>"+
									"<kpjh>"+pd.getString("kpjh")+"</kpjh>"+
									pdtzdbh+
									"<yfpdm>"+pd.getString("yfpdm")+"</yfpdm>"+
									"<yfphm>"+pd.getString("yfphm")+"</yfphm>"+
									"<qmcs>"+pd.getString("qmcs")+"</qmcs>"+
								"</input>"+
								"</body>"+
								"</business>";
					}
					err=fqxml;
			}
		}else{
			err=null;		
		}
				
		return err;		
	}

}
