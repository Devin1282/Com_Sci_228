package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Simulates the interaction between objects
 */
public class Simulator {
    public static void main(String[] args) throws FileNotFoundException {
    	try {
    		File f = new File("Simulation.Configuration");
        	Scanner scanner = new Scanner(f);
        	//How do I do the exceptions?
        	int dimensionX = 0;
        	int dimensionY = 0;
        	int numberOfMobileObjects = 0;
        	int durationOfSimulationTime = 0;
        	Ground ground = null;
        	while(scanner.hasNextLine())
        	{
        		String s = scanner.nextLine();
        		Scanner lineScanner = new Scanner(s);
        		String type = lineScanner.next();
        		if(dimensionX != 0 && dimensionY != 0 && ground == null)
        		{
        			ground = new Ground(dimensionX, dimensionY);
        		}
        		else if(type.equals("dimensionX:"))
        		{
        			dimensionX = lineScanner.nextInt();
        		}
        		else if(type.equals("dimensionY:"))
        		{
        			dimensionY = lineScanner.nextInt();
        		}
        		if(type.equals("numberOfMobileObjects:"))
        		{
        			numberOfMobileObjects = lineScanner.nextInt();
        		}
        		else if(type.equals("durationOfSimulationTime:"))
        		{
        			durationOfSimulationTime = lineScanner.nextInt();
        		}
        		else if(type.equals("WarningGeotask:"))
        		{
        			lineScanner.useDelimiter(",");
        			if(lineScanner.hasNext())
        			{
        				int x = Integer.parseInt(lineScanner.next().trim());
        				int y = Integer.parseInt(lineScanner.next().trim());
        				ground.addGeotask(new WarningGeotask(x,y));
        			}
        		}
        		else if(type.equals("CounterGeotask:"))
        		{
        			lineScanner.useDelimiter(",");
        			if(lineScanner.hasNext())
        			{
        				int x = Integer.parseInt(lineScanner.next().trim());
        				int y = Integer.parseInt(lineScanner.next().trim());
        				ground.addGeotask(new CounterGeotask(x,y));
        			}
        		}
        		else if(type.equals("PopuplationMonitoringGeotask:"))
        		{
        			lineScanner.useDelimiter(",");
        			if(lineScanner.hasNext())
        			{
        				int x = Integer.parseInt(lineScanner.next().trim());
        				int y = Integer.parseInt(lineScanner.next().trim());
        				int z = Integer.parseInt(lineScanner.next().trim());
        				ground.addGeotask(new PopulationMonitoringGeotask(x,y,z));
        			}
        		}
        		
        	}
        	

        	MobileObject[] objects = new MobileObject[numberOfMobileObjects];
        	for(int i = 0; i < numberOfMobileObjects; i++)
        	{
        		objects[i] = new MobileObject(i, i%dimensionX, i%dimensionY, 1, i%8, ground);
        	}
        	for(int i = 0; i <= durationOfSimulationTime; i++)
        	{
        		for(int j = 0; j < objects.length; j++)
        		{
        			objects[j].move();
        			System.out.println(objects[j].getID() + ": (" + objects[j].getCurrentX() + "," +
        			objects[j].getCurrentY() + ")");
        			
        			
        		}
        	}
    	}
    	catch (FileNotFoundException e) {
    		System.out.println("FileNotFoundException: " + e.getMessage());
    	}
    	catch (NullPointerException e) {
    		System.out.println("NullPointerException: " + e.getMessage());
    	}
    	catch (IllegalStateException e) {
    		System.out.println("IllegalStateException: " + e.getMessage());
    	}
    	catch (IllegalArgumentException e) {
    		System.out.println("IllegalArguementException: " + e.getMessage());
    	}
    	catch (IOException e) {
    		System.out.println("IOException: " + e.getMessage());
    	}
    	
    }
}
