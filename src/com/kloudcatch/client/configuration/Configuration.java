package com.kloudcatch.client.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.kloudcatch.client.core.KloudCatchTray;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Configuration {
	private static final String salt = "e&5$SALTd1/6f!";

	private String email;
	private String password;
	private String server;
	private String folder;
	private String language;
	private Boolean startup;
	private Boolean notifications;

	public Configuration() {
		Properties properties = new Properties();
		String file = System.getProperty("user.dir") + File.separator + "kloudcatch.properties";
		try {
			properties.load(new FileInputStream(file));

			setEmail(properties.getProperty("email"));
			setPassword(decodePassword(properties.getProperty("credential")));
			setServer(properties.getProperty("server"));
			setFolder(properties.getProperty("folder"));
			setLanguage(properties.getProperty("language"));
			setStartup(Boolean.parseBoolean(properties.getProperty("startup")));
			setNotifications(Boolean.parseBoolean(properties.getProperty("notifications")));
		} catch (FileNotFoundException e) {
			KloudCatchTray.displayWarning("File not found", file);
		} catch (IOException e) {
			KloudCatchTray.displayError("I/O Error", file);
		}

	}

	public void store() {
		Properties properties = new Properties();
		properties.setProperty("email", getEmail());
		properties.setProperty("credential", encodePassword(getPassword()));
		properties.setProperty("server", getServer());
		properties.setProperty("folder", getFolder());
		properties.setProperty("language", getLanguage());
		properties.setProperty("startup", Boolean.toString(getStartup()));
		properties.setProperty("notifications", Boolean.toString(getNotifications()));

		String file = System.getProperty("user.dir") + File.separator + "kloudcatch.properties";
		try {
			properties.store(new FileOutputStream(file), null);
		} catch (FileNotFoundException e) {
			KloudCatchTray.displayWarning("File not found", file);
		} catch (IOException e) {
			KloudCatchTray.displayError("I/O Error", file);
		}
	}

	private String encodePassword(String password) {
		String noise = salt.replace("SALT", password);
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(noise.getBytes());
	}

	private String decodePassword(String password) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			String noise = new String(decoder.decodeBuffer(password));
			return noise.replace("e&5$", "").replace("d1/6f!", "");
		} catch (Exception e) {
			KloudCatchTray.displayWarning("Credentials not found", "");
			return "";
		}
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getServer() {
		return server;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getFolder() {
		return folder;
	}

	public void setStartup(Boolean startup) {
		this.startup = startup;
	}

	public Boolean getStartup() {
		return startup;
	}

	public void setNotifications(Boolean notifications) {
		this.notifications = notifications;
	}

	public Boolean getNotifications() {
		return notifications;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}
