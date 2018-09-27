package Model;

import org.json.simple.JsonObject;

public class ServerLinkSingleton {

	// L'instance unique de la classe
	static ServerLinkSingleton instance;

	// L'ip du serveur auquel va communiquer l'objet
	private String ip;

	public void send(JsonObject datas) {
		System.out.println("ServerLink sent an event");
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
