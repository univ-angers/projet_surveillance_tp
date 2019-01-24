package com.surveillance.tp.utilitaire;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class recuperationIP {
	/**
	 * Renvoie l'adresse IP du serveur
	 * Source: https://stackoverflow.com/questions/40912417/java-getting-ipv4-address
	 * @return String ipv4
	 */
	public static String getAdresseIPV4() {
		String ip="";
		try {
			Enumeration<NetworkInterface> interfaces=NetworkInterface.getNetworkInterfaces();
			while(interfaces.hasMoreElements()) {
				NetworkInterface iface=interfaces.nextElement();
				// Filtre les adresses 127.xxx et les interfaces inactives
				if(iface.isLoopback() || !iface.isUp()) continue;
				Enumeration<InetAddress> addresses=iface.getInetAddresses();
				while(addresses.hasMoreElements()) {
					InetAddress addr=addresses.nextElement();
					if(addr instanceof Inet6Address) continue;
					ip=addr.getHostAddress();
				}
			}
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
		return ip;
	}
}