package net.bwjf.kpjk.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * ���طǵ�¼���û�����
 * @author Administrator
 *
 */
public class LoginInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 602383287065066157L;

	@Override
	protected String doIntercept(ActionInvocation invoker) throws Exception {
		 System.out.println("��¼���ء�������"+ActionContext.getContext().getSession().get("userName"));
		 Object loginUserName = ActionContext.getContext().getSession().get("userName");  //��ȡ�û���
			ServletActionContext.getRequest().setAttribute("login",
					"success");
	     if(null == loginUserName){  
			ServletActionContext.getRequest().setAttribute("login",
					"login");
	         return "login";  // ���ﷵ���û���¼ҳ����ͼ  
	     }  
	        return invoker.invoke();  
	}

}
