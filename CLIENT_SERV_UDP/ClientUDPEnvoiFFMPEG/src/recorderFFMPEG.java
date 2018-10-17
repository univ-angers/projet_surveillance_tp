import java.io.File;
import java.io.IOException;

public class recorderFFMPEG extends Thread{

	////////
	private int tempsTemporaire;
	private String addresseDestinataire;
	private int portDest;
	private Process ffmpeg;		
	private boolean running;
	private int largeur, hauteur;

	public recorderFFMPEG(int w, int h, int temps, String adresseDest, int port){
		ffmpeg = null;
		largeur = w;
		hauteur = h;
		tempsTemporaire = temps;	//sera supprimé à terme
		addresseDestinataire = adresseDest;
		portDest = port;
	}
	//////////////


	public void run()
	{				
		try {
			//Chaine de commande pour filmer l'écran et l'envoyer au serveur
			//CMD A REVOIR POUR L'ENVOYER VERS UN PIPE
			String cmdCreationPipe = "mkfifo /tmp/pipeReception";
			ProcessBuilder procPipe = new ProcessBuilder(cmdCreationPipe.split("\\s+"));
			try {
				procPipe.start();
				System.out.println("Pipe créé.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//String cmd = "ffmpeg -video_size " + largeur + "x" + hauteur + " -framerate 5 -f x11grab -i :0 -t 10 -f avi pipe:1 > /tmp/pipeReception";
			String cmd = "ffmpeg -video_size " + largeur + "x" + hauteur + " -framerate 5 -f x11grab -i :0 -t 10 -f avi pipe:1 > /tmp/pipeReception";
			
			//Console pour envoi sur PC: ffmpeg -f x11grab -r 25 -s 1000x700 -framerate 25 -f x11grab -i :0.0+0 -vcodec libx264 -tune zerolatency -t 3 ~/superTest.mp4";
			
			//Permet de lancer la commande depuis l'application Java
			ProcessBuilder procFF = new ProcessBuilder(cmd.split("\\s+"));
			
			
			try {
				System.out.println("Démarrage de l'enregistrement");
				ffmpeg = procFF.start();
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

