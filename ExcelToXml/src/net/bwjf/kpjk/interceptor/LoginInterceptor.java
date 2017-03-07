package net.bwjf.kpjk.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 拦截非登录的用户请求
 * @author Administrator
 *
 */
public class LoginInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 602383287065066157L;

	@Override
	protected String doIntercept(ActionInvocation invoker) throws Exception {
		 System.out.println("登录拦截。。。。"+ActionContext.getContext().getSession().get("userName"));
		 Object loginUserName = ActionContext.getContext().getSession().get("userName");  //获取用户名
			ServletActionContext.getRequest().setAttribute("login",
					"success");
	     if(null == loginUserName){  
			ServletActionContext.getRequest().setAttribute("login",
					"login");
	         return "login";  // 这里返回用户登录页面视图  
	     }  
	        return invoker.invoke();  
	}

}
