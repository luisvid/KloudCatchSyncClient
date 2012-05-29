package com.kloudcatch.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class CloseFrameActionListener implements ActionListener {

	private JFrame frame;
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		frame.dispose();
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return frame;
	}
}
