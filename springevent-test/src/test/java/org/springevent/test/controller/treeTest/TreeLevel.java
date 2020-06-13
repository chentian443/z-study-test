package org.springevent.test.controller.treeTest;

public class TreeLevel {

	private TreeNode[] value;
	private TreeNode[] up;
	private TreeNode[] down;
	
	public TreeNode[] getValue() {
		return value;
	}
	public void setValue(TreeNode[] value) {
		this.value = value;
	}
	public TreeNode[] getUp() {
		return up;
	}
	public void setUp(TreeNode[] up) {
		this.up = up;
	}
	public TreeNode[] getDown() {
		return down;
	}
	public void setDown(TreeNode[] down) {
		this.down = down;
	}
	
}
