package net.bwjf.kpjk.domain.entities;

import java.io.Serializable;

public class KpjkExcel implements Serializable{
															
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String EXCELFILEBS=""; //excel文件标识
	private String DJH=""; //单据号
	private String GFMC="";//购方名称
	private String GFSBH="";//购方税号
	private String GFDZDH="";//购方地址电话
	private String GFYHZH="";//购方银行帐号
	private String BZ="";//备注
	private String FHR="";//复核人
	private String SKR="";//收款人
	private String HWMC="";//货物名称
	private String JLDW="";//计量单位
	private String GG=""; //规格
	private String SPSL="";//数量
	private String JE="";//金额
	private String SL=""; //税率
	private String DJ=""; //单价
	//以下属性用于发票汇总页
	private String JE_BHS=""; //不含税金额，用于明细页
	private String DJ_BHS=""; //不含税单价，用于明细页
	private String SE=""; //税额
	private String ZJE=""; //总金额，就是价税合计
	private String MXTS=""; //明细条数
	private String JE_BHS_FPMX=""; //不含税金额,用于发票明细
	//用户代码
	private String user_dm;
	
	public String getUser_dm() {
		return user_dm;
	}
	public void setUser_dm(String user_dm) {
		this.user_dm = user_dm;
	}
	public String getDJ_BHS() {
		return DJ_BHS;
	}
	public void setDJ_BHS(String dJ_BHS) {
		DJ_BHS = dJ_BHS;
	}
	public String getJE_BHS_FPMX() {
		return JE_BHS_FPMX;
	}
	public void setJE_BHS_FPMX(String jE_BHS_FPMX) {
		JE_BHS_FPMX = jE_BHS_FPMX;
	}
	public String getMXTS() {
		return MXTS;
	}
	public void setMXTS(String mXTS) {
		MXTS = mXTS;
	}
	public String getJE_BHS() {
		return JE_BHS;
	}
	public void setJE_BHS(String jE_BHS) {
		JE_BHS = jE_BHS;
	}
	public String getSE() {
		return SE;
	}
	public void setSE(String sE) {
		SE = sE;
	}
	public String getZJE() {
		return ZJE;
	}
	public void setZJE(String zJE) {
		ZJE = zJE;
	}
	public String getEXCELFILEBS() {
		return EXCELFILEBS;
	}
	public void setEXCELFILEBS(String eXCELFILEBS) {
		EXCELFILEBS = eXCELFILEBS;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDJH() {
		return DJH;
	}
	public void setDJH(String dJH) {
		DJH = dJH;
	}
	public String getGFMC() {
		return GFMC;
	}
	public void setGFMC(String gFMC) {
		GFMC = gFMC;
	}
	public String getGFSBH() {
		return GFSBH;
	}
	public void setGFSBH(String gFSBH) {
		GFSBH = gFSBH;
	}
	public String getGFDZDH() {
		return GFDZDH;
	}
	public void setGFDZDH(String gFDZDH) {
		GFDZDH = gFDZDH;
	}
	public String getGFYHZH() {
		return GFYHZH;
	}
	public void setGFYHZH(String gFYHZH) {
		GFYHZH = gFYHZH;
	}
	public String getBZ() {
		return BZ;
	}
	public void setBZ(String bZ) {
		BZ = bZ;
	}
	public String getFHR() {
		return FHR;
	}
	public void setFHR(String fHR) {
		FHR = fHR;
	}
	public String getSKR() {
		return SKR;
	}
	public void setSKR(String sKR) {
		SKR = sKR;
	}
	public String getHWMC() {
		return HWMC;
	}
	public void setHWMC(String hWMC) {
		HWMC = hWMC;
	}
	public String getJLDW() {
		return JLDW;
	}
	public void setJLDW(String jLDW) {
		JLDW = jLDW;
	}
	public String getGG() {
		return GG;
	}
	public void setGG(String gG) {
		GG = gG;
	}
	public String getSPSL() {
		return SPSL;
	}
	public void setSPSL(String sPSL) {
		SPSL = sPSL;
	}
	public String getJE() {
		return JE;
	}
	public void setJE(String jE) {
		JE = jE;
	}
	public String getSL() {
		return SL;
	}
	public void setSL(String sL) {
		SL = sL;
	}
	public String getDJ() {
		return DJ;
	}
	public void setDJ(String dJ) {
		DJ = dJ;
	}
	
}
