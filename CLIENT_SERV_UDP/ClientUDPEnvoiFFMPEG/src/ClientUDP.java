import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Cette classe permet d'envoyer via le protocole UDP le record de l'écran du PC client
 * A FAIRE: Donner une information basique (a déterminer) pour pouvoir récupérer le flux
 * que l'on veut plus tard depuis le serveur UDP
 * @author Base: Anais & Bastien
 *
 */
public class ClientUDP {
	
	

	public final static int port = 2345;

	static String addIp = "127.0.0.1";
	static String name = "C1";
	static int tempsFilm = 10;	//Temporaire

	//Dans le cas où l'on doit envoyer des informations supplémentaires par rapport à qui envoie
	public ClientUDP(String pName){
		name = pName;
	}

	public static void main(String[] args){
				
		//Création de l'objet qui va gérer la capture et l'envoi vers le serveur
		recorderFFMPEG rec = new recorderFFMPEG(1000,700,tempsFilm,addIp,port);
		//On lance la capture et l'envoi
		rec.run();
	}
}
