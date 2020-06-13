package org.springevent.test.sort;

public final class SortUitls {

	/**
	 * 选择排序 思路：
	 */
	public static int[] selectSort(int[] arr) {
		for (int x = 0; x < arr.length - 1; x++) // 最后一个数不用在自己和自己进行比较了，n-1轮
		{
			for (int y = x + 1; y < arr.length; y++) {
				if (arr[x] > arr[y]) {
					int temp = arr[x];
					arr[x] = arr[y];
					arr[y] = temp;
				}
			}
		}
		return arr;
	}

	/**
	 * 插入排序
	 * 
	 */
	public static int[] insertSort(int[] arr) {
		int len = arr.length;
		for (int i = 1; i < len; i++) {
			// j表示当前元素的位置，将其和左边的元素比较，若当前元素小，就交换，也就相当于插入
			// 这样当前元素位于j-1处，j--来更新当前元素,j一直左移不能越界，因此应该大于0
			for (int j = i; j > 0 && arr[j] < arr[j - 1]; j--) {
				int temp = arr[j]; // 元素交换
				arr[j] = arr[j - 1];
				arr[j - 1] = temp;
			}
		}
		return arr;
	}

	// 冒泡排序
	public static int[] bubbleSort(int[] arr) {
		int len = arr.length;
		for (int i = 0; i < len - 1; i++) {
			for (int j = 0; j < len - 1 - i; j++) {
				if (arr[j] > arr[j + 1]) { // 相邻元素两两对比
					int temp = arr[j + 1]; // 元素交换
					arr[j + 1] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr;
	}

	/**
	 * 快速排序
	 */
	public static void quickSort(int[] a, int start, int end) {
		if (start > end) {
			// 如果只有一个元素，就不用再排下去了
			return;
		} else {
			// 如果不止一个元素，继续划分两边递归排序下去
			// 返回基准值的定位
			int partition = divide(a, start, end);
			// 基准值左边重复该过程
			quickSort(a, start, partition - 1);
			// 基准值右边重复该过程
			quickSort(a, partition + 1, end);
		}
	}

	/**
	 * 快速排序会选定一个基准，这里选择最右边的元素作为基准值，通过left指针和right指针不断移动和替换，
	 * 最终将基准值方法准确的位置（left指针和right指针指向同一个位置），这时基准值左边都比基准值小，右边都比基准值大。
	 * 然后左边和右边继续该过程。 以下函数即为基准值定位的过程：将数组的某一段元素进行划分，小的在左边，大的在右边
	 */
	private static int divide(int[] a, int start, int end) {
		// 每次都以最右边的元素作为基准值
		int base = a[end];
		// start一旦等于end，就说明左右两个指针合并到了同一位置，可以结束此轮循环。
		while (start < end) {
			while (start < end && a[start] <= base)
				// 从左边开始遍历，如果比基准值小，就继续向右走
				start++;
			// 上面的while循环结束时，就说明当前的a[start]的值比基准值大，应与基准值进行交换
			if (start < end) {
				// 交换
				int temp = a[start];
				a[start] = a[end];
				a[end] = temp;
				// 交换后，此时的那个被调换的值也同时调到了正确的位置(基准值右边)，因此右边也要同时向前移动一位
				end--;
			}
			while (start < end && a[end] >= base)
				// 从右边开始遍历，如果比基准值大，就继续向左走
				end--;
			// 上面的while循环结束时，就说明当前的a[end]的值比基准值小，应与基准值进行交换
			if (start < end) {
				// 交换
				int temp = a[start];
				a[start] = a[end];
				a[end] = temp;
				// 交换后，此时的那个被调换的值也同时调到了正确的位置(基准值左边)，因此左边也要同时向后移动一位
				start++;
			}

		}
		// 这里返回start或者end皆可，此时的start和end都为基准值所在的位置
		return end;
	}

	/**
	 * 归并排序
	 * 
	 */
	public static void guiBingSort(int[] arr, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;
			guiBingSort(arr, start, mid);// 左边归并排序，使得左子序列有序
			guiBingSort(arr, mid + 1, end);// 右边归并排序，使得右子序列有序
			guiBingmerge(arr, start, mid, mid + 1, end);// 将两个有序子数组合并操作
		}
	}

	/**
	 * 归并函数
	 * 
	 * @param arr
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 */
	private static void guiBingmerge(int[] arr, int start1, int end1, int start2, int end2) {
		// 建立辅助数组
		int len = end2 - start1 + 1;
		int[] temp = new int[len];

		int i = start1;// 左序列指针
		int j = start2;// 右序列指针
		int t = 0;// 临时数组指针
		while (i <= end1 && j <= end2) { // 将小的放入辅助数组
			if (arr[i] <= arr[j]) {
				temp[t++] = arr[i++];
			} else {
				temp[t++] = arr[j++];
			}
		}
		// 若左序列此时还有有剩余的，将左边剩余元素填充进temp中
		while (i <= end1) {
			temp[t++] = arr[i++];
		}
		// 若右序列此时还有有剩余的，将右序列剩余元素填充进temp中
		while (j <= end2) {
			temp[t++] = arr[j++];
		}
		t = 0;
		// 将temp中的元素全部拷贝到原数组中
		while (start1 <= end2) {
			arr[start1++] = temp[t++];
		}
	}

	/**
	 * 堆排序
	 * 
	 */
	public static void heapSort(int[] arr) {
		// 1.构建大顶堆
		for (int i = arr.length / 2 - 1; i >= 0; i--) {
			// 从最后一个一个非叶子结点i从下至上，从右至左调整结构
			adjustHeap(arr, i, arr.length);
		}
		// 2.调整堆结构+交换堆顶元素与末尾元素
		for (int j = arr.length - 1; j > 0; j--) {
			swapForHeap(arr, 0, j);// 将堆顶元素与末尾元素进行交换
			adjustHeap(arr, 0, j);// 重新对堆进行调整
		}

	}

	/**
	 * 调整大顶堆
	 */
	private static void adjustHeap(int[] arr, int i, int length) {
		// i是父节点
		while (2 * i + 1 < length) {
			// k是子节点
			int k = i * 2 + 1;
			// 从i结点的左子结点开始，也就是2i+1处开始，使得k指向i的左右子节点中较大的一个
			// 如果不存在右子节点，就不用比较了
			if (k + 1 < length && arr[k] < arr[k + 1]) {
				// 如果左子结点小于右子结点，k指向右子结点
				k++;
			}
			// 比较该较大节点与父节点
			if (arr[k] < arr[i])
				break;

			// 如果子节点大于父节点，将子节点和父节点交换
			swapForHeap(arr, i, k);
			// 更新父节点，调整下面的子树
			i = k;
		}
	}

	/**
	 * 交换元素
	 */
	private static void swapForHeap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	
	/**
	 * 希尔排序
	 * 
	 * @param arr
	 */
	public static void shellSort(int[] arr) {
		// 确定初始的增量gap，保证其不能越界
		int gap = 1;
		while (gap < arr.length / 3){
			gap = 3 * gap + 1;
		}
		// 对于相隔gap的元素进行插入排序
		while (gap >= 1) {
			for (int i = gap; i < arr.length; i++) {
				for (int j = i; j >= gap && arr[j] < arr[j - gap]; j -= gap)
					swapForShell(arr, j, j - gap);
			}
			gap = gap / 3;
		}

	}

	/*
	 * 交换数组元素（无需声明新对象的交换方法）
	 */
	private static void swapForShell(int[] arr, int a, int b) {
		arr[a] = arr[a] + arr[b];
		arr[b] = arr[a] - arr[b];
		arr[a] = arr[a] - arr[b];
	}

}
