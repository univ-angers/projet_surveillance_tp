package Model.Watcher;


import Model.EtudiantExamenInfoSingleton;
import Model.PackageCapture;

public class NetworkWatcher extends Watcher{

	static String TYPE = "NET";

	public NetworkWatcher(EtudiantExamenInfoSingleton etud) {
		super(TYPE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run()
	{
			PackageCapture NetCheck = new PackageCapture();
			NetCheck.run();
	}

}
