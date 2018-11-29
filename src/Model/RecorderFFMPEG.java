package Model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import Model.Watcher.VideoWatcher;


public class RecorderFFMPEG extends Thread{
	private Process ffmpeg;		
	public static boolean running;
	private int largeur, hauteur;

	public RecorderFFMPEG(int w, int h)
	{
		ffmpeg = null;
		largeur = w;
		hauteur = h;
	}

	/**
	 * Fonction se chargeant de lancer la commande ffmpeg depuis l'appli Java puis de l'envoie dans le Pipe
	 * @author Bastien et Anaïs
	 */
	public void run()
	{				
		try {			
			String cmd = "ffmpeg -video_size " + largeur + "x" + hauteur + " -framerate 5 -f x11grab -i :0 -f mpegts pipe:1";

			//Permet de lancer la commande depuis l'application Java
			ProcessBuilder procFF = new ProcessBuilder(cmd.split("\\s+"));
			
			//On envoie tout sur le pipe créé
			procFF.redirectOutput(new File("/tmp/pipeReception" + VideoWatcher.name + VideoWatcher.IDENTIFIANT));

			try {
				ffmpeg = procFF.start();
				running = true;
				ffmpeg.waitFor();
				running = false;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * Envoie la lettre q au processus afin d'ordonner l'arrêt de ffmpeg par l'utilisateur
	 * @author Anaïs
	 */
	public void stopRecord()
	{
		if (ffmpeg != null)
		{
			BufferedOutputStream bos = new BufferedOutputStream(ffmpeg.getOutputStream());
			try
			{
				bos.write(new String("q").getBytes());
				bos.flush();

				bos.close();
			}
			catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				running = false;
			}

		}
	}

}

