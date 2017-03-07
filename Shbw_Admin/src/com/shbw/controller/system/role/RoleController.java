package com.shbw.controller.system.role;

import java.util.List;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.role.RoleService;
import com.shbw.util.FromValidate;
import com.shbw.util.PageData;

/**
 * 类名称：RoleController 
 * @version
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {
	
	@Resource(name = "roleService")
	private RoleService roleService;
		
	/**
	 * 显示分页角色列表
	 */
	@RequestMapping(value = "listRole")
	public ModelAndView listtabRole(Page page) throws Exception {

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();		
		page.setPd(pd);
		List<PageData> roleList = roleService.listAllRole(page); //查询角色
		mv.setViewName("system/role/role_list");
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * go添加与修改
	 */
	@RequestMapping(value = "toAddUpdateRole")
	public ModelAndView toAddUpdateRole() throws Exception {

		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		String id=pd.getString("id");//获取角色id
		List<PageData> fzyList=null;
		if(StringUtils.isNotBlank(id)){				
			pd=roleService.listObjRole(pd);	//查询角色信息
			fzyList=roleService.listGxMenu(pd);//查询资源类型
			mv.setViewName("system/role/role_edit");
			mv.addObject("fzyList", fzyList);
			mv.addObject("pd", pd);
		}else{		
			fzyList=roleService.listAllMenu(pd);//查询资源类型
			mv.setViewName("system/role/role_add");
			mv.addObject("fzyList", fzyList);
		}
		
		return mv;
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "insertRole")
	@ResponseBody
	public Object insertRole() throws Exception {
		
		PageData pd=new PageData();
		pd=this.getPageData();		
		JSONObject json=new JSONObject();
		FromValidate da=new FromValidate();//后台校验
		boolean bool1=da.judgeLength(pd.getString("jsmc"), 64);
		boolean bool2=da.judgeLength(pd.getString("jsms"), 100);
		if(bool1==true && bool2==true){
			String username=this.getSessionUser();
			pd.put("creator",username);
			roleService.insertRole(pd);//添加角色
			String zyid=pd.getString("zyid");	
			for(int i=0;i<zyid.split(",").length;i++){	
				pd.put("jsid",pd.get("id"));
				pd.put("zyid",zyid.split(",")[i]);
				pd.put("creator",username);
				roleService.insertResourceGx(pd);//添加资源角色关系
			}				
			json.put("msg", "success");
		}else{
			json.put("msg", "error");
		}
						
		return json;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "updateRole")
	@ResponseBody
	public Object updateRole() throws Exception {
		
		PageData pd=new PageData();
		pd=this.getPageData();
		JSONObject json=new JSONObject();
		FromValidate da=new FromValidate();//后台校验
		boolean bool1=da.judgeLength(pd.getString("jsmc"), 64);
		boolean bool2=da.judgeLength(pd.getString("jsms"), 100);
		if(bool1==true && bool2==true){
			String username=this.getSessionUser();
			pd.put("updator",username);
			roleService.updateRole(pd);//修改角色
			//删除资源角色关系
			roleService.deleteResourceGx(pd);
			
			String zyid=pd.getString("zyid");	
			for(int i=0;i<zyid.split(",").length;i++){	
				pd.put("jsid",pd.get("id"));
				pd.put("zyid",zyid.split(",")[i]);
				pd.put("creator",username);
				pd.put("updator",username);
				roleService.updateResourceGx(pd);//添加资源角色关系
			}		
			json.put("msg", "success");
		}else{
			json.put("msg", "error");
		}
						
		return json;
				
	}
	
	/**
	 * go详情
	 */
	@RequestMapping(value = "toDetailsRole")
	public ModelAndView toDetailsRole() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		String id=pd.getString("id");//获取角色id
		List<PageData> fzyList=null;
		if(StringUtils.isNotBlank(id)){				
			pd=roleService.listObjRole(pd);//查询角色信息
			fzyList=roleService.listGxMenu(pd);//查询资源类型
			mv.setViewName("system/role/role_details");
			mv.addObject("fzyList", fzyList);
			mv.addObject("pd", pd);
		}
		return mv;
    }
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "deleteRole")
	@ResponseBody
	public Object deleteRole() throws Exception {
		
		PageData pd=new PageData();
		pd=this.getPageData();	
		String id=pd.getString("id");//获取角色id
		if(StringUtils.isNotBlank(id)){
			for(int i=0;i<id.split(",").length;i++){	
				pd.put("id", id.split(",")[i]);
				roleService.deleteRole(pd);//删除角色
				pd.put("jsid", id.split(",")[i]);
				roleService.deleteResourceGx(pd);//删除资源角色关系
			}	
			
		}		
		JSONObject json=new JSONObject();
		json.put("msg", "success");
		return json;
	}
	
	/*public static void main(String[] args) {
		List<String> lis=new ArrayList<String>();
		lis.add("你好");
		lis.add("它好");
		lis.add("都好");
		lis.add("可以");
		lis.add("不可以");
		Map map=new HashMap<String, String>();
		map.put("1", "sfdfa");
		map.put("2", "dsfs");
		map.put("3", "jkl");
		lis.add(map.toString());
		
		System.out.println(lis.toString());

	}*/

}
