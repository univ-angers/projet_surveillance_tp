package Controller;

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
		
		JSONObject datas = new JSONObject();
		datas.put("Niveau", "critique");
		datas.put("IDexamen", EtudiantExamenInfoSingleton.getInstanceExistante().getNumeroExamen());
		datas.put("INE", EtudiantExamenInfoSingleton.getInstanceExistante().getNumeroEtudiant());
		datas.put("nom", EtudiantExamenInfoSingleton.getInstanceExistante().getNomEtudiant());
		datas.put("prenom", EtudiantExamenInfoSingleton.getInstanceExistante().getPrenomEtudiant());
		datas.put("info", "L'appplication a été fermée");
		
		server.send(datas);
		
		
	}
}