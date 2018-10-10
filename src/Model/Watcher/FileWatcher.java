package Model.Watcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.List;

import org.json.simple.JsonObject;

import Model.EtudiantExamenInfo;

public class FileWatcher extends Watcher {

	static String TYPE = "FILE";

	public FileWatcher() {
		super(TYPE);
	}

	protected JsonObject createDataBeforeSendEvent(EtudiantExamenInfo etudiantExamenInfo) {
		JsonObject datas = new JsonObject();
		datas.put("type", "ajout");
		return datas;
	}

	@Override
	public void run() {
        //define a folder root
        Path myDir = Paths.get(System.getProperty("user.home"));

        while (true) {

            try {
               WatchService watcher = myDir.getFileSystem().newWatchService();
               myDir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

               WatchKey watckKey = watcher.take();

               List<WatchEvent<?>> events = watckKey.pollEvents();
               for (WatchEvent event : events) {
                    if (event.kind() == ENTRY_CREATE) {
                        System.out.println("Created: " + event.context().toString());
                    }
                    if (event.kind() == ENTRY_DELETE) {
                        System.out.println("Delete: " + event.context().toString());
                    }
                    if (event.kind() == ENTRY_MODIFY) {
                        System.out.println("Modify: " + event.context().toString());
                    }
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.toString());
            }
        }
	}

}
