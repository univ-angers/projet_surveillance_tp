package com.surveillance.tp.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.beans.reponseTest;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;
import com.surveillance.tp.utilitaire.directoryManager;

public class receptionJSON extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	private DAOExamen daoExamen;
	private DAOUtilisateur daoUtilisateur;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		reponseTest rep = reponseTest.getInstance();
		String res = rep.getJSON();

		System.out.println("DEBUG GET: " + res);

		/* Stockage du bean dans la request */
		request.setAttribute("ResultatJSON", res);

		/* Transmission vers la page en charge de l'affichage des résultats */
		this.getServletContext().getRequestDispatcher( "/WEB-INF/affichageJSON.jsp" ).forward( request, response );

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			StringBuilder sb = new StringBuilder();
			BufferedReader br = request.getReader();
			String str = null;
			String resultat = "";

			while ((str = br.readLine()) != null) {
				resultat = resultat + str;
			}

			JSONParser parser = new JSONParser();
			JSONObject jObj = (JSONObject) parser.parse(resultat);


			System.out.println("DEBUG: CHAINE JSON RECU: " + resultat);		

			String type = (String) jObj.get("type");
			System.out.println("DEBUG TYPE: " + type);


			if ( type.equals("connexion_etudiant"))
				connexionEtudiant(jObj);
			else
				ajoutLog(jObj);




		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gère la connexion d'un étudiant
	 * @param obj
	 */
	public void connexionEtudiant(JSONObject obj)
	{
		System.out.println("DEBUG: Connexion détéctée");
		String mail = (String) obj.get("mailEtudiant");
		
		System.out.println("Mail à chercher: " + mail);

		System.out.println("DEBUG: Faire ce qu'il faut");
		//Verifier la présence de l'utilisateur dans la BDD avec son numero étudiant et son identifiant
		//Si c'est le cas, envoyer la réponse au client qu'il peut démarrer les watchers
		//Et création du fichier
		Utilisateur util = 	daoUtilisateur.trouver(mail);
		int idEtud = util.getId();

		String idExamen = (String) obj.get("IDexamen");	
		String chemin = directoryManager.idDbToString(Integer.parseInt(idExamen));	//A REVOIR POUR LE STATIC

		//Exemple chemin: /opt/data_dir/0/0/0/0/0/0/0/1/4/7/247/logEtud.lg
		chemin = chemin + "/" + idEtud + "/";
		
		File dossierEt = new File(chemin);
		dossierEt.mkdir();
	}

	/**
	 * Ajoute les informations de l'alerte adns le fichier log de l'étudiant
	 * @param obj
	 */
	public void ajoutLog(JSONObject obj)
	{
		System.out.println("DEBUG: Ajout dans le fichier log ");
		//Si on est ici, on a déjà un dossier + fichier log créé pour l'examen

		String mail = (String) obj.get("mailEtudiant");
		Utilisateur util = 	daoUtilisateur.trouver(mail);
		int idEtud = util.getId();

		String idExamen = (String) obj.get("IDexamen");	
		String chemin = directoryManager.idDbToString(Integer.parseInt(idExamen));	//A REVOIR POUR LE STATIC

		//Exemple chemin: /opt/data_dir/0/0/0/0/0/0/0/1/4/7/247/logEtud.lg
		chemin = chemin + "/" + idEtud + "/logEtud.lg";
		String informationLog = obj.toString() + "\n";
		
		System.out.println("DEBUG: Chemin du log = " + chemin);
		System.out.println("DEBUG: Log ajouté = " + informationLog);

		//Ecriture de la string informationLog dans le fichier logEtud.lg
		FileWriter writer;
		try {
			writer = new FileWriter(chemin, true);
			writer.write(informationLog);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
