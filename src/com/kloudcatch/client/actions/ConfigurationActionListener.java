package com.kloudcatch.client.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.kloudcatch.client.configuration.ConfigurationWindow;

public class ConfigurationActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		new ConfigurationWindow();
	}

}
