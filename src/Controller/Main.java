package Controller;

import javax.swing.SwingUtilities;

import Model.ServerLinkSingleton;
import Vue.Connexion;


public class Main {
	
	public static boolean surveillanceEnCours;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		surveillanceEnCours = false;

		//On créer un lien vers le server
		ServerLinkSingleton serverLink = ServerLinkSingleton.getInstance("localhost");
		
		// On créer la fenêtre
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				Connexion fenetre = new Connexion();
				fenetre.setVisible(true);
			}
		});
		
	}
}
