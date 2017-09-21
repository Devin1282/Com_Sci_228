package edu.iastate.cs228.hw1;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MobileObjectTest {

	private Ground g;
	
	@Before
	public void setup()
	{
		g = new Ground(20,20);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void outOfBoundsOfGroundTest() {
		MobileObject x = new MobileObject(25,25, 0, 1, 0, g);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void lessThanZeroBoundTest() {
		MobileObject x = new MobileObject(-1,-1, 0, 1, 0, g);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeSpeedTest() {
		MobileObject x = new MobileObject(5,5, 0, -1, 0, g);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void badDirectionTest() {
		MobileObject x = new MobileObject(5,5, 0, 1, 9, g);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void badDirectionTest2() {
		MobileObject x = new MobileObject(5,5, 0, 1, -1, g);
	}
	
	@Test
	public void northWallTest() {
		MobileObject m = new MobileObject(0,0, 0, 1, 0, g);
		m.move();
		String msg = ("After moving towards the north wall, direction should change to south.");
		assertEquals(msg, 1, m.getDirection());
	}
	
	@Test
	public void westWallTest() {
		MobileObject m = new MobileObject(0,0, 0, 1, 3, g);
		m.move();
		String msg = ("After moving towards to west wall, direction should change to east.");
		assertEquals(msg, 2, m.getDirection());
	}
	
	@Test
	public void northWestCornerTest() {
		MobileObject m = new MobileObject(0, 0, 0, 1, 7, g);
		m.move();
		String msg = ("After moving towards the northwest corner, direction should change to southeast");
		assertEquals(msg, 5, m.getDirection());
	}
	
	@Test
	public void southWallTest() {
		MobileObject m = new MobileObject(20, 20, 0, 1, 1, g);
		m.move();
		String msg = ("After moving towards the south wall, direction should change to north");
		assertEquals(msg, 0, m.getDirection());
	}
	
	@Test
	public void eastWallTest() {
		MobileObject m = new MobileObject(20, 20, 0, 1, 2, g);
		m.move();
		String msg = ("After moving towards the east wall, direction should change to west");
		assertEquals(msg, 3, m.getDirection());
	}

	@Test
	public void southEastCornerTest() {
		MobileObject m = new MobileObject(20, 20, 0, 1, 5, g);
		m.move();
		String msg = ("After moving towards the southeast corner, direction should change to northwest");
		assertEquals(msg, 7, m.getDirection());
	}
	
	@Test
	public void northEastCornerTest() {
		MobileObject m = new MobileObject(20, 0, 0, 1, 4, g);
		m.move();
		String msg = ("AFter moving towards the northeast corner, direction should change to southwest");
		assertEquals(msg, 6, m.getDirection());
	}
	
	@Test
	public void southWestCornerTest() {
		MobileObject m = new MobileObject(0, 20, 0, 1, 6, g);
		m.move();
		String msg = ("After moving towards the southwest corner, direction should change to northeast");
		assertEquals(msg, 4, m.getDirection());
	}
	
	@Test
	public void xPostitionTest() {
		MobileObject m = new MobileObject(10, 10, 0, 1, 2, g);
		m.move();
		String msg = ("After moving the x-position should be 11");
		assertEquals(msg, 11, m.getCurrentX());
	}
	
	@Test
	public void yPositionTest() {
		MobileObject m = new MobileObject(10, 10, 0, 1, 2, g);
		m.move();
		String msg = ("After moving, the y-position should be 11");
		assertEquals(msg, 11, m.getCurrentY());
	}
	
	
}
