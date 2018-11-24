/* Copyright 2018 Philip Klein

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;

import gui.element.ZoneSpec;

public class PasswordGeneratorZoneGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5435024379805897010L;
	private static final int MAX_ZONES = 30;
	private RandomGeneratorOptionsGUI parent;
	private Vector<ZoneSpec> specs = new Vector<ZoneSpec>();

	public PasswordGeneratorZoneGUI(RandomGeneratorOptionsGUI rgo) {
		setMinimumSize(new Dimension(500,300));
		setLayout(new BorderLayout());
		parent = rgo;
		resetZones();
		
	}
	
	public void addZone(int startPos) {
		ZoneSpec temp = new ZoneSpec();
		temp.setSize(parent.getNumberOfCharacters());
		temp.moveStart(startPos);
		temp.moveEnd(startPos + 1);
	}
	
	/**
	 * clears all zone configuration and resets it to one zone with lowercase checked
	 */
	public synchronized void resetZones() {
		ZoneSpec temp = new ZoneSpec();
		temp.setSize(parent.getNumberOfCharacters());
		specs.clear();
		specs.add(temp);
		
	}
	
}

/*
// textField used that sets the width of the textbox to three
textField = new TextField(3);
panel.add(textField);

lblNewLabel = new JLabel("X");
panel.add(lblNewLabel);
*/



/*
// Key stroke listener, when the user enters the enter key or up, down, left, right arrow keys
// the program will take the last character the user enters
textField.addKeyListener(new KeyAdapter(){
    @Override
    public void keyPressed(KeyEvent evt) {
    	int e = evt.getKeyCode();

    	if (e == KeyEvent.VK_ENTER || e == KeyEvent.VK_UP || e == KeyEvent.VK_DOWN 
    			|| e == KeyEvent.VK_LEFT || e == KeyEvent.VK_RIGHT){
    		/* 
    		 Once the user enters a key stroke, the code will determine whether the
    		 entry field is empty, if the field is empty, the program will set text to "Empty!"
    		 and the last character obtained will be "!".
    		 
    		 If the field isn't empty, then the code will proceed to obtain the
    		 last character in the text field and change the values as the user 
    		 enters in the proper key stroke.
    		 
	    	if(textField.getText() == null || "".equals(textField.getText().toString())){
	    		textField.setText("Empty!"); 
	    	}
	    	else {
	    		lblNewLabel.setText(textField.getText().substring(textField.getText().length()-1));
	    	}
    	}
    }
}); */
