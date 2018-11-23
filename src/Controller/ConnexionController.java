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
		NetworkWatcher netWatcher = new NetworkWatcher();
		netWatcher.start();
		
		//KEYBOARD
		KeyListenerWatcher klWatcher = new KeyListenerWatcher();
		klWatcher.start();

		//On créer un lien vers le server
		ServerLinkSingleton serverLink = ServerLinkSingleton.getInstance("localhost");
	}

	@SuppressWarnings("unchecked")
	public void logIn() 
	{
		ServerLinkSingleton server;
		server = ServerLinkSingleton.getInstanceExistante();
		
		JSONObject datas = new JSONObject();
		datas.put("IDexamen", EtudiantExamenInfoSingleton.getInstanceExistante().getNumeroExamen());
		datas.put("IDetudiant", EtudiantExamenInfoSingleton.getInstanceExistante().getIdentifiant());
		datas.put("mdp", EtudiantExamenInfoSingleton.getInstanceExistante().getMotDePasse());
		datas.put("type", "connexion_etudiant");
		
		server.send(datas);
	}
}
