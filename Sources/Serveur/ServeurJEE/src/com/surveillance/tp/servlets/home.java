package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet affichant l'écran d'acceuil du site
 */
@WebServlet("")
public class home extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();

		//Aucun utilisateur connecté
		if(session.getAttribute("id_user")==null) getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
		else {
			if(session.getAttribute("groupeUtilisateur").equals("professeur")) response.sendRedirect(request.getContextPath()+"/listeUtilisateurs");
			else response.sendRedirect(request.getContextPath()+"/monCompte");
		}
	}
}