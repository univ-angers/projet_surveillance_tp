package Controller;

import Model.ServerLinkSingleton;
import Model.Watcher.UsbWatcher;
import Vue.Window;


public class MainController {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// On créer un watcher
		UsbWatcher usbWatcher = new UsbWatcher();

		// On créer un lien vers le server
		ServerLinkSingleton serverLink = ServerLinkSingleton.getInstance("127.0.0.1");

		// On envoit un evenement en passant par le lien serverLink
		usbWatcher.sendEvent(serverLink);

		// On créer la fenêtre
		new Window();
	}

}
