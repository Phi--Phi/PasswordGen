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
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import exception.LengthOutOfBoundsException;
import exception.NumberOutOfBoundsException;
import exception.OptionSelectException;
import gui.element.ZoneSpec;
import main.RandomGeneratorMain;

/**
 * 
 * This is the main GUI page the user will see. Here the user can generate
 * passwords and select the number of passwords to generate, length of the
 * password, and the alphabet used for that password. There are three potential
 * error messages: character bounds, number bounds, and option select error.
 * Each password has a limit from 6 to 30 characters. The Password Generator can
 * only generate up to 100 passwords at a time. To generate a password, at least
 * one option must be selected for the alphabet of that password.
 *
 */

public class RandomGeneratorOptionsGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4088542285928124199L;

	private JButton submit, advancedOpt;
	private JTextArea characters, passwords;
	private JPanel panel, numberOfChars, numberOfPasswords;

	private int chars = 6, passes = 1;

	private PasswordGeneratorGUI pgen;
	private PasswordGeneratorZoneGUI zonegui;

	public RandomGeneratorOptionsGUI() throws HeadlessException {

		Image icon = Toolkit.getDefaultToolkit().getImage("res/icon.png");
		panel = new JPanel();
		pgen = new PasswordGeneratorGUI(this);
		zonegui = new PasswordGeneratorZoneGUI(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(RandomGeneratorMain.TITLE);
		setMinimumSize(new Dimension(400, 10));
		setIconImage(icon);
		setupPanel();
		add(panel);
		pack();
		setVisible(true);
	}

	/**
	 * This function sets up the GUI elements and positions them on the screen.
	 */
	private void setupPanel() {

		setupNumberOfCharacters();
		setupNumberOfPasswords();
		
		advancedOpt = new JButton("Advanced Options");
		submit 		= new JButton("Generate");
		submit.addActionListener(e -> {
			setVisible(false);
			generate(zonegui.getZones());
		});
		advancedOpt.addActionListener(e -> {
			setVisible(false);
			zonegui.resetZones();
			zonegui.setVisible(true);
		});
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		numberOfChars.setAlignmentX(Component.LEFT_ALIGNMENT);
		numberOfPasswords.setAlignmentX(Component.LEFT_ALIGNMENT);
		submit.setAlignmentX(Component.LEFT_ALIGNMENT);
		advancedOpt.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(numberOfChars);
		panel.add(numberOfPasswords);
		panel.add(advancedOpt);
		panel.add(submit);

	}

	/**
	 * this function sets up the length of password selection
	 */
	private void setupNumberOfCharacters() {

		JLabel numberOfCharactersInPassword;
		JButton increment, decrement;
		numberOfCharactersInPassword = new JLabel("How many characters do you want?");

		characters = new JTextArea();
		increment = new JButton("+");
		increment.addActionListener(event -> {
			/**
			 * on click for increment
			 */
			try {
				incrementChars();
			} catch (LengthOutOfBoundsException exception) {
				JOptionPane.showMessageDialog(increment, "Enter a number between 6 and 30");
			}
		});
		decrement = new JButton("-");
		decrement.addActionListener(e -> {
			/**
			 * on click for decrement
			 */
			try {
				decrementChars();
			} catch (LengthOutOfBoundsException exception) {
				JOptionPane.showMessageDialog(decrement, "Enter a number between 6 and 30");
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
		increment = new JButton("+");
		increment.addActionListener(e -> {
			/**
			 * on click for increment
			 */
			try {
				incrementPasses();
			} catch (NumberOutOfBoundsException exception) {
				JOptionPane.showMessageDialog(increment, "Enter a number between 1 and 100");
			}

			});
		decrement = new JButton("-");
		decrement.addActionListener(e -> {
			/**
			 * on click for decrement
			 */
			try {
				decrementPasses();
			} catch (NumberOutOfBoundsException exception) {
				JOptionPane.showMessageDialog(decrement, "Enter a number between 1 and 100");
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

	private void generate(Collection<ZoneSpec> zones) {

		setVisible(false);
		ZoneSpec zone;
		for (Iterator<ZoneSpec> i = zones.iterator(); i.hasNext();) {
			zone = i.next();
			try {
				if (!zone.lowercase() && !zone.uppercase() && !zone.specialCharacters() && !zone.numbers()) {

					throw new OptionSelectException();

				} 
			} catch (OptionSelectException e) {
				JOptionPane.showMessageDialog(this, "An option must be selected");
				setVisible(true);
			}
			pgen.generate(zones);
		}
	}

	/**
	 * Increases the number of characters to generate by 1
	 * 
	 * @throws NumberOutOfBoundsException when the value for number of characters in
	 *                                    the password goes out of range [6,30]
	 */
	private void incrementChars() throws LengthOutOfBoundsException {

		chars++;
		if (chars > 30) {
			chars--;
			throw new LengthOutOfBoundsException();
		}
		updateCharacters();

	}

	/**
	 * Decreases the number of characters to generate by 1
	 * 
	 * @throws NumberOutOfBoundsException when the value for number of characters in
	 *                                    the password goes out of range [6,30]
	 */
	private void decrementChars() throws LengthOutOfBoundsException {

		chars--;
		if (chars < 6) {
			chars++;
			throw new LengthOutOfBoundsException();
		}
		updateCharacters();

	}

	/**
	 * Increases the number of passwords to generate by 1
	 * 
	 * @throws NumberOutOfBoundsException when the value for number of passwords
	 *                                    goes out of range [1,100]
	 */
	private void incrementPasses() throws NumberOutOfBoundsException {

		passes++;
		if (passes > 100) {
			passes--;
			throw new NumberOutOfBoundsException();
		}
		updatePasswords();

	}

	/**
	 * Decreases the number of passwords to generate by 1
	 * 
	 * @throws NumberOutOfBoundsException when the value for number of passwords
	 *                                    goes out of range [1,100]
	 */
	private void decrementPasses() throws NumberOutOfBoundsException {

		passes--;
		if (passes < 1) {
			passes++;
			throw new NumberOutOfBoundsException();
		}
		updatePasswords();

	}

	/**
	 * After changing the numeric value for the number of characters, this function
	 * will update that change to the user.
	 */
	private void updateCharacters() {
		characters.setText(String.valueOf(chars));
	}

	/**
	 * After changing the numeric value for the number of passwords, this function
	 * will update that change to the user.
	 */
	private void updatePasswords() {
		passwords.setText(String.valueOf(passes));
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
