package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;

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
        this.getServletContext().getRequestDispatcher( "/WEB-INF/ParametresExamen.jsp" ).forward( request, response );
	}
	
	

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		System.out.println("DEBUG: Reception des infos en POST pour examen");
		
		/* Creation d'un utilisateur si les conditions sont remplies */
		Examen nouvExam = ajouterExamen(request);
		
		//A partir d'ici, nouvExam est l'examen ajouté
		creerDossierExamen(nouvExam);

		/* Stockage du bean dans la request */
		request.setAttribute("Examen", nouvExam);
		
		/* Affichage de la vue qu'on veut */
		//this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

	public Examen ajouterExamen(HttpServletRequest request)
	{
		//TODO
		return null;
	
	}
	
	public void creerDossierExamen(Examen exam)
	{
		//TODO
	}
}
