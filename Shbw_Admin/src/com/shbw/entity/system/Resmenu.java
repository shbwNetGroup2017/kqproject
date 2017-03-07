package com.shbw.entity.system;

import java.io.Serializable;

public class Resmenu implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;   //主键ID
	private String zymc; 
	private String zylj;
	private String zyxh;
	private String zyms;
	private String zt;
	private String creator;
	private String create_date;
	private String updator;
	private String update_date;
	private String del_flag;
	private String remark;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getZymc() {
		return zymc;
	}
	public void setZymc(String zymc) {
		this.zymc = zymc;
	}
	public String getZylj() {
		return zylj;
	}
	public void setZylj(String zylj) {
		this.zylj = zylj;
	}
	public String getZyxh() {
		return zyxh;
	}
	public void setZyxh(String zyxh) {
		this.zyxh = zyxh;
	}
	public String getZyms() {
		return zyms;
	}
	public void setZyms(String zyms) {
		this.zyms = zyms;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
