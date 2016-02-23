package reader;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.xerial.snappy.Snappy;

public class DEMOReader {

	public byte[] data;
	
	public static final int HEADERLEN = 8;
	public static final int STRINGLEN = 260;
	public static final int FLOATLEN = 4;
	public static final int INTLEN = 4;
	
	private String header; 				//8 characters, should be "HL2DEMO"+NULL
	private int demoProtocol;			//Demo protocol version
	private int	networkProtocol;		//Network protocol version number
	private String serverName;			//260 characters long
	private String clientName;			//260 characters long
	private String mapName;				//260 characters long
	private String gameDirectory;		//260 characters long
	private float playbackTime;			//The length of the demo, in seconds
	private int	ticks;					//The number of ticks in the demo
	private int	frames;					//The number of frames in the demo
	private int	signOnLength;			//Length of the sign on data (Init for first frame)
	
	public DEMOReader(String url) throws IOException {
		FileInputStream fis = new FileInputStream(url);
		DataInputStream buffer = new DataInputStream(fis);
		
		readDemo(buffer);
	}
	
	public void readDemo(DataInputStream buffer) throws IOException {
			
			header = readString(buffer, HEADERLEN);
			demoProtocol = readInt(buffer);
			networkProtocol = readInt(buffer);
			serverName = readString(buffer, STRINGLEN);
			clientName = readString(buffer, STRINGLEN);
			mapName = readString(buffer, STRINGLEN);
			gameDirectory = readString(buffer, STRINGLEN);
			playbackTime = readFloat(buffer);
			ticks = readInt(buffer);
			frames = readInt(buffer);
			signOnLength = readInt(buffer);
		
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
			
			//tester(buffer, false, 2000, false);
	}

	public String readString(DataInputStream buffer, int len) throws IOException {
		data = new byte[len];
		buffer.read(data, 0, len);
		return (new String(data, "ISO-8859-1"));
	}
	
	public int readInt(DataInputStream buffer) throws IOException {
		return Integer.reverseBytes(buffer.readInt());
	}
	
	public float readFloat(DataInputStream buffer) throws IOException {
		return Float.intBitsToFloat(Integer.reverseBytes(buffer.readInt()));
	}
	
	public void tester(DataInputStream buffer, boolean filter, int testLimit, boolean decompress) throws IOException {
		
		int testCount = 0;
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
				System.out.print(data[0]+" ");
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		DEMOReader demo = new DEMOReader("src/reader/demo.dem");
	}
}