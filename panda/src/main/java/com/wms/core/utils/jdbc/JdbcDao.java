package com.wms.core.utils.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.wms.core.exceptions.WMSException;

public class JdbcDao extends JdbcDaoSupport implements IJdbcDao {
	/**
	 * 查询sql执行,指定返回类型
	 * @param sql语句
	 * @param requiredType返回类型
	 * @return 查询结果对象
	 * @throws WMSException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object query(StringBuffer sql, Class requiredType) throws WMSException {
		return getJdbcTemplate().queryForObject(sql.toString(), requiredType);
	}
	/**
	 * 执行sql语句(create,update,delete)
	 * @param sql语句
	 * @throws WMSException
	 */
	public void execute(StringBuffer sql) throws WMSException {
		getJdbcTemplate().execute(sql.toString());
	}
	
	/**
	 * 查询sql执行,返回数据集合
	 * @param sql语句
	 * @return数据集合
	 * @throws WMSException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List queryForList(String sql) throws WMSException {
		return getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 查询sql执行,返回数据键值对
	 * @param sql语句
	 * @return 价值对数据
	 * @throws WMSException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map queryForMap(String sql) throws WMSException {
		return getJdbcTemplate().queryForMap(sql);
	}


	/**
	 * 查询sql执行,返回数据集合
	 * @param sql语句
	 * @param requiredType返回类型
	 * @return 数据集合
	 * @throws WMSException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryForList(StringBuffer sql, Class requiredType) throws WMSException {
		return getJdbcTemplate().queryForList(sql.toString(), requiredType);
	}
	
	@Override
	public List<Map<String, Object>> queryForList(StringBuffer sql) throws WMSException {
		return getJdbcTemplate().queryForList(sql.toString());
	}
	
	@Override
	public boolean checkTable(String table) {
		Connection conn = null ;
		ResultSet rs = null ;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			var data = conn.getMetaData();
			String[] types = {"TABLE"};
			rs = data.getTables(null, null, table, types);
			if(rs.next()){
				return true;
			}	
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
				try {
					if(null!=rs) {
						rs.close();
					}
					if(null!=conn) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
		}
		
		return false;
	}

}