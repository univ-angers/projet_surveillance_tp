package Model.Watcher;
import org.json.simple.JsonObject;

public class UsbWatcher extends Watcher {

	static String TYPE = "USB";

	public UsbWatcher() {
		super(TYPE);
	}

	protected JsonObject createDataBeforeSendEvent() {
		JsonObject datas = new JsonObject();
		return datas;
	}

}
