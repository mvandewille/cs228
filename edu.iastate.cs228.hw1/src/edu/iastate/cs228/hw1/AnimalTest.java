package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Max Van de Wille
 *
 */
public class AnimalTest {

	@Test
	public void ageTest1()
	{
		Living badger = new Badger(null, 0, 0, 3);
		assertEquals(3, ((Animal)badger).myAge());
	}
	
	@Test
	public void ageTest2()
	{
		Living fox = new Fox(null, 0, 0, 8);
		assertEquals(8, ((Animal)fox).myAge());
	}
	
	@Test
	public void ageTest3()
	{
		Living rabbit = new Rabbit(null, 0, 0, 4);
		assertEquals(4, ((Animal)rabbit).myAge());
	}
}
