package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("/arretExamen")
public class ArretExamen extends HttpServlet {
	public static final String CONF_DAO_FACTORY="daofactory";
	private DAOExamen daoExamen;

	public void init() throws ServletException {
		// Récupération d'une instance de notre DAO Examen
		daoExamen=((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getExamenDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();

		// Aucun utilisateur connecté
		if(session.getAttribute("id_user")==null) response.sendRedirect(request.getContextPath()+"/logout");
		else if(session.getAttribute("groupeUtilisateur").equals("professeur")) {
			Integer idProf=(Integer)session.getAttribute("id_user");
			Examen examEnCours=daoExamen.trouverExamenUtil(idProf);	
			if(examEnCours!=null) daoExamen.updateExamenStop(idProf);
			response.sendRedirect("/ServeurJEE/listeUtilisateurs");
		}
		else response.sendRedirect(request.getContextPath()+"/monCompte"); // L'utilisateur est un élève, donc pas le droit d'accès
	}
}