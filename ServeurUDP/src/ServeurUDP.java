import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Classe recevant les demandes de connexion au serveur vidéo, qui va indiquer
 * au client quel port spécifique utiliser pour communiquer
 */
public class ServeurUDP {
	public final static int port = 2345;
	private static HashMap<Integer,ClientHandler> listeClient;	
	
	public static void main( String args[] )
	{				
		listeClient = new HashMap<Integer, ClientHandler>();

		Thread t = new Thread(new Runnable(){
			public void run(){
				try
				{					
					// Socket du serveur
					DatagramSocket socketServeur = new DatagramSocket( port ) ;

					while(true)
					{
						byte[] donneesRecues = new byte[4096];
						DatagramPacket paquetReception = new DatagramPacket( donneesRecues, donneesRecues.length );

						//Si on reçoit un paquet
						socketServeur.receive( paquetReception );

						String donnees = new String(paquetReception.getData());
						
						//Deux types de données
						//NOUVEAU:name:session si nouveau client
						//FIN:name pour la fermeture d'un client existant
						String[] resultat = donnees.split(":");

						if (resultat[0].trim().equals("NOUVEAU"))
						{
							int portPropose = 1024 + (int)(Math.random() * (20000-1024));

							int cle;
							boolean portDispo = true;

							do
							{
								if (portPropose == 2345)	//Port d'écoute principal
									portDispo = false;
								else
								{	//Verification que le port proposé n'est pas déjà utilisé
									for (Entry<Integer, ClientHandler> entry : listeClient.entrySet())
									{
										cle = entry.getKey();
										if (cle == portPropose)
										{
											portDispo = false;
											break;
										}
									}
								}
							}while (portDispo == false);

							if (portDispo)	//Creation d'un client avec le port et renvoi que c'est OK
							{
								ClientHandler client = new ClientHandler(resultat[1].trim(),resultat[2].trim(),portPropose);
								listeClient.put(portPropose, client);

								client.start();

								byte[] reponsePositive = new String("OK:" + portPropose).getBytes();
								DatagramPacket reponse = new DatagramPacket(reponsePositive, reponsePositive.length,paquetReception.getAddress(),paquetReception.getPort());

								//Et on envoie vers l'émetteur du datagramme reçu précédemment
								socketServeur.send(reponse);
							}	
						}
						if (resultat[0].trim().equals("FIN"))
						{
							int portArret = Integer.parseInt(resultat[1].trim());

							//On supprime le client au port indiqué
							for (Entry<Integer, ClientHandler> entry : listeClient.entrySet())
							{
								int cle = entry.getKey();
								if (cle == portArret)
								{
									listeClient.get(cle).stopSocket();
									listeClient.remove(cle);
									break;
								}
							}
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
