package Controller;

import javax.swing.SwingUtilities;

import Vue.Connexion;


public class Main {
	
	public static boolean surveillanceEnCours;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		surveillanceEnCours = false;
		
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
