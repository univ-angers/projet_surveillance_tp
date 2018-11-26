package com.surveillance.tp.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.beans.RegleExam;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAORegleExamen;
import com.surveillance.tp.utilitaire.directoryManager;

public class formExamen extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";

	private DAOExamen daoExamen;
	private DAORegleExamen daoRegleExamen;

	public void init() throws ServletException {
		/* Récupération d'une instance de notre DAO Utilisateur */
		this.daoExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getExamenDao();
		this.daoRegleExamen = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getRegleExamDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		/* Transmission vers la page en charge de l'affichage des résultats */
		this.getServletContext().getRequestDispatcher( "/WEB-INF/CreerExamen.jsp" ).forward( request, response );
	}


	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		System.out.println("DEBUG: Reception des infos en POST pour examen");

		/* Creation d'un utilisateur si les conditions sont remplies */
		Examen nouvExam = ajouterExamen(request);

		//A partir d'ici, nouvExam est l'examen ajouté
		//On va l'ajouter ainsi que ses règles
		creerDossierExamen(nouvExam);

		/* Stockage du bean dans la request */
		request.setAttribute("Examen", nouvExam);

		/* Affichage de la vue qu'on veut */
		this.getServletContext().getRequestDispatcher( "/WEB-INF/home.jsp" ).forward( request, response );
	}

	/**
	 * Ajoute un examen dans la BDD
	 * @param request
	 * @return
	 */
	public Examen ajouterExamen(HttpServletRequest request)
	{
		/* Récupération des données du formulaire */
		//Recupération de la durée
		String dureeH = request.getParameter("duree-heure");
		dureeH = "0" + dureeH;		//Pour la mise en forme
		String dureeM = request.getParameter("duree-minute");
		String timeSt = dureeH + ":" + dureeM + ":00";
		Time time = Time.valueOf(timeSt);		//DUREE A CORRIGER, NULL DANS LA BASE
		//Recupération de la matière
		String matiere = request.getParameter("matiere");
		//Récupération de la white-list
		//String ListeExamens = request.getParameter("white-list");

		/* Création d'un examen */
		Examen examen = new Examen();		
		examen.setIdProf(1);	//UTILISER LA SESSION A TERME
		examen.setMatiere(matiere);
		examen.setDuree(time);

		daoExamen.creer(examen);
		System.out.println("DEBUG: Examen ajouté");

		/* Gestion des règles */
		String cocheFichier = (String)request.getParameter("bouton_fichier");	// "on" si coché, null sinon
		String cocheUSB = (String)request.getParameter("bouton_USB");
		String cocheClavier = (String)request.getParameter("bouton_clavier");
		String cocheVideo = (String)request.getParameter("bouton_video");
		
		RegleExam re = new RegleExam();
		re.setIdExam(examen.getIdExam());

		if (cocheUSB != null)
		{
			re.setIdRegle(1);			//Connexion USB
			daoRegleExamen.creer(re);
			re.setIdRegle(2);			//Deconnexion USB
			daoRegleExamen.creer(re);
		}

		if (cocheFichier != null)
		{
			re.setIdRegle(3);			//creation fichier
			daoRegleExamen.creer(re);
			re.setIdRegle(4);			//modification fichier
			daoRegleExamen.creer(re);
			re.setIdRegle(5);			//suppression fichier
			daoRegleExamen.creer(re);
		}

		if (cocheVideo != null)
		{
			System.out.println("DEBUG; Num exam = " + re.getIdExam());
			re.setIdRegle(6);			// Vidéo
			daoRegleExamen.creer(re);
		}

		//TODO NET

		if (cocheClavier != null)
		{
			re.setIdRegle(8);			//Touche appuyée
			daoRegleExamen.creer(re);
		}

		return examen;
	}

	/**
	 * Créer le chemin de l'examen et y ajoute le fichier log
	 * @param exam
	 */
	public void creerDossierExamen(Examen exam)
	{
		File examDir;
		String pathDir = directoryManager.idDbToString(exam.getIdExam());

		System.out.println("DEBUG: CHEMIN SERVLET: " + pathDir);

		examDir = new File(pathDir);
		examDir.mkdirs();
	}    


}
