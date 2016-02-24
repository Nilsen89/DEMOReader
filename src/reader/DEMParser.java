package reader;

import java.io.DataInputStream;
import java.io.IOException;

public class DEMParser {
	
	public static final int DEM_SIGNON = 1;
	public static final int DEM_PACKET = 2;
	public static final int DEM_SYNCTICK = 3;
	public static final int DEM_CONSOLECMD = 4;
	public static final int DEM_USERCMD = 5;
	public static final int DEM_DATATABLES = 6;
	public static final int DEM_STOP = 7;
	public static final int DEM_LASTCOMMAND = DEM_STOP;
	
	public byte[] command;
	public int tick;
	
	public DataInputStream buffer;
	
	public DEMParser(DataInputStream buffer) {
		this.buffer = buffer;
	}
	
	//http://forum.xentax.com/viewtopic.php?f=36&t=13388
	public void checkCommand(DataInputStream buffer) throws IOException {
		System.out.println("-----CHECKING COMMAND------");
		do {
			command = new byte[1];
			buffer.read(command);
			if(command[0] == DEM_LASTCOMMAND) {
				System.out.println("Last command");
				break;
			}
			tick = buffer.readInt();
			if(command[0] == DEM_SIGNON) {
				buffer.skipBytes(DEMOReader.signOnLength);
				System.out.println("Skipped");
			} else if(command[0] == DEM_PACKET) {
				buffer.skipBytes(4);
			}
			System.out.println("Command: "+command[0]);
		} while (command[0] != DEM_LASTCOMMAND);
	}
}
