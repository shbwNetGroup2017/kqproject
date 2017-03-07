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
	 * excel文件转换成xml文件
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		jxl.Workbook readwb = null;
		// 调用方法，获取上传文件保存的路径
		Map<String, String> filePath = new HashMap<String,String>();
		//path.put("pt", "c:/excel.xls");
		// 创建根节点;
		Element root = new Element("sheet");
		// 将根节点添加到文档中；
		Document Doc = new Document(root);
		// 构建Workbook对象, 只读Workbook对象
		// 直接从本地文件创建Workbook
		InputStream instream = new FileInputStream(filePath.get("pt"));
		try {
			readwb = Workbook.getWorkbook(instream);
			// Sheet的下标是从0开始
			// 获取第一张Sheet表
			Sheet readsheet = readwb.getSheet(0);
			// 获取Sheet表中所包含的总列数
			int rsColumns = readsheet.getColumns();
			// 获取Sheet表中所包含的总行数
			int rsRows = readsheet.getRows();
			// 获取指定单元格的对象引用
			for (int i = 0; i < rsRows; i++) {
				Element elements = new Element("tr");
				for (int j = 0; j < rsColumns; j++) {
					Cell cell = readsheet.getCell(j, i);
					// str[i][j]=cell.getContents();//在此创建一个二维数组，获取单元格的数据
					// 生成xml文件
					elements.addContent(new Element("cell").setText(cell
							.getContents())); // 填写单元格的数据。
					root.addContent(elements.detach());
				}
			}
			Format format = Format.getPrettyFormat();
			XMLOutputter XMLOut = new XMLOutputter(format);
			XMLOut.output(Doc, new FileOutputStream("C:/Users/cyl/Desktop/books.xml"));
		} catch (BiffException e) {
			e.printStackTrace();
		}
		// 调用方法，删除上传文件保存
		//delFile(path.get("filePath"), path.get("name"));
	}

	/**
	 * 上传文件信息。
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
    * 刪除上傳的文件
    * @param filePath 文件路勁
    * @param fileName 文件名
    */
	public void delFile(String filePath, String fileName) {
		File file = new File(filePath, fileName);
		if (file.exists()) {
			file.delete();
		}
	}

}
