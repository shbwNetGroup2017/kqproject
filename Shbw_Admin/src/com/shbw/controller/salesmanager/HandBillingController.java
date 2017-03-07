package com.shbw.controller.salesmanager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;
import com.shbw.controller.base.BaseController;
import com.shbw.core.KpCheckUtils;
import com.shbw.service.baseinfo.customerinfo.CustomerInfoService;
import com.shbw.service.baseinfo.groupmanager.ZzjgService;
import com.shbw.service.salesmanager.HandBillingService;
import com.shbw.util.FromValidate;
import com.shbw.util.PageData;

/**
 * @author Aiwen create at 2017-02-11
 *
 */
@Controller
@RequestMapping(value = "server/handbilling")
public class HandBillingController extends BaseController {
	@Resource(name = "zzjgService")
	private ZzjgService zzjgService;
	@Resource(name = "handBillingService")
	private HandBillingService handBillingService;
	@Resource(name = "customerInfoService")
	private CustomerInfoService customerInfoService;

	/**
	 * 显示手工开票操作页面
	 * 
	 * @return mv
	 * @throws Exception
	 */
	@RequestMapping(value = "/show")
	public ModelAndView showHandBilling() throws Exception {
		ModelAndView mv = new ModelAndView();
		List<PageData> zzjgs = zzjgService.selectXfmc();
		List<PageData> spbmList = handBillingService.listSpbms();
		mv.addObject("zzjgs", zzjgs);
		mv.addObject("spbmList", spbmList);
		mv.setViewName("handbilling/handBilling");
		return mv;
	}

	/**
	 * 通过选择开票单位查询出开票点
	 * 
	 * @return Json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getKpds")
	@ResponseBody
	public Object getKpds() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> result = new HashMap<String, Object>();
		List<PageData> list = new ArrayList<PageData>();
		String zzjgId = pd.getString("zzjgId");
		if (!StringUtils.isNullOrEmpty(zzjgId)) {
			list = handBillingService.selectKpds(zzjgId);
		}
		result.put("kpdList", list);
		return result;
	}

	/**
	 * 通过客户ID查询出客户信息
	 * 
	 * @return Json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCustomerInfoById")
	@ResponseBody
	public Object getCustomerInfoById() throws Exception {
		PageData customerInfo = new PageData();
		Map<String, Object> result = new HashMap<String, Object>();
		customerInfo = this.getPageData();
		String id = customerInfo.getString("id");
		if (!StringUtils.isNullOrEmpty(id)) {
			customerInfo = customerInfoService.selectCustomerInfoById(id);
		}
		result.put("customerInfo", customerInfo);
		return result;
	}

	/**
	 * 通过ID查询出销方信息
	 * 
	 * @return Json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getXfxxById")
	@ResponseBody
	public Object getXfxxById() throws Exception {
		PageData xfxx = new PageData();
		Map<String, Object> result = new HashMap<String, Object>();
		xfxx = this.getPageData();
		String id = xfxx.getString("id");
		if (!StringUtils.isNullOrEmpty(id)) {
			xfxx = zzjgService.selectZzjgById(id);
		}
		result.put("xfxx", xfxx);
		return result;
	}

	/**
	 * 模糊查询出客户信息
	 * 
	 * @return Json
	 * @throws Exception
	 */
	@RequestMapping(value = "/listCustomers")
	@ResponseBody
	public Object listCustomers(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String gfmc = request.getParameter("gfmc");
		PageData pd = new PageData();
		pd.put("gfmc", gfmc);
		List<PageData> khxxs = customerInfoService.selectListCustomerInfos(pd);
		if (khxxs != null && khxxs.size() > 0) {
			result.put("data", getXfxxHtml(khxxs));
			result.put("msg", "success");
		} else {
			result.put("msg", "fail");
		}
		return result;
	}

	/**
	 * 把客户信息拼接成html
	 * 
	 * @param khxxs
	 * @return
	 */
	public String getXfxxHtml(List<PageData> khxxs) {
		StringBuffer sb = new StringBuffer();
		sb.append("<ul id='customerUl'> ");
		for (PageData pd : khxxs) {
			// "<a href='javascript:void(0)' onclick='customerSelect("+a.id+")'
			// ><div class='customer'><li>"+a.gfmc+"</li></div></a>"
			sb.append(" <a href='javascript:void(0)'  onclick='customerSelect(");
			sb.append(pd.get("id"));
			sb.append(")'");
			sb.append("><li class='customer'>");
			sb.append(pd.getString("gfmc"));
			sb.append("</li></a>");
		}
		sb.append("</ul>");
		return sb.toString();
	}

