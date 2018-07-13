package com.wms.core.utils.digest.encode;

import javax.annotation.Resource;

import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.stereotype.Repository;

import com.wms.core.utils.applicationcontext.ApplicationContextUtil;

import java.io.UnsupportedEncodingException;

/**
 * 摘要证书加密方式
 *
 * @author xb
 *
 */
@Repository
public class EncodeUtil {
	/**
	 * digest证书用户密码加密方法
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public String digestEncode(String username, String password) {
		try {
			return DigestAuthUtils.encodePasswordInA1Format(username,
					this.getRealmName(), password);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 获取当前系统指定的证书公钥
	 *
	 * @return
	 */
	public String getRealmName() {
		DigestAuthenticationEntryPoint digestEntryPoint = (DigestAuthenticationEntryPoint)applicationContextUtil
				.getApplicationContext().getBean("digestEntryPoint");
		return digestEntryPoint.getRealmName();
	}

	@Resource
	private ApplicationContextUtil applicationContextUtil;

}
