package com.shbw.controller.dataSum;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shbw.controller.base.BaseController;
import com.shbw.entity.Page;
import com.shbw.service.dataSum.YkfpcxService;
import com.shbw.util.PageData;


/**
 * 已开发票查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/ykfpcx")
public class YkfpcxController extends BaseController {

	@Resource(name="ykfpcxService")
	private YkfpcxService ykfpcxService;
	
	
	/**
	 * 已开发票查询数据
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listYkfpcx")
	public ModelAndView selectYkfpcxlistPage(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<Map<String,Object>>  listYkfpcx = ykfpcxService.selectYkfpcxlistPage(page);
		mv.setViewName("dataSum/ykfpcx/ykfpcx_list");
		mv.addObject("listYkfpcx", listYkfpcx);
		mv.addObject("pd", pd);
		return mv;
	}
}
