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

package gui.element;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class CharacterBox extends JTextField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -188494454562309722L;

	public CharacterBox() {
		// textField used that sets the width of the textbox to three
		
		// Key stroke listener, when the user enters the enter key or up, down, left, right arrow keys
		// the program will take the last character the user enters
		addKeyListener(new KeyAdapter(){
		    @Override
		    public void keyPressed(KeyEvent evt) {
		    	int e = evt.getKeyCode();
		    	if (e == KeyEvent.VK_BACK_SPACE) {
		    		setText("xx");
		    	}else if(e != KeyEvent.VK_ALPHANUMERIC) {
		    		setText("x");
		    	}
		    	else {
		    		setText("");
		    	}
		    }
		}); 
	}
}