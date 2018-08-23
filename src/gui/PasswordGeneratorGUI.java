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
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.RandomGeneratorMain;

public class PasswordGeneratorGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1442493858262690625L;
	
	private JPanel panel;
	private JButton goBack, next;
	private JTextField password;
	
	private RandomGeneratorOptionsGUI options;
	
	public PasswordGeneratorGUI (RandomGeneratorOptionsGUI opt) {
		
		Image icon = Toolkit.getDefaultToolkit().getImage("res/icon.png");
		panel = new JPanel();
		options = opt;
		setVisible(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(RandomGeneratorMain.TITLE);
		setMinimumSize(new Dimension(380,10));
	    setIconImage(icon);
	    setupPanel();
	    add(panel);
	    pack();
	    
	}
	
	public void setupPanel() {
		
		JPanel buttons = new JPanel();
		goBack   = new JButton("Go Back");
		next     = new JButton("Get Next Password");
		password = new JTextField();
		
		password.setEditable(false);
		goBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				pack();
				options.setVisible(true);
				
			}
			
		});
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				generate();
				
			}
			
		});
		
		buttons.setLayout(new BorderLayout());
		buttons.add(goBack, BorderLayout.WEST);
		buttons.add(next, BorderLayout.EAST);
		panel.setLayout(new BorderLayout());
		panel.add(password, BorderLayout.NORTH);
		panel.add(buttons, BorderLayout.SOUTH);
		
	}
	
	public synchronized void generate() {
		
		ArrayList<Character> alphabet = new ArrayList<Character>();
		Random rand                   = new Random();
		StringBuilder sb              = new StringBuilder();
		
		options.pack();
		if (options.lowercase()) {
			
			for (char c = 'a'; c <= 'z'; c++) {
				
				alphabet.add(c);
				
			}
			
		}
		if (options.uppercase()) {
			
			for (char c = 'A'; c <= 'Z'; c++) {
				
				alphabet.add(c);
				
			}
			
		}
		if (options.numbers()) {
			
			for (char c = '0'; c <= '9'; c++) {
				
				alphabet.add(c);
				
			}
			
		}
		if (options.specialCharacters()) {
			
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
		for (int i = 0; i < options.getNumberOfCharacters(); i++) {
			
			sb.append(alphabet.get(rand.nextInt(alphabet.size())));
			
		}
		password.setText(sb.toString());
		setVisible(true);
		
	}
	
}
