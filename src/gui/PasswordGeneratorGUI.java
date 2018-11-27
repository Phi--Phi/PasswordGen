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
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import gui.element.ZoneSpec;
import main.RandomGeneratorMain;

public class PasswordGeneratorGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1442493858262690625L;

	private JPanel panel;
	// visibility is set to package
	JButton goBack, next, saveBtn;
	JTextArea password;

	private RandomGeneratorOptionsGUI options;

	public PasswordGeneratorGUI(RandomGeneratorOptionsGUI opt) {

		Image icon = Toolkit.getDefaultToolkit().getImage("res/icon.png");
		panel = new JPanel();
		options = opt;
		setVisible(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(RandomGeneratorMain.TITLE);
		setMinimumSize(new Dimension(380, 10));
		setIconImage(icon);
		setupPanel();
		add(panel);
		pack();

	}

	public void setupPanel() {

		JPanel buttons = new JPanel();
		goBack = new JButton("Go Back");
		next = new JButton("Get Next Password");
		password = new JTextArea();
		saveBtn = new JButton("Save");
		password.setEditable(false);
		goBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				pack();
				options.setVisible(true);

			}

		});

		saveBtn.addActionListener(new FileSaveGUI(this));

		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//generate();

			}

		});

		buttons.setLayout(new BorderLayout());
		buttons.add(goBack, BorderLayout.WEST);
		buttons.add(next, BorderLayout.EAST);
		buttons.add(saveBtn, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout());
		panel.add(password, BorderLayout.NORTH);
		panel.add(buttons, BorderLayout.SOUTH);

	}

	/**
	 * <p>
	 * This function will generate the passwords based on options.lowercase()
	 * options.uppercase() options.numbers() options.specialCharacters
	 * options.getNumberOfPasswords() options.getNumberOfCharacters
	 * </p>
	 * <p>
	 * The generated password list is immediately displayed to the user
	 * </p>
	 * 
	 * @param zones
	 */
	public synchronized void generate(List<ZoneSpec> zones) {

		ArrayList<Character> alphabet = new ArrayList<Character>();
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		ZoneSpec z;

		options.pack();
		for (int i = 0; i < options.getNumberOfPasswords(); i++) {
			for (Iterator<ZoneSpec> j = zones.iterator(); j.hasNext();) {
				z = j.next();
				alphabet.clear();
				if (z.lowercase()) {

					for (char c = 'a'; c <= 'z'; c++) {

						alphabet.add(c);

					}

				}
				if (z.uppercase()) {

					for (char c = 'A'; c <= 'Z'; c++) {

						alphabet.add(c);

					}

				}
				if (z.numbers()) {

					for (char c = '0'; c <= '9'; c++) {

						alphabet.add(c);

					}

				}
				if (z.specialCharacters()) {

					alphabet.add('!');
					alphabet.add('@');
					alphabet.add('#');
					alphabet.add('$');
					alphabet.add('%');
					alphabet.add('^');
					alphabet.add('&');
					alphabet.add('*');
					alphabet.add('(');
					alphabet.add(')');
					alphabet.add('-');
					alphabet.add('_');
					alphabet.add('+');
					alphabet.add('=');
					alphabet.add(':');
					alphabet.add(';');
					alphabet.add('<');
					alphabet.add('>');
					alphabet.add('?');

				}
				for(int k = 0; k < z.getZone().getLength(); k++) {
					sb.append(alphabet.get(rand.nextInt(alphabet.size())));
				}

			}
			sb.append("\n");
		}
		password.setText(sb.toString());
		setVisible(true);
		pack();

	}

}
