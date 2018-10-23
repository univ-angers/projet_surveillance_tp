import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Cette classe permet d'envoyer via le protocole UDP le record de l'écran du PC client
 * 
 * A FAIRE: 
 * -- Donner une information basique (a déterminer) pour pouvoir récupérer le flux
 * que l'on veut plus tard depuis le serveur UDP
 * -- Faire en sorte qu'une vidéo ai une longueur indéfinie
 * 
 * 
 * @author Base: Anais & Bastien
 *
 */
public class ClientUDP {

	public final static int port = 2345;

	static String addIp = "127.0.0.1";
	static String name = "C1";
	static String session = "maths";
	static int tempsFilm = 10;	//Temporaire

	//Dans le cas où l'on doit envoyer des informations supplémentaires par rapport à qui envoie
	public ClientUDP(String pName, String sess){
		name = pName;
		session = sess;
	}

	public static void main(String[] args){
		//Création du client et envoi des infos du client au serveur

		try {
			//On initialise la connexion côté client
			DatagramSocket client = new DatagramSocket();

			//On crée notre datagramme
			InetAddress adresse = InetAddress.getByName("127.0.0.1");
			String donneeSt = "NOUVEAU:" + name + ":" + session;
			byte[] donnee = donneeSt.getBytes();
			DatagramPacket packet = new DatagramPacket(donnee, donnee.length, adresse, 2345);

			//On lui affecte les données à envoyer
			packet.setData(donnee);

			//On envoie au serveur
			client.send(packet);	
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{}

		//création du pipe
		pipe pip = new pipe();
		//On lance le pipe
		pip.start();

		//Création de l'objet qui va gérer la capture et l'envoi vers le serveur
		recorderFFMPEG rec = new recorderFFMPEG(1920,1080,tempsFilm,addIp,port);
		//On lance la capture et l'envoi
		rec.start();
	}
}
