package net.bwjf.kpjk.domain.entities;

public class User {
	private String USERNAME;
	private String PASSWORD;
	private String GSMC;
	private String SH;
	private String ip;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
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
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	
	

}
