package Model;

public class EtudiantExamenInfoSingleton {

	private String prenomEtudiant;
	private String nomEtudiant;
	private String numeroEtudiant;
	private String adrServeur;
	
	// Sous la forme /a/b/c/d/e/f/g/h/i/j 
	private String numeroExamen;

	boolean toutesLesInfos;

	// L'instance unique de la classe
	static EtudiantExamenInfoSingleton instance = null;

	private EtudiantExamenInfoSingleton(String pren, String nom, String numEt, String adrServ)
	{
		prenomEtudiant = pren;
		nomEtudiant = nom;
		numeroEtudiant = numEt;
		adrServeur = adrServ;
		
		//Il manque le numero d'examen
		toutesLesInfos = false;
	}

	static public EtudiantExamenInfoSingleton getInstance(String pren, String nom, String numEt, String adrServ) {
		if (instance == null) {
			instance = new EtudiantExamenInfoSingleton(pren, nom, numEt, adrServ);
		}
		return instance;
	}

	static public EtudiantExamenInfoSingleton getInstanceExistante() {
		return instance;
	}

	public void affichageEtudiant()
	{
		System.out.println(prenomEtudiant + "\n" + nomEtudiant + "\n" + numeroEtudiant + "\n" + adrServeur + "\n");
}
	// GETTERS & SETTERS
	public String getPrenomEtudiant() {
		return prenomEtudiant;
	}
	public void setPrenomEtudiant(String prenomEtudiant) {
		this.prenomEtudiant = prenomEtudiant;
	}
	public String getNomEtudiant() {
		return nomEtudiant;
	}
	public void setNomEtudiant(String nomEtudiant) {
		this.nomEtudiant = nomEtudiant;
	}
	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}
	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
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
	public void setToutesLesInfosEtud()	{
		if (!(prenomEtudiant.isEmpty() && nomEtudiant.isEmpty() && numeroEtudiant.isEmpty() && adrServeur.isEmpty() && numeroExamen.isEmpty()))
			toutesLesInfos = true;
		else
			System.out.println("TOUT N'EST PAS COMPLET");
	}
}
