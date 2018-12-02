package com.surveillance.tp.beans;

/**
 * Contient les données stockées dans le header d'un log d'étudiant pour un examen précis
 *{"header":{"nbClavier":2019,"nbNet":0,"nbCrit":0,"nbFichier":0,"nbLog":0,"nbUSB":0,"nom":"noob","prenom":"prozengan"}
 */


public class EtudiantExamen {
	private String nomEt;
	private String prenEt;
	private int nbAlertes;
	private int nbAlertesCritiques;
	private int nbAlertesClavier;
	private int nbAlertesNet;
	private int nbAlertesFichier;
	private int nbAlertesUSB;
	public String getNomEt() {
		return nomEt;
	}
	public void setNomEt(String nomEt) {
		this.nomEt = nomEt;
	}
	public String getPrenEt() {
		return prenEt;
	}
	public void setPrenEt(String prenEt) {
		this.prenEt = prenEt;
	}
	public int getNbAlertes() {
		return nbAlertes;
	}
	public void setNbAlertes(int nbAlertes) {
		this.nbAlertes = nbAlertes;
	}
	public int getNbAlertesCritiques() {
		return nbAlertesCritiques;
	}
	public void setNbAlertesCritiques(int nbAlertesCritiques) {
		this.nbAlertesCritiques = nbAlertesCritiques;
	}
	public int getNbAlertesClavier() {
		return nbAlertesClavier;
	}
	public void setNbAlertesClavier(int nbAlertesClavier) {
		this.nbAlertesClavier = nbAlertesClavier;
	}
	public int getNbAlertesNet() {
		return nbAlertesNet;
	}
	public void setNbAlertesNet(int nbAlertesNet) {
		this.nbAlertesNet = nbAlertesNet;
	}
	public int getNbAlertesFichier() {
		return nbAlertesFichier;
	}
	public void setNbAlertesFichier(int nbAlertesFichier) {
		this.nbAlertesFichier = nbAlertesFichier;
	}
	public int getNbAlertesUSB() {
		return nbAlertesUSB;
	}
	public void setNbAlertesUSB(int nbAlertesUSB) {
		this.nbAlertesUSB = nbAlertesUSB;
	}
}
