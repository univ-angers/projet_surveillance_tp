package Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import Model.EtudiantExamenInfoSingleton;
import Model.ServerLinkSingleton;
import Model.Watcher.FileWatcher;
import Model.Watcher.UsbWatcher;
import Vue.Window;


public class MainController {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TEMPORAIRE			
		Scanner saisieInfo = new Scanner(System.in);
		System.out.println("Saisissez votre prénom");
		String prenomClient = saisieInfo.next();
		System.out.println("Saisissez votre nom");
		String nomClient = saisieInfo.next();
		System.out.println("Saisissez votre numero etudiant");
		String numEtClient = saisieInfo.next();
		System.out.println("Saisissez le numero d'examen");
		String numExamSt = saisieInfo.next();
		int numExam = Integer.parseInt(numExamSt.trim());
		
		EtudiantExamenInfoSingleton etudiantCourant = EtudiantExamenInfoSingleton.getInstance(prenomClient,nomClient,numEtClient,numExam);
		
		System.out.println("INFORMATION: Vos actions sont maintenant enregistrées.");
		/////////////
				
		// On créer les watchers et on les lance
		UsbWatcher usbWatcher = new UsbWatcher(etudiantCourant);
		usbWatcher.start();
				
		Path p = Paths.get(System.getProperty("user.home"));
		try {
			FileWatcher fileWatcher = new FileWatcher(etudiantCourant,Paths.get(System.getProperty("user.home")));
			// On démarre le thread du FileWatcher
			fileWatcher.start();
		} catch (IOException e) {
			System.out.println("Impossible de créer le FileWatcher");
			e.printStackTrace();
		}

		// On créer un lien vers le server
		ServerLinkSingleton serverLink = ServerLinkSingleton.getInstance("localhost");


		// On créer la fenêtre
		new Window();
	}
}
