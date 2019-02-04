package Model;

import java.util.Calendar;

import org.json.simple.JSONObject;

/**
 * Sert à détecter les arrêts de l'application dûs à une commande du terminal
 * Source : http://journal.thobe.org/2013/02/jvms-and-kill-signals.html
 */
public class DetectionArret {
	/**
	 * Envoie une alerte au serveur en cas d'arrêt du client, de manière normale ou non
	 * Envoie l'heure réelle de déconnexion, et pas le temps depuis le lancement
	 * de la surveillance
	 * @throws Exception
	 */
	public void detection_arret() throws Exception {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				ServerLinkSingleton server=ServerLinkSingleton.getInstanceExistante();
				EtudiantExamenInfoSingleton etudiant=EtudiantExamenInfoSingleton.getInstanceExistante();
				if(server!=null && etudiant!=null) {
					Calendar cal=Calendar.getInstance();
					JSONObject datas=new JSONObject();
					datas.put("type", "etudiant_deconnexion");
					datas.put("IDexamen", etudiant.getNumeroExamen());
					datas.put("mailEtudiant", etudiant.getIdentifiant());
					datas.put("horodatage", cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND));
					datas.put("info", "");
					server.send(datas);
				}
			}
		});
		for(;;) Thread.sleep(100);
	}
}