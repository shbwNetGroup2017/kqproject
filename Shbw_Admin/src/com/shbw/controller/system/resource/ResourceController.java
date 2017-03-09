
package com.shbw.controller.system.resource;

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
import com.shbw.service.resource.ResourceService;
import com.shbw.util.FromValidate;
import com.shbw.util.PageData;

/**
 * 类名称：ResourceController 
 * @version
 */
@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends BaseController {
	
	@Resource(name = "resourceService")
	private ResourceService resourceService;
		
	/**
	 * 显示分页资源列表
	 */
	@RequestMapping(value = "listResource")
	public ModelAndView listtabResource(Page page) throws Exception {

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();		
		page.setPd(pd);
		List<PageData> resourceList = resourceService.listAllResource(page); //查询资源
		
		PageData pa = new PageData();		
		pa.put("zyxh", "__");
		List<PageData> fzyList = resourceService.listAllMenu(pa);//查询上级资源类型
		
		mv.addObject("fzyList", fzyList);
		mv.setViewName("system/resource/resource_list");
		mv.addObject("resourceList", resourceList);
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * go添加与修改
	 */
	@RequestMapping(value = "toAddUpdateResource")
	public ModelAndView toAddUpdateResource() throws Exception {

		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		String id=pd.getString("id");//获取资源id
		String zyxh=pd.getString("zyxh");//获取资源序号
		List<PageData> fzyList=null;
		if(StringUtils.isNotBlank(id)){	
			if(zyxh.length()>2){
				pd.put("zyxh", "__");
				fzyList = resourceService.listAllMenu(pd);//查询上级资源类型	
			}
			pd=resourceService.listObjResource(pd);//查询资源信息			
			mv.setViewName("system/resource/resource_edit");
			mv.addObject("fzyList", fzyList);
			mv.addObject("pd", pd);
		}else{		
			pd.put("zyxh", "__");
			fzyList = resourceService.listAllMenu(pd);//查询上级资源类型
			mv.setViewName("system/resource/resource_add");
			mv.addObject("fzyList", fzyList);
		}
				
		return mv;
	}
	
	/**
	 * go详情
	 */
	@RequestMapping(value = "toDetailsResource")
	public ModelAndView toDetailsResource() throws Exception {
		
		ModelAndView mv = this.getModelAndView();
		PageData pd=new PageData();
		pd=this.getPageData();
		String id=pd.getString("id");//获取资源id
		String zyxh=pd.getString("zyxh");//获取资源序号
		List<PageData> fzyList=null;
		if(StringUtils.isNotBlank(id)){	
			if(zyxh.length()>2){
				pd.put("zyxh", "__");
				fzyList = resourceService.listAllMenu(pd);//查询上级资源类型			
			}
			pd=resourceService.listObjResource(pd);//查询资源信息					
			mv.setViewName("system/resource/resource_details");
			mv.addObject("fzyList", fzyList);
			mv.addObject("pd", pd);
		}
				
		return mv;
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "updateResource")
	@ResponseBody
	public Object updateResource() throws Exception {
		
		PageData pd=new PageData();
		pd=this.getPageData();
		JSONObject json=new JSONObject();
		FromValidate da=new FromValidate();//后台校验
		String zymc=pd.getString("zymc");
		String zyms=pd.getString("zyms");
		String zylj=pd.getString("zylj");
		String zt=pd.getString("zt");
		String id=pd.getString("id");
		boolean bool1=da.judgeLength(zymc, 64);		
		boolean bool4=da.judgeLength(zyms, 100);		
		if(StringUtils.isNotBlank(zylj)){
			boolean bool2=da.judgeLength(zylj, 100);
			if(bool1==true && bool2==true && bool4==true){
				String jzyxh=pd.getString("jzyxh");
				String zyxh=pd.getString("zyxh");
				if(jzyxh.equals(zyxh)){
					pd.put("updator",this.getSessionUser());
					resourceService.updateResource(pd);//修改资源
					json.put("msg", "success");
				}else{					
						pd.put("zyxh", zyxh.subSequence(0, 2)+"%");
						pd=resourceService.listRsZyxh(pd);//查询出最大的资源序号
						System.out.println(pd.getString("zyxh"));
						if(pd.getString("zyxh").substring(0, 1).equals("0")){
							pd.put("zyxh", "0"+(Integer.valueOf(pd.getString("zyxh"))+1));
						}else{
							pd.put("zyxh", Integer.valueOf(pd.getString("zyxh"))+1);
						}
						pd.put("updator",this.getSessionUser());
						pd.put("zymc", zymc);
						pd.put("zylj", zylj);
						pd.put("zyms", zyms);
						pd.put("zt", zt);
						pd.put("id", id);
						resourceService.updateResource(pd);//修改资源
						json.put("msg", "success");
				}				
			}else{
				json.put("msg", "error");
			}
		}else{
			if(bool1==true && bool4==true){
				pd.put("updator",this.getSessionUser());
				resourceService.updateResource(pd);//修改资源
				json.put("msg", "success");
			}else{
				json.put("msg", "error");
			}
		}
				
		return json;

	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "insertResource")
	@ResponseBody
	public Object insertResource() throws Exception {
		
		PageData pd=new PageData();
		pd=this.getPageData();
		JSONObject json=new JSONObject();
		FromValidate da=new FromValidate();//后台校验				
		boolean bool1=da.judgeLength(pd.getString("zymc"), 64);		
		boolean bool3=da.judgeLength(pd.getString("zyxh"), 10);
		boolean bool4=da.judgeLength(pd.getString("zyms"), 100);
		if(StringUtils.isNotBlank(pd.getString("zylj"))){
			boolean bool2=da.judgeLength(pd.getString("zylj"), 100);
			if(bool1==true && bool2==true && bool3==true && bool4==true){
				PageData ta=resourceService.listResource(pd);//判断税号是否存在
				if(ta!=null){
					json.put("msg", "fail");
				}else{
					pd.put("creator",this.getSessionUser());
					resourceService.insertResource(pd);//添加资源
					json.put("msg", "success");
				}
				
			}else{
				json.put("msg", "error");
			}
		}else{
			if(bool1==true && bool3==true && bool4==true){
				PageData ta=resourceService.listResource(pd);//判断税号是否存在
				if(ta!=null){
					json.put("msg", "fail");
				}else{
					pd.put("creator",this.getSessionUser());
					resourceService.insertResource(pd);//添加资源
					json.put("msg", "success");
				}				
			}else{
				json.put("msg", "error");
			}
		}
				
		return json;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "deleteResource")
	@ResponseBody
	public Object deleteResource() throws Exception {
		
		PageData pd=new PageData();
		pd=this.getPageData();
	
		String id=pd.getString("id");	//获取资源id
		if(StringUtils.isNotBlank(id)){
			for(int i=0;i<id.split(",").length;i++){	
				String str=id.split(",")[i];
				String zyxh=str.substring(str.indexOf("|")+1, str.length()).trim();
				String zyid=str.substring(0, str.indexOf("|")).trim();
				PageData pdrole=new PageData();
				if(zyxh.length()>2){					
					pdrole.put("id", zyid);
					resourceService.deleteResource(pdrole);//删除二级资源类型
				}else{
					pdrole.put("zyxh", zyxh+"%%");
					resourceService.deleteResource(pdrole);//删除一级资源类型
				}
				
			}	
		}
		JSONObject json=new JSONObject();
		json.put("msg", "success");
		return json;
	}
	
	public static void main(String[] args) {
		String s="1104";
		if(s.substring(0, 1).equals("0")){
			System.out.println("0"+(Integer.valueOf(s)+1));
		}else{
			System.out.println(Integer.valueOf(s)+1);
		}
		
	}
	
}
