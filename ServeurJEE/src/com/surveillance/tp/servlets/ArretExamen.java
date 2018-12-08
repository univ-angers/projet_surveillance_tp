package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;

/**
 * Servlet passant un examen en cours à arrêté
 */
public class ArretExamen extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	private DAOExamen daoExamen;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Examen */
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		//Aucun utilisateur connecté
		if (session.getAttribute("id_user") == null)
			response.sendRedirect("/ServeurJEE/LoginRegister");

		//L'utilisateur est un élève, donc pas le droit d'accès
		else if (session.getAttribute("groupeUtilisateur").equals("eleve"))
			response.sendRedirect("/ServeurJEE/monCompte");

		else 
		{
			Integer idProf = (Integer)session.getAttribute("id_user");

			Examen examEnCours = daoExamen.trouverExamenUtil(idProf);	
			if (examEnCours != null)
				daoExamen.updateExamenStop(idProf);

			response.sendRedirect("/ServeurJEE/listeUtilisateurs");
		}
	}
}
