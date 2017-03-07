package com.cyl.bwjf.exceltoxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class XmlDownLoad extends ActionSupport {

	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5255353234558139156L;

	@Override
	public String execute() throws Exception {
		return super.execute();
	}
	
	   /**
	    * 下载xml文件，资源下载。
	    * @return
	    */
	   public InputStream getZyDl() { 
		   String filePath="";
		   InputStream tInputStringStream=null;
			try {
				   System.out.println("fileName..."+this.fileName);
				   if(this.fileName.equals("csData")){
					   filePath=ServletActionContext.getServletContext().getRealPath("/upload/test1.xls");
					   this.fileName="test1.xls";
				   }
				   else if(this.fileName.equals("csMB")){
					   filePath=ServletActionContext.getServletContext().getRealPath("/upload/百旺简版开票接口模板.xls");
					   this.fileName="百旺简版开票接口模板.xls";
				   }
				   else {
					   filePath=ServletActionContext.getServletContext().getRealPath("/upload/百旺简版开票接口操作手册.doc");
					   this.fileName="百旺简版开票操作手册.doc";
				   }
				   this.fileName=new String(fileName.getBytes("GBK"),"8859_1");
		           tInputStringStream = new FileInputStream(new File(filePath)); 
			} catch (Exception e) {
				e.printStackTrace();
			} 
			 return tInputStringStream;
	   }

	
}
