package Model.Watcher;

import Model.ServerLinkSingleton;
import org.json.simple.JsonObject;


public abstract class Watcher {

	// Le nom du type du Watcher
	protected String type;

	// CONSTRUCTOR

	public Watcher(String type) {
		super();
		this.type = type;
	}

	/**
	 * Envoie l'évènement à l'objet qui se charge de communiquer avec le serveur
	 */
	public void sendEvent(ServerLinkSingleton serverLink) {
		// Envoie de l'évènement avec les données créées spécifiquement
		serverLink.send(createDataBeforeSendEvent());
	}

	/**
	 * Méthode qui sera défini dans les classes filles pour traiter et construire l'objet JSON qui sera envoyé
	 */
	protected abstract JsonObject createDataBeforeSendEvent();

}
