package com.shbw.controller.system.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shbw.controller.base.BaseController;
import com.shbw.entity.system.Menu;
import com.shbw.service.menu.MenuService;
import com.shbw.util.AppUtil;
import com.shbw.util.Const;
import com.shbw.util.PageData;
import com.shbw.util.Tools;

/*
 * 系统登录总入口
 */
@Controller
public class LoginController extends BaseController {
	
	@Resource(name = "menuService")
	private MenuService menuService;

	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value = "/login_toLogin")
	public ModelAndView toLogin() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.setViewName("system/admin/login");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value = "/login_login", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object login() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString("KEYDATA").replaceAll("qq123456789cyl", "").replaceAll("QQ987654321cyl", "").split(",cyl,");
		if (null != KEYDATA && KEYDATA.length == 3) {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE); 
			String code = KEYDATA[2];
			if (null == code || "".equals(code)) {
				errInfo = "nullcode";
			} else {
				String USERNAME = KEYDATA[0];
				String PASSWORD = KEYDATA[1];
				if (Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)) {
					Subject subject = SecurityUtils.getSubject();
					UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
					try {
						subject.login(token);
					} catch (AuthenticationException e) {
						errInfo = "usererror"; 
					}
				} else {
					errInfo = "codeerror"; 
				}
				if (Tools.isEmpty(errInfo)) {
					errInfo = "success"; 
				}
			}
		} else {
			errInfo = "error"; 
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 访问系统首页
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/main/index")
	public ModelAndView login_index() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			String USERNAME = (String) session.getAttribute("USERNAME");
			pd.put("USERNAME", USERNAME);
			if (USERNAME != null&&!USERNAME.equals("")) {
				mv.setViewName("system/admin/index");
				List<Menu> allmenuList = new ArrayList<Menu>();
				allmenuList = menuService.listAllMenu(USERNAME);
				session.setAttribute(Const.SESSION_allmenuList, allmenuList); // 菜单权限放入session中
				mv.addObject("menuList", allmenuList);
			}
		} catch (Exception e) {
			mv.setViewName("system/admin/login");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); // 读取系统名称
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 系统退出
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.removeAttribute("USERNAME");
		session.removeAttribute(Const.SESSION_allmenuList);
		
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "system/admin/login";
	}
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value = "/tab")
	public String tab() {
		return "system/admin/tab";
	}
}
