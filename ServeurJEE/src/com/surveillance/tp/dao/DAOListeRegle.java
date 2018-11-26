package com.surveillance.tp.dao;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.beans.RegleExam;

public interface DAOListeRegle {
	void creer(RegleExam regle, Examen examen) throws DAOException;

	RegleExam trouver(int idRegle, int idExam) throws DAOException;
	
	void supprimer(int idRegle, int idExamn) throws DAOException;
	
	void miseAJour(RegleExam regle, Examen examen) throws DAOException;
}
