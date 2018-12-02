package com.surveillance.tp.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.surveillance.tp.beans.EtudiantExamen;
import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAOUtilisateur;
import com.surveillance.tp.utilitaire.directoryManager;

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
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("D1");

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
			int idProf = (int) session.getAttribute("id_user");
			Examen examEnCours = daoExamen.trouverExamenUtil(idProf);

			ArrayList<EtudiantExamen> listeUtilisateurExamenCourant = new ArrayList<>();
			if (examEnCours != null)
			{
				String cheminExam = directoryManager.idDbToString(examEnCours.getIdExam());
				listeUtilisateurExamenCourant = recupererEtudiants(cheminExam);
			}
			request.setAttribute("utilisateurs", listeUtilisateurExamenCourant);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/listeUtilisateurs.jsp" ).forward( request, response );
		}
	}


/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub



	//request.setAttribute("utilisateurs", daoUtilisateur.recupererUtilisateurs());

	this.getServletContext().getRequestDispatcher("/WEB-INF/listeUtilisateurs.jsp").forward(request, response);
}

/**
 * Va parcourir tous les dossiers (étudiants) du dossier de l'examen, lire chaque log, et créer un
 * EtudiantExamen pour chacun.
 * @param cheminExam
 * @return
 */
private ArrayList<EtudiantExamen> recupererEtudiants(String cheminExam) {
	ArrayList<EtudiantExamen> listeEt = new ArrayList<>();

	System.out.println("DEBUG: CHEMIN DE L'EXAMEN = " + cheminExam);

	File dossierCourant = new File(cheminExam);

	//On récupère chaque élément du dossier, donc chaque dossier d'étudiant
	String s[] = dossierCourant.list();
	for (int i=0; i<s.length; i++){
		File dirTemp = new File(cheminExam + s[i] + "\\" );

		if(dirTemp.isDirectory()){
			EtudiantExamen etEx = creerEtExDepuisLog(cheminExam + s[i] + "\\",s[i]);
			listeEt.add(etEx);
		}
	}

	return listeEt;
}

/**
 * Retourne l'étudiant correspondant au log du chemin indiqué
 * @param cheminEt
 * @return
 */
private EtudiantExamen creerEtExDepuisLog(String cheminEt, String idEtud) {
	System.out.println("DEBUG: CHEMIN DE L'ETUDIANT = " + cheminEt);

	//Lecture du fichier log avec arrêt au premier }
	try{
		StringBuilder sb = new StringBuilder();
		char stop = '}';
		try {
			InputStream ips=new FileInputStream(cheminEt + "\\" + idEtud + ".lg");
			BufferedReader buffer=new BufferedReader(new InputStreamReader(ips));
			int r;
			while ((r = buffer.read()) != -1) {
				char c = (char) r;
				sb.append(c);
				if (c == stop)
					break;
			}
			buffer.close();
		} catch(IOException e) {
		}
		String headerSt = sb.toString() + "}";	//On ajoute un } pour avec un JSON correctement formé

		System.out.println("DEBUG: Header etud " + idEtud + " = " + headerSt);

		return null;
	}
	finally
	{}			
}
}