package com.cyl.bwjf.exceltoxml;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Clob;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import net.bwjf.kpjk.bo.KpjkExcelService;
import net.bwjf.kpjk.domain.entities.KpjkExcel;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ExcToXmlAction extends ActionSupport {

	private static final long serialVersionUID = -2610697774274347898L;
	private File excelFile; // ��װ�ϴ��ļ��������
	private String excelContentType; // ��װ�ϴ��ļ����͵�����
	private String excelFileFileName; // ��װ�ϴ��ļ���������
	private String savePath; // ��������ע�������
	private KpjkExcelService kpjkExcelServiceImpl;
	private String EXCELFILEBS;
	private String DJH;
	private String ZJE;
	private String JE_BHS;
	private String SE;
    private String BZ;

	/**
	 * ����ת��xml���ݵ�ҳ��
	 */
	@Override
	public String execute() throws Exception {
		return "success";
	}

	String errorStr=""; //��Ԫ���鱨����Ϣ
	
	public String upfile() throws Exception {
		try {
		NumberFormat nf=NumberFormat.getPercentInstance();
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		Map<String, String> map = new HashMap<String, String>();
		map.put("fileName", getExcelFileFileName());
		//Map<String, String> map = uploadFile();// �ϴ��ļ�
		List<KpjkExcel> excelList = new ArrayList<KpjkExcel>();
		List<KpjkExcel> schlList = new ArrayList<KpjkExcel>(); // ���ɺ������ݿ���ļ�¼
		String wjBs = "";
		jxl.Workbook readwb = null;
		// ����Workbook����, ֻ��Workbook����
		// ֱ�Ӵӱ����ļ�����Workbook
		InputStream instream = new FileInputStream(getExcelFile());
		
			readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0);
			int rsRows = readsheet.getRows();
			int beginRow = 1; // �ӵ�N�п�ʼ��
			// ��ȡָ����Ԫ��Ķ�������
			//���ݼ��
			for (int i = beginRow; i < rsRows; i++) {
				    int flag=0;
				    String str="";
					for(int j=0;j<15;j++){
						if(readsheet.getCell(j, i).getContents().trim().equals("")){
							flag++;
						}
						//ÿ�е�Ԫ��Ĵ�����Ϣ
						str+=checkCell(readsheet.getCell(j, i).getContents(),j,i);
					}
					//ÿ�еĴ�����Ϣ
					System.out.println("flag....."+flag);
					if(flag!=15){
						this.errorStr+=str;
					}
			}
			System.out.println("ִ�С�������"+this.errorStr);
			//ͨ��У�飬��������ֵ
			if(this.errorStr.equals("")){
			for (int i = beginRow; i < rsRows; i++) {
					System.out.println(this.errorStr);
					KpjkExcel ke = new KpjkExcel();
				ke.setDJH(readsheet.getCell(0, i).getContents());
				ke.setGFMC(readsheet.getCell(1, i).getContents());
				ke.setGFSBH(readsheet.getCell(2, i).getContents());
				ke.setGFDZDH(readsheet.getCell(3, i).getContents());
				ke.setGFYHZH(readsheet.getCell(4, i).getContents());
				ke.setHWMC(readsheet.getCell(5, i).getContents());
				ke.setGG(readsheet.getCell(6, i).getContents());
				ke.setJLDW(readsheet.getCell(7, i).getContents());
				ke.setSPSL(readsheet.getCell(8, i).getContents());
				ke.setDJ(readsheet.getCell(9, i).getContents());
				ke.setJE(readsheet.getCell(10, i).getContents());
				if(readsheet.getCell(11, i).getContents().indexOf("%")>-1){
					ke.setSL(nf.parse(readsheet.getCell(11, i).getContents()).toString());
				}else{
					ke.setSL(readsheet.getCell(11, i).getContents());
				}
				ke.setBZ(readsheet.getCell(12, i).getContents());
				ke.setFHR(readsheet.getCell(13, i).getContents());
				ke.setSKR(readsheet.getCell(14, i).getContents());
				ke.setUser_dm(session.get("userName").toString());
				excelList.add(ke);
				//��������
				wjBs = kpjkExcelServiceImpl.saveExcel(excelList);
				System.out.println("wjBs....."+wjBs);
				KpjkExcel keCs = new KpjkExcel();
				keCs.setEXCELFILEBS(wjBs);
				schlList = kpjkExcelServiceImpl.selectByExcelID_HZ(keCs);
				session.put("wjBs", wjBs);
				session.put("fileName", map.get("fileName"));
				ServletActionContext.getRequest().setAttribute("schlList",
						schlList);
				}
				}else{//δͨ��У��
				   ServletActionContext.getRequest().setAttribute("errorStr",
						errorStr);
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
	public String fpHz(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		List<KpjkExcel> excelList = new ArrayList<KpjkExcel>();
		KpjkExcel keCs = new KpjkExcel();
		keCs.setEXCELFILEBS(session.get("wjBs").toString());
		excelList = kpjkExcelServiceImpl.selectByExcelID_HZ(keCs);
		ServletActionContext.getRequest().setAttribute("schlList",
				excelList);
		return "success";
	}
	
	public String getFPDetails(){
		List<KpjkExcel> excelList = new ArrayList<KpjkExcel>();
		KpjkExcel keCs = new KpjkExcel();
		keCs.setEXCELFILEBS(this.EXCELFILEBS);
		keCs.setDJH(this.DJH);
		System.out.println("DJH...."+this.DJH+"...EXCELFILEBS...."+this.EXCELFILEBS+"kpjkExcelServiceImpl"+kpjkExcelServiceImpl);
		excelList = kpjkExcelServiceImpl.selectFPDetails(keCs);
		ServletActionContext.getRequest().setAttribute("schlList",
				excelList);
		ServletActionContext.getRequest().setAttribute("ZJE",
				this.ZJE);
		ServletActionContext.getRequest().setAttribute("JE_BHS",
				this.JE_BHS);
		ServletActionContext.getRequest().setAttribute("SE",
				this.SE);
		ServletActionContext.getRequest().setAttribute("BZ",
				excelList.get(0).getBZ());
		System.out.println(this.ZJE+"...."+this.SE);
		return "success";
		
	}
    
	/**
	 * ��Ԫ���飬��һϵ�й���Ŀǰ�Ǳ�����ַ������ȼ�顢���ͼ�顢˰�ʼ��
	 * @return
	 * @throws IOException
	 */
	/**
	 * 
	 * @param cellStr ��Ԫ�ַ���
	 * @param checkKg ��鿪��,˳���Ǳ�����ַ������ȼ�顢���ͼ�顢˰�ʼ��,1000��ʾ��������
	 * @param cellXm ��Ԫ����Ŀ�����絥�ݺ�
	 * @param cellWz ��Ԫ��λ��
	 * @return
	 */
	private String checkCell(String cellStr,int colJ,int rowI){
		int celLenth=cellStr.length();
		String tempStr="";
		List<String> colList=new ArrayList<String>(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"));
		String wz=rowI+"��"+colList.get(colJ)+"��";
      if(colJ==0||colJ==1||colJ==5){//����Ϊ�գ��г��ȵ��ֶ�
    	  switch (colJ) {
		case 0://���ݺ�
			System.out.println("���ݺų��ȡ�����"+celLenth);
			if(celLenth==0){
				tempStr+=wz+"���ݺŲ���Ϊ�գ�";
			}
			else if(celLenth>30){
				tempStr+=wz+"���ݺų��Ȳ��ܳ���30���ַ���";
			}
			break;
		case 1://��������
			if(celLenth==0)
				tempStr+=wz+"�������Ʋ���Ϊ�գ�";
			else if(celLenth>100){
				tempStr+=wz+"�������Ƴ��Ȳ��ܳ���100���ַ���";
			}
			break;
		case 5://��������
			if(celLenth==0)
				tempStr+=wz+"�������Ʋ���Ϊ�գ�";
			else if(celLenth>100){
				tempStr+=wz+"�������Ƴ��Ȳ��ܳ���100���ַ���";
			}
			break;
		default:
			break;
		}  
      } else if(colJ==3||colJ==4||colJ==6||colJ==7||colJ==12||colJ==13||colJ==14){//��Ϊ�գ��г���Ҫ����ֶ�
    	  switch (colJ) {
		case 3://������ַ�绰
			if(celLenth>100)
				tempStr+=wz+"������ַ�绰���Ȳ��ܳ���100���ַ���";
			break;
		case 4://���������˺�
			if(celLenth>100)
				tempStr+=wz+"���������˺ų��Ȳ��ܳ���100���ַ���";
			break;
		case 6://����ͺ�
			if(celLenth>36)
				tempStr+=wz+"����ͺų��Ȳ��ܳ���36���ַ���";
			break;
		case 7://��λ
			if(celLenth>32)
				tempStr+=wz+"����ͺų��Ȳ��ܳ���32���ַ���";
			break;

		case 12://��ע
			if(celLenth>138)
				tempStr+=wz+"��ע���Ȳ��ܳ���138���ַ���";
			break;
		case 13://������
			if(celLenth>60)
				tempStr+=wz+"�����˳��Ȳ��ܳ���60���ַ���";
			break;
		case 14://�տ���
			if(celLenth>60)
				tempStr+=wz+"�տ��˳��Ȳ��ܳ���60���ַ���";
			break;

		default:
			break;
		}
      }else if(colJ==8||colJ==9||colJ==10){//��С���������
         	if(!isNumeric(cellStr)){
         		if(colJ==8)tempStr+=wz+"��������Ϊ���֣�";
         		if(colJ==9)tempStr+=wz+"��˰���۱���Ϊ���֣�";
         		if(colJ==10)tempStr+=wz+"��˰������Ϊ���֣�";
         	}
	   }else if(colJ==11){//˰�ʹ̶�ֵ
		   List<String> list=new ArrayList<String>(Arrays.asList("0","0.03","0.04","0.06","0.11","0.13","0.17","3%","4%","6%","11%","13%","17%"));
		   if(!list.contains(cellStr)){
			   tempStr+=wz+"˰�ʱ����Ƿ�����ֵ��";
		   }
	   }
	   return tempStr;
	}
	/**
	 * �ж��Ƿ������֣�����С��
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str){ 

		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str.trim().replace(".", ""));//ȥ��С����
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}

	
	@SuppressWarnings("rawtypes")
	public String scXml() throws IOException {
		ActionContext actionContext = ActionContext.getContext();
		try {
			Map session = actionContext.getSession();
			String wjBsStr = session.get("wjBs").toString();
			Clob xmlClob;
			String xmlStr;
			System.out.println("str=" + wjBsStr);
			List<Map> flagList = kpjkExcelServiceImpl.scxmlProc(wjBsStr);
			String flag = flagList.get(0).get("p_cgbz").toString();
			System.out.println("flag=" + flag);
			if (Integer.parseInt(flag) > 0) { // д��xml�ļ���
				KpjkExcel keCs = new KpjkExcel();
				keCs.setEXCELFILEBS(wjBsStr);
				List<Map> xmlMap = kpjkExcelServiceImpl.getXml(keCs);
				xmlClob = (Clob) xmlMap.get(0).get("XMLSTR");
				xmlStr = xmlClob.getSubString(1, (int) xmlClob.length());
				String fileName = "D:\\xmlTest.xml";
				File fa = new File(fileName);
				this.writeXmlFile(xmlStr, fa);

			}
			JSONObject json = JSONObject.fromObject(flag);
			flag = json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * �ϴ����ص�Excel�ļ�
	 */
	public Map<String, String> uploadFile() {
		Map<String, String> map = new HashMap<String, String>();
			map.put("fileName", getExcelFileFileName());
		return map;
	}

	/**
	 * �ر����������
	 * 
	 * @param fos
	 * @param fis
	 */
	private void close(FileOutputStream fos, FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				System.out.println("FileInputStream�ر�ʧ��");
				e.printStackTrace();
			}
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				System.out.println("FileOutputStream�ر�ʧ��");
				e.printStackTrace();
			}
		}
	}
  

   /**
    * ����xml�ļ��������ķ�ʽ���ء�
    * @return
    */
   public InputStream getXmlDl() {  
	   System.out.println("��ȡXML�ļ�");
	   String xmlStr=null;
	   ByteArrayInputStream tInputStringStream=null;
	   ActionContext actionContext = ActionContext.getContext();
		try {
			Map session = actionContext.getSession();
			String wjBsStr = session.get("wjBs").toString();
			Clob xmlClob;
			List<Map> flagList = kpjkExcelServiceImpl.scxmlProc(wjBsStr);
			String flag = flagList.get(0).get("p_cgbz").toString();
			if (Integer.parseInt(flag) > 0) { // д��xml�ļ���
				KpjkExcel keCs = new KpjkExcel();
				keCs.setEXCELFILEBS(wjBsStr);
				List<Map> xmlMap = kpjkExcelServiceImpl.getXml(keCs);
				xmlClob = (Clob) xmlMap.get(0).get("XMLSTR");
				//ת�����ݵ��ļ���
				xmlStr = xmlClob.getSubString(1, (int) xmlClob.length());
	            tInputStringStream = new ByteArrayInputStream(xmlStr.getBytes()); 
	            String name=session.get("fileName").toString();
	            String strName=name.substring(0,name.lastIndexOf("."));
	            strName=new String(strName.getBytes("GBK"),"8859_1");	            
	            this.fileName=strName+".xml";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		 return tInputStringStream;
   }  
	 
	/**
	 * ����String���ڵ�xml�ļ����ַ�������ȡ���ļ��У�������xml�ļ���
	 * �õ�һ���ļ������뵽FileOutputStream�У���ת��BufferedOutputStream�С�
	 * ��ͨ��FileInputStream��BufferedInputStream���ļ����뵽���С�
	 * @param content
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private boolean writeXmlFile(String content, File fileName)
			throws Exception {
		FileOutputStream fos = new FileOutputStream(fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(content.getBytes());
		bos.close();
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
		StringBuffer result = new StringBuffer();
		while (reader.ready()) {
			result.append((char) reader.read());
		}
		System.out.println(result.toString());
		reader.close();
		return true;
	}
    
	/**
	 * �ж�һ��·���ϵ��ļ��Ƿ���ڣ�����������򴴽��ļ���
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean createFile(File fileName) throws Exception {
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
    /**
     * ��clob�ļ�ת����String�ַ���
     * �õ�clob�ļ�����������ȡ��String�У��ٽ�Stringȡ����ֵ��StringBuffer��
     * ��StringBuffer����ת����String���͡�
     * @param clob
     * @return
     */
	private String ClobToString(Clob clob) {
		String reString = "";
		try {
			Reader is = clob.getCharacterStream();// �õ���
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (s != null) { // ִ��ѭ�����ַ���ȫ��ȡ����ֵ��StringBuffer��StringBufferת��STRING
				sb.append(s);
				s = br.readLine();
			}
			reString = sb.toString();
			if (br != null) {
				br.close();
			}
			if (is != null) {
				is.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return reString;
	}
	
    //ȫ�����Ե�getter��setter������

	public String getEXCELFILEBS() {
		return EXCELFILEBS;
	}


	public String getErrorStr() {
		return errorStr;
	}


	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}


	public void setEXCELFILEBS(String eXCELFILEBS) {
		EXCELFILEBS = eXCELFILEBS;
	}


	public String getDJH() {
		return DJH;
	}


	public void setDJH(String dJH) {
		DJH = dJH;
	}
	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public String getExcelContentType() {
		return excelContentType;
	}

	public void setExcelContentType(String excelContentType) {
		this.excelContentType = excelContentType;
	}

	public String getExcelFileFileName() {
		return excelFileFileName;
	}

	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}

	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath(savePath);
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	private String fileName;
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public KpjkExcelService getKpjkExcelServiceImpl() {
		return kpjkExcelServiceImpl;
	}

	public void setKpjkExcelServiceImpl(KpjkExcelService kpjkExcelServiceImpl) {
		this.kpjkExcelServiceImpl = kpjkExcelServiceImpl;
	}


	public String getZJE() {
		return ZJE;
	}


	public void setZJE(String zJE) {
		ZJE = zJE;
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

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String bZ) {
		BZ = bZ;
	}
}