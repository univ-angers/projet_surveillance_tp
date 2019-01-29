package com.surveillance.tp.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;

/**
 * Servlet affichant la page de connexion
 */
@WebServlet("/login")
public class LoginRegister extends HttpServlet {
	public static final String CONF_DAO_FACTORY="daofactory";
	private DAOUtilisateur daoUtilisateur;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		daoUtilisateur=((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		//Aucun utilisateur connecté
		if(session.getAttribute("id_user")==null) request.getRequestDispatcher("/WEB-INF/authentification.jsp").forward(request, response);
		else {
			if(session.getAttribute("groupeUtilisateur").equals("professeur")) response.sendRedirect(request.getContextPath()+"/listeUtilisateurs");
			else response.sendRedirect(request.getContextPath()+"/monCompte");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userMail=request.getParameter("login-mail");
		String password=request.getParameter("login-password");
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(password.getBytes());
	    byte[] digest = md.digest();
	    password = DatatypeConverter.printHexBinary(digest).toLowerCase();
		Utilisateur utilCo=daoUtilisateur.trouverMdp(userMail, password);

		if(utilCo==null) {
			request.setAttribute("erreur", "Utilisateur non trouvé ! Réessayez !");
			request.getRequestDispatcher("/WEB-INF/authentification.jsp").forward(request, response);
		}
		else {
			HttpSession session=request.getSession();
			session.setAttribute("nomUtilisateur", utilCo.getNom());
			session.setAttribute("prenomUtilisateur", utilCo.getPrenom());
			session.setAttribute("groupeUtilisateur", utilCo.getGroupe());
			session.setAttribute("id_user", utilCo.getId());

			if(utilCo.getGroupe().equals("professeur")) response.sendRedirect(request.getContextPath()+"/listeUtilisateurs");
			else response.sendRedirect(request.getContextPath()+"/monCompte");
		}
	}
}