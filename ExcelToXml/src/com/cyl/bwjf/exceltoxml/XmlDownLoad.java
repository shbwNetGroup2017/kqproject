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
	    * ����xml�ļ�����Դ���ء�
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
					   filePath=ServletActionContext.getServletContext().getRealPath("/upload/������濪Ʊ�ӿ�ģ��.xls");
					   this.fileName="������濪Ʊ�ӿ�ģ��.xls";
				   }
				   else {
					   filePath=ServletActionContext.getServletContext().getRealPath("/upload/������濪Ʊ�ӿڲ����ֲ�.doc");
					   this.fileName="������濪Ʊ�����ֲ�.doc";
				   }
				   this.fileName=new String(fileName.getBytes("GBK"),"8859_1");
		           tInputStringStream = new FileInputStream(new File(filePath)); 
			} catch (Exception e) {
				e.printStackTrace();
			} 
			 return tInputStringStream;
	   }

	
}
