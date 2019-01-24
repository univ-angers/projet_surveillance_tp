package com.surveillance.tp.beans;

/**
 * Contient les données d'une règle
 */
public class Regle {
	private int idRegle;
	private String description;
	private int niveauRegle;
	private int idWatcher;

	public int getIdRegle() {
		return idRegle;
	}
	public void setIdRegle(int i) {
		idRegle=i;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String d) {
		description=d;
	}

	public int getNiveauRegle() {
		return niveauRegle;
	}
	public void setNiveauRegle(int n) {
		niveauRegle=n;
	}

	public int getIdWatcher() {
		return idWatcher;
	}
	public void setIdWatcher(int i) {
		idWatcher=i;
	}
}