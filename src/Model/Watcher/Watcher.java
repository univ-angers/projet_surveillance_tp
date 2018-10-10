package Model.Watcher;

import Model.EtudiantExamenInfo;
import Model.ServerLinkSingleton;
import org.json.simple.JsonObject;


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
	public void sendEvent(ServerLinkSingleton serverLink, EtudiantExamenInfo etudiantExamenInfo) {
		// Envoie de l'évènement avec les données créées spécifiquement
		serverLink.send(createDataBeforeSendEvent(etudiantExamenInfo));
	}

	/**
	 * Méthode qui sera défini dans les classes filles pour traiter et construire l'objet JSON qui sera envoyé
	 */
	protected abstract JsonObject createDataBeforeSendEvent(EtudiantExamenInfo etudiantExamenInfo);

}
