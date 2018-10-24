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
 * -- Mettre le name en private, il est pour l'instant nécessaire de l'avoir en public pour créer un pipe
 * propre à chaque client SUR LE MEME PC
 * 
 * @author Base: Anaïs & Bastien
 *
 */
public class ClientUDP {

	// On choisit un port aléatoire pour discuter avec le serveur pour l'envoi futur
	public static int port;

	static String addIp = "127.0.0.1";						//Adresse du serveur
	public static String name;								//Nom du client
	static String session;									//Matiere
	static boolean connexionEtablie = false;				
	static Scanner saisieInfo = new Scanner(System.in);
	static int tempsFilm = 10;	//Temporaire


	public static void envoiServ() throws IOException
	{
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

		// Récupération de la réponse du serveur
		byte[] buffer = new byte[2048];
		DatagramPacket packet2 = new DatagramPacket(buffer, buffer.length, adresse, 2345);
		client.receive(packet2);

		System.out.println("DEBUG: " + name + " a reçu une réponse du serveur : " + packet2.getData().toString());

		String reponseServ = new String(packet2.getData());
		reponseServ = reponseServ.trim();
		
		//OK:port
		String[] resultatReponse = reponseServ.split(":");
		String avis = resultatReponse[0].trim();
		
		if (avis.equals("OK"))
		{
			connexionEtablie = true;
			String portSt = resultatReponse[1].trim();
			port = Integer.parseInt(portSt);
		}
		
		client.close();
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
		pipe pip = new pipe(port); //name temporaire pour les test sur un seul PC
		//On lance le pipe
		pip.start();

		//Création de l'objet qui va gérer la capture et l'envoi vers le serveur
		recorderFFMPEG rec = new recorderFFMPEG(1920,1080, tempsFilm);
		//On lance la capture et l'envoi
		rec.start();				
	}
}
