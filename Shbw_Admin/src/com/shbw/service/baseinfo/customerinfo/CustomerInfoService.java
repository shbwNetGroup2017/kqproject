package com.shbw.service.baseinfo.customerinfo;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;

import net.sf.json.JSONObject;

/**
 * @author Aiwen
 *
 */
@Service("customerInfoService")
public class CustomerInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	public List<PageData> selectListCustomerInfos(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CustomerInfoMapper.customerInfoslistPage", page);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> selectListCustomerTtInfos(Page page) throws Exception {
		return (List<PageData>) dao.findForList("CustomerInfoMapper.customerTtInfoslistPage", page);
	}

	public PageData selectCustomerInfoById(String id) throws Exception {
		return (PageData) dao.findForObject("CustomerInfoMapper.selectCustomerInfoById", id);
	}

	@SuppressWarnings("unchecked")
	public List<String> selectCustomerTtInfosList(String id) throws Exception {
		return (List<String>) dao.findForList("CustomerInfoMapper.customerTtInfosList", id);
	}

	public int saveCustomerInfo(PageData pd) throws Exception {

		int i = (int) dao.save("CustomerInfoMapper.insertCustomerInfo", pd);
		String ttIds = pd.getString("ttIds");
		if (ttIds == null || "".equals(ttIds)) {
			ttIds = String.valueOf(pd.get("id"));
		} else {
			ttIds += "," + String.valueOf(pd.get("id"));
		}
		pd.put("ttIds", ttIds);
		saveTtxx(pd.getString("ttIds"), pd);
		return i;
	}

	public int updateCustomerInfo(PageData pd) throws Exception {
		String ttIds = pd.getString("ttIds");
		if (ttIds != null) {
			saveTtxx(ttIds, pd);
		}
		return (int) dao.update("CustomerInfoMapper.updateByPrimaryKeySelective", pd);
	}

	public int deleteCustomerInfo(String ids) throws Exception {
		dao.delete("CustomerInfoMapper.deleteTtxx", ids);
		return (int) dao.delete("CustomerInfoMapper.delete", ids);
	}

	public void saveTtxx(String ttIds, PageData pd) throws Exception {
		if (StringUtils.isEmpty(ttIds))
			return;
		String[] ids = ttIds.split(",");
		String ttfjNames = pd.getString("ttfjName");
		JSONObject json = null;
		if (ttfjNames != null && !"".equals(ttfjNames)) {
			json = JSONObject.fromObject(ttfjNames);
		}

		for (String id : ids) {
			pd.put("ttid", id);
			if (json != null) {
				pd.put("fileName", json.get(id));
			}
			dao.save("CustomerInfoMapper.insertTtxx", pd);
		}
	}

	/**
	 * add by Aiwen at 2017-01-13 不分页查询出所有的客户名+ID
	 * 
	 * @return list
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> selectListCustomerInfos(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("CustomerInfoMapper.customerInfoslist", pd);
	}
	// end at 2017-01-13 by Aiwen
}
