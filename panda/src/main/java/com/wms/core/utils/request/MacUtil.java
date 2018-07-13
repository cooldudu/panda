package com.wms.core.utils.request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取局域网内部访问者的mac地址
 *
 * @author xb
 *
 */
public class MacUtil {
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			if (ip != null && ip.length() > 0 && ip.indexOf(',') != -1) {
				int index = ip.indexOf(',');
				if (index != -1) {
					return ip.substring(0, index);
				} else {
					return ip;
				}
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		return ip;
	}

	public static String getMACAddress(String ip) throws IOException {
		if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
			ip = "192.168.16.105";
			return "BC-77-37-E4-16-05";
		}
		String str = "";
		String macAddress = "";
		InputStreamReader ir = null;
		LineNumberReader input = null;
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			ir = new InputStreamReader(p.getInputStream(),"UTF-8");
			input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
					if (str.indexOf("MAC 地址") > 1) {
						macAddress = str.substring(str.indexOf("MAC 地址") + 9,
								str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}finally {
			if(null!=ir){
				ir.close();
			}
			if(null!=input){
				input.close();
			}
		}
		return macAddress;
	}
	// nmblookup -A linux 用这个 linux下str.indexOf("MAC Address")!=1，所以要处理一下
}
