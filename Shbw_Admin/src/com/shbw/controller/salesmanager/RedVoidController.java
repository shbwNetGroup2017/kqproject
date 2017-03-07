package com.shbw.controller.salesmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.salesmanager.RedVoidService;
import com.shbw.util.Const;
import com.shbw.util.FileUpload;
import com.shbw.util.FileUtil;
import com.shbw.util.FromValidate;
import com.shbw.util.PageData;

/**
 * @author Aiwen create at 2017-01-16
 *
 */
@Controller
@RequestMapping(value = "server/redVoid")
public class RedVoidController extends BaseController {
	@Resource(name = "redVoidService")
	private RedVoidService redVoidService;

	/**
	 * 红冲作废重打显示
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/redVoidShow")
	public ModelAndView redVoidApplyShow(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 获取用户名
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String userName = (String) session.getAttribute(Const.SESSION_USERNAME);
		pd.put("kpr", userName);
		//
		// 红冲作废申请标志
		String kpbz = pd.getString("kpbz");
		String url = "";
		page.setPd(pd);
		List<PageData> list = new ArrayList<PageData>();
		list = redVoidService.selectListRedVoidApply(page);
		mv.addObject("list", list);
		mv.addObject("pd", pd);
		if ("1".equals(kpbz) || "4".equals(kpbz)) {// 红冲作废重打申请显示页面
			url = "redvoid/redVoidApply_list";
		} else if ("2".equals(kpbz)) {// 红冲作废重打审核页面
			url = "redvoid/redVoidAudit_list";
		} else if ("3".equals(kpbz)) {// 红冲作废重打开具页面
			url = "redvoid/redVoidPrint_list";
		}
		mv.setViewName(url);
		return mv;
	}

	/**
	 * 红冲作废重打显示
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/redVoidApply")
	public ModelAndView redVoidApply() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 查询出要申请的开票流水详细信息
		PageData kpls = new PageData();
		kpls = redVoidService.selectRedVoidApplyById(pd);
		mv.addObject("pd", kpls);
		mv.setViewName("redvoid/redVoid_apply");
		return mv;
	}

	@RequestMapping(value = "/redVoidApplyAdd")
	@ResponseBody
	public Object redVoidApplyAdd(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 文件上传开始
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		MultipartFile file = req.getFile("tp"); // 取得上传文件
		String id = request.getParameter("id");
		String kpbz = request.getParameter("kpbz");
		String sqyy = request.getParameter("sqyy");
		String tzdbh = request.getParameter("tzdbh");
		if(tzdbh==null||"".equals(tzdbh)){
			tzdbh="170120";//默认值作废
		}
		// 参数校验
		String checkResult = redVoidDataJudge(id, kpbz, sqyy, file, tzdbh);
		// 文件上传

		if (checkResult == null || "".equals(checkResult)) {
			String name = "";
			String fileName = String.valueOf(System.currentTimeMillis());
			name = FileUpload.fileUp(file, "D:/kqxm/", fileName);
			redVoidService.insertRedVoid(id, kpbz, sqyy, name, tzdbh);
			result.put("msg", "success");
		} else {
			result.put("msg", "fail");
			result.put("checkResult", checkResult);
		}
		return result;
	}

	/**
	 * 红冲作废重打审核显示
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/redVoidAuditShow")
	public ModelAndView redVoidAuditShow() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//// 查询出要申请的开票流水详细信息
		String id = pd.getString("id");
		if (pd.getString("id") == null) {
			return null;
		}
		PageData kpls = new PageData();
		kpls = redVoidService.selectRedVoidApplyById(id);
		mv.addObject("pd", kpls);
		mv.setViewName("redvoid/redVoid_audit");
		return mv;
	}

	/**
	 * 红冲作废重打图片查看
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "redVoidTpShow")
	@ResponseBody
	public void redVoidTpShow(HttpServletResponse response) {
		PageData pd = new PageData();
		pd = this.getPageData();
		String url = pd.getString("picurl");
		String path = "D:/kqxm/" + url;
		FileUtil.lookPicture(path, response);
	}

	/**
	 * 红冲作废重打图片查看
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "redVoidAudit")
	@ResponseBody
	public Object redVoidAudit() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> result = new HashMap<String, Object>();
		String id = pd.getString("id");
		String lcjd = pd.getString("lcjd");
		if (id == null || lcjd == null) {
			result.put("msg", "fail");
			result.put("checkResult", "审核失败,请检查数据!");
		} else {
			redVoidService.updateRedVoidByAudit(pd);
			result.put("msg", "success");
		}
		return result;
	}

	private String redVoidDataJudge(String id, String kpbz, String sqyy, MultipartFile file, String tzdbh) {
		String msg = null;
		FromValidate fv = new FromValidate();// 后台校验
		if (!fv.judgeLength(id, 32)) {
			msg = "申请单有误,请检查!";
			return msg;
		} else if (!fv.judgeLength(kpbz, 1)) {
			msg = "申请失败,请选择操作类型!";
			return msg;
		} else if (!fv.judgeLength(sqyy, 100)) {
			msg = "申请失败,请检查申请原因!";
			return msg;
		} else if (file == null) {
			msg = "申请失败,请上传附件!";
			return msg;
		} else if ("1".equals(kpbz) && !fv.judgeLength(tzdbh, 32)) {
			msg = "申请失败,发票红冲时通知单编号必须填写!";
			return msg;
		}
		return msg;
	}
}
