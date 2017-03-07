package com.shbw.service.resource;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;

@Service("resourceService")
public class ResourceService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 查询分页资源列表
	 */
	public List<PageData> listAllResource(Page page) throws Exception{
		return (List<PageData>) dao.findForList("ResourceMapper.selectlistPage", page);	
	}
	
	/*
	 * 查询上级资源列表
	 */
	public List<PageData> listAllMenu(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ResourceMapper.selectlistMenu", pd);	
	}
	
	
	/*
	 * 查询
	 */
	public PageData listObjResource(PageData pd)throws Exception{
		return (PageData) dao.findForObject("ResourceMapper.selectobjResource", pd);	
	}
	
	/*
	 * 查询(判断序号是否存在)
	 */
	public PageData listResource(PageData pd)throws Exception{
		return (PageData) dao.findForObject("ResourceMapper.selectResource", pd);	
	}
	
	/*
	 * 修改
	 */
	public void updateResource(PageData pd)throws Exception{
		dao.update("ResourceMapper.updateResource", pd);	
	}
	
	/*
	 * 添加
	 */
	public void insertResource(PageData pd)throws Exception{
		dao.save("ResourceMapper.insertResource", pd);	
	}
				
	/*
	 * 删除
	 */
	public void deleteResource(PageData pd)throws Exception{
		dao.update("ResourceMapper.deleteResource", pd);	
	}
	
	/*
	 * 查询(查询出最大的序号)
	 */
	public PageData listRsZyxh(PageData pd)throws Exception{
		return (PageData) dao.findForObject("ResourceMapper.selectRsZyxh", pd);	
	}
	
}
