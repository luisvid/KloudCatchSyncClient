package com.kloudcatch.client.core;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;

import javax.swing.ImageIcon;

import com.kloudcatch.client.actions.ConfigurationActionListener;
import com.kloudcatch.client.actions.ExitActionListener;
import com.kloudcatch.client.configuration.Configuration;

public class KloudCatchTray implements Runnable{
    
	private final static int INTERVAL = 30000;
    private static TrayIcon trayIcon;
    private static Configuration cfg;
    private static long lastAccess = 0;

	private static void createAndShowGUI() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        trayIcon = new TrayIcon(createImage("icon.png", "KloudCatch"));
        final SystemTray tray = SystemTray.getSystemTray();
        
        // Create a popup menu components
        MenuItem configurationItem = new MenuItem("Configuration");
        MenuItem exitItem = new MenuItem("Exit");
        
        //Add components to popup menu
        popup.add(configurationItem);
        popup.addSeparator();
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        configurationItem.addActionListener(new ConfigurationActionListener());
        exitItem.addActionListener(new ExitActionListener());
    }
	
	private static boolean shouldNotify() {
		long currentTime = System.currentTimeMillis();
		if (cfg == null || currentTime > lastAccess + INTERVAL){
			cfg = new Configuration();
		}
		return trayIcon != null && cfg.getNotifications();
	}
	
	public static void displayMessage(String caption, String text) {
		if(shouldNotify()) {
			trayIcon.displayMessage(caption, text, TrayIcon.MessageType.NONE);
		}
	}
	

	public static void displayInfo(String caption, String text) {
		if(shouldNotify()) {
			trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
		}
	}
	
	public static void displayWarning(String caption, String text) {
		if(shouldNotify()) {
			trayIcon.displayMessage(caption, text, TrayIcon.MessageType.WARNING);
		}
	}
	
	public static void displayError(String caption, String text) {
		if(shouldNotify()) {
			trayIcon.displayMessage(caption, text, TrayIcon.MessageType.ERROR);
		}
	}
    
    protected static Image createImage(String path, String description) {
        URL imageURL = KloudCatchTray.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

	@Override
	public void run() {
	   createAndShowGUI();		
	}
}