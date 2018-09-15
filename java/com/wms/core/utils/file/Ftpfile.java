package com.wms.core.utils.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Ftpfile {
	
	private final static Log logger = LogFactory.getLog(Ftpfile.class);
	/** 
	 * Description: 向FTP服务器上传文件 
	 * @param url FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param path FTP服务器保存目录 
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param input 输入流 
	 * @return 成功返回true，否则返回false 
	 */  
	public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) {  
	    var success = false;
		var ftp = new FTPClient();
	    try {  
	        int reply;  
	        ftp.connect(url, port);//连接FTP服务器  
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
	        ftp.login(username, password);//登录  
	        reply = ftp.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return success;  
	        }

			var pathresult=false;
	        pathresult= ftp.changeWorkingDirectory(path);  
	        if(!pathresult){//没有此目录
	        	 // 设置上传目录(没有则创建)  
//		        var result = ftp.makeDirectory(path);//只能创建一级目录，不能级联创建
				var result = createMultiDir(ftp, path);
				System.out.println(result);
				ftp.changeWorkingDirectory(path);
	        }
	        ftp.storeFile(filename, input);           
	          
	        input.close();  
	        ftp.logout();  
	        success = true;  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
	    }  
	    return success;  
	}
	
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @param url FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param remotePath FTP服务器上的相对路径 
	 * @param fileName 要下载的文件名 
	 * @param localPath 下载后保存到本地的路径 
	 * @return 
	 */  
	public static boolean downFile(String url, int port,String username, String password, String remotePath,String fileName,String localPath) {
		var success = false;
		var ftp = new FTPClient();
	    OutputStream is = null;
	    try {  
	        int reply;  
	        ftp.connect(url, port);  
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
	        ftp.login(username, password);//登录  
	        reply = ftp.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return success;  
	        }  
	        ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录  
			var fs = ftp.listFiles();
	        for(var ff:fs){
	            if(ff.getName().equals(fileName)){  
	                //写入本地
					if(FileUtil.getExists(localPath)){
						FileUtil.newFolder(localPath);
					}

					var localFile = new File(localPath+"/"+ff.getName());
	                  
	                 is = new FileOutputStream(localFile);   
	                ftp.retrieveFile(ff.getName(), is);
	            }  
	        }  
	          
	        ftp.logout();  
	        success = true;  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
	        if(is!=null){
	        	 try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	        }
	    }  
	    return success;  
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String url, int port,String username, String password, String remotePath,String fileName,String localName,String localPath) {
		var success = false;
		var ftp = new FTPClient();
		OutputStream is =null;
		try {
			int reply;
			ftp.connect(url, port);
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
			var fs = ftp.listFiles();
			for(var ff:fs){
				if(ff.getName().equals(fileName)){
					//写入本地
					if(FileUtil.getExists(localPath)){
						FileUtil.newFolder(localPath);
					}

					var localFile = new File(localPath+"/"+localName);

					 is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
				}
			}

			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
			 if(is!=null){
	        	 try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
	        }
		}
		return success;
	}
	/**
     * 创建多级目录(有bug)
     * @param multiDir 多级文件路径 格式"/a/b/c/"
     * @return
     * @throws IOException
     */
    public static boolean  createMultiDir(FTPClient ftpClient,String multiDir) throws IOException {
		var bool = false;
		var dirs = multiDir.split("/");
        ftpClient.changeWorkingDirectory("/");
        for (var i = 1; dirs != null && i < dirs.length; i++) {
            if (!ftpClient.changeWorkingDirectory(dirs[i])) {//不存在
                if (ftpClient.makeDirectory(dirs[i])) {//创建
                    if (!ftpClient.changeWorkingDirectory(dirs[i])) {
                        return false;
                    } else {
                    	continue;
                    }
                }
            }
            bool = true;
        }
        return bool;
    }

    /* 
     * 从SFTP服务器下载文件 
     *  
     * @param ftpHost SFTP IP地址 
     *  
     * @param ftpUserName SFTP 用户名 
     *  
     * @param ftpPassword SFTP用户名密码 
     *  
     * @param ftpPort SFTP端口 
     *  
     * @param ftpPath SFTP服务器中文件所在路径 格式： ftptest/aa 
     *  
     * @param localPath 下载到本地的位置 格式：H:/download 
     *  
     * @param fileName 文件名称 
     */  
    public static void downloadSftpFile(String ftpHost, String ftpUserName,  
            String ftpPassword, int ftpPort, String ftpPath, String localPath,  
            String fileName) throws JSchException {  
        Session session = null;  
        Channel channel = null;

		var jsch = new JSch();
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort);  
        session.setPassword(ftpPassword);  
        session.setTimeout(100000);
		var config = new Properties();
        config.put("StrictHostKeyChecking", "no");  
        session.setConfig(config);  
        session.connect();  
  
        channel = session.openChannel("sftp");  
        channel.connect();
		var chSftp = (ChannelSftp) channel;

		var ftpFilePath = ftpPath + "/" + fileName;
		var localFilePath = localPath + File.separatorChar + fileName;
  
        try {  
            chSftp.get(ftpFilePath, localPath);  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.info("download error.");  
        } finally {  
            chSftp.quit();  
            channel.disconnect();  
            session.disconnect();  
        }  
  
    }  
}
