package com.surveillance.tp.dao;

import com.surveillance.tp.beans.RegleExam;

public interface DAORegleExamen {

	void creer(RegleExam regleexam) throws DAOException;

	RegleExam trouver(int idRegle, int idExam) throws DAOException;
	
	void supprimer(int idRegle, int idExam) throws DAOException;
	
	void miseAJour(RegleExam regleexam) throws DAOException;
}
