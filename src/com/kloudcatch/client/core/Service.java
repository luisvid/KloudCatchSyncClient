package com.kloudcatch.client.core;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;
import com.kloudcatch.client.configuration.Configuration;
import com.kloudcatch.client.entities.Droplet;
import com.kloudcatch.client.entities.DropletContainer;

public class Service extends Thread {

	private static final int INTERVAL = 30000;
	private static final int BUFFER_SIZE = 4096;
	private Configuration cfg;
	private boolean running;
	private DropletContainer pending;

	public Service() {
		CookieHandler.setDefault(new CookieManager());
	}

	@Override
	public void run() {
		setRunning(true);

		while (isRunning()) {
			cfg = new Configuration();
			getPending();
			downloadAll();

			try {
				sleep(INTERVAL);
			} catch (InterruptedException e) {
				setRunning(false);
			}
		}
	}

	private void getPending() {
		String urlPath = "";
		pending = new DropletContainer();
		try {
			urlPath = cfg.getServer() + "/pending?" + getCredentials();
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
			BufferedReader buff = new BufferedReader(in);
			String json = buff.readLine();
			conn.disconnect();

			pending = new Gson().fromJson(json, DropletContainer.class);
			if (pending.size() > 0) {
				KloudCatchTray.displayInfo(pending.size() + " files updated", "You have catched " + pending.size() + " droplets.");
			}

		} catch (MalformedURLException e) {
			KloudCatchTray.displayError("Malformed url", "");
		} catch (IOException e) {
			KloudCatchTray.displayError("Connection failed", "");
		}
	}

	private void downloadAll() {
		for (Droplet droplet : pending.getDroplets()) {
			download(droplet);
		}
	}

	private void download(Droplet droplet) {
		String url = "";
		String file = "";

		InputStream input = null;
		OutputStream output = null;
		URLConnection conn = null;

		try {
			KloudCatchTray.displayInfo("Downloading", droplet.getName());

			byte[] buf;
			int byteRead, byteWritten = 0;

			url = cfg.getServer() + "/synch/" + droplet.getId() + "?" + getCredentials();
			URL source = new URL(url);

			file = cfg.getFolder() + File.separator + droplet.getName();
			output = new BufferedOutputStream(new FileOutputStream(file));

			conn = source.openConnection();
			input = conn.getInputStream();
			buf = new byte[BUFFER_SIZE];
			while ((byteRead = input.read(buf)) != -1) {
				output.write(buf, 0, byteRead);
				byteWritten += byteRead;
			}
			confirm(droplet);
			
		} catch (MalformedURLException e) {
			KloudCatchTray.displayError("Malformed url", "");
		} catch (FileNotFoundException e) {
			KloudCatchTray.displayError("File not found", file);
		} catch (IOException e) {
			KloudCatchTray.displayError("Error synching file", file);
		} finally {
			try {
				input.close();
				output.close();
			} catch (IOException e) {
				KloudCatchTray.displayError("I/O error", "something went wrong");
			}
		}

	}

	private void confirm(Droplet droplet) {
		String urlPath = "";
		try {
			urlPath = cfg.getServer() + "/confirm/" + droplet.getId() + "?" + getCredentials();
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
			BufferedReader buff = new BufferedReader(in);
			@SuppressWarnings("unused")
			String response = buff.readLine();
			conn.disconnect();

			KloudCatchTray.displayInfo("Download complete", droplet.getName());

		} catch (MalformedURLException e) {
			KloudCatchTray.displayError("Malformed url", "");
		} catch (IOException e) {
			KloudCatchTray.displayError("Connection failed", "");
		}
	}

	private String getCredentials() {
		return "email=" + cfg.getEmail() + "&password=" + cfg.getPassword();
	}

	public void setRunning(Boolean running) {
		this.running = running;
	}

	public Boolean isRunning() {
		return running;
	}
}
