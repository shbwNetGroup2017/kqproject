package net.bwjf.kpjk.domain.entities;

import java.io.Serializable;

public class KpjkExcel implements Serializable{
															
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String EXCELFILEBS=""; //excel�ļ���ʶ
	private String DJH=""; //���ݺ�
	private String GFMC="";//��������
	private String GFSBH="";//����˰��
	private String GFDZDH="";//������ַ�绰
	private String GFYHZH="";//���������ʺ�
	private String BZ="";//��ע
	private String FHR="";//������
	private String SKR="";//�տ���
	private String HWMC="";//��������
	private String JLDW="";//������λ
	private String GG=""; //���
	private String SPSL="";//����
	private String JE="";//���
	private String SL=""; //˰��
	private String DJ=""; //����
	//�����������ڷ�Ʊ����ҳ
	private String JE_BHS=""; //����˰��������ϸҳ
	private String DJ_BHS=""; //����˰���ۣ�������ϸҳ
	private String SE=""; //˰��
	private String ZJE=""; //�ܽ����Ǽ�˰�ϼ�
	private String MXTS=""; //��ϸ����
	private String JE_BHS_FPMX=""; //����˰���,���ڷ�Ʊ��ϸ
	//�û�����
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
