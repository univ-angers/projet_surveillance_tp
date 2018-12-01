package com.surveillance.tp.dao;

import java.util.ArrayList;

import org.json.simple.JSONArray;

import com.surveillance.tp.beans.RegleExam;

public interface DAORegleExamen {

	void creer(RegleExam regleexam) throws DAOException;

	void creerAttribut(RegleExam regleexam, String jsTab);

	public ArrayList<RegleExam> trouver(int idExam) throws DAOException;
	
	void supprimer(int idRegle, int idExam) throws DAOException;
	
	void miseAJour(RegleExam regleexam) throws DAOException;

}
