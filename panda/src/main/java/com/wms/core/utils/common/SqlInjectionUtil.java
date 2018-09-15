package com.wms.core.utils.common;

import org.apache.commons.logging.LogFactory;

public class SqlInjectionUtil {
	public static final String sqlkey = "+|(|)|\"|'|@|%|$|&|;|>|<|sp_|xp_|\\|cmd|^|(|)|+|$|'|copy|format|%20and%20|%20or%20| and | or |exec|insert|select|delete|update|*|null|NULL|chr|master|truncate|char|declare|../|..%2f|&quot;|&lt;|&gt;|&lsquo;|&rsquo;|&sbquo;|&ldquo;|&rdquo;|\"|‘|’|‚";
	public static final String sqlkey2 = "+|(|)|<|>|\"|'|;|sp_|xp_|\\|cmd|(|)|+|'|copy|format|%20and%20|%20or%20| and | or |exec|insert|select|delete|update|null|NULL|chr|master|truncate|char|declare|../|..%2f|&quot;|&lt;|&gt;|&lsquo;|&rsquo;|&sbquo;|&ldquo;|&rdquo;|\"|‘|’|‚";
	public static final String selectSqlKey = "+|drop |@|$|&|1=1| statistics| key_| columns| tables| table_|dba_| user_| all_| session_| index_|$|;| sp_| xp_|\\|cmd |^|show |copy |format|exec |insert |delete |update |chr |master |truncate |char |declare |../|..%2f";

	public static boolean isIllegalParam(String val, String sqlKey) {
		var stk = new java.util.StringTokenizer(sqlKey,
				"|");
		var temp = "";
		var flag = false;
		while (stk.hasMoreTokens()) {
			temp = stk.nextToken();
			if (val.toLowerCase().indexOf(temp) != -1) {
				LogFactory.getLog(SqlInjectionUtil.class).error(temp+"******************");
				flag = true;
				break;
			}
		}
		return flag;
	}

//	public static void main(String[] args){
//        String a = "select * from T_ACCOUNTS t where t.createdate > to_date('2016-06-01', 'YYYY-MM-DD')";
//        boolean r = SqlInjectionUtil.isIllegalParam(a,SqlInjectionUtil.selectSqlKey);
//        System.out.println(r);
//    }

}