package com.surveillance.tp.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.surveillance.tp.beans.Examen;
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
			
			//Conversion de la chaine en JSON
			JSONParser parser = new JSONParser();
			JSONObject jObj = (JSONObject) parser.parse(resultat);

			System.out.println("DEBUG: CHAINE JSON RECUE SUR JEE: " + resultat);	
			//Recupération du type de données
			String type = (String) jObj.get("type");

			if ( type.equals("connexion_etudiant"))
				connexionEtudiant(jObj,response);
			else if ( type.equals("get_time_exam"))
				retourTimer(jObj, response);
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
	public void connexionEtudiant(JSONObject obj, HttpServletResponse rep)
	{
		System.out.println("DEBUG: Connexion détéctée");
		String mail = (String) obj.get("mailEtudiant");
		String motDePasse = (String) obj.get("mdp");
		String idExam = (String) obj.get("IDexamen");
		int idEx = Integer.parseInt(idExam);

		//System.out.println("Mail à chercher: " + mail);
		//Verifier la présence de l'utilisateur dans la BDD avec son numero étudiant et son identifiant
		//Si c'est le cas, envoyer la réponse au client qu'il peut démarrer les watchers
		//Et création du fichier
		Utilisateur util = 	daoUtilisateur.trouver(mail);
		Examen exam = daoExamen.trouver(idEx);

		//Si l'utilisateur existe et que son mot de passe est correct, ainsi qu'un examen valide
		if (util != null && exam != null &&  util.getPassword().equals(motDePasse))
		{
			int idEtud = util.getId();

			String chemin = directoryManager.idDbToString(idEx);	//A REVOIR POUR LE STATIC

			//Exemple chemin: /opt/data_dir/0/0/0/0/0/0/0/1/4/7/247/logEtud.lg
			chemin = chemin + "/" + idEtud + "/";

			File dossierEt = new File(chemin);
			dossierEt.mkdir();

			//On lui renvoie sont ID afin de pouvoir lancer les watchers
			String retour = "{\"type\":\"rep_co_pos\",\"code\":200,\"idbdd\":\"" + String.valueOf(idEtud) + "\"}";
			envoiInfoClient(retour, rep);	
		}
		else
		//Dans tous les autres cas, on envoie que l'on a pas retrouvé les informations nécessaires
		{
			String retour = "{\"type\":\"rep_co_neg\",\"code\":200}";
			envoiInfoClient(retour, rep);	
		}
	}

	/** 
	 * Renvoie le timer au client
	 * @param jObj
	 * @param response
	 */
	private void retourTimer(JSONObject obj, HttpServletResponse rep) {
		System.out.println("DEBUG: RERESH TIMER DEMANDE");
		String idExamenSt = (String) obj.get("IDexamen");	
		int idEx = Integer.parseInt(idExamenSt);
		Examen exam = daoExamen.trouver(idEx);
		
		if (exam != null)
		{
			Date dateDebut = exam.getHeureDebut();
			Time duree = exam.getDuree();
			
			long tempsDebut = dateDebut.getTime();
			long tempsDuree = duree.getTime();
			long heureFin = tempsDuree + tempsDebut;			
			long tempsActuel = System.currentTimeMillis();
			
			long tempsRestant = heureFin - tempsActuel;			
			
			//CALCULS A REVOIR
			
			Date fin = new Date(tempsRestant);
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			String temps = formatter.format(fin);
			
			System.out.println("DEBUG: Temps restant = " + temps);
			
			String retour = "{\"type\":\"rep_temps\",\"code\":200,\"tps\":\"" + temps + "\"}";
			envoiInfoClient(retour, rep);			
		}
	}

	/**
	 * Ajoute les informations de l'alerte dans le fichier log de l'étudiant
	 * @param obj
	 */
	public void ajoutLog(JSONObject obj)
	{
		/*
		 * 
		 * TODO: Rechercher l'ID de la règle qui doit être appliquée dans la BDD, et la mettre dans le log
		 * 
		 */
		
		//Si on est ici, on a déjà un dossier + fichier log créé pour l'examen
		String mail = (String) obj.get("mailEtudiant");
		Utilisateur util = 	daoUtilisateur.trouver(mail);
		int idEtud = util.getId();

		String idExamen = (String) obj.get("IDexamen");	
		String chemin = directoryManager.idDbToString(Integer.parseInt(idExamen));

		//Exemple chemin: /opt/data_dir/0/0/0/0/0/0/0/1/4/7/247/logNom.lg
		chemin = chemin + "/" + idEtud + "/log" + util.getNom() + ".lg";
		String informationLog = obj.toString() + "\n";

		//System.out.println("DEBUG: Chemin du log = " + chemin);
		//System.out.println("DEBUG: Log ajouté = " + informationLog);

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

	/**
	 * Envoi l'information contenue dans st au client qui a envoyé son JSON
	 * @param st
	 * @param rep
	 */
	public void envoiInfoClient(String st,HttpServletResponse rep)
	{
		//On renvoie à l'utilisateur son ID afin de pouvoir lancer le watcher vidéo avec le bon chemin
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("application/json; charset=utf-8");
		PrintWriter out;
		try {
			out = rep.getWriter();
			out.println(st);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
