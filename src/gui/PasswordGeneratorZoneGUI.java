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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import gui.element.CharacterBox;
import gui.element.ZoneSpec;

public class PasswordGeneratorZoneGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5435024379805897010L;
	private RandomGeneratorOptionsGUI parent;
	private Vector<ZoneSpec> specs = new Vector<ZoneSpec>();
	private JPanel characters,zoneMadness;
	private Vector<CharacterBox> boxes;

	public PasswordGeneratorZoneGUI(RandomGeneratorOptionsGUI rgo) {
		setMinimumSize(new Dimension(800, 400));
		specs = new Vector<ZoneSpec>();
		boxes = new Vector<CharacterBox>();
		initialize();
		parent = rgo;
		resetZones();
		setVisible(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Advanced Options");
		pack();
	}

	private void initialize() {
		setBounds(100, 100, 450, 300);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 51, 36, 0, 36, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		characters = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 10;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		getContentPane().add(characters, gbc_panel_1);

		zoneMadness = new JPanel();
		zoneMadness.setLayout(new BorderLayout());
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 11;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 2;
		getContentPane().add(zoneMadness, gbc_panel_2);
		
		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.gridwidth = 4;
		gbc_buttonPanel.gridheight = 3;
		gbc_buttonPanel.insets = new Insets(0, 0, 5, 5);
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.gridx = 6;
		gbc_buttonPanel.gridy = 3;
		getContentPane().add(buttonPanel, gbc_buttonPanel);
		buttonPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel buttonPanel2 = new JPanel();
		buttonPanel.add(buttonPanel2);
		
		JButton addZone = new JButton("   Add Zone  ");
		JButton deleteZone = new JButton("Delete Zone");
		JButton goBack = new JButton("    Go Back   ");
		
		GroupLayout groupLayout_buttonPanel = new GroupLayout(buttonPanel2);
		groupLayout_buttonPanel.setHorizontalGroup(
				groupLayout_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_buttonPanel.createSequentialGroup()
						
					
					.addGroup(groupLayout_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(goBack, Alignment.TRAILING)
						.addComponent(deleteZone, Alignment.TRAILING)
						.addComponent(addZone, Alignment.TRAILING))
					.addContainerGap(26, Short.MAX_VALUE)
				//	.addContainerGap())
		));
		groupLayout_buttonPanel.setVerticalGroup(
				groupLayout_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_buttonPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(goBack)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(deleteZone)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(addZone)
					.addContainerGap(16, Short.MAX_VALUE))
		);
		buttonPanel2.setLayout(groupLayout_buttonPanel);
	
		
		goBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				pack();
				parent.setVisible(true);

			}

		});
	}

	public void addZone(int startPos) {
		ZoneSpec temp = new ZoneSpec(this,parent.getNumberOfCharacters());
		temp.getZone().setSize(parent.getNumberOfCharacters());
		temp.moveStart(startPos);
		temp.moveEnd(startPos + 1);
		for(Iterator<ZoneSpec> i = specs.iterator(); i.hasNext();) {
			ZoneSpec temp2 = i.next();
			temp2.setVisible(false);
			
		}
		temp.setVisible(true);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 7;
		gbc_panel.gridheight = 4;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
		getContentPane().add(temp, gbc_panel);
		zoneMadness.add(temp.getZone(), BorderLayout.CENTER);
		specs.add(temp);
		
	}

	/**
	 * clears all zone configuration and resets it to one zone with lowercase
	 * checked
	 */
	public synchronized void resetZones() {
		ZoneSpec temp = new ZoneSpec(this, parent.getNumberOfCharacters());
		CharacterBox temp2;
		
		temp.getZone().setSize(parent.getNumberOfCharacters());
		characters.removeAll();
		zoneMadness.removeAll();
		characters.setLayout(new GridLayout(1, parent.getNumberOfCharacters()));
		
		boxes.clear();
		for (int i = 0; i < parent.getNumberOfCharacters(); i++) {
			temp2 = new CharacterBox();
			boxes.add(temp2);
			characters.add(temp2);
		}
		
		specs.clear();
		specs.add(temp);
		
		for(int i = 0; i < specs.size(); i++) {
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 7;
			gbc_panel.gridheight = 4;
			gbc_panel.insets = new Insets(0, 0, 0, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 1;
			gbc_panel.gridy = 4;
			getContentPane().add(specs.elementAt(i), gbc_panel);
			zoneMadness.add(specs.elementAt(i).getZone(), BorderLayout.CENTER);
		}
	}
	
	public synchronized int scan(int index, int direction) {
		if(direction > 0) {
			for (int i = index; i < boxes.size(); i++) {
				if(boxes.elementAt(i).isEditable()) {
					index = i;
				} else {
					return index;
				}
				if(index == boxes.size() -1) index = boxes.size();
			}
		} else if (direction < 0) {
			for(int i = index -1; i >=0; i--) {
				if(boxes.elementAt(i).isEditable()) {
					index = i;
				} else {
					return index;
				}
			}
		}
		return index;
	}

	public List<ZoneSpec> getZones() {
		return specs;
	}

	public void claim(int i) {
		boxes.elementAt(i).setEditable(false);
		boxes.elementAt(i).setText("x");
	}

	public void free(int i) {
		boxes.elementAt(i).setEditable(true);
		boxes.elementAt(i).setText("");
	}
	
}
