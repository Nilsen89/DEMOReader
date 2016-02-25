package reader;

import java.io.DataInputStream;
import java.io.IOException;

import org.xerial.snappy.Snappy;

public class DEMOHeader {

	public byte[] data;
	
	public static String header; 			//8 characters, should be "HL2DEMO"+NULL
	public static int demoProtocol;			//Demo protocol version
	public static int networkProtocol;		//Network protocol version number
	public static String serverName;		//260 characters long
	public static String clientName;		//260 characters long
	public static String mapName;			//260 characters long
	public static String gameDirectory;		//260 characters long
	public static float playbackTime;		//The length of the demo, in seconds
	public static int ticks;				//The number of ticks in the demo
	public static int frames;				//The number of frames in the demo
	public static int signOnLength;			//Length of the sign on data (Init for first frame)
	
	public DEMOHeader() {}
	
	public void parseHeader(DataInputStream buffer) throws IOException {
			
			System.out.println("-----Parsing Header------");
			
			header = Readers.readString(buffer, Readers.HEADERLEN);
			demoProtocol = Readers.readInt(buffer);
			networkProtocol = Readers.readInt(buffer);
			serverName = Readers.readString(buffer, Readers.STRINGLEN);
			clientName = Readers.readString(buffer, Readers.STRINGLEN);
			mapName = Readers.readString(buffer, Readers.STRINGLEN);
			gameDirectory = Readers.readString(buffer, Readers.STRINGLEN);
			playbackTime = Readers.readFloat(buffer);
			ticks = Readers.readInt(buffer);
			frames = Readers.readInt(buffer);
			signOnLength = Readers.readInt(buffer);
			
			System.out.println("HEADER: "+header);
			System.out.println("DEMOPROTOCOL: "+demoProtocol);
			System.out.println("NETWORKPROTOCOL: "+networkProtocol);
			System.out.println("SERVERNAME: "+serverName);
			System.out.println("CLIENTNAME: "+clientName);
			System.out.println("MAPNAME: "+mapName);
			System.out.println("GAMEDIR: "+gameDirectory);
			System.out.println("PLAYBACKTIME: "+playbackTime);
			System.out.println("TICKS: "+ticks);
			System.out.println("FRAMES: "+frames);
			System.out.println("SIGNONLENGTH: "+signOnLength);
			
			//tester(buffer, true, 2000, false);
	}
	
	public void tester(DataInputStream buffer, boolean filter, int testLimit, boolean decompress) throws IOException {
		
		int testCount = 0;
		int test;
		String uncompressed;
		data = new byte[1];
		while(testCount++ < testLimit) {
			buffer.read(data);
			if (Snappy.isValidCompressedBuffer(data) && decompress) {
				uncompressed = Snappy.uncompressString(data);
				System.out.println(uncompressed);
			} else if(filter) {
				if(data[0] == 10 || data[0] > 47) {				
					System.out.print(new String(data, "ISO-8859-1"));
				}
			} else {
				System.out.print(new String(data, "ISO-8859-1"));
			}
		}
	}
}