package com.wms.core.utils.encoding;

import java.io.UnsupportedEncodingException;

/**
 * 将制定字符串从ISO-8859-1转码为UTF-8
 * @author xb
 *
 */
public class Encoder {
	public static String getStringFromISO_8859_1TOUTF_8(String str)
			throws UnsupportedEncodingException {
		return new String(str.getBytes(EncodingType.ISO_8859_1),
				EncodingType.UTF_8);
	}
}
