package com.shbw.entity.system;

import java.io.Serializable;
import java.util.List;

/**
 * 系统菜单实体类
 */
public class Menu implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;   //主键ID
	private String zymc; 
	private String zylj;
	private String zyxh;

	private List<Menu> subMenu;
	private boolean hasMenu = false;  //是否有子菜单
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
	public List<Menu> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	public boolean isHasMenu() {
		return hasMenu;
	}
	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}

}
