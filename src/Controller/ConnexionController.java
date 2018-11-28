package Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONObject;

import Model.EtudiantExamenInfoSingleton;
import Model.ServerLinkSingleton;
import Model.Watcher.FileWatcher;
import Model.Watcher.KeyListenerWatcher;
import Model.Watcher.NetworkWatcher;
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

		etudiant.setNumeroExamen(idExam);
	}

	/** TODO
	 * 		VERIFIER QUE LE SERVEUR EST DISPO AVANT DE POUVOIR S'Y CO
	 */

	public void lancementSurveillance() 
	{
		//Variable qui permet de faire boucler les Watchers
		Main.surveillanceEnCours = true;

		//Sera à terme récupéré depuis le serveur
		boolean lanceUSB = true;
		boolean lanceFichier = true;
		boolean lanceVideo = true;
		boolean lanceNet = false;
		boolean lanceKey = true;

		// On créer les watchers et on les lance
		//USB
		if (lanceUSB)
		{
			UsbWatcher usbWatcher = new UsbWatcher();
			usbWatcher.start();
		}
		//FILE
		if (lanceFichier)
		{
			Path p = Paths.get(System.getProperty("user.home"));
			try {
				FileWatcher fileWatcher = new FileWatcher(Paths.get(System.getProperty("user.home")));
				// On démarre le thread du FileWatcher
				fileWatcher.start();
			} catch (IOException e) {
				System.out.println("Impossible de créer le FileWatcher");
				e.printStackTrace();
			}
		}
		//VIDEO
		if (lanceVideo)
		{
			VideoWatcher vidWatcher = new VideoWatcher();
			vidWatcher.start();
		}

		//NETWORK
		if (lanceNet)
		{
			NetworkWatcher netWatcher = new NetworkWatcher();
			netWatcher.start();
		}

		//KEYBOARD
		if (lanceKey)
		{
			KeyListenerWatcher klWatcher = new KeyListenerWatcher();
			klWatcher.start();
		}
	}

	@SuppressWarnings("unchecked")
	public boolean logIn() 
	{
		etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();
		
		//On créer un lien vers le server
		ServerLinkSingleton serverLink = ServerLinkSingleton.getInstance(etudiant.getAdresseServeur());


		JSONObject datas = new JSONObject();
		datas.put("IDexamen", etudiant.getNumeroExamen());
		datas.put("mailEtudiant", etudiant.getIdentifiant());
		datas.put("mdp", etudiant.getMotDePasse());
		datas.put("type", "connexion_etudiant");

		boolean reussi = serverLink.send(datas);

		return reussi;
	}
}
