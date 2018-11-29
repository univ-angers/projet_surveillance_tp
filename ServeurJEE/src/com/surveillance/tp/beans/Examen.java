package com.surveillance.tp.beans;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Examen {
	
	private int idExam;		//PRIMARY KEY
	private int idProf;		//FOREIGN KEY
	private String matiere;
	private Time duree;
	private Timestamp heureDebut;
	
	public int getIdExam() {
		return idExam;
	}
	public void setIdExam(int idExam) {
		this.idExam = idExam;
	}
	public int getIdProf() {
		return idProf;
	}
	public void setIdProf(int idProf) {
		this.idProf = idProf;
	}
	public String getMatiere() {
		return matiere;
	}
	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}
	public Time getDuree() {
		return duree;
	}
	public void setDuree(Time duree) {
		this.duree = duree;
	}
	public Timestamp getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(Timestamp heureDebut) {
		this.heureDebut = heureDebut;
	}
}
