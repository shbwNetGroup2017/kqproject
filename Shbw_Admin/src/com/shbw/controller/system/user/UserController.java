package com.shbw.controller.system.user;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.user.UserService;
import com.shbw.util.FromValidate;
import com.shbw.util.PageData;
import net.sf.json.JSONObject;

/**
 * 类名称：UserController
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 显示分页用户列表
	 */
	@RequestMapping(value = "/listUsers")
	public ModelAndView listtabUsers(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> userList = userService.listAllUser(page);
		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 跳转到修改或添加界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toAddOrUpdatePage")
	public ModelAndView toAddOrUpdatePage() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String flag = pd.get("flag").toString();
		List<PageData> roleList = null;
		List<PageData> userGroupList = null;
		if(flag.equals("1")) {
			// 查询修改用户信息
			roleList = userService.findEditAllRole(pd);
			userGroupList = userService.finEditAllUserGroup(pd);
			PageData pdUser = userService.findUserByAccount(pd);
			mv.addObject("pdUser", pdUser);
			mv.setViewName("system/user/user_edit");
		}else if(flag.equals("0")) {
			roleList = userService.findAllRole();// 用户角色信息
			userGroupList = userService.finAllUserGroup();// 用户组信息
			mv.setViewName("system/user/user_edit");
		}else if(flag.equals("2")) {
			// 查询用户信息
			roleList = userService.findEditAllRole(pd);
			userGroupList = userService.finEditAllUserGroup(pd);
			PageData pdUser = userService.findUserByAccount(pd);
			mv.addObject("pdUser", pdUser);
			mv.setViewName("system/user/user_details");
		}

		mv.addObject("flag", flag);
		mv.addObject("roleList", roleList);
		mv.addObject("userGroupList", userGroupList);
		return mv;
	}
	

	/**
	 * 保存或修改用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "addOrEditUser")
	@ResponseBody
	public Object addOrEditUser() {
		JSONObject json = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("operator", this.getSessionUser());
		FromValidate fv=new FromValidate();//后台校验
		boolean flag=true;
		if(!fv.judgeLength(pd.getString("yhzh"), 30)) {
			flag=false;
		} else if(!fv.judgeLength(pd.getString("yhmc"), 50)) {
			flag=false;
		}
		if(pd.getString("id").equals("")) {//添加情况下的密码信息验证
			if(!fv.judgeLength(pd.getString("yhmm"), 20)) {
				flag=false;
			}
		}else {//修改情况下的密码信息验证
			if(!fv.judgeUnNullLength(pd.getString("yhmm"), 20)) {
				flag=false;
			}
		}
		try {
			if(flag) {
				if (pd.getString("id").equals("")) {// 添加操作
					if (userService.findUserByAccount(pd) == null) {
						userService.insertUser(pd);
						json.put("msg", "success");
					} else {
						json.put("msg", "exit");
					}
				} else {//修改操作
					userService.updateUser(pd);
					json.put("msg", "success");
				}
			} else {
				json.put("msg", "validate");
				json.put("msgInfo", "保存信息有误！");
			}
		} catch (Exception e) {
			json.put("msg", "error");
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 批量删除
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteUser")
	@ResponseBody
	public Object deleteUser() throws Exception {
		JSONObject json = new JSONObject();
		PageData pd = this.getPageData();
		String USER_IDS = pd.getString("USER_IDS");
		if (null != USER_IDS && !"".equals(USER_IDS)) {
			String ArrayUSER_IDS[] = USER_IDS.split(",");
			userService.deleteUser(ArrayUSER_IDS);
			json.put("msg", "success");
		} else {
			json.put("msg", "fail");
		}
		return json;
	}

}
