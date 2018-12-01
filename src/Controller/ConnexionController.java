package Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import Model.EtudiantExamenInfoSingleton;
import Model.ServerLinkSingleton;
import Model.Watcher.FileWatcher;
import Model.Watcher.KeyListenerWatcher;
import Model.Watcher.NetworkWatcher;
import Model.Watcher.UsbWatcher;
import Model.Watcher.VideoWatcher;
import Vue.Connexion;

/**
 * Controlleur qui gère la fenêtre de connexion
 * Va vérifier les valeurs entrées, et permettre la connexion si celles ci sont valides
 * Va ensuite lancer les watchers en fonction de l'examen
 * @author erinyth
 *
 */
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

	/**
	 * Lance les watchers en fonction de la demande du serveur
	 */
	public void lancementSurveillance() 
	{
		//Variable qui permet de faire boucler les Watchers
		Main.surveillanceEnCours = true;

		//Passerons à vrai si le serveur le demande
		boolean lanceUSB = false;
		boolean lanceFichier = false;
		boolean lanceVideo = false;
		boolean lanceNet = false;
		boolean lanceKey = false;
		
		etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();
		ArrayList<Integer> list = new ArrayList<>();
		list = etudiant.getListeWatchers();
		
		for (int w : list) {
			switch (w)
			{
			case 1:
				lanceUSB = true;
				System.out.println(">>>> WATCHER USB ACTIVE");
				break;
			case 2:
				lanceFichier = true;
				System.out.println(">>>> WATCHER FICHIER ACTIVE");
				break;
			case 3:
				lanceVideo = true;
				System.out.println(">>>> WATCHER VIDEO ACTIVE");
				break;
			case 4:
				lanceNet = true;
				System.out.println(">>>> WATCHER NET ACTIVE");
				break;
			case 5:
				lanceKey = true;
				System.out.println(">>>> WATCHER CLAVIER ACTIVE");
				break;
			}
		}

		if (lanceUSB)
		{
			UsbWatcher usbWatcher = new UsbWatcher();
			usbWatcher.start();
		}
		
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
		
		if (lanceVideo)
		{
			VideoWatcher vidWatcher = new VideoWatcher();
			vidWatcher.start();
		}
		
		if (lanceNet)
		{
			NetworkWatcher netWatcher = new NetworkWatcher();
			netWatcher.start();
		}
		
		if (lanceKey)
		{
			KeyListenerWatcher klWatcher = new KeyListenerWatcher();
			klWatcher.start();
		}
	}

	@SuppressWarnings("unchecked")
	/**
	 * Envoi les données au serveur afin de vérifier si celles ci sont connues
	 * @return
	 */
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
