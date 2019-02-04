package Controller;

import Model.EtudiantExamenInfoSingleton;
import Vue.Configuration;

/**
 * Controleur qui gère la fenêtre de configuration du nom et mot de passe
 */
public class ConfigController {
	private Configuration fenConfig;
	private EtudiantExamenInfoSingleton etudiant;

	public ConfigController(Configuration fenetre) {
		fenConfig=fenetre;
	}

	/**
	 * Ajoute les données de connexion de l'étudiant dans le singleton de l'application
	 * @param id
	 * @param mdp
	 * @param adrServ
	 */
	public void receptionEtudiant(String id, String mdp, String adrServ) {
		etudiant=EtudiantExamenInfoSingleton.getInstanceExistante();

		// Si l'étudiant veut modifier les infos le concernant / l'adresse du serveur
		if(etudiant!=null && (!id.equals(etudiant.getIdentifiant()) || !mdp.equals(etudiant.getMotDePasse()) || !adrServ.equals(etudiant.getAdresseServeur()))) {
			etudiant.setIdentifiant(id);
			etudiant.setMotDePasse(mdp);
			etudiant.setAdresseServeur(adrServ);
		}

		// Si l'étudiant n'a rentré encore aucune information
		if(etudiant==null) etudiant=EtudiantExamenInfoSingleton.getInstance(id, mdp, adrServ);
	}
}