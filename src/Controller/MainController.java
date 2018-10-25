package Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import Model.EtudiantExamenInfo;
import Model.ServerLinkSingleton;
import Model.Watcher.FileWatcher;
import Model.Watcher.UsbWatcher;
import Vue.Window;


public class MainController {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// On créer les watchers et on les lance
		UsbWatcher usbWatcher = new UsbWatcher();
		usbWatcher.start();
				
		Path p = Paths.get(System.getProperty("user.home"));
		try {
			FileWatcher fileWatcher = new FileWatcher(Paths.get(System.getProperty("user.home")));
			// On démarre le thread du FileWatcher
			fileWatcher.start();
		} catch (IOException e) {
			System.out.println("Impossible de créer le FileWatcher");
			e.printStackTrace();
		}

		// On créer un lien vers le server
		ServerLinkSingleton serverLink = ServerLinkSingleton.getInstance("127.0.0.1");

		// On créer un etudiant et un examen fictif
		EtudiantExamenInfo etudiantExamenInfo = new EtudiantExamenInfo();
		etudiantExamenInfo.setPrenomEtudiant("Prenom");
		etudiantExamenInfo.setNomEtudiant("Nom");
		etudiantExamenInfo.setNumeroEtudiant("1234");
		etudiantExamenInfo.setNumeroExamen(1234);

		// On envoit un evenement en passant par le lien serverLink
		usbWatcher.sendEvent(serverLink, etudiantExamenInfo);
		

		// On créer la fenêtre
		new Window();
	}
}
