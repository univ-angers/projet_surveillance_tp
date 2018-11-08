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

	public void receptionEtudiant(String pren, String nom, String numEt, String adrServ)
	{
		etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();
		
		/* Si l'étudiant veut modifier les infos le concernant / l'adresse du serveur */
		if (etudiant != null && (!nom.equals(etudiant.getNomEtudiant())  || !pren.equals(etudiant.getPrenomEtudiant()) || !numEt.equals(etudiant.getNumeroEtudiant()) || !adrServ.equals(etudiant.getAdresseServeur())))
		{
			etudiant.setNomEtudiant(nom);
			etudiant.setPrenomEtudiant(pren);
			etudiant.setNumeroEtudiant(numEt);
			etudiant.setAdresseServeur(adrServ);
		}	
		
		/* Si l'étudiant n'a rentré encore aucune information */
		if (etudiant == null)
		{
			etudiant = EtudiantExamenInfoSingleton.getInstance(pren, nom, numEt, adrServ);	
		}
	}
}
