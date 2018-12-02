package com.surveillance.tp.dao;

import java.util.ArrayList;

import com.surveillance.tp.beans.Examen;

public interface DAOExamen {

	void creer(Examen examen) throws DAOException;

	Examen trouver(int idExam) throws DAOException;
	Examen trouverExamenUtil(int id_util);
	
	void supprimer(int idExam) throws DAOException;
	
	void miseAJour(Examen examen) throws DAOException;
	
	void updateExamen(int id_user) throws DAOException;
	
	ArrayList<Examen>recupererExams(int id_user)throws DAOException;
}

