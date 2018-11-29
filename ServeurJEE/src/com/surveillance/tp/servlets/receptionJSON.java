package com.surveillance.tp.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.beans.Regle;
import com.surveillance.tp.beans.RegleExam;
import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.beans.reponseTest;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAORegle;
import com.surveillance.tp.dao.DAORegleExamen;
import com.surveillance.tp.dao.DAOUtilisateur;
import com.surveillance.tp.utilitaire.directoryManager;

public class receptionJSON extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	private DAOExamen daoExamen;
	private DAOUtilisateur daoUtilisateur;
	private DAORegle daoRegle;
	private DAORegleExamen daoRegleExam;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
		/* Récupération d'une instance de notre DAO Examen */
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
		/* Récupération d'une instance de notre DAO Regle */
		this.daoRegle = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getRegleDao();
		/* Récupération d'une instance de notre DAO RegleExam */
		this.daoRegleExam = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getRegleExamDao();

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

		Utilisateur util = 	daoUtilisateur.trouverMdp(mail, motDePasse);
		Examen exam = daoExamen.trouver(idEx);

		//Si l'utilisateur existe et que son mot de passe est correct, ainsi qu'un examen valide
		if (util != null && exam != null)
		{
			int idEtud = util.getId();

			String chemin = directoryManager.idDbToString(idEx);

			//Exemple chemin: /opt/data_dir/0/0/0/0/0/0/0/1/4/7/247/logEtud.lg
			chemin = chemin + "/" + idEtud + "/";

			//Créer le dossier de l'étudiant
			File dossierEt = new File(chemin);
			dossierEt.mkdir();

			//Créer le log de l'étudiant et son header
			String cheminLog = chemin + "log" + util.getId() + ".lg";
			File fichierLog = new File(cheminLog);
			//Dans le cas ou le fichier existe déjà (un étudiant qui quitte l'application et revient), on ne recréé pas de header
			if (!fichierLog.exists())
			{
				try {
					fichierLog.createNewFile();

					String jsonStr = "{\"header\":[{\"nbLog\":0,\"nbCrit\":0,\"nbNet\":0,\"nbClavier\":0,\"nbFichier\":0,\"nbUSB\":0}],\"body\":[{}]}";
					JSONParser parser = new JSONParser();
					JSONObject objInit = (JSONObject) parser.parse(jsonStr);


					JSONObject header = new JSONObject();
					header.put("nbLog", 0);
					header.put("nbCrit", 0);
					header.put("nbNet", 0);
					header.put("nbClavier", 0);
					header.put("nbFichier", 0);
					header.put("nbUSB", 0);

					JSONArray body = new JSONArray();

					objInit.put("header", header);
					objInit.put("body", body);

					FileWriter file = new FileWriter(cheminLog, true);
					try {
						file.write(objInit.toJSONString());

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						file.flush();
						file.close();
					}
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
			}

			//On lui renvoie son ID afin de pouvoir lancer les watchers
			JSONObject jsObj = new JSONObject();
			jsObj.put("type", "rep_co_pos");
			jsObj.put("code", 200);
			jsObj.put("idbdd", String.valueOf(idEtud));

			//Récupération des règles appliquées pour l'examen auquel l'étudiant se connecte
			ArrayList<RegleExam> listeReglesAppliquees = new ArrayList<>();
			ArrayList<Integer> listeIdWatcher = new ArrayList<>();
			listeReglesAppliquees = daoRegleExam.trouver(exam.getIdExam());
	
			for (RegleExam re : listeReglesAppliquees) {
				Regle r = daoRegle.trouver(re.getIdRegle());

				boolean dejaExistant = false;
				for (int i : listeIdWatcher)
				{
					if (i == r.getIdWatcher())
						dejaExistant = true;
				}
				if (!dejaExistant)
					listeIdWatcher.add(r.getIdWatcher());
			}
			
			int val = 1;
			JSONArray jsWatchers = new JSONArray();
			for (int i : listeIdWatcher)
			{
				JSONObject objW = new JSONObject();
				objW.put("W"+val, i);
				jsWatchers.add(objW);
				val++;
			}
			
			jsObj.put("list_watcher", jsWatchers);
			String retour = jsObj.toJSONString();
			envoiInfoClient(retour, rep);	
		}
		else
			//Dans tous les autres cas, on envoie que l'on a pas retrouvé les informations nécessaires
		{			
			JSONObject jsObj = new JSONObject();
			jsObj.put("type", "rep_co_neg");
			jsObj.put("code", 200);
			String retour = jsObj.toJSONString();
			envoiInfoClient(retour, rep);	
		}
	}

	/** 
	 * Renvoie le timer au client
	 * @param jObj
	 * @param response
	 */
	private void retourTimer(JSONObject obj, HttpServletResponse rep) {
		String idExamenSt = (String)obj.get("IDexamen");	
		int idEx = Integer.parseInt(idExamenSt);
		Examen exam = daoExamen.trouver(idEx);

		if (exam != null)
		{
			Timestamp dateDebut = exam.getHeureDebut();
			//Time dateDebut = exam.getHeureDebut();
			System.out.println("dateDebut :" + dateDebut);
			Time duree = exam.getDuree();
			long GMT1 = 3600000;		//Decalage horaire du à GMT+1
			
			long tempsDebut = dateDebut.getTime();
			long tempsDuree = duree.getTime();
			long heureFin = tempsDuree + tempsDebut + GMT1;
			long tempsActuel = System.currentTimeMillis();

			long tempsRestant = heureFin - tempsActuel;		

			Date fin = new Date(tempsRestant);
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			String temps = formatter.format(fin);

			JSONObject jsObj = new JSONObject();
			jsObj.put("type", "rep_temps");
			jsObj.put("code", 200);
			jsObj.put("tps", temps);
			String retour = jsObj.toJSONString();
			envoiInfoClient(retour, rep);			
		}
	}

	/**
	 * Ajoute les informations de l'alerte dans le fichier log de l'étudiant
	 * @param obj Alerte reçue
	 */
	public void ajoutLog(JSONObject alerte)
	{
		/*
		 * 
		 * TODO: Rechercher l'ID de la règle qui doit être appliquée dans la BDD, et la mettre dans le log
		 * 
		 */

		//Si on est ici, on a déjà un dossier + fichier log créé pour l'examen
		String mail = (String) alerte.get("mailEtudiant");
		Utilisateur util = 	daoUtilisateur.trouver(mail);
		int idEtud = util.getId();

		String idExamen = (String) alerte.get("IDexamen");	
		String chemin = directoryManager.idDbToString(Integer.parseInt(idExamen));

		//Exemple chemin: /opt/data_dir/0/0/0/0/0/0/0/1/4/7/247/logNom.lg
		chemin = chemin + "/" + idEtud + "/log" + util.getId() + ".lg";

		miseAJourLog(chemin, alerte);		
	}

	/**
	 * Met à jour le header du fichier log et ajoute l'alerte reçue
	 * @param chemin
	 * @param alerte
	 */
	void miseAJourLog(String chemin, JSONObject alerte)
	{
		//Modification du header
		JSONParser parser = new JSONParser();
		try {
			FileReader lecture = new FileReader(chemin);

			Object logExistant = parser.parse(lecture);
			JSONObject jsonLog = (JSONObject) logExistant;
			JSONObject header = (JSONObject) jsonLog.get("header");
			JSONArray body = (JSONArray) jsonLog.get("body");

			//Récupération des informations header existantes
			long nbLog = (long) header.get("nbLog");
			long nbCrit = (long) header.get("nbCrit");
			long nbClavier = (long) header.get("nbClavier");
			long nbFichier = (long) header.get("nbFichier");
			long nbUSB = (long) header.get("nbUSB");
			long nbNet = (long) header.get("nbNet");

			//Mise à jour du header
			nbLog++;
			alerte.replace("nbLog", nbLog);

			//On récupère le type de l'alerte
			String type = (String) alerte.get("type");

			//On recherche la règle coorespondante au type, on récupère son ID, et on remplace le type texte par l'id de la règle dans l'alerte
			Regle reg = daoRegle.trouverSt(type);
			String idRegle = String.valueOf(reg.getIdRegle());
			alerte.replace("type", idRegle);
			//On modifie le nombre de règle critiques si nécessaire
			int niveauRegle = reg.getNiveauRegle();
			if (niveauRegle == 1)
			{
				nbCrit++;
				header.replace("nbCrit", nbCrit);
			}
			//En fonction du type, on incrémente le compteur de règle correspondante
			switch (type) {
			case "connexion_usb": case "deconnexion_usb": 
				//On modifie le nombre d'alerte de type USB
				nbUSB++;
				header.replace("nbUSB", nbUSB);
				break;
			case "creation_fichier": case "modification_fichier": case "suppresion_fichier":
				//On modifie le nombre d'alerte de type Fichier
				nbFichier++;
				header.replace("nbFichier", nbFichier);
				break;
			case "network":
				//On modifie le nombre d'alerte de type Net
				nbNet++;
				header.replace("nbNet", nbNet);
				break;
			case "touche_appuyee":
				//On modifie le nombre d'alerte de type Clavier
				nbClavier++;
				header.replace("nbClavier", nbClavier);
				break;
			}			


			System.out.println("DEBUG: AJOUT NOUVELLES INFORMATIONS DANS LE LOG");
			//Mise à jour du log
			body.add(alerte);
			jsonLog.put("header", header);
			jsonLog.put("body", body);


			FileWriter file = new FileWriter(chemin);
			try {
				file.write(jsonLog.toJSONString());

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				file.flush();
				file.close();
				lecture.close();
			}
		} catch(Exception e) {
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
