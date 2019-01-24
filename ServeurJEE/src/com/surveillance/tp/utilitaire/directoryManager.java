package com.surveillance.tp.utilitaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.surveillance.tp.beans.EtudiantExamen;

/**
 * Classe contenant diverses méthodes concernant l'accès aux dossiers d'examens
 */
public class directoryManager {
	/**
	 * Modifie un identifiant d'examen pour en faire un chemin valide
	 * @param int exam
	 * @return String exam
	 */
	public static String idDbToString(int id) {
		String idExam=Integer.toString(id);

		// On ajoute les 0 pour avoir une haine de 10 caractères
		while(idExam.length()!=10) idExam="0"+idExam;

		// On transforme la chaine en tableau de char pour pouvoir intercaler des / entre chacun des caractères de la chaîne
		char[] tmp=idExam.toCharArray();
		idExam="";
		for(int i=0; i<10; i++) idExam=idExam+"/"+tmp[i];
		idExam="/opt/data_dir/"+idExam;
		return idExam;
	}

	/**
	 * Va parcourir tous les dossiers (étudiants) du dossier de l'examen, lire chaque log, et créer un EtudiantExamen pour chacun.
	 * @param cheminExam
	 * @return ArrayList étudiant exam
	 */
	public static ArrayList<EtudiantExamen> recupererHeaderEtudiants(String cheminExam) {
		ArrayList<EtudiantExamen> listeEt=new ArrayList<>();
		File dossierCourant=new File(cheminExam);
		// On récupère chaque élément du dossier, donc chaque dossier d'étudiant
		String s[]=dossierCourant.list();
		for(int i=0; i<s.length; i++) {
			// Et on appelle la méthode permettant de créer un étudiant depuis son log
			EtudiantExamen etEx=creerEtExDepuisLog(cheminExam+"/"+s[i]+"/", s[i]);
			listeEt.add(etEx);
		}
		return listeEt;
	}

	/**
	 * Retourne l'étudiant correspondant au log du chemin indiqué
	 * @param cheminEt
	 * @return étudiant exam
	 */
	private static EtudiantExamen creerEtExDepuisLog(String cheminEt, String idEtud) {
		EtudiantExamen etudiant=new EtudiantExamen();
		// Lecture du fichier log avec arrêt au premier }
		StringBuilder sb=new StringBuilder();
		char stop='}';
		try {
			InputStream ips=new FileInputStream(cheminEt+"/"+idEtud+".lg");
			BufferedReader buffer=new BufferedReader(new InputStreamReader(ips));
			int r;
			while((r=buffer.read())!=-1) {
				char c=(char)r;
				sb.append(c);
				if(c==stop) break;
			}
			buffer.close();
		} catch(IOException e) {}
		String headerSt=sb.toString()+"}"; // On ajoute un } pour avec un JSON correctement formé

		// Conversion de la chaine en JSON
		JSONParser parser=new JSONParser();
		JSONObject jObj;
		try {
			jObj=(JSONObject)parser.parse(headerSt);
			JSONObject header=(JSONObject)jObj.get("header");

			// Récupération des données
			String nom=(String)header.get("nom");
			String prenom=(String)header.get("prenom");
			long nbLog=(long)header.get("nbLog");
			long nbCrit=(long)header.get("nbCrit");
			long nbClavier=(long)header.get("nbClavier");
			long nbNet=(long)header.get("nbNet");
			long nbFichier=(long)header.get("nbFichier");
			long nbUSB=(long)header.get("nbUSB");

			// Ajout pour l'étudiant
			etudiant.setNomEt(nom);
			etudiant.setPrenEt(prenom);
			etudiant.setNbAlertes(String.valueOf(nbLog));
			etudiant.setNbAlertesCritiques(String.valueOf(nbCrit));	
			etudiant.setNbAlertesClavier(String.valueOf(nbClavier));
			etudiant.setNbAlertesNet(String.valueOf(nbNet));
			etudiant.setNbAlertesFichier(String.valueOf(nbFichier));
			etudiant.setNbAlertesUSB(String.valueOf(nbUSB));
			etudiant.setId(Integer.valueOf(idEtud));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return etudiant;			
	}
}