package Model;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;

/**
 * A VOIR AU NIVEAU DE L'IP
 */
public class ServerLinkSingleton {

	// L'instance unique de la classe
	static ServerLinkSingleton instance;

	// L'ip du serveur auquel va communiquer l'objet
	private String ip;

	public void send(JSONObject datas) {
		//System.out.println("DEBUG: Envoi des données: " + datas);
		
		URL url;
	    HttpURLConnection connection = null;
	    
	    //Temporaire ?
	    try {
	        url = new URL("http://" + ip + ":8080/ServeurJEE/receptionJSON");     //Creating the URL.
	        connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setUseCaches(false);
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        //Send request
	        OutputStream os = connection.getOutputStream();
	        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
	        System.out.println(datas.toString());
	        osw.write(datas.toString());
	        osw.flush();
	        osw.close();
	        /* DEBUG
	        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	            System.out.println("DEBUG: OK");
	        } else {
	            System.out.println("DEBUG: FAIL");
	        }*/
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}

	// GENERATION

	private ServerLinkSingleton(String ip) {
		this.ip = ip;
	}

	static public ServerLinkSingleton getInstance(String ip) {
		if (instance == null) {
			instance = new ServerLinkSingleton(ip);
		}
		return instance;
	}

	// GETTERS & SETTERS

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


}
