package com.surveillance.tp.utilitaire;

import java.io.File;
import java.io.IOException;

public class ConversionVideo implements Runnable{

	private String cheminSource;
	private String cheminDest;

	public ConversionVideo(String source, String dest)
	{
		this.cheminSource = source;
		this.cheminDest = dest;
	}

	@Override
	public void run() {
		try {			
			File f = new File(cheminDest + "assets/img/videos/output.mp4");
			f.delete();
			
			String cmd = "ffmpeg -i " + cheminSource + " -c:v libx264 -c:a copy -bsf:a aac_adtstoasc " + cheminDest + "assets/img/videos/output.mp4";
			
			//Permet de lancer la commande depuis l'application Java
			ProcessBuilder procFF = new ProcessBuilder(cmd.split("\\s+"));

			Process ffmpeg;	

			ffmpeg = procFF.start();
			ffmpeg.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}
}
