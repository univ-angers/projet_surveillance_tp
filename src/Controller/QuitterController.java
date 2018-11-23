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

	@SuppressWarnings("unchecked")
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
		datas.put("type", "etudiant_deconnexion");
		datas.put("IDexamen", EtudiantExamenInfoSingleton.getInstanceExistante().getNumeroExamen());
		datas.put("mailEtudiant", EtudiantExamenInfoSingleton.getInstanceExistante().getIdentifiant());
		datas.put("horodatage", hour+":"+minute+":"+sec);
		
		server.send(datas);
		
		Main.surveillanceEnCours = false;
		//Cela va arrêter les watchers en même temps
	}
}