package com.cyl.bwjf.exceltoxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ExcelToXml extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * excel�ļ�ת����xml�ļ�
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		jxl.Workbook readwb = null;
		// ���÷�������ȡ�ϴ��ļ������·��
		Map<String, String> filePath = new HashMap<String,String>();
		//path.put("pt", "c:/excel.xls");
		// �������ڵ�;
		Element root = new Element("sheet");
		// �����ڵ���ӵ��ĵ��У�
		Document Doc = new Document(root);
		// ����Workbook����, ֻ��Workbook����
		// ֱ�Ӵӱ����ļ�����Workbook
		InputStream instream = new FileInputStream(filePath.get("pt"));
		try {
			readwb = Workbook.getWorkbook(instream);
			// Sheet���±��Ǵ�0��ʼ
			// ��ȡ��һ��Sheet��
			Sheet readsheet = readwb.getSheet(0);
			// ��ȡSheet������������������
			int rsColumns = readsheet.getColumns();
			// ��ȡSheet������������������
			int rsRows = readsheet.getRows();
			// ��ȡָ����Ԫ��Ķ�������
			for (int i = 0; i < rsRows; i++) {
				Element elements = new Element("tr");
				for (int j = 0; j < rsColumns; j++) {
					Cell cell = readsheet.getCell(j, i);
					// str[i][j]=cell.getContents();//�ڴ˴���һ����ά���飬��ȡ��Ԫ�������
					// ����xml�ļ�
					elements.addContent(new Element("cell").setText(cell
							.getContents())); // ��д��Ԫ������ݡ�
					root.addContent(elements.detach());
				}
			}
			Format format = Format.getPrettyFormat();
			XMLOutputter XMLOut = new XMLOutputter(format);
			XMLOut.output(Doc, new FileOutputStream("C:/Users/cyl/Desktop/books.xml"));
		} catch (BiffException e) {
			e.printStackTrace();
		}
		// ���÷�����ɾ���ϴ��ļ�����
		//delFile(path.get("filePath"), path.get("name"));
	}

	/**
	 * �ϴ��ļ���Ϣ��
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, String> upload(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
						path.put("name", fileName);
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
    * �h���ς����ļ�
    * @param filePath �ļ�·��
    * @param fileName �ļ���
    */
	public void delFile(String filePath, String fileName) {
		File file = new File(filePath, fileName);
		if (file.exists()) {
			file.delete();
		}
	}

}
