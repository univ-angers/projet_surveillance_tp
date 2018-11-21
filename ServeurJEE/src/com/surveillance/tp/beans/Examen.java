package com.surveillance.tp.beans;

import java.sql.Date;
import java.sql.Time;

public class Examen {
	
	private int idExam;		//PRIMARY KEY
	private int idProf;		//FOREIGN KEY
	private String matiere;
	private Time duree;
	private Date heureDebut;
	
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
	public Date getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(Date heureDebut) {
		this.heureDebut = heureDebut;
	}
}
