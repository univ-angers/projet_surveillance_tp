import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Ce serveur ne prend pour le moment en charge QU'UN SEUL flux d'entrée et UN SEUL 
 * fichier en sortie
 * Trouver comment convertir le flux en temps réel pour la sortie
 * A faire: permettre de séparer chaque entrée UDP en une vidéo de sortie chacune
 * @author Base: Anais & Bastien
 *
 */
public class ServeurUDP {

	public final static int port = 2345;
	private static int numVid = 0;

	public static void main( String args[] )
	{				
		
		/** A FAIRE
		 * Créer un objet qui contient un fileOutputStream et le nom du client par ex
		 * Par rapport à l'origine du client, on envvera les paquets vers ces objets
		 * qui rajouteront les éléments aux stream de ces objets
		 */
		Thread t = new Thread(new Runnable(){
			public void run(){
				int i = 0;
				try
				{
					//Fichier qui contiendra la vidéo en cours de création
					FileOutputStream sortieVideo = new FileOutputStream("testStream" + ++numVid + ".ts", true);

					// Construct the socket
					DatagramSocket socket = new DatagramSocket( port ) ;

					System.out.println( "Le serveur est prêt." ) ;


					byte[] donneesRecues = new byte[8192];
					DatagramPacket paquetReception = new DatagramPacket( donneesRecues, donneesRecues.length ) ;
					while(true)
					{
						//Si on reçoit un paquet
						socket.receive( paquetReception );
						try {
							System.out.println("DEBUG: Port entrée = " + paquetReception.getAddress() + ", Port entrée = " + paquetReception.getPort());
							//On l'écrit dans la flux
							sortieVideo.write(paquetReception.getData());
							i++;
							System.out.println("Paquet "+i+" reçu.");
						} finally {
							//output.close();
						}
					}  
				}catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		});  

		//Lancement du serveur
		t.start();

	}
}