	/**
	 * 新增手工开票记录
	 * 
	 * @return Json
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSgkp")
	@ResponseBody
	public Object addSgkp() throws Exception {
		PageData pd = new PageData();

		Map<String, Object> result = new HashMap<String, Object>();
		pd = this.getPageData();
		// 编码表版本号和含税税率标识重新赋值
		pd.put("bbh", "0");
		pd.put("hsslbs", "0");
		pd.put("kpbz", "0");
		pd.put("kplsh", this.get32UUID());// 开票流水号
		/// 未知字段

		/*pd.put("tzdbh", "tzdbh");// 通知单编号
		pd.put("yfpdm", "yfpdm");// 原发票代码
		pd.put("yfphm", "yfphm");// 原发票号码
*/		pd.put("qmcs", "0000004282000000");// 签名参数

		// 调用数据校验
		/*
		 * boolean flag = true; flag = sgkpDataJudge(pd); if (flag) {
		 * pd.put("kplsh", this.get32UUID()); handBillingService.insertKpls(pd);
		 * result.put("msg", "success"); } else { result.put("msg", "fail"); }
		 */
		// 保存前的校验
		KpCheckUtils kpCheckUtils = new KpCheckUtils();
		PageData checkResult = new PageData();
		List<PageData> mx=sgkpSpmx(pd);
		pd.put("mx", mx);
		// 价税合计 金额合计类型转换Begin
		String jshj=pd.getString("jshj");
		Double jshjD=Double.valueOf(jshj);
		String hjje=pd.getString("hjje");
		Double hjjeD=Double.valueOf(hjje);
		String hjse=pd.getString("hjse");
		Double hjseD=Double.valueOf(hjse);
		pd.put("jshj", jshjD);
		pd.put("hjje", hjjeD);
		pd.put("hjse", hjseD);
		// 价税合计金额合计类型转换End
		if(mx.size()>8){
			pd.put("qdbz", "1");// 清单标志
		}else{
			pd.put("qdbz", "0");// 非清单
		}
		
		checkResult = kpCheckUtils.kpCheck(pd);
		
		if (checkResult == null) {// 校验通过
			handBillingService.insertKpls(pd);
			result.put("msg", "success");

		} else {
			result.put("msg", "fail");
			result.put("checkResult", checkResult);
		}

