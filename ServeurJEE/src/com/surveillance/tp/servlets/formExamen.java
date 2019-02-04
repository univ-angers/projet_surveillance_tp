package com.surveillance.tp.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.surveillance.tp.beans.Examen;
import com.surveillance.tp.beans.RegleExam;
import com.surveillance.tp.dao.DAOExamen;
import com.surveillance.tp.dao.DAOFactory;
import com.surveillance.tp.dao.DAORegleExamen;
import com.surveillance.tp.utilitaire.directoryManager;

/**
 * Servlet affichant le formulaire permettant la création d'un examen
 */
@WebServlet("/formExamen")
public class formExamen extends HttpServlet {
	public static final String CONF_DAO_FACTORY="daofactory";
	private DAOExamen daoExamen;
	private DAORegleExamen daoRegleExamen;

	public void init() throws ServletException {
		// Récupération d'une instance de nos DAO
		daoExamen=((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getExamenDao();
		daoRegleExamen=((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getRegleExamDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		HttpSession session=request.getSession();
		// Aucun utilisateur connecté
		if(session.getAttribute("id_user")==null) response.sendRedirect(request.getContextPath()+"/logout");
		else if(session.getAttribute("groupeUtilisateur").equals("professeur")) getServletContext().getRequestDispatcher("/WEB-INF/CreerExamen.jsp").forward(request, response);
		else response.sendRedirect(request.getContextPath()+"/monCompte"); // L'utilisateur est un élève, donc pas le droit d'accès
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Creation d'un utilisateur si les conditions sont remplies
		Examen nouvExam=ajouterExamen(request);

		// Ajout de l'id de l'examen en paramètre de session
		HttpSession session=request.getSession();
		session.setAttribute("idExamEnCours", nouvExam.getIdExam());

		// On va l'ajouter ainsi que ses règles
		creerDossierExamen(nouvExam);

		// Affichage de la vue qu'on veut
		response.sendRedirect(request.getContextPath()+"/paramExam");
	}

	/**
	 * Ajoute un examen dans la BDD
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Examen ajouterExamen(HttpServletRequest request) {
		// Récupération des données du formulaire
		// Recupération de la durée
		String dureeH=request.getParameter("duree-heure");
		dureeH="0"+dureeH; // Pour la mise en forme
		String dureeM=request.getParameter("duree-minute");
		String timeSt=dureeH + ":" + dureeM + ":00";
		Time time=Time.valueOf(timeSt);
		// Recupération de la matière
		String matiere=request.getParameter("matiere");

		// Création d'un examen
		Examen examen=new Examen();

		HttpSession session=request.getSession();
		examen.setIdProf((int)session.getAttribute("id_user"));
		examen.setMatiere(matiere);
		examen.setDuree(time);
		daoExamen.creer(examen);

		// Gestion des règles
		String cocheFichier=(String)request.getParameter("bouton_fichier");	// "on" si coché, null sinon
		String cocheUSB=(String)request.getParameter("bouton_usb");
		String cocheClavier=(String)request.getParameter("bouton_clavier");
		String cocheVideo=(String)request.getParameter("bouton_video");
		// Récupération de la white-list
		String ListeExamens=request.getParameter("white-list");
		RegleExam re=new RegleExam();
		re.setIdExam(examen.getIdExam());

		if(cocheUSB!=null) {
			re.setIdRegle(1); // Connexion USB
			daoRegleExamen.creer(re);
			re.setIdRegle(2); // Deconnexion USB
			daoRegleExamen.creer(re);
		}

		if(cocheFichier!=null) {
			re.setIdRegle(3); // création fichier
			daoRegleExamen.creer(re);
			re.setIdRegle(4); // modification fichier
			daoRegleExamen.creer(re);
			re.setIdRegle(5); // suppression fichier
			daoRegleExamen.creer(re);
		}

		if(cocheVideo!=null) {
			re.setIdRegle(6); // Vidéo
			daoRegleExamen.creer(re);
		}

		if(ListeExamens.length()>0) {
			re.setIdRegle(7);
			// Recupération de chaque site dans le tableau
			ListeExamens = ListeExamens.trim().replace(" +"," ");
			String[] tabSite=ListeExamens.split(" ");
			
			// Tableau JSON qui sera mis dans la table
			JSONArray jsTab=new JSONArray();
			for(int i=0; i<tabSite.length; i++) {
				JSONObject obj=new JSONObject();
				obj.put("url",tabSite[i]);
				jsTab.add(obj);
			}

			String jsString=jsTab.toJSONString();
			daoRegleExamen.creerAttribut(re, jsString);
}

		if(cocheClavier!=null) {
			re.setIdRegle(8); // Touche appuyée
			daoRegleExamen.creer(re);
		}
		return examen;
	}

	/**
	 * Créer le chemin de l'examen et y ajoute le fichier log
	 * @param exam
	 */
	public void creerDossierExamen(Examen exam) {
		File examDir;
		String pathDir=directoryManager.idDbToString(exam.getIdExam());
		examDir=new File(pathDir);
		examDir.mkdirs();
	}
}