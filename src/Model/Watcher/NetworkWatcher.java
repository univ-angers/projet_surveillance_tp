package Model.Watcher;


import Model.EtudiantExamenInfoSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetworkWatcher extends Watcher{

	/* TCPDUMP
	 * HTTP: sudo tcpdump -s 0 -A 'tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'
	 * HTTPS: ?
	 */

	static String TYPE = "NET";

	public NetworkWatcher(EtudiantExamenInfoSingleton etud) {
		super(TYPE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run()
	{
		System.out.println("Net en marche");

		try {			
			
			String[] tcpdumpCmd = {"tcpdump", "-i", "any", "port 80"};	//A changer
			/* HTTP: sudo tcpdump -s 0 -A 'tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'*/
			Process p = new ProcessBuilder(tcpdumpCmd).start();

			while (true)
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String tcpdumpOut;

				tcpdumpOut = null;
				int tcpdumpLineCnt = 0;
				while ((tcpdumpOut = in.readLine()) != null ) {
					System.out.println("LIGNE " + tcpdumpOut);
					System.out.println(tcpdumpOut);
					tcpdumpLineCnt++;
				}

				System.out.println("Output lines: " + tcpdumpLineCnt+"\nCommand exit code: " + p.exitValue());
			}
			//System.out.println("Fin du net.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{}
	}
}