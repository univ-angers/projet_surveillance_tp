package Model.Watcher;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import Model.EtudiantExamenInfoSingleton;
import Model.Pipe;
import Model.RecorderFFMPEG;

/**
 * Cette classe permet d'envoyer via le protocole UDP le record de l'écran du PC client
 * 
 * A FAIRE: 
 * -- ENVOYER LE BON TYPE DE DONNEES POUR LA RECONNAISSANCE PAR LE SERVEUR
 * 
 * @author Base: Anaïs & Bastien
 *
 */
public class VideoWatcher extends Watcher{

	static String TYPE = "FILE";
	
	public VideoWatcher() {
		super(TYPE);
	}

	// On choisit un port aléatoire pour discuter avec le serveur pour l'envoi futur
	public static int port;

	static String addIp = "127.0.0.1";						//Adresse du serveur
	public static String name;								//Nom du client
	static String session;									//Matiere
	static boolean arretDemande = false;
	static boolean connexionEtablie = false;				
	static Scanner saisieInfo = new Scanner(System.in);
	
	
	
	

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

		//System.out.println("DEBUG: " + name + " a reçu une réponse du serveur : " + packet2.getData().toString());

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
	
	@Override
	public void run(){
		EtudiantExamenInfoSingleton etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();
		//Création du client et envoi des infos du client au serveur
		name = etudiant.getIdentifiant();
		session = etudiant.getNumeroExamen();

		while (connexionEtablie == false)
		{
			try {
				envoiServ();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//création du pipe
		Pipe pip = new Pipe(port); //name temporaire pour les test sur un seul PC
		//On lance le pipe
		pip.start();

		//Création de l'objet qui va gérer la capture et l'envoi vers le serveur
		RecorderFFMPEG rec = new RecorderFFMPEG(1920,1080);
		//On lance la capture et l'envoi
		rec.start();	

		//Arret du record temporaire, à voir de quelle manière on arrête à terme
		while (arretDemande == false)
		{
			System.out.println("Entrez \"Stop\" pour arrêter le record.");
			String receptionConsole = saisieInfo.next();

			if (receptionConsole.equals("Stop"))
			{
				rec.stopRecord();
				arretDemande=true;
			}
			else
			{
				System.out.println("Texte non reconnu.");
			}
		}
	}
}
