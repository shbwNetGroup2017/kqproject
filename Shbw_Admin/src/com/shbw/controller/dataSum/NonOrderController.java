package com.shbw.controller.dataSum;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.dataSum.NonOrderService;
import com.shbw.util.PageData;
/**
 * 订单类信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/nonorder")
public class NonOrderController extends BaseController{
	@Resource(name = "nonOrderService")
	private NonOrderService nonOrderService;
	/**
	 * 非订单信息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listNonOrders")
	public  ModelAndView listNonOrders(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<Map<String,Object>>  orderList = nonOrderService.listNonOrders(page);
		mv.setViewName("dataSum/nonorder/nonorder_list");
		mv.addObject("orderList", orderList);
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 非订单信息列表详情
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listNonOrderMX")
	public  ModelAndView listNonOrderMX(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<Map<String,Object>>  orderList = nonOrderService.listNonOrderMX(page);
		mv.setViewName("dataSum/nonorder/listNonOrderMX");
		mv.addObject("orderList", orderList);
		mv.addObject("pd", pd);
		return mv;
	}
}
