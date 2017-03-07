package com.shbw.controller.xxgl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.kjpj.KjpjService;
import com.shbw.util.PageData;

/**
 * 类名称：KjpjController 
 * @version
 */
@Controller
@RequestMapping(value = "/kjpj")
public class KjpjController extends BaseController {
	
	@Resource(name = "kjpjService")
	private KjpjService kjpjService;
		
	/**
	 * 显示分页开票流水列表
	 */
	@RequestMapping(value = "listKjpj")
	public ModelAndView listtabKjpj(Page page) throws Exception {
		//参数(用户id)
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username=session.getAttribute("USERNAME").toString();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();		
		page.setPd(pd);
		List<PageData> kjpjList = kjpjService.listAllKjpj(page); //查询开票流水
		mv.setViewName("xxgl/kjpj/kjpj_list");
		mv.addObject("kjpjList", kjpjList);
		pd.put("kpr", username);
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 预览
	 */
	@RequestMapping(value = "toKjpjPreview")
	public ModelAndView toKjpjPreview(Page page) throws Exception {

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();		
		System.out.println(pd.getString("id"));
		System.out.println(pd.getString("kplsh"));
		List<PageData> kpmxList=kjpjService.listAllKpmx(pd);//查询开票明细	
		pd= kjpjService.listObjKjpj(pd); //预览		
		if(pd.get("jshj")!=null){
			String jshj=Trans2RMB(pd.get("jshj").toString());//数字大写
			pd.put("dxjshj", jshj);
		}
		if(kpmxList.size()>8){		
			mv.addObject("kpmxList", null);
		}else{
			mv.addObject("kpmxList", kpmxList);
		}
		mv.addObject("pd", pd);
		mv.setViewName("xxgl/kjpj/kjpj_preview");
		
		return mv;
	}
	
	
	/**
	 * 详情清单
	 */
	@RequestMapping(value = "toKjpjDetails")
	public ModelAndView toKjpjDetails(Page page) throws Exception {
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();		
		page.setPd(pd);
		List<PageData> kpmxList=kjpjService.listAllKpmx(page);//查询开票明细	
		mv.addObject("kpmxList", kpmxList);
		mv.setViewName("xxgl/kjpj/kjpj_details");	
		mv.addObject("pd", pd);
		return mv;
		
	}
	

	/**
	 * 开票接口
	 */
	@RequestMapping(value = "toBlInterface")
	public ModelAndView toBlInterface (Page page) throws Exception {
			
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();	
		/*String len=pd.getString("len");//开票明细数量
		String fplx=pd.getString("fplx");//发票类型
*/		String kplsh=pd.getString("kplsh");//开票流水号

	/*	String kpbz=pd.getString("kpbz");//开票标志(开票类型) (0：正常开票，1：红冲，2：作废，3：重打。)
		
		//开票主信息查询
		pd=kjpjService.listObjKpls(pd);
		//开票明细信息查询(多条)
		List<Map<Object,Object>> listkpmx=kjpjService.listObjKpmx(pd);
		
		//效验开票参数
		KpCheckUtils check=new KpCheckUtils();
		check.zxxcheck(pd);//主信息效验
		check.fxxcheck(listkpmx);//明细信息效验
		
		mv.addObject("pd", pd);*/
		return mv;
		
	}
	
	//数字大写转换
	public static String Trans2RMB(String money) {  
		        int index = money.indexOf(".");  
		        if (index < 0) {// 没有角分  
		            money = money + ".00";  
		            index = money.indexOf(".");  
		        }  
		        if (money.substring(index, money.length()).length() < 3) {// 没有分  
		            money = money + "0";  
		            index = money.indexOf(".");  
		        }  
		        money = money.replaceAll("\\D", "");// 去除"."  
		        int length = money.length();  
		       // 货币大写形式  
		        String bigLetter[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };  
		        // 货币单位  
		        String unit[] = { "仟", "佰", "拾", "万", "仟", "佰", "拾", "亿", "仟", "佰",  
		                "拾", "万", "仟", "佰", "拾", "元", "角", "分" };  
		        StringBuffer buf = new StringBuffer("");  
		        for (int i = 0; i < length; i++) {  
		            int num = Integer.parseInt(String.valueOf(money.charAt(i)));  
		            buf.append(bigLetter[num]);  
		            buf.append(unit[unit.length - money.length() + i]);  
		        }  
		        String moneyTmp = buf.toString();  
		        buf = null;  
		        for (int i = 0; i < 4; i++) {  
		            // 亿 万 元是四个为单元的最后一位例如1234(亿)4567(万)7891(元)  
		            moneyTmp = moneyTmp.replaceAll("零亿", "亿");  
		            moneyTmp = moneyTmp.replaceAll("零万", "万");  
		            moneyTmp = moneyTmp.replaceAll("零仟", "零");  
		            moneyTmp = moneyTmp.replaceAll("零佰", "零");  
		            moneyTmp = moneyTmp.replaceAll("零拾", "零");  
		            moneyTmp = moneyTmp.replaceAll("零元", "元");  
		            moneyTmp = moneyTmp.replaceAll("零角", "零");  
		            moneyTmp = moneyTmp.replaceAll("零分", "整");  
		        }  
		        moneyTmp = moneyTmp.replaceAll("亿万", "亿");  
		        moneyTmp = moneyTmp.replaceAll("拾元", "拾元零");  
		        moneyTmp = moneyTmp.replaceAll("[零]{1,}", "零");// 多个连续的零替换为一个零  
		        moneyTmp = moneyTmp.replaceAll("零整", "整");  
		        return moneyTmp;  
	}  

	
	/*public static void main(String[] args) throws Exception {
		URL dataUrl = new URL("http://localhost:8080/Shbw_Admin/KjpjServlet");
        HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String source;
        StringBuffer sb=new StringBuffer();
        while((source=br.readLine())!=null) {
        	sb.append(source+"\n");
        }
        System.out.println(sb);
		
		 HttpClient httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true)); 
	        InputStream is = null;
	        PostMethod method=null;
	        method = new PostMethod("http://localhost:8080/Shbw_Admin/KjpjServlet");
            httpClient.executeMethod(method);
           //读取响应
           is = method.getResponseBodyAsStream();
           BufferedReader br = new BufferedReader(new InputStreamReader(is));
           String source;
           StringBuffer sb=new StringBuffer();
           while((source=br.readLine())!=null) {
           	sb.append(source+"\n");
           }
           System.out.println(sb);
 
	}*/
	
	/*public static void main(String[] args) {
		BigDecimal bd = new BigDecimal("1.5982906E7"); 
		String str = bd.toPlainString();
		System.out.println(str);
		String ids="<body><output></output><skpbh>499000134418</skpbh></body>";
		15982906.00
		Document document3 = Jsoup.parse(ids);			
		Elements content3 = document3.select("skpbh");
		System.out.println(content3.get(0).text());
		String[] s=ids.split("#");
		String st="e,y,u,i";
		for(int i=0;i<4;i++){
			String str=ids.split("#")[i].split(",")[0];
			System.out.println(str);
			
			for(int j=0;j<str.split(",").length;j++){
				//参数(开票流水表id)
				String id=str.split(",")[0];
				//参数(开票明细表数据长度)
				String len=str.split(",")[1];
				//参数(发票类型)
				String fplx=str.split(",")[2];
				//参数(开票流水号)
				String kplsh=str.split(",")[3];
				//参数(开票标志)
				String kpbz=str.split(",")[4];
				//参数(清单标志)
				String qdbz=str.split(",")[5];
				System.out.println(id+" "+len+" "+fplx+" "+kplsh+" "+kpbz+" "+qdbz);
			}
		}
		System.out.println(st.split(",").length);
		
	}*/
	
	public static void main(String[] args)
	{
		Double a = 5324326453654.8989809;
		System.out.println(String.valueOf(a));
		System.out.println(a + "");
		
		DecimalFormat format = new DecimalFormat("0.00");
		String sMoney = format.format(a);
		System.out.println(sMoney);
		/*JSONObject json=new JSONObject();
		json.put("zx", "pp");
		
		System.out.println(json.get("xml"));*/
		
	}



}
