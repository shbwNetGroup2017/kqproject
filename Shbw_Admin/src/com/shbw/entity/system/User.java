package com.shbw.entity.system;

import java.io.Serializable;

import com.shbw.entity.Page;

/**
 * 用户实体类
 * @author Administrator
 *
 */
public class User implements  Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String uuid; // 用户id
	private String yhzh; // 用户账号
	private String yhmc; // 用户名称
	private String yhmm; // 用户密码
	private String xb; // 性别
	private String sjhm; // 手机号码
	private String zt; // 用户状态
	
	private Page page; // 分页对象
	
	
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getYhzh() {
		return yhzh;
	}
	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}
	public String getYhmc() {
		return yhmc;
	}
	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}
	public String getYhmm() {
		return yhmm;
	}
	public void setYhmm(String yhmm) {
		this.yhmm = yhmm;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getSjhm() {
		return sjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	
}
