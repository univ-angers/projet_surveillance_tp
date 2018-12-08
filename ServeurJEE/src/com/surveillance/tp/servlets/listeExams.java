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
import com.surveillance.tp.utilitaire.examTimer;

/**
 * Servlet affichant la liste des examens créés par l'utilisateur
 */
public class listeExams extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
		//Un eleve est connecté, pas les droits d'accès
		else if (session.getAttribute("groupeUtilisateur").equals("eleve"))
			response.sendRedirect("/ServeurJEE/monCompte");

		else
		{
			int idProf = (int) session.getAttribute("id_user");
			Examen examEnCours = daoExamen.trouverExamenUtil(idProf);

			//Verification que le temps d'examen n'est pas terminé
			if (examEnCours != null) {
				if (examTimer.examenTermine(examEnCours))
					response.sendRedirect("/ServeurJEE/arretExamen");
			}

			//Permet de savoir quels examens ont été fait par ce prof
			request.setAttribute("Archive", daoExamen.recupererExams(idProf));

			//Permet de savoir quel onglet afficher
			if (examEnCours != null)
				request.setAttribute("afficheParam", 1);

			this.getServletContext().getRequestDispatcher( "/WEB-INF/Historique.jsp" ).forward( request, response );
		}
	}
}