package Controller;

import javax.swing.SwingUtilities;

import Vue.Connexion;


public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
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
