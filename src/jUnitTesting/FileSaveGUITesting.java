package jUnitTesting;

import static org.junit.Assert.assertEquals;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

public class FileSaveGUITesting {
	@Test
	public void testEventListener() {
	  FileSaveGUITesting subjectUnderTest = new FileSaveGUITesting();
	  ActionEvent mockEvent = subjectUnderTest.actionPerformed(null);
	
	  assertEquals(7, mockEvent);	//enter the expected outcome 
			
	}
}
