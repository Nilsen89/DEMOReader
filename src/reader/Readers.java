package reader;

import java.io.DataInputStream;
import java.io.IOException;

public class Readers {
	
	public static byte[] data;
	
	public static int position = 0;
	
	public static final int HEADERLEN = 8;
	public static final int STRINGLEN = 260;
	public static final int FLOATLEN = 4;
	public static final int INTLEN = 4;
	
	public Readers() {}
	
	public static String readString(DataInputStream buffer, int len) throws IOException {
		position += len;
		data = new byte[len];
		buffer.read(data, 0, len);
		return (new String(data, "ISO-8859-1"));
	}
	
	public static int readInt(DataInputStream buffer) throws IOException {
		position += 4;
		return Integer.reverseBytes(buffer.readInt());
	}
	
	public static float readFloat(DataInputStream buffer) throws IOException {
		position += 4;
		return Float.intBitsToFloat(Integer.reverseBytes(buffer.readInt()));
	}
}
