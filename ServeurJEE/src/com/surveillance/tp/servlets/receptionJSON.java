package com.surveillance.tp.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAORegle;
import com.surveillance.tp.dao.DAORegleExamen;
import com.surveillance.tp.dao.DAOUtilisateur;
import com.surveillance.tp.utilitaire.directoryManager;
import com.surveillance.tp.utilitaire.examTimer;

/**
 * Classe communiquant avec les clients. Elle récupère les informations, les traites
 * et répond aux différents clients
 */
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			BufferedReader br = request.getReader();
			String str = null;
			String resultat = "";

			while ((str = br.readLine()) != null) {
				resultat = resultat + str;
			}

			//Conversion de la chaine en JSON
			JSONParser parser = new JSONParser();
			JSONObject jObj = (JSONObject) parser.parse(resultat);

			//Recupération du type de données
			String type = (String) jObj.get("type");

			if ( type.equals("connexion_etudiant"))
				connexionEtudiant(jObj,response);
			else if ( type.equals("get_time_exam"))
				retourTimer(jObj, response);
			else
			{				
				ajoutLog(jObj);
			}
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
		String mail = (String) obj.get("mailEtudiant");
		String motDePasse = (String) obj.get("mdp");
		String idExam = (String) obj.get("IDexamen");
		int idEx = Integer.parseInt(idExam);

		Utilisateur util = 	daoUtilisateur.trouverMdp(mail, motDePasse);
		Examen exam = daoExamen.trouverExamenIDEnCours(idEx);

		//Si l'utilisateur existe et que son mot de passe est correct, ainsi qu'un examen valide
		if (util != null && exam != null )
		{
			int idEtud = util.getId();

			String chemin = directoryManager.idDbToString(idEx);

			//Exemple chemin: /opt/data_dir/0/0/0/0/0/0/0/1/4/7/247/idEtud.lg
			chemin = chemin + "/" + idEtud + "/";

			//Créer le dossier de l'étudiant
			File dossierEt = new File(chemin);
			dossierEt.mkdir();

			//Créer le log de l'étudiant et son header
			String cheminLog = chemin + util.getId() + ".lg";
			File fichierLog = new File(cheminLog);
			//Dans le cas ou le fichier existe déjà (un étudiant qui quitte l'application et revient), on ne recréé pas de header
			if (!fichierLog.exists())
			{
				try {
					fichierLog.createNewFile();

					JSONObject objInit = new JSONObject();

					JSONObject header = new JSONObject();
					header.put("nom", util.getNom());
					header.put("prenom", util.getPrenom());
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
				} catch (IOException e) {
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

			JSONArray jsLienSurv = new JSONArray();	//Contient les liens à surveiller

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

				//Dans le cas du watcher net, il y a les sites à mettre en attributs
				if (r.getIdRegle() == 7)
				{					
					//On enlève les [] du tableau qui empechent de parser
					String sites = re.getAttributs().substring(1, re.getAttributs().length()-1);
					//On split chaque site dans un tableau
					String[] resSites = sites.split(",");


					//Conversion de la chaine en JSON
					JSONParser parser = new JSONParser();
					for (String s : resSites)
					{
						JSONObject siteObj;
						try {
							siteObj = (JSONObject) parser.parse(s);
							jsLienSurv.add(siteObj);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
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
			jsObj.put("site_surveillance", jsLienSurv);
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
			String retour = "";
			if (exam.getHeureDebut() != null)
			{
				long tempsRestant = examTimer.tempsRestant(exam);

				String temps = "";
				if (tempsRestant > 0)
				{
					Date fin = new Date(tempsRestant);
					DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
					formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
					temps = formatter.format(fin);
				}
				else
					temps = "Examen terminé";

				JSONObject jsObj = new JSONObject();
				jsObj.put("type", "rep_temps");
				jsObj.put("code", 200);
				jsObj.put("tps", temps);
				retour = jsObj.toJSONString();
			}
			else
			{
				JSONObject jsObj = new JSONObject();
				jsObj.put("type", "rep_temps");
				jsObj.put("code", 200);
				jsObj.put("tps", "Examen non démarré");
				retour = jsObj.toJSONString();
			}
			envoiInfoClient(retour, rep);			
		}
	}

	/**
	 * Ajoute les informations de l'alerte dans le fichier log de l'étudiant
	 * @param obj Alerte reçue
	 */
	public void ajoutLog(JSONObject alerte)
	{
		String idExamen = (String) alerte.get("IDexamen");	
		int idEx = Integer.valueOf(idExamen);
		Examen examEnCours = daoExamen.trouverExamenIDEnCours(idEx);

		//Si l'examen auquel on veut ajouter nos logs a bien commencé
		if (examEnCours.getHeureDebut() != null)
		{
			//Et qu'il n'est pas terminé
			if (!examTimer.examenTermine(examEnCours))
			{
				//Si on est ici, on a déjà un dossier + fichier log créé pour l'examen
				String mail = (String) alerte.get("mailEtudiant");
				Utilisateur util = 	daoUtilisateur.trouver(mail);
				int idEtud = util.getId();

				String chemin = directoryManager.idDbToString(Integer.parseInt(idExamen));

				//Exemple chemin: /opt/data_dir/0/0/0/0/0/0/0/1/4/7/247/idEtud.lg
				chemin = chemin + "/" + idEtud + "/" + util.getId() + ".lg";

				miseAJourLog(examEnCours, chemin, alerte);
			}
		}
	}

	/**
	 * Met à jour le header du fichier log et ajoute l'alerte reçue
	 * @param chemin
	 * @param alerte
	 */
	void miseAJourLog(Examen exam, String chemin, JSONObject alerte)
	{
		//Modification du header
		JSONParser parser = new JSONParser();
		try {
			if (exam != null)
			{

				FileReader lecture = new FileReader(chemin);

				Object logExistant = parser.parse(lecture);
				JSONObject jsonLog = (JSONObject) logExistant;
				JSONObject header = (JSONObject) jsonLog .get("header");
				JSONArray body = (JSONArray) jsonLog.get("body");

				//Récupération des informations header existantes
				long nbLog = (long) header.get("nbLog");
				long nbCrit = (long) header.get("nbCrit");
				long nbClavier = (long) header.get("nbClavier");
				long nbFichier = (long) header.get("nbFichier");
				long nbUSB = (long) header.get("nbUSB");
				long nbNet = (long) header.get("nbNet");


				//On récupère le type de l'alerte
				String type = (String) alerte.get("type");

				//On recherche la règle coorespondante au type, on récupère son ID, et on remplace le type texte par l'id de la règle dans l'alerte
				Regle reg = daoRegle.trouverSt(type);
				String idRegle = String.valueOf(reg.getIdRegle());
				//Mise a jour de l'alerte qui ira dans le body
				alerte.put("type", idRegle);

				//Mise à jour du header
				nbLog++;
				header.put("nbLog", nbLog);
				//On modifie le nombre de règle critiques si nécessaire
				int niveauRegle = reg.getNiveauRegle();
				if (niveauRegle == 1)
				{
					//Ne pas envoyer l'alerte de l'étudiant qui quitte l'examen si le temps est écoulé
					if (reg.getIdRegle()==9 && examTimer.tempsRestant(exam) < 0)
					{/*On ajoute rien */}
					//Pour tous les autres cas, on ajoute l'alerte
					else
					{}
					nbCrit++;
					header.put("nbCrit", nbCrit);

				}
				//En fonction du type, on incrémente le compteur de règle correspondante
				switch (type) {
				case "connexion_usb": case "deconnexion_usb": 
					//On modifie le nombre d'alerte de type USB
					nbUSB++;
					header.put("nbUSB", nbUSB);
					break;
				case "creation_fichier": case "modification_fichier": case "suppresion_fichier":
					//On modifie le nombre d'alerte de type Fichier
					nbFichier++;
					header.put("nbFichier", nbFichier);
					break;
				case "network":
					//On modifie le nombre d'alerte de type Net
					nbNet++;
					header.put("nbNet", nbNet);
					break;
				case "touche_appuyee":
					//On modifie le nombre d'alerte de type Clavier
					nbClavier++;
					header.put("nbClavier", nbClavier);
					break;
				}			

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
			e.printStackTrace();
		}
	}
}
