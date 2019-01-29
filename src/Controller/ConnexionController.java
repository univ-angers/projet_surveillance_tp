package Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

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
 * Controleur qui gère la fenêtre de connexion
 * Va vérifier les valeurs entrées, et permettre la connexion si celles ci sont valides
 * Va ensuite lancer les watchers en fonction de l'examen
 * @author erinyth
 *
 */
public class ConnexionController
{
	private Connexion fenConnexion;
	private EtudiantExamenInfoSingleton etudiant;

	public ConnexionController(Connexion fenetre)
	{
		fenConnexion = fenetre;
	}

	/**
	 * Ajoute l'id de l'examen au singleton d'étudiant examen en cours
	 * @param idExam
	 */
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
				break;
			case 2:
				lanceFichier = true;
				break;
			case 3:
				lanceVideo = true;
				break;
			case 4:
				lanceNet = true;
				break;
			case 5:
				lanceKey = true;
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
	 * Envoi les données de connexion au serveur afin de vérifier si celles ci sont connues
	 * @return Vrai si la connexion est possible
	 */
	public boolean logIn()
	{
		String md5_pass = null;
		try {
			String mdp = etudiant.getMotDePasse();
			MessageDigest md = MessageDigest.getInstance("MD5");md.update(mdp.getBytes());
		    byte[] digest = md.digest();
		    md5_pass = DatatypeConverter.printHexBinary(digest).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();

		//On créer un lien vers le server
		ServerLinkSingleton serverLink = ServerLinkSingleton.getInstance(etudiant.getAdresseServeur());


		JSONObject datas = new JSONObject();
		datas.put("IDexamen", etudiant.getNumeroExamen());
		datas.put("mailEtudiant", etudiant.getIdentifiant());
		datas.put("mdp", md5_pass);
		datas.put("type", "connexion_etudiant");
		boolean reussi = serverLink.send(datas);

		return reussi;
	}
}
