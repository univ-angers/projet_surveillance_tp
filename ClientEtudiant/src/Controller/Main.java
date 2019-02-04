package Controller;

import javax.swing.SwingUtilities;

import Model.DetectionArret;
import Vue.Connexion;

public class Main {
	public static boolean surveillanceEnCours;

	public static void main(String[] args){
		surveillanceEnCours=false;
		// Surveille si un arrêt s'effectue, qu'il soit normal ou mal intentionné (kill)
		DetectionArret detectino_arret=new DetectionArret();

		// On créer la fenêtre
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Connexion fenetre=new Connexion();
				fenetre.setVisible(true);
			}
		});
		try {
			detectino_arret.detection_arret();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}