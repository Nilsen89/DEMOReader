package reader;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

import org.xerial.snappy.Snappy;

public class DEMOReader {

	byte[] compressed, decompressed;
	byte[] data = new byte[1];
	
	private String header; 				//8 characters, should be "HL2DEMO"+NULL
	private int demoProtocol;			//Demo protocol version
	private int	networkProtocol;		//Network protocol version number
	private String serverName;			//260 characters long
	private String clientName;			//260 characters long
	private String mapName;				//260 characters long
	private String gameDirectory;		//260 characters long
	private float playbackTime;			//The length of the demo, in seconds
	private int	ficks;					//The number of ticks in the demo
	private int	frames;					//The number of frames in the demo
	private int	signOnLength;			//Length of the sign on data (Init for first frame)
	
	public DEMOReader() {}
	
	public void readDemo() throws IOException {
			FileInputStream fis = new FileInputStream("src/reader/demo2.dem");
			DataInputStream dis = new DataInputStream(fis);
			
			fetchHeader(dis);
			System.out.println("Header: "+header);
			
			fetchDemoProtocol(dis);
			System.out.println("Demo Protocol: "+demoProtocol);
			
			fetchNetworkProtocol(dis);
			System.out.println("Network Protocol: "+networkProtocol);
			
			fetchServerName(dis);
			System.out.println("Server name: "+serverName);
			
			fetchClientName(dis);
			System.out.println("Client name: "+clientName);
			
			fetchMapName(dis);
			System.out.println("Map name: "+mapName);
			
			fetchGameDirectory(dis);
			System.out.println("Game directory: "+gameDirectory);
			
			//fetchPlaybackTime(dis);
			//System.out.println(playbackTime);
			
			tester(dis, true, 5000, true);
	}
	
	
	public void fetchHeader(DataInputStream dis) throws IOException {
		String tempBuffer = "";
		int readPos = 0;
		while(readPos++ < 12) {
			dis.read(data);
			if(data[0] > 47) {				
				tempBuffer += (new String(data, "ISO-8859-1"));
			}
		}
		this.header = tempBuffer;
	}
	
	public void fetchDemoProtocol(DataInputStream dis) throws IOException {
		dis.read(data);
		if(data[0] < 47) {
			demoProtocol = -1;
		} else {			
			this.demoProtocol = Integer.parseInt(new String(data, "ISO-8859-1"));
		}
	}
	
	public void fetchNetworkProtocol(DataInputStream dis) throws IOException {
		dis.read(data);
		this.networkProtocol = Integer.parseInt(new String(data, "ISO-8859-1"));
	}
	
	public void fetchClientName(DataInputStream dis) throws IOException {
		int readPos = 0;
		String tempBuffer = "";
		while(readPos++ < 260) {
			dis.read(data);
			if(data[0] > 47) {				
				tempBuffer += (new String(data, "ISO-8859-1"));
			}
		}
		this.clientName = tempBuffer;
	}
	
	public void fetchServerName(DataInputStream dis) throws IOException {
		int readPos = 0;
		String tempBuffer = "";
		while(readPos++ < 260) {
			dis.read(data);
			if(data[0] > 47) {				
				tempBuffer += (new String(data, "ISO-8859-1"));
			}
		}
		this.serverName = tempBuffer;
	}
	
	public void fetchMapName(DataInputStream dis) throws IOException {
		int readPos = 0;
		String tempBuffer = "";
		while(readPos++ < 260) {
			dis.read(data);
			if(data[0] > 47) {				
				tempBuffer += (new String(data, "ISO-8859-1"));
			}
		}
		this.mapName = tempBuffer;
	}
	
	public void fetchGameDirectory(DataInputStream dis) throws IOException {
		int readPos = 0;
		String tempBuffer = "";
		while(readPos++ < 260) {
			dis.read(data);
			if(data[0] > 47) {				
				tempBuffer += (new String(data, "ISO-8859-1"));
			}
		}
		this.gameDirectory = tempBuffer;
	}
	
	public void fetchPlaybackTime(DataInputStream dis) throws IOException {
		int readPos = 0;
		String tempBuffer = "";
		while(readPos++ < 4) {
			dis.read(data);
			if(data[0] > 47) {
				tempBuffer += (new String(data, "ISO-8859-1"));
			}
		}
		this.playbackTime = Float.parseFloat(tempBuffer);
	}
	
	public void tester(DataInputStream dis, boolean filter, int testLimit, boolean decompress) throws IOException {
		
		int testCount = 0;
		String uncompressed;
		
		while(testCount++ < testLimit) {
			dis.read(data);
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
	
	public static void main(String[] args) throws IOException {
		DEMOReader demo = new DEMOReader();
		demo.readDemo();
	}
}