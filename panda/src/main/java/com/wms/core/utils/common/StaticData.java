package com.wms.core.utils.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 所有的静态数据
 * 
 * xb
 * 
 * @date：2010-5-20 上午09:46:11
 */
public final class StaticData {
	/**
	 * 反斜杠字符
	 */
	public static final String BACKSLASH_CHARACTER = "/";

	/**
	 * 半角逗号
	 */
	public static final String COMMA = ",";

	/**
	 * 下滑杠
	 */
	public static final String DOWN_BARS = "_";

	/**
	 * 小数点
	 */
	public static final String RADIX_POINT = ".";

	/**
	 * 空字符串
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * 数字0
	 */
	public static final int ZERO = 0;

	/**
	 * 数字1
	 */
	public static final int FIRST = 1;
	
	public static final String Country = "999";

	/**
	 * 字符true
	 */
	public static final String TRUE = "true";

	/**
	 * 字符false
	 */
	public static final String FALSE = "false";

	/**
	 * 分页查询默认步长
	 */
	public static final int NO_PAGENUM_LIMIT = ZERO;

	/**
	 * 分页查询默认返回总数
	 */
	public static final int NO_ROWS_TO_RETURN_LIMIT = ZERO;

	public static final String METHOD_POST = "POST";

	public static final String METHOD_GET = "GET";

	public static final int DEFAULT_CANVA_WIDTH = 650;

	public static final int DEFAULT_CANVA_HEIGHT = 400;

	public static final int DEFAULT_FORMPAGECOLUMN_WIDTH = 500;

	public static final int DEFAULT_FORMPAGECOLUMN_HEIGHT = 300;

	public static final int SELECT_LIMIT = 10;
    public static final String SUBCC_NO="IPCC1";
	/**
	 * 模板默认宽高
	 */
	public static final int TEMPLATE_DEFAULT_WIDTH = ResourceBundleUtil
			.getInteger("default", "templateDefaultWidth");
	public static final int TEMPLATE_DEFAULT_HEIGHT = ResourceBundleUtil
			.getInteger("default", "templateDefaultHeight");

	public static final Map<String, String> PORTLET_TYPE = new HashMap<String, String>() {
		{
			put("1", "articleCategoryPortletService");
		}
	};

	public static final Map<String, String> PORTLET_UPDATE_URL = new HashMap<String, String>() {
		{
			put("1", "articlecategoryportlet/update");
		}
	};

	public static final Map<String, String> WORKFLOW_NODE_TYPE = new HashMap<String, String>() {
		{
			put("开始节点", "Start");
			put("结束节点", "End");
			put("分支节点", "Decision");
			put("任务节点", "Task");
			put("并行分支节点", "Fork");
			put("并行聚合节点", "Join");
			put("子流程", "SubProcess");
		}
	};
	
	

	public static String MODULEPROPERTYTYPE_ZIFUXING = "字符型";

	public static String MODULEPROPERTYTYPE_SHUZHIXING = "数值型";

	public static String MODULEPROPERTYTYPE_BOOLEAN = "布尔型";

	public static String MODULEPROPERTYTYPE_RIQIXING = "日期型";

	public static String MODULEPROPERTYTYPE_DASHUJUXING = "大数据型";

	public static String MODULEPROPERTYTYPE_ERJINZHI = "图片";

	public static String UUID = "UUID";

	public static String EXECUTIONID = "EXECUTIONID";

	public static int DEFAULT_VARCHAR_LENGTH = 32;

	public static String TABLE_PREFIX = "U_";

	public static String HIVE_PREFIX = "H_";

	public static String COLUMN_PREFIX = "F_";

	public static String ROLE_PREFIX = "ROLE_";

	public static String ROOT = "ROOT";

