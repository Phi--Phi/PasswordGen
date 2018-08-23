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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import exception.CharactersBoundsException;
import main.RandomGeneratorMain;

public class RandomGeneratorOptionsGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4088542285928124199L;
	
	private JButton increment, decrement, submit;
	private JTextArea characters;
	private JPanel panel, numberOfChars, options;
	private JCheckBox lower, upper, number, special;
	private int chars = 6;
	
	private PasswordGeneratorGUI pgen;
	
	public RandomGeneratorOptionsGUI() throws HeadlessException {
		
		Image icon = Toolkit.getDefaultToolkit().getImage("res/icon.png");
		panel      = new JPanel();
		pgen       = new PasswordGeneratorGUI(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(RandomGeneratorMain.TITLE);
		setMinimumSize(new Dimension(380,10));
	    setIconImage(icon);
		setupPanel();
		add(panel);
		pack();
		setVisible(true);
		
	}
	
	private void setupPanel() {
		
		setupNumberOfCharacters();
		setupOptions();
		submit = new JButton("Generate");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				generate();
				
			}
			
		});
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		numberOfChars.setAlignmentX(Component.LEFT_ALIGNMENT);
		options.setAlignmentX(Component.LEFT_ALIGNMENT);
		submit.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(numberOfChars);
		panel.add(options);
		panel.add(submit);
		
	}
	
	private void setupOptions() {
		
		lower 	= new JCheckBox("Lowercase letters");
		upper 	= new JCheckBox("Uppercase letters");
		number 	= new JCheckBox("Numeric");
		special = new JCheckBox("Special characters");
		
		
		options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.PAGE_AXIS));
		options.add(lower);
		options.add(upper);
		options.add(number);
		options.add(special);
		
	}
	
	private void setupNumberOfCharacters() {
		
		JLabel numberOfCharactersInPassword;
		numberOfCharactersInPassword = new JLabel("How many characters do you want?");
		
		characters = new JTextArea();
		increment  = new JButton("+");
		increment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				increment();
				
			}
			
		});
		decrement  = new JButton("-");
		decrement.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					decrement();
					
				} catch (CharactersBoundsException e) {
					
					Color original_background = decrement.getBackground();
					Border b                  = BorderFactory.createLineBorder(Color.RED),
							original_border = decrement.getBorder();
					decrement.setBackground(Color.RED);
					decrement.setBorder(b);
					shake(decrement, original_border, original_background);
					
				}
				
			}

			private synchronized void shake(JButton button, Border original_border, Color original_background) {
				
				long delay = 75;
				Point p    = button.getLocation();
				
				for (int i = 0; i < 30; i++) {
					
					try {
						
						//TODO execute Runnables
						SwingUtilities.invokeLater(moveButton(button, new Point(p.x + 5, p.y)));
						Thread.sleep(delay);
						SwingUtilities.invokeLater(moveButton(button, p));
						Thread.sleep(delay);
						SwingUtilities.invokeLater(moveButton(button, new Point(p.x - 5, p.y)));
						Thread.sleep(delay);
						SwingUtilities.invokeLater(moveButton(button, p));
						Thread.sleep(delay);
						
					} catch (InterruptedException ex) {
						
						SwingUtilities.invokeLater(moveButton(button,p));
						button.setBorder(original_border);
						button.setBackground(original_background);
						return;
						
					}
					button.setBorder(original_border);
					button.setBackground(original_background);
					
				}
				
			}
			
			private Runnable moveButton(JButton button, final Point p) {
				
				return new Runnable() {
					
					@Override
					public void run() {
						
						button.setLocation(p);
						
					}
					
				};
				
			}
			
		});
		updateCharacters();
		characters.setEditable(false);
		numberOfChars = new JPanel();
		numberOfChars.setLayout(new BorderLayout());
		numberOfChars.add(decrement, BorderLayout.WEST);
		numberOfChars.add(characters, BorderLayout.CENTER);
		numberOfChars.add(increment, BorderLayout.EAST);
		numberOfChars.add(numberOfCharactersInPassword, BorderLayout.NORTH);
		
	}
	
	private void generate() {
		
		setVisible(false);
		if (!lowercase() && !uppercase() && !specialCharacters() && !numbers()) {
			
			JOptionPane.showMessageDialog(this, "An option must be selected");
			setVisible(true);
			
		} else {
			
			pgen.generate();
			
		}
		
	}
	
	private void increment() {
		
		chars++;
		updateCharacters();
		
	}
	
	private void decrement() throws CharactersBoundsException {
		
		chars--;
		if(chars < 6) {
			chars++;
			throw new CharactersBoundsException();
		}
		updateCharacters();
		
	}
	
	private void updateCharacters() {
		
		characters.setText(String.valueOf(chars));
		
	}
	
	public boolean lowercase() {
		
		return lower.isSelected();
		
	}
	
	public boolean uppercase() {
		
		return upper.isSelected();
		
	}
	
	public boolean specialCharacters() {
		
		return special.isSelected();
		
	}
	
	public boolean numbers() {
		
		return number.isSelected();
		
	}
	
	public int getNumberOfCharacters() {
		
		return chars;
		
	}
	
}
