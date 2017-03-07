package com.shbw.controller.baseinfo.ticktedpoint;

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
import com.shbw.util.FromValidate;
import com.shbw.util.PageData;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "server/ticketedPoint")
public class TicketedPointController extends BaseController {
	
	@Resource(name="ticketedPointService")
	private TicketedPointService ticketedPointService;

	/**
	 * 查询开票点的list界面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listPage")
	public ModelAndView toListPage(Page page) throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=this.getPageData();
		page.setPd(pd);
		List<PageData> list=(List<PageData>)ticketedPointService.listTicketedPoint(page);
		mv.addObject("list",list);
		mv.addObject("pd",pd);
		mv.setViewName("baseinfo/ticketedPoint/ticketedPoint_list");
		return mv;
	}
	
	/**
	 * 跳转到修改、添加、详情界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editTicketedPoint")
	public ModelAndView editTicketedPoint() throws Exception{
		ModelAndView mv=new ModelAndView();
		PageData pd=this.getPageData();
		List<PageData> list=ticketedPointService.findGroupList(pd);
		if(pd.getString("type").equals("1")){//修改
			PageData pointPd=ticketedPointService.findTicketedPointById(pd);
			mv.addObject("pd",pointPd);
			mv.setViewName("baseinfo/ticketedPoint/ticketedPoint_edit");
		}else if(pd.getString("type").equals("2")){//查看详情
			PageData pointPd=ticketedPointService.findTicketedPointById(pd);
			mv.addObject("pd",pointPd);
			mv.setViewName("baseinfo/ticketedPoint/ticketedPoint_detail");
		}else{//新增
			mv.setViewName("baseinfo/ticketedPoint/ticketedPoint_edit");
		}
		mv.addObject("list",list);
		return mv;
	}

	/**
	 * 保存开票点信息
	 * @return
	 */
	@RequestMapping(value="/saveTicketedPointInfo")
	@ResponseBody
	public Object saveTicketedPointInfo(){
		PageData pd=this.getPageData();
		JSONObject json=new JSONObject();
		pd.put("username", this.getSessionUser());
		String errorMsg="";
		FromValidate fv=new FromValidate();
    	if(!fv.judgeLength(pd.get("kpdmc").toString(), 64)){
    		errorMsg+="商品编码信息不能为空或长度不能超过64个字符";
    	}else if(!fv.judgeLength(pd.get("skph").toString(), 100)){
    		errorMsg+="税控盘号信息不能为空或长度不能超过100个字符";
    	}else if(!fv.judgeLength(pd.get("zzjgId").toString(), 32)){
    		errorMsg+="组织机构信息不能为空或长度不能超过32个字符";
    	}else if(!fv.judgeLength(pd.get("zcm").toString(), 200)){
    		errorMsg+="注册码不能为空或长度不能超过200个字符";
    	}else if(!fv.judgeLength(pd.get("skpmm").toString(), 20)){
    		errorMsg+="税控盘密码不能为空或长度不能超过20个字符";
    	}else if(!fv.judgeLength(pd.get("skpkl").toString(), 20)){
    		errorMsg+="税控盘口令不能为空或长度不能超过20个字符";
    	}else if(pd.get("remark").toString().length()>100){
    		errorMsg+="备注信息长度不能超过100个字符";
    	}
    	if(!StringUtils.isNotBlank(errorMsg)){
    		try {
    			if(!pd.getString("id").equals("")){
    				ticketedPointService.updateTicketedPointById(pd);
    				json.put("msg", "success");
    			}else{
    				PageData tickePoint=ticketedPointService.findTicketedPointByName(pd);
    				if(tickePoint==null){
    					ticketedPointService.insertTicketedPoint(pd);
    					json.put("msg", "success");
    				}else{
    					json.put("msg", "exit");
    				}
    			}
    			
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
	@RequestMapping(value="/deleteTicketedPoint")
	@ResponseBody
	public Object deleteTicketedPoint() throws Exception{
		PageData pd=this.getPageData();
		Map<String,String> map=new HashMap<String,String>();
		String ids = pd.getString("DATA_IDS");
		if (!StringUtils.isEmpty(ids)) {
			ticketedPointService.deleteTicketedPointInfo(ids);
			map.put("msg", "ok");
		}
		return map;
	}
}
