package org.springevent.test.sort;

import java.util.Arrays;
import java.util.Date;

public class SortUtilsTest {
	

	public static void main(String[] args) {
		int[] arr = generateRandomArray(1000000, 90000);
		//int[] arr = generateRandomArray(100, 50);
		//testSelectSort(arr); // 10616
		//testInsertSort(arr); // 3103
		//testBubbleSort(arr);// 12791
		//testQuickSort(arr);// 16
		//testGuiBingSort(arr);// 16
		//testHeapSort(arr); //15
		testShellSort(arr); // 31
	}
	
	public static void testSelectSort(int[] arr){
		Date startDate = new Date();
		SortUitls.selectSort(arr);
		printTimeGap(startDate);
	}
	
	public static void testInsertSort(int[] arr){
		Date startDate = new Date();
		SortUitls.insertSort(arr);
		printTimeGap(startDate);
	}
	
	public static void testBubbleSort(int[] arr){
		Date startDate = new Date();
		SortUitls.bubbleSort(arr);
		printTimeGap(startDate);
	}
	
	
	public static void testQuickSort(int[] arr){
		Date startDate = new Date();
		SortUitls.quickSort(arr, 0, arr.length-1);
		printTimeGap(startDate);
	}
	
	public static void testGuiBingSort(int[] arr){
		Date startDate = new Date();
		SortUitls.guiBingSort(arr, 0, arr.length-1);
		printTimeGap(startDate);
	}
	
	public static void testHeapSort(int[] arr){
		Date startDate = new Date();
		SortUitls.heapSort(arr);
		printTimeGap(startDate);
	}
	
	public static void testShellSort(int[] arr){
		Date startDate = new Date();
		SortUitls.shellSort(arr);
		printTimeGap(startDate);
	}
	
	

	public static int[] generateRandomArray(int max, int num) {
		int[] arr = new int[num];
		for (int i = 0; i < num; i++) {
			int random = (int) (Math.random() * max + 1);
			arr[i] = random;
		}
		return arr;
	}
	
	public static void printArr(int[] arr){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]).append(" ");
		}
		System.out.println(sb);
	}
	
	public static void printTimeGap(Date startDate){
		Date nowDate = new Date();
		long mil = nowDate.getTime()-startDate.getTime();
		System.out.println(mil);
	}
	
	
	
	private void researchArraysSort(){
		int[] a = new int[10];
		Arrays.sort(a);
	}
	
	
}
