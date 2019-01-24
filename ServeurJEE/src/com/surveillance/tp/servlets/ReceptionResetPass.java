package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;

/**
 * Servlet affichant l'écran de reset de mot de passe
 */
@WebServlet("/receptionReset")
public class ReceptionResetPass extends HttpServlet {
	public static final String CONF_DAO_FACTORY="daofactory";
	private DAOUtilisateur daoUtilisateur;

	public void init() throws ServletException {		
		// Récupération d'une instance de notre DAO Utilisateur
		daoUtilisateur=((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recupérer token du lien
		String token=request.getQueryString();
		if (token.length()==30) {
			Utilisateur util=daoUtilisateur.trouverCleReset(token);

			// Un utilisateur correspond à cette clé, on lui permet donc de reset son mot de passe
			if(util!=null) {
				request.setAttribute("id_util", util.getId());
				request.getRequestDispatcher("/WEB-INF/recupReset.jsp").forward(request, response);	
			}
			else response.sendRedirect(request.getContextPath());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nouvMdp=(String)request.getParameter("nouv_mdp");
		String verifMdp=(String)request.getParameter("mdp_conf");
		String idUtilSt=(String)request.getParameter("id_util");
		int idUtil=Integer.valueOf(idUtilSt);
		Utilisateur util=daoUtilisateur.trouverID(idUtil);

		// Si l'utilisateur existe bien
		if(util!=null) {
			// Les deux mots deux passe indiqués sont identiques
			if(nouvMdp.equals(verifMdp)) {
				util.setPassword(nouvMdp);
				// On lui change son mot de passe
				daoUtilisateur.miseAJour(util);
				// Et son token de réinitialisation est passé à null
				daoUtilisateur.miseAJourReset(util, null);
				response.sendRedirect(request.getContextPath()+"/login");
			}
			else { // Les deux mots de passe indiqués sont différents
				request.setAttribute("id_util", util.getId());
				request.setAttribute("mdp_egaux", "non");
				request.getRequestDispatcher("/WEB-INF/recupReset.jsp").forward(request, response);	
			}
		}
		else {
			request.setAttribute("erreur", "oui");
			request.getRequestDispatcher("/WEB-INF/recupReset.jsp").forward(request, response);	
		}

	}
}