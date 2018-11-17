package com.surveillance.tp.dao;

import com.surveillance.tp.beans.Examen;

public interface DAOExamen {

	void creer(Examen examen) throws DAOException;

	Examen trouver(int idExam) throws DAOException;
	
	void supprimer(int idExam) throws DAOException;
	
	void miseAJour(Examen examen) throws DAOException;
}

