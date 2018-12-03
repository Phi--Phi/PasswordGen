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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;

class FileSaveGUI implements ActionListener {

	private PasswordGeneratorGUI parent;
	public FileSaveGUI(PasswordGeneratorGUI parent) {
		this.parent = parent;
	}
	
	private static final FileFilter txt = new FileNameExtensionFilter("Text File (*.txt)","txt"),
			csv = new FileNameExtensionFilter("CSV File (*.csv)","csv");
	
				
	public void actionPerformed(ActionEvent obj) {
		
		JFileChooser chooser = new JFileChooser();
		//set the default file type to .txt files
		//chooser.setFileFilter(new FileNameExtensionFilter("Text File (*.txt)","txt"));
		chooser.setFileFilter(csv);
		chooser.setFileFilter(txt);
		
		chooser.showSaveDialog(null);
		
		if(obj.getActionCommand()== parent.saveBtn.getActionCommand())
			try {
				File file = chooser.getSelectedFile();
				FileWriter outfile;
				int overwrite;
				FileFilter filter = chooser.getFileFilter();
				System.out.println(file.getName());
				if (filter.equals(txt) && !file.getName().endsWith(".txt"))
					file = new File(file.getPath() + ".txt");
				if (filter.equals(csv) && !file.getName().endsWith(".csv"))
					file = new File(file.getPath() + ".csv");
				if (file.exists()) {
					overwrite = JOptionPane.showConfirmDialog(null,
							"The file " + file.getName() + " already exisits.\nAre you sure you want to overwrite it?",
							"File exsists", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
					if (overwrite != JOptionPane.YES_OPTION)
						throw new NullPointerException();
				}
				outfile = new FileWriter(file);
				outfile.write(parent.password.getText());
				outfile.flush();
				outfile.close();
			} catch (NullPointerException e) {
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e + "");
			}
	
}}