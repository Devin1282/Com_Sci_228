package hw2;

public class MergeAndCount {
	
	public static MyObject2 mergeAndCount(int[] r1, int[] r2)
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
		MyObject2 mine2 = new MyObject2(arr, count);
		return mine2;
	}
	private static class MyObject2
	{
		private int[] arr;
		private int count;
		
		public MyObject2(int[] arr, int count)
		{
			this.arr = arr;
			this.count = count;
		}
	}
}
