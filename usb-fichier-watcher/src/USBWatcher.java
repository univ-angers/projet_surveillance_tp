
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;



public class USBWatcher extends Thread {
	
	boolean enregistrer;

	
	
	public USBWatcher(){
		
		enregistrer=true;
	}
	
	
	@Override
	public void run() {
		
		enregistrer = true;
		// Ligne de commande qui renvoie le nombre de clés usb de type mass branchées 
		String cmd = "lsusb -t | grep Class=Mass | wc -l";
		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", cmd);
		
		int n_usb = -1;
		while (enregistrer) {
			try {
				Process usb = pb.start();
				
				usb.waitFor();
				BufferedReader reader = new BufferedReader(new InputStreamReader(usb.getInputStream()));
				StringBuilder builder = new StringBuilder();
				String line = null;
				while ( (line = reader.readLine()) != null) {
				   builder.append(line);
				   
				}
				int result = Integer.parseInt(builder.toString());
				
				if (n_usb == -1){
					n_usb = result;
					
				} else {
					if (n_usb < result){
						System.out.println("Connexion d'une clé USB");
						
					} else if (n_usb > result) {
						System.out.println("Deconnexion d'une clé USB");
						
					}
					n_usb = result;
				}
				Thread.sleep(500);
			} catch (IOException | InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public boolean isRecording() {
		return enregistrer;
	}

	/**
	 * Modifie l'état de l'enregistrement
	 * @param state l'état de l'enregistrement
	 */
	public void setRecording(boolean state) {
		this.enregistrer = state;
	}

	/**
	 * Permet de stopper l'enregistrement
	 */
	public void stopRecording() {
		this.enregistrer=false;
	}
	
	
	public static void main(String[] args) {
		
		
		USBWatcher u=new USBWatcher();
		u.start();
		
		
		
		// Watcher pour l'ajout, suppression et modification de fichier 
        Path folder = Paths.get(System.getProperty("user.home"));
		while (true) {        
			try {
		
            WatchService watchservice = folder.getFileSystem().newWatchService();
            folder.register(watchservice, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);  
            WatchKey watckKey = watchservice.take();       
            List<WatchEvent<?>> events = watckKey.pollEvents();
            for (WatchEvent event : events) {
                 if (event.kind() == ENTRY_CREATE) {
                     System.out.println("Créé: " + event.context().toString());
                 }
                 if (event.kind() == ENTRY_DELETE) {
                     System.out.println("Supprimé: " + event.context().toString());
                 }
                 if (event.kind() == ENTRY_MODIFY) {
                     System.out.println("Modifié: " + event.context().toString());
                 }
             }            } catch (Exception e) {
             System.out.println("Error: " + e.toString());
             
         }
     }
		
		
		
		
	}
	

}
