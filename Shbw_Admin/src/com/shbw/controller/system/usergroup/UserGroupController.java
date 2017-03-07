package com.shbw.controller.system.usergroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.baseinfo.ticktedpoint.TicketedPointService;
import com.shbw.service.system.usergroup.UserGroupService;
import com.shbw.util.FromValidate;
import com.shbw.util.PageData;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "userGroup")
public class UserGroupController extends BaseController {
	
	@Resource(name="userGroupService")
	private UserGroupService userGroupService;

	/**
	 * 查询用户组的list界面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listPage")
	public ModelAndView toListPage(Page page) throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=(List<PageData>)userGroupService.listUserGroup(page);
		mv.addObject("list",list);
		mv.addObject("pd",pd);
		mv.setViewName("system/usergroup/userGroup_list");
		return mv;
	}
	
	/**
	 * 跳转到修改、添加、详情界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editUserGroup")
	public ModelAndView editUserGroup() throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=this.getPageData();
		if(pd.getString("type").equals("1")){//修改
			PageData pointPd=userGroupService.findUserGroupById(pd);
			mv.addObject("pd",pointPd);
			mv.setViewName("system/usergroup/userGroup_edit");
		}else if(pd.getString("type").equals("2")){//查看详情
			PageData pointPd=userGroupService.findUserGroupById(pd);
			mv.addObject("pd",pointPd);
			mv.setViewName("system/usergroup/userGroup_detail");
		}else{//新增
			mv.setViewName("system/usergroup/userGroup_edit");
		}
		return mv;
	}

	/**
	 * 保存开票点信息
	 * @return
	 */
	@RequestMapping(value="/saveUserGroupInfo")
	@ResponseBody
	public Object saveUserGroupInfo(){
		PageData pd=this.getPageData();
		JSONObject json=new JSONObject();
		pd.put("username", this.getSessionUser());
		String errorMsg="";
		FromValidate fv=new FromValidate();
    	if(!fv.judgeLength(pd.get("yhzmc").toString(), 64)){
    		errorMsg+="用户组名称信息不能为空或长度不能超过64个字符";
    	}else if(!fv.judgeLength(pd.get("yhzms").toString(), 100)){
    		errorMsg+="用户组描述信息不能为空或长度不能超过100个字符";
    	}
    	if(!StringUtils.isNotBlank(errorMsg)){
    		try {
    			if(!pd.getString("id").equals("")){
    				userGroupService.updateUserGroupById(pd);
    			}else{
    				userGroupService.insertUserGroup(pd);
    			}
    			json.put("msg", "success");
    		} catch (Exception e) {
    			e.printStackTrace();
    			json.put("msg", "fail");
    		}
    	}else{
			json.put("msg", "validateError");
			json.put("errorMsg", errorMsg);
    	}
		return json;
	}
	
	/**
	 * 删除开票点信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteUserGroup")
	@ResponseBody
	public Object deleteUserGroup() throws Exception{
		PageData pd=this.getPageData();
		Map<String,String> map=new HashMap<String,String>();
		String ids = pd.getString("DATA_IDS");
		if (!StringUtils.isEmpty(ids)) {
			userGroupService.deleteUserGroupInfo(ids);
			map.put("msg", "ok");
		}
		return map;
	}
}
