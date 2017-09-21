package edu.iastate.cs228.hw1;

import java.util.ArrayList;

import edu.iastate.cs228.hw1.Ground;

/**
 * Mobile Object that traverses the ground
 */
public class MobileObject {

    /**
     * The ground which the MobileObjhect resides on
     */
    private final Ground ground;
    /**
     * The id of the mobileObject
     */
    private final int ID;
    /**
     * The current x coordinate
     */
    private int currentX;
    /**
     * The current y coordinate
     */
    private int currentY;
    /**
     * The speed of the MobileObject
     */
    private int speed;
    /**
     * The direction of the MobileObject
     * 0-7 correlates to N, S, E, W, NE, SE, SW, NW
     */
    private int direction;

    /**
     * Constructs a new MobileObject
     *
     * @param ID        the ID of the object
     * @param currentX  the x coordinate of the object
     * @param currentY  the y coordinate of the object
     * @param speed     the speed of the object
     * @param direction the direction of the object
     * @param ground    the Ground on which the object resides
     * @throws java.lang.IllegalArgumentException if the cell coordinates, speed, or direction is out of bounds (e.g., a speed that is less than 0; a direction that is less than 0 or greater than 7)  
     */
    public MobileObject(int ID, int currentX, int currentY, int speed, int direction, Ground ground) {
    	this.ID = ID;
    	this.currentX = currentX;
    	this.currentY = currentY;
    	this.speed = speed;
    	this.direction = direction;
    	this.ground = ground;
    	if(currentX > ground.getDimensionX() || currentX < 0)
    	{
    		throw new IllegalArgumentException();
    	}
    	if(currentY > ground.getDimensionY() || currentY < 0)
    	{
    		throw new IllegalArgumentException();
    	}
    	if(speed < 0)
    	{
    		throw new IllegalArgumentException();
    	}
    	if(direction < 0 || direction > 7)
    	{
    		throw new IllegalArgumentException();
    	}
    }

    /**
     * Gets the x coordinate of the mobileObject
     */
    public int getCurrentX() {
    	return currentX;
    }


    /**
     * Gets the y coordinate of the mobileObject
     */
    public int getCurrentY() {
    	return currentY;
    }

    /**
     * Gets the speed of the MobileObject
     */
    public int getSpeed() {
    	return speed;
    }

    /**
     * Gets the direction of the MobileObject
     */
    public int getDirection() {
    	return direction;
    }
    /**
     * Gets the ID of the MobileObject
     */
    public int getID() {
    	return ID;
    }

    /**
     * Moves according to the speed
     */
    public void move() {
    	int movesLeft = speed;
    	ArrayList<Geotask> arr = ground.getGeotask(this);
    	for(int i = 0; i < arr.size(); i++)
    	{
    		arr.get(i).moveOut(this);
    	}
    	while(movesLeft > 0)
    	{
    		moverHelper(direction);
    		movesLeft--;
    	}
    	ArrayList<Geotask> arr1 = ground.getGeotask(this);
    	for(int i = 0; i < arr1.size(); i++)
    	{
    		arr1.get(i).moveIn(this);
    	}
    }
    private void moverHelper(int dir)
    {
    	int[][] arr = {{0,-1},{0,1},{1,0},{-1,0},{1,0},{1,1},{-1,1},{-1,-1}};
    	//crossing the west wall
    	if(currentX + arr[dir][0] < 0)
    	{
    		//crossing the northwest corner
    		if(currentY + arr[dir][1] < 0)
    		{
    			dir = 5;
    		}
    		//crossing the southwest corner
    		else if(currentY + arr[dir][1] > ground.getDimensionY())
    		{
    			dir = 4;
    		}
    		else
    		{
    			if(dir == 3)
    			{
    				dir = 2;
    			}
    			else if(dir == 5)
    			{
    				dir = 6;
    			}
    			else if(dir == 7)
    			{
    				dir = 4;
    			}
    		}
    		
    	}
    	//crossing the east wall
    	else if(currentX + arr[dir][0] > ground.getDimensionX())
    	{
    		//crossing the northeast corner
    		if(currentY + arr[dir][1] < 0)
    		{
    			dir = 6;
    		}
    		//crossing the southeast corner
    		else if(currentY + arr[dir][1] > ground.getDimensionY())
    		{
    			dir = 7;
    		}
    		else
    		{
    			if(dir == 2)
    			{
    				dir = 3;
    			}
    			else if(dir == 4)
    			{
    				dir = 7;
    			}
    			else if(dir == 5)
    			{
    				dir = 6;
    			}
    		}
    	}
    	//crossing the north wall
    	else if(currentY + arr[dir][1] < 0)
    	{
    		if(dir == 0)
    		{
    			dir = 1;
    		}
    		else if(dir == 4)
    		{
    			dir = 5;
    		}
    		else if(dir == 7)
    		{
    			dir = 6;
    		}
    	}
    	//crossing the south wall
    	else if(currentY + arr[dir][1] > ground.getDimensionY())
    	{
    		if(dir == 1)
    		{
    			dir = 0;
    		}
    		else if(dir == 5)
    		{
    			dir = 4;
    		}
    		else if(dir == 6)
    		{
    			dir = 7;
    		}
    	}
    	
    	else
    	{
    		currentX += arr[dir][0];
    		currentY += arr[dir][1];
    	}
    	direction = dir;
    }
    }
