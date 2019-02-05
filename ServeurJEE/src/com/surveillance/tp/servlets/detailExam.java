package com.surveillance.tp.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAORegle;
import com.surveillance.tp.utilitaire.directoryManager;

/**
 * Servlet affichant les détails d'un étudiant concernant un examen spécifique
 */
@WebServlet("/detailExamen")
public class detailExam extends HttpServlet {
	public static final String CONF_DAO_FACTORY="daofactory";
	private DAOExamen daoExamen;
	private DAORegle daoRegle;

	public void init() throws ServletException {
		// Récupération d'une instance de notre DAO Examen
		daoExamen=((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getExamenDao();
		daoRegle=((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getRegleDao();
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		// Aucun utilisateur connecté
		if(session.getAttribute("id_user")==null) response.sendRedirect(request.getContextPath()+"/logout");
		else if(session.getAttribute("groupeUtilisateur").equals("professeur")) {
			String idE=request.getParameter("id_etudiant");
			String idExamSt=request.getParameter("id_examen");
			int id_etudiant=Integer.parseInt(idE);
			Examen examen;

			// On veut accéder à un dossier archivé
			if(idExamSt!=null) {
				int idExam=Integer.valueOf(idExamSt);
				examen=daoExamen.trouver(idExam);
				request.setAttribute("id_examen", examen.getIdExam());
			}
			else examen=daoExamen.trouverExamenUtil((int)session.getAttribute("id_user"));

			JSONArray logBody=null;
			String chemin=directoryManager.idDbToString(examen.getIdExam());
			if(examen!=null) {
				JSONParser parser=new JSONParser();
				try {
					logBody=(JSONArray)((JSONObject)parser.parse(new FileReader(chemin+"/"+id_etudiant+"/"+id_etudiant+".log"))).get("body");
					Iterator<JSONObject> iterator=logBody.iterator();
					while(iterator.hasNext()) {
						JSONObject temp=iterator.next();
						temp.put("type", daoRegle.trouver(Integer.parseInt((String)temp.get("type"))).getDescription());
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}			
			}

			// Vérification qu'un fichier vidéo existe
			File f=new File(chemin+"/"+id_etudiant+"/"+id_etudiant+".ts");
			if(f.exists()) request.setAttribute("video", "oui");
			request.setAttribute("date_exam", String.valueOf(examen.getHeureDebut()));
			request.setAttribute("id_etud", String.valueOf(id_etudiant));
			request.setAttribute("log", logBody);
			getServletContext().getRequestDispatcher("/WEB-INF/ExamenDetail.jsp").forward(request, response);
		}
		else response.sendRedirect(request.getContextPath()+"/monCompte"); // L'utilisateur est un élève, donc pas le droit d'accès
	}
}