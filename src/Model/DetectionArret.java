package Model;

import java.util.Date;

import org.json.simple.JSONObject;

/**
 * Sert à détecter les arrêts de l'application dûs à une commande du terminal
 * Source : http://journal.thobe.org/2013/02/jvms-and-kill-signals.html
 */
public class DetectionArret
{
	/**
	 * Envoie une alerte au serveur en cas d'arrêt du client, de manière normale ou non
	 * Envoie l'heure réelle de déconnexion, et pas le temps depuis le lancement
	 * de la surveillance
	 * @throws Exception
	 */
	public void detection_arret() throws Exception
	{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
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
			}
		});
		for (;;)
			Thread.sleep(100);
	}
}