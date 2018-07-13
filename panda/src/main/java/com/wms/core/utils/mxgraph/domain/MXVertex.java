package com.wms.core.utils.mxgraph.domain;

/**
 * mxgraph图形类型
 * @author xb
 *
 */
public class MXVertex extends MXCell {
	/**
	 * 图形类型
	 */
	private String vertex;
	/**
	 * 图形类型关系
	 */
	private MXVertexGeometry mxVertexGeometry;

	public String getVertex() {
		return vertex;
	}

	public void setVertex(String vertex) {
		this.vertex = vertex;
	}

	public MXVertexGeometry getMxVertexGeometry() {
		return mxVertexGeometry;
	}

	public void setMxVertexGeometry(MXVertexGeometry mxVertexGeometry) {
		this.mxVertexGeometry = mxVertexGeometry;
	}

}
