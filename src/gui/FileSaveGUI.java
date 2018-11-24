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

public class FileSaveGUI implements ActionListener {

	private PasswordGeneratorGUI parent;
	
	public FileSaveGUI(PasswordGeneratorGUI parent) {
		this.parent = parent;
	}
	
	public void actionPerformed(ActionEvent obj) {
		
		JFileChooser chooser = new JFileChooser();
		//set the default file type to .txt files
		chooser.setFileFilter(new FileNameExtensionFilter("Text File (*.txt)","txt"));
		chooser.showSaveDialog(null);
		
		if(obj.getActionCommand()== parent.saveBtn.getActionCommand()) {
			
			try {
				//save the file:
				File file = chooser.getSelectedFile();
				FileWriter outfile;
				int overwrite;
				if(file.exists()) {
					overwrite = JOptionPane.showConfirmDialog(null, "The file " + file.getName() + 
							" already exisits.\nAre you sure you want to overwrite it?", 
							"File exsists", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
					if(overwrite != JOptionPane.YES_OPTION) {
						throw new NullPointerException();
					}
					
				}
				outfile = new FileWriter(file);
				outfile.write(parent.password.getText());
				outfile.flush();
				outfile.close();
				
			} catch (NullPointerException e) {
				//this happens when the user clicks cancel
				//or programmatically from attempting to overwrite a file,
				//so no action is required
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,e+"");
			}
		}
	}
}
