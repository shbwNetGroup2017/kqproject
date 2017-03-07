package com.shbw.controller.activiti;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.activiti.ActivitiService;
import com.shbw.service.baseinfo.customerinfo.CustomerInfoService;
import com.shbw.service.baseinfo.groupmanager.ZzjgService;
import com.shbw.service.dataSum.OrderService;
import com.shbw.util.FileUpload;
import com.shbw.util.PageData;
import net.sf.json.JSONObject;

/**
 * 工作流
 * 
 * @author zhchch
 *
 */
@Controller
@RequestMapping(value = "/leaveBill")
public class ActivitiController extends BaseController {
	@Resource(name = "activitiService")
	private ActivitiService workFlowService;
	@Resource(name = "zzjgService")
	private ZzjgService zzjgService;
	@Resource(name = "customerInfoService")
	private CustomerInfoService customerInfoService;
	@Resource(name = "orderService")
	private OrderService orderService;
	private static final long serialVersionUID = 5392610450770925612L;

	/**
	 * 显示票据申请列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listLeaveBills")
	public ModelAndView listLeaveBills(Page page) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username = (String) session.getAttribute("USERNAME");// 当前用户名

		List<Task> presonTaskList = workFlowService.findMyGroupTask(username);// 组任务

		String ids = "";
		if (presonTaskList != null && presonTaskList.size() > 0) {
			for (Task task : presonTaskList) {
				String taskid = task.getId();
				PageData temp = workFlowService.findKplsByTaskId(taskid);
				ids = ids + "'" + temp.get("id") + "',";
			}
		}
		if (!"".equals(ids)) {
			ids = ids.substring(0, ids.length() - 1);
		} else {
			ids = "''";
		}
		// 查询个人任务,并添加到查询列表中 end

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ids", ids);
		page.setPd(pd);
		DecimalFormat nt = new DecimalFormat("#.##");
		nt.setRoundingMode(RoundingMode.HALF_UP);
		nt.setMinimumFractionDigits(2);
		List<PageData> leaveBillList = workFlowService.selectLeaveBillAll(page);
		List<PageData> leaveBillAndTaskList = new ArrayList<PageData>();
		if (leaveBillList != null && leaveBillList.size() > 0) {
			for (PageData pagedata : leaveBillList) {
				String key = "sky_cs." + pagedata.get("id");
				String taskId = workFlowService.findTaskIdByleavebillId(key);
				pagedata.put("taskid", taskId);
				pagedata.put("jshj", nt.format(Double.valueOf(pagedata.get("jshj").toString())));
				leaveBillAndTaskList.add(pagedata);
			}
		}

		mv.setViewName("activiti/shenqing/leaveBill_list");
		mv.addObject("leaveBillList", leaveBillAndTaskList);
		mv.addObject("pd", pd);
		return mv;
	}

	@RequestMapping(value = "toAddOrUpdatePage")
	public ModelAndView toAddOrUpdatePage() throws Exception {
		DecimalFormat nt = new DecimalFormat("#.##");
		nt.setRoundingMode(RoundingMode.HALF_UP);
		nt.setMinimumFractionDigits(2);
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String flag = pd.get("flag").toString();
		if ("0".equals(flag)) {
			// 新增页面
			List<PageData> xflist = zzjgService.selectXfmc();// 查询开票单位
			mv.addObject("xflist", xflist);
			List<PageData> ywlxList = workFlowService.selectYwlx(pd);
			mv.addObject("ywlxList", ywlxList);
			mv.addObject("flag", flag);
			mv.setViewName("activiti/shenqing/leaveBill_add");
			return mv;
		} else if ("1".equals(flag)) {
			// 修改页面
			List<PageData> xflist = zzjgService.selectXfmc();// 查询开票单位
			mv.addObject("xflist", xflist);
			List<PageData> ywlxList = workFlowService.selectYwlx(pd);
			mv.addObject("ywlxList", ywlxList);
			PageData pdKpls = workFlowService.findKpxxById(pd);
			if (pdKpls != null) {
				pdKpls.put("ykpje", nt.format(Double.valueOf(pdKpls.get("jshj").toString())));// 已开票金额
			} else {
				pdKpls.put("ykpje", "");
			}

			if ("0".equals(pdKpls.getString("cplb"))) {// 订单
				pdKpls.put("zje", nt.format(Double.valueOf(pdKpls.get("jshj").toString())));// 订单类可开票金额等于开票金额

				// 查询订单ID
				List<PageData> jylshAndIds = workFlowService.selectDdidAndJylshByKplsh(pdKpls);
				String dingDanIds = "";
				if (jylshAndIds != null && jylshAndIds.size() > 0) {
					for (PageData pdJylsAndId : jylshAndIds) {
						dingDanIds = dingDanIds + pdJylsAndId.get("id") + ",";
					}
				}
				if (!"".equals(dingDanIds)) {
					dingDanIds = dingDanIds.substring(0, dingDanIds.length() - 1);
				}
				pdKpls.put("dingDanIds", dingDanIds);// 订单ID
				String dingDanJylshs = "";
				if (jylshAndIds != null && jylshAndIds.size() > 0) {
					for (PageData pdJylsAndId : jylshAndIds) {
						dingDanJylshs = dingDanJylshs + pdJylsAndId.getString("jylsh") + ",";
					}
				}
				if (!"".equals(dingDanJylshs)) {
					dingDanJylshs = dingDanJylshs.substring(0, dingDanJylshs.length() - 1);
				}
				pdKpls.put("dingDanJylshs", dingDanJylshs);// 订单流水号

			} else if ("1".equals(pdKpls.getString("cplb"))) {// 手续费
				PageData temp = new PageData();
				temp.put("zhlx", pdKpls.getString("zhlx"));
				temp.put("zhbh", pdKpls.getString("zhbh"));
				temp.put("ygsid", pdKpls.getString("ygsid"));
				temp.put("ywlx", pdKpls.getString("ywlx"));
				temp.put("jslx", pdKpls.get("jslx"));
				temp.put("jylsly", pdKpls.get("sjly"));
				// 进行计算
				PageData pdzje = workFlowService.findKhzje(temp);
				if (pdzje != null) {
					pdKpls.put("zje", nt.format(Double.valueOf(pdzje.get("zje").toString())));
				} else {
					pdKpls.put("zje", 0);
				}
			} else {
				mv.addObject("msg", "产品类型有误");
			}

			String ygsid = pdKpls.getString("ygsid");
			PageData pdgfmc = workFlowService.findGfyxtid(ygsid);// 根据原公司ID查询公司名称
			ygsid = pdKpls.getString("gfid");
			PageData fptt = workFlowService.findGfyxtid(ygsid);// 根据gfid查询发票抬头
			if(pdgfmc!=null){
				pdKpls.put("ygsidmc", pdgfmc.getString("gfmc"));// 公司名称
			}else{
				pdKpls.put("ygsidmc", "");// 公司名称
			}
			
			mv.addObject("fptt", fptt);// 发票抬头
			mv.addObject("pdKpls", pdKpls);
			mv.addObject("flag", flag);
			mv.addObject("ttxxid", pd.get("ttxxid"));
			mv.setViewName("activiti/shenqing/leaveBill_edit");

			return mv;
		} else if ("2".equals(flag)) {
			// 部署页面
			mv.addObject("flag", flag);
			mv.setViewName("activiti/shenqing/upload");
			return mv;
		} else {
			// 一些默认处理
			return mv;
		}

	}

	/**
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save() throws Exception {
		
		String msg = "success";
		JSONObject json = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username = (String) session.getAttribute("USERNAME");//当前用户名
		String ttfjName=pd.getString("ttfjName");
		String ttIds=pd.getString("ttIds");
		String khid=pd.getString("ygsid");
		if(StringUtils.isNotBlank(pd.getString("ttfjName"))){
			JSONObject jsStr = JSONObject.fromObject(ttfjName);
			String filename=jsStr.getString(ttIds);
			pd.put("khid", khid);
			pd.put("ttid", ttIds);
			pd.put("fileName", filename);
			pd.put("creator", username);
			workFlowService.insertKhTt(pd);//新增客户抬头			
		}	
		if(Double.valueOf(pd.getString("ykpje"))>Double.valueOf(pd.getString("zje"))){
			msg = "开票金额大于了该客户的可开票金额";
			json.put("msg", msg);
			return json;
		}
		String xfid = pd.getString("xfid");
		if (!"".equals(xfid) || null != xfid) {
			PageData xf = zzjgService.selectZzjgById(xfid);
			if (xf != null) {
				pd.put("xfsh", xf.get("xfsh"));
				pd.put("xfmc", xf.get("xfmc"));
				pd.put("xfyh", xf.get("xfyh"));
				pd.put("xfyhzh", xf.get("xfyhzh"));
				pd.put("xfdz", xf.get("xfdz"));
				pd.put("xfdh", xf.get("xfdh"));
			}
		}
		pd.put("ttxxid", pd.get("id"));
		msg = workFlowService.insert(pd);
		json.put("msg", msg);
		return json;
	}

	/**
	 * 修改
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "editKpls")
	@ResponseBody
	public Object editKpls() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username = (String) session.getAttribute("USERNAME");//当前用户名
		String ttfjName=pd.getString("ttfjName");
		String ttIds=pd.getString("ttIds");
		String khid=pd.getString("ygsid");
		String ttxxid=pd.getString("ttxxid");
		if(StringUtils.isNotBlank(pd.getString("ttfjName"))){
			JSONObject jsStr = JSONObject.fromObject(ttfjName);
			String filename=jsStr.getString(ttIds);
			pd.put("khid", khid);
			pd.put("ttid", ttIds);
			pd.put("fileName", filename);
			pd.put("creator", username);
			pd.put("ttxxid", ttxxid);
			workFlowService.updateKhTt(pd);//更新客户抬头			
		}	
		String xfid = pd.getString("xfid");
		String msg = "success";
		if (!"".equals(xfid) || null != xfid) {
			PageData xf = zzjgService.selectZzjgById(xfid);
			if (xf != null) {
				pd.put("xfsh", xf.get("xfsh"));
				pd.put("xfmc", xf.get("xfmc"));
				pd.put("xfyh", xf.get("xfyh"));
				pd.put("xfyhzh", xf.get("xfyhzh"));
				pd.put("xfdz", xf.get("xfdz"));
				pd.put("xfdh", xf.get("xfdh"));
			}
		}
		pd.put("gfid", pd.get("ttIds"));
		msg = workFlowService.updateKpls(pd);
		json.put("msg", msg);
		return json;

	}

	/**
	 * 批量删除
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "delKpls")
	@ResponseBody
	public Object delKpls() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		String ids = pd.getString("ids");
		if (null != ids && !"".equals(ids)) {
			String ArrayIds[] = ids.split(",");
			workFlowService.deleteKpls(ArrayIds);
			json.put("msg", "success");
		} else {
			json.put("msg", "fail");
		}
		return json;
	}


	/**
	 * 领取任务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "lingqu")
	@ResponseBody
	public Object lingqu() throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String username = (String) session.getAttribute("USERNAME");// 当前用户名
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("username", username);
		String msg = "success";
		String ids = pd.getString("ids");
		if (ids != null) {
			String[] idList = ids.split(",");
			for (String id : idList) {
				pd.put("id", id);
				msg = workFlowService.updateLingqu(pd);
			}
		}
		JSONObject returnJson = new JSONObject();
		returnJson.put("msg", msg);
		return returnJson;
	}

	/**
	 * 部署流程
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "newdeploy")
	@ResponseBody
	public Object newdeploy(@RequestParam(required = false) MultipartFile file, String filename){
		JSONObject json = new JSONObject();
		String msg = "success";
		try {
			workFlowService.insertNewDeploye(file.getInputStream(), filename);
		} catch (IOException e) {
			msg = "部署失败:"+e.toString();
			e.printStackTrace();
		}
		json.put("msg", msg);
		return json;
	}

	/**
	 * 查询抬头信息
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "searchTt")
	@ResponseBody
	public Object searchTt() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		List<PageData> pdGfList = workFlowService.findGfById(pd);
		json.put("list", pdGfList);
		return json;
	}

	/**
	 * 分页查询客户信息，抬头名称
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "customerTtList")
	public ModelAndView listCustomerTt(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> customerInfoList = new ArrayList<PageData>();
		customerInfoList = customerInfoService.selectListCustomerInfos(page);
		mv.addObject("customerInfoList", customerInfoList);
		mv.addObject("pd", pd);
		mv.addObject("size", customerInfoList.size());
		mv.setViewName("activiti/shenqing/searchGf_list");
		return mv;
	}
	
	/**
	 * 分页查询客户信息，公司名称
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "listCustomerMc")
	public ModelAndView listCustomerMc(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> customerInfoList = new ArrayList<PageData>();
		customerInfoList = customerInfoService.selectListCustomerInfos(page);
		mv.addObject("customerInfoList", customerInfoList);
		mv.addObject("pd", pd);
		mv.setViewName("activiti/shenqing/searchMc_list");
		return mv;
	}


	/**
	 * 查询可开票总金额
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "searchKkpje")
	@ResponseBody
	public Object searchKkpje() throws Exception {
		DecimalFormat nt = new DecimalFormat("#.##");
		nt.setRoundingMode(RoundingMode.HALF_UP);
		nt.setMinimumFractionDigits(2);
		JSONObject json = new JSONObject();
		String msg = "success";
		PageData pd = this.getPageData();
		PageData pdzje = workFlowService.findKhzje(pd);
		if (pdzje != null && pdzje.size() > 0) {
			pdzje.put("zje", nt.format(Double.valueOf(pdzje.get("zje").toString())));
			pdzje.put("ykpje", nt.format(Double.valueOf(pdzje.get("ykpje").toString())));
			json.put("pdzje", pdzje);
		} else {
			msg = "所选公司，对应账号，金额为空";
		}
		json.put("msg", msg);
		return json;
	}
	
	/**
	 * 上传控件
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/customerTtFjInfo")
	@ResponseBody
	public Object redVoidApplyAdd(HttpServletRequest request) throws Exception {
		String ttfjIds = request.getParameter("ttfjIds");
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		JSONObject json = new JSONObject();
		if (ttfjIds != null && !"".equals(ttfjIds)) {
			String ids[] = ttfjIds.split(",");
			String name = "";
			String fileName = "";
			for (int i = 0; i < ids.length; i++) {
				MultipartFile file = req.getFile("ttfj" + ids[i]); // 取得上传文件
				if (file != null) {
					fileName = String.valueOf(System.currentTimeMillis());
					name = FileUpload.fileUp(file, "D:/kqxm/", fileName); // 文件上传开始
					json.put(ids[i], name);
				}
			}

		}
		return json;
	}

	/**
	 * 订单信息查询
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "searchDd")
	public ModelAndView searchDd(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<Map<String, Object>> orderList = workFlowService.listOrder(page);
		mv.setViewName("activiti/shenqing/searchDd_list");
		mv.addObject("orderList", orderList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 根据订单ID查询开票总金额
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "jszjeByDdid")
	@ResponseBody
	public Object jszjeByDdid() throws Exception {
		DecimalFormat nt = new DecimalFormat("#.##");
		nt.setRoundingMode(RoundingMode.HALF_UP);
		nt.setMinimumFractionDigits(2);
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		pd.getString("ddids").split(",");
		String ids = "";
		for (String id : pd.getString("ddids").split(",")) {
			ids = ids + "'" + id + "',";
		}
		if (!"".equals(ids)) {
			ids = ids.substring(0, ids.length() - 1);
		} else {
			ids = "''";
		}
		pd.put("ddids", ids);
		PageData pdzje = workFlowService.jszjeByDdid(pd);
		String fplx = pd.get("fplx").toString();
		//查询最大开票金额
		//String kpzdje = PropertiesUtil.getString("kpzdje");
		PageData pdMaxKpje = workFlowService.selectMaxKpje(pd);
		String kpzdje = "0";
		if(pdMaxKpje!=null){
			if("007".equals(fplx)){//普票
				kpzdje = pdMaxKpje.get("ppzdje").toString();
			}else{
				kpzdje = pdMaxKpje.get("zpzdje").toString();
			}
		}
		
		// 判断金额是否大于最大开票限额
		String jshj = pdzje.get("zje").toString();
		BigDecimal b1 = new BigDecimal(jshj);
		BigDecimal b2 = new BigDecimal(kpzdje);
		String msg = "success";
		if (b1.compareTo(b2) == 1) {
			msg = "总金额不能大于最大开票限额";
		} else {
			pdzje.put("zje", nt.format(Double.valueOf(jshj)));
			json.put("pdzje", pdzje);
			// 修改订单状态为开票中
			// workFlowService.updateDdzt(pd);

		}
		json.put("msg", msg);
		return json;
	}

	/**
	 * 查看详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "lookDetails")
	public ModelAndView lookDetails() throws Exception {
		DecimalFormat nt = new DecimalFormat("#.##");
		nt.setRoundingMode(RoundingMode.HALF_UP);
		nt.setMinimumFractionDigits(2);
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> ywlxList = workFlowService.selectYwlx(pd);
		mv.addObject("ywlxList", ywlxList);
		PageData pdKpls = workFlowService.findKpxxById(pd);
		if (pdKpls != null) {
			String sfShouGong = pdKpls.getString("ygsid");
			if ("x".equalsIgnoreCase(sfShouGong)) {// 手工开票详情
				mv.setViewName("activiti/shenqing/shougongkplsDetails");
				mv.addObject("kpls", pdKpls);
			} else {// 票据申请详情

				if (!pdKpls.getString("ygsid").equals("x")) {
					PageData pdYgsid = workFlowService.findGfyxtid(pdKpls.getString("ygsid"));
					pdKpls.put("ygsidmc", pdYgsid.getString("gfmc"));// 公司名称
				} else {
					pdKpls.put("ygsidmc", "");// 公司名称
				}

				if ("1".equals(pdKpls.getString("cplb"))) {// 手续费
					PageData temp = new PageData();
					temp.put("zhlx", pdKpls.getString("zhlx"));
					temp.put("zhbh", pdKpls.getString("zhbh"));
					temp.put("ygsid", pdKpls.getString("ygsid"));
					temp.put("ywlx", pdKpls.getString("ywlx"));
					temp.put("jslx", pdKpls.getString("jslx"));
					temp.put("jylsly", pdKpls.getString("sjly"));
					PageData pdZje = workFlowService.findKhzje(temp);
					if (pdZje != null) {
						pdKpls.put("zje", nt.format(Double.valueOf(pdZje.get("zje").toString())));// 可开票金额
						pdKpls.put("ykpje", nt.format(Double.valueOf(pdKpls.get("jshj").toString())));// 开票金额
					} else {
						pdKpls.put("zje", 0.00);
						pdKpls.put("zje", 0.00);
					}

				} else {// 订单,开票金额等于可开票金额
					if (pdKpls.get("jshj") != null) {
						pdKpls.put("zje", nt.format(Double.valueOf(pdKpls.get("jshj").toString())));// 可开票金额
						pdKpls.put("ykpje", nt.format(Double.valueOf(pdKpls.get("jshj").toString())));// 开票金额
					} else {
						pdKpls.put("zje", 0.00);// 可开票金额
						pdKpls.put("ykpje", 0.00);// 开票金额
					}

					List<PageData> jylshs = workFlowService.selectDdJylshByKplsh(pdKpls);
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

						pdKpls.put("ddjylsh", jylshlist);
					}
				}

				mv.setViewName("activiti/shenqing/kplsDetails");
				mv.addObject("pdKpls", pdKpls);
			}
		}
		return mv;
	}

	/**
	 * 分配页面
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "toFenPeiPage")
	public ModelAndView toFenPeiPage() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = workFlowService.selectYhz(pd);
		mv.addObject("list", list);
		mv.addObject("pd", pd);
		mv.setViewName("activiti/shenqing/fenpei");
		return mv;
	}

	/**
	 * 根据用户组id查询用户组下的用户
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "selectYhByYhzid")
	@ResponseBody
	public Object selectYhByYhzid() throws Exception {
		JSONObject json = new JSONObject();
		String msg = "success";
		PageData pd = this.getPageData();
		List<PageData> pdYh = workFlowService.selectYhByYhzid(pd);
		json.put("pdYh", pdYh);
		json.put("msg", msg);
		return json;
	}

	/**
	 * 分配任务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "inserFenPei")
	@ResponseBody
	public Object inserFenPei() throws Exception {
		JSONObject json = new JSONObject();
		String msg = "success";
		PageData pd = this.getPageData();
		pd.put("username", pd.getString("yh"));
		String ids = pd.getString("fenpeiIds");
		if (ids != null) {
			String[] idList = ids.split(",");
			for (String id : idList) {
				pd.put("id", id);
				msg = workFlowService.updateLingqu(pd);
			}
		}

		json.put("msg", msg);
		return json;
	}

	/**
	 * 明细信息列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listOrderMX")
	public ModelAndView listOrderMX(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<Map<String, Object>> orderMXList = orderService.listOrderMX(page);
		mv.setViewName("activiti/shenqing/orderMX_list");
		mv.addObject("orderMXList", orderMXList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 查询客户账号编号
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "selectKhbm")
	@ResponseBody
	public Object selectKhbm() throws Exception {
		JSONObject json = new JSONObject();
		String msg = "success";
		PageData pd = this.getPageData();
		PageData pdKhbm = workFlowService.selectKhbm(pd);
		if (pdKhbm != null) {
			json.put("pdKhbm", pdKhbm);
		} else {
			msg = "没有对应账号编码";
		}
		json.put("msg", msg);
		return json;
	}
	/**
	 * 详情中查看订单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "searchDdDeatils")
	public ModelAndView searchDdDeatils(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<Map<String, Object>> orderList = workFlowService.searchDdDeatils(page);
		mv.setViewName("activiti/shenqing/searchDd_list");
		mv.addObject("orderList", orderList);
		mv.addObject("pd", pd);
		return mv;
	} 
	
	public static void main(String[] args) {
	/*	String s="{\"31\":\"1488693145842.doc\"}";
		JSONObject jsStr = JSONObject.fromObject(s);
		String filename=jsStr.get;
		System.out.println(filename);*/
	}
	
}
