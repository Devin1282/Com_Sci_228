package hw2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/*
 * A Ranking object represents a ranking of a set of items numbered 1 through n, for some n. 
 * The Ranking class contains various methods to construct rankings. Once a ranking is constructed,
 * you can determine how many items it ranks and obtain the rank of some specific item. You can
 * also compute distances between rankings. Ranking objects are immutable; that is, once created,
 * they cannot be modified.
 * 
 * @author Devin Johnson
 */

public class Ranking extends SortAndCount
{
	private String[] names;
	private int[] rank;
	private RankingObject[] rankSorted;
	private RankingObject[] stringSorted;

	/*
	 * Returns the number of items in the ranking. Must run in O(1) time.
	 */
	public int getNumItems() {
		return rankSorted.length;
	}

	/*
	 * Returns the string of rank i. Throws an IllegalArgumentException if i is
	 * not between 1 and this.getNumItems(). Must run in O(1) time.
	 */
	public String getStringOfRank(int i) {
		if (i < 1 || i > this.getNumItems()) {
			throw new IllegalArgumentException();
		}
		return rankSorted[i - 1].name;
	}

	/*
	 * Returns the rank of name. Throws an IllegalArgumentException if name is
	 * not present in the ranking. Must run in O(log n) time, where n =
	 * this.getNumItems().
	 */
	public int getRankOfString(String name) {
		int i = binarySearch(stringSorted, name);
		if (i != -1) {
			return stringSorted[i].rank;
		} else {
			throw new IllegalArgumentException();
		}

	}

