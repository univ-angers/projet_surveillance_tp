package Model.Watcher;


import Model.EtudiantExamenInfoSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import Controller.Main;

/**
 * Watcher qui va envoyer une alerte si une requête HTTP contient un host suspect / non toléré
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * OBLIGATION DE LANCER EN SUDO 
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * Basé sur le tutoriel: https://crunchify.com/how-to-execute-tcpdump-linux-command-using-java-process-class-and-capture-tcpip-packets/
 * @author Bastien
 *
 */
public class NetworkWatcher extends Watcher{

	static String TYPE = "NET";

	EtudiantExamenInfoSingleton etudiant;

	public NetworkWatcher() {
		super(TYPE);
		// TODO Auto-generated constructor stub
	}


	// HTTP: sudo tcpdump -s 0 -A 'tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'
	@Override
	/**
	 * Lance la commande tcpdump, récupère les informations, et va lancer la vérification
	 */
	public void run()
	{
		System.out.println("Net en marche");

		try {			

			//String tcpdumpCmd = "/usr/sbin/tcpdump -c 2 -v -A dst port 80";	//A changer
			String tcpdumpCmd = "sudo /usr/sbin/tcpdump -s 0 -i any -A 'tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'";	//Ce qu'on veut
			ProcessBuilder PB = null;

			PB = new ProcessBuilder("/bin/bash", "-c", tcpdumpCmd);
			PB.redirectErrorStream(true);


			try {
				Process process = PB.start();
				if (Main.surveillanceEnCours) {
					InputStream inStream = process.getInputStream();

					if (inStream != null) {
						Writer resultatTcpDump = new StringWriter();

						char[] Buffer = new char[2048];
						try {
							Reader tcpDumpReader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
							int count;

							int compteurRetourChariot = 0;
							while ((count = tcpDumpReader.read(Buffer)) != -1) 
							{
								
								resultatTcpDump.write(Buffer, 0, count);

								// ICI ON VA CHECK SI Y A UN TRUC SUSPECT
								//IL FAUT RECUPERER UNIQUEMENT LA PARTIE APRES HOST
								System.out.println(resultatTcpDump.toString());
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							try {
								inStream.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} 
				} 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				//System.out.println();
			}

		} finally {
			//System.out.println();
		}
	}
}