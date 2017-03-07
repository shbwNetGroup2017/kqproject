package com.cyl.bwjf.exceltoxml;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.bwjf.kpjk.bo.RegistService;
import net.bwjf.kpjk.domain.entities.User;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RegistAction extends ActionSupport {


	private static final long serialVersionUID = -1095258646691585358L;

	private String userName;
	private String password;
	private String GSMC;
	private String SH;
	
	public String getGSMC() {
		return GSMC;
	}

	public void setGSMC(String gSMC) {
		GSMC = gSMC;
	}

	public String getSH() {
		return SH;
	}

	public void setSH(String sH) {
		SH = sH;
	}
	private RegistService registServiceImpl;
	
	public RegistService getRegistServiceImpl() {
		return registServiceImpl;
	}

	public void setRegistServiceImpl(RegistService registServiceImpl) {
		this.registServiceImpl = registServiceImpl;
	}

	/**
	 * �û�ע��
	 * @return
	 * @throws Exception 
	 */
	public String zhuce() throws Exception{
		User user=new User();
		String ip = InetAddress.getLocalHost().getHostAddress();
		//�ж��û����Ƿ�Ψһ
		if(!registServiceImpl.judgeUser(this.userName)){
			user.setUSERNAME(this.userName);
			user.setPASSWORD(this.password);
			user.setGSMC(this.GSMC);
			user.setSH(this.SH);
			user.setIp(ip);
			String str=registServiceImpl.zhuce(user);
			return str;
		}else{
			String msg="�����û�ע����˺��ˣ���������д��";
			ServletActionContext.getRequest().setAttribute("msg",msg);
			return "error";
		}
		
	}
	
	
	/**
	 * ��ת���û�ע��ҳ��
	 * @return
	 */
	public String goResPage(){
		return "success";
	}
	
	/**
	 * ��ת���û���¼ҳ��
	 * @return
	 */
	public String goLoginPage(){
		System.out.println("wesocket>>>>>����ɹ�");
		return "success";
	}
	
	/**
	 * �û���¼
	 * @return
	 */
	@SuppressWarnings("unused")
	public String login(){
		User user=new User();
		user.setUSERNAME(this.userName);
		user.setPASSWORD(this.password);
		User us=registServiceImpl.login(user);
		try{
		   if(us!=null){
			  ActionContext actionContext = ActionContext.getContext();
			  Map<String, Object> session = actionContext.getSession();
			  session.put("userName", us.getUSERNAME());
			  ServletActionContext.getRequest().setAttribute("userName",
					 us.getUSERNAME());
		     return "success";
		   }else{
				ServletActionContext.getRequest().setAttribute("error","�û��˺Ż��������");
				return "error";
		   }
		}catch (Exception e){
			ServletActionContext.getRequest().setAttribute("error","�û��˺Ż��������");
			return "error";
		} 
	}
	
	/**
	 * �û��˳�
	 * @return
	 */
	public String exit(){
		  ActionContext actionContext = ActionContext.getContext();
		  Map<String, Object> session = actionContext.getSession();
		  session.remove("userName");
		return "success";
	}
	
	@SuppressWarnings("null")
	public String jsonMethod(){
		//HttpServletResponse rep=new 
		PrintWriter out=null;
		String str="error";
		JSONObject json=new JSONObject();
		json.put("str",str);
		out.print(str);
		return "success";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