	// weka对应的执行方法
	public static final Map<String, String> WEKA_MAP = new HashMap<String, String>() {
		{
			put("weka.classifiers.trees.J48", "buildClassifier");
			put("weka.clusterers.SimpleKMeans", "buildClusterer");
			put("weka.classifiers.bayes.NaiveBayes", "buildClassifier");
			put("weka.classifiers.functions.Logistic", "buildClassifier");
			put("weka.associations.Apriori", "buildAssociations");
		}
	};
	// weka对应的执行方法
	public static final Map<String, String> WEKAINTERFACE_MAP = new HashMap<String, String>() {
		{
			put("weka.classifiers.trees.J48", "Classifier");
			put("weka.clusterers.SimpleKMeans", "Clusterer");
			put("weka.classifiers.bayes.NaiveBayes", "Classifier");
			put("weka.classifiers.functions.Logistic", "Classifier");
			put("weka.associations.Apriori", "Associator");
		}
	};

	public static final Map<String, String> RESULT_TYPE_MAP = new HashMap<String, String>() {
		{
			put("1", "统计分析");
			put("2", "数据算法");
		}
	};

	public static final Map<String, String> CHART_TYPE = new HashMap<String, String>() {
		{
			put("折线图", "line");
			put("柱状图", "bar");
			put("散点图", "scatter");
			put("k线图", "k");
			put("饼状图", "pie");
			put("雷达图", "radar");
			put("和弦图", "chord");
			put("时间轴图", "time");
			put("仪表图", "gauge");
			put("漏斗图", "funnel");
		}
	};

	public static final Map<String, String> CHART_TYPE2 = new HashMap<String, String>() {
		{
			put("柱状图", "bar");
			put("饼状图", "pie");
			put("力导向布局图", "force");
		}
	};
	
	
	public static final Map<String, String> SECURITY_KEYS=new HashMap<String,String>(){
		{
			put("123597","VQyQN7V#sUTnYX/R");
			put("123697","n3KB2t&2TH#USxf+");
			put("123741","lGLAHhN+eqWfYtlo");
			put("123742","UqIygsKNFIW/MC/w");
			put("123743","ngD1e-gZUF@KKX2R");
			put("123744","e%xDAClN3rq=OCpo");
			put("123745","sqGuLVGwI#hSTdsI");
			put("123746","aqWpIZl6&#yHwplY");
			put("123747","SAko%1iBGXnG/0Pt");
			put("123748","VF8u5YyDyGNI#ac3");
			put("123749","uYgkKXcVYz69#8OL");
			put("123750","ybB5qd7r-n1-8eb-");
			put("123751","4t*Csl4gVxqCA5@1");
			put("123752","*CuxL7G$lPkh*#rK");
			put("123753","t*g/0/@OUoR#Ezo8");
			put("123754","3myT=*pxGEp2M=FF");
			put("123698","zMIfOqgncan#=oq&");
			put("123699","+h+bMvhKM+yMDak/");
			put("123700","Gw9V7#KMN*/tOIrG");
			put("123701","S%C$GPl-l7$LQIda");
			put("123702","Gu1iEyyBSg6fES$Z");
			put("123703","Pd-X%Qki=*GpZfWF");
			put("123704","9X&tHSG7%WRwYgFV");
			put("123705","1Z*m9WIRZ97=ZGH+");
			put("123706","#TVoXmg19=ABheWq");
			put("123708","PaaT7NASbo6B*aZL");
			put("123709","gVxz8Y@gZU6fv1xL");
			put("123710","1r=Gde9-r4M%K5ZT");
			put("123711","Zf2K*Ciha5UvuQ@0");
			put("123712","R0Kn$gSku/enC-qW");
			put("123713","7Zkixrg-hWV*cOMh");
			put("123714","4AM9qw90hB%6y%Xu");
			put("123715","M@pWQFEa+t9BYu9-");
			put("123716","Xu4WAFL5CX#bm7gL");
			put("123717","Lkezf972%GGOx@QG");
			put("123718","rL%n-@uWXQ9EgW%x");
			put("123719","ZP4OvbU73vU=Fr+3");
			put("123720","*9@tS-Kd8sxycd8t");
			put("123721","S6$/DOg8kDlNK6rv");
			put("123722","c@fvyuT7vApQ73hG");
			put("123723","NapbkYIsgYM4n1Ri");
			put("123724","QYvW&#CF%$bF0PHw");
			put("123725","xmtl98aIGyo+Qqk5");
			put("123726","BD6zngnA0+%-nYQO");
			put("123727","%KzVp7ZbdNOvKm6D");
			put("123728","zd%VZUMR=8qg/%Pv");
			put("123729","Ffchs7tHtI/TDW6d");
			put("123730","5uxEwmPFBU@lkxOo");
			put("123731","%75334Paua01w#sk");
			put("123732","yceKF6$hNkP8xiHP");
			put("123733","piTRH*k=C3d*ZeBN");
			put("123734","p-@wzdVGGKVxh=0g");
			put("123735","/Ia1eYynTevpspbm");
			put("123736","U2wLm*YIQiAWSn34");
			put("123737","96gWK-EngOlS-fX&");
			put("123738","UVB@$vG*#t+IpikR");
			put("123739","v/XoRngMS3ElnL53");
			put("123740","&Wnx/K0wCDTx1uFl");
		}
	};

