package com.shbw.service.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;

@Service("roleService")
public class RoleService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 查询分页角色列表
	 */
	public List<PageData> listAllRole(Page page) throws Exception{
		return (List<PageData>) dao.findForList("RoleMapper.selectlistPage", page);	
	}
	
	/*
	 * 查询
	 */
	public PageData listObjRole(PageData pd)throws Exception{
		return (PageData) dao.findForObject("RoleMapper.selectobjRole", pd);	
	}
	
	/*
	 * 查询资源列表
	 */
	public List<PageData> listAllMenu(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("RoleMapper.selectlistMenu", pd);	
	}
	
	/*
	 * 添加
	 */
	public void insertRole(PageData pd)throws Exception{
		dao.save("RoleMapper.insertRole", pd);	
	}
		
	/*
	 * 添加资源关系
	 */
	public void insertResourceGx(PageData pd)throws Exception{
		dao.save("RoleMapper.insertResourceGx", pd);	
	}
	
	/*
	 * 修改
	 */
	public void updateRole(PageData pd)throws Exception{
		dao.update("RoleMapper.updateRole", pd);	
	}
	
	/*
	 * 删除资源关系
	 */
	public void deleteResourceGx(PageData pd)throws Exception{
		dao.update("RoleMapper.deleteResourceGx", pd);	
	}
	
	/*
	 * 修改资源关系
	 */
	public void updateResourceGx(PageData pd)throws Exception{
		dao.save("RoleMapper.updateResourceGx", pd);	
	}
	
	/*
	 * 查询关系资源列表
	 */
	public List<PageData> listGxMenu(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("RoleMapper.selectlistGxMenu", pd);	
	}
	
	/*
	 * 删除
	 */
	public void deleteRole(PageData pd)throws Exception{
		dao.update("RoleMapper.deleteRole", pd);	
	}
	

}
