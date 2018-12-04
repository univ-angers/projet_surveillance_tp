package com.surveillance.tp.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.surveillance.tp.beans.EtudiantExamen;
import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;
import com.surveillance.tp.utilitaire.directoryManager;
import com.surveillance.tp.utilitaire.examTimer;

/**
 * Servlet implementation class ListeUtilisateurs
 */
public class listeUtilisateurs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	private DAOUtilisateur daoUtilisateur;
	private DAOExamen daoExamen;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public void init() throws ServletException {
		/* Récupération d'une instance de nos DAO */
		this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		//Aucun utilisateur connecté
		if (session.getAttribute("id_user") == null)
		{
			response.sendRedirect("/ServeurJEE/LoginRegister");
		}
		else if (session.getAttribute("groupeUtilisateur").equals("eleve"))
		{
			response.sendRedirect("/ServeurJEE/monCompte");
		}
		else
		{
			String param = request.getQueryString();
			int idProf = (int) session.getAttribute("id_user");
			Examen examEnCours = daoExamen.trouverExamenUtil(idProf);

			//Si on recherche un examen archivé
			if (param != null)
			{
				//Paramètre de la forme id_examen=X
				String[] res = param.split("=");
				int idExamArchive = Integer.valueOf(res[1]);
				
				ArrayList<EtudiantExamen> listeUtilisateurExamenCourant = new ArrayList<>();
				String cheminExam = directoryManager.idDbToString(idExamArchive);
				listeUtilisateurExamenCourant = directoryManager.recupererHeaderEtudiants(cheminExam);

				//Permet de savoir quel onglet afficher
				if (examEnCours != null)
					request.setAttribute("afficheParam", 1);				

				request.setAttribute("utilisateurs", listeUtilisateurExamenCourant);
				request.setAttribute("id_examen", idExamArchive);

				this.getServletContext().getRequestDispatcher( "/WEB-INF/listeUtilisateurs.jsp" ).forward( request, response );
			}
			//Sinon on recherche l'examen actuel
			else
			{				
				//Verification que le temps d'examen n'est pas terminé
				if (examEnCours != null) {
					if (examTimer.examenTermine(examEnCours))
					{
						System.out.println("pouet");
						response.sendRedirect("/ServeurJEE/arretExamen");
					}
					else
					{
						ArrayList<EtudiantExamen> listeUtilisateurExamenCourant = new ArrayList<>();

						String cheminExam = directoryManager.idDbToString(examEnCours.getIdExam());
						listeUtilisateurExamenCourant = directoryManager.recupererHeaderEtudiants(cheminExam);

						//Permet de savoir quel onglet afficher
						request.setAttribute("afficheParam", 1);				

						request.setAttribute("utilisateurs", listeUtilisateurExamenCourant);				

						this.getServletContext().getRequestDispatcher( "/WEB-INF/listeUtilisateurs.jsp" ).forward( request, response );
					}
				}
				else
				{
					ArrayList<EtudiantExamen> listeUtilisateurExamenCourant = new ArrayList<>();

					request.setAttribute("utilisateurs", listeUtilisateurExamenCourant);				

					this.getServletContext().getRequestDispatcher( "/WEB-INF/listeUtilisateurs.jsp" ).forward( request, response );
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher("/WEB-INF/listeUtilisateurs.jsp").forward(request, response);
	}


}