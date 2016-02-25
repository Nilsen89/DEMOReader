package reader;

import java.io.IOException;

public class Executer {
	public static void main(String[] args) throws IOException {
		FileBuffer file = new FileBuffer("src/reader/demo.dem");
		DEMOHeader demoReader = new DEMOHeader();
		DEMParser demParser = new DEMParser(file.getBuffer());
		
		demoReader.parseHeader(file.getBuffer());
		demParser.checkCommand(file.getBuffer());
	}
}