package Model.Watcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.EtudiantExamenInfoSingleton;

/**
 * A FAIRE
 * -- Corriger les bugs sur la récurisivité: les fichiers ne sont pas toujours détectés
 * @author Riad et Bastien
 *
 */
public class FileWatcher extends Watcher {

	static String TYPE = "FILE";
    private final WatchService watcher;
    private final Map<WatchKey,Path> keys;

	public FileWatcher(EtudiantExamenInfoSingleton etud, Path cheminInitial) throws IOException {
		super(TYPE);
		this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey,Path>();
        registerAll(cheminInitial);
	}

	/**
	 * Met le dossier et tous ses sous dossiers dans le watcher
	 * Source1: https://stackoverflow.com/questions/16611426/monitor-subfolders-with-a-java-watch-service
	 * Source2: https://stackoverflow.com/questions/49803363/how-to-ignore-accessdeniedexceptions-when-using-files-walkfiletree
	 */
	private void registerAll(final Path start) throws IOException {
	    // register directory and sub-directories
	    Files.walkFileTree(start, new SimpleFileVisitor<Path>() {	
	    	@Override
	        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
	            return FileVisitResult.CONTINUE;
	        }

	        @Override
	        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
	            if (exc instanceof AccessDeniedException) {
	                return FileVisitResult.SKIP_SUBTREE;
	            }

	            return super.visitFileFailed(file, exc);
	        }
	    });
	}
	
	@Override
	public void run() {
		while (true) {        
			try {				
				WatchKey watckKey = watcher.take();       
				List<WatchEvent<?>> events = watckKey.pollEvents();
				for (WatchEvent event : events) {
					if (event.kind() == ENTRY_CREATE) {
						//System.out.println("DEBUG: Créé: " + event.context().toString());
						String information = "Creation fichier: " + event.context().toString();
						createDataBeforeSendEvent(information,"normal");
					}
					if (event.kind() == ENTRY_DELETE) {
						//System.out.println("DEBUG: Supprimé: " + event.context().toString());
						String information = "Suppression fichier: " + event.context().toString();
						createDataBeforeSendEvent(information,"normal");
					}
					if (event.kind() == ENTRY_MODIFY) {
						//System.out.println("DEBUG: Modifié: " + event.context().toString());
						String information = "Modification fichier: " + event.context().toString();
						createDataBeforeSendEvent(information,"normal");
					}
				}          
			} catch (Exception e) {
				System.out.println("Error: " + e.toString());
			}
		}
	}
}
