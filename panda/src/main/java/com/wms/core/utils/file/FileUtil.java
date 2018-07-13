package com.wms.core.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wms.core.exceptions.WMSException;
import com.wms.core.utils.common.ObjectUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
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
		boolean result = false;
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
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

	/**
	 * 创建远程服务上的文件
	 *
	 * @param request
	 * @throws IOException
	 */
	public void createHTML(HttpServletRequest request) throws IOException {
		java.io.InputStream l_urlStream;

		java.net.URL l_url = new java.net.URL(
				"http://localhost:1000/chinavvvcms/luohe.jsp");
		java.net.HttpURLConnection l_connection = (java.net.HttpURLConnection) l_url
				.openConnection();
		l_connection.connect();

		l_urlStream = l_connection.getInputStream();
		java.io.BufferedReader l_reader = null;
		OutputStreamWriter fw = null;
		try {
			l_reader = new java.io.BufferedReader(
					new java.io.InputStreamReader(l_urlStream,"UTF-8"));
			String path = request.getSession().getServletContext().getRealPath(".");
			fw = new OutputStreamWriter(new FileOutputStream(path + "\\index.html"), "UTF-8");

			String Line = l_reader.readLine();// 从文件读取一行字符串
			StringBuffer content = new StringBuffer();
			// 判断读取到的字符串是否不为空
			while (Line != null) {
				Line = Line.trim();
				content.append(Line);
				System.out.println(Line);// 输出从文件中读取的数据
				fw.write(Line);
				// out.println(content.toString());//输出从文件中读取的数据
				// fw.write(content.toString());
				Line = l_reader.readLine();// 从文件中继续读取一行数据
			}
		}catch (Exception ex){
			LogFactory.getLog(this.getClass()).error(ex.getMessage());
		}finally {
			if(null!=l_reader) {
				l_reader.close();// 关闭BufferedReader对象
			}
			if(null!=fw) {
				fw.close();
			}
		}
	}

	public static boolean isPicFile(String ext) {
		boolean isJpg = ext.equalsIgnoreCase("jpg");
		boolean isJpeg = ext.equalsIgnoreCase("jpeg");
		boolean isPng = ext.equalsIgnoreCase("png");
		boolean isBmp = ext.equalsIgnoreCase("bmp");
		boolean isGif = ext.equalsIgnoreCase("gif");

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
		String fileName = file.getOriginalFilename();
		if (fileName.indexOf(".") != -1) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return null;
	}

	public static String getMiniPath(String filePath) {
		if (filePath.indexOf(".") != -1) {
			String miniPath = filePath.substring(0, filePath.lastIndexOf("."));
			miniPath += "-mini."
					+ filePath.substring(filePath.lastIndexOf(".") + 1);
			return miniPath;
		}
		return null;
	}

	public static String getFileNameFromPath(String filePath) {
		String fileName = "";
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
		StringBuffer result = new StringBuffer(filePath.substring(0,
				filePath.lastIndexOf(".") + 1));
		result.append(filePath.substring(filePath.lastIndexOf(".") + 1)
				.toLowerCase());
		return result.toString();
	}
	
	/**
	 * 读取文件内容
	 * @param filePath
	 * @return
	 * @throws WMSException
	 */
	public static String fileContentRead(File file) throws WMSException {
		Long filelength = file.length();  
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);  
			in.read(filecontent);  
			return new String(filecontent);
		} catch (IOException e) {
			logger.debug("读取文件错误,{}",e);
			throw new WMSException("读取文件错误");
		}
	}

	public static String processFileName(HttpServletRequest request, String fileNames) {
		String codedfilename = null;
		try {
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
					&& -1 != agent.indexOf("Trident")) {// ie

				String name = java.net.URLEncoder.encode(fileNames, "UTF8");

				codedfilename = name;
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等


				codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codedfilename;
	}

//	public static void main(String[] args) {
//		 String onestr="sampleid,campaignid,accepthone,phone1,phone2,phone3,"
//				+"hangflag,dellflag,appointflag,updatesequence,lockno,isvalid,isallot,"
//				 +"importtime,extractseq, dealstaff\r\n";
//		List<String> simples = new ArrayList<String>();
//		simples.add(onestr.toString());
//		simples.add(onestr.toString());
//		simples.add(onestr.toString());
//		String path="E:\\tmp\\ss";
//		String  filename ="tmp.txt";
////		try {
////			newFolder(path);
////			File file = new File(path+"\\"+filename);
////			FileUtil.creatTxtFile(file);
//		long begin = System.currentTimeMillis();
////			FileUtil.appendFile(path+"\\"+filename,onestr);
//		long end = System.currentTimeMillis();
//			System.out.println(end-begin);
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//
//
//	}
	
}
