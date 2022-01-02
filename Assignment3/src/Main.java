import java.util.Scanner;

import java.io.FileWriter;

import java.io.OutputStream;


public class Main {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String option = sc.nextLine();
		option = option.toUpperCase();
		
		/*
		if(option.equals("A")) // time result output
		{
			Test();
			return;
		}
		*/
		
		int N = sc.nextInt();
		int[] unsorted_array = new int[N];
		for(int i=0; i<N; i++)
		{
			unsorted_array[i] = sc.nextInt();
		}
				
		if(option.equals("B"))
		{
			BubbleSort(unsorted_array);
		}
		else if(option.equals("I"))
		{
			InsertionSort(unsorted_array);
		}
		else if(option.equals("Q"))
		{
			QuickSort(unsorted_array);
		}
		else if(option.equals("T"))
		{
			ThreeWayQuickSort(unsorted_array);
		}
		else if(option.equals("M"))
		{
			MergeSort(unsorted_array);
		}
		else if(option.equals("R"))
		{
			RadixSort(unsorted_array);
		}
		
		printArr(unsorted_array);
	}
	
	private static long max(long[][] time, int i ) {
		long max = time[0][i];
		
		for(int j=0; j<2; j++) {
			if(max < time[j][i] ) max= time[j][i];
		}
		
		return max;
	}
	
	private static long min(long[][] time, int i ) {
		long min = time[0][i];
		
		for(int j=0; j<2; j++) {
			if(min > time[j][i] ) min= time[j][i];
		}
		
		return min;
	}
	
	private static void Test() {
		
		long[][] timeConsume = new long[3][101];
		
		long[][] yAxis = new long[3][101];
		
		for(int i=0; i<= 100; i++) {	
			int[] arr = new int[i*1000];
			
			
			for(int order=0; order<3; order++) {
			
				for(int j=0; j< i*1000; j++) {
					
					arr[j] = (int)( (Math.random()*1000000)- 500000 );
				}
				
				long beforeTime = System.currentTimeMillis();
				//long beforeTime = System.nanoTime();
				
				//QuickSort(arr);
				//RadixSort(arr);
				//ThreeWayQuickSort(arr);
				//InsertionSort(arr);
				//MergeSort(arr);
				
				BubbleSort(arr);
				
				long afterTime = System.currentTimeMillis();
				//long afterTime = System.nanoTime();
				
				timeConsume[order][i] = afterTime - beforeTime;
				
			}
		}
		
		for(int i=0; i<=100; i++) {
			yAxis[0][i] = (timeConsume[0][i] + timeConsume[1][i]+ timeConsume[1][i])/3 ;
			
			yAxis[1][i] = max(timeConsume, i);
			yAxis[2][i] = min(timeConsume, i);
		}
		
		
		String path = "Downloads/Assignment3/src/bubble";
		
		for(int order=1; order<=3; order++) {
			
			String pathTmp = path+ String.valueOf(order)+".txt";
			
			try {
			
				FileWriter output = new FileWriter(pathTmp);
				for(int i=0; i<=100; i++) {
					
					
					output.write(String.valueOf(i*1000));
					
					output.write('\t');
					output.write(  String.valueOf((int)yAxis[order-1][i]) );
					output.write("\r\n");
				}
				
				output.close();
				
			} catch(Exception e) {
				System.out.println("----"+e);
				e.getStackTrace();
			}
		}
	}

	private static void BubbleSort(int[] arr) {
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */ 
		
		for(int i=0; i< arr.length -1 ; i++) {
			for(int j=0; j< arr.length -i-1 ; j++ ) {
				if( arr[j] > arr[j+1] ) {
					swap(arr, j,j +1);
				}
			}
		}
	}

	private static void InsertionSort(int[] arr) {
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */ 
		
		for(int i=1; i<arr.length; i++ ) {
			for(int j=i; j>0; j--) {
				if(arr[j] <arr[j-1] ) {
					swap(arr, j, j-1);
				}
				else {
					break;
				}
			}
		}
		
	}
	
	private static void QuickSort(int[] arr) {
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */ 	
		
		quickfun(arr, 0, arr.length-1);
		
		
	}
	
	private static void ThreeWayQuickSort(int[] arr) {
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */ 	
		
		quick3fun(arr, 0, arr.length - 1 );
		
	}
	
	private static void MergeSort(int[] arr) {
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */ 
		mergeArr(arr);
		
	}
	
	private static void RadixSort(int[] arr) {
		/*  ################ IMPLEMENT YOUR CODES HERE##################### */ 		

		
		int pMax =0, nMax = 0;
		int tmp1 = 0;
		int power = 1;
		
		int[] pArr = new int[arr.length];
		int[] nArr = new int[arr.length];
		int pSize = 0, nSize = 0;
		
		int size = arr.length;
		
		for(int i=0; i< size; i++) {
			if(arr[i]>=0 ) {
				pArr[pSize++] = arr[i];
			}
			
			else {
				nArr[nSize++] = -arr[i];
			}
		}
		
		
		
		for(int i=0; i< pSize; i++) {		
			
			tmp1 = pArr[i]; //(int) Math.log10((double)pArr[i])+1;
			if ( pMax < tmp1) pMax = tmp1;
		}
		
		for(int i=0; i< nSize; i++) {
			tmp1 = nArr[i]; //(int) Math.log10((double)nArr[i])+1;
			if ( nMax < tmp1) nMax = tmp1;
		}
		
		while( pMax/power >0 ) {
			countSort(pArr, power, pSize);
			power *= 10;
		}
		power = 1;
		while( nMax/power >0) {
			countSort(nArr, power, nSize);
			power *=10;
		}
		
		for(int i=0; i<nSize; i++) {
			arr[i] = nArr[i];
		}
		for(int i=nSize; i<size; i++ ) {
			arr[i] = pArr[i-nSize];
		}
		
		//printArr(arr);
	}
	
	public static void countSort(int[] data, int power, int size) {
		int[] out= new int[size];
		
		int[] count = new int[10];
		
		for(int i=0; i<10; i++) {
			count[i] = 0;
		}
		
		for(int i=0; i<size; i++) {
			count[ (data[i]/power)%10 ] ++;
		}
		for(int i=1; i<10; i++) {
			count[ i] += count[i-1];
			
		}
		for(int i= size-1; i>=0; i--) { 
			out[ count[(data[i]/power)%10] -1 ] = data[i];
			count[(data[i]/power)%10]--; 
		}
		for(int i=0; i<size; i++) {
			data[i] = out[i];
		}

	}
	
	
	private static void swap(int[] arr, int i, int j ) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	private static void printArr(int arr[]) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i]+" ");
		}
		
		System.out.println();
	}
	
	private static void quick3fun(int arr[], int left, int right) {

		
		if( left >=right) return;
		
		int pivot1 = partition(arr, left, right);
		
		int pivot2 = partition(arr, pivot1 , right );
		
		quick3fun(arr, left, pivot1 -1);
		quick3fun(arr, pivot1 +1,  pivot2 -1);
		quick3fun(arr, pivot2 +1, right );
		
	}
	
	public static int partition3(int arr[], int leftBound, int rightBound) {
		int left = leftBound -1;
		int right = rightBound;
		
		int pivot1 = arr[leftBound];
		int pivot2 = arr[rightBound];
		
		while(true) {
			
			while( arr[++left] <pivot1 ) { ;}
			while ( right> leftBound && arr[--right] > pivot1 ) {;}
				
		}
		
	}
	public static int partition(int[] arr, int leftBound, int rightBound) {
		
		int pivot = arr[rightBound];
		int left = leftBound-1;
		int right = rightBound;
		
		while(true) {
			while(arr[++left]< pivot) {;}
			while(right>leftBound && arr[--right] >pivot) {;}
			if(left>=right) break;
			else swap(arr, left, right);
		}
		
		swap(arr, left, rightBound);
		
		return left;
	}
	
	private static void quickfun(int arr[] , int left, int right ) {
		if( left>= right ) {
			return;
		}
		
		int pivot = partition(arr, left, right);
		
		quickfun(arr, left, pivot -1);
		quickfun(arr, pivot+1, right);
		
		
	}
	
	
	
	private static void mergeArr(int arr[]) {
		if( arr ==null || arr.length <=1 ) {
			return;
		}
		int middle = arr.length /2;
		int[] left = new int[middle];
		int[] right = new int[arr.length - middle];
		
		for(int i=0; i<middle; i++) {
			left[i] = arr[i];
		}
		for(int i= 0 ; i< arr.length - middle; i++) {
			right[i] = arr[middle+i];
		}
		
		mergeArr(left);
		mergeArr(right);
		
		int l = 0;
		int r = 0;
		
		for(int i=0; i< arr.length; i++) 
		{
			if( r >= right.length || (l < left.length && left[l]<right[r] )) {
				arr[i] = left[l++];
			}
			else {
				arr[i] = right[r++];
			}
		}
	}
	
}