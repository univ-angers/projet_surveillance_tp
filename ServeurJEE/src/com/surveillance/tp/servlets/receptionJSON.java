package com.surveillance.tp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.surveillance.tp.beans.reponseTest;

public class receptionJSON extends HttpServlet {
	
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

		System.out.println("DEBUG: Faire ce qu'il faut");
		//Verifier la présence de l'utilisateur dans la BDD avec son numero étudiant et son identifiant
		//Si c'est le cas, envoyer la réponse au client qu'il peut démarrer les watchers
	}
	
	/**
	 * Ajoute les informations de l'alerte adns le fichier log de l'étudiant
	 * @param obj
	 */
	public void ajoutLog(JSONObject obj)
	{
		System.out.println("DEBUG: Ajout dans le fichier log ");
		String informationLog = obj.toString();
		System.out.println("DEBUG: Log à ajouter dans fichier = " + informationLog);
	}
}
