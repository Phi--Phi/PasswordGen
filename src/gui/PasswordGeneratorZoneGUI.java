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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.element.ZoneSpec;
import main.RandomGeneratorMain;

public class PasswordGeneratorZoneGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5435024379805897010L;
	private static final int MAX_ZONES = 30;
	private RandomGeneratorOptionsGUI parent;
	private Vector<ZoneSpec> specs = new Vector<ZoneSpec>();
	
	private JFrame frame;
	
	public PasswordGeneratorZoneGUI(RandomGeneratorOptionsGUI rgo) {
	    setMinimumSize(new Dimension(500,300));		   
	    initialize();
		parent = rgo;
		resetZones();
		
		//panel = new JPanel();
		setVisible(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(RandomGeneratorMain.TITLE);
		//add(panel);
		pack();
	}
	
	private void initialize()
	{
		setBounds(100, 100, 450, 300);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{20, 51, 36, 0, 36, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel characters = new JPanel();
		characters.setBackground(Color.GREEN);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 10;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		getContentPane().add(characters, gbc_panel_1);
		
		JPanel zoneMadness = new JPanel();
		zoneMadness.setBackground(Color.ORANGE);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 11;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 2;
		getContentPane().add(zoneMadness, gbc_panel_2);
		
		JPanel checkboxes = new JPanel();
		checkboxes.setBackground(Color.PINK);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 7;
		gbc_panel.gridheight = 4;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
		getContentPane().add(checkboxes, gbc_panel);
		
		
		
		JButton addZone = new JButton("Add Zone");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 10;
		gbc_btnNewButton.gridy = 5;
		getContentPane().add(addZone, gbc_btnNewButton);
		
		JButton deleteZone = new JButton("Delete Zone");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 10;
		gbc_btnNewButton_1.gridy = 6;
		getContentPane().add(deleteZone, gbc_btnNewButton_1);
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
