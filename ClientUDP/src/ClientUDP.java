import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Cette classe permet d'envoyer via le protocole UDP le record de l'écran du PC client
 * 
 * A FAIRE: 
 * -- Faire en sorte qu'une vidéo ai une longueur indéfinie
 * 
 * 
 * @author Base: Anaïs & Bastien
 *
 */
public class ClientUDP {

	// On choisit un port aléatoire pour discuter avec le serveur
	public static int port = 1024 + (int)(Math.random() * (65000-1024));

	static String addIp = "127.0.0.1";
	static String name;
	static String session;
	static boolean connexionEtablie = false;
	static Scanner saisieInfo = new Scanner(System.in);
	static int tempsFilm = 10;	//Temporaire

	//Dans le cas où l'on doit envoyer des informations supplémentaires par rapport à qui envoie
	public ClientUDP(String pName, String sess){
		name = pName;
		session = sess;
	}

	public static void main(String[] args) throws IOException{
		//Création du client et envoi des infos du client au serveur
		System.out.println("Veuillez entrer votre nom : ");
		name = saisieInfo.next();
		System.out.println("Veuillez entrer la session pour laquelle vous allez composer : ");
		session = saisieInfo.next();
		
		while (connexionEtablie == false)
		{
			envoiServ();
		}

		//création du pipe
		pipe pip = new pipe();
		//On lance le pipe
		pip.start();

		//Création de l'objet qui va gérer la capture et l'envoi vers le serveur
		recorderFFMPEG rec = new recorderFFMPEG(1920,1080, tempsFilm, addIp, port);
		//On lance la capture et l'envoi
		rec.start();
	}
	
	public static void envoiServ() throws IOException
	{
		//On initialise la connexion côté client
		DatagramSocket client = new DatagramSocket();

		//On crée notre datagramme
		InetAddress adresse = InetAddress.getByName("127.0.0.1");
		String donneeSt = "NOUVEAU:" + name + ":" + session + ":" + port;
		byte[] donnee = donneeSt.getBytes();
		DatagramPacket packet = new DatagramPacket(donnee, donnee.length, adresse, 2345);

		//On lui affecte les données à envoyer
		packet.setData(donnee);

		//On envoie au serveur
		client.send(packet);	
		client.close();
		
		// Récupération de la réponse du serveur
		byte[] buffer = new byte[2048];
		DatagramPacket packet2 = new DatagramPacket(buffer, buffer.length, adresse, port);
		client.receive(packet2);
		
		System.out.println(name + " a reçu une réponse du serveur : ");
		System.out.println(new String(packet2.getData()));
		
		String reponseServ = new String(packet2.getData());
		
		if (reponseServ.equals("OK"))
		{
			connexionEtablie = true;
		}
		else
		{
			connexionEtablie = false;
		}
	}
}
