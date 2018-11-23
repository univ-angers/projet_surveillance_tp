package com.surveillance.tp.utilitaire;

public class directoryManager {
	
	/**
	 * Modifie un identifiant d'examen pour en faire un chemin valide
	 * @param exam
	 * @return
	 */
	public static String idDbToString(int id)
	{
		String idExam = Integer.toString(id);

		// On ajoute les 0 pour avoir une haine de 10 caractères
		while (idExam.length() != 10)
		{
			idExam = "0" + idExam;
		}

		/* On transforme la chaine en tableau de char pour pouvoir intercaler
           des / entre chacun des caractères de la chaîne */
		char[] tmp = idExam.toCharArray();
		idExam = "";

		for(int i = 0; i < 10; i++)
		{
			idExam = idExam + "/" + tmp[i];
		}

		idExam = "/opt/data_dir" + idExam;

		return idExam;
	}

}
