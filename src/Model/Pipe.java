package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import Model.Watcher.VideoWatcher;

/**
 * Créer le fichier pipe qui va recevoir les informations de la vidéo
 * et les envoie en continu vers le serveur UDP, qui est chargé d'enregistrer
 * et stocker les vidéos
 */
public class Pipe extends Thread {

	private final int taillePaquet = 2048;
	private final int portVideo;
	private Process pPipe;
	private boolean pipePret;
	private EtudiantExamenInfoSingleton etudiant;

	public Pipe(int port)
	{
		pPipe = null;
		portVideo = port;
		etudiant = EtudiantExamenInfoSingleton.getInstanceExistante();
	}

	/**
	 * Envoie les données au serveur vidéo
	 * @param donnees
	 * @param taillePaquet
	 * @throws IOException
	 */
	public void envoiPaquet(byte[] donnees, int taillePaquet) throws IOException
	{
		//On initialise la connexion côté client
		DatagramSocket client = new DatagramSocket();

		//On crée notre datagramme
		InetAddress adresse = InetAddress.getByName(etudiant.getAdresseServeur());
		DatagramPacket packet = new DatagramPacket(donnees, taillePaquet, adresse, portVideo);

		//On lui affecte les données à envoyer
		packet.setData(donnees);

		//On envoie au serveur
		client.send(packet);		

		client.close();
	}

	/**
	 * Envoie le paquet annonçant que le client a terminé d'enregistrer, permettant la fermeture du socket
	 * qui lui est réservé côté serveur
	 * @throws IOException
	 */
	public void envoiPaquetFinClient() throws IOException
	{
		String fin = "FIN:"+ VideoWatcher.port;
		byte[] donnees = fin.getBytes();		
		int taillePaquet = donnees.length;

		//On initialise la connexion côté client
		DatagramSocket client = new DatagramSocket();

		//On crée notre datagramme
		InetAddress adresse = InetAddress.getByName(etudiant.getAdresseServeur());
		DatagramPacket packet = new DatagramPacket(donnees, taillePaquet, adresse, 2345);

		//On lui affecte les données à envoyer
		packet.setData(donnees);

		//On envoie au serveur
		client.send(packet);		

		client.close();
	}

	/**
	 * Créé le fichier pipe de reception, et enclenche un envoi de données dès qu'il y
	 * a suffisament à envoyer
	 */
	@Override
	public void run() {
		//Chaine de commande pour créer le pipe
		String cmdCreationPipe = "mkfifo /tmp/pipeReception" + VideoWatcher.name + VideoWatcher.IDENTIFIANT;

		ProcessBuilder pbPipe = new ProcessBuilder(cmdCreationPipe.split("\\s+"));
		try {
			pPipe = pbPipe.start();
			pPipe.waitFor();
			pipePret = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pipePret = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pipePret = false;
		}

		if (pipePret == true)	//Si le process a bien démarré
		{
			FileInputStream recuPipe;
			try {
				recuPipe = new FileInputStream(new File("/tmp/pipeReception" + VideoWatcher.name + VideoWatcher.IDENTIFIANT));

				while (!RecorderFFMPEG.running)
				{}
				while (RecorderFFMPEG.running){
					try {
						if (recuPipe.available() > taillePaquet)	//Si on a des données de la taille d'un paquet dans le pipe
						{	
							byte[] donneesVideo = new byte[taillePaquet];
							recuPipe.read(donneesVideo, 0, taillePaquet);

							envoiPaquet(donneesVideo,taillePaquet);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				try {
					if (recuPipe.available() != 0)
					{
						int sizeData = recuPipe.available();
						byte[] donneesVideo = new byte[sizeData];
						recuPipe.read(donneesVideo, 0, sizeData);	
						envoiPaquet(donneesVideo, sizeData);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			finally
			{
				//Suppresion du fichier
				File f = new File("/tmp/pipeReception"+VideoWatcher.name.toUpperCase());
				f.delete();
				//Toutes les actions du client sont terminées, on envoie un message au serveur pour lui dire
				//qu'on peut supprimer ce client
				try {
					envoiPaquetFinClient();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
	}
}
