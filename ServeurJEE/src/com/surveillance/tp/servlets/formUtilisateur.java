package com.surveillance.tp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;

/**
 * Servlet affichant le formulaire de création de compte utilisateur
 */
public class formUtilisateur extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";

	private DAOUtilisateur daoUtilisateur;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		HttpSession session = request.getSession();
		//Aucun utilisateur connecté
		if (session.getAttribute("id_user") == null)
		{
			/* Transmission vers la page en charge de l'affichage des résultats */
			this.getServletContext().getRequestDispatcher( "/WEB-INF/Inscription.jsp" ).forward( request, response );
		}
		else
			response.sendRedirect("/ServeurJEE/listeUtilisateurs");
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		/* Creation d'un utilisateur si les conditions sont remplies */
		ajouterUtilisateur(request);

		/* Affichage de la vue qu'on veut */
		response.sendRedirect("/ServeurJEE/LoginRegister");
	}

	public Utilisateur ajouterUtilisateur(HttpServletRequest request)
	{
		/* Récupération des données du formulaire */
		String nom = request.getParameter("register-username");
		String prenom = request.getParameter("register-userfirstname");
		String mail = request.getParameter("register-email");
		String password = request.getParameter("register-password");
		/* Création d'un utilisateur et verifications (basiques pour le moment)*/
		Utilisateur util = new Utilisateur();
		boolean donneesValides = traiterDonnees(nom,prenom,mail);
		if (donneesValides)
		{
			String mdpTraite = traiterMotDePasse(password);
			if (mdpTraite.length() != 0)
			{
				util.setMail(mail);
				util.setNom(nom);
				util.setPrenom(prenom);
				util.setPassword(password);

				/* Données valides donc ajout dans la BDD grâce à notre DAO */
				daoUtilisateur.creer(util);
			}
		}

		return util;
	}

	public boolean traiterDonnees(String nom, String prenom, String mail)
	{
		if (!nom.isEmpty() && !prenom.isEmpty() && !mail.isEmpty())
			return true;
		else
			return false;
	}

	public String traiterMotDePasse(String mdp)
	{
		if (mdp.length()>=5)
		{
			//Traiter le mot de passe (chiffrer)
			return mdp;
		}
		else
			return "";
	}
}
