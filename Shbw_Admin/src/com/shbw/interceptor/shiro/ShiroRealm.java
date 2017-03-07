package com.shbw.interceptor.shiro;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.shbw.service.user.UserService;
import com.shbw.util.Const;
import com.shbw.util.PageData;

/**
 * @author cyl 2016-12-11
 */
public class ShiroRealm extends AuthorizingRealm {
	
	@Resource(name = "userService")
	private UserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String USERNAME = (String) token.getPrincipal(); // 得到用户名
		String PASSWORD = new String((char[])token.getCredentials()); // 密码
		PageData pd=new PageData();
		pd.put("PASSWORD", PASSWORD);
		pd.put("USERNAME", USERNAME);
		try {
			pd=userService.getUserByNameAndPwd(pd);
			if(pd!=null){
				Subject currentUser = SecurityUtils.getSubject();
				Session session = currentUser.getSession();
				session.setAttribute("USERNAME", USERNAME);
				session.removeAttribute(Const.SESSION_SECURITY_CODE);
				return new SimpleAuthenticationInfo(USERNAME, PASSWORD, getName());
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("========2");
		return null;
	}

}
