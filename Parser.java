import java.io.FileInputStream;
import java.io.IOException;

//////////////
// MY IMPORT

public class VertexType {
	public float x, y, z;
	
	public void VertexType() {
		x = y = z = 0.0f;
	}
}

public class Parser {
	private int vertexCount, textureCount, normalCount, faceCount;
	
	public void Parser() {
		vertexCount = textureCount = normalCount = faceCount = 0;
	}
	
	public void extract(String file) {
		ReadCounts(file);
		
		LoadData(file);
	}
	
	public void ReadCounts(String f) {
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		while(true) {
			String line = br.readLine();
			if(line == null) break;
			
			if(line.charAt(0) == 'v') {
				if(line.charAt(1) == '') vertexCount++;
				if(line.charAt(1) == 't') textureCount++;
				if(line.charAt(1) == 'n') normalCount++;
				
			} else if(line.charAt(0) == 'f') faceCount++;
		}
		
		br.close();
	}
	
	public void LoadData(String f) {
		VertexType vertices = new VertexType[vertexCount];
		VertexType texcoords = new VertexType[textureCount];
		VertexType normals = new VertexType[normalCount];
		
		// TODO : open the file and extract data we needed and then write file
	}
	
	public 
}