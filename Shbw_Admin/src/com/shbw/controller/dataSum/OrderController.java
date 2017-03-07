package com.shbw.controller.dataSum;

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
import com.shbw.service.dataSum.OrderService;
import com.shbw.util.PageData;

import net.sf.json.JSONObject;

/**
 * 订单类信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController{
	@Resource(name = "orderService")
	private OrderService orderService;
	
	/**
	 * 订单信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listOrders")
	public  ModelAndView listOrder(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<Map<String,Object>>  orderList = orderService.listOrder(page);
		mv.setViewName("dataSum/order/order_list");
		mv.addObject("orderList", orderList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 明细信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listOrderMX")
	public ModelAndView  listOrderMX(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<Map<String,Object>>  orderMXList = orderService.listOrderMX(page);
		mv.setViewName("dataSum/order/orderMX_list");
		mv.addObject("orderMXList", orderMXList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping(value = "/deleteOrder")
	@ResponseBody
	public Object  deleteOrder() {
		JSONObject json= new JSONObject();
		try {
			PageData pd=new PageData();
			pd=this.getPageData();	
			String id=pd.getString("ids");//获取角色id
			if(StringUtils.isNotBlank(id)){
				for(int i=0;i<id.split(",").length;i++){	
					pd.put("jylsh", id.split(",")[i]);
					orderService.deleteOrder(pd);
					orderService.deleteOrderMX(pd);
				}	
				
			}		
			
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("msg", "error");
			e.printStackTrace();
		}
		return json;
	}
	@RequestMapping(value = "/deleteOrderMX")
	@ResponseBody
	public Object  deleteOrderMX() {
		JSONObject json= new JSONObject();
		try {
			PageData pd=new PageData();
			pd=this.getPageData();	
			String id=pd.getString("ids");//获取角色id
			if(StringUtils.isNotBlank(id)){
				for(int i=0;i<id.split(",").length;i++){	
					pd.put("id", id.split(",")[i]);
					orderService.deleteOrderMX(pd);
				}	
				
			}		
			
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("msg", "error");
			e.printStackTrace();
		}
		return json;
	}
}
