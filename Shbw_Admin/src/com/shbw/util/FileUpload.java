package com.shbw.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件 创建人：FH 创建时间：2014年12月23日
 * 
 * @version
 */
public class FileUpload {

	/**
	 * @param file
	 *            //文件对象
	 * @param filePath
	 *            //上传路径
	 * @param fileName
	 *            //文件名
	 * @return 文件名
	 */
	public static String fileUp(MultipartFile file, String filePath, String fileName) {
		String extName = ""; // 文件扩展名格式：
		try {
			if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
				extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			}
			copyFile(file.getInputStream(), filePath, fileName + extName).replaceAll("-", "");
		} catch (IOException e) {
			System.out.println(e);
		}
		return fileName + extName;
	}

	/**
	 * 写文件到当前目录的upload目录中
	 * 
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	private static String copyFile(InputStream in, String dir, String realName) throws IOException {
		File file = new File(dir, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		FileUtils.copyInputStreamToFile(in, file);
		return realName;
	}

	/**
	 * add by Aiwen at 2017-02-21 文件上传方法,穿入页面上传的MultipartFile 对象和要上传到的目的目录
	 * 
	 * @param file
	 * @param dir
	 * @return 新文件名
	 */
	public String fileUpload(MultipartFile file, String dir) {
		String path = null;
		if (file.getSize() > 0) {
			// 获取文件名
			String fileName = file.getOriginalFilename();
			fileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf('.'));// 取时间戳加文件后缀名作为上传文件的新文件名
			path = dir + fileName;// "D:/kqxm/" + fileName;
			System.out.println(" file path:" + path);
			try {
				File f = new File(path);
				InputStream in = file.getInputStream();
				OutputStream os = new FileOutputStream(f);
				byte buffer[] = new byte[1024];
				// 判断输入流中的数据是否已经读完的标识
				int len = 0;
				// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
				while ((len = in.read(buffer)) > 0) {
					// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" +
					// filename)当中
					os.write(buffer, 0, len);
				}
				in.close();
				os.close();
			} catch (FileNotFoundException e1) {
				//
				e1.printStackTrace();
			} catch (IOException e) {
				//
				e.printStackTrace();
			}
		}
		return path;
	}
	// end by Aiwen at 2017-02-21
}
