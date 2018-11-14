package Controller;

import java.util.Date;

import org.json.simple.JSONObject;

import Model.EtudiantExamenInfoSingleton;
import Model.ServerLinkSingleton;
import Vue.Quitter;

public class QuitterController 
{
	Quitter fenQuitter;

	public QuitterController(Quitter fenetre)
	{
		fenQuitter = fenetre;
	}

	public void alerteQuit() 
	{
		ServerLinkSingleton server;
		server = ServerLinkSingleton.getInstanceExistante();
		
		Date date = new Date();
		@SuppressWarnings("deprecation")
		int hour = date.getHours();
		@SuppressWarnings("deprecation")
		int minute = date.getMinutes();
		@SuppressWarnings("deprecation")
		int sec = date.getSeconds();
		
		JSONObject datas = new JSONObject();
		datas.put("Niveau", "critique");
		datas.put("IDexamen", EtudiantExamenInfoSingleton.getInstanceExistante().getNumeroExamen());
		datas.put("IDEtudiant", EtudiantExamenInfoSingleton.getInstanceExistante().getIdentifiant());
		datas.put("mdp", EtudiantExamenInfoSingleton.getInstanceExistante().getMotDePasse());
		datas.put("horodatage", hour+":"+minute+":"+sec);
		datas.put("info", "L'appplication a été fermée");
		
		server.send(datas);
		
		Main.surveillanceEnCours = false;
		//Cela va arrêter les watchers en même temps
	}
}