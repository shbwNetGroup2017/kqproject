package com.shbw.controller.activiti;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.activiti.ShenHeService;
import com.shbw.util.PageData;

import net.sf.json.JSONObject;
/**
 * 票据审核
 * @author zhchch
 *
 */
@Controller
@RequestMapping(value = "/shenhe")
public class ShenHeController extends BaseController{
	@Resource(name="shenHeService")
	private ShenHeService shenHeService;
	/**
	 * 显示分页票据审核列表
	 * @return
	 */
	@RequestMapping(value = "/listLeaveBills")
	public ModelAndView listLeaveBills(Page page) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username = (String) session.getAttribute("USERNAME");//当前用户名
		
		//查询个人任务,并添加到查询列表中 begin
		List<Task> groupTaskList = shenHeService.findMyGroupTask(username);//组任务
		List<Task> presonTaskList = shenHeService.findTaskListByName(username);//个人任务
		
		String ids = "";
		if(presonTaskList!=null && presonTaskList.size()>0){//个人任务
			for(Task task :presonTaskList){
				String taskid = task.getId();
				PageData temp = shenHeService.findKplsByTaskId(taskid);
				if(temp!=null){
					ids = ids+"'"+temp.get("id")+"',";
				}
			}
		}
		if(groupTaskList!=null && groupTaskList.size()>0){//组任务
			for(Task task :groupTaskList){
				String taskid = task.getId();
				PageData temp = shenHeService.findKplsByTaskId(taskid);
				if(temp!=null){
					ids = ids+"'"+temp.get("id")+"',";
				}
			}
		}
		if(!"".equals(ids)){
			ids = ids.substring(0, ids.length()-1);
		}else{
			ids = "''";
		}
		//查询个人任务,并添加到查询列表中 end
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ids", ids);
		page.setPd(pd);
		DecimalFormat nt = new DecimalFormat("#.##");
		nt.setRoundingMode(RoundingMode.HALF_UP);
		nt.setMinimumFractionDigits(2);
		List<PageData> leaveBillList = shenHeService.selectLeaveBillAll(page);
		List<PageData> leaveBillAndTaskList = new ArrayList<PageData>();
		if(leaveBillList!=null && leaveBillList.size()>0){
			for(PageData pagedata:leaveBillList){
				String key = "sky_cs."+pagedata.get("id");
				String taskId = shenHeService.findTaskIdByleavebillId(key);
				pagedata.put("taskid", taskId);
				pagedata.put("jshj", nt.format(Double.valueOf(pagedata.get("jshj").toString())));
				leaveBillAndTaskList.add(pagedata);
			}
		}
		mv.setViewName("activiti/shenhe/shenhe_list");
		mv.addObject("leaveBillList", leaveBillAndTaskList);
		mv.addObject("pd", pd);
		return mv;
	}
	// 准备审批是的表单数据
	@RequestMapping(value="toAudit")
	public ModelAndView toAudit() throws Exception{
		DecimalFormat nt = new DecimalFormat("#.##");
		nt.setRoundingMode(RoundingMode.HALF_UP);
		nt.setMinimumFractionDigits(2);
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		List<PageData> ywlxList = shenHeService.selectYwlx(pd);
		mv.addObject("ywlxList",ywlxList);
		//获取任务ID
		String taskId = pd.getString("taskid");
		/**一：使用任务ID，查找票据申请ID，从而获取票据信息*/
		 PageData kpls = shenHeService.findKplsByTaskId(taskId);
		 
		if(kpls!=null){
			String ygsid = kpls.getString("ygsid");
			PageData pdgfmc = shenHeService.findGfyxtid(ygsid);//根据原公司ID查询公司名称
			if(pdgfmc!=null){
				kpls.put("ygsidmc", pdgfmc.getString("gfmc"));//公司名称
			}else{
				kpls.put("ygsidmc","");
			}
			if("1".equals(kpls.getString("cplb"))){//手续费
				PageData temp = new PageData();
				temp.put("zhlx", kpls.getString("zhlx"));
				temp.put("zhbh", kpls.getString("zhbh"));
				temp.put("ygsid", kpls.getString("ygsid"));
				temp.put("ywlx", kpls.getString("ywlx"));
				temp.put("jslx", kpls.getString("jslx"));
				temp.put("jylsly", kpls.getString("sjly"));
				
				//进行计算
				PageData pdzje = shenHeService.findKhzje(temp);
				if(pdzje!=null){
					kpls.put("zje", nt.format(Double.valueOf(pdzje.get("zje").toString())));
				}else{
					kpls.put("zje",0);
				}
			}else if("0".equals(kpls.getString("cplb"))){//订单
				List<PageData> jylshs = shenHeService.selectDdJylshByKplsh(kpls);
				if (jylshs != null && jylshs.size() > 0) {
					String jylshlist = "";
					for (PageData jylsh : jylshs) {
						jylshlist = jylshlist + "'" + jylsh.getString("jylsh") + "',";
					}
					if (!"".equals(jylshlist)) {
						jylshlist = jylshlist.substring(0, jylshlist.length() - 1);
					} else {
						jylshlist = "''";
					}

					kpls.put("ddjylsh", jylshlist);
				}
				kpls.put("zje",  nt.format(Double.valueOf(kpls.get("jshj").toString())));//订单类可开票金额等于开票金额
			}else{
				//产品类别有误
				kpls.put("zje", 0);
			}
			
			if("x".equalsIgnoreCase(ygsid)){//手工开票审批
				mv.setViewName("activiti/shenhe/shougong_shenpi");
			}else{//非手工开票审批
				mv.setViewName("activiti/shenhe/kpls_shenpi");
			}
			
		}
		mv.addObject("kpls",kpls);
		mv.addObject("taskId",taskId);
		/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
		List<String> outcomeList = shenHeService.findOutComeListByTaskId(taskId);//结果格式：[通过, 退回, 拒绝]
		List<PageData> buttonList = new ArrayList<PageData>();
		
		if(outcomeList!=null && outcomeList.size()>0){
			for (String item:outcomeList){
				PageData pdoutcome=new PageData();
				if("拒绝".equals(item)){
					pdoutcome.put("id",item);
					pdoutcome.put("name",item);
				}else if("退回".equals(item)){
					pdoutcome.put("id",item);
					pdoutcome.put("name",item);
				}else{
					pdoutcome.put("id",item);
					pdoutcome.put("name",item);
				}
				buttonList.add(pdoutcome);
			}
		}
		mv.addObject("outcomeList", buttonList);
		/**三：查询所有历史审核人的审核信息，帮助当前人完成审核，返回List<Comment>*/
		//List<Comment> commentList2 = shenHeService.findCommentByTaskId(taskId);
		pd.put("kplsId", pd.get("id"));
		List<PageData> commentList = shenHeService.selectComment(pd);
		mv.addObject("commentList",commentList);
		
		
		return mv;
	}
	/**
	 * 提交任务
	 * @throws Exception 
	 */
	@RequestMapping(value="submitTask")
	public ModelAndView submitTask() throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		shenHeService.saveSubmitTask(pd);
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 查看当前流程图（查看当前活动节点，并使用红色的框标注）
	 */
	@RequestMapping(value="viewCurrentImage")
	public ModelAndView viewCurrentImage(){
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		//任务ID
		String taskId = pd.getString("taskid");
		
		/**一：查看流程图*/
		//1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
		ProcessDefinition processDefinition = shenHeService.findProcessDefinitionByTaskId(taskId);
		//workflowAction_viewImage?deploymentId=<s:property value='#deploymentId'/>&imageName=<s:property value='#imageName'/>
	
		mv.addObject("deploymentId", processDefinition.getDeploymentId());
		mv.addObject("imageName", processDefinition.getDiagramResourceName());
		/**二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
		Map<String, Object> map = shenHeService.findCoordingByTask(taskId);
		mv.addObject("acs", map);
		mv.setViewName("activiti/shenhe/image");
		return mv;
	}
	/**
	 * 查看流程图
	 * @return
	 */
	@RequestMapping(value="selectViewImage")
	public String selectViewImage(HttpServletResponse response){
		PageData pd=new PageData();
		pd=this.getPageData();
		InputStream in = null;
		OutputStream out = null;
		try {
			//1：获取页面传递的部署对象ID和资源图片名称
			//部署对象ID
			String deploymentId = pd.getString("deploymentId");
			//资源图片名称
			String imageName = pd.getString("imageName");
			//2：获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
			in = shenHeService.findImageInputStream(deploymentId,imageName);
			//3：从response对象获取输出流
			out = response.getOutputStream();
			//4：将输入流中的数据读取出来，写到输出流中
			for(int b=-1;(b=in.read())!=-1;){
				out.write(b);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//将图写到页面上，用输出流写
		return null;
	}
	/**
	 * 查看详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="lookDetails")
	public ModelAndView lookDetails() throws Exception{
		DecimalFormat nt = new DecimalFormat("#.##");
		nt.setRoundingMode(RoundingMode.HALF_UP);
		nt.setMinimumFractionDigits(2);
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData(); 
		List<PageData> ywlxList = shenHeService.selectYwlx(pd);
		mv.addObject("ywlxList",ywlxList);
		
		PageData pdKpls = shenHeService.findKplsById(pd);
		if(pdKpls!=null){
			String sfShouGong = pdKpls.getString("ygsid");
			if("x".equalsIgnoreCase(sfShouGong)){//手工开票详情
				mv.setViewName("activiti/shenhe/shenheShouGongDetails");
				mv.addObject("kpls", pdKpls);
			}else{//票据申请详情
				PageData pdYgsid = shenHeService.findGfyxtid(pdKpls.getString("ygsid"));
				if(pdYgsid!=null){
					pdKpls.put("ygsidmc", pdYgsid.getString("gfmc"));//公司名称
				}else{
					pdKpls.put("ygsidmc","");
				}
				
				if("1".equals(pdKpls.getString("cplb"))){//手续费
					PageData temp = new PageData();
					temp.put("zhlx", pdKpls.getString("zhlx"));
					temp.put("zhbh", pdKpls.getString("zhbh"));
					temp.put("ygsid", pdKpls.getString("ygsid"));
					temp.put("ywlx", pdKpls.getString("ywlx"));
					temp.put("jslx", pdKpls.getString("jslx"));
					temp.put("jylsly", pdKpls.getString("sjly"));
					PageData pdZje = shenHeService.findKhzje(temp);
					if(pdZje !=null){
						pdKpls.put("zje",nt.format(Double.valueOf(pdZje.get("zje").toString())));//可开票金额
						pdKpls.put("ykpje", nt.format(Double.valueOf(pdKpls.get("jshj").toString())));//开票金额
					}else{
						pdKpls.put("zje",0.00);
						pdKpls.put("zje",0.00);
					}
					
				}else{//订单,开票金额等于可开票金额
					if(pdKpls.get("jshj")!=null){
						pdKpls.put("zje", nt.format(Double.valueOf(pdKpls.get("jshj").toString())));//可开票金额
						pdKpls.put("ykpje", nt.format(Double.valueOf(pdKpls.get("jshj").toString())));//开票金额
					}else{
						pdKpls.put("zje",0.00);//可开票金额
						pdKpls.put("ykpje", 0.00);//开票金额
					}
					
					List<PageData> jylshs = shenHeService.selectDdJylshByKplsh(pdKpls);
					if(jylshs!=null && jylshs.size()>0){
						String jylshlist = "";
						for(PageData jylsh:jylshs){
							jylshlist = jylshlist+"'"+jylsh.getString("jylsh")+"',";
						}
						if(!"".equals(jylshlist)){
							jylshlist = jylshlist.substring(0, jylshlist.length()-1);
						}else{
							jylshlist = "''";
						}
						
						pdKpls.put("ddjylsh", jylshlist);
					}
				}
				mv.setViewName("activiti/shenhe/shenheDetails");
				mv.addObject("kpls", pdKpls);
			}
		}
		return mv;
	}
	/**
	 * 领取任务
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="updateLingQu")
	@ResponseBody
	public Object updateLingQu() throws Exception{
		String msg = "success";
		PageData pd=new PageData();
		pd=this.getPageData();
		msg = shenHeService.updateLingQu(pd);
		JSONObject returnJson=new JSONObject();
		returnJson.put("msg", msg);
		return returnJson;
	}
	/**
	 * 退回页面
	 * @throws Exception 
	 */
	@RequestMapping(value="toTuihuiPage")
	public ModelAndView toTuihuiPage() throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		List<PageData> list = shenHeService.selectYhz(pd);
		mv.addObject("list",list);
		mv.addObject("pd",pd);
		mv.setViewName("activiti/shenhe/tuihui");
		return mv;
	}
	/**
	 * 分配页面
	 * @throws Exception 
	 */
	@RequestMapping(value="toFenPeiPage")
	public ModelAndView toFenPeiPage() throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		List<PageData> list = shenHeService.selectYhz(pd);
		mv.addObject("list",list);
		mv.addObject("pd",pd);
		mv.setViewName("activiti/shenhe/shenHeFenPei");
		return mv;
	}
	/**
	 * 分配任务
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="updateFenPei")
	@ResponseBody
	public Object updateFenPei() throws Exception{
		JSONObject json = new JSONObject();
		String msg = "success";
		PageData pd = this.getPageData();
		msg = shenHeService.updateFenPei(pd);
		json.put("msg", msg);
		return json;
	}
	/**
	 * 分配页面
	 * @throws Exception 
	 */
	@RequestMapping(value="serarchKpmx")
	public ModelAndView serarchKpmx(Page page) throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list = shenHeService.selectKpmxByKplsh(page);
		mv.addObject("list",list);
		mv.addObject("pd",pd);
		mv.setViewName("activiti/shenhe/serarchKpmx");
		return mv;
	}
}
