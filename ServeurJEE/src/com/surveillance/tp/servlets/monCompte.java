package com.surveillance.tp.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.beans.Utilisateur;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;


public class monCompte extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	private DAOExamen daoExamen;
	private DAOUtilisateur daoUtilisateur;

	public void init() throws ServletException {
		/* Récupération d'une instance de nos DAO */
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
		this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		//Aucun utilisateur connecté
		if (session.getAttribute("id_user") == null)
			response.sendRedirect("/ServeurJEE/LoginRegister");

		//Si l'utilisateur est un élève, donc pas les droits
		else if (session.getAttribute("groupeUtilisateur").equals("eleve"))
		{
			request.setAttribute("eleve", "oui");
			this.getServletContext().getRequestDispatcher( "/WEB-INF/MonCompte.jsp" ).forward( request, response ); 
		}

		else
		{
			int idProf = (int) session.getAttribute("id_user");

			//Pour savoir quel onglet afficher
			Examen examEnCours = daoExamen.trouverExamenUtil(idProf);

			if (examEnCours != null)
				request.setAttribute("afficheParam", 1);

			/* Afichage */
			this.getServletContext().getRequestDispatcher( "/WEB-INF/MonCompte.jsp" ).forward( request, response );        
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		int idUtil = (int) session.getAttribute("id_user");
		Utilisateur util = daoUtilisateur.trouverID(idUtil);
		//Les infos à sauvegarder sont le nom et le prénom
		if (request.getParameter("saveInfos") != null)
		{
			String nomEt = request.getParameter("nom");
			String prenEt = request.getParameter("prenom");

			util.setNom(nomEt);
			util.setPrenom(prenEt);

			daoUtilisateur.miseAJour(util);
			
			request.setAttribute("info_change", "oui");
		}
		//L'info à sauvegarder est le mot de passe
		else if (request.getParameter("saveMdp") != null)
		{
			String ancienMDP = request.getParameter("motPass");
			String nouvMDP = request.getParameter("nouvPass");
			String verifMDP = request.getParameter("confPass");

			if (nouvMDP.equals(verifMDP))
			{
				MessageDigest md;
				try {
					md = MessageDigest.getInstance("MD5");
					byte[] hashInBytes = md.digest(ancienMDP.getBytes(StandardCharsets.UTF_8));

					StringBuilder sb = new StringBuilder();
					for (byte b : hashInBytes) {
						sb.append(String.format("%02x", b));
					}
					String mdpHash = sb.toString();

					System.out.println("Ancien MDP = " + ancienMDP);
					System.out.println("Ancien MDP hash = " + mdpHash);
					System.out.println("MDP dans base = " + util.getPassword());

					if (mdpHash.equals(util.getPassword()))
					{
						util.setPassword(nouvMDP);
						daoUtilisateur.miseAJour(util);

						request.setAttribute("mdp_change", "oui");
					}
					else
						request.setAttribute("mdp_fail", "oui");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
			else
				request.setAttribute("mdp_fail", "oui");
		}

		this.getServletContext().getRequestDispatcher( "/WEB-INF/MonCompte.jsp" ).forward( request, response );
	}
}
