package com.kloudcatch.client.core;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class KloudCatch {

	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("swing.boldMetal", Boolean.FALSE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(new KloudCatchTray());

		Service service = new Service();
		service.start();
		try {
			service.join();
		} catch (InterruptedException e) {
			if(service.isRunning()){
				service.setRunning(false);
			}
		}
	}
}
