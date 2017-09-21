package hw2;


import java.util.Arrays;

public class SortAndCount {
	protected static MyObject1 sortAndCount(int[] r1)
	{
		MyObject1 mine1;
		MyObject1 mine2;
		MyObject1 mine3;
		MyObject1 mine4;
		if(r1.length == 1)
		{
			mine1 = new MyObject1(r1, 0);
			return mine1;
		}
		else
		{
			int mid = r1.length/2;
			int[] left = Arrays.copyOfRange(r1, 0, mid);
			int[] right = Arrays.copyOfRange(r1, mid, r1.length);
			mine2= sortAndCount(right);
			mine3= sortAndCount(left);
			mine4 = mergeAndCount(left, right);
			return new MyObject1(r1, (mine2.num + mine3.num + mine4.num));
		}
	}
	
	public static MyObject1 mergeAndCount(int[] r1, int[] r2)
	{
		int i = 1;
		int j = 1;
		int count = 0;
		int newArrayIndex = 0;
		int p = r1.length - 1;
		int q = r2.length -1;
		int[] arr = new int[r1.length + r2.length];
		while(i <= p && j <= q)
		{
			if(r1[i] < r2[j])
			{
				arr[newArrayIndex] = r1[i];
				i++;
				newArrayIndex++;
			}
			if(r2[j] < r1[i])
			{
				arr[newArrayIndex] = r2[j];
				count = count + (p - i + 1);
				j++;
				newArrayIndex++;
			}
		}
		if(i > p)
		{
			while(j <= q)
			{
				arr[newArrayIndex] = r2[j];
				j++;
				newArrayIndex++;
			}
		}
		if(j > q)
		{
			while(i <= p)
			{
				arr[newArrayIndex] = r1[i];
				i++;
				newArrayIndex++;
			}
		}
		MyObject1 mine2 = new MyObject1(arr, count);
		return mine2;
	}
	
	public static class MyObject1
	{
		private int[] arr;
		int num;
		public MyObject1(int[] arr, int num)
		{
			this.arr = arr;
			this.num = num;
		}
	}
}


