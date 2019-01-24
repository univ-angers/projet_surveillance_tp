package com.surveillance.tp.dao;

import java.util.ArrayList;

import com.surveillance.tp.beans.Utilisateur;

public interface DAOUtilisateur {
	void creer(Utilisateur utilisateur) throws DAOException;
	Utilisateur trouverMdp(String mail, String password) throws DAOException;
	Utilisateur trouverCleReset(String cle) throws DAOException;
	Utilisateur trouverID(int idUtil);
	Utilisateur trouver(String mail) throws DAOException;
	void supprimer(int idUtil) throws DAOException;
	void miseAJour(Utilisateur utilisateur) throws DAOException;
	ArrayList<Utilisateur> recupererUtilisateurs() throws DAOException;
	public void miseAJourReset(Utilisateur utilisateur, String token) throws DAOException;
}