package com.surveillance.tp.dao;

import com.surveillance.tp.beans.Examen;

public interface DAOExamen {

	void creer( Examen examen ) throws DAOException;

	Examen trouver( String nomExam ) throws DAOException;
}

