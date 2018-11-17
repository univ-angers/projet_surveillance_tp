package com.surveillance.tp.beans;

public class Utilisateur {
	private int id;
	private String prenom;
	private String nom;
	private String numero_etudiant;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNumero_etudiant() {
		return numero_etudiant;
	}
	public void setNumero_etudiant(String numero_etudiant) {
		this.numero_etudiant = numero_etudiant;
	}	
}
