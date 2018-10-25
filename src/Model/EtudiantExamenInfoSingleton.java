package Model;


public class EtudiantExamenInfoSingleton {

	private String prenomEtudiant;
	private String nomEtudiant;
	private String numeroEtudiant;
	private int numeroExamen;
	
	// L'instance unique de la classe
	static EtudiantExamenInfoSingleton instance = null;

	private EtudiantExamenInfoSingleton(String pren, String nom, String numEt, int numExa)
	{
		prenomEtudiant = pren;
		nomEtudiant = nom;
		numeroEtudiant = numEt;
		numeroExamen = numExa;
	}

	static public EtudiantExamenInfoSingleton getInstance(String pren, String nom, String numEt, int numExa) {
		if (instance == null) {
			instance = new EtudiantExamenInfoSingleton(pren, nom, numEt, numExa);
		}
		return instance;
	}
	
	static public EtudiantExamenInfoSingleton getInstanceExistante() {
		return instance;
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
	public int getNumeroExamen() {
		return numeroExamen;
	}
	public void setNumeroExamen(int numeroExamen) {
		this.numeroExamen = numeroExamen;
	}

}
