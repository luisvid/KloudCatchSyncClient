package com.kloudcatch.client.configuration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.kloudcatch.client.actions.CloseFrameActionListener;
import com.kloudcatch.client.actions.FolderActionListener;

public class ConfigurationWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 3278371494183769548L;

	private JPanel container;

	private JLabel emailLabel;
	private JTextField emailText;

	private JLabel passLabel;
	private JTextField passText;

	private JLabel serverLabel;
	private JTextField serverText;

	private JLabel folderLabel;
	private JTextField folderText;
	private JButton folderButton;
	private FolderActionListener folderActionListener;

	private JLabel languageLabel;
	private JLabel languageValueLabel;

	private JLabel startupLabel;
	private JCheckBox startupCheckBox;

	private JLabel notificationsLabel;
	private JCheckBox notificationsCheckBox;

	private JButton acceptButton;
	private CloseFrameActionListener closeConfiguration;
	private JButton cancelButton;

	private Configuration configuration;

	public ConfigurationWindow() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new MigLayout());
		setResizable(false);

		configuration = new Configuration();

		container = new JPanel(new MigLayout());
		container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		emailLabel = new JLabel("email");
		emailText = new JTextField(20);
		emailText.setText(configuration.getEmail());

		passLabel = new JLabel("password");
		passText = new JPasswordField(20);
		passText.setText(configuration.getPassword());

		serverLabel = new JLabel("server");
		serverText = new JTextField(20);
		serverText.setText(configuration.getServer());

		folderLabel = new JLabel("folder");
		folderText = new JTextField(20);
		folderText.setText(configuration.getFolder());

		folderActionListener = new FolderActionListener();
		folderActionListener.setFolderText(folderText);
		folderActionListener.setParent(this);

		folderButton = new JButton("?");
		folderButton.addActionListener(folderActionListener);

		languageLabel = new JLabel("language");
		languageValueLabel = new JLabel(configuration.getLanguage());

		startupLabel = new JLabel("startup");
		startupCheckBox = new JCheckBox();
		startupCheckBox.setSelected(configuration.getStartup());

		notificationsLabel = new JLabel("notifications");
		notificationsCheckBox = new JCheckBox();
		notificationsCheckBox.setSelected(configuration.getNotifications());

		acceptButton = new JButton("Accept");
		acceptButton.addActionListener(this);
		
		closeConfiguration = new CloseFrameActionListener();
		closeConfiguration.setFrame(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(closeConfiguration);

		// Layout
		container.add(emailLabel);
		container.add(emailText, "wrap");
		container.add(passLabel);
		container.add(passText, "wrap");
		container.add(serverLabel);
		container.add(serverText, "wrap");
		container.add(folderLabel);
		container.add(folderText, "split 2");
		container.add(folderButton, "wrap");
		container.add(languageLabel);
		container.add(languageValueLabel, "wrap");
		container.add(startupLabel);
		container.add(startupCheckBox, "wrap");
		container.add(notificationsLabel);
		container.add(notificationsCheckBox, "wrap");

		container.add(acceptButton);
		container.add(cancelButton);
		add(container);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		configuration.setEmail(emailText.getText());
		configuration.setPassword(passText.getText());
		configuration.setServer(serverText.getText());
		configuration.setFolder(folderText.getText());
		configuration.setLanguage(languageValueLabel.getText());
		configuration.setStartup(startupCheckBox.isSelected());
		configuration.setNotifications(notificationsCheckBox.isSelected());
		configuration.store();
		dispose();
	}
}
