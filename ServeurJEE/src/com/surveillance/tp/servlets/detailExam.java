package com.surveillance.tp.servlets;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;

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

			if(examen!=null) {

				log=usingBufferedReader(examen.getIdExam(),id_etudiant);
				String[] test1 = log.split("body");
				logBody+=test1[1];			
			}

			request.setAttribute("date_exam",String.valueOf(examen.getHeureDebut()));
			request.setAttribute("id_etud",String.valueOf(id_etudiant));
			request.setAttribute("log", logBody);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/ExamenDetail.jsp" ).forward( request, response );   
		}
	}

	public String readLog(int id_exam,int id_etudiant)
	{
		String content = "";
		try
		{
			content = new String ( Files.readAllBytes( Paths.get("/opt/data_dir/0/0/0/0/0/0/0/0/0/"+id_exam+"/"+id_etudiant+"/"+id_etudiant+"/"+".log") ) );
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return content;
	}


	private static String usingBufferedReader(int id_ex,int id_etud)
	{
		StringBuilder contentBuilder = new StringBuilder();
		System.out.println("ID Ex : "+ id_ex);
		System.out.println("ID Etu : "+ id_etud);
		try (BufferedReader br = new BufferedReader(new FileReader("/opt/data_dir/0/0/0/0/0/0/0/0/0/"+id_ex+"/"+id_etud+"/"+id_etud+".lg")))
		{

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null)
			{
				contentBuilder.append(sCurrentLine).append("\n");
			}
			System.out.println(contentBuilder.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}
}
