package com.wms.core.utils.pagestatic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;

import com.wms.core.exceptions.WMSException;
import com.wms.core.utils.common.ResourceBundleUtil;
import com.wms.core.utils.common.StaticData;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * FreeMarker工具类
 *
 * @author：xb
 * @since：2010-3-10 下午05:24:17
 *
 */
public final class HTMLCreater {
	/**
	 * 生成静态文件
	 *
	 * @param context
	 *            ServletContext
	 * @param templatePath
	 *            ftl文件所在目录
	 * @param templateName
	 *            ftl文件全名
	 * @param encoding
	 *            编码格式
	 * @param data
	 *            数据Map
	 * @param targetHtmlPath
	 *            静态文件保存目录
	 * @return
	 */
	private static final String STATIC_HTML_PAGE_PATH = "staticHtmlPagePath";

	private static final String HOUSESOURCE_PAGE_PATH = "housesource";

	private static final String HOUSESOURCEREQUIRMENT_PAGE_PATH = "housesourcerequirment";
	/**
	 * 房屋银行静态页存放路径
	 */
	private static final String SPDB_PAGE_PATH = "spdb";

	private static String statichtmlpath = ResourceBundleUtil.getString(
			ResourceBundleUtil.DEFAULT_PROPERTY_FILE_NAME,
			STATIC_HTML_PAGE_PATH);

	@SuppressWarnings("deprecation")
	public static void merge(ServletContext context, String templatePath,
							 String templateName, String encoding, Map<String, Object> data,
							 String htmlUrl) {

		try {
			// 创建和配置Configuration对象
			Configuration cfg = new Configuration();

			// 这只模板所在的目录
			cfg.setServletContextForTemplateLoading(context, templatePath);

			// 设置对象包装器，用于将对象包装为数据模型
			cfg.setObjectWrapper(new DefaultObjectWrapper());

			// 获取一个模板
			Template template = cfg.getTemplate(templateName, encoding);
			template.setEncoding(encoding);
			// TODO读取配置文件路径
			File htmlFile = new File(statichtmlpath + htmlUrl);

			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));

			template.process(data, out);

			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除静态文件
	 *
	 * @param context
	 * @param urlPath
	 * @throws WMSException
	 */
	@SuppressWarnings("unused")
	public static boolean deleteHTML(String htmlPath) throws WMSException {
		File htmlFile = new File(statichtmlpath + htmlPath);

		if (htmlFile == null) {
			throw new WMSException("读取文件失败");
		}

		return htmlFile.delete();

	}

	/**
	 * 获取静态文件存放路径
	 *
	 * @param classType
	 *            实体类型的类型名称
	 * @return
	 */
	public static String getHtmlPath(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return StaticData.BACKSLASH_CHARACTER + HOUSESOURCEREQUIRMENT_PAGE_PATH
				+ StaticData.BACKSLASH_CHARACTER + calendar.get(Calendar.YEAR)
				+ StaticData.BACKSLASH_CHARACTER
				+ (calendar.get(Calendar.MONTH) + 1)
				+ StaticData.BACKSLASH_CHARACTER + calendar.get(Calendar.DATE)
				+ StaticData.BACKSLASH_CHARACTER;
	}

	public static String getHtmlPath1(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return StaticData.BACKSLASH_CHARACTER + HOUSESOURCE_PAGE_PATH
				+ StaticData.BACKSLASH_CHARACTER + calendar.get(Calendar.YEAR)
				+ StaticData.BACKSLASH_CHARACTER
				+ (calendar.get(Calendar.MONTH) + 1)
				+ StaticData.BACKSLASH_CHARACTER + calendar.get(Calendar.DATE)
				+ StaticData.BACKSLASH_CHARACTER;
	}

	public synchronized static String getHtmlName(String no) {
		return no + ".shtml";
	}

