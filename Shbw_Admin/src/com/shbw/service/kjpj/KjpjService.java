package com.shbw.service.kjpj;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;

@Service("kjpjService")
public class KjpjService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 查询分页开票流水列表
	 */
	public List<PageData> listAllKjpj(Page page) throws Exception{
		return (List<PageData>) dao.findForList("KjpjMapper.selectlistPage", page);	
	}
	
	/*
	 * 预览(查询开票流水)
	 */
	public PageData listObjKjpj(PageData pd)throws Exception{
		return (PageData) dao.findForObject("KjpjMapper.selectobjkJPj", pd);	
	}
	
	/*
	 * 预览(查询开票明细)
	 */
	public List<PageData> listAllKpmx(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("KjpjMapper.listAllKpmx", pd);	
	}
	
	/*
	 * 查询分页开票明细列表
	 */
	public List<PageData> listAllKpmx(Page page) throws Exception{
		return (List<PageData>) dao.findForList("KjpjMapper.selectKpmxlistPage", page);	
	}
	
	/*
	 * 开票(获取开票主信息)
	 */
	public PageData listObjKpls(PageData pd)throws Exception{
		return (PageData) dao.findForObject("KjpjMapper.listObjKpls", pd);	
	}
	
	/*
	 * 开票(获取开票明细信息)
	 */
	public List<PageData> listObjKpmx(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("KjpjMapper.listObjKpmx", pd);	
	}
	
	/*
	 * 修改(开票人)
	 */
	public void updateKpls(PageData pd)throws Exception{
		dao.update("KjpjMapper.updateKpls", pd);	
	}
	
	/*
	 * 返写开票流水信息(开票成功)
	 */
	public void updateCgfxKpls(PageData pd)throws Exception{
		dao.update("KjpjMapper.updateCgfxKpls", pd);	
	}
	
	/*
	 * 返写开票流水信息(开票失败)
	 */
	public void updateSbfxKpls(PageData pd)throws Exception{
		dao.update("KjpjMapper.updateSbfxKpls", pd);	
	}
	
	/*
	 * 返写销方信息(专票普票的最大开票金额)
	 */
	public void updateXfxx(PageData pd)throws Exception{
		dao.update("KjpjMapper.updateXfxx", pd);	
	}
	
	
}
