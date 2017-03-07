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
	private File excelFile; // 封装上传文件域的属性
	private String excelContentType; // 封装上传文件类型的属性
	private String excelFileFileName; // 封装上传文件名的属性
	private String savePath; // 接受依赖注入的属性
	private KpjkExcelService kpjkExcelServiceImpl;
	private String EXCELFILEBS;
	private String DJH;
	private String ZJE;
	private String JE_BHS;
	private String SE;
    private String BZ;

	/**
	 * 进入转换xml数据的页面
	 */
	@Override
	public String execute() throws Exception {
		return "success";
	}

	String errorStr=""; //单元格检查报错信息
	
	public String upfile() throws Exception {
		try {
		NumberFormat nf=NumberFormat.getPercentInstance();
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		Map<String, String> map = new HashMap<String, String>();
		map.put("fileName", getExcelFileFileName());
		//Map<String, String> map = uploadFile();// 上传文件
		List<KpjkExcel> excelList = new ArrayList<KpjkExcel>();
		List<KpjkExcel> schlList = new ArrayList<KpjkExcel>(); // 生成后在数据库里的记录
		String wjBs = "";
		jxl.Workbook readwb = null;
		// 构建Workbook对象, 只读Workbook对象
		// 直接从本地文件创建Workbook
		InputStream instream = new FileInputStream(getExcelFile());
		
			readwb = Workbook.getWorkbook(instream);
			Sheet readsheet = readwb.getSheet(0);
			int rsRows = readsheet.getRows();
			int beginRow = 1; // 从第N行开始读
			// 获取指定单元格的对象引用
			//数据检查
			for (int i = beginRow; i < rsRows; i++) {
				    int flag=0;
				    String str="";
					for(int j=0;j<15;j++){
						if(readsheet.getCell(j, i).getContents().trim().equals("")){
							flag++;
						}
						//每行单元格的错误信息
						str+=checkCell(readsheet.getCell(j, i).getContents(),j,i);
					}
					//每行的错误信息
					System.out.println("flag....."+flag);
					if(flag!=15){
						this.errorStr+=str;
					}
			}
			System.out.println("执行。。。。"+this.errorStr);
			//通过校验，设置属性值
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
				//保存数据
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
				}else{//未通过校验
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
	 * 单元框检查，有一系列规则，目前是必填项、字符串长度检查、类型检查、税率检查
	 * @return
	 * @throws IOException
	 */
	/**
	 * 
	 * @param cellStr 单元字符串
	 * @param checkKg 检查开关,顺序是必填项、字符串长度检查、类型检查、税率检查,1000表示检查必填项
	 * @param cellXm 单元格项目，比如单据号
	 * @param cellWz 单元格位置
	 * @return
	 */
	private String checkCell(String cellStr,int colJ,int rowI){
		int celLenth=cellStr.length();
		String tempStr="";
		List<String> colList=new ArrayList<String>(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"));
		String wz=rowI+"行"+colList.get(colJ)+"列";
      if(colJ==0||colJ==1||colJ==5){//不能为空，有长度的字段
    	  switch (colJ) {
		case 0://单据号
			System.out.println("单据号长度。。。"+celLenth);
			if(celLenth==0){
				tempStr+=wz+"单据号不能为空！";
			}
			else if(celLenth>30){
				tempStr+=wz+"单据号长度不能超过30个字符！";
			}
			break;
		case 1://购方名称
			if(celLenth==0)
				tempStr+=wz+"购方名称不能为空！";
			else if(celLenth>100){
				tempStr+=wz+"购方名称长度不能超过100个字符！";
			}
			break;
		case 5://货物名称
			if(celLenth==0)
				tempStr+=wz+"货物名称不能为空！";
			else if(celLenth>100){
				tempStr+=wz+"货物名称长度不能超过100个字符！";
			}
			break;
		default:
			break;
		}  
      } else if(colJ==3||colJ==4||colJ==6||colJ==7||colJ==12||colJ==13||colJ==14){//可为空，有长度要求的字段
    	  switch (colJ) {
		case 3://购方地址电话
			if(celLenth>100)
				tempStr+=wz+"购方地址电话长度不能超过100个字符！";
			break;
		case 4://购方银行账号
			if(celLenth>100)
				tempStr+=wz+"购方银行账号长度不能超过100个字符！";
			break;
		case 6://规格型号
			if(celLenth>36)
				tempStr+=wz+"规格型号长度不能超过36个字符！";
			break;
		case 7://单位
			if(celLenth>32)
				tempStr+=wz+"规格型号长度不能超过32个字符！";
			break;

		case 12://备注
			if(celLenth>138)
				tempStr+=wz+"备注长度不能超过138个字符！";
			break;
		case 13://复核人
			if(celLenth>60)
				tempStr+=wz+"复核人长度不能超过60个字符！";
			break;
		case 14://收款人
			if(celLenth>60)
				tempStr+=wz+"收款人长度不能超过60个字符！";
			break;

		default:
			break;
		}
      }else if(colJ==8||colJ==9||colJ==10){//带小数点的数字
         	if(!isNumeric(cellStr)){
         		if(colJ==8)tempStr+=wz+"数量必须为数字！";
         		if(colJ==9)tempStr+=wz+"含税单价必须为数字！";
         		if(colJ==10)tempStr+=wz+"含税金额必须为数字！";
         	}
	   }else if(colJ==11){//税率固定值
		   List<String> list=new ArrayList<String>(Arrays.asList("0","0.03","0.04","0.06","0.11","0.13","0.17","3%","4%","6%","11%","13%","17%"));
		   if(!list.contains(cellStr)){
			   tempStr+=wz+"税率必须是法定数值！";
		   }
	   }
	   return tempStr;
	}
	/**
	 * 判断是否是数字，包括小数
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str){ 

		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str.trim().replace(".", ""));//去掉小数点
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
			if (Integer.parseInt(flag) > 0) { // 写到xml文件中
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
	 * 上传本地的Excel文件
	 */
	public Map<String, String> uploadFile() {
		Map<String, String> map = new HashMap<String, String>();
			map.put("fileName", getExcelFileFileName());
		return map;
	}

	/**
	 * 关闭输入输出流
	 * 
	 * @param fos
	 * @param fis
	 */
	private void close(FileOutputStream fos, FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				System.out.println("FileInputStream关闭失败");
				e.printStackTrace();
			}
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				System.out.println("FileOutputStream关闭失败");
				e.printStackTrace();
			}
		}
	}
  

   /**
    * 下载xml文件，以留的方式返回。
    * @return
    */
   public InputStream getXmlDl() {  
	   System.out.println("获取XML文件");
	   String xmlStr=null;
	   ByteArrayInputStream tInputStringStream=null;
	   ActionContext actionContext = ActionContext.getContext();
		try {
			Map session = actionContext.getSession();
			String wjBsStr = session.get("wjBs").toString();
			Clob xmlClob;
			List<Map> flagList = kpjkExcelServiceImpl.scxmlProc(wjBsStr);
			String flag = flagList.get(0).get("p_cgbz").toString();
			if (Integer.parseInt(flag) > 0) { // 写到xml文件中
				KpjkExcel keCs = new KpjkExcel();
				keCs.setEXCELFILEBS(wjBsStr);
				List<Map> xmlMap = kpjkExcelServiceImpl.getXml(keCs);
				xmlClob = (Clob) xmlMap.get(0).get("XMLSTR");
				//转换数据的文件名
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
	 * 将以String存在的xml文件的字符串，读取到文件中，并生成xml文件。
	 * 得到一个文件，放入到FileOutputStream中，再转到BufferedOutputStream中。
	 * 再通过FileInputStream和BufferedInputStream把文件输入到其中。
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
	 * 判断一个路径上的文件是否存在，如果不存在则创建文件。
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
     * 将clob文件转换成String字符串
     * 得到clob文件的流，并读取到String中，再将String取出赋值给StringBuffer，
     * 由StringBuffer管理，转换成String类型。
     * @param clob
     * @return
     */
	private String ClobToString(Clob clob) {
		String reString = "";
		try {
			Reader is = clob.getCharacterStream();// 得到流
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (s != null) { // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
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
	
    //全局属性的getter和setter方法。

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