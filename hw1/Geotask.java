package edu.iastate.cs228.hw1;

/**
 * Abstract class representing shared logic for Geotasks
 */
public abstract class Geotask implements IGeotask {

    /**
     * Number of Geotasks created
     */
    private static int totalNumberOfGeotaskEverCreated = 0;
    /**
     * x coordinate of Geotask
     */
    private int x;
    /**
     * y coordinate of Geotask
     */
    private int y;

    /**
     * Constructs the base for a Geotask
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @throws java.lang.IllegalArgumentException if x or y is less than 0
     */
    public Geotask(int x, int y) {
    	this.x = x;
    	this.y = y;
    	totalNumberOfGeotaskEverCreated++;
    	if(x < 0 || y < 0)
    	{
    		throw new IllegalArgumentException();
    	}
    }

    /**
     * Gets the number of geotasks created
     */
    public static int getTotalNumberOfGeotaskEverCreated() {
    	return totalNumberOfGeotaskEverCreated;
    }

    /**
     * Gets the x coordinate
     */
    public int getX() {
    	return x;
    }

    /**
     * Gets the y coordinate
     */
    public int getY() {
    	return y;
    }

    /**
     * Prints the type of Geotask to the console
     */
    public void printType() {
    	System.out.println("This is a generic geotask");
    }
}
