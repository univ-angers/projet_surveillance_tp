package com.surveillance.tp.servlets;

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
import com.surveillance.tp.utilitaire.examTimer;
import com.surveillance.tp.utilitaire.recuperationIP;

/**
 * Servlet affichant les paramètres d'un examen, permettant de démarrer ou modifier l'examen en cours
 */
@WebServlet("/paramExam")
public class paramExam extends HttpServlet {
	public static final String CONF_DAO_FACTORY="daofactory";
	private DAOExamen daoExamen;
	private DAORegleExamen daoRegleExamen;

	public void init() throws ServletException {
		// Récupération d'une instance de nos DAO
		daoExamen=((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getExamenDao();
		daoRegleExamen=((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getRegleExamDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();

		// Aucun utilisateur connecté
		if(session.getAttribute("id_user")==null) response.sendRedirect(request.getContextPath()+"/logout");
		else if(session.getAttribute("groupeUtilisateur").equals("professeur")) {
			int idProf=(int)session.getAttribute("id_user");
			Examen examEnCours=daoExamen.trouverExamenUtil(idProf);

			// Vérification que le temps d'examen n'est pas terminé
			if(examEnCours!=null) {
				if(examTimer.examenTermine(examEnCours)) response.sendRedirect(request.getContextPath()+"/arretExamen");
				else {
					request.setAttribute("IDexam", examEnCours.getIdExam());
					request.setAttribute("Matiere", examEnCours.getMatiere());
					request.setAttribute("IP", recuperationIP.getAdresseIPV4());
					request.setAttribute("nb_heure", examEnCours.get_nb_hours());
					request.setAttribute("nb_minute", examEnCours.get_nb_minute());
					// Savoir quel bouton on affiche entre démarrage et stop
					if(examEnCours.getHeureDebut()==null) request.setAttribute("Demarre", "non");
					getServletContext().getRequestDispatcher("/WEB-INF/ParametresExamen.jsp").forward(request, response);
				}
			}
		}
		else response.sendRedirect(request.getContextPath()+"/monCompte"); // L'utilisateur est un élève, donc pas le droit d'accès
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Récupération des données du formulaire

		// Recupération de la durée
		String dureeH=request.getParameter("duree-heure");
		dureeH="0"+dureeH; // Pour la mise en forme
		String dureeM=request.getParameter("duree-minute");
		String timeSt=dureeH+":"+dureeM+":00";
		Time time=Time.valueOf(timeSt);

		// Récupération de la matière
		String matiere=request.getParameter("matiere");
		HttpSession session=request.getSession();
		int idProf=(int)session.getAttribute("id_user");

		// Modification si besoin de l'examen
		Examen examEnCours=daoExamen.trouverExamenUtil(idProf);
		if(examEnCours.getDuree()!=time || examEnCours.getMatiere()!=matiere) {			
			examEnCours.setMatiere(matiere);
			examEnCours.setDuree(time);
			daoExamen.miseAJour(examEnCours);
		}

		// Gestion des règles
		String cocheFichier=(String)request.getParameter("bouton_fichier"); // "on" si coché, null sinon
		String cocheUSB=(String)request.getParameter("bouton_usb");
		String cocheClavier=(String)request.getParameter("bouton_clavier");
		String cocheVideo=(String)request.getParameter("bouton_video");

		// Récupération de la white-list
		String ListeExamens = request.getParameter("white-list");

		// On va supprimer toutes les règles qui existaient pour cet examen, et les recréer
		daoRegleExamen.supprimerExam(examEnCours.getIdExam());
		RegleExam re=new RegleExam();
		re.setIdExam(examEnCours.getIdExam());

		if(cocheUSB!=null) {
			re.setIdRegle(1); // Connexion USB
			daoRegleExamen.creer(re);
			re.setIdRegle(2); // Déconnexion USB
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
			// Récupération de chaque site dans le tableau
			String[] tabSite=ListeExamens.split(" ");
			// Tableau JSON qui sera mis dans la table
			JSONArray jsTab=new JSONArray();
			for(int i=0; i<tabSite.length; i++) {
				JSONObject obj=new JSONObject();
				obj.put(String.valueOf(i), tabSite[i]);
				jsTab.add(obj);
			}

			String jsString=jsTab.toJSONString();
			daoRegleExamen.creerAttribut(re,jsString);
		}

		if(cocheClavier!=null) {
			re.setIdRegle(8); // Touche appuyée
			daoRegleExamen.creer(re);
		}
		response.sendRedirect(request.getContextPath()+"/listeUtilisateurs");
	}
}