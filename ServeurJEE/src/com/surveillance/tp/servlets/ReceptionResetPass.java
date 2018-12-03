package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;


public class ReceptionResetPass extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";

	private DAOUtilisateur daoUtilisateur;

	public void init() throws ServletException {		
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Coucou");
		//Recupérer ID 
		String token = request.getQueryString();
		
		Utilisateur util = daoUtilisateur.trouverCleReset(token);
		
		if (util != null)
		{
			request.setAttribute("id_util", util.getId());
			request.getRequestDispatcher("/WEB-INF/recupReset.jsp").forward(request, response);	
		}
		else
			request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BLBLBL");
		String nouvMdp = (String) request.getParameter("nouv_mdp");
		String verifMdp = (String) request.getParameter("mdp_conf");

		String idUtilSt = (String) request.getParameter("id_user");
		int idUtil = Integer.valueOf(idUtilSt);
		
		Utilisateur util = daoUtilisateur.trouverID(idUtil);
		if (util != null)
		{
			if (nouvMdp.equals(verifMdp))
			{
				util.setPassword(nouvMdp);
				daoUtilisateur.miseAJour(util);
				daoUtilisateur.miseAJourReset(util, null);
				
				response.sendRedirect("/ServeurJEE/home");
			}
			else
			{
				request.setAttribute("id_util", util.getId());
				request.setAttribute("mdp_egaux", "non");
				request.getRequestDispatcher("/WEB-INF/recupReset.jsp").forward(request, response);	
			}
		}
		else
		{
			request.setAttribute("erreur", "oui");
			request.getRequestDispatcher("/WEB-INF/recupReset.jsp").forward(request, response);	
		}
		
	}
}