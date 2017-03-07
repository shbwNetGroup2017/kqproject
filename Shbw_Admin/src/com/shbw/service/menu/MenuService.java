package com.shbw.service.menu;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.system.Menu;
import com.shbw.util.PageData;

@Service("menuService")
public class MenuService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取所有的菜单
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu(String username) throws Exception {
		List<Menu> rl = this.listAllParentMenu();//查询所有的一级菜单
		PageData pd=this.listAllZyid(username);
		for (Menu menu : rl) {
			if(pd==null){
				return null;
			}
			PageData pd1=new PageData();
			pd1.putAll(pd);
			pd1.put("zyxh", menu.getZyxh());
			List<Menu> subList = this.listSubMenuByParentId(pd1);
			if(subList.size()>0){
				menu.setHasMenu(true);
				menu.setSubMenu(subList);
			}
		}
		return rl;
	}
	
	/**
	 * 获取授权的资源ID
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public PageData listAllZyid(String username) throws Exception{
		return (PageData)dao.findForObject("MenuMapper.listAllZyid", username);
	}
	
	/**
	 * 获取所有的一级菜单资源
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listAllParentMenu() throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listAllParentMenu", null);
	}
	
	/**
	 * 获取所有的二级菜单资源
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> listSubMenuByParentId(PageData pd) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId", pd);
	}
	

}
