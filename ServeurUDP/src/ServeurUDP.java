import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map.Entry;

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
	
	private static HashMap<Integer,ClientHandler> listeClient;

	public static void main( String args[] )
	{				
		
		/** A FAIRE
		 * -- Créer un objet Java qui contient un fileOutputStream et le nom du client par ex (?)
		 * Par rapport à l'origine du client, on envvera les paquets vers ces objets
		 * qui rajouteront les éléments aux stream de ces objets
		 * -- Gérer plusieurs client
		 */
		listeClient = new HashMap<Integer, ClientHandler>();
		
		Thread t = new Thread(new Runnable(){
			public void run(){
				int i = 0;
				try
				{
					// Socket du serveur
					DatagramSocket socketServeur = new DatagramSocket( port ) ;

					System.out.println( "Le serveur est prêt." ) ;


					byte[] donneesRecues = new byte[4096];
					DatagramPacket paquetReception = new DatagramPacket( donneesRecues, donneesRecues.length ) ;
					while(true)
					{
						//Si on reçoit un paquet
						socketServeur.receive( paquetReception );
						
						System.out.println("Un nouveau client s'est connecté");
						
						String donnees = new String(paquetReception.getData());
						//NOUVEAU:name:session:port
						String[] resultat = donnees.split(":");
						
						System.out.println("DEBUG: Nom = " + resultat[1]);
						System.out.println("DEBUG: Matiere = " + resultat[2]);
						System.out.println("DEBUG: Port = " + resultat[3]);
						int portPropose = Integer.parseInt(resultat[3]);
						int cle;
						
						boolean portDispo = true;
						for (Entry<Integer, ClientHandler> entry : listeClient.entrySet())
						{
							cle = entry.getKey();
							if (cle == portPropose)
							{
								portDispo = false;
							}
						}
						
						if (portDispo)	//Creation d'un client avec le port et renvoi que c'est OK
						{
							ClientHandler client = new ClientHandler(resultat[1],resultat[2],portPropose);
							listeClient.put(portPropose, client);
							
							byte[] reponsePositive = new String("OK").getBytes();
							DatagramPacket reponse = new DatagramPacket(reponsePositive, reponsePositive.length,paquetReception.getAddress(),paquetReception.getPort());
							
							//Et on envoie vers l'émetteur du datagramme reçu précédemment
							socketServeur.send(reponse);							
						}
						else	//Renvoi que le port est déjà utilsé, besoin d'une nouvelle possibilité de port
						{
							byte[] reponseNegative = new String("NON").getBytes();
							DatagramPacket reponse = new DatagramPacket(reponseNegative, reponseNegative.length,paquetReception.getAddress(),paquetReception.getPort());
							
							//Et on envoie vers l'émetteur du datagramme reçu précédemment
							socketServeur.send(reponse);
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
