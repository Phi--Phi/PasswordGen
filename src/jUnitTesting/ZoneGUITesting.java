package jUnitTesting;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZoneGUITesting{

	@Test
	public void test() {
	RandomGeneratorOptionsGUI validate = new RandomGeneratorOptionsGUI();
	int output = validate.generate(2);
	assertEquals(7, output);	
		
	}

}
