package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;
import com.surveillance.tp.utilitaire.mailUtil;

/**
 * Servlet permettant d'indiquer un mail pour récupérer un mot de passe perdu
 */
public class recupPass extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	private DAOUtilisateur daoUtilisateur;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher( "/WEB-INF/RecupPass.jsp" ).forward( request, response );        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String destination = (String) request.getParameter("reminder-email");
		Utilisateur util = daoUtilisateur.trouver(destination);
		
		//On envoie un mail que si le mail correspond à un utilisateur
		if (util != null)
		{
			String token = mailUtil.creationChaine30();
			daoUtilisateur.miseAJourReset(util,token);
			
			String nom = util.getNom();
			String prenom = util.getPrenom();
			mailUtil.envoyerReset(destination,token,nom,prenom);
			request.setAttribute("util_trouve", "oui");
		}
		else
		{
			request.setAttribute("util_non_trouve", "oui");
		}	
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/RecupPass.jsp").forward(request, response);
	}
}