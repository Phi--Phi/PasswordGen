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

import javax.swing.JFrame;

import gui.element.Zone;
import gui.element.ZoneSpec;

public class PasswordGeneratorZoneGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5435024379805897010L;
	private static final int MAX_ZONES = 30;
	private RandomGeneratorOptionsGUI parent;
	private ZoneSpec []specs = new ZoneSpec[MAX_ZONES];
	private Zone [] zones = new Zone[MAX_ZONES];
	private volatile int numZones;

	public PasswordGeneratorZoneGUI(RandomGeneratorOptionsGUI rgo) {
		setMinimumSize(new Dimension(600,10));
		setLayout(new BorderLayout());
		parent = rgo;
		resetZones();
		numZones = 1;
		
	}
	
	public synchronized void resetZones() {
		int size = parent.getNumberOfCharacters();
		for(int i = 0; i < MAX_ZONES; i ++) {
			specs[i] = new ZoneSpec();
			zones[i] = new Zone(size);
			specs[i].setSize(size);
		}
	}
	
}
