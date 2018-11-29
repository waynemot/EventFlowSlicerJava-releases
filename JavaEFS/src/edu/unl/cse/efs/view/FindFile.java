package edu.unl.cse.efs.view;

import java.awt.Component;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SelectFile.java
 *
 * Created on Jul 12, 2011, 5:08:03 PM
 */

import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.unl.cse.efs.util.OSDetector;


/**
 *
 * Source for the CogToolHelperSelectFile class. This class aids the user in selecting a file when promted to by
 * cogtool helper.
 */
public class FindFile extends javax.swing.JPanel {

	private javax.swing.JFileChooser chooseFile;
	public static class ViewFrame
	{
		public static JFrame window;
//		public static
	}
	public static void main(String[] args)
	{
		JFrame myFrame = new JFrame("The Title");
		java.awt.Dimension w = new java.awt.Dimension(400, 400);
		myFrame.setPreferredSize(w);
		myFrame.pack();
		myFrame.setVisible(true);
		new FindFile().launch(false, myFrame);
	}
	private static final long serialVersionUID = 1L;


    public FindFile() {
        initComponents(new File(System.getProperty("user.dir")));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public FindFile(File defaultDirectory) {
        initComponents(defaultDirectory);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
    public void setDefaultDirectory(File defaultDirectory)
    {
    	removeAll();
    	initComponents(defaultDirectory);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents(File defaultDirectory) {
    	chooseFile = new JFileChooser(defaultDirectory);
        chooseFile.setName("File_Chooser");
        chooseFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseFileMouseClicked(evt);
            }
        });
        chooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseFileActionPerformed(evt);
            }
        });
        add(chooseFile);
    }

    private void chooseFileActionPerformed(java.awt.event.ActionEvent evt){}

    private void chooseFileMouseClicked(java.awt.event.MouseEvent evt) {}



    /**
     * Launches the file chooser.
     * @param file - true if selections are limited to files only, false if selections are limited to folders only
     */
    public String launch(boolean file, final Component parent) {

       //Select file or directory
       String label = "";
       if(!file){
           chooseFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
           label = "Select Folder";
       }else{
    	   chooseFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
           label = "Select File";
       }

//        int res = chooseFile.showDialog(parent, label); // blocks
       chooseFile.setApproveButtonText(label);
       int res = chooseFile.showOpenDialog(parent);
       	if(res==JFileChooser.APPROVE_OPTION) {
            File fileName = chooseFile.getSelectedFile();
            return fileName.getAbsolutePath();
        }
        else
            return "";
    }

}
