package com.shbw.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shbw.util.Const;

/**
 * 系统URL访问拦截器
 * @author Administrator
 *
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		//对路径进行判断，有的路径是不需要判断的
		if (path.matches(Const.NO_INTERCEPTOR_PATH)) {
			return true;
		} else {
			Subject currentUser = SecurityUtils.getSubject();
			Session session = currentUser.getSession();
			String user = (String) session.getAttribute("USERNAME");
			if (user != null) {
				return true;
			} else {
				// 登陆过滤,直接跳转登录界面
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;
			}
			
		}
	}

}
