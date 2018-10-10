package Model.Watcher;
import org.json.simple.JsonObject;

import Model.EtudiantExamenInfo;

public class UsbWatcher extends Watcher {

	static String TYPE = "USB";

	public UsbWatcher() {
		super(TYPE);
	}

	protected JsonObject createDataBeforeSendEvent(EtudiantExamenInfo etudiantExamenInfo) {
		JsonObject datas = new JsonObject();
		datas.put("prenom", etudiantExamenInfo.getPrenomEtudiant());
		return datas;
	}

}
