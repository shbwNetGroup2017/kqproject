package com.shbw.service.baseinfo.commodity;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;

@Service("commodityCodeService")
public class CommodityCodeService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public void insertCommodityCodeList(List<PageData> list) throws Exception{
		dao.update("CommodityCodeMapper.insertCommodityCodeList", list);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listCommodityCode(Page page) throws Exception{
		return (List<PageData>)dao.findForList("CommodityCodeMapper.findCommodityCodelistPage", page);
	}
	
	public PageData findCommodityCode(PageData pd) throws Exception{
		return (PageData)dao.findForObject("CommodityCodeMapper.findCommodityCode", pd);
	}
	
	public PageData findCommodityCodeById(PageData pd) throws Exception{
		return (PageData)dao.findForObject("CommodityCodeMapper.findCommodityCodeById", pd);
	}
	
	public void updateCommodityCodeById(PageData pd) throws Exception{
		dao.update("CommodityCodeMapper.updateCommodityCodeById", pd);
	}
	
	public void insertCommodityCode(PageData pd) throws Exception{
		dao.update("CommodityCodeMapper.insertCommodityCode", pd);
	}
	
	public void deleteCommodityCodeInfo(String ids) throws Exception{
		dao.update("CommodityCodeMapper.deleteCommodityCode", ids);
	}

}
