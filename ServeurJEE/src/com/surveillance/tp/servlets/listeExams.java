package com.surveillance.tp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;

/**
 * Servlet implementation class ListeUtilisateurs
 */
public class listeExams extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private DAOExamen daoexamen;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoexamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		//Aucun utilisateur connecté
		if (session.getAttribute("nomUtilisateur") == null)
		{
			System.out.println("DEBUG: Nom util = " + session.getAttribute("nomUtilisateur"));
			response.sendRedirect("/ServeurJEE/LoginRegister");
		}
		else if (session.getAttribute("groupeUtilisateur").equals("eleve"))
			response.sendRedirect("/ServeurJEE/monCompte");
		else
		{
			Integer id=(Integer)session.getAttribute("id_user");
			request.setAttribute("Archive", daoexamen.recupererExams(id));

			this.getServletContext().getRequestDispatcher( "/WEB-INF/Historique.jsp" ).forward( request, response );
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		//request.setAttribute("utilisateurs", daoUtilisateur.recupererUtilisateurs());

		this.getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}

}