package com.cyl.bwjf.exceltoxml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * ���û���׼��ʽ��excelת��Ϊ�淶��xml
 * @author cyl
 *
 */
public interface IExcelToXml {
	
	
	   /**
	    * �ϴ��û���excel
	    * @param request
	    * @param response
	    * @return ��¼�ļ�·�����ļ�����
	    */
    public Map<String, String> upload(HttpServletRequest request,HttpServletResponse response);
	
   /**
    * ������Ϲ淶xml�����ݽṹ,�������û���excel�ǵ���ṹ
    * @param excWb���ͻ���excel������Լ���ĸ�ʽ
    * @return
 * @throws IOException 
 * @throws FileNotFoundException 
 * @throws BiffException 
    */
   public String[][] getFpxxListFromDb(Workbook excWb) throws FileNotFoundException, IOException, BiffException; 

   /**
    * ������Ϲ淶��xml���ݽṹ���������û���excel���������ʽ
    * @param excWb
    * @return
    */
   public List getFpxxListFromZfb(Workbook excWb);
   
   /**
    * ���ݽ����������ݼ��ϣ�ƴװĿ��xml��
    * @param FpxxList
    * @return xml�ַ���
    */
   public String getXmlStr(String[][] str,List rule);
   
   /**
    * дxml�ļ�
    * @param xmlStr
    * @return д�ļ��ɰܱ�־
    */
   public String writeXmlFile(String xmlStr,String FilePath);
   
}
