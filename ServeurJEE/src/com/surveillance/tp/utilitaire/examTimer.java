package com.surveillance.tp.utilitaire;

import java.sql.Time;
import java.sql.Timestamp;

import com.surveillance.tp.beans.Examen;

public class examTimer {

	public static boolean examenTermine(Examen exam)
	{
		if (exam != null)
		{
			Timestamp dateDebut = exam.getHeureDebut();
			Time duree = exam.getDuree();
			long GMT1 = 3600000;		//Decalage horaire du à GMT+1

			long tempsDebut = dateDebut.getTime();
			long tempsDuree = duree.getTime();
			long heureFin = tempsDuree + tempsDebut + GMT1;
			long tempsActuel = System.currentTimeMillis();

			long tempsRestant = heureFin - tempsActuel;
			
			//Temps supplémentaire pour que les watchers ne s'arrêtent pas pile à l'examen, en cas de retard
			long tempsSupplementaire = -600000;	//10 minutes
			if (tempsRestant > tempsSupplementaire)
				return false;
		}
		
		return true;
	}
}
