package com.surveillance.tp.beans;

/**
 * Contient les informations d'un utilisateur
 */
public class Utilisateur {
	private int id;
	private String prenom;
	private String nom;
	private String password;
	private String mail;
	private String groupe;
	private String cleRecup;

	public String getGroupe() {
		return groupe;
	}
	public void setGroupe(String g) {
		groupe=g;
	}

	public int getId() {
		return id;
	}
	public void setId(int i) {
		id=i;
	}

	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String p) {
		prenom=p;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String n) {
		nom=n;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String p) {
		password=p;
	}

	public String getMail() {
		return mail;
	}
	public void setMail(String m) {
		mail=m;
	}

	public String getCleRecup() {
		return cleRecup;
	}
	public void setCleRecup(String c) {
		cleRecup=c;
	}
}