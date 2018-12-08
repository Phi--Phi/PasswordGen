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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.element.CharacterBox;
import gui.element.ZoneSpec;

/**
 * This class is responsible for laying out and displaying all the advanced
 * options to the user. Primarily, the zone setting feature.
 * 
 */
public class PasswordGeneratorZoneGUI extends JFrame {

	private static final long serialVersionUID = 0x4B6D15E09FEB1132L;
	private RandomGeneratorOptionsGUI parent;
	private Vector<ZoneSpec> specs = new Vector<ZoneSpec>();
	private JPanel characters,zoneMadness,checkboxes;
	private JButton AddZone, RemoveZone, GoBack;
	private Vector<CharacterBox> boxes;

	public PasswordGeneratorZoneGUI(RandomGeneratorOptionsGUI rgo) {
		setMinimumSize(new Dimension(1050, 500));
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
	/**
	 * This function sets up the complicated layout of the sections of the advanced options screen.
	 */
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
		gbc_panel_1.gridy = gbc_panel_1.gridx = 1;
		getContentPane().add(characters, gbc_panel_1);
		
		checkboxes = new JPanel();
		checkboxes.setLayout(new BorderLayout());
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 7;
		gbc_panel.gridheight = 4;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
		getContentPane().add(checkboxes, gbc_panel);
		
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
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.PAGE_AXIS));
		
		AddZone = new JButton("   Add Zone  ");
		RemoveZone = new JButton("Delete Zone");
		GoBack = new JButton("    Go Back   ");
		
		buttonPanel.add(Box.createRigidArea(new Dimension(16,16)));
		buttonPanel.add(AddZone);
		buttonPanel.add(Box.createRigidArea(new Dimension(16,16)));
		buttonPanel.add(RemoveZone);
		buttonPanel.add(Box.createRigidArea(new Dimension(16,16)));
		buttonPanel.add(GoBack);
		
		AddZone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addZone(getSelectedStart());
			}
		});
		
		GoBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				pack();
				parent.setVisible(true);

			}

		});
	}
	
	/**
	 * Add a zone at the specified startPos index.
	 * @param startPos the index of the zone to add
	 */
	public void addZone(int startPos) {
		AddZone.setEnabled(false);
		ZoneSpec temp = new ZoneSpec(this,parent.getNumberOfCharacters(),startPos,startPos+1);
		for(Iterator<ZoneSpec> i = specs.iterator(); i.hasNext();)
			i.next().setVisible(false);
		temp.setVisible(true);
		checkboxes.add(temp, BorderLayout.CENTER);
		zoneMadness.add(temp.getZone(), BorderLayout.CENTER);
		specs.add(temp);
		boxes.elementAt(startPos).setEditable(false);
		boxes.elementAt(startPos).setText("x");
		
	}
	/**
	 * 
	 * @return the position of the CharacterBox that is selected.
	 * The assumption is that only one CharacterBox can be selected at a time.
	 */
	private int getSelectedStart() {
		int j = 0;
		for(Iterator<CharacterBox> i = boxes.iterator(); i.hasNext();) {
			if(i.next().isSelected()) {
				return j;
			}
			j++;
		}
		return j;
	}
	
	/**
	 * Clears all zone configuration and resets it to the initial default state.
	 * Is also used to setup the initial default state.
	 */
	public synchronized void resetZones() {
		ZoneSpec temp = new ZoneSpec(this, parent.getNumberOfCharacters());
		CharacterBox temp2;
		
		temp.getZone().setSize(parent.getNumberOfCharacters());
		characters.removeAll();
		zoneMadness.removeAll();
		checkboxes.removeAll();
		characters.setLayout(new GridLayout(1, parent.getNumberOfCharacters()));
		
		boxes.clear();
		for (int i = 0; i < parent.getNumberOfCharacters(); ++i) {
			temp2 = new CharacterBox();
			boxes.add(temp2);
			characters.add(temp2);
			temp2.addMouseListener((new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					CharacterBox source = ((CharacterBox)e.getSource());
					for(Iterator<CharacterBox> j = boxes.iterator(); j.hasNext();) {
						j.next().deselect();
					}
					source.select();
					if(source.isEditable()) {
						AddZone.setEnabled(true);
					} else {
						AddZone.setEnabled(false);
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			}));
		}
		
		specs.clear();
		specs.add(temp);
		
		for(int i = 0; i < specs.size(); ++i) {
			checkboxes.add(specs.elementAt(i), BorderLayout.CENTER);
			zoneMadness.add(specs.elementAt(i).getZone(), BorderLayout.CENTER);
		}
		
		RemoveZone.setEnabled(false);
		AddZone.setEnabled(false);
		GoBack.setEnabled(canGoBack());
	}
	/**
	 * This function calculates the maximum or minimum expansion frontier for a zone's
	 * start or end position.
	 * @param index the start of the scan.
	 * @param direction the direction of the scan. If positive, scan indexes > index.
	 * @return the position of the maximum/minimum expansion.
	 */
	public synchronized int scan(int index, int direction) {
		if (direction <= 0) {
			if (direction < 0)
				for (int i = index - 1; i >= 0; --i) {
					if (!boxes.elementAt(i).isEditable())
						return index;
					index = i;
				}
		} else
			for (int i = index; i < boxes.size(); ++i) {
				if (!boxes.elementAt(i).isEditable())
					return index;
				index = i;
				if (index < boxes.size() && boxes.elementAt(index).isEditable())
					index = boxes.size();
			}
		return index;
	}

	public List<ZoneSpec> getZones() {
		return specs;
	}
	/**
	 * Disables input for the CharacterBox located at index i.
	 * @param i the CharacterBox to disable.
	 */
	public synchronized void claim(int i) {
		CharacterBox temp = boxes.elementAt(i);
		temp.setEditable(false);
		temp.setText("x");
		if(temp.isSelected()) {
			AddZone.setEnabled(false);
		}
		
		GoBack.setEnabled(canGoBack());
	}
	/**
	 * Enables input for the CharacterBox located at index i.
	 * @param i the CharacterBox to enable.
	 */
	public synchronized void free(int i) {
		CharacterBox temp = boxes.elementAt(i);
		temp.setEditable(true);
		temp.setText("");
		if(temp.isSelected()) {
			AddZone.setEnabled(true);
		}
		GoBack.setEnabled(false);
	}
	
	/**
	 * 
	 * @return true if there are no errors with the options the user 
	 * has currently selected.
	 */
	public synchronized boolean canGoBack() {
		boolean canGoBack = true;
		CharacterBox temp;
		ZoneSpec temp2;
		for(Iterator<CharacterBox> i = boxes.iterator(); i.hasNext();) {
			temp = i.next();
			if(temp.isEditable() && temp.getText().equals("")) {
				canGoBack = false;
			}
		}
		for(Iterator<ZoneSpec> i = specs.iterator(); i.hasNext();) {
			temp2 = i.next();
			if(!temp2.lowercase() && !temp2.uppercase() && !temp2.specialCharacters() && !temp2.numbers()) {
				canGoBack = false;
			}
		}
		return canGoBack;
	}
}
