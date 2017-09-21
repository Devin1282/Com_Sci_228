package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class GroundTest {
	
	private Ground g;
	
	@Before
	public void setup()
	{
		g = new Ground(10, 10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void belowZeroGround() {
		Ground g1 = new Ground(-1, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void belowZeroGeotask() {
		g.addGeotask(new CounterGeotask(-1, -1));
	}
	
	@Test
	public void visitorCounterTest() {
		MobileObject m = new MobileObject(0, 1, 0, 1, 1, g);
		g.addGeotask(new CounterGeotask(1, 1));
		m.move();
		g.getGeotask(1, 1).getNumberOfVisitors();
	}

}
