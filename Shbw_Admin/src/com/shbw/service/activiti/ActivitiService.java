package com.shbw.service.activiti;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.shbw.core.Cf;
import com.shbw.core.Jsfl;
import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.entity.activiti.LeaveBill;
import com.shbw.entity.activiti.WorkFlowBean;
import com.shbw.util.PageData;
import com.shbw.util.PropertiesUtil;
import com.shbw.util.UuidUtil;
@Service("activitiService")
public class ActivitiService {
	@Resource(name="repositoryService")
	private RepositoryService repositoryService;
	@Resource(name="runtimeService")
	private RuntimeService runtimeService;
	@Resource(name="taskService")
	private TaskService taskService;
	@Resource(name="historyService")
	private HistoryService historyService;
	@Resource(name="formService")
	private FormService formService;
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 部署流程定义
	 */
	public void insertNewDeploye(InputStream in, String name) throws FileNotFoundException {
			ZipInputStream zip = new ZipInputStream(in);
			repositoryService.createDeployment()//
			.name(name)//添加部署名称
			.addZipInputStream(zip)//
			.deploy();//完成部署
	}

	/**查询部署对象信息，对应表（act_re_deployment）*/
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()//创建部署对象查询
				.orderByDeploymenTime().asc()//
				.list();
		return list;
	}


	/**查询流程定义的信息，对应表（act_re_procdef）*/
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//创建流程定义查询
				.orderByProcessDefinitionVersion().asc()//
				.list();
		return list;
	}


	/**使用部署对象ID和资源图片名称，获取图片的输入流*/
	public InputStream findImageInputStream(String deploymentId, String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}


	/**使用部署对象ID，删除流程定义*/
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}


	//票据申请列表
	public List<PageData> selectLeaveBillAll(Page page) throws Exception {
		return (List<PageData>)dao.findForList("LeaveBillMapper.selectAlllistPage", page);
	}


	/**2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>*/
	public List<Task> findTaskListByName(String name) {
		List<Task> list = taskService.createTaskQuery()//
				//没有指定办理人，查询的是所有任务
				.taskAssignee(name)//指定个人任务查询
				.orderByTaskCreateTime().asc()//
				.list();
	return list;
	}
	/**查询当前人的组任务*/
	public List<Task> findMyGroupTask(String candidateUser){
		List<Task> list = taskService//与正在执行的任务管理相关的Service
						.createTaskQuery()//创建任务查询对象
						/**查询条件（where部分）*/
						.taskCandidateUser(candidateUser)//组任务的办理人查询
						/**排序*/
						.orderByTaskCreateTime().asc()//使用创建时间的升序排列
						/**返回结果集*/
						.list();//返回列表
		return list;
	}
	/**1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象*/
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//查询流程定义的对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef 
					.processDefinitionId(processDefinitionId)//使用流程定义ID查询
					.singleResult();
		return pd;
	}
	/**
	 * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
		 map集合的key：表示坐标x,y,width,height
		 map集合的value：表示坐标对应的值
	 */
	public Map<String, Object> findCoordingByTask(String taskId) {
		//存放坐标
		Map<String, Object> map = new HashMap<String,Object>();
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义的ID
		String processDefinitionId = task.getProcessDefinitionId();
		//获取流程定义的实体对象（对应.bpmn文件中的数据）
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		//流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//创建流程实例查询
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		//获取当前活动的ID
		String activityId = pi.getActivityId();
		//获取当前活动对象
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);//活动ID
		//获取坐标
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		return map;
	}
	/**使用任务ID，获取当前任务节点中对应的Form key中的连接的值*/
	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		//获取Form key的值
		String url = formData.getFormKey();
		return url;
	}
	
	
	// 使用票据申请ID 查询任务ID
	public String findTaskIdByleavebillId(String key){
		Task task = taskService.createTaskQuery()//
				.processInstanceBusinessKey(key)
				.singleResult();
		String taskId = null;
		if(task!=null){
			taskId = task.getId();
		}
		return taskId;
	}
	
	/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
	public List<String> findOutComeListByTaskId(String taskId) {
		//返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		//1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		//获取当前活动的id
		String activityId = pi.getActivityId();
		//4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		//5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvm:pvmList){
				String name = (String) pvm.getProperty("name");
				if(StringUtils.isNotBlank(name)){
					list.add(name);
				}
				else{
					list.add("默认提交");
				}
			}
		}
		return list;
	}

	

	/**
	 * 新增
	 * @throws Exception 
	 */
	public String insert(PageData pd) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username = (String) session.getAttribute("USERNAME");//当前用户名
		String msg = "success";
		String qmcs = PropertiesUtil.getString("qmcs");
		pd.put("qmcs", qmcs);
		pd.put("saveOrsubmit", pd.get("saveOrsubmit"));
		if("1".equals(pd.get("saveOrsubmit").toString())){
			pd.put("sqsj", new Date());
		}
		pd.put("creator", username);
		pd.put("del_flag", 0);
		pd.put("create_date",new Date());
		
		PageData  gf_yxtid = (PageData) dao.findForObject("LeaveBillMapper.findGfyxtid", pd.getString("ygsid"));
		
		String kplsh = UuidUtil.get32UUID();
		pd.put("kplsh", kplsh);
		
		PageData gf = (PageData) dao.findForObject("LeaveBillMapper.findGfyxtid", pd.getString("gfmc"));
		
		//根据发票抬头填写购方信息
		pd.put("gfid", gf.get("id").toString());
		pd.put("gfsh", gf.getString("gfsh"));
		pd.put("gfmc", gf.getString("gfmc"));
		pd.put("gfyh", gf.getString("gfyh"));
		pd.put("gfyhzh", gf.getString("gfyhzh"));
		pd.put("gfdz", gf.getString("gfdz"));
		pd.put("gfdh", gf.getString("gfdh"));
		
		DecimalFormat nt = new DecimalFormat("#.##");
		nt.setRoundingMode(RoundingMode.HALF_UP);
		nt.setMinimumFractionDigits(2);
		
		if("1".endsWith(pd.getString("cplb"))){
			//对客户累计金额表的金额，已开发票金额进行修改
			pd.put("jylsly", pd.getString("sjly"));
			PageData khljje = (PageData) dao.findForObject("LeaveBillMapper.findKhzje", pd);
			String ykpje = pd.getString("ykpje");
			
			String jszje = Cf.sub(khljje.get("zje").toString(),ykpje);
			String jsykpje = Cf.add(khljje.get("ykpje").toString(), ykpje);
			
			khljje.put("zje", nt.format(Double.valueOf(jszje)));
			khljje.put("ykpje", nt.format(Double.valueOf(jsykpje)));
			khljje.put("creator", username);
			khljje.put("creator_date", new Date());
			dao.update("LeaveBillMapper.updateKhljje", khljje);
			
			pd.put("jshj", "-"+pd.getString("ykpje"));//价税合计
			pd.put("jshj", "-"+nt.format(Double.valueOf(pd.getString("ykpje"))));
			pd.put("hsbz", 1);
			pd.put("kpzt", 0);
			pd.put("sjbs", 1);
			pd.put("jylsly", pd.get("sjly"));
			pd.put("gf_yxtid", gf_yxtid.getString("gf_yxtid"));
			
			//开票明细表新增
			pd.put("spmc", "手续费");
			pd.put("spbm", "123");
			pd.put("spje", nt.format(Double.valueOf(pd.getString("ykpje"))));
			pd.put("dj", nt.format(Double.valueOf(pd.getString("ykpje"))));
			pd.put("spsl", 1);
			pd.put("fphxz", 0);
			String sl=PropertiesUtil.getString("sl");
			pd.put("sl", sl);
			PageData pdMaxKpje = (PageData) dao.findForObject("LeaveBillMapper.selectMaxKpje", pd);
			String maxKpje = "0";
			if(pdMaxKpje!=null){
				if("007".equals(pd.get("fplx").toString())){//普票
					maxKpje = pdMaxKpje.get("ppzdje").toString();
				}else{
					maxKpje = pdMaxKpje.get("zpzdje").toString();
				}
			}
			if("0".equals(maxKpje)){
				msg = "最大开票金额为0";
				return msg;
			}
			String[] chaifen = Cf.cfje(nt.format(Double.valueOf(pd.getString("ykpje"))),maxKpje);
			String qdbz = "0";
			if(chaifen!=null && chaifen.length>0){
				for(String jshj:chaifen){
					pd.put("jshj", nt.format(Double.valueOf(jshj)));
					String [] jeAndSe = Cf.cfjs(jshj, sl);
					if(jeAndSe!=null && jeAndSe.length>0){
						pd.put("spje", nt.format(Double.valueOf(jshj)));
						pd.put("spse", nt.format(Double.valueOf(jeAndSe[1])));
						Jsfl js = new Jsfl(6);
						js.addMx(nt.format(Double.valueOf(jshj)), "1", pd.getString("sl"), true);
						pd.put("bhsspje", js.getMxJe());
						pd.put("bhsdj", js.getMxDj());
						pd.put("dj", nt.format(Double.valueOf(jshj)));
						
						pd.put("je", nt.format(Double.valueOf(jeAndSe[0])));
					//	pd.put("se", nt.format(Double.valueOf(jeAndSe[1])));
						pd.put("hjse", nt.format(Double.valueOf(jeAndSe[1])));
						pd.put("hjje", nt.format(Double.valueOf(jeAndSe[0])));
					}
					dao.update("LeaveBillMapper.insertKpmx", pd);//新增开票流水明细
					pd.put("jshj", "-"+nt.format(Double.valueOf(jshj)));
					
					if("terminal_id".equals(pd.getString("zhlx"))){//终端编号
						//终端编号
						pd.put("terminal_id", pd.getString("zhbh"));
						//结算账户
						pd.put("jszh", null);
						//快钱账号
						pd.put("kqzh", null);
						//商户编号
						pd.put("merchant_id", null);
					}else if("jszh".equals(pd.getString("zhlx"))){//结算账户
						//终端编号
						pd.put("terminal_id", null);
						//结算账户
						pd.put("jszh", pd.getString("zhbh"));
						//快钱账号
						pd.put("kqzh", null);
						//商户编号
						pd.put("merchant_id", null);
					}else if("kqzh".equals(pd.getString("zhlx"))){//快钱账号
						//终端编号
						pd.put("terminal_id", null);
						//结算账户
						pd.put("jszh", null);
						//快钱账号
						pd.put("kqzh", pd.getString("zhbh"));
						//商户编号
						pd.put("merchant_id", null);
					}else if("merchant_id".equals(pd.getString("zhlx"))){//商户编号
						//终端编号
						pd.put("terminal_id", null);
						//结算账户
						pd.put("jszh", null);
						//快钱账号
						pd.put("kqzh", null);
						//商户编号
						pd.put("merchant_id", pd.getString("zhbh"));
					}else{//无效类型
						msg = "账号类型有误";
					}
					if("0".equals(pd.getString("jslx"))){//表名
						pd.put("biaoming","t_khyljje_kssj");
					}else if("1".equals(pd.getString("jslx"))){
						pd.put("biaoming","t_khyljje_jssj");
					}else if("2".equals(pd.getString("jslx"))){
						pd.put("biaoming","t_khyljje_jsqsj");
					}else if("3".equals(pd.getString("jslx"))){
						pd.put("biaoming","t_khyljje_jshsj");
					}else{
						msg = "结算类型有误";
					}
					pd.put("je",pd.get("jshj").toString());
					dao.update("LeaveBillMapper.insertT_fddljymx", pd);//新增非订单类交易明细
					pd.put("jshj",nt.format(Double.valueOf(jshj)));
					pd.put("kpbz", 0);
					pd.put("qdbz", qdbz);
					dao.update("LeaveBillMapper.insert", pd);//新增开票流水
				}
			}
			
			if(!"success".equals(msg)){
				return msg;
			}
			return msg;
		}else if("0".endsWith(pd.getString("cplb"))){
			String ddid = pd.getString("dingDanJylshs");
			String qdbz = "0";
			String ids = "";
			for(String id:ddid.split(",")){
				ids = ids+"'"+id+"',";
			}
			if(!"".equals(ids)){
				ids = ids.substring(0, ids.length()-1);
			}else{
				ids = "''";
			}
			pd.put("ids", ids);
			List<PageData> list = (List<PageData>) dao.findForList("LeaveBillMapper.selectDdByJylsh", pd);
			if(list!=null && list.size()>0){
				if(list.size()>8){
					qdbz = "1";
				}
				Jsfl js = new Jsfl(6);
				for(PageData pdDd:list){
					if(pdDd.getString("spmc")==null ||"".equals(pdDd.getString("spmc"))){
						msg ="商品名称为空";
					}
					if(pdDd.getString("spbm")==null ||"".equals(pdDd.getString("spbm"))){
						msg ="商品编码为空";
					}
				
					if(pdDd.getString("dj")==null ||"".equals(pdDd.getString("dj"))){
						msg ="单价为空";
					}
					if(pdDd.getString("spsl")==null ||"".equals(pdDd.getString("spsl"))){
						msg ="商品数量为空";
					}
					pdDd.put("kplsh", kplsh);
					pdDd.put("creator", username);
					pdDd.put("create_date",new Date());
					if(!"success".equals(msg)){
						return msg;
					}
					boolean isHs=true;
					if("0".equals(pdDd.getString("hsbz")))
					{
						isHs=false;
					}
					js.addMx(pdDd.getString("dj"), pdDd.getString("spsl"), pdDd.getString("sl"), isHs);
					pdDd.put("spje",  pdDd.get("spje").toString());
					pdDd.put("spse", pdDd.get("spse").toString());
					pdDd.put("dj",  pdDd.get("dj").toString());
					
					pdDd.put("bhsspje", js.getMxJe());
					pdDd.put("bhsdj", js.getMxDj());
					
					dao.update("LeaveBillMapper.insertKpmx", pdDd);
				}
				PageData  hj =  (PageData) dao.findForObject("LeaveBillMapper.jsJsAndSeByJylsh", pd);
				//查询合计金额，合计税额
				pd.put("jshj", nt.format(Double.valueOf(pd.getString("ykpje"))));
				pd.put("hjse",  js.getSehj());
				pd.put("hjje", js.getJehj());
				//pd.put("hjse", hj.get("hjse"));
				//pd.put("hjje", hj.get("hjje"));
			}else{
				msg ="没有查询到订单明细信息";
			}
			if(!"success".equals(msg)){
				return msg;
			}
			pd.put("kpbz", 0);
			pd.put("qdbz", qdbz);
			dao.update("LeaveBillMapper.insert", pd);
			String dingDanIds = pd.getString("dingDanIds");
			
			String ddids = "";
			for(String id:dingDanIds.split(",")){
				ddids = ddids+"'"+id+"',";
			}
			if(!"".equals(ddids)){
				ddids = ddids.substring(0, ddids.length()-1);
			}else{
				ddids = "''";
			}
			PageData dingdanid = new PageData();
			dingdanid.put("ddids", ddids);
			dingdanid.put("kpzt", 1);
			dao.update("LeaveBillMapper.updateDdzt",dingdanid);//修改订单状态为开票中
			return msg;
		}else{
			msg = "产品类型无效，没有相应处理";
			return msg;
		}
		
		
	}

	public PageData findKpxxById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LeaveBillMapper.findKpxxById", pd);
	}
	/**
	 * 批量删除
	 * @param arrayIds
	 * @throws Exception 
	 */
	public void deleteKpls(String[] arrayIds) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username = (String) session.getAttribute("USERNAME");// 当前用户名
		if(arrayIds!=null){
			PageData pd = new PageData();
			for(String id : arrayIds){
				pd.put("id",id);
				dao.update("LeaveBillMapper.deleteKpls", pd);
				PageData kpls = (PageData) dao.findForObject("LeaveBillMapper.selectKplshById", pd);
				if(kpls!=null){
					pd.put("delkplsh", "'"+kpls.getString("kplsh")+"'");
					dao.update("LeaveBillMapper.updatet_kpmx", pd);//修改开票明细del_flag = 1
				}
				if(!"x".equals(kpls.getString("ygsid"))){
					if("0".equals(kpls.getString("cplb"))){// 订单
					List<PageData> jylshs = (List<PageData>) dao.findForList("LeaveBillMapper.selectDdidAndJylshByKplsh_pl", pd);
					if(jylshs!=null && jylshs.size()>0){////修改订单数据
						String delJylshs="";
						for(PageData p:jylshs){
							if(p!=null){
								delJylshs = delJylshs+"'"+p.getString("jylsh")+"',";
							}
						}
						
						if(!"".equals(delJylshs)){
							delJylshs = delJylshs.substring(0, delJylshs.length()-1);
						}else{
							delJylshs = "''";
						}
						pd.put("delJylshs", delJylshs);
						dao.update("LeaveBillMapper.updatet_jyls", pd);
						}
					}else if("1".equals(kpls.getString("cplb"))){//手续费
						//修改累计金额表中的数据 
						String jshj = kpls.get("jshj").toString();
						kpls.put("jylsly", kpls.getString("sjly"));
						PageData khljje = (PageData) dao.findForObject("LeaveBillMapper.findKhzje", kpls);
						if(khljje!=null){
							DecimalFormat nt = new DecimalFormat("#.##");
							nt.setRoundingMode(RoundingMode.HALF_UP);
							nt.setMinimumFractionDigits(2);
							PageData temp = new PageData();
							String jszje = Cf.add(khljje.get("zje").toString(), jshj);
							String jsykpje =Cf.sub(khljje.get("ykpje").toString(),jshj);
							temp.put("id", khljje.get("id"));
							temp.put("zje", nt.format(Double.valueOf(jszje)));
							temp.put("ykpje", nt.format(Double.valueOf(jsykpje)));
							temp.put("creator", username);
							temp.put("creator_date", new Date());
							dao.update("LeaveBillMapper.updateKhljje", temp);
						}
						
						
						//删除对应表中的负数数据
						if("0".equals(kpls.getString("jslx"))){//交易开始时间
							pd.put("biaoming", "t_khyljje_kssj");
						}else if("1".equals(kpls.getString("jslx"))){//交易结束时间
							pd.put("biaoming", "t_khyljje_jssj");
						}else if("2".equals(kpls.getString("jslx"))){//交易结算前时间
							pd.put("biaoming", "t_khyljje_jsqsj");
						}else if("3".equals(kpls.getString("jslx"))){//交易结算后时间
							pd.put("biaoming", "t_khyljje_jshsj");
						}else{
							throw new RuntimeException("结算类型有误");
						}
						pd.put("kplsh", kpls.getString("kplsh"));
						dao.delete("LeaveBillMapper.deleteFddljymx", pd);
					}else{
						throw new RuntimeException("产品类别有误");
					}
				}
			}
		}
	}

	public String updateKpls(PageData pd) throws Exception {
		DecimalFormat nt = new DecimalFormat("#.##");
		nt.setRoundingMode(RoundingMode.HALF_UP);
		nt.setMinimumFractionDigits(2);
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username = (String) session.getAttribute("USERNAME");//当前用户名
		String msg = "success";
		String maxKpje = "0";
		if("1".equals(pd.get("cplb").toString())){
			PageData pdMaxKpje = (PageData) dao.findForObject("LeaveBillMapper.selectMaxKpje", pd);
			if(pdMaxKpje!=null){
				if("007".equals(pd.get("fplx").toString())){//普票
					maxKpje = pdMaxKpje.get("ppzdje").toString();
				}else{
					maxKpje = pdMaxKpje.get("zpzdje").toString();
				}
			}
			if("0".equals(maxKpje)){
				msg = "最大开票金额为0";
				return msg;
			}
		}
		
		// 删除所有数据，然后新增
		if("1".equals(pd.getString("editCplb"))){//手续费
			if("0".equals(pd.getString("old_jslx"))){//表名
				pd.put("biaoming","t_khyljje_kssj");
			}else if("1".equals(pd.getString("old_jslx"))){
				pd.put("biaoming","t_khyljje_jssj");
			}else if("2".equals(pd.getString("old_jslx"))){
				pd.put("biaoming","t_khyljje_jsqsj");
			}else if("3".equals(pd.getString("old_jslx"))){
				pd.put("biaoming","t_khyljje_jshsj");
			}else{
				msg = "结算类型有误";
			}
			dao.delete("LeaveBillMapper.deleteFddljymx",pd);
			//删除完成后需要更新累计金额t_khljje表中的zje,ykpje
			PageData kplstemp = (PageData) dao.findForObject("LeaveBillMapper.findKpxxById", pd);
			if(kplstemp!=null){
				kplstemp.put("jylsly", kplstemp.get("sjly"));
				PageData khljje = (PageData) dao.findForObject("LeaveBillMapper.findKhzje", kplstemp);
				String ykpje = kplstemp.get("jshj").toString();
				String jszje = Cf.add(khljje.get("zje").toString(), ykpje);
				String jsykpje =Cf.sub(khljje.get("ykpje").toString(),ykpje);
				khljje.put("zje", nt.format(Double.valueOf(jszje)));
				khljje.put("ykpje", nt.format(Double.valueOf(jsykpje)));
				khljje.put("creator", username);
				khljje.put("creator_date", new Date());
				dao.update("LeaveBillMapper.updateKhljje", khljje);
			}
			dao.delete("LeaveBillMapper.deleteWlKpls", pd);
			dao.delete("LeaveBillMapper.deleteKpmx", pd);
			
		}else if("0".equals(pd.getString("editCplb"))){//订单
			//查询订单ID
			List<PageData> jylshAndIds = (List<PageData>) dao.findForList("LeaveBillMapper.selectDdidAndJylshByKplsh", pd);
			String dingDanIds = "";
			if(jylshAndIds!=null && jylshAndIds.size()>0){
				for(PageData pdJylsAndId:jylshAndIds){
					dingDanIds = dingDanIds+pdJylsAndId.get("id")+",";
				}
			}
			if(!"".equals(dingDanIds)){
				dingDanIds = dingDanIds.substring(0, dingDanIds.length()-1);
			}
			else{
				dingDanIds ="''";
			}
			PageData pdTemp =new PageData();
			pdTemp.put("ddids", dingDanIds);
			pdTemp.put("kpzt", 0);
			dao.update("LeaveBillMapper.updateDdzt",pdTemp);//还原订单状态为未开票
			dao.delete("LeaveBillMapper.deleteWlKpls", pd);
			dao.delete("LeaveBillMapper.deleteKpmx", pd);
		}else{
			msg = "产品类型有误";
		}

		//新增
	
		String qmcs = PropertiesUtil.getString("qmcs");
		pd.put("qmcs", qmcs);
		pd.put("saveOrsubmit", pd.get("saveOrsubmit"));
		if("1".equals(pd.get("saveOrsubmit").toString())){
			pd.put("sqsj", new Date());
		}
		pd.put("creator", username);
		pd.put("del_flag", 0);
		pd.put("create_date",new Date());
		PageData  gf_yxtid = (PageData) dao.findForObject("LeaveBillMapper.findGfyxtid", pd.getString("ygsid"));
		
		String kplsh = UuidUtil.get32UUID();
		pd.put("kplsh", kplsh);
		PageData gf = (PageData) dao.findForObject("LeaveBillMapper.findGfyxtid", pd.getString("gfmc"));
		//根据发票抬头填写购方信息
		pd.put("gfid", gf.get("id").toString());
		pd.put("gfsh", gf.getString("gfsh"));
		pd.put("gfmc", gf.getString("gfmc"));
		pd.put("gfyh", gf.getString("gfyh"));
		pd.put("gfyhzh", gf.getString("gfyhzh"));
		pd.put("gfdz", gf.getString("gfdz"));
		pd.put("gfdh", gf.getString("gfdh"));

		if("1".endsWith(pd.getString("cplb"))){
			//对客户累计金额表的金额，已开发票金额进行修改
			pd.put("jylsly", pd.getString("sjly"));
			PageData khljje = (PageData) dao.findForObject("LeaveBillMapper.findKhzje", pd);
			String ykpje = pd.getString("ykpje");
			if(khljje!=null){
				String jszje = Cf.sub(khljje.get("zje").toString(),ykpje);
				String jsykpje = Cf.add(khljje.get("ykpje").toString(), ykpje);
				khljje.put("zje", nt.format(Double.valueOf(jszje)));
				khljje.put("ykpje", nt.format(Double.valueOf(jsykpje)));
				khljje.put("creator", username);
				khljje.put("creator_date", new Date());
				dao.update("LeaveBillMapper.updateKhljje", khljje);
			}else{
				throw new RuntimeException("客户累计金额为空");
			}
			
			pd.put("jshj", "-"+nt.format(Double.valueOf(pd.getString("ykpje"))));
			pd.put("hsbz", 1);
			pd.put("kpzt", 1);
			pd.put("sjbs", 1);
			pd.put("jylsly", pd.get("sjly"));
			pd.put("gf_yxtid", gf_yxtid.getString("gf_yxtid"));
			
			//开票明细表新增
			pd.put("spmc", "手续费");
			pd.put("spbm", "123");
			pd.put("spje", nt.format(Double.valueOf(pd.getString("ykpje"))));
			pd.put("dj", nt.format(Double.valueOf(pd.getString("ykpje"))));
			pd.put("spsl", 1);
			pd.put("fphxz", 0);
			String sl=PropertiesUtil.getString("sl");
			pd.put("sl", sl);
			
			String[] chaifen = Cf.cfje(nt.format(Double.valueOf(pd.getString("ykpje"))),maxKpje);
			String qdbz = "0";
			if(chaifen!=null && chaifen.length>0){
				for(String jshj:chaifen){
					pd.put("jshj", nt.format(Double.valueOf(jshj)));
					String [] jeAndSe = Cf.cfjs(jshj, sl);
					if(jeAndSe!=null && jeAndSe.length>0){
						pd.put("spje", nt.format(Double.valueOf(jshj)));
						pd.put("spse", nt.format(Double.valueOf(jeAndSe[1])));
						Jsfl js = new Jsfl(6);
						js.addMx(nt.format(Double.valueOf(jshj)), "1", pd.getString("sl"), true);
						pd.put("bhsspje", js.getMxJe());
						pd.put("bhsdj", js.getMxDj());
						pd.put("dj", nt.format(Double.valueOf(jshj)));
						
						pd.put("je", nt.format(Double.valueOf(jeAndSe[0])));
					//	pd.put("se", nt.format(Double.valueOf(jeAndSe[1])));
						pd.put("hjse", nt.format(Double.valueOf(jeAndSe[1])));
						pd.put("hjje", nt.format(Double.valueOf(jeAndSe[0])));
					}
					dao.update("LeaveBillMapper.insertKpmx", pd);//新增开票流水明细
					pd.put("jshj", "-"+nt.format(Double.valueOf(jshj)));
					if("terminal_id".equals(pd.getString("zhlx"))){//终端编号
						//终端编号
						pd.put("terminal_id", pd.getString("zhbh"));
						//结算账户
						pd.put("jszh", null);
						//快钱账号
						pd.put("kqzh", null);
						//商户编号
						pd.put("merchant_id", null);
					}else if("jszh".equals(pd.getString("zhlx"))){//结算账户
						//终端编号
						pd.put("terminal_id", null);
						//结算账户
						pd.put("jszh", pd.getString("zhbh"));
						//快钱账号
						pd.put("kqzh", null);
						//商户编号
						pd.put("merchant_id", null);
					}else if("kqzh".equals(pd.getString("zhlx"))){//快钱账号
						//终端编号
						pd.put("terminal_id", null);
						//结算账户
						pd.put("jszh", null);
						//快钱账号
						pd.put("kqzh", pd.getString("zhbh"));
						//商户编号
						pd.put("merchant_id", null);
					}else if("merchant_id".equals(pd.getString("zhlx"))){//商户编号
						//终端编号
						pd.put("terminal_id", null);
						//结算账户
						pd.put("jszh", null);
						//快钱账号
						pd.put("kqzh", null);
						//商户编号
						pd.put("merchant_id", pd.getString("zhbh"));
					}else{//无效类型
						msg = "账号类型有误";
					}
					if("0".equals(pd.getString("jslx"))){//表名
						pd.put("biaoming","t_khyljje_kssj");
					}else if("1".equals(pd.getString("jslx"))){
						pd.put("biaoming","t_khyljje_jssj");
					}else if("2".equals(pd.getString("jslx"))){
						pd.put("biaoming","t_khyljje_jsqsj");
					}else if("3".equals(pd.getString("jslx"))){
						pd.put("biaoming","t_khyljje_jshsj");
					}else{
						msg = "结算类型有误";
					}
					pd.put("je",pd.get("jshj").toString());
					
					dao.update("LeaveBillMapper.insertT_fddljymx", pd);//新增非订单类交易明细
					pd.put("jshj",nt.format(Double.valueOf(jshj)));
					pd.put("kpbz", 0);
					pd.put("qdbz", qdbz);
					dao.update("LeaveBillMapper.insert", pd);//新增开票流水
				}
			}
			if(!"success".equals(msg)){
				return msg;
			}
			return msg;
		}else if("0".endsWith(pd.getString("cplb"))){
			String ddid = pd.getString("dingDanJylshs");
			String qdbz = "0";
			String ids = "";
			for(String id:ddid.split(",")){
				ids = ids+"'"+id+"',";
			}
			if(!"".equals(ids)){
				ids = ids.substring(0, ids.length()-1);
			}else{
				ids = "''";
			}
			pd.put("ids", ids);
			List<PageData> list = (List<PageData>) dao.findForList("LeaveBillMapper.selectDdByJylsh", pd);
			if(list!=null && list.size()>0){
				if(list.size()>8){
					qdbz = "1";
				}
				Jsfl js = new Jsfl(6);
				for(PageData pdDd:list){
					if(pdDd.getString("spmc")==null ||"".equals(pdDd.getString("spmc"))){
						msg ="商品名称为空";
					}
					if(pdDd.getString("spbm")==null ||"".equals(pdDd.getString("spbm"))){
						msg ="商品编码为空";
					}
				
					if(pdDd.getString("dj")==null ||"".equals(pdDd.getString("dj"))){
						msg ="单价为空";
					}
					if(pdDd.getString("spsl")==null ||"".equals(pdDd.getString("spsl"))){
						msg ="商品数量为空";
					}
					pdDd.put("kplsh", kplsh);
					pdDd.put("creator", username);
					pdDd.put("create_date",new Date());
					if(!"success".equals(msg)){
						return msg;
					}
					boolean isHs=true;
					if("0".equals(pdDd.getString("hsbz")))
					{
						isHs=false;
					}
					js.addMx(pdDd.getString("dj"), pdDd.getString("spsl"), pdDd.getString("sl"), isHs);
					pdDd.put("spje",  pdDd.get("spje").toString());
					pdDd.put("spse", pdDd.get("spse").toString());
					pdDd.put("dj",  pdDd.get("dj").toString());
					pdDd.put("bhsspje",  js.getMxJe());
					pdDd.put("bhsspse", js.getMxSe());
					pdDd.put("bhsdj",  js.getMxDj());
					
					dao.update("LeaveBillMapper.insertKpmx", pdDd);
				}
				PageData  hj =  (PageData) dao.findForObject("LeaveBillMapper.jsJsAndSeByJylsh", pd);
				//查询合计金额，合计税额
				pd.put("jshj", nt.format(Double.valueOf(pd.getString("ykpje"))));
				pd.put("hjse",  js.getSehj());
				pd.put("hjje", js.getJehj());
				//pd.put("hjse", hj.get("hjse"));
				//pd.put("hjje", hj.get("hjje"));
			}else{
				msg ="没有查询到订单明细信息";
			}
			if(!"success".equals(msg)){
				return msg;
			}
			pd.put("kpbz", 0);
			pd.put("qdbz", qdbz);
			dao.update("LeaveBillMapper.insert", pd);
			String dingDanIds = pd.getString("dingDanIds");
			
			String ddids = "";
			for(String id:dingDanIds.split(",")){
				ddids = ddids+"'"+id+"',";
			}
			if(!"".equals(ddids)){
				ddids = ddids.substring(0, ddids.length()-1);
			}else{
				ddids = "''";
			}
			PageData dingdanid = new PageData();
			dingdanid.put("ddids", ddids);
			dingdanid.put("kpzt", 1);
			dao.update("LeaveBillMapper.updateDdzt",dingdanid);//修改订单状态为开票中
			return msg;
		}else{
			msg = "产品类型无效，没有相应处理";
			return msg;
		}
		
	}
	/**
	 * 根据Id查询发票抬头
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findGfById(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("LeaveBillMapper.findGfById", pd);
	}
	/**
	 * 查询开票总金额
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData findKhzje(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LeaveBillMapper.findKhzje", pd);
	}

	public PageData findGfyxtid(String id) throws Exception {
		return (PageData) dao.findForObject("LeaveBillMapper.findGfyxtid", id);
	}
	/**
	 * 订单信息根据ID计算开票金额
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData jszjeByDdid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("LeaveBillMapper.jszjeByDdid", pd);
	}
	/**
	 * 根据订单id，修改订单开票状态
	 * @param pd
	 * @throws Exception
	 */
	public void updateDdzt(PageData pd) throws Exception {
		dao.update("LeaveBillMapper.updateDdzt", pd);
	}

	public List<PageData> selectDdJylshByKplsh(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LeaveBillMapper.selectDdJylshByKplsh", pd);
	}
	/**
	 * 根据开票流水号，查询对应的订单ID和JYLSH
	 * @param pdKpls
	 * @return
	 * @throws Exception
	 */
	public List<PageData> selectDdidAndJylshByKplsh(PageData pdKpls) throws Exception {
		return (List<PageData>) dao.findForList("LeaveBillMapper.selectDdidAndJylshByKplsh", pdKpls);
	}
	/**
	 * 领取任务
	 * @return
	 * @throws Exception 
	 */
	public String updateLingqu(PageData pd) throws Exception {
		String msg = "success";
		String key = "sky_cs"; //TODO 流程定义的key
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(key).list();
		if(list!=null && list.size()>0){
			//更新kpls表中lcjd为审核中
			pd.put("lcjd", 2);
			dao.update("LeaveBillMapper.updatet_kplsLcjd", pd);
			
			//启动流程实例 start
			//3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
			
			/**
			 * 4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
			 * inputUser是流程变量的名称，获取的办理人是流程变量的值
			 */
			Map<String, Object> variables = new HashMap<String,Object>();
			
			//TODO 从数据库中查对应工作组中的用户 begin
			List<String> users = new ArrayList<String>();
			users.add(pd.getString("username"));
			String temp = users.toString();
			String userIds = temp.substring(1, temp.length()-1);
			variables.put("yhz", userIds);
			
			//TODO 从数据库中查对应工作组中的用户 end
			/**
			 *(1)使用流程变量设置字符串（格式：key.id的形式），通过设置，让启动的流程（流程实例）关联业务
	   		 *(2)使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段），让启动的流程（流程实例）关联业务
			 */
			//格式：LeaveBill.id的形式（使用流程变量）
			String objId = key+"."+pd.get("id");
			variables.put("objId", objId);
			//6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key,objId,variables);
			Task task =  taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			if(task !=null){
				taskService.claim(task.getId(), pd.getString("username"));//将组任务分配为个任务
			}else{
				msg = "查询任务失败";
			}
		}else{
			msg = "没有对应流程，请先部署流程";
		}
		//启动流程实例结束
		return msg;
	}
	/**
	 * 查询用户组
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> selectYhz(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("LeaveBillMapper.selectYhz", pd);
	}
	/**
	 * 查询用户，根据用户组id
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> selectYhByYhzid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LeaveBillMapper.selectYhByYhzid", pd);
	}
	/**
	 * 查询业务类型
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> selectYwlx(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LeaveBillMapper.selectYwlx", pd);
	}
	/**
	 * 查询客户账号编号
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData selectKhbm(PageData pd) throws Exception{
		return (PageData)dao.findForObject("LeaveBillMapper.selectKhbm", pd);
	}
	/**
	 * 根据任务ID查询开票流水
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public PageData findKplsByTaskId(String taskId) throws Exception {
		//1：使用任务ID，查询任务对象Task
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//使用任务ID查询
						.singleResult();
		//2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//使用流程实例ID查询
						.singleResult();
		//4：使用流程实例对象获取BUSINESS_KEY
		String buniness_key = pi.getBusinessKey();
		//5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		String id = "";
		if(StringUtils.isNotBlank(buniness_key)){
			//截取字符串，取buniness_key小数点的第2个值
			id = buniness_key.split("\\.")[1];
		}
		//查询请假单对象
		PageData kpls =  (PageData) dao.findForObject("shenheMapper.findKplsById", id);
		return kpls;
	}
	/**
	 * 查询订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  List<Map<String,Object>> listOrder(Page page) throws Exception{
		return (List<Map<String, Object>>) dao.findForList("LeaveBillMapper.getOrderMXlistPage", page);
	}
	/**
	 * 详情中查看订单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> searchDdDeatils(Page page) throws Exception {
		return (List<Map<String, Object>>) dao.findForList("LeaveBillMapper.searchDdDeatils", page);
	}
	/**
	 * 新增客户抬头信息
	 * @param pd
	 * @throws Exception
	 */
	public void insertKhTt(PageData pd) throws Exception{
		dao.save("LeaveBillMapper.insertKhTt", pd);
	}
	
	/**
	 * 更新客户抬头信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateKhTt(PageData pd) throws Exception{
		dao.update("LeaveBillMapper.updateKhTt", pd);
	}
	/**
	 * 查询最大开票金额
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData selectMaxKpje(PageData pd) throws Exception{
		return (PageData) dao.findForObject("LeaveBillMapper.selectMaxKpje", pd);
	}
}
