package Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import Model.EtudiantExamenInfoSingleton;
import Model.ServerLinkSingleton;
import Model.Watcher.FileWatcher;
import Model.Watcher.UsbWatcher;
import Model.Watcher.VideoWatcher;
import Vue.Connexion;

public class ConnexionController 
{
	Connexion fenConnexion;
	EtudiantExamenInfoSingleton etudiant;

	public ConnexionController(Connexion fenetre)
	{
		fenConnexion = fenetre;
	}

	public void receptionIdExamen(String idExam) 
	{
		etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();

		//PENSER A COMPLETER L'ID DE L'EXAM POUR QU'IL FASSE 10 CARACTERES ET METTRE DES / ENTRE CHAQUE CARACTERE POUR LE CHEMIN		
		while (idExam.length() != 10)
		{
			idExam = "0" + idExam;
		}

		/* A FAIRE AU NIVEAU DU SERVEUR POUR POUVOIR NOMMER LES VIDEOS CORRECTEMENT 
		char[] tmp = idExam.toCharArray();
		idExam = "";

		for(int i = 0; i < 10; i++)
		{
			idExam = idExam + "/" + tmp[i];
		}
		*/

		etudiant.setNumeroExamen(idExam);
	}

	public void lancementSurveillance() 
	{
		// On créer les watchers et on les lance
		//USB
		UsbWatcher usbWatcher = new UsbWatcher();
		usbWatcher.start();
		//FILE
		Path p = Paths.get(System.getProperty("user.home"));
		try {
			FileWatcher fileWatcher = new FileWatcher(Paths.get(System.getProperty("user.home")));
			// On démarre le thread du FileWatcher
			fileWatcher.start();
		} catch (IOException e) {
			System.out.println("Impossible de créer le FileWatcher");
			e.printStackTrace();
		}
		//VIDEO
		VideoWatcher vidWatcher = new VideoWatcher();
		vidWatcher.start();
		//NETWORK
		//NetworkWatcher netWatcher = new NetworkWatcher(etudiantCourant);
		//netWatcher.start();

		// On créer un lien vers le server
		ServerLinkSingleton serverLink = ServerLinkSingleton.getInstance("localhost");
	}

}