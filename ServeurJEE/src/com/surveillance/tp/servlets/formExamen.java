package com.surveillance.tp.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.utilitaire.directoryManager;

public class formExamen extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";

	private DAOExamen daoExamen;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		/* Transmission vers la page en charge de l'affichage des résultats */
		this.getServletContext().getRequestDispatcher( "/WEB-INF/CreerExamen.jsp" ).forward( request, response );
	}


	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		System.out.println("DEBUG: Reception des infos en POST pour examen");

		/* Creation d'un utilisateur si les conditions sont remplies */
		Examen nouvExam = ajouterExamen(request);

		//A partir d'ici, nouvExam est l'examen ajouté
		creerDossierExamen(nouvExam);
		
		/*
		 * 
		 * AJOUTER LA RECUPERATION DES REGLES
		 * 
		 */

		/* Stockage du bean dans la request */
		request.setAttribute("Examen", nouvExam);

		/* Affichage de la vue qu'on veut */
		this.getServletContext().getRequestDispatcher( "/WEB-INF/home.jsp" ).forward( request, response );
	}

	/**
	 * Ajoute un examen dans la BDD
	 * @param request
	 * @return
	 */
	public Examen ajouterExamen(HttpServletRequest request)
	{
		/* Récupération des données du formulaire */
		String dureeH = request.getParameter("duree-heure");
		String dureeM = request.getParameter("duree-minute");
		String timeSt = dureeH + ":" + dureeM + ":00";
		System.out.println("DEBUG TIME: " + timeSt);
		Time time = null;
		Time.valueOf(timeSt);		//DUREE A CORRIGER, NULL DANS LA BASE
		String matiere = request.getParameter("matiere");
		//String ListeExamens = request.getParameter("white-list");

		/* Création d'un examen */
		Examen examen = new Examen();
		
		examen.setIdProf(1);	//COMMENT RECUPERER L'ID DU PROF
		examen.setMatiere(matiere);
		examen.setDuree(time);
		
		daoExamen.creer(examen);
		System.out.println("DEBUG: Examen ajouté");
		return examen;
	}

	/**
	 * Créer le chemin de l'examen et y ajoute le fichier log
	 * @param exam
	 */
	public void creerDossierExamen(Examen exam)
	{
		File examDir;
		String pathDir = directoryManager.idDbToString(exam.getIdExam());

		System.out.println("DEBUG: CHEMIN SERVLET: " + pathDir);

		examDir = new File(pathDir);
		examDir.mkdirs();
	}    

	
}
