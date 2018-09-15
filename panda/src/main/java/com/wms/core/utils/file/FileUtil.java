package com.wms.core.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.wms.core.exceptions.WMSException;
import com.wms.core.utils.common.ObjectUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作工具类
 *
 * @author xb
 *
 */
public class FileUtil {
	
	private static Logger logger=LoggerFactory.getLogger(FileUtil.class);
	
	public static void appendFile(String fileName, String content) throws IOException {
			FileUtils.writeStringToFile(new File(fileName),content,"UTF-8",true);
	}

	public static boolean getExists(String fPath) {
		return ObjectUtils.isNotEmpty(FileUtils.getFile(fPath));
	}
    
	/**
	 * 新建目录
	 *
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static boolean newFolder(String folderPath) {
		var result = false;
		try {
			var filePath = folderPath;
			filePath = filePath.toString();
			var myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				result = myFilePath.mkdirs();
			}
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除文件
	 *
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) throws IOException{
			FileUtils.forceDelete(new File(filePathAndName));
	}

	/**
	 * 删除文件夹
	 *
	 * @param folderPath
	 *            String 文件夹路径及名称 如c:/fqf
	 *            String
	 * @return boolean
	 */
	public void delFolder(String folderPath) throws IOException{
			FileUtils.deleteDirectory(new File(folderPath));
	}

	/**
	 * 复制单个文件
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	@SuppressWarnings("resource")
	public void copyFile(String oldPath, String newPath) throws IOException {
		FileUtils.copyFile(new File(oldPath),new File(newPath));
	}

	/**
	 * 复制整个文件夹内容
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) throws IOException {
		FileUtils.copyDirectory(new File(oldPath),new File(newPath));
	}

	/**
	 * 移动文件到指定目录
	 *
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 * @throws IOException 
	 */
	public void moveFile(String oldPath, String newPath) throws IOException {
		try {
			copyFile(oldPath, newPath);
		} catch (IOException e) {
			LogFactory.getLog(this.getClass()).error(e.getMessage());
		}
		delFile(oldPath);
	}

	/**
	 * 移动文件到指定目录
	 *
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 * @throws IOException 
	 */
	public void moveFolder(String oldPath, String newPath) throws IOException {
			copyFolder(oldPath, newPath);
		   delFolder(oldPath);

	}

	public static boolean isPicFile(String ext) {
		var isJpg = ext.equalsIgnoreCase("jpg");
		var isJpeg = ext.equalsIgnoreCase("jpeg");
		var isPng = ext.equalsIgnoreCase("png");
		var isBmp = ext.equalsIgnoreCase("bmp");
		var isGif = ext.equalsIgnoreCase("gif");

		if (StringUtils.isEmpty(ext)) {
			return false;
		} else {
			if (isJpg || isJpeg || isPng || isBmp || isGif) {
				return true;
			}
		}
		return false;
	}

	public static String getFileExt(MultipartFile file) {
		var fileName = file.getOriginalFilename();
		if (fileName.indexOf(".") != -1) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return null;
	}

	public static String getMiniPath(String filePath) {
		if (filePath.indexOf(".") != -1) {
			var miniPath = filePath.substring(0, filePath.lastIndexOf("."));
			miniPath += "-mini."
					+ filePath.substring(filePath.lastIndexOf(".") + 1);
			return miniPath;
		}
		return null;
	}

	public static String getFileNameFromPath(String filePath) {
		var fileName = "";
		if (filePath.indexOf("\\") != -1) {
			fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
		} else if (filePath.indexOf("/") != -1) {
			fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		} else {
			fileName = filePath;
		}
		return fileName;
	}

	public static String lowerCaseFilePathExt(String filePath) {
		var result = new StringBuffer(filePath.substring(0,
				filePath.lastIndexOf(".") + 1));
		result.append(filePath.substring(filePath.lastIndexOf(".") + 1)
				.toLowerCase());
		return result.toString();
	}
	
	/**
	 * 读取文件内容
	 * @param file
	 * @return
	 * @throws WMSException
	 */
	public static String fileContentRead(File file) throws WMSException {
		Long filelength = file.length();
		var filecontent = new byte[filelength.intValue()];
		try {
			var in = new FileInputStream(file);
			in.read(filecontent);  
			return new String(filecontent);
		} catch (IOException e) {
			logger.debug("读取文件错误,{}",e);
			throw new WMSException("读取文件错误");
		}
	}
	
}
