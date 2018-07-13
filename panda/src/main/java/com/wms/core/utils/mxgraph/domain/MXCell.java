package com.wms.core.utils.mxgraph.domain;

import java.util.Map;

/**
 * mxgraph中对应的列实体结构
 * @author xb
 *
 */
public class MXCell {
	/**
	 * 标识ID
	 */
	private String id;
	/**
	 * 值
	 */
	private String value;
	/**
	 * 父节点
	 */
	private String parent;
	/**
	 * 参数集合
	 */
	private Map<String, String> params;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}
