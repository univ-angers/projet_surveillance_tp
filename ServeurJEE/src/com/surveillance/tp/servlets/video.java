package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class video extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	//private DAOUtilisateur daoUtilisateur;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		//this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		//Aucun utilisateur connecté
		if (session.getAttribute("nomUtilisateur") == null)
		{
			System.out.println("DEBUG: Nom util = " + session.getAttribute("nomUtilisateur"));
			response.sendRedirect("/ServeurJEE/LoginRegister");
		}
		//L'utilisateur est un élève, donc pas le droit d'accès
		else if (session.getAttribute("groupeUtilisateur").equals("eleve"))
			response.sendRedirect("/ServeurJEE/monCompte");
		else
			this.getServletContext().getRequestDispatcher( "/WEB-INF/Viedo.jsp" ).forward( request, response );        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//this.getServletContext().getRequestDispatcher("/WEB-INF/MonCompte.jsp").forward(request, response);
	}
}