	/**
	 * 创建文件夹
	 *
	 * @param fileName
	 *            文件名
	 * @throws WMSException
	 */
	public static void createFileDir(String fileName) throws WMSException {
		// 读取配置文件路径
		File htmlFile = new File(statichtmlpath
				+ StaticData.BACKSLASH_CHARACTER + SPDB_PAGE_PATH
				+ StaticData.BACKSLASH_CHARACTER);

		// 判断目录是否存在，否则创建
		if (!htmlFile.exists()) {
			if (!htmlFile.mkdirs()) {
				throw new WMSException("静态页面路径创建失败！");
			}
		}
	}

	/**
	 * 创建简单HTML文件
	 *
	 * @param context
	 * @param templatePath
	 * @param templateName
	 * @param encoding
	 * @param htmlUrl
	 * @throws WMSException
	 */
	public static void createSimplePage(ServletContext context,
										String templatePath, String templateName, String encoding,
										String targetHtmlName) throws WMSException {
		createFileDir(targetHtmlName);
		merge(context, templatePath, templateName, encoding, null,
				SPDB_PAGE_PATH + StaticData.BACKSLASH_CHARACTER
						+ targetHtmlName);
	}

	public static void createField(Date date) throws WMSException {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		// 读取配置文件路径
		File htmlFile = new File(statichtmlpath
				+ StaticData.BACKSLASH_CHARACTER
				+ HOUSESOURCEREQUIRMENT_PAGE_PATH
				+ StaticData.BACKSLASH_CHARACTER);

		// 判断目录是否存在，否则创建
		if (!htmlFile.exists()) {
			if (!htmlFile.mkdirs()) {
				throw new WMSException("静态页面路径创建失败！");
			}

			File htmlFile1 = new File(statichtmlpath
					+ StaticData.BACKSLASH_CHARACTER
					+ HOUSESOURCEREQUIRMENT_PAGE_PATH
					+ StaticData.BACKSLASH_CHARACTER
					+ calendar.get(Calendar.YEAR)
					+ StaticData.BACKSLASH_CHARACTER);

			if (!htmlFile1.exists()) {
				if (!htmlFile1.mkdirs()) {
					throw new WMSException("静态页面路径创建失败！");
				}
				File htmlFile2 = new File(statichtmlpath
						+ StaticData.BACKSLASH_CHARACTER
						+ HOUSESOURCEREQUIRMENT_PAGE_PATH
						+ StaticData.BACKSLASH_CHARACTER
						+ calendar.get(Calendar.YEAR)
						+ StaticData.BACKSLASH_CHARACTER
						+ (calendar.get(Calendar.MONTH) + 1)
						+ StaticData.BACKSLASH_CHARACTER);
				if (!htmlFile2.exists()) {
					if (!htmlFile2.mkdirs()) {
						throw new WMSException("静态页面路径创建失败！");
					}
					File htmlFile3 = new File(statichtmlpath
							+ StaticData.BACKSLASH_CHARACTER
							+ HOUSESOURCEREQUIRMENT_PAGE_PATH
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.YEAR)
							+ StaticData.BACKSLASH_CHARACTER
							+ (calendar.get(Calendar.MONTH) + 1)
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.DATE)
							+ StaticData.BACKSLASH_CHARACTER);
					if (!htmlFile3.exists()) {
						if (!htmlFile3.mkdirs()) {
							throw new WMSException("静态页面路径创建失败！");
						}
					}
				}
			}
		} else {
			File htmlFile1 = new File(statichtmlpath
					+ StaticData.BACKSLASH_CHARACTER
					+ HOUSESOURCEREQUIRMENT_PAGE_PATH
					+ StaticData.BACKSLASH_CHARACTER
					+ calendar.get(Calendar.YEAR)
					+ StaticData.BACKSLASH_CHARACTER);
			if (!htmlFile1.exists()) {
				if (!htmlFile1.mkdirs()) {
					throw new WMSException("静态页面路径创建失败！");
				}
				File htmlFile2 = new File(statichtmlpath
						+ StaticData.BACKSLASH_CHARACTER
						+ HOUSESOURCEREQUIRMENT_PAGE_PATH
						+ StaticData.BACKSLASH_CHARACTER
						+ calendar.get(Calendar.YEAR)
						+ StaticData.BACKSLASH_CHARACTER
						+ (calendar.get(Calendar.MONTH) + 1)
						+ StaticData.BACKSLASH_CHARACTER);
				if (!htmlFile2.exists()) {
					if (!htmlFile2.mkdirs()) {
						throw new WMSException("静态页面路径创建失败！");
					}
					File htmlFile3 = new File(statichtmlpath
							+ StaticData.BACKSLASH_CHARACTER
							+ HOUSESOURCEREQUIRMENT_PAGE_PATH
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.YEAR)
							+ StaticData.BACKSLASH_CHARACTER
							+ (calendar.get(Calendar.MONTH) + 1)
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.DATE)
							+ StaticData.BACKSLASH_CHARACTER);
					if (!htmlFile3.exists()) {
						if (!htmlFile3.mkdirs()) {
							throw new WMSException("静态页面路径创建失败！");
						}
					}
				}
			} else {
				File htmlFile2 = new File(statichtmlpath
						+ StaticData.BACKSLASH_CHARACTER
						+ HOUSESOURCEREQUIRMENT_PAGE_PATH
						+ StaticData.BACKSLASH_CHARACTER
						+ calendar.get(Calendar.YEAR)
						+ StaticData.BACKSLASH_CHARACTER
						+ (calendar.get(Calendar.MONTH) + 1)
						+ StaticData.BACKSLASH_CHARACTER);
				if (!htmlFile2.exists()) {
					if (!htmlFile2.mkdirs()) {
						throw new WMSException("静态页面路径创建失败！");
					}
					File htmlFile3 = new File(statichtmlpath
							+ StaticData.BACKSLASH_CHARACTER
							+ HOUSESOURCEREQUIRMENT_PAGE_PATH
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.YEAR)
							+ StaticData.BACKSLASH_CHARACTER
							+ (calendar.get(Calendar.MONTH) + 1)
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.DATE)
							+ StaticData.BACKSLASH_CHARACTER);
					if (!htmlFile3.exists()) {
						if (!htmlFile3.mkdirs()) {
							throw new WMSException("静态页面路径创建失败！");
						}
					}
				} else {
					File htmlFile3 = new File(statichtmlpath
							+ StaticData.BACKSLASH_CHARACTER
							+ HOUSESOURCEREQUIRMENT_PAGE_PATH
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.YEAR)
							+ StaticData.BACKSLASH_CHARACTER
							+ (calendar.get(Calendar.MONTH) + 1)
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.DATE)
							+ StaticData.BACKSLASH_CHARACTER);
					if (!htmlFile3.exists()) {
						if (!htmlFile3.mkdirs()) {
							throw new WMSException("静态页面路径创建失败！");
						}
					}
				}
			}
		}
	}

	public static void createField1(Date date) throws WMSException {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		// 读取配置文件路径
		File htmlFile = new File(statichtmlpath
				+ StaticData.BACKSLASH_CHARACTER + HOUSESOURCE_PAGE_PATH
				+ StaticData.BACKSLASH_CHARACTER);

		// 判断目录是否存在，否则创建
		if (!htmlFile.exists()) {
			if (!htmlFile.mkdirs()) {
				throw new WMSException("静态页面路径创建失败！");
			}

			File htmlFile1 = new File(statichtmlpath
					+ StaticData.BACKSLASH_CHARACTER + HOUSESOURCE_PAGE_PATH
					+ StaticData.BACKSLASH_CHARACTER
					+ calendar.get(Calendar.YEAR)
					+ StaticData.BACKSLASH_CHARACTER);

			if (!htmlFile1.exists()) {
				if (!htmlFile1.mkdirs()) {
					throw new WMSException("静态页面路径创建失败！");
				}
				File htmlFile2 = new File(statichtmlpath
						+ StaticData.BACKSLASH_CHARACTER
						+ HOUSESOURCE_PAGE_PATH
						+ StaticData.BACKSLASH_CHARACTER
						+ calendar.get(Calendar.YEAR)
						+ StaticData.BACKSLASH_CHARACTER
						+ (calendar.get(Calendar.MONTH) + 1)
						+ StaticData.BACKSLASH_CHARACTER);
				if (!htmlFile2.exists()) {
					if (!htmlFile2.mkdirs()) {
						throw new WMSException("静态页面路径创建失败！");
					}
					File htmlFile3 = new File(statichtmlpath
							+ StaticData.BACKSLASH_CHARACTER
							+ HOUSESOURCE_PAGE_PATH
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.YEAR)
							+ StaticData.BACKSLASH_CHARACTER
							+ (calendar.get(Calendar.MONTH) + 1)
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.DATE)
							+ StaticData.BACKSLASH_CHARACTER);
					if (!htmlFile3.exists()) {
						if (!htmlFile3.mkdirs()) {
							throw new WMSException("静态页面路径创建失败！");
						}
					}
				}
			}
		} else {
			File htmlFile1 = new File(statichtmlpath
					+ StaticData.BACKSLASH_CHARACTER + HOUSESOURCE_PAGE_PATH
					+ StaticData.BACKSLASH_CHARACTER
					+ calendar.get(Calendar.YEAR)
					+ StaticData.BACKSLASH_CHARACTER);
			if (!htmlFile1.exists()) {
				if (!htmlFile1.mkdirs()) {
					throw new WMSException("静态页面路径创建失败！");
				}
				File htmlFile2 = new File(statichtmlpath
						+ StaticData.BACKSLASH_CHARACTER
						+ HOUSESOURCE_PAGE_PATH
						+ StaticData.BACKSLASH_CHARACTER
						+ calendar.get(Calendar.YEAR)
						+ StaticData.BACKSLASH_CHARACTER
						+ (calendar.get(Calendar.MONTH) + 1)
						+ StaticData.BACKSLASH_CHARACTER);
				if (!htmlFile2.exists()) {
					if (!htmlFile2.mkdirs()) {
						throw new WMSException("静态页面路径创建失败！");
					}
					File htmlFile3 = new File(statichtmlpath
							+ StaticData.BACKSLASH_CHARACTER
							+ HOUSESOURCE_PAGE_PATH
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.YEAR)
							+ StaticData.BACKSLASH_CHARACTER
							+ (calendar.get(Calendar.MONTH) + 1)
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.DATE)
							+ StaticData.BACKSLASH_CHARACTER);
					if (!htmlFile3.exists()) {
						if (!htmlFile3.mkdirs()) {
							throw new WMSException("静态页面路径创建失败！");
						}
					}
				}
			} else {
				File htmlFile2 = new File(statichtmlpath
						+ StaticData.BACKSLASH_CHARACTER
						+ HOUSESOURCE_PAGE_PATH
						+ StaticData.BACKSLASH_CHARACTER
						+ calendar.get(Calendar.YEAR)
						+ StaticData.BACKSLASH_CHARACTER
						+ (calendar.get(Calendar.MONTH) + 1)
						+ StaticData.BACKSLASH_CHARACTER);
				if (!htmlFile2.exists()) {
					if (!htmlFile2.mkdirs()) {
						throw new WMSException("静态页面路径创建失败！");
					}
					File htmlFile3 = new File(statichtmlpath
							+ StaticData.BACKSLASH_CHARACTER
							+ HOUSESOURCE_PAGE_PATH
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.YEAR)
							+ StaticData.BACKSLASH_CHARACTER
							+ (calendar.get(Calendar.MONTH) + 1)
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.DATE)
							+ StaticData.BACKSLASH_CHARACTER);
					if (!htmlFile3.exists()) {
						if (!htmlFile3.mkdirs()) {
							throw new WMSException("静态页面路径创建失败！");
						}
					}
				} else {
					File htmlFile3 = new File(statichtmlpath
							+ StaticData.BACKSLASH_CHARACTER
							+ HOUSESOURCE_PAGE_PATH
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.YEAR)
							+ StaticData.BACKSLASH_CHARACTER
							+ (calendar.get(Calendar.MONTH) + 1)
							+ StaticData.BACKSLASH_CHARACTER
							+ calendar.get(Calendar.DATE)
							+ StaticData.BACKSLASH_CHARACTER);
					if (!htmlFile3.exists()) {
						if (!htmlFile3.mkdirs()) {
							throw new WMSException("静态页面路径创建失败！");
						}
					}
				}
			}
		}

	}
}
