package org.springevent.test.controller.treeTest;

public class BinaryTreeTest {

	public static void main(String[] args) {
		int[] a = {5, 3, 8, 1, 4, 6, 10, 12};
		
		BinaryTree bt = new BinaryTree();
		
		for (int i : a) {
			bt.add(i);
		}
		
		System.out.println(bt);
	}
}
