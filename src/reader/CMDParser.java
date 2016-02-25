package reader;

import java.io.DataInputStream;
import java.io.IOException;

public class CMDParser {

	public CMDParser() {}
	
	public static int packetSize;
	
	public static void parsePacket(DataInputStream buffer) throws IOException {
		// TODO Auto-generated method stub
		buffer.skip(160);
		Readers.position += 160;
		
		packetSize = Readers.readInt(buffer);
		int stopper = packetSize + Readers.position;
		
		System.out.println("RUNNING: parsePacket");
		System.out.println("packetSize: "+packetSize);
		
		while(Readers.position < stopper) {
			
		}
		
	}
	
	public static void parseConsoleCMD(DataInputStream buffer) {
		// TODO Auto-generated method stub
		System.out.println("RUNNING: parseConsoleCMD");
	}
	
	public static void parseUserCMD(DataInputStream buffer) {
		// TODO Auto-generated method stub
		System.out.println("RUNNING: parseUserCMD");
	}
	
	public static void parseDataTables(DataInputStream buffer) {
		// TODO Auto-generated method stub
		System.out.println("RUNNING: parseDataTables");
	}

	public static void parseSyncTick(DataInputStream buffer) {
		// TODO Auto-generated method stub
		System.out.println("RUNNING: parseSyncTick");
	}
}
