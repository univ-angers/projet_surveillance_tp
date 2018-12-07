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
import java.util.List;
import Controller.Main;


/**
 * Permet de détecter des créations, modifications et suppressions de fichier
 * sur l'ensemble de l'ordinateur
 */
public class FileWatcher extends Watcher {

	static String TYPE = "FILE";
    private final WatchService watcher;

	public FileWatcher(Path cheminInitial) throws IOException {
		super(TYPE);
		this.watcher = FileSystems.getDefault().newWatchService();
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
	
	/**
	 * Va ajouter à la surveillance tous les fichiers et sous-dossiers à partir
	 * du chemin initial
	 * Envoie une alerte dès qu'un élément change
	 */
	@Override
	public void run() {
		while (Main.surveillanceEnCours) {        
			try {				
				WatchKey watckKey = watcher.take();       
				List<WatchEvent<?>> events = watckKey.pollEvents();
				for (WatchEvent event : events) {
					if (event.kind() == ENTRY_CREATE) {
						String information = event.context().toString();
						createDataBeforeSendEvent("creation_fichier", information);
					}
					if (event.kind() == ENTRY_DELETE) {
						String information = event.context().toString();
						createDataBeforeSendEvent("suppression_fichier", information);
					}
					if (event.kind() == ENTRY_MODIFY) {
						String information = event.context().toString();
						createDataBeforeSendEvent("modification_fichier", information);
					}
				}          
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
