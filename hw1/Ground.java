package edu.iastate.cs228.hw1;

import java.util.ArrayList;

/**
 * Class representing the ground where geotasks and mobile objects are placed
 */
public class Ground {

    /**
     * The x dimension
     */
    private int dimensionX;
    /**
     * The y dimension
     */
    private int dimensionY;
    /**
     * List of all Geotasks
     */
    private ArrayList<Geotask> geotasks;

    /**
     * Constructs a new Ground object
     *
     * @param dimensionX the size on the x axis
     * @param dimensionY the size on the y axis
     * @throws java.lang.IllegalArgumentException if either dimension is less than 1
     */
    public Ground(int dimensionX, int dimensionY) {
    	this.dimensionX = dimensionX;
    	this.dimensionY = dimensionY;
    	geotasks = new ArrayList<Geotask>();
    	if(dimensionX <= 0 || dimensionY <= 0)
    	{
    		throw new IllegalArgumentException();
    	}
    }

    /**
     * Adds a Geotask
     *
     * @param t the task to add
     */
    public void addGeotask(Geotask t) {
    	geotasks.add(t);
    }

    /**
     * Gets the x dimension
     * @return dimensionX
     */
    public int getDimensionX() {
    	return dimensionX;
    }

   /**
    * Gets the y dimension
    * @return dimensionY
    */
    public int getDimensionY() {
    	return dimensionY;
    }

    /**
     * Gets the Geotask belonging to a cell
     *
     * @param x the x coordinate of the Geotask
     * @param y the y coordinate of the Geotask
     * @return an ArrayList of geotasks
     */
    public ArrayList<Geotask> getGeotask(int x, int y) {
    	ArrayList<Geotask> geotasks1 = new ArrayList<Geotask>();
    	for(int i = 0; i < geotasks.size(); i++)
    	{
    		if(geotasks.get(i).getX() == x && geotasks.get(i).getY() == y)
    		{
    			geotasks1.add(geotasks.get(i));
    		}
    	}
    	return geotasks1;
    }

    /**
     * Gets the Geotask belonging to a mobile object
     *
     * @param mo the MobileObject to get Geotasks belonging to
     * @return an ArrayList of geotasks.
     */
    public ArrayList<Geotask> getGeotask(MobileObject mo) {
    	return getGeotask(mo.getCurrentX(), mo.getCurrentY());
    }
}
