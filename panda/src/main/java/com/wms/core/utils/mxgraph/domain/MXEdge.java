package com.wms.core.utils.mxgraph.domain;

/**
 * mxgraph线元素
 * @author xb
 *
 */
public class MXEdge extends MXCell{
	/**
	 * 所属线段
	 */
	private String edge;
	/**
	 * 线段起点
	 */
	private String source;
	/**
	 * 线段终点
	 */
	private String target;
	/**
	 * 线段形状
	 */
	private MXEdgeGeometry mxEdgeGeometry;

	public String getEdge() {
		return edge;
	}

	public void setEdge(String edge) {
		this.edge = edge;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public MXEdgeGeometry getMxEdgeGeometry() {
		return mxEdgeGeometry;
	}

	public void setMxEdgeGeometry(MXEdgeGeometry mxEdgeGeometry) {
		this.mxEdgeGeometry = mxEdgeGeometry;
	}

}
