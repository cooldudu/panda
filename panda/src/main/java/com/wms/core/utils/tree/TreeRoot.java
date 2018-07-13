package com.wms.core.utils.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeRoot {
	private boolean expanded;
	private List<TreeNode> children = new ArrayList<TreeNode>();
	private String icon="";
	private String text="Root";

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void addChild(TreeNode child) {
		this.children.add(child);
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
