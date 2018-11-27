package jUnitTesting;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FileSaveGUITesting {

	@Test
	public void testEventListener() {
	  ActionListener subjectUnderTest = new MyActionListener();
	  ActionEvent mockEvent = mock(ActionEvent.class);
	
	  subjectUnderTest.actionPerformed(mockEvent);
		assertEquals(7, output);	//enter the expected outcome 
			
	}
}