	public static String WEKA_CLASSIFIER = "Classifier";

	public static String WEKA_CLUSTERER = "Clusterer";

	public static String WEKA_ASSOCIATOR = "Associator";

	public static String DATASHOW_WENZIZHANSHI = "文字展示";

	public static String DATASHOW_PIE = "pie";

	public static String DATASHOW_BAR = "bar";

	public static String DATASHOW_CHORD = "chord";

	public static String DATASHOW_FORCE = "force";

	public static String WEKA_J48 = "weka.classifiers.trees.J48";

	public static String WEKA_SimpleKMeans = "weka.clusterers.SimpleKMeans";

	public static String WEKA_NaiveBayes = "weka.classifiers.bayes.NaiveBayes";

	public static String WEKA_Logistic = "weka.classifiers.functions.Logistic";

	public static String WEKA_Apriori = "weka.associations.Apriori";

	public final static boolean ML_COUNT = ResourceBundleUtil.getBoolean("default",
			"ML_COUNT");
	public final static boolean HIT_COUNT = ResourceBundleUtil.getBoolean("default",
			"HIT_COUNT");

	public static String DEFAULT_THEME = ResourceBundleUtil.getString(
			"default", "DEFAULT_THEME");
	public static String DEFAULT_COLOR = ResourceBundleUtil.getString(
			"default", "DEFAULT_COLOR");
	public static String DEFAULT_WALLPAPERID = ResourceBundleUtil.getString(
			"default", "DEFAULT_WALLPAPERID");
	public static String DEFAULT_ICON = ResourceBundleUtil.getString("default",
			"DEFAULT_ICON");

	public static final Set<String> CLOUD_SUPPORT_EXT = new HashSet<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("accdb");
			add("avi");
			add("bmp");
			add("css");
			add("doc");
			add("docx");
			add("eml");
			add("eps");
			add("fla");
			add("gif");
			add("html");
			add("ind");
			add("ini");
			add("jpg");
			add("jpeg");
			add("jsf");
			add("midi");
			add("mov");
			add("mp3");
			add("mpeg");
			add("pdf");
			add("png");
			add("ppt");
			add("pptx");
			add("proj");
			add("psd");
			add("pst");
			add("pub");
			add("rar");
			add("tiff");
			add("txt");
			add("url");
			add("vsd");
			add("wav");
			add("wma");
			add("wmv");
			add("xls");
			add("xlsx");
			add("zip");
		}
	};

	public static final String GROUP_CALL=ResourceBundleUtil.getString("default", "GROUP_CALL");
	public static final String ZWGW_URL=ResourceBundleUtil.getString("default", "ZWGW_URL");
}