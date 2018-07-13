package com.wms.core.utils.jdbc;

import java.util.List;
import java.util.Map;

import com.wms.core.exceptions.WMSException;

/**
 * jdbc调用接口
 * 
 * @author xb
 *
 */
public interface IJdbcDao {
	/**
	 * 查询sql执行,指定返回类型
	 * 
	 * @param sql语句
	 * @param requiredType返回类型
	 * @return 查询结果对象
	 * @throws WMSException
	 */
	@SuppressWarnings("rawtypes")
	Object query(StringBuffer sql, Class requiredType) throws WMSException;

	/**
	 * 执行sql语句(create,update,delete)
	 * 
	 * @param sql语句
	 * @throws WMSException
	 */
	void execute(StringBuffer sql) throws WMSException;

	/**
	 * 查询sql执行,返回数据集合
	 * 
	 * @param sql语句
	 * @return数据集合
	 * @throws WMSException
	 */
	@SuppressWarnings("rawtypes")
	List queryForList(String sql) throws WMSException;

	/**
	 * 查询sql执行,返回数据集合
	 * 
	 * @param sql语句
	 * @param requiredType返回类型
	 * @return 数据集合
	 * @throws WMSException
	 */
	@SuppressWarnings("rawtypes")
	List queryForList(StringBuffer sql, Class requiredType) throws WMSException;

	/**
	 * 查询sql执行,返回数据键值对
	 * 
	 * @param sql语句
	 * @return 价值对数据
	 * @throws WMSException
	 */
	@SuppressWarnings("rawtypes")
	Map queryForMap(String sql) throws WMSException;
	

	List<Map<String, Object>> queryForList(StringBuffer sql)
			throws WMSException;
	/**
	 * 检测表是否存在
	 * @param table 表名
	 * @return
	 */
	boolean checkTable(String table);
}