package com.shbw.service.system.usergroup;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;

@Service("userGroupService")
public class UserGroupService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 用户组信息的列表查询
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listUserGroup(Page page) throws Exception{
		return (List<PageData>)dao.findForList("UserGroupMapper.findUserGrouplistPage", page);
	}
	
	/**
	 * 有条件查询用户组信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findUserGroupById(PageData pd) throws Exception{
		return (PageData)dao.findForObject("UserGroupMapper.findUserGroupById", pd);
	}
	
	
	/**
	 * 修改用户组信息
	 * @param pd
	 * @throws Exception
	 */
	public void updateUserGroupById(PageData pd) throws Exception{
		dao.update("UserGroupMapper.updateUserGroupById", pd);
	}
	
	/**
	 * 新增用户组信息
	 * @param pd
	 * @throws Exception
	 */
	public void insertUserGroup(PageData pd) throws Exception{
		dao.update("UserGroupMapper.insertUserGroup", pd);
	}
	
	/**
	 * 删除用户组信息
	 * @param ids
	 * @throws Exception
	 */
	public void deleteUserGroupInfo(String ids) throws Exception{
		dao.update("UserGroupMapper.deleteUserGroupInfo", ids);
	}
}
