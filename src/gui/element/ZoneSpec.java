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

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class ZoneSpec extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -375743799737480566L;
	private JCheckBox lower, upper, number, special;
	private int size;
	private Zone z;
	
	public ZoneSpec() {
		size = 0;
		setupOptions();
		z = new Zone(size);
	}
	
	/**
	 * this function sets up the options checkboxes
	 */
	private void setupOptions() {
		
		lower 	= new JCheckBox("Lowercase letters");
		upper 	= new JCheckBox("Uppercase letters");
		number 	= new JCheckBox("Numeric");
		special = new JCheckBox("Special characters");
		//default will have lower selected
		lower.setSelected(true);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(lower);
		add(upper);
		add(number);
		add(special);
		
	}
	
	public Zone getZone() {
		return z;
	}
	
	public void setSize(int size) {
		this.size = size;
		z.setSize(size);
	}
	
	/**
	 * 
	 * @return A boolean value TRUE if the user checked the lowercase option,
	 * and FALSE otherwise
	 */
	public boolean lowercase() {
		
		return lower.isSelected();
		
	}
	
	/**
	 * 
	 * @return A boolean value TRUE if the user checked the uppercase option,
	 * and FALSE otherwise
	 */
	public boolean uppercase() {
		
		return upper.isSelected();
		
	}
	
	/**
	 * 
	 * @return A boolean value TRUE if the user checked the special characters
	 * option, and FALSE otherwise
	 */
	public boolean specialCharacters() {
		
		return special.isSelected();
		
	}
	
	/**
	 * 
	 * @return A boolean value TRUE if the user checked the numeric option,
	 * and FALSE otherwise
	 */
	public boolean numbers() {
		
		return number.isSelected();
		
	}
	
	public int getNumberOfCharacters() {
		return size;
	}
	
	public synchronized void moveStart (int newstart) {
		z.moveStart(newstart);
	}
	
	public synchronized void moveEnd(int newend) {
		z.moveEnd(newend);
	}
}
