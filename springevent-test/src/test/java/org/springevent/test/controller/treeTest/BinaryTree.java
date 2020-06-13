package org.springevent.test.controller.treeTest;

import java.util.ArrayList;

public class BinaryTree {

	private TreeNode root;
	
	public BinaryTree() {}

	public BinaryTree(int value) {
		this.root = new TreeNode(value);
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
	
	public boolean add(int value){
		if(this.root == null){
			this.root = new TreeNode(value);
			return true;
		}else{
			TreeNode current = this.root;
			while(true){
				// 首先比较大小
				if(value < current.getValue()){
					// 判断左节点是否为空
					if(current.getLeft() == null){
						// 放左节点
						current.setLeft(new TreeNode(value));
						return true;
					}else{
						// 移动当前结点
						current = current.getLeft();
					}
				}else if(value > current.getValue()){
					// 判断右节点是否为空
					if(current.getRight() == null){
						// 放左节点
						current.setRight(new TreeNode(value));
						return true;
					}else{
						// 移动当前结点
						current = current.getRight();
					}
				}else{
					return false;
				}
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		ArrayList<TreeNode> list=new ArrayList<>();
		if(this.root != null){
			sb.append(this.root.getValue()+"\n");
			list.add(this.root);
		}
		
		while(true){
			ArrayList<TreeNode> newList=new ArrayList<>();
			for(int i=0; i<list.size(); i++){
				if(list.get(i).getLeft()!=null){
					sb.append(list.get(i).getLeft().getValue()+"  ");
					newList.add(list.get(i).getLeft());
				}
				if(list.get(i).getRight()!=null){
					sb.append(list.get(i).getRight().getValue()+"  ");
					newList.add(list.get(i).getRight());
				}
			}
			if(newList.size() == 0){
				break;
			}
			list = newList;
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
