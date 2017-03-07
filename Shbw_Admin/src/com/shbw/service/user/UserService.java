package com.shbw.service.user;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.shbw.dao.DaoSupport;
import com.shbw.entity.Page;
import com.shbw.util.PageData;

@Service("userService")
public class UserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

   /**
    * 登录判断
    * @param pd
    * @return
    * @throws Exception
    */
	public PageData getUserByNameAndPwd(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.getUserInfo", pd);
	}

	/**
	 * 查询用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUser(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.userlistPage", page);
	}

	/**
	 * 保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void insertUser(PageData pd) throws Exception {
		String role=pd.getString("roleId");
		String userGroup=pd.getString("userGroupId");
		dao.update("UserXMapper.insertUser", pd);             //添加用户信息
		if(role!=null&&!role.equals("")){
			String ArrayRole[] = role.split(",");
			pd.put("roleList", Arrays.asList(ArrayRole));
			dao.update("UserXMapper.insertUserToRole",pd);    //添加用户和角色信息
		}
		if(userGroup!=null&&!userGroup.equals("")){
			String ArrayUserGroup[] = userGroup.split(",");
			pd.put("roleList", Arrays.asList(ArrayUserGroup));
			dao.update("UserXMapper.insertUserToUserGroup",pd);//添加用户和用户组信息
		}
	}
	
    /**
     * 删除用户
     * @param pd
     * @throws Exception
     */
	public void deleteUser(PageData pd) throws Exception {
		dao.update("UserXMapper.deleteUser", pd);
	}

	/**
	 * 更改用户
	 * @param pd
	 * @throws Exception
	 */
	public void updateUser(PageData pd) throws Exception {
		String role=pd.getString("roleId");
		String userGroup=pd.getString("userGroupId");
		dao.update("UserXMapper.updateUser", pd);           //更新用户信息
		dao.update("UserXMapper.deleteUserToRole", pd);           //删除用户和角色的关系
		dao.update("UserXMapper.deleteUserToUserGroup", pd);      //删除用户和用户组的关系
		if(role!=null&&!role.equals("")){
			String ArrayRole[] = role.split(",");
			pd.put("roleList", Arrays.asList(ArrayRole));
			dao.update("UserXMapper.insertUserToRole",pd);  //更新用户和角色关系信息
		}
		if(userGroup!=null&&!userGroup.equals("")){
			String ArrayUserGroup[] = userGroup.split(",");
			pd.put("roleList", Arrays.asList(ArrayUserGroup));
			dao.update("UserXMapper.insertUserToUserGroup",pd);//更新用户和用户组关系信息
		}
	}
	
	/**
	 * 查询用户信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findUserByAccount(PageData pd) throws Exception{
		PageData user=(PageData)dao.findForObject("UserXMapper.findUserByAccount", pd);
		return user;
	}
	
	/**
	 * 查询所有的角色
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findAllRole() throws Exception{
		return (List<PageData>) dao.findForList("UserXMapper.findAllRole", null);
	}
	
	/**
	 * 查询所有的用户组
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> finAllUserGroup() throws Exception{
		return (List<PageData>) dao.findForList("UserXMapper.finAllUserGroup", null);
	}
	
	/**
	 * 查询修改用户时的角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findEditAllRole(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("UserXMapper.findEditAllRole", pd);
	}
	
	/**
	 * 查询修改用户时的用户组
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> finEditAllUserGroup(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("UserXMapper.finEditAllUserGroup", pd);
	}
	
	/**
	 * 批量删除
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteUser(String[] USER_IDS) throws Exception {
		dao.delete("UserXMapper.deleteUser", USER_IDS);
	}

}
