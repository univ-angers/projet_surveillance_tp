package Model;

import java.util.ArrayList;

public class EtudiantExamenInfoSingleton {

	// l'identifiant du singleton étudiant correspond au mail étudiant
	private String identifiant;
	private String motDePasse;
	private String adrServeur;
	private String idBDD;

	//Permettra de savoir quels watchers activer pour cet examen
	private ArrayList<Integer> listeWatchers;
	
	// Sous la forme /a/b/c/d/e/f/g/h/i/j 
	private String numeroExamen;

	boolean toutesLesInfos;

	// L'instance unique de la classe
	static EtudiantExamenInfoSingleton instance = null;

	private EtudiantExamenInfoSingleton(String id, String mdp, String adrServ)
	{
		identifiant = id;
		motDePasse = mdp;
		adrServeur = adrServ;
		listeWatchers = new ArrayList<>();

		//Il manque le numero d'examen
		toutesLesInfos = false;
	}

	static public EtudiantExamenInfoSingleton getInstance(String id, String mdp, String adrServ) {
		if (instance == null) {
			instance = new EtudiantExamenInfoSingleton(id, mdp, adrServ);
		}
		return instance;
	}

	static public EtudiantExamenInfoSingleton getInstanceExistante() {
		return instance;
	}

	// GETTERS & SETTERS
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String id) {
		this.identifiant = id;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String mdp) {
		this.motDePasse = mdp;
	}
	public String getAdresseServeur() {
		return adrServeur;
	}
	public void setAdresseServeur(String adrServ) {
		this.adrServeur = adrServ;
	}
	public void setNumeroExamen(String numExam) {
		this.numeroExamen = numExam;
	}
	public String getNumeroExamen() {
		return numeroExamen;
	}
	public boolean getToutesLesInfosEtud() {
		return toutesLesInfos;
	}
	
	public void setIdBDD(String idbdd)
	{
		this.idBDD = idbdd;
	}
	public String getIdBDD()
	{
		return this.idBDD;
	}
	
	public void setToutesLesInfosEtud()	{
		if (!(identifiant.isEmpty() && motDePasse.length() == 0 && adrServeur.isEmpty() && numeroExamen.isEmpty()))
			toutesLesInfos = true;
		else
			System.out.println("TOUT N'EST PAS COMPLET");
	}

	public ArrayList<Integer> getListeWatchers() {
		return listeWatchers;
	}

	public void setListeWatchers(ArrayList<Integer> listeWatchers) {
		this.listeWatchers = listeWatchers;
	}
}
