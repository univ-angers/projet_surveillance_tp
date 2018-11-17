package com.surveillance.tp.dao;

import com.surveillance.tp.beans.Utilisateur;

public interface DAOUtilisateur {

	void creer(Utilisateur utilisateur) throws DAOException;

	Utilisateur trouver(int id) throws DAOException;
	
	void supprimer(Utilisateur utilisateur) throws DAOException;
	
	void miseAJour(Utilisateur utilisateur) throws DAOException;
}
