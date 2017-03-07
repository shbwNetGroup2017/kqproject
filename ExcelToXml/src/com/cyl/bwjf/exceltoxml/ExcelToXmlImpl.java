package com.cyl.bwjf.exceltoxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelToXmlImpl implements IExcelToXml {

	/**
	 * 上传Excel文件
	 */
	@Override
	public Map<String, String> upload(HttpServletRequest request,
			HttpServletResponse response) {
		// 判断提交过来的表单是否为文件上传菜单
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		Map<String, String> path = new HashMap<String, String>();
		if (isMultipart) {
			// 构造一个文件上传处理对象
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			Iterator items;
			try {
				// 解析表单中提交的所有文件内容
				items = upload.parseRequest(request).iterator();
				while (items.hasNext()) {
					FileItem item = (FileItem) items.next();
					if (!item.isFormField()) {
						// 取出上传文件的文件名称
						String name = item.getName();
						// 取得上传文件以后的存储路径
						String fileName = name.substring(
								name.lastIndexOf('\\') + 1, name.length());
						// 上传文件以后的存储路径
						String pt = "c:" + File.separatorChar + fileName;
						// 上传文件
						File uploaderFile = new File(pt);
						item.write(uploaderFile);
						// 打印上传成功信息
						response.setContentType("text/html");
						response.setCharacterEncoding("GB2312");
						path.put("pt", pt);
						path.put("fileName", fileName);
						path.put("filePath", "d:" + File.separatorChar);
					}
				}
				return path;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
     /**
      * 单表数据的设计
     * @throws IOException 
     * @throws FileNotFoundException 
      */
	@SuppressWarnings("null")
	@Override
	public String[][] getFpxxListFromDb(Workbook excWb) throws FileNotFoundException, IOException, BiffException {
		String[][] str=null;
		// Sheet的下标是从0开始
		// 获取第一张Sheet表    单表适用
		Sheet readsheet = excWb.getSheet(0);
		// 获取Sheet表中所包含的总列数
		int rsColumns = readsheet.getColumns();
		// 获取Sheet表中所包含的总行数
		int rsRows = readsheet.getRows();
		// 获取指定单元格的对象引用
		for (int i = 0; i < rsRows; i++) {
			for (int j = 0; j < rsColumns; j++) {
				Cell cell = readsheet.getCell(j, i);
				str[i][j]=cell.getContents();//在此创建一个二维数组，获取行和列的单元格的数据
			}
		}
		return str;
	}

	@Override
	public List getFpxxListFromZfb(Workbook excWb) {
		List<List> list0=new ArrayList<List>();//最外层的list
		List<Object> list1=new ArrayList<Object>();//中间层的list
		List<List> list2=new ArrayList<List>();//最里层的list
		
		
		
		
		
		list1.add(list2);
		list0.add(list1);
		return list0;
	}
	
    /**
     * 根据list的信息拼装成Xml的字符串
     */
	@Override
	public String getXmlStr(String[][] str,List rule) {
		
		
		
		
		
		return null;
	}
    
	/**
	 * 输出xml文件
	 */
	@Override
	public String writeXmlFile(String xmlStr, String FilePath) {
		
		return null;
	}

}
