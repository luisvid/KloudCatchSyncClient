package com.kloudcatch.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class FolderActionListener implements ActionListener {
	
	private JTextField folderText;
	private JFileChooser folderChooser;
	private JFrame parent;

	@Override
	public void actionPerformed(ActionEvent e) {
		folderChooser = new JFileChooser();
		folderChooser.setCurrentDirectory(new java.io.File("."));
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		folderChooser.showOpenDialog(parent);
		File selectedFile = folderChooser.getSelectedFile();
		folderText.setText(selectedFile.getAbsolutePath());
	}

	public void setFolderText(JTextField folderText) {
		this.folderText = folderText;
	}

	public JTextField getFolderTextField() {
		return folderText;
	}

	public void setParent(JFrame parent) {
		this.parent = parent;
	}

	public JFrame getParent() {
		return parent;
	}
}
