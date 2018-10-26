package Model;


import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;


public class PackageCapture extends Thread {

	public void run() {
		
		PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

			public void nextPacket(PcapPacket packet, String user) {

				JBuffer buffer = packet;
				byte[] arr=packet.getByteArray(0, packet.size());
				System.out.println(buffer.toHexdump());

			}

		};
	}
}