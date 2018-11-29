package Model;

import java.util.TimerTask;

import org.json.simple.JSONObject;

public class RefreshTimer extends TimerTask{

	@Override
	public void run() {
		
		ServerLinkSingleton server;
		server = ServerLinkSingleton.getInstanceExistante();
		
		EtudiantExamenInfoSingleton etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();
		
		JSONObject datas = new JSONObject();
		datas.put("IDexamen", etudiant.getNumeroExamen());
		datas.put("type", "get_time_exam");
		
		server.send(datas);
	}

	
}
