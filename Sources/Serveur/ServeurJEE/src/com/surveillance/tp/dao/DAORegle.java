package com.surveillance.tp.dao;

import com.surveillance.tp.beans.Regle;

public interface DAORegle {

	void creer(Regle regle) throws DAOException;

	Regle trouverSt(String typeRegle) throws DAOException;
	
	Regle trouver(int idRegle) throws DAOException;
	
	void supprimer(int idRegle) throws DAOException;
	
	void miseAJour(Regle regle) throws DAOException;
}
