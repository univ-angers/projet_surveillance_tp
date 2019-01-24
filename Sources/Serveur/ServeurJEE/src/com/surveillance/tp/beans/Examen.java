package com.surveillance.tp.beans;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Contient les données d'un examen
 */
public class Examen {
	private int idExam; // PRIMARY KEY
	private int idProf; // FOREIGN KEY
	private String matiere;
	private Time duree;
	private Timestamp heureDebut;

	public int getIdExam() {
		return idExam;
	}
	public void setIdExam(int i) {
		idExam=i;
	}

	public int getIdProf() {
		return idProf;
	}
	public void setIdProf(int i) {
		idProf=i;
	}

	public String getMatiere() {
		return matiere;
	}
	public void setMatiere(String m) {
		matiere=m;
	}

	public Time getDuree() {
		return duree;
	}
	public void setDuree(Time d) {
		duree=d;
	}

	public Timestamp getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(Timestamp h) {
		heureDebut=h;
	}
}