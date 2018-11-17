package com.surveillance.tp.dao;

import com.surveillance.tp.beans.Utilisateur;

public interface DAOUtilisateur {

	void creer(Utilisateur utilisateur) throws DAOException;

	Utilisateur trouver(int idUtil) throws DAOException;
	
	void supprimer(int idUtil) throws DAOException;
	
	void miseAJour(Utilisateur utilisateur) throws DAOException;
}
