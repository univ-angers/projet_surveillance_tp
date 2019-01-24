package com.surveillance.tp.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;

/**
 * Servlet permettant à l'utilisateur de modifier ses informations
 */
@WebServlet("/monCompte")
public class monCompte extends HttpServlet {
	public static final String CONF_DAO_FACTORY="daofactory";
	private DAOExamen daoExamen;
	private DAOUtilisateur daoUtilisateur;

	public void init() throws ServletException {
		/* Récupération d'une instance de nos DAO */
		daoExamen=((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getExamenDao();
		daoUtilisateur=((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		// Aucun utilisateur connecté
		if(session.getAttribute("id_user")==null) response.sendRedirect(request.getContextPath()+"/logout");
		else { // Pour savoir quel onglet afficher
			Utilisateur user=daoUtilisateur.trouverID((int)session.getAttribute("id_user"));
			request.setAttribute("utilisateur", user);
			if(session.getAttribute("groupeUtilisateur").equals("professeur")) {
				if(daoExamen.trouverExamenUtil((int)session.getAttribute("id_user"))!=null) request.setAttribute("afficheParam", 1);
			}
			else request.setAttribute("eleve", "oui"); // Si l'utilisateur est un élève, donc pas les droits
			getServletContext().getRequestDispatcher("/WEB-INF/MonCompte.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		Utilisateur util=daoUtilisateur.trouverID((int)session.getAttribute("id_user"));
		//Les infos à sauvegarder sont le nom et le prénom
		if(request.getParameter("saveInfos")!=null) {
			String nomEt=request.getParameter("nom");
			String prenEt=request.getParameter("prenom");
			if(nomEt.compareTo("")!=0) util.setNom(nomEt);
			if(prenEt.compareTo("")!=0) util.setPrenom(prenEt);
			daoUtilisateur.miseAJour(util);
			if(nomEt.compareTo("")!=0 || prenEt.compareTo("")!=0) request.setAttribute("info_change", "oui");
		}
		// L'info à sauvegarder est le mot de passe
		else if(request.getParameter("saveMdp")!=null) {
			String ancienMDP=request.getParameter("motPass");
			String nouvMDP=request.getParameter("nouvPass");
			String verifMDP=request.getParameter("confPass");

			if(nouvMDP.compareTo("")!=0 && nouvMDP.equals(verifMDP)) {
				MessageDigest md;
				try {
					md=MessageDigest.getInstance("MD5");
					byte[] hashInBytes=md.digest(ancienMDP.getBytes(StandardCharsets.UTF_8));
					StringBuilder sb=new StringBuilder();
					for(byte b: hashInBytes) sb.append(String.format("%02x", b));
					String mdpHash=sb.toString();

					if(mdpHash.equals(util.getPassword())) {
						util.setPassword(nouvMDP);
						daoUtilisateur.miseAJour(util);
						request.setAttribute("mdp_change", "oui");
					}
					else request.setAttribute("mdp_fail", "oui");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
			else request.setAttribute("mdp_fail", "oui");
		}

		if(session.getAttribute("groupeUtilisateur").equals("eleve")) request.setAttribute("eleve", "oui");
		getServletContext().getRequestDispatcher("/WEB-INF/MonCompte.jsp").forward(request, response);
	}
}