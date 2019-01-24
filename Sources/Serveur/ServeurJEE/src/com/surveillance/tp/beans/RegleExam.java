package com.surveillance.tp.beans;

/**
 * Contient les informations d'une règle liée à un examen spécifique
 */
public class RegleExam {
	private int idRegle;
	private int idExam;
	private String attributs;

	public int getIdRegle() {
		return idRegle;
	}
	public void setIdRegle(int i) {
		idRegle=i;
	}

	public int getIdExam() {
		return idExam;
	}
	public void setIdExam(int i) {
		idExam=i;
	}

	public String getAttributs() {
		return attributs;
	}
	public void setAttributs(String a) {
		attributs=a;
	}
}