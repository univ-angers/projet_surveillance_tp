package Model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 * Classe qui fait le lien entre le client et le serveur
 * Les différentes demandes et récupérations de données se font par elle
 */
public class ServerLinkSingleton {
	// L'instance unique de la classe
	static ServerLinkSingleton instance;

	// L'ip du serveur auquel va communiquer l'objet
	private String ip;

	/**
	 * Gère l'envoi de données et la récupération des réponses
	 * Retourne vrai si une requête a abouti au résultat attendu
	 * @param datas
	 * @return
	 */
	public boolean send(JSONObject datas) {
		URL url;
		HttpURLConnection connection=null;

		try {
			url=new URL("http://"+ip+":8080/ServeurJEE/receptionJSON");
			connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Envoi
			OutputStream os=connection.getOutputStream();
			OutputStreamWriter osw=new OutputStreamWriter(os, "UTF-8");
			osw.write(datas.toString());
			osw.flush();
			osw.close();

			if(connection.getResponseCode()==HttpURLConnection.HTTP_OK) return traitementDonnees(connection);
			else return false;
		} catch(Exception ex) {
			return false;
		}
	}

	/**
	 * Gère les différentes réponses. Retourne vrai ou faux suivant le résultat
	 * du traitement
	 * @param connection
	 * @return
	 */
	private boolean traitementDonnees(HttpURLConnection connection) {
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder sb=new StringBuilder();
			String line;
			while((line=br.readLine())!=null) sb.append(line+"\n");
			br.close();
			String res=sb.toString();

			if(res.length()>0) { // On fait nos comparaisons uniquement si le serveur a retourné quelque chose
				JSONParser parser=new JSONParser();
				JSONObject jObj=(JSONObject)parser.parse(res);
				String type=(String)jObj.get("type");

				// Connexion de l'étudiant réussie
				if(type.equals("rep_co_pos")) {
					EtudiantExamenInfoSingleton etudiant=EtudiantExamenInfoSingleton.getInstanceExistante();
					String idBDDEtud=(String)jObj.get("idbdd");
					etudiant.setIdBDD(idBDDEtud);
					boolean checkSiteActif=false; // Ce boolean permet de savoir si on vérifie les sites internets

					JSONArray idWatchers=(JSONArray)jObj.get("list_watcher");
					for(int i=0; i<idWatchers.size(); ++i) {
						JSONObject wat=(JSONObject)idWatchers.get(i);
						String s="W"+String.valueOf(i+1);
						long idWatcherLong=(long)wat.get(s);
						int idWatcherInt=(int)idWatcherLong;
						if(idWatcherInt==4) checkSiteActif=true;
						etudiant.getListeWatchers().add(idWatcherInt);
					}

					if (checkSiteActif)
					{
						JSONArray json = null;
						json = (JSONArray) jObj.get("site_surveillance");
						Iterator<JSONObject> iterator = json.iterator();
						while (iterator.hasNext()) {
							JSONObject newob = new JSONObject();
							newob = iterator.next();
							if(newob.get("url")!=null) {
								String site = (String)newob.get("url");
								etudiant.getSiteASurveiller().add(site);
							}
						}
					}
					return true;
				}

				// Connexion de l'étudiant non réussie, mauvais ID
				if(type.equals("rep_co_neg")) return false;

				// Refresh de l'interface de l'étudiant
				if(type.equals("rep_temps")) {
					TempsSingleton tps=TempsSingleton.getInstance();
					String tpsRestant=(String)jObj.get("tps");
					tps.setTemps(tpsRestant);
					return true;
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return false; // Si aucun des cas n'a été trouvé
	}

	private ServerLinkSingleton(String i) {
		ip=i;
	}

	static public ServerLinkSingleton getInstance(String ip) {
		if(instance==null) instance=new ServerLinkSingleton(ip);
		return instance;
	}

	static public ServerLinkSingleton getInstanceExistante() {
		return instance;
	}

	// GETTERS & SETTERS
	public String getIp() {
		return ip;
	}

	public void setIp(String i) {
		ip=i;
	}
}