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

public class ArretExamen extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	private DAOExamen daoExamen;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("id_user")!=null) {
			System.out.println("ICI");
			Integer idProf = (Integer)session.getAttribute("id_user");
			
			Examen examEnCours = daoExamen.trouverExamenUtil(idProf);			
			daoExamen.updateExamen(examEnCours.getIdExam());

			response.sendRedirect("/ServeurJEE/formExamen");
		}
		else 
		{
			response.sendRedirect("/ServeurJEE/formExamen");
		}




	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//this.getServletContext().getRequestDispatcher("/WEB-INF/MonCompte.jsp").forward(request, response);
	}
}
