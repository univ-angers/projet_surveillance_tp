import java.io.IOException;

public class recorderFFMPEG extends Thread{

	////////
	private int tempsTemporaire;
	private String addresseDestinataire;
	private int portDest;
	private Process ffmpeg;		
	private boolean running;
	private int screen_width, screen_height;

	public recorderFFMPEG(int w, int h, int temps, String adresseDest, int port){
		ffmpeg = null;
		screen_width = w;
		screen_height = h;
		tempsTemporaire = temps;	//sera supprimé à terme
		addresseDestinataire = adresseDest;
		portDest = port;
	}
	//////////////


	public void run()
	{				
		try {
			//Chaine de commande pour filmer l'écran et l'envoyer au serveur
			String cmd = "ffmpeg -f x11grab -r 25 -s " + screen_width+"x"+screen_height+ " -framerate 25 -f x11grab -i :0.0+0 -vcodec libx264 -tune zerolatency -t " + tempsTemporaire + " -f mpegts udp://"+ addresseDestinataire + ":" + portDest;
			//Console pour envoi sur PC: ffmpeg -f x11grab -r 25 -s 1000x700 -framerate 25 -f x11grab -i :0.0+0 -vcodec libx264 -tune zerolatency -t 3 ~/superTest.mp4";
			
			//Permet de lancer la commande depuis l'application Java
			ProcessBuilder pb = new ProcessBuilder(cmd.split("\\s+"));
			
			try {
				System.out.println("Démarrage de l'enregistrement");
				ffmpeg = pb.start();
				running = true;
				System.out.println("Enregistrement en cours");
				ffmpeg.waitFor();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Fin de l'enregistrement");
		}
	}

}

