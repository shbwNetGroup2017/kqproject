package com.shbw.service.salesmanager;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.util.Const;
import com.shbw.util.PageData;
import com.shbw.util.UuidUtil;

/**
 * @author Aiwen create at 2017-01-13
 *
 */
@Service("handBillingService")
public class HandBillingService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 不分页获取开票点信息
	 * 
	 * @param zzjgId
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> selectKpds(String zzjgId) throws Exception {
		return (List<PageData>) dao.findForList("HandBillingMapper.selectKpds", zzjgId);
	}

	/**
	 * 新增一条开票流水记录
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void insertKpls(PageData pd) throws Exception {

		pd.put("sqlx", "2");// 发票申请类型
		pd.put("lsjd", "1");// 发票流水阶段
		pd.put("zhlx", "1");// 账户类型
		pd.put("zhbm", "1");// 账户编码
		pd.put("cplb", "1");// 产品类别
		pd.put("ywlx", "1");// 业务类型
		pd.put("jslx", "1");// 结算类型
		pd.put("yjdz", "上海市中山北路长城大厦");// 邮寄地址
		pd.put("gfyb", "210093");// 购方邮编
		pd.put("kpbz", "0");// 开票标志
		// 获取用户名
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String userName = (String) session.getAttribute(Const.SESSION_USERNAME);
		//
		pd.put("creator", userName);
		dao.save("HandBillingMapper.insertKpls", pd);
		// 开票明细的保存
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<PageData> mx=(List)pd.get("mx");
		for (int i = 0; i < mx.size(); i++) {
			PageData mxPd=mx.get(i);
			mxPd.put("kplsh", pd.getString("kplsh"));
			mxPd.put("kpmxId", UuidUtil.get32UUID());
			mxPd.put("jylsh", UuidUtil.get32UUID().substring(0, 18));
			mxPd.put("creator", pd.getString("creator"));
			mxPd.put("del_flag", 0);
			mxPd.put("create_date", new Date());
			dao.save("HandBillingMapper.insertKpmx", mxPd);
		}
		
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listSpbms() throws Exception {
		return (List<PageData>) dao.findForList("HandBillingMapper.spbmMc", null);
	}
	
}
