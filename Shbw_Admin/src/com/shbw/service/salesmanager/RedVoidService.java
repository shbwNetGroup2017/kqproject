package com.shbw.service.salesmanager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.Const;
import com.shbw.util.PageData;
import com.shbw.util.UuidUtil;

/**
 * @author Aiwen create at 2017-02-16
 *
 */
@Service("redVoidService")
public class RedVoidService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 分页查询出红冲作废重打显示列表
	 * 
	 * @param page
	 * @return List<PageData>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> selectListRedVoidApply(Page page) throws Exception {
		return (List<PageData>) dao.findForList("RedVoidMapper.redVoidApplylistPage", page);
	}

	/**
	 * 根据ID查询出开票流水的详细信息
	 * 
	 * @param id
	 * @return PageData
	 * @throws Exception
	 */
	public PageData selectRedVoidApplyById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RedVoidMapper.redVoidApplyById", pd);
	}

	@SuppressWarnings("unchecked")
	public void insertRedVoid(String id, String kpbz, String sqyy, String fileName, String tzdbh) throws Exception {
		PageData kpls = new PageData();

		// 获取用户名
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String userName = (String) session.getAttribute(Const.SESSION_USERNAME);
		//

		kpls = (PageData) dao.findForObject("RedVoidMapper.redVoidApplyDetailById", id);

		List<PageData> kpmxList = (List<PageData>) dao.findForList("RedVoidMapper.listKpmxs", kpls);

		if (kpmxList.size() > 8 && "1".equals(kpbz)) {
			@SuppressWarnings("rawtypes")
			List<List> cfList = fpcf(kpmxList);
			for (int i = 0; i < cfList.size(); i++) {
				List<PageData> kpmxPdList = cfList.get(i);
				saveKplsAndKpmx(kpls, kpmxPdList, id, kpbz, sqyy, fileName, tzdbh, userName);
			}
		} else {
			saveKplsAndKpmx(kpls, kpmxList, id, kpbz, sqyy, fileName, tzdbh, userName);
		}
		dao.update("RedVoidMapper.updateKpls", id);// 修改原开票流水的红冲作废重打标志

	}

	public void saveKplsAndKpmx(PageData kpls, List<PageData> kpmxList, String id, String kpbz, String sqyy,
			String fileName, String tzdbh, String userName) throws Exception {
		BigDecimal hjse = new BigDecimal(0);
		BigDecimal hjje = new BigDecimal(0);
		PageData hzc = new PageData();
		String hzcKplsh = UuidUtil.get32UUID();
		String yskplsh = kpls.getString("kplsh");
		kpls.put("kplsh", hzcKplsh);
		kpls.put("kpbz", kpbz);
		kpls.put("lsjd", "2");
		kpls.put("zhbm", kpls.getString("zhbh"));
		kpls.put("xfcustomerId", kpls.getString("xfid"));
		kpls.put("customerId", kpls.getString("gfid"));
		kpls.put("tzdbh", tzdbh);
		kpls.put("yfpdm", kpls.getString("fpdm"));
		kpls.put("yfphm", kpls.getString("fphm"));

		///// 开票流水对应的红冲表数据
		hzc.put("ysid", id);
		hzc.put("sqyy", sqyy);
		hzc.put("fileName", fileName);
		hzc.put("yskplsh", yskplsh);
		hzc.put("hzckplsh", hzcKplsh);
		hzc.put("kpbz", kpbz);
		hzc.put("tzdbh", tzdbh);
		hzc.put("creator", userName);
		hzc.put("create_date", new Date());
		//////

		//// 开票明细重新写入Begin
		if (kpmxList != null) {
			for (PageData kpmx : kpmxList) {
				kpmx.put("kplsh", hzcKplsh);
				kpmx.put("creator", userName);
				kpmx.put("create_date", new Date());
				if ("1".equals(kpbz)) {
					/// 红冲是金额取负值
					BigDecimal spjeMx = new BigDecimal(String.valueOf(kpmx.get("spje")));
					BigDecimal bhsspjeMx = new BigDecimal(String.valueOf(kpmx.get("bhsspje")));
					BigDecimal spseMx = new BigDecimal(String.valueOf(kpmx.get("spse")));
					BigDecimal spslMx = new BigDecimal(String.valueOf(kpmx.get("spsl")));
					kpmx.put("spje", (new BigDecimal(0).subtract(spjeMx)).setScale(2, BigDecimal.ROUND_HALF_UP));
					kpmx.put("bhsspje", (new BigDecimal(0).subtract(bhsspjeMx)).setScale(2, BigDecimal.ROUND_HALF_UP));
					kpmx.put("spse", (new BigDecimal(0).subtract(spseMx)).setScale(2, BigDecimal.ROUND_HALF_UP));
					kpmx.put("spsl", (new BigDecimal(0).subtract(spslMx)).setScale(2, BigDecimal.ROUND_HALF_UP));
					/*
					 * kpmx.put("spje", (0.00d - (Double) kpmx.get("spje")));
					 * kpmx.put("bhsspje", (0.00d - (Double)
					 * kpmx.get("bhsspje"))); kpmx.put("spse", (0.00d - (Double)
					 * kpmx.get("spse"))); kpmx.put("spsl", (0.00d -
					 * Double.valueOf(kpmx.getString("spsl"))));
					 */
					hjse = hjse.add(spseMx);
					hjje = hjje.add(bhsspjeMx);
				}
				// System.out.println("kpmx:" + kpmx);
				dao.save("HandBillingMapper.insertKpmx", kpmx);
			}
		}
		//// 开票明细重新写入End
		// 发票红冲金额取负
		if ("1".equals(kpbz)) {
			hjse = (new BigDecimal(0).subtract(hjse)).setScale(2, BigDecimal.ROUND_HALF_UP);
			hjje = (new BigDecimal(0).subtract(hjje)).setScale(2, BigDecimal.ROUND_HALF_UP);
			kpls.put("hjse", hjse.setScale(2, BigDecimal.ROUND_HALF_UP));
			kpls.put("hjje", hjje.setScale(2, BigDecimal.ROUND_HALF_UP));
			kpls.put("jshj", (hjse.add(hjje)).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		/*
		 * System.out.println("kpls:" + kpls);
		 * System.out.println("------------------end----------");
		 */
		dao.save("HandBillingMapper.insertKpls", kpls);// 红冲作废开票流水新写入
		dao.save("RedVoidMapper.insertKplsHzc", hzc);// 红冲作废数据写入t_kpls_hzc对应表
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<List> fpcf(List<PageData> kpmxList) throws Exception {
		float len = (float) kpmxList.size();
		int num = (int) Math.ceil(len / 8);
		/////
		int index = 0;
		List<List> cfLists = new ArrayList<List>();
		for (int i = 0; i < num; i++) {
			List<PageData> list = new ArrayList<PageData>();
			cfLists.add(list);
		}
		for (int j = 0; j < kpmxList.size(); j++) {
			PageData newKpmx = new PageData();
			newKpmx = kpmxList.get(j);
			cfLists.get(index).add(newKpmx);
			if (j % 8 == 7) {
				index++;
			}
		}

		/////

		return cfLists;
	}

	/**
	 * 根据ID查询出开票流水的详细信息
	 * 
	 * @param id
	 * @return PageData
	 * @throws Exception
	 */
	public PageData selectRedVoidApplyById(String id) throws Exception {
		return (PageData) dao.findForObject("RedVoidMapper.redVoidApplyDetailById", id);
	}

	public void updateRedVoidByAudit(PageData pd) throws Exception {
		dao.update("RedVoidMapper.updateRedVoidById", pd);
	}

	////
	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * BeanFactory f = new
	 * ClassPathXmlApplicationContext("spring/ApplicationContext.xml");
	 * DaoSupport daoSupport = (DaoSupport) f.getBean("daoSupport"); //
	 * 获得service接口 RedVoidService RedVoidService redVoidService =
	 * (RedVoidService) f.getBean("redVoidService"); PageData kpls = (PageData)
	 * daoSupport.findForObject("RedVoidMapper.redVoidApplyDetailById", "122");
	 * List<PageData> kpmxList = (List<PageData>)
	 * daoSupport.findForList("RedVoidMapper.listKpmxs", kpls);
	 * System.out.println(kpls); List<List> kplsCfList = redVoidService.fpcf(
	 * kpmxList); for (List<PageData> list : kplsCfList) {
	 * System.out.println(list); System.out.println("---------------------"); }
	 * 
	 * }
	 */
}
