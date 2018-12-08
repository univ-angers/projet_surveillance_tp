package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet affichant l'écran d'acceuil du site
 */
public class home extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		//Aucun utilisateur connecté
		if (session.getAttribute("id_user") == null)
			request.setAttribute("affiche_auth", "oui");
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/home.jsp" ).forward( request, response );        
	}
}