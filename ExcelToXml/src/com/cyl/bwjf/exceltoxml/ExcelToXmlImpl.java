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
	 * �ϴ�Excel�ļ�
	 */
	@Override
	public Map<String, String> upload(HttpServletRequest request,
			HttpServletResponse response) {
		// �ж��ύ�����ı��Ƿ�Ϊ�ļ��ϴ��˵�
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		Map<String, String> path = new HashMap<String, String>();
		if (isMultipart) {
			// ����һ���ļ��ϴ��������
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			Iterator items;
			try {
				// ���������ύ�������ļ�����
				items = upload.parseRequest(request).iterator();
				while (items.hasNext()) {
					FileItem item = (FileItem) items.next();
					if (!item.isFormField()) {
						// ȡ���ϴ��ļ����ļ�����
						String name = item.getName();
						// ȡ���ϴ��ļ��Ժ�Ĵ洢·��
						String fileName = name.substring(
								name.lastIndexOf('\\') + 1, name.length());
						// �ϴ��ļ��Ժ�Ĵ洢·��
						String pt = "c:" + File.separatorChar + fileName;
						// �ϴ��ļ�
						File uploaderFile = new File(pt);
						item.write(uploaderFile);
						// ��ӡ�ϴ��ɹ���Ϣ
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
      * �������ݵ����
     * @throws IOException 
     * @throws FileNotFoundException 
      */
	@SuppressWarnings("null")
	@Override
	public String[][] getFpxxListFromDb(Workbook excWb) throws FileNotFoundException, IOException, BiffException {
		String[][] str=null;
		// Sheet���±��Ǵ�0��ʼ
		// ��ȡ��һ��Sheet��    ��������
		Sheet readsheet = excWb.getSheet(0);
		// ��ȡSheet������������������
		int rsColumns = readsheet.getColumns();
		// ��ȡSheet������������������
		int rsRows = readsheet.getRows();
		// ��ȡָ����Ԫ��Ķ�������
		for (int i = 0; i < rsRows; i++) {
			for (int j = 0; j < rsColumns; j++) {
				Cell cell = readsheet.getCell(j, i);
				str[i][j]=cell.getContents();//�ڴ˴���һ����ά���飬��ȡ�к��еĵ�Ԫ�������
			}
		}
		return str;
	}

	@Override
	public List getFpxxListFromZfb(Workbook excWb) {
		List<List> list0=new ArrayList<List>();//������list
		List<Object> list1=new ArrayList<Object>();//�м���list
		List<List> list2=new ArrayList<List>();//������list
		
		
		
		
		
		list1.add(list2);
		list0.add(list1);
		return list0;
	}
	
    /**
     * ����list����Ϣƴװ��Xml���ַ���
     */
	@Override
	public String getXmlStr(String[][] str,List rule) {
		
		
		
		
		
		return null;
	}
    
	/**
	 * ���xml�ļ�
	 */
	@Override
	public String writeXmlFile(String xmlStr, String FilePath) {
		
		return null;
	}

}
