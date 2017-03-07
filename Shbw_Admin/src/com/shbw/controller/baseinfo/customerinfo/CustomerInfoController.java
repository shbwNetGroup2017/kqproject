package com.shbw.controller.baseinfo.customerinfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.shbw.service.baseinfo.customerinfo.CustomerInfoService;
import com.shbw.util.Const;
import com.shbw.util.FileUpload;
import com.shbw.util.PageData;

import net.sf.json.JSONObject;

/**
 * @author Aiwen
 *
 */
@Controller
@RequestMapping(value = "server/customer")
public class CustomerInfoController extends BaseController {
	@Resource(name = "customerInfoService")
	CustomerInfoService customerInfoService;

	/**
	 * 分页查询客户信息
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "customerList")
	public ModelAndView listCustomerInfos(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> customerInfoList = new ArrayList<PageData>();
		customerInfoList = customerInfoService.selectListCustomerInfos(page);
		mv.addObject("customerInfoList", customerInfoList);
		mv.addObject("size", customerInfoList.size());
		mv.addObject("pd", pd);
		mv.setViewName("baseinfo/customerInfo/customerInfo_list");
		return mv;
	}

	/**
	 * 分页查询客户信息
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "lookCustomerTtInfo")
	public ModelAndView lookCustomerTtInfo(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> customerTtInfoList = new ArrayList<PageData>();
		customerTtInfoList = customerInfoService.selectListCustomerTtInfos(page);
		mv.addObject("customerTtInfoList", customerTtInfoList);
		mv.setViewName("baseinfo/customerInfo/customerTt_list");
		return mv;
	}

	/**
	 * 打开编辑customerInfo窗口
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "editCustomerInfo")
	public ModelAndView editCustomerInfo() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String type = pd.getString("type");
		String id = pd.getString("id");
		PageData customerInfo = new PageData();
		if ("0".equals(type)) {
			String size = pd.getString("size");
			mv.addObject("size", size);
			mv.setViewName("baseinfo/customerInfo/customerInfo_add");
		} else if ("1".equals(type)) {
			customerInfo = customerInfoService.selectCustomerInfoById(id);
			mv.setViewName("baseinfo/customerInfo/customerInfo_edit");
		} else if ("2".equals(type)) {
			customerInfo = customerInfoService.selectCustomerInfoById(id);
			mv.setViewName("baseinfo/customerInfo/customerInfo_detail");
		}
		mv.addObject("customerInfo", customerInfo);
		return mv;
	}

	@RequestMapping(value = "/saveCustomerInfo")
	@ResponseBody
	public Object saveCustomerInfo() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id = pd.getString("id");
		// 获取用户名
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String userName = (String) session.getAttribute(Const.SESSION_USERNAME);
		//
		String gflx = pd.getString("gflx");
		if (gflx != null && "0".equals(gflx)) {
			pd.put("gflx", "企业");
		}
		if (gflx != null && "1".equals(gflx)) {
			pd.put("gflx", "个人");
		}
		pd.put("zt", "0");// 客户信息默认状态
		if (StringUtils.isEmpty(id)) {
			String gfYxtid = this.get32UUID();
			pd.put("gf_yxtid", gfYxtid);
			pd.put("creator", userName);
			pd.put("del_flag", "0");
			// 保存抬头信息
			logBefore(logger, "新增一条客户信息记录");
			customerInfoService.saveCustomerInfo(pd);
			json.put("msg", "success");

		} else {
			pd.put("updator", userName);
			pd.put("creator", userName);
			pd.put("ykpje", pd.get("ykpje") == null || "".equals(pd.getString("ykpje")) ? "0" : pd.get("ykpje"));
			logBefore(logger, "修改客户信息机构");
			customerInfoService.updateCustomerInfo(pd);
			json.put("msg", "success");
		}
		return json;
	}

	@RequestMapping(value = "/deleteCustomerInfo")
	@ResponseBody
	public Object deleteCustomerInfo() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ids = pd.getString("DATA_IDS");
		if (!StringUtils.isEmpty(ids)) {
			// List<String> list = Arrays.asList(ids);
			// 删除客户之前把抬头附件名称查询出来
			List<String> ttFj = new ArrayList<String>();
			ttFj = customerInfoService.selectCustomerTtInfosList(ids);
			logBefore(logger, "开始删除客户信息!");
			customerInfoService.deleteCustomerInfo(ids);
			// 开始删除附件
			if (ttFj != null && ttFj.size() > 0) {
				for (String name : ttFj) {
					String fileName = "D:/kqxm/";
					fileName += name;
					File f = new File(fileName);
					if (f.exists()) {
						f.delete();
					}
				}
			}
			map.put("msg", "ok");

		}
		return map;
	}

	/**
	 * 分页查询客户抬头信息
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
		mv.setViewName("baseinfo/customerInfo/customerInfoTt_list");
		return mv;
	}

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

	@RequestMapping(value = "lookTtfj")
	@ResponseBody
	public void lookKpsc(HttpServletResponse res) {
		PageData pd = new PageData();
		pd = this.getPageData();
		String url = pd.getString("url");
		String path = "D:/kqxm/" + url;
		File f = null;
		InputStream is = null;
		byte buffer[] = new byte[1024];
		int len = 0;
		try {
			f = new File(path);
			if (f.exists()) {
				is = new FileInputStream(f);
				res.setContentType("text/html;charset=utf-8");
				res.setHeader("content-disposition", "attachment;filename=" + url);
				OutputStream out = res.getOutputStream();
				while ((len = is.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.flush();
				out.close();
				is.close();
			}
		} catch (FileNotFoundException e) {
			//
			e.printStackTrace();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}

	/**
	 * 删除抬头附件
	 * 
	 * @param page
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteCustomerTtFj")
	public void deleteCustomerTtFj() throws Exception {

		PageData pd = new PageData();
		pd = this.getPageData();
		String ttfjNames = pd.getString("ttfjName");
		if (ttfjNames != null && !"".equals(ttfjNames)) {
			JSONObject json = JSONObject.fromObject(ttfjNames);
			@SuppressWarnings("rawtypes")
			Iterator it = json.values().iterator();
			while (it.hasNext()) {
				String fileName = "D:/kqxm/";
				String name = (String) it.next();
				fileName += name;
				File f = new File(fileName);
				if (f.exists()) {
					f.delete();
				}

			}
		}
	}

}
