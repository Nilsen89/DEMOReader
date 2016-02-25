package reader;

import java.io.DataInputStream;
import java.io.IOException;

public class DEMParser {
	
	public static final int DEM_SIGNON = 0x01;
	public static final int DEM_PACKET = 0x02;
	public static final int DEM_SYNCTICK = 0x03;
	public static final int DEM_CONSOLECMD = 0x04;
	public static final int DEM_USERCMD = 0x05;
	public static final int DEM_DATATABLES = 0x06;
	public static final int DEM_STOP = 0x07;
	public static final int DEM_LASTCOMMAND = DEM_STOP;
	
	public byte cmd;
	public int tick;
	
	public DataInputStream buffer;
	
	public DEMParser(DataInputStream buffer) {
		this.buffer = buffer;
	}
	
	//http://forum.xentax.com/viewtopic.php?f=36&t=13388
	//https://github.com/mikeemoo/jsgo/blob/master/lib/jsgo.js
	public void checkCommand(DataInputStream buffer) throws IOException {
		System.out.println("-----CHECKING cmd------");
		do {
			cmd = buffer.readByte();
			tick = Readers.readInt(buffer);
			if(cmd != DEM_PACKET) {
				tick = 0;
			}
			
			buffer.skip(1);
			
			switch(cmd) {
				case DEM_SIGNON:
					System.out.println("SignOn Running");
				case DEM_PACKET:
					CMDParser.parsePacket(buffer);
					break;
				case DEM_SYNCTICK:
					CMDParser.parseSyncTick(buffer);
					break;
				case DEM_CONSOLECMD:
					CMDParser.parseConsoleCMD(buffer);
					break;
				case DEM_USERCMD:
					CMDParser.parseUserCMD(buffer);
					break;
				case DEM_DATATABLES:
					CMDParser.parseDataTables(buffer);
					break;
				case DEM_LASTCOMMAND:
					break;
			}
			//tester break
			break;
			
		} while (cmd != DEM_LASTCOMMAND);
	}
}
