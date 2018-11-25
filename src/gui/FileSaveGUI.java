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
		
		if(obj.getActionCommand()== parent.saveBtn.getActionCommand()) {
			
			try {
				//save the file:
				File file = chooser.getSelectedFile();
				FileWriter outfile;
				int overwrite;
				FileFilter filter = chooser.getFileFilter();
				
				
				//type file
				System.out.println(file.getName());
				if(filter.equals(txt)) {
					if(!file.getName().endsWith(".txt")){
						file = new File(file.getPath()+".txt");
				}			
					

				}	
				if(filter.equals(csv))  {

						if(!file.getName().endsWith(".csv")){
							file = new File(file.getPath()+".csv");

					}			
				}
				
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
	
}}