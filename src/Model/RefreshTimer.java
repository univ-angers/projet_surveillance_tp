package Model;

import java.util.TimerTask;

import org.json.simple.JSONObject;

public class RefreshTimer extends TimerTask{

	@Override
	public void run() {
		System.out.println("Acquisition du temps depuis le serveur.");
		
		ServerLinkSingleton server;
		server = ServerLinkSingleton.getInstanceExistante();
		
		EtudiantExamenInfoSingleton etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();
		
		JSONObject datas = new JSONObject();
		datas.put("IDexamen", etudiant.getNumeroExamen());
		datas.put("type", "get_time_exam");
		
		boolean reussi = server.send(datas);
		if (reussi)
			System.out.println("ENVOI REFRESH REUSSI");
	}

	
}
