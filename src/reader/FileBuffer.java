package reader;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileBuffer {
	
	DataInputStream buffer;
	
	public FileBuffer(String url) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(url);
		buffer = new DataInputStream(fis);
	}
	
	public DataInputStream getBuffer() {
		return buffer;
	}
}
