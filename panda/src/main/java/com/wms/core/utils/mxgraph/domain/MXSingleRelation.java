package com.wms.core.utils.mxgraph.domain;

/**
 * mxgraph关联关系
 * @author xb
 *
 */
public class MXSingleRelation {
	/**
	 * 联系关系源行数
	 */
	private int sourceRow;
	/**
	 * 联系关系目标行数
	 */
	private int targetRow;
	/**
	 * 联系关系源ID
	 */
	private String sourceId;
	/**
	 * 联系关系目标ID
	 */
	private String targetId;

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public int getSourceRow() {
		return sourceRow;
	}

	public void setSourceRow(int sourceRow) {
		this.sourceRow = sourceRow;
	}

	public int getTargetRow() {
		return targetRow;
	}

	public void setTargetRow(int targetRow) {
		this.targetRow = targetRow;
	}
}