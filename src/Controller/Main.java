package Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import Model.EtudiantExamenInfoSingleton;
import Model.ServerLinkSingleton;
import Model.Watcher.FileWatcher;
import Model.Watcher.NetworkWatcher;
import Model.Watcher.UsbWatcher;
import Vue.Connexion;


public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		// On créer la fenêtre
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				Connexion fenetre = new Connexion();
				fenetre.setVisible(true);
			}
		});
		
	}
}
