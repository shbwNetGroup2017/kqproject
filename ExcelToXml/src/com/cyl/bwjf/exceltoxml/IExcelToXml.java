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
 * 将用户标准格式的excel转换为规范的xml
 * @author cyl
 *
 */
public interface IExcelToXml {
	
	
	   /**
	    * 上传用户的excel
	    * @param request
	    * @param response
	    * @return 记录文件路劲、文件名称
	    */
    public Map<String, String> upload(HttpServletRequest request,HttpServletResponse response);
	
   /**
    * 构造符合规范xml的数据结构,适用于用户的excel是单表结构
    * @param excWb，客户的excel，符合约定的格式
    * @return
 * @throws IOException 
 * @throws FileNotFoundException 
 * @throws BiffException 
    */
   public String[][] getFpxxListFromDb(Workbook excWb) throws FileNotFoundException, IOException, BiffException; 

   /**
    * 构造符合规范的xml数据结构，适用于用户的excel是主附表格式
    * @param excWb
    * @return
    */
   public List getFpxxListFromZfb(Workbook excWb);
   
   /**
    * 根据解析到的数据集合，拼装目标xml串
    * @param FpxxList
    * @return xml字符串
    */
   public String getXmlStr(String[][] str,List rule);
   
   /**
    * 写xml文件
    * @param xmlStr
    * @return 写文件成败标志
    */
   public String writeXmlFile(String xmlStr,String FilePath);
   
}