	/*
	 * Constructs a ranking sigma of the strings in the array names, where
	 * sigma(names[i]) = rank[i] for each i between 0 and names.length - 1.
	 * Throws a NullPointerException if names is null or if rank is null. Throws
	 * an IllegalArgumentException if names.length != rank.length, if names
	 * contains duplicate strings or one or more null strings, or if rank does
	 * not consist of distinct elements between 1 and rank.length. Must run in
	 * O(n log n) time, where n = names.length.
	 */
	public Ranking(String[] names, int[] rank) {
		if (names.length != rank.length) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < names.length; i++) {
			if (names[i] == null || names[i] == names[i + 1]) {
				throw new IllegalArgumentException();
			}
		}
		for (int i = 0; i < rank.length; i++) {
			if (rank[i] == rank[i + 1] || rank[i] > rank.length || rank[i] == 0) {
				throw new IllegalArgumentException();
			}
		}
		RankingObject[] r = new RankingObject[names.length];
		for (int i = 0; i < names.length; i++) {
			if (names[i] == null || rank[i] == 0) {
				throw new NullPointerException();
			}
			r[i] = new RankingObject(names[i], rank[i]);
		}
		RankCompare ranker = new RankCompare();
		StringCompare stringer = new StringCompare();
		stringSorted = mergeSort(r, stringer);
		rankSorted = mergeSort(r, ranker);
		this.rank = rank;
		this.names = names;
	}

	/*
	 * Constructs a ranking sigma of the strings in the array names, where
	 * sigma(names[i]) = k if and only if scores[i] is the kth largest element
	 * in the array scores. Throws a NullPointerException if names is null or if
	 * scores is null. Throws an IllegalArgumentException if names.length !=
	 * scores.length, if names contains duplicate strings or one or more null
	 * strings, or if scores contains duplicate values. Must run in O(n log n)
	 * time, where n = names.length.
	 */
	public Ranking(String[] names, float[] scores) {
		if (names.length != scores.length) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < names.length; i++) {
			if (names[i] == null || names[i] == names[i + 1]) {
				throw new IllegalArgumentException();
			}
			if (scores[i] == scores[i + 1] || scores[i] == 0) {
				throw new IllegalArgumentException();
			}
		}
		RankingObject[] r = new RankingObject[names.length];
		int[] newRanks = new int[scores.length];
		float[] scoresCopy = Arrays.copyOfRange(scores, 0, scores.length);
		Arrays.sort(scoresCopy);
		for (int i = 0; i < scores.length; i++) {
			int newRank = 1 + (binarySearch(scoresCopy, scores[i]));
			newRanks[i] = newRank;
		}
		for (int i = 0; i < names.length; i++) {
			if (names[i] == null || scores[i] == 0) {
				throw new NullPointerException();
			}
			r[i] = new RankingObject(names[i], rank[i]);
		}
		StringCompare stringer = new StringCompare();
		stringSorted = mergeSort(r, stringer);
		RankCompare ranker = new RankCompare();
		rankSorted = mergeSort(r, ranker);
		this.names = names;
		rank = newRanks;
	}

	/*
	 * Constructs a random ranking of the strings in the array names. Throws a
	 * NullPointerException if names is null. Throws an IllegalArgumentException
	 * if names contains duplicates or one or more null strings. Must run in O(n
	 * log n) time, where n = names.length.
	 */
	public Ranking(String[] names, long seed) {
		// NOTE: Use this random generator to get your random ranking
		Random gen = new Random(seed);
		for (int i = 0; i < names.length; i++) {
			if (names[i] == null) {
				throw new NullPointerException();
			}
		}
		for (int i = names.length - 1; i > 0; i--) {
			int j = gen.nextInt(i + 1);
			String temp = names[i];
			names[i] = names[j];
			names[j] = temp;
		}
		RankingObject[] r = new RankingObject[names.length];
		int[] nums = new int[names.length];
		for (int i = 0; i < names.length; i++) {
			nums[i] = i + 1;
			r[i] = new RankingObject(names[i], nums[i]);
		}
		StringCompare stringer = new StringCompare();
		stringSorted = mergeSort(r, stringer);
		RankCompare ranker = new RankCompare();
		rankSorted = mergeSort(r, ranker);
	}

	/*
	 * Returns a string representation of this ranking. The string should be a
	 * comma-separated list of the names in the ranking, ordered by rank,
	 * starting at rank 1.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < rankSorted.length; i++) {
			s = s + rankSorted[i].name;
		}
		return s;
	}

	/*
	 * Returns true if r1 and r2 are rankings of the same set of strings;
	 * otherwise, returns false. Throws a NullPointerException if either r1 or
	 * r2 is null. Must run in O(n) time, where n is the number of elements in
	 * r1 (or r2).
	 */
	public static boolean sameNames(Ranking r1, Ranking r2) {
		if (r1 == null || r2 == null) {
			throw new NullPointerException();
		}
		boolean sameNames = true;
		for (int i = 0; i < r1.stringSorted.length; i++) {
			if (!r1.stringSorted[i].equals(r2.stringSorted[i])) {
				sameNames = false;
			}
		}
		return sameNames;
	}

	/*
	 * Returns true if this and other are rankings of the same set of strings;
	 * otherwise, returns false. Throws a NullPointerException if other is null.
	 * Must run in O(n) time, where n is the number of elements in this (or
	 * other).
	 */
	public boolean sameNames(Ranking other) {
		return sameNames(this, other);
	}

	/*
	 * Returns the Kemeny distance between r1 and r2. Throws a
	 * NullPointerException if either r1 or r2 is null. Throws an
	 * IllegalArgumentException if r1 and r2 rank different sets of strings.
	 * Must run in O(n log n) time, where n is the number of elements in r1 (or
	 * r2).
	 */
	public static int kemeny(Ranking r1, Ranking r2) {
		if (r1 == null || r2 == null) {
			throw new NullPointerException();
		}
		if (!sameNames(r1, r2)) {
			throw new IllegalArgumentException();
		}
		int[] newSpot = new int[r1.rankSorted.length];
		for (int i = 0; i < r1.rankSorted.length; i++) {
			int newIndex = binarySearch(r2.rankSorted, r1.rankSorted[i].rank);
			newSpot[i] = r2.rankSorted[newIndex].rank;
		}
		RankingObject[] k = new RankingObject[r1.rankSorted.length];
		for(int i = 0; i < r1.rankSorted.length; i++)
		{
			k[i].name = r1.rankSorted[i].name;
			k[i].rank = r1.rankSorted[i].rank;
			k[i].rank1 = newSpot[i];
		}
		int[] q = new int[r1.rankSorted.length + r2.rankSorted.length];
		int i = 0;
		while(i < r1.rankSorted.length)
		{
			q[i] = k[i].rank;
			i++;
		}
		int j = 0;
		while(j < r2.rankSorted.length)
		{
			q[j] = k[i].rank1;
		}
		return sortAndCount(q).num;
	}

	/*
	 * Returns the Kemeny distance between this and other. Throws a
	 * NullPointerException if other is null. Throws an IllegalArgumentException
	 * if this and other rank different sets of strings. Must run in O(n log n)
	 * time, where n is the number of elements in this (or other).
	 */
	public int kemeny(Ranking other) {
		return kemeny(this, other);
	}

	/*
	 * Returns the footrule distance between r1 and r2. Throws a
	 * NullPointerException if either r1 or r2 is null. Throws an
	 * IllegalArgumentException if r1 and r2 rank different sets of strings.
	 * Must run in O(n) time, where n is the number of elements in r1 (or r2).
	 */
	public static int footrule(Ranking r1, Ranking r2) {
		if (r1 == null || r2 == null) {
			throw new NullPointerException();
		}
		if (!sameNames(r1, r2)) {
			throw new IllegalArgumentException();
		}
		int total = 0;
		for (int i = 0; i < r1.stringSorted.length; i++) {
			total += Math
					.abs(r1.stringSorted[i].rank - r2.stringSorted[i].rank);
		}
		return total;
	}

	/*
	 * Returns the footrule distance between this and other. Throws a
	 * NullPointerException if other is null. Throws an IllegalArgumentException
	 * if this and other rank different sets of strings. Must run in O(n) time,
	 * where n is the number of elements in this (or other).
	 */
	public int footrule(Ranking other) {
		return footrule(this, other);
	}

	private static RankingObject[] mergeSort(RankingObject[] arr,
			Comparator<RankingObject> c) {
		if (arr.length <= 1) {
			return arr;
		}
		int mid = arr.length / 2;
		RankingObject[] left = Arrays.copyOfRange(arr, 0, mid);
		RankingObject[] right = Arrays.copyOfRange(arr, mid, arr.length);
		mergeSort(left, c);
		mergeSort(right, c);

		return merge(left, right, c);

	}

	private static RankingObject[] merge(RankingObject[] left,
			RankingObject[] right, Comparator<RankingObject> c) {
		RankingObject[] newArray = new RankingObject[left.length + right.length];
		int leftIndex = 0;
		int rightIndex = 0;
		int newArrayIndex = 0;
		while (leftIndex < left.length && rightIndex < right.length) {
			if (c.compare(left[leftIndex], right[rightIndex]) <= 0) {
				newArray[newArrayIndex] = left[leftIndex];
				leftIndex++;
				newArrayIndex++;

			} else {
				newArray[newArrayIndex] = right[rightIndex];
				rightIndex++;
				newArrayIndex++;
			}
		}
		if (leftIndex >= left.length) {
			while (rightIndex < right.length) {
				newArray[newArrayIndex] = right[rightIndex];
				rightIndex++;
				newArrayIndex++;
			}
		}
		if (rightIndex >= right.length) {
			while (leftIndex < left.length) {
				newArray[newArrayIndex] = left[leftIndex];
				leftIndex++;
				newArrayIndex++;
			}
		}
		return newArray;
	}

	private static int binarySearch(RankingObject[] arr, String key) {
		int low = 0;
		int high = arr.length - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (key.compareTo(arr[mid].name) < 0) {
				high = mid;
			} else if (key.compareTo(arr[mid].name) > 0) {
				low = mid + 1;
			} else if (arr[mid].name.equals(key)) {
				return mid;
			}
		}
		return -1;
	}

	private static int binarySearch(float[] arr, float key) {
		int low = 0;
		int high = arr.length - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (key < arr[mid]) {
				high = mid;
			} else if (key > arr[mid]) {
				low = mid + 1;
			} else if (key == arr[mid]) {
				return mid;
			}
		}
		return -1;
	}
	
	private static int binarySearch(RankingObject[] arr, int key) {
		int low = 0;
		int high = arr.length - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (key < arr[mid].rank) {
				high = mid;
			} else if (key > arr[mid].rank) {
				low = mid + 1;
			} else if (arr[mid].rank == key) {
				return mid;
			}
		}
		return -1;
	}

	private class StringCompare implements Comparator<RankingObject> {

		@Override
		public int compare(RankingObject o1, RankingObject o2) {
			return o1.name.compareTo(o2.name);
		}

	}

	private class RankCompare implements Comparator<RankingObject> {

		@Override
		public int compare(RankingObject o1, RankingObject o2) {
			int compare = 0;
			if (o1.rank < o2.rank) {
				compare = -1;
			} else if (o1.rank == o2.rank) {
				compare = 0;
			} else if (o1.rank > o2.rank) {
				compare = 1;
			}
			return compare;
		}

	}

	private class RankingObject {
		private String name;
		private float scores;
		private int rank;
		private int rank1;

		public RankingObject(String name, int rank) {
			this.name = name;
			this.rank = rank;
		}

		public RankingObject(String name, int rank, int rank1) {
			this.name = name;
			this.rank = rank;
			this.rank1 = rank1;
		}
	}
	
	
}
