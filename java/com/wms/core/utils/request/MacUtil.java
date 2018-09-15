package com.wms.core.utils.request;

import com.wms.core.utils.common.StaticData;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;


/**
 * 获取局域网内部访问者的mac地址
 *
 * @author xb
 *
 */
public class MacUtil {
	public static String getIpAddr(ServerWebExchange exchange) {
		var ip = exchange.getRequest().getHeaders().get("Host").get(StaticData.FIRST);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = exchange.getRequest().getRemoteAddress().toString();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")||ip.equals("localhost")) {
			ip = "127.0.0.1";
		}
		return ip;
	}

	public static String getMACAddress(String ip) throws IOException {
		if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
			ip = "192.168.16.105";
			return "BC-77-37-E4-16-05";
		}
		var str = "";
		var macAddress = "";
		InputStreamReader ir = null;
		LineNumberReader input = null;
		try {
			var p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			ir = new InputStreamReader(p.getInputStream(),"UTF-8");
			input = new LineNumberReader(ir);
			for (var i = 1; i < 100; i++) {
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
