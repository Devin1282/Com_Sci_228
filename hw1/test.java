package edu.iastate.cs228.hw1;

import java.util.Scanner;

public class test {
	public static void main(String[] args) {
		String s = "WarningGeotask: 1, 3";
		Scanner scanner = new Scanner(s);
		int x = 0;
		int y = 0;
		scanner.next();
		while(scanner.hasNext())
		{
			scanner.useDelimiter(",");
			if(scanner.hasNext())
			{
				x = Integer.parseInt(scanner.next().trim());
				y = Integer.parseInt(scanner.next().trim());
			}
		}
		System.out.println(x);
		System.out.println(y);

	}

}
