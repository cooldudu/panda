package com.wms.core.utils.mxgraph.domain;

/**
 * mxgraph关联关系
 * @author xb
 *
 */
public class MXRelation {
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
	private long sourceId;
	/**
	 * 联系关系目标ID
	 */
	private long targetId;

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

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

}
