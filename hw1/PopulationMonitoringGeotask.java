package edu.iastate.cs228.hw1;

/**
 * Geotask that monitors the current population of mobile units and alerts when
 * the number goes above or below a threshold
 */
public class PopulationMonitoringGeotask extends Geotask {

    /**
     * The population threshold to alert the user
     */
    private int alertThreshold = 0;

    /**
     * The current population
     */
    private int currentPopulation = 0;
    

    /**
     * Constructs a new PopulationMonitoringGeotask
     *
     * @param x              the x coordinate
     * @param y              the y coordinate
     * @param alertThreshold the threshold of which to alert the user
     * @throws java.lang.IllegalArgumentException if x or y is less than 0
     */
    public PopulationMonitoringGeotask(int x, int y, int alertThreshold) {
    	super(x, y);
    	this.alertThreshold = alertThreshold;
    	if(x < 0 || y < 0)
    	{
    		throw new IllegalArgumentException();
    	}
    }

    @Override
    public void moveIn(MobileObject mo) {
    	int tracker = currentPopulation;
    	currentPopulation++;
    	if(tracker < alertThreshold)
    	{
    		if(currentPopulation >= alertThreshold)
    		{
    			System.out.println("too crowded");
    		}
    	}
    	
    }

    @Override
    public void moveOut(MobileObject mo) {
    	int tracker = currentPopulation;
    	currentPopulation--;
    	if(tracker >= alertThreshold)
    	{
    		if(currentPopulation < alertThreshold)
    		{
    			System.out.println("no longer too crowded");
    		}
    	}
    }
    
    @Override
    public void printType() {
    	System.out.println("This is a PopulationMonitoringGeotask");
    }
}
