import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

public class fluxClient extends Thread{

	//A déterminer ce que l'on prendra exactement
	String nomClient;
	String matiere;
	FileOutputStream sortieVideo;

	public fluxClient(String NC, String M) throws FileNotFoundException
	{
		nomClient = NC;
		matiere = M;
		sortieVideo = new FileOutputStream("streamClient" + nomClient + ".ts");
	}

	/**
	 * Ajoute le contenu du paquet à la suite de la vidéo du client
	 * @param paquet
	 */
	public void ajoutElementVideo(DatagramPacket paquet){
		try
		{
			System.out.println("Ajout dans la vidéo du client: " + nomClient);
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
