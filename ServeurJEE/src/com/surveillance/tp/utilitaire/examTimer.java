package com.surveillance.tp.utilitaire;

import java.sql.Time;
import java.sql.Timestamp;

import com.surveillance.tp.beans.Examen;

/**
 * Classe contenant diverses méthodes concernant le temps d'examen
 */
public class examTimer {
	public static boolean examenTermine(Examen exam) {
		if(exam!=null) {
			// Cas de l'examen créé mais qui n'a jamais démarré
			// On ne retourne pas qu'il est fini puisqu'il n'a pas commencé
			if(exam.getHeureDebut()==null) return false;
			else {
				long tempsRestant=examTimer.tempsRestant(exam);
				// Temps supplémentaire pour que les watchers ne s'arrêtent pas pile à l'examen, en cas de retard
				long tempsSupplementaire=600000; // 10 minutes
				if(tempsRestant>-tempsSupplementaire) return false;
			}
		}
		return true;
	}

	public static long tempsRestant(Examen exam) {
		Timestamp dateDebut=exam.getHeureDebut();
		Time duree=exam.getDuree();
		long GMT1=3600000; // Décalage horaire du à GMT+1
		long tempsDebut=dateDebut.getTime();
		long tempsDuree=duree.getTime();
		long heureFin=tempsDuree+tempsDebut+GMT1;
		long tempsActuel=System.currentTimeMillis();
		long tempsRestant=heureFin-tempsActuel;
		return tempsRestant;
	}
}