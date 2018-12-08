package com.surveillance.tp.servlets;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.utilitaire.directoryManager;

/**
 * Servlet affichant les détails d'un étudiant concernant un examen spécifique
 */
public class detailExam extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	private DAOExamen daoExamen;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Examen */
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		//Aucun utilisateur connecté
		if (session.getAttribute("id_user") == null)
			response.sendRedirect("/ServeurJEE/LoginRegister");

		//L'utilisateur est un élève, donc pas le droit d'accès
		else if (session.getAttribute("groupeUtilisateur").equals("eleve"))
			response.sendRedirect("/ServeurJEE/monCompte");

		else
		{
			String idE=request.getParameter("id_etudiant");
			String idExamSt = request.getParameter("id_examen");
			int id_etudiant=Integer.parseInt(idE);
			
			Examen examen;
			//On veut accéder à un dossier archivé
			if (idExamSt != null)
			{
				int idExam = Integer.valueOf(idExamSt);
				examen=daoExamen.trouver(idExam);
				request.setAttribute("id_examen", examen.getIdExam());
			}
			else
				examen=daoExamen.trouverExamenUtil((int)session.getAttribute("id_user"));
			
			String log="";
			String logBody="\"body";

			String chemin = directoryManager.idDbToString(examen.getIdExam());
			if(examen!=null) {

				log=usingBufferedReader(chemin,id_etudiant);
				String[] test1 = log.split("body");
				logBody+=test1[1];			
			}
			
			//Vérification qu'un fichier vidéo existe
			File f = new File(chemin+"/"+id_etudiant+"/"+id_etudiant+".surv");
			if (f.exists())
				request.setAttribute("video", "oui");

			request.setAttribute("date_exam",String.valueOf(examen.getHeureDebut()));
			request.setAttribute("id_etud",String.valueOf(id_etudiant));
			request.setAttribute("log", logBody);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/ExamenDetail.jsp" ).forward( request, response );   
		}
	}

	private static String usingBufferedReader(String chemin,int id_etud)
	{		
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(chemin+"/"+id_etud+"/"+id_etud+".lg")))
		{

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null)
			{
				contentBuilder.append(sCurrentLine).append("\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}
}
