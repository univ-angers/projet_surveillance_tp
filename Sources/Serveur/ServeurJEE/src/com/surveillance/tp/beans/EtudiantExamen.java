package com.surveillance.tp.beans;

/**
 * Contient les données stockées dans le header d'un log d'étudiant pour un examen précis
 */

public class EtudiantExamen {
	private String nomEt;
	private String prenEt;
	private String nbAlertes;
	private String nbAlertesCritiques;
	private String nbAlertesClavier;
	private String nbAlertesNet;
	private String nbAlertesFichier;
	private String nbAlertesUSB;
	private int id;

	public String getNomEt() {
		return nomEt;
	}
	public void setNomEt(String n) {
		nomEt=n;
	}

	public String getPrenEt() {
		return prenEt;
	}
	public void setPrenEt(String p) {
		prenEt=p;
	}

	public String getNbAlertes() {
		return nbAlertes;
	}
	public void setNbAlertes(String n) {
		nbAlertes=n;
	}

	public String getNbAlertesCritiques() {
		return nbAlertesCritiques;
	}
	public void setNbAlertesCritiques(String n) {
		nbAlertesCritiques=n;
	}

	public String getNbAlertesClavier() {
		return nbAlertesClavier;
	}
	public void setNbAlertesClavier(String n) {
		nbAlertesClavier=n;
	}

	public String getNbAlertesNet() {
		return nbAlertesNet;
	}
	public void setNbAlertesNet(String n) {
		nbAlertesNet=n;
	}

	public String getNbAlertesFichier() {
		return nbAlertesFichier;
	}
	public void setNbAlertesFichier(String n) {
		nbAlertesFichier=n;
	}

	public String getNbAlertesUSB() {
		return nbAlertesUSB;
	}
	public void setNbAlertesUSB(String n) {
		nbAlertesUSB=n;
	}

	public int getId() {
		return id;
	}
	public void setId(int i) {
		id=i;
	}
}