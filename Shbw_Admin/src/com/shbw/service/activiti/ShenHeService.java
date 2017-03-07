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
import com.shbw.util.PageData;
import com.shbw.util.PropertiesUtil;
@Service("shenHeService")
public class ShenHeService {
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


	//查询票据审核列表
	public List<PageData> selectLeaveBillAll(Page page) throws Exception {
		return (List<PageData>)dao.findForList("shenheMapper.selectAlllistPage", page);
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
	
	// TODO 使用请假单ID 查询任务ID
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
					list.add("通过");
				}
			}
		}
		return list;
	}


	/**获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注*/
	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		//使用当前的任务ID，查询当前流程对应的历史任务ID
		//使用当前任务ID，获取当前任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}
	
	/**使用票据申请ID，查询历史批注信息
	 * @throws Exception */
	public List<Comment> findCommentByLeaveBillId(String id) throws Exception {
		//使用请假单ID，查询请假单对象
		PageData kplsxx = (PageData) dao.findForObject("shenheMapper.findKplsById", id);
		
		String objectName = "sky_cs";
		//组织流程表中的字段中的值
		String objId = objectName+"."+id;

		/**2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID*/
		HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//对应历史的流程变量表
						.variableValueEquals("objId", objId)//使用流程变量的名称和流程变量的值查询
						.singleResult();
		//流程实例ID
		if(hvi!=null){
			String processInstanceId = hvi.getProcessInstanceId();
			List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
			return list;
		}else{
			return null;
		}
		
	}

	/**指定连线的名称完成任务
	 * @throws Exception */
	public void saveSubmitTask(PageData pd) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username = (String) session.getAttribute("USERNAME");//当前用户名
		//获取任务ID
		String taskId = pd.getString("taskid");
		//获取连线的名称
		String outcome = pd.getString("outcome");
		//批注信息
		String message = pd.getString("comment");
		//获取票据申请ID
		String id = pd.getString("id");
		PageData pdfhr = new PageData();
		pdfhr.put("id", id);
		pdfhr.put("fhr", username);
		dao.update("shenheMapper.updateFhr", pdfhr);//修改复合人
		/**
		 * 1：在完成之前，添加一个批注信息，向t_comment表中添加数据，用于记录审核人的一些审核信息
		 */
		PageData pdcomment = new PageData();
		pdcomment.put("kplsId", id);
		pdcomment.put("comment", message);
		pdcomment.put("creator", username);
		pdcomment.put("create_date", new Date());
		dao.update("shenheMapper.insertComment", pdcomment);
		
		//使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//使用任务ID查询
						.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		

		/**
		 * 2：如果连线的名称是“通过”，那么就不需要设置，如果不是，就需要设置流程变量
		 * 在完成任务之前，设置流程变量，按照连线的名称，去完成任务
				 流程变量的名称：outcome
				 流程变量的值：连线的名称
		 */
		
		//使用下拉框当做按钮bigin 
		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("outcome", outcome);
		//用于测试完成任务的时候设置下一个任务的人员 TODO
		List<String> users = new ArrayList<String>();
		String yhzmc = PropertiesUtil.getString("shenpizu");
		List<PageData> yhList = (List<PageData>) dao.findForList("shenheMapper.selectYhByYhz", yhzmc);
		if(yhList!=null){
			for(PageData pdUser:yhList){
				if(pdUser!=null){
					users.add(pdUser.getString("yhzh"));
				}
			}
		}
		if(users==null || users.size()<=0){
			users.add(username);
		}
		String temp = users.toString();
		String userIds = temp.substring(1, temp.length()-1);
		variables.put("yhz", userIds);
	
		
		//使用下拉框当做按钮end 
		
		//3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(taskId, variables);
		
		//4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		// TODO 将个人任务变为组任务 分配给指定的一组人来查询,处理begin
		//taskService.setAssignee(taskId, null);//个人任务转变为组任务
		//taskService.deleteCandidateUser(taskId, userId);//清空用户组
		//taskService.addCandidateUser(taskId, userId);//添加用户组成员
		
		//将个人任务变为组任务 分配给指定的一组人来查询,处理end
		/**
		 * 5：在完成任务之后，判断流程是否结束
   			如果流程结束了，更新请假单表的状态从2变成3（审核中-->审核完成）
		 */
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//使用流程实例ID查询
						.singleResult();
		String lcjd = "";
		if(outcome!=null && outcome.equals("拒绝")){
			lcjd = "0";//审核拒绝，回到草稿
			PageData pdsjzclq = new PageData();
			pdsjzclq.put("id", id);
			pdsjzclq.put("sjzclq", 0);
			dao.update("shenheMapper.updatesjzclq", pdsjzclq);
		}else if(outcome!=null && "退回".equals(outcome)){
			lcjd = "2";//审核退回
			PageData pdsjzclq = new PageData();
			pdsjzclq.put("id", id);
			pdsjzclq.put("sjzclq", 0);
			dao.update("shenheMapper.updatesjzclq", pdsjzclq);
			// TODO 需要设置办理人
			if(pi!=null){
				Task huituiTask= taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
				if(huituiTask!=null){
					taskService.claim(huituiTask.getId(), pd.getString("yhzh"));
				}
			}
			
		}else{
			lcjd = "3";//审核通过
		}
		
		if(pi==null){
			PageData pdlcjd = new PageData();
			pdlcjd.put("id", id);
			pdlcjd.put("lcjd",lcjd);
			dao.update("shenheMapper.updateLcjd", pdlcjd);
		}else{
			if(outcome!=null && !"退回".equals(outcome)){
				//确定是否需要再次领取分配
				PageData pdsjzclq = new PageData();
				pdsjzclq.put("id", id);
				pdsjzclq.put("sjzclq", 1);
				dao.update("shenheMapper.updatesjzclq", pdsjzclq);
			}
		}
		
	}

	/**
	 * 使用任务ID，查找票据申请ID
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
	 * 查询业务类型
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> selectYwlx(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("shenheMapper.selectYwlx", pd);
	}
	/**
	 * 添加批注信息
	 * @param pd
	 * @throws Exception 
	 */
	public void insertComment(PageData pd) throws Exception{
		dao.update("shenheMapper.insertComment", pd);
	}
	/**
	 * 查询批注信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> selectComment(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("shenheMapper.selectComment", pd);
	}
	/**
	 * 根据id查询开票流水信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findKplsById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("shenheMapper.findKplsById", pd);
	}
	/**
	 * 查询开票总金额
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData findKhzje(PageData pd) throws Exception {
		return (PageData) dao.findForObject("shenheMapper.findKhzje", pd);
	}
	public PageData findGfyxtid(String id) throws Exception {
		return (PageData) dao.findForObject("shenheMapper.findGfyxtid", id);
	}
	public List<PageData> selectDdJylshByKplsh(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("LeaveBillMapper.selectDdJylshByKplsh", pd);
	}

	/**
	 * 任务二次领取
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public String updateLingQu(PageData pd) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username = (String) session.getAttribute("USERNAME");//当前用户名
		String ids = pd.getString("ids");
		String msg = "success";
		if(ids!=null){
			String[] idList = ids.split(",");
			for(String id : idList){
				taskService.claim(id, username);
				//修改领取状态为已领取
				Task task = taskService.createTaskQuery().taskId(id).singleResult();
				//2：使用任务对象Task获取流程实例ID
				String processInstanceId = task.getProcessInstanceId();
				//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
				ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
								.processInstanceId(processInstanceId).singleResult();
				//4：使用流程实例对象获取BUSINESS_KEY
				String buniness_key = pi.getBusinessKey();
				//5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
				String kplsid = "";
				if(StringUtils.isNotBlank(buniness_key)){
					//截取字符串，取buniness_key小数点的第2个值
					kplsid = buniness_key.split("\\.")[1];
				}
				PageData pdkpls = new PageData();
				pdkpls.put("id", kplsid);
				pdkpls.put("sjzclq", 0);
				dao.findForObject("shenheMapper.updatesjzclq", pdkpls);
			}
		}else{
			msg = "任务ID为空";
		}
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
	 * 任务二次分配
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public String updateFenPei(PageData pd) throws Exception {
		String ids = pd.getString("fenpeiIds");
		String msg = "success";
		String username = pd.getString("yh");
		if(ids!=null){
			String[] idList = ids.split(",");
			for(String id : idList){
				taskService.claim(id, username);
				//修改领取状态为已领取
				Task task = taskService.createTaskQuery().taskId(id).singleResult();
				//2：使用任务对象Task获取流程实例ID
				String processInstanceId = task.getProcessInstanceId();
				//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
				ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
								.processInstanceId(processInstanceId).singleResult();
				//4：使用流程实例对象获取BUSINESS_KEY
				String buniness_key = pi.getBusinessKey();
				//5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
				String kplsid = "";
				if(StringUtils.isNotBlank(buniness_key)){
					//截取字符串，取buniness_key小数点的第2个值
					kplsid = buniness_key.split("\\.")[1];
				}
				PageData pdkpls = new PageData();
				pdkpls.put("id", kplsid);
				pdkpls.put("sjzclq", 0);
				dao.findForObject("shenheMapper.updatesjzclq", pdkpls);
			}
		}else{
			msg = "任务ID为空";
		}
		return msg;
	}

	/**
	 * 查询开票流水明细信息
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> selectKpmxByKplsh(Page page) throws Exception {
		return (List<PageData>) dao.findForList("shenheMapper.selectKpmxByKplshlistPage", page);
	}
}
