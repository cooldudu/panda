package com.wms.core.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public class TreeNode extends HashMap<String, Object> {

	public String getId() {
		return (String) this.get("id");
	}

	public void setId(String id) {
		this.put("id", id);
	}

	public String getText() {
		return (String) this.get("text");
	}

	public void setText(String text) {
		this.put("text", text);
	}

	public String getIconCls() {
		return (String) this.get("iconCls");
	}

	public void setIconCls(String iconCls) {
		this.put("iconCls", iconCls);
	}

	public String getIcon() {
		return (String) this.get("icon");
	}

	public void setIcon(String icon) {
		this.put("icon", icon);
	}

	public boolean isLeaf() {
		return (Boolean) this.get("leaf");
	}

	public void setLeaf(boolean leaf) {
		this.put("leaf", leaf);
	}

	public String getCls(String cls) {
		return (String) this.get("cls");
	}

	public void setCls(String cls) {
		this.put("cls", cls);
	}

	@SuppressWarnings("unchecked")
	public List<TreeNode> getChildren() {
		if (null == this.get("children")) {
			this.put("children", new ArrayList<TreeNode>());
		}
		return (List<TreeNode>) this.get("children");
	}

	public void addChild(TreeNode node) {
		this.getChildren().add(node);
		this.put("children", this.getChildren());
	}

}
