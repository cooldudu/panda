package com.wms.core.utils.file;

import java.io.File;

/**
 * 文件地址分隔符,自动区分windows操作系统和linux操作系统
 *
 * @author xb
 *
 */
public class PathType {
	public static String getPathType() {
		var result = "";

		var osName = System.getProperty("os.name");
		osName = osName.substring(0, 1);
		if (osName.equals("W")) {
			result = "\\";
		} else
			result = "/";
		return result;
	}

	public static boolean deleteDirFile(File file) {
		var result = false;
		if (file.isDirectory()) {
			var array = file.listFiles();
			if (null != array) {
				for (var i = 0; i < array.length; i++) // 删除目录下的子目录
				{
					deleteDirFile(array[i]);
				}
			}
			// 当目录下的所有删除后，再把本目录删除
			// System.out.println("fileDir="+file.getAbsolutePath());
			result = file.delete();
			file.deleteOnExit();
		} else {
			// System.out.println("fileName="+file.getAbsolutePath());
			result = file.delete();
			file.deleteOnExit();
		}
		return result;
	}

	// 创建多级文件夹
	public static boolean createDirFile(String filepath, String PathType) {
		var result = false;
		var st = new java.util.StringTokenizer(filepath,
				PathType);
		var temp = "";
		var sb = new StringBuffer();
		try {
			while (st.hasMoreTokens()) {
				temp = st.nextToken();
				sb.append(temp + PathType);
				if (!(new File(sb.toString()).isDirectory())) {
					result = new File(sb.toString()).mkdir();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 新建目录
	 *
	 * @param folderPath
	 *            目录
	 * @return 返回目录创建后的路径
	 */
	public String createFolder(String folderPath) {
		var result = false;
		var txt = folderPath;
		try {
			var myFilePath = new File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				result = myFilePath.mkdir();
			}
		} catch (Exception e) {
			System.out.println("创建目录操作出错");
		}
		return result?txt:"";
	}

	/**
	 * 多级目录创建
	 *
	 * @param folderPath
	 *            准备要在本级目录下创建新目录的目录路径 例如 c:myf
	 * @param paths
	 *            无限级目录参数，各级目录以单数线区分 例如 a|b|c
	 * @return 返回创建文件后的路径 例如 c:myfa c
	 */
	@SuppressWarnings("unused")
	public String createFolders(String folderPath, String paths) {
		var txts = folderPath;
		try {
			String txt;
			txts = folderPath;
			var st = new java.util.StringTokenizer(paths,
					"|");
			for (var i = 0; st.hasMoreTokens(); i++) {
				txt = st.nextToken().trim();
				if (txts.lastIndexOf("/") != -1) {
					txts = createFolder(txts + txt);
				} else {
					txts = createFolder(txts + txt + "/");
				}
			}
		} catch (Exception e) {
			System.out.println("创建目录操作出错");
		}
		return txts;
	}

}
