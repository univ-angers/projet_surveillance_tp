import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Classe qui correspond à la connexion à un client
 * Chaque client va communiquer avec le serveur par un port précis qui lui est attribué
 * @author etudiant
 *
 */
public class ClientHandler extends Thread{

	//A déterminer ce que l'on prendra exactement
	String nomClient;
	String matiere;
	int port;
	FileOutputStream sortieVideo;

	public ClientHandler(String NC, String M, int P) throws FileNotFoundException
	{
		nomClient = NC;
		matiere = M;
		port = P;
		sortieVideo = new FileOutputStream("streamClient" + nomClient.toUpperCase() + matiere.toUpperCase() + ".surv");
	}

	public void run()
	{
		// Socket du serveur
		try {
			System.out.println( "Le serveur écoute le client " + nomClient + " sur le port " + port + "." ) ;

			DatagramSocket socketSpecialClient = new DatagramSocket( port ) ;
			byte[] receptionVideo = new byte[2048];

			while(true){                 				
				DatagramPacket paquetVideo = new DatagramPacket(receptionVideo, receptionVideo.length);
				try {
					socketSpecialClient.receive(paquetVideo);

						//System.out.println("DEBUG: Client: " + nomClient + ", Port ecoute: "+ port + ", Port reception: " + paquetVideo.getPort());
						ajoutElementVideo(paquetVideo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * Ajoute le contenu du paquet à la suite de la vidéo du client
	 * @param paquet
	 */
	public void ajoutElementVideo(DatagramPacket paquet){
		try
		{
			//On l'écrit dans la flux
			try {
				sortieVideo.write(paquet.getData());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		finally
		{
			//?
		}

	}
}
