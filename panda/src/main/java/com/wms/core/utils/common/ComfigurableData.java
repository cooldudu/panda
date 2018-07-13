package com.wms.core.utils.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 配置文件数据
 * 
 * @author：xb
 */
public final class ComfigurableData {
	// 图片上传路径
	public static final String PIC_UPLOAD_PATH = "uploadPath";
	// 图片最大宽度
	public static final String MAX_WIDTH = "maxWidth";
	// 图片对大高度
	public static final String MAX_HEIGHT = "maxHeight";
	// 图片相对路径
	public static final String PIC_RELATIVE_PATH = "picRelativePath";
	/**
	 * 水印图片路径
	 */
	public static final String WATERMARKER_PATH = ResourceBundleUtil.getString(
			ResourceBundleUtil.DEFAULT_PROPERTY_FILE_NAME, "warterMarkerPic");

	/**
	 * 系统图片上传路径
	 */
	public static final String UPLOAD_PIC_PATH = ResourceBundleUtil.getString(
			ResourceBundleUtil.DEFAULT_PROPERTY_FILE_NAME,
			"fileDefaultUploadPath");

	/**
	 * 系统图片上传绝对地址
	 */
	public static final String UPLOAD_PIC_REALPATH = ((ServletRequestAttributes) RequestContextHolder
			.getRequestAttributes()).getRequest().getSession().getServletContext()
			.getRealPath(UPLOAD_PIC_PATH)
			+ "/";
	/**
	 * 系统访问路径
	 */
	public static final String CONTEXTPATH = ((ServletRequestAttributes) RequestContextHolder
			.getRequestAttributes()).getRequest().getScheme()
			+ "://"
			+ ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest().getServerName()
			+ ":"
			+ ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest().getServerPort()
			+ ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest().getContextPath();

	/**
	 * 系统图片上传后相对路径
	 */
	public static final String PIC_PATH = CONTEXTPATH
			+ ResourceBundleUtil.getString(
					ResourceBundleUtil.DEFAULT_PROPERTY_FILE_NAME,
					"fileDefaultUploadPath") + "/";
}
