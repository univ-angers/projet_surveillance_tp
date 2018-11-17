package com.surveillance.tp.dao;

import com.surveillance.tp.beans.Examen;

public interface DAOExamen {

	void creer(Examen examen) throws DAOException;

	Examen trouver(int nomExam) throws DAOException;
	
	void supprimer(Examen examen) throws DAOException;
	
	void miseAJour(Examen examen) throws DAOException;
}

