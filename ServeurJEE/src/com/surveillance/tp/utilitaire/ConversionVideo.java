package com.surveillance.tp.utilitaire;

import java.io.File;
import java.io.IOException;

/**
 * Classe récupération la vidéo au format MPEG-TS et la convertit en MP4 en l'envoyant
 * vers le dossier du serveur Web, permettant son affichage
 */
public class ConversionVideo implements Runnable {
	private String cheminSource;
	private String cheminDest;

	public ConversionVideo(String s, String d) {
		cheminSource=s;
		cheminDest=d;
	}

	@Override
	public void run() {
		try {			
			File f=new File(cheminDest+"assets/img/videos/output.mp4");
			if(f.exists()) f.delete();
			// Conversion en mp4+envoi vers le serveur web
			String cmd="ffmpeg -i "+cheminSource+" -c:v libx264 -c:a copy -bsf:a aac_adtstoasc "+cheminDest+"assets/img/videos/output.mp4";				
			// Permet de lancer la commande depuis l'application Java
			ProcessBuilder procFF=new ProcessBuilder(cmd.split("\\s+"));
			Process ffmpeg;	
			ffmpeg=procFF.start();
			ffmpeg.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}