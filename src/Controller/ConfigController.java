package Controller;

import Model.EtudiantExamenInfoSingleton;
import Vue.Configuration;

public class ConfigController 
{
	Configuration fenConfig;
	EtudiantExamenInfoSingleton etudiant;

	public ConfigController(Configuration fenetre)
	{
		fenConfig = fenetre;
	}

	public void receptionEtudiant(String id, char[] mdp, String adrServ)
	{
		etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();
		
		/* Si l'étudiant veut modifier les infos le concernant / l'adresse du serveur */
		if (etudiant != null && (!id.equals(etudiant.getIdentifiant())  || !mdp.equals(etudiant.getMotDePasse()) || !adrServ.equals(etudiant.getAdresseServeur())))
		{
			etudiant.setIdentifiant(id);
			etudiant.setMotDePasse(mdp);
			etudiant.setAdresseServeur(adrServ);
		}	
		
		/* Si l'étudiant n'a rentré encore aucune information */
		if (etudiant == null)
		{
			etudiant = EtudiantExamenInfoSingleton.getInstance(id, mdp, adrServ);	
		}
	}
}
