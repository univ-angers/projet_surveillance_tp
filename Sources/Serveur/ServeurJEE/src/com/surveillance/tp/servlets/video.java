package com.surveillance.tp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.utilitaire.ConversionVideo;
import com.surveillance.tp.utilitaire.directoryManager;

/**
 * Servlet affichant la vidéo de l'utilisateur pour un examen précis
 */
public class video extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	private DAOExamen daoExamen;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Examen */
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		//Aucun utilisateur connecté
		if (session.getAttribute("nomUtilisateur") == null)
		{
			System.out.println("DEBUG: Nom util = " + session.getAttribute("nomUtilisateur"));
			response.sendRedirect("/ServeurJEE/LoginRegister");
		}
		//L'utilisateur est un élève, donc pas le droit d'accès
		else if (session.getAttribute("groupeUtilisateur").equals("eleve"))
			response.sendRedirect("/ServeurJEE/monCompte");
		else
		{
			String idExamSt = request.getParameter("id_examen");
			String idEtudSt = request.getParameter("id_etud");

			int idExamAChercher;

			//Si pas d'examen en paramètre on accède à notre examen courant
			if (idExamSt == null)
			{
				int idEtud=Integer.parseInt(idEtudSt);
				Examen examen;
				examen=daoExamen.trouverExamenUtil((int)session.getAttribute("id_user"));
				idExamAChercher = examen.getIdExam();
			}
			else
				idExamAChercher = Integer.parseInt(idExamSt);

			String cheminExam = directoryManager.idDbToString(idExamAChercher);
			cheminExam = cheminExam + "/" + idEtudSt + "/" + idEtudSt + ".surv";

			String cheminVideo = this.getServletContext().getRealPath("");

			ConversionVideo vid = new ConversionVideo(cheminExam, cheminVideo);			

			vid.run();
		}
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/Video.jsp" ).forward( request, response );        
	}
}

