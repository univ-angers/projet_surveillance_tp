package Model;

import java.util.ArrayList;

/**
 * Contient les informations de l'étudiant ainsi que les informations
 * de l'examen auquel il se connecte 
 */
public class EtudiantExamenInfoSingleton {
	// L'identifiant du singleton étudiant correspond au mail étudiant
	private String identifiant;
	private String motDePasse;
	private String adrServeur;
	private String idBDD;

	// Permettra de savoir quels watchers activer pour cet examen
	private ArrayList<Integer> listeWatchers;
	private ArrayList<String> siteASurveiller;
	// Sous la forme /a/b/c/d/e/f/g/h/i/j 
	private String numeroExamen;	

	// L'instance unique de la classe
	static EtudiantExamenInfoSingleton instance=null;

	private EtudiantExamenInfoSingleton(String id, String mdp, String adrServ) {
		identifiant=id;
		motDePasse=mdp;
		adrServeur=adrServ;
		listeWatchers=new ArrayList<>();
		siteASurveiller=new ArrayList<>();
	}

	static public EtudiantExamenInfoSingleton getInstance(String id, String mdp, String adrServ) {
		if(instance==null) instance=new EtudiantExamenInfoSingleton(id, mdp, adrServ);
		return instance;
	}

	/**
	 * Retourne l'instance existante. N'est appelée qu'après qu'elle ait été créée
	 * @return
	 */
	static public EtudiantExamenInfoSingleton getInstanceExistante() {
		return instance;
	}

	// GETTERS & SETTERS
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String id) {
		identifiant=id;
	}

	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String mdp) {
		motDePasse=mdp;
	}

	public String getAdresseServeur() {
		return adrServeur;
	}
	public void setAdresseServeur(String adrServ) {
		adrServeur=adrServ;
	}

	public void setNumeroExamen(String numExam) {
		numeroExamen=numExam;
	}
	public String getNumeroExamen() {
		return numeroExamen;
	}
	
	public void setIdBDD(String idbdd) {
		idBDD=idbdd;
	}
	public String getIdBDD() {
		return idBDD;
	}

	public ArrayList<Integer> getListeWatchers() {
		return listeWatchers;
	}
	public void setListeWatchers(ArrayList<Integer> l) {
		listeWatchers=l;
	}

	public ArrayList<String> getSiteASurveiller() {
		return siteASurveiller;
	}
	public void setSiteASurveiller(ArrayList<String> s) {
		siteASurveiller=s;
	}
}