package Model.Watcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Controller.Main;

/**
 * Envoie une alerte si un périphérique usb est connecté ou déconnecté
 */
public class UsbWatcher extends Watcher {
	static String TYPE="USB";

	public UsbWatcher() {
		super(TYPE);
	}

	@Override
	public void run() {
		// Ligne de commande qui renvoie le nombre de clés usb de type mass branchées 
		String cmd="lsusb -t | grep Class=Mass | wc -l";
		ProcessBuilder pb=new ProcessBuilder("/bin/bash", "-c", cmd);
		int n_usb=-1;
		while(Main.surveillanceEnCours) {
			try {
				Process usb=pb.start();
				usb.waitFor();
				BufferedReader reader=new BufferedReader(new InputStreamReader(usb.getInputStream()));
				StringBuilder builder=new StringBuilder();
				String line=null;

				while((line=reader.readLine())!=null) builder.append(line);
				int result=Integer.parseInt(builder.toString());
				if(n_usb==-1) n_usb=result;
				else {
					if(n_usb<result) {
						String information="nom_usb";
						createDataBeforeSendEvent("connexion_usb", information);						
					} else if(n_usb>result) {
						String information="nom_usb";
						createDataBeforeSendEvent("deconnexion_usb", information);	
					}
					n_usb=result;
				}
				Thread.sleep(500);
			} catch(IOException | InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}