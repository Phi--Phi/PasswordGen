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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import exception.LengthOutOfBoundsException;
import exception.NumberOutOfBoundsException;
import exception.OptionSelectException;
import main.RandomGeneratorMain;

/**
 * 
 * This is the main GUI page the user will see. 
 * Here the user can generate passwords and select the number 
 * of passwords to generate, length of the password, and the 
 * alphabet used for that password. There are three potential 
 * error messages: character bounds, number bounds, and option 
 * select error. Each password has a limit from 6 to 30 
 * characters. The Password Generator can only generate up to 
 * 100 passwords at a time. To generate a password, at least 
 * one option must be selected for the alphabet of that password.
 *
 */

public class RandomGeneratorOptionsGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4088542285928124199L;
	
	private JButton submit;
	private JTextArea characters, passwords;
	private JPanel panel, numberOfChars, numberOfPasswords, options;
	private JCheckBox lower, upper, number, special;
	private int chars = 6, passes = 1;
	
	private PasswordGeneratorGUI pgen;
	
	public RandomGeneratorOptionsGUI() throws HeadlessException {
		
		Image icon = Toolkit.getDefaultToolkit().getImage("res/icon.png");
		panel      = new JPanel();
		pgen       = new PasswordGeneratorGUI(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(RandomGeneratorMain.TITLE);
		setMinimumSize(new Dimension(400,10));
	    setIconImage(icon);
		setupPanel();
		add(panel);
		pack();
		setVisible(true);
		
	}
	
	/**
	 * This function sets up the GUI elements and positions them
	 * on the screen.
	 */
	private void setupPanel() {
		
		setupNumberOfCharacters();
		setupNumberOfPasswords();
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
		numberOfPasswords.setAlignmentX(Component.LEFT_ALIGNMENT);
		options.setAlignmentX(Component.LEFT_ALIGNMENT);
		submit.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(numberOfChars);
		panel.add(numberOfPasswords);
		panel.add(options);
		panel.add(submit);
		
	}
	
	/**
	 * this function sets up the options checkboxes
	 */
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
	
	/**
	 * this function sets up the length of password selection
	 */
	private void setupNumberOfCharacters() {
		
		JLabel numberOfCharactersInPassword;
		JButton increment, decrement;
		numberOfCharactersInPassword = new JLabel("How many characters do you want?");
		
		characters = new JTextArea();
		increment  = new JButton("+");
		increment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * on click for increment
				 */
				try {
					incrementChars();
				} catch (LengthOutOfBoundsException e) {
					JOptionPane.showMessageDialog(increment, "Enter a number between 6 and 30");
				}
				
			}
			
		});
		decrement  = new JButton("-");
		decrement.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * on click for decrement
				 */
				try {
					decrementChars();
				} catch (LengthOutOfBoundsException e) {
					JOptionPane.showMessageDialog(decrement, "Enter a number between 6 and 30");
				}
				
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
	/**
	 * this function sets up the number of passwords the user wants to generate
	 * interface
	 */
	private void setupNumberOfPasswords() {
		
		JLabel numberOfPasswordsToGenerate;
		JButton increment, decrement;
		numberOfPasswordsToGenerate = new JLabel("How many passwords do you want?");
		
		passwords = new JTextArea();
		increment  = new JButton("+");
		increment.addActionListener(new ActionListener() {
			/**
			 * on click for increment
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					incrementPasses();
				} catch (NumberOutOfBoundsException e) {
					JOptionPane.showMessageDialog(increment, "Enter a number between 1 and 100");
				}
				
			}
			
		});
		decrement  = new JButton("-");
		decrement.addActionListener(new ActionListener() {
			/**
			 * on click for decrement
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					decrementPasses();
				} catch (NumberOutOfBoundsException e) {
					JOptionPane.showMessageDialog(decrement, "Enter a number between 1 and 100");
				}
				
			}	
		});
		updatePasswords();
		passwords.setEditable(false);
		numberOfPasswords = new JPanel();
		numberOfPasswords.setLayout(new BorderLayout());
		numberOfPasswords.add(decrement, BorderLayout.WEST);
		numberOfPasswords.add(passwords, BorderLayout.CENTER);
		numberOfPasswords.add(increment, BorderLayout.EAST);
		numberOfPasswords.add(numberOfPasswordsToGenerate, BorderLayout.NORTH);
		
	}
	
	private void generate() {
		
		setVisible(false);
		try {
			if (!lowercase() && !uppercase() && !specialCharacters() && !numbers()) {
				
				throw new OptionSelectException();
				
			} else {
				
				pgen.generate();
				
			}
		}catch (OptionSelectException e) {
			JOptionPane.showMessageDialog(this, "An option must be selected");
			setVisible(true);
		}
		
	}
	
	/**
	 * Increases the number of characters to generate by 1
	 * @throws NumberOutOfBoundsException when the value for number of 
	 * characters in the password goes out of range [6,30]
	 */
	private void incrementChars() throws LengthOutOfBoundsException {
		
		chars++;
		if(chars > 30) {
			chars--;
			throw new LengthOutOfBoundsException();
		}
		updateCharacters();
		
	}
	
	/**
	 * Decreases the number of characters to generate by 1
	 * @throws NumberOutOfBoundsException when the value for number of 
	 * characters in the password goes out of range [6,30]
	 */
	private void decrementChars() throws LengthOutOfBoundsException {
		
		chars--;
		if(chars < 6) {
			chars++;
			throw new LengthOutOfBoundsException();
		}
		updateCharacters();
		
	}
	
	/**
	 * Increases the number of passwords to generate by 1
	 * @throws NumberOutOfBoundsException when the value for number of 
	 * passwords goes out of range [1,100]
	 */
	private void incrementPasses() throws NumberOutOfBoundsException {
		
		passes++;
		if(passes > 100) {
			passes--;
			throw new NumberOutOfBoundsException();
		}
		updatePasswords();
		
	}
	
	/**
	 * Decreases the number of passwords to generate by 1
	 * @throws NumberOutOfBoundsException when the value for number of 
	 * passwords goes out of range [1,100]
	 */
	private void decrementPasses() throws NumberOutOfBoundsException {
		
		passes--;
		if(passes < 1) {
			passes++;
			throw new NumberOutOfBoundsException();
		}
		updatePasswords();
		
	}
	
	/**
	 * After changing the numeric value for the number of characters,
	 * this function will update that change to the user.
	 */
	private void updateCharacters() {
		
		characters.setText(String.valueOf(chars));
		
	}
	
	/**
	 * After changing the numeric value for the number of passwords,
	 * this function will update that change to the user.
	 */
	private void updatePasswords() {
		
		passwords.setText(String.valueOf(passes));
		
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
	
	/**
	 * 
	 * @return The number of characters in the password length
	 */
	public int getNumberOfCharacters() {
		
		return chars;
		
	}
	
	/**
	 * 
	 * @return The number of passwords to generate
	 */
	public int getNumberOfPasswords() {
		return passes;
	}
	
}
