package com.shbw.controller.xxgl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.shbw.core.KpCheckUtils;
import com.shbw.core.PjXml;
import com.shbw.entity.Page;
import com.shbw.service.kjpj.KjpjService;
import com.shbw.util.Logger;
import com.shbw.util.PageData;
import com.shbw.util.PropertiesUtil;

public class KjpjServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Constructor of the object.
	 */
	public KjpjServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());//获取spring容器
		KjpjService kjpjService = (KjpjService)ctx.getBean("kjpjService"); //获得service接口
		
		//参数(方法名称)
		String FFNAME=request.getParameter("FFNAME");
		//参数(开票流水号)
		String kplsh=request.getParameter("kplsh");
		//参数(开票人)
		String kpr=request.getParameter("kpr");
		//参数(税控盘编号)
		String skpbh=request.getParameter("skpbh");
		//参数(开票流水表id)
		String id=request.getParameter("id");	
		//参数(税控盘编号)
		String ppzdje=request.getParameter("PPZDJE");
		//参数(开票流水表id)
		String zpzdje=request.getParameter("ZPZDJE");	
				
		
		//设置Page对象
		Page page=new Page();
		PageData pd=new PageData();	
		
		JSONObject json=new JSONObject();
		response.setCharacterEncoding("utf-8");	
		if(FFNAME.equals("editKpLs")){//开票成功后反写
			//参数(发票代码)
			String fpdm=request.getParameter("FPDM");
			//参数(发票号码)
			String fphm=request.getParameter("FPHM");
			//设置更新条件		
			pd.put("id",id);
			pd.put("fpdm",fpdm);
			pd.put("fphm",fphm);		
			page.setPd(pd);
			
			try {
				kjpjService.updateCgfxKpls(pd);//反写成功开票流水信息
				json.put("FLATE", true);
				response.getWriter().print(json.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(FFNAME.equals("getKpXml")){//开票
			//获取编码表版本号
			String bmbbh=PropertiesUtil.getString("bmbbh");

			//设置查询条件				
			pd.put("id",id);
			pd.put("kpr",kpr);
			pd.put("kplsh",kplsh);	
			pd.put("skph",skpbh);	
			page.setPd(pd);
			
			try {			
				kjpjService.updateKpls(pd);//更新开票人
				List<PageData> listkpmx=kjpjService.listObjKpmx(pd);//获取开票明细信息
				pd=kjpjService.listObjKpls(pd);//获取开票流水信息
				if(listkpmx.size()>0 && pd!=null){
					pd.put("bbh",bmbbh);//编码表版本号
					//效验开票参数
					PageData pa=new PageData();			
					pa.putAll(pd);
					pa.put("mx", listkpmx);			
					KpCheckUtils unit=new KpCheckUtils();
					pa=unit.kpCheck(pa);								
					if(pa!=null){
						System.out.println(pa.getString("msg"));
						System.out.println(pa.getString("cwmx"));
						System.out.println(pa.getString("cwvalue"));					
						json.put("FLATE", false);
						json.put("error", pa.getString("cwmx")+"!");
						logger.info("校检数据============start");
						logger.error("校检"+pa.getString("msg")+":"+pa.getString("cwmx"));
						logger.info("校检数据============end");
					}else{
						PjXml xml=new PjXml();
						String pjxml= xml.pjxml(String.valueOf(listkpmx.size()), pd, listkpmx, pd.getString("fplx"), pd.getString("kpbz"), pd.getString("qdbz"));					
						if(StringUtils.isNotBlank(pjxml)){
							json.put("FLATE", true);
							json.put("xml", pjxml.replaceAll("null", "").trim());
							logger.info("获取开票报文============start");
							logger.error("获取开票报文成功！");
							logger.info("获取开票报文============end");					
						}else{
							json.put("FLATE", false);
							json.put("error", "");
							logger.info("获取开票报文============start");
							logger.error("获取开票报文出现异常...");
							logger.info("获取开票报文============end");
						}										
					}					
				}else{
					//设置更新条件	
					PageData pa=new PageData();	
					pa.put("id",id);	
					page.setPd(pa);
					json.put("FLATE", false);
					json.put("error", "开票数据有误！");
					logger.info("查询开票流水和开票明细============start");
					logger.error("查询开票流水和开票明细出现了空指针异常...");
					logger.info("查询开票流水和开票明细============end");
					try {
						kjpjService.updateSbfxKpls(pa);//反写失败开票流水信息
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				response.getWriter().print(json.toString());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(FFNAME.equals("failKpLs")){//开票失败反写
			//设置更新条件	
			pd.put("id",id);	
			page.setPd(pd);
			json.put("FLATE", true);
			response.getWriter().print(json.toString());
			try {
				kjpjService.updateSbfxKpls(pd);//反写失败的开票流水信息
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(FFNAME.equals("editCd")){
			//设置更新条件	
			pd.put("id",id);	
			page.setPd(pd);
			json.put("FLATE", true);
			response.getWriter().print(json.toString());
			try {
				kjpjService.updateSbfxKpls(pd);//反写失败开票流水信息
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(FFNAME.equals("upkpxe")){
			//设置更新条件	
			pd.put("skph",request.getParameter("SKPBH"));
			pd.put("ppzdje",ppzdje);
			pd.put("zpzdje",zpzdje);
			page.setPd(pd);
			json.put("FLATE", true);
			response.getWriter().print(json.toString());
			try {
				kjpjService.updateXfxx(pd);//启动客户端时写入专普票的最大开票金额
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
		
	}

}
