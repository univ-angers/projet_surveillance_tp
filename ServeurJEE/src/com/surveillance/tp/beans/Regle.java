package com.surveillance.tp.beans;

import org.json.simple.JSONObject;

public class Regle {

	private int idRegle;
	private String description;
	private int niveauRegle;
	private int idWatcher;
	
	public int getIdRegle() {
		return idRegle;
	}
	public void setIdRegle(int idRegle) {
		this.idRegle = idRegle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getNiveauRegle() {
		return niveauRegle;
	}
	public void setNiveauRegle(int niveauRegle) {
		this.niveauRegle = niveauRegle;
	}
	public int getIdWatcher() {
		return idWatcher;
	}
	public void setIdWatcher(int idWatcher) {
		this.idWatcher = idWatcher;
	}
}
