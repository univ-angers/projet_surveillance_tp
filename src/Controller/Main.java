package Controller;

import javax.swing.SwingUtilities;

import Model.DetectionArret;
import Model.ServerLinkSingleton;
import Vue.Connexion;


public class Main 
{	
	public static boolean surveillanceEnCours;
	
	public static void main(String[] args){
				
		surveillanceEnCours = false;
		DetectionArret da = new DetectionArret();
		
		// On créer la fenêtre
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				Connexion fenetre = new Connexion();
				fenetre.setVisible(true);
			}
		});
		try {
			da.detAr();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
