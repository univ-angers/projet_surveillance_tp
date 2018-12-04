package Model.Watcher;

import Model.EtudiantExamenInfoSingleton;
import Model.ServerLinkSingleton;

import java.sql.Time;
import java.util.Date;

import org.json.simple.JSONObject;


public abstract class Watcher extends Thread {
	
	// Le nom du type du Watcher
	protected String type;
	private long tempsDemarrage;

	// CONSTRUCTOR

	public Watcher(String type) {
		this.type = type;
		this.tempsDemarrage = System.currentTimeMillis();
	}

	/**
	 * Envoie l'évènement à l'objet qui se charge de communiquer avec le serveur
	 */
	public void sendEvent(ServerLinkSingleton serverLink, JSONObject infos) {
		// Envoie de l'évènement avec les données créées spécifiquement
		serverLink.send(infos);
	}

	/**
	 * Méthode qui sera défini dans les classes filles pour traiter et construire l'objet JSON qui sera envoyé
	 * La transformation du long en string provient de : http://www.java2s.com/Code/Java/Development-Class/Takesatimeinmillisecondsandreturnsanhoursminutesandsecondsrepresentation.htm
	 */
	@SuppressWarnings("unchecked")
	protected  void createDataBeforeSendEvent(String typ, String information)
	{
		long tempsEnvoi = System.currentTimeMillis();
		long delai = tempsEnvoi - tempsDemarrage;
		
		long tempsSeconde = delai/1000;
        long heure = (tempsSeconde / 3600);
        long min = (tempsSeconde / 60) % 60;
        long sec = tempsSeconde % 60;
        String horodatage;
        String minsString = (min == 0)
            ? "00"
            : ((min < 10)
               ? "0" + min
               : "" + min);
        String secsString = (sec == 0)
            ? "00"
            : ((sec < 10)
               ? "0" + sec
               : "" + sec);
        if (heure > 0)
            horodatage =  "" + heure + ":" + minsString + ":" + secsString;
        else if (min > 0)
        	horodatage = "00:" + min + ":" + secsString;
        else horodatage = "00:00:" + secsString;
		
		// Niveau d'alerte à ajouter et autres infos si besoin
		JSONObject datas = new JSONObject();
		datas.put("IDexamen", EtudiantExamenInfoSingleton.getInstanceExistante().getNumeroExamen());
		datas.put("mailEtudiant", EtudiantExamenInfoSingleton.getInstanceExistante().getIdentifiant());
		datas.put("horodatage", horodatage);
		datas.put("type", typ);
		datas.put("info", information);
		
		ServerLinkSingleton SLS = ServerLinkSingleton.getInstanceExistante();
		this.sendEvent(SLS, datas);
	}

}
