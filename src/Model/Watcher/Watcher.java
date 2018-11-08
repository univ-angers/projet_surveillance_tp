package Model.Watcher;

import Model.EtudiantExamenInfoSingleton;
import Model.ServerLinkSingleton;

import org.json.simple.JSONObject;


public abstract class Watcher extends Thread {
	
	// Le nom du type du Watcher
	protected String type;

	// CONSTRUCTOR

	public Watcher(String type) {
		this.type = type;
	}

	/**
	 * Envoie l'évènement à l'objet qui se charge de communiquer avec le serveur
	 */
	public void sendEvent(ServerLinkSingleton serverLink, JSONObject infos) {
		// Envoie de l'évènement avec les données créées spécifiquement
		serverLink.send(infos);
	}

	/**
	 * Méthode qui sera défini dans les classes filles pour traiter et construire l'objet JSON qui sera envoyé
	 */
	@SuppressWarnings("unchecked")
	protected  void createDataBeforeSendEvent(String information, String nivAlerte)
	{
		// Niveau d'alerte à ajouter et autres infos si besoin
		JSONObject datas = new JSONObject();
		datas.put("Niveau", nivAlerte);
		datas.put("IDexamen", EtudiantExamenInfoSingleton.getInstanceExistante().getNumeroExamen());
		datas.put("IDEtudiant", EtudiantExamenInfoSingleton.getInstanceExistante().getIdentifiant());
		datas.put("mdp", EtudiantExamenInfoSingleton.getInstanceExistante().getMotDePasse());
		datas.put("info", information);
		
		ServerLinkSingleton SLS = ServerLinkSingleton.getInstanceExistante();
		this.sendEvent(SLS, datas);
	}

}
