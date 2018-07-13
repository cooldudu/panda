package com.wms.core.utils.mxgraph.domain;

/**
 * mxgraph图形坐标和宽高
 * @author xb
 *
 */
public class MXVertexGeometry extends MXGeometry {
	/**
	 * x坐标
	 */
	private String x;
	/**
	 * y坐标
	 */
	private String y;
	/**
	 * 宽度
	 */
	private String width;
	/**
	 * 高度
	 */
	private String height;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}
