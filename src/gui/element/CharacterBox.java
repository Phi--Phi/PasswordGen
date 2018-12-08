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

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
/**
 * This class is responsible for allowing the user to specify
 * character values for non-zoned sections of the password.
 *
 */
public class CharacterBox extends JTextField {
	
	private static final long serialVersionUID = -0x29DAAB52E51465AL;
	private boolean selected = false;

	public CharacterBox() {
		// textField used that sets the width of the textbox to three
		setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
		setEditable(false);
		setText("x");
	
		// Key stroke listener, when the user enters the enter key or up, down, left, right arrow keys
		// the program will take the last character the user enters
		addKeyListener(new KeyAdapter(){
		    @Override
		    public void keyPressed(KeyEvent evt) {
		    	int e = evt.getKeyCode();
		    	setText(e == KeyEvent.VK_BACK_SPACE ? "xx"
						: e == KeyEvent.VK_DELETE || e == KeyEvent.VK_CONTROL || e == KeyEvent.VK_ALT
								|| e == KeyEvent.VK_WINDOWS || e == KeyEvent.VK_NUM_LOCK || e == KeyEvent.VK_CAPS_LOCK
								|| e == KeyEvent.VK_ESCAPE || e == KeyEvent.VK_ENTER || e == KeyEvent.VK_PAGE_DOWN
								|| e == KeyEvent.VK_PAGE_UP || e == KeyEvent.VK_END || e == KeyEvent.VK_HOME
								|| e == KeyEvent.VK_INSERT ? "x" : "");
		    }
		});
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void deselect() {
		selected = false;
	}
	
	public void select() {
		selected = true;
	}
	
}