		return result;
	}

	public List<PageData> sgkpSpmx(PageData pd) throws Exception {
		if ("hs".equals(pd.getString("hs"))) {
			pd.put("hsbz", "0");
		} else if ("bhs".equals(pd.getString("hs"))) {
			pd.put("hsbz", "1");
		}
		// 开票明细的保存
		String c = pd.getString("count");
		List<PageData> spmxList = new ArrayList<PageData>();
		int count = Integer.parseInt(c);
		for (int i = 1; i <= count; i++) {
			PageData checkSpmx = new PageData();
			checkSpmx = getSpmx(pd, i);
			spmxList.add(checkSpmx);
		}
		return spmxList;
	}

	public PageData getSpmx(PageData pd, int i) {
		PageData spmx = new PageData();
		spmx.put("fphxz", "0");// 发票行性质
		spmx.put("spbm", pd.getString("spbm"+i));// 商品编码
		spmx.put("hsbz", pd.getString("hsbz"));
		spmx.put("spmc", pd.getString("hwmc" + i));
		spmx.put("dw", pd.getString("dw" + i));
		spmx.put("spsl", pd.getString("sls" + i));
		spmx.put("dj", pd.getString("dj" + i));
		spmx.put("sl", pd.getString("sl" + i));
		spmx.put("ggxh", pd.getString("ggxh" + i));
		spmx.put("spje", pd.getString("je" + i));
		if ("0".equals(pd.getString("hsbz"))) {

			BigDecimal dj = new BigDecimal(pd.getString("dj" + i));
			BigDecimal spsl = new BigDecimal(pd.getString("sls" + i));
			BigDecimal sl = new BigDecimal(pd.getString("sl" + i));
			BigDecimal spje = dj.multiply(spsl);// 商品金额
			spmx.put("spje", spje.setScale(2, BigDecimal.ROUND_HALF_UP));
			
			dj = dj.divide(sl.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_EVEN);
			// 不含税单价
			// 计算结果
			BigDecimal je = dj.multiply(spsl);// 不含税金额
			BigDecimal se = je.multiply(sl);// 不含税税额

			BigDecimal bhsje = je.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal bhsse = se.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal jshj = bhsje.add(bhsse);

			spmx.put("bhsspje", bhsje);
			spmx.put("spse", bhsse);
			spmx.put("jshj", jshj);
			spmx.put("bhsdj", String.valueOf(dj));

		} else if ("1".equals(pd.getString("hsbz"))) {// 不含税
			spmx.put("bhsdj", pd.getString("dj" + i));
			String spje = pd.getString("je" + i);
			Double spjeD=Double.valueOf(spje);
			spmx.put("bhsspje", spjeD);
			spmx.put("spje", spjeD);
			String spse = pd.getString("se" + i);
			spmx.put("spse", Double.valueOf(spse));
			spmx.put("jshj", Double.valueOf(spje) + Double.valueOf(spse));
		}
		return spmx;
	}

	/**
	 * 判断手工开票数据
	 * 
	 * @param pd
	 * @return boolean
	 */
	public boolean sgkpDataJudge(PageData pd) {
		boolean flag = true;
		String dh = "";
		String dz = "";
		String yh = "";
		String yhzh = "";
		FromValidate fv = new FromValidate();// 后台校验
		if (!fv.judgeUnNullLength(pd.getString("fplx"), 10)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("xfmc"), 32)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("kpd"), 32)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("gfsh"), 20)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("customerName"), 100)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("gfdh"), 128)) {
			flag = false;
			return flag;
		} else if (!pd.getString("gfdh").contains("/")) {
			flag = false;
			return flag;
		} else if (pd.getString("gfdh").indexOf('/') == pd.getString("gfdh").length() - 1) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("gfdh").split("/")[0], 64)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("gfdh").split("/")[1], 64)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("khyhzh"), 128)) {
			flag = false;
			return flag;
		} else if (!pd.getString("khyhzh").contains("/")) {
			flag = false;
			return flag;
		} else if (pd.getString("khyhzh").indexOf('/') == pd.getString("khyhzh").length() - 1) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("khyhzh").split("/")[0], 64)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("khyhzh").split("/")[1], 64)) {
			flag = false;
			return flag;
		} else if (!sgkpSpxxJudge(pd)) {
			flag = false;
		} else if (!fv.judgeUnNullLength(pd.getString("xfsh"), 20)) {
			flag = false;
		} else if (!fv.judgeUnNullLength(pd.getString("xfcustomerName"), 100)) {
			flag = false;
		} else if (!fv.judgeUnNullLength(pd.getString("xfdh"), 128)) {
			flag = false;
			return flag;
		} else if (!pd.getString("xfdh").contains("/")) {
			flag = false;
			return flag;
		} else if (pd.getString("xfdh").indexOf('/') == pd.getString("xfdh").length() - 1) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("xfdh").split("/")[0], 64)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("xfdh").split("/")[1], 64)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("xfyhzh"), 128)) {
			flag = false;
			return flag;
		} else if (!pd.getString("xfyhzh").contains("/")) {
			flag = false;
			return flag;
		} else if (pd.getString("xfyhzh").indexOf('/') == pd.getString("xfyhzh").length() - 1) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("xfyhzh").split("/")[0], 64)) {
			flag = false;
			return flag;
		} else if (!fv.judgeUnNullLength(pd.getString("xfyhzh").split("/")[1], 64)) {
			flag = false;
			return flag;
		}
		// 购方地址电话重新赋值
		dz = pd.getString("gfdh").split("/")[0];
		dh = pd.getString("gfdh").split("/")[1];
		pd.put("gfdz", dz);
		pd.put("gfdh", dh);
		// 购方银行和银行账户
		yh = pd.getString("khyhzh").split("/")[0];
		yhzh = pd.getString("khyhzh").split("/")[1];
		pd.put("gfyh", yh);
		pd.put("gfyhzh", yhzh);
		// 销方地址电话从新赋值
		dz = pd.getString("xfdh").split("/")[0];
		dh = pd.getString("xfdh").split("/")[1];
		pd.put("xfdz", dz);
		pd.put("xfdh", dh);
		// 销方银行和银行账户重新赋值
		yh = pd.getString("xfyhzh").split("/")[0];
		yhzh = pd.getString("xfyhzh").split("/")[1];
		pd.put("xfyh", yh);
		pd.put("xfyhzh", yhzh);
		return flag;
	}

	/**
	 * 判断手工开票数据
	 * 
	 * @param pd
	 * @return boolean
	 */
	public boolean sgkpSpxxJudge(PageData pd) {
		boolean flag = true;
		int count = 0;
		FromValidate fv = new FromValidate();// 后台校验
		String c = pd.getString("count");
		if (!StringUtils.isNullOrEmpty(c)) {
			count = Integer.parseInt(c);
			for (int i = 1; i <= count; i++) {
				if (!fv.judgeUnNullLength(pd.getString("hwmc" + i), 100)) {
					flag = false;
				}
				if (!fv.judgeUnNullLength(pd.getString("sls" + i), 18)) {
					flag = false;
				}
				if (!fv.judgeUnNullLength(pd.getString("dj" + i), 18)) {
					flag = false;
				}
				if (!fv.judgeUnNullLength(pd.getString("sl" + i), 18)) {
					flag = false;
				}
			}
			return flag;
		} else {
			return false;
		}
	}
}
