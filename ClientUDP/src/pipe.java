import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class pipe extends Thread {

	private final int taillePaquet = 2048;
	private Process pPipe;
	private boolean pipePret;

	public pipe()
	{
		pPipe = null;
	}
	
	public void envoiPaquet(byte[] donnees, int taillePaquet) throws IOException
	{
		//On initialise la connexion côté client
		DatagramSocket client = new DatagramSocket();

		//On crée notre datagramme
		InetAddress adresse = InetAddress.getByName("127.0.0.1");
		DatagramPacket packet = new DatagramPacket(donnees, taillePaquet, adresse, 2345);

		//On lui affecte les données à envoyer
		packet.setData(donnees);

		//On envoie au serveur
		client.send(packet);		
		
		client.close();
	}

	@Override
	public void run() {
		//Chaine de commande pour créer le pipe
		String cmdCreationPipe = "mkfifo /tmp/pipeReception";
		
		ProcessBuilder pbPipe = new ProcessBuilder(cmdCreationPipe.split("\\s+"));
		try {
			pPipe = pbPipe.start();
			System.out.println("Pipe créé.");
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
				recuPipe = new FileInputStream(new File("/tmp/pipeReception"));

				System.out.println("Le pipe est prêt à réceptionner des informations");

				while (recorderFFMPEG.running){
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally
			{
				System.out.println("Le pipe est maintenant fermé.");
				//Suppresion du fichier
				File f = new File("/tmp/videopipe");
				f.delete();
			}
		}
	}
}
