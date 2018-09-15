package com.wms.core.utils.file;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.util.Properties;  
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;  
import com.jcraft.jsch.ChannelSftp;  
import com.jcraft.jsch.JSch;  
import com.jcraft.jsch.Session;  
import com.jcraft.jsch.SftpException;
import com.wms.core.utils.common.ResourceBundleUtil;  

public class SftpUtil {
	
	private static Logger logger=LoggerFactory.getLogger(SftpUtil.class);
	/** 
     * 链接sftp 
     *  
     * @param host 
     *            主机 
     * @param port 
     *            端口 
     * @param username 
     *            用户名 
     * @param password 
     *            密码 
     * @return 
     */  
    public static ChannelSftp connect(String host, int port, String username,  
            String password) {  
        ChannelSftp sftp = null;  
        try {
            var jsch = new JSch();
            jsch.getSession(username, host, port);
            var sshSession = jsch.getSession(username, host, port);
            System.out.println("Session创建成功");  
            sshSession.setPassword(password);
            var sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");  
            sshSession.setConfig(sshConfig);  
            sshSession.connect();  
            System.out.println("Session已连接");
            var channel = sshSession.openChannel("sftp");
            channel.connect();  
            sftp = (ChannelSftp) channel;  
            System.out.println("连接到主机" + host + ".");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return sftp;  
    }  
    /** 
     * 链接sftp静态方法 
     *  
     * @param host 
     *            主机 
     * @param portStr
     *            端口 
     * @param username 
     *            用户名 
     * @param password 
     *            密码 
     * @return 
     * @throws ConnectException 
     */  
    public static ChannelSftp connect(String host, String portStr, String username,  
    		String password) throws ConnectException {  
    	ChannelSftp sftp = null;  
    	int port=Integer.valueOf(portStr);
    	try {
            var jsch = new JSch();
    		jsch.getSession(username, host, port);
            var sshSession = jsch.getSession(username, host, port);
    		System.out.println("Session创建成功");  
    		sshSession.setPassword(password);
            var sshConfig = new Properties();
    		sshConfig.put("StrictHostKeyChecking", "no");  
    		sshSession.setConfig(sshConfig);  
    		sshSession.connect();  
    		System.out.println("Session已连接");
            var channel = sshSession.openChannel("sftp");
    		channel.connect();  
    		sftp = (ChannelSftp) channel;  
    		System.out.println("连接到主机" + host + ".");  
    	} catch (Exception e) {  
    		if(e instanceof ConnectException) {
    			throw new ConnectException("远程数据网络连接失败");
    		}
    	}  
    	return sftp;  
    }  
    
    public void disconnect(ChannelSftp sftp) {
    	sftp.disconnect();
    }
  
    /** 
     * 文件重命名 
     *  
     * @param directory 目录 
     * @param oldname 旧文件名   
     * @param newname 新文件名 
     * @param sftp 
     */  
    public void renameFile(String directory, String oldname, String newname,  
            ChannelSftp sftp) {  
        try {  
            sftp.cd(directory);  
            sftp.rename(oldname, newname);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 文件上传 
     *  
     * @param directory 目录 
     * @param uploadFile 要上传的文件名 
     * @param sftp 
     */  
    public void upload(String directory, String uploadFile, ChannelSftp sftp) {  
        try {  
            sftp.cd(directory);
            var file = new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 文件上传 
     *  
     * @param directory 目录 
     * @param file 要上传的文件名
     * @param sftp 
     */  
    public static void uploadRemoteFile(String directory, File file, ChannelSftp sftp) {  
        try {  
            sftp.cd(directory);  
            sftp.put(new FileInputStream(file), file.getName());  
        } catch (Exception e) {  
        	logger.debug("上传文件时发生异常,{}",e);
        }  
    }
    /** 
     * 文件下载 
     *  
     * @param directory 目录 
     * @param downloadFile 要下载文件名 
     * @param saveFile 保持的文件名 
     * @param sftp 
     */  
    public void download(String directory, String downloadFile,  
            String saveFile, ChannelSftp sftp) {  
        try {  
        	if(sftp==null) {
        		throw new ConnectException();
        	}
            sftp.cd(directory);  
            
//             directory=directory+"/"+downloadFile;
            var file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * 文件下载 
     *  
     * @param directory 目录 
     * @param downloadFile 要下载文件名 
     * @param saveFile 保持的文件名 
     * @param sftp 
     * @throws ConnectException 
     */  
    public static File downloadMDataFile(String directory, String downloadFile,  
    		String saveFile, ChannelSftp sftp) throws ConnectException {  
    	File file =null;
    	try {  
    		if(sftp==null) {
        		throw new ConnectException();
        	}
    		logger.info("准备cd文件目录");
    		System.out.println("准备cd文件目录");
    		sftp.cd(directory);  
    		
//             directory=directory+"/"+downloadFile;
    		file = new File(saveFile);  
    		System.out.println("准备获取文件"+downloadFile);
    		sftp.get(downloadFile, new FileOutputStream(file));  
    	} catch (Exception e) {
    		System.out.println("连接异常");
    		e.printStackTrace();
    		logger.info("连接异常{}",e);
    		if(e instanceof ConnectException) {
    			throw new ConnectException("远程数据网络连接异常");
    		}
    	}  
    	return file;
    }  
    
    
    /** 
     * 文件删除 
     *  
     * @param directory 目录 
     * @param deleteFile 要删除的文件名 
     * @param sftp 
     */  
    public void delete(String directory, String deleteFile, ChannelSftp sftp) {  
        try {  
            sftp.cd(directory);  
            sftp.rm(deleteFile);  
            System.out.println("删除成功");  
        } catch (Exception e) {  
            System.out.println("删除失败");  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 列出目录下的文件 
     *  
     * @param directory 目录 
     * @param sftp 
     * @return 
     * @throws SftpException 
     */  
    public Vector listFiles(String directory, ChannelSftp sftp)  
            throws SftpException {  
        return sftp.ls(directory);  
    }
}
