package com.surveillance.tp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;

/**
 * Servlet implementation class LoginRegister
 */
@WebServlet("/LoginRegister")
public class LoginRegister extends HttpServlet {

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
		request.getRequestDispatcher("/WEB-INF/authentification.jsp").forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("POST LOGIN");
		String userMail=request.getParameter("login-mail");
		String password=request.getParameter("login-password");

		System.out.println(userMail);
		System.out.println(password);

		Utilisateur utilCo = daoUtilisateur.trouverMdp(userMail, password);

		if (utilCo == null)
		{
			request.setAttribute("erreur", "Utilisateur non trouvé! Réessayez.");
			request.getRequestDispatcher("/WEB-INF/authentification.jsp").forward(request, response);
		}
		else
		{
			HttpSession session = request.getSession();
			session.setAttribute("nomUtilisateur", utilCo.getNom());
			session.setAttribute("prenomUtilisateur", utilCo.getPrenom());
			session.setAttribute("groupeUtilisateur", utilCo.getGroupe());
			session.setAttribute("id_user",utilCo.getId() );

			if (utilCo.getGroupe().equals("eleve"))
				response.sendRedirect("/ServeurJEE/monCompte");
			else
				response.sendRedirect("/ServeurJEE/listeUtilisateurs");
		}
	}
}
