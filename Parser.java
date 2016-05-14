package obj2F;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;

//////////////
// MY IMPORT

public class Parser {
	private int vertexCount, textureCount, normalCount, faceCount;
	
	public Parser() {
		vertexCount = textureCount = normalCount = faceCount = 0;
	}
	
	public void convert(String file) {
		try {
			ReadCounts(file);
			
			LoadData(file);
		} catch(IOException e) {
			
		}
	}
	
	public void ReadCounts(String f) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		while(true) {
			String line = br.readLine();
			if(line == null) break;
			
			// IndexOutOfBoundsException 발생가능
			try {
				if(line.charAt(0) == 'v') {
				
					if(line.charAt(1) == ' ') vertexCount++;
					if(line.charAt(1) == 't') textureCount++;
					if(line.charAt(1) == 'n') normalCount++;
				
				} else if(line.charAt(0) == 'f') faceCount++;
			} catch(IndexOutOfBoundsException e) {
				
			}
		}
		
		br.close();
	}
	
	public void LoadData(String f) throws IOException {
		VertexType[] vertices = new VertexType[vertexCount];
		VertexType[] texcoords = new VertexType[textureCount];
		VertexType[] normals = new VertexType[normalCount];
		FaceType[] faces = new FaceType[faceCount];
		int vertexIndex, texcoordIndex, normalIndex, faceIndex;
		int vIndex, tIndex, nIndex;
		PrintWriter epw = new PrintWriter("./error.txt"); // TODO : have to remove.
		
		// 객체 배열 생성
		for(int j=0; j<vertexCount; j++) {
			vertices[j] = new VertexType();
		}
		
		for(int j=0; j<textureCount; j++) {
			texcoords[j] = new VertexType();
		}
		
		for(int j=0; j<normalCount; j++) {
			normals[j] = new VertexType();
		}
		
		for(int j=0; j<faceCount; j++) {
			faces[j] = new FaceType();
		}
		
		vertexIndex = 0;
		texcoordIndex = 0;
		normalIndex = 0;
		faceIndex = 0;
		
		// 파일 읽기 시작
		BufferedReader br = new BufferedReader(new FileReader(f));
		while(true) {
			String line = br.readLine();
			if(line == null) break;
			
			// IndexOutOfBoundsException 발생가능
			try {
				if(line.charAt(0) == 'v') {
					// 정점
					if(line.charAt(1) == ' ') {
						String[] splitedLine = line.split(" ");
						vertices[vertexIndex].x = Float.parseFloat(splitedLine[1]);
						vertices[vertexIndex].y = Float.parseFloat(splitedLine[2]);
						vertices[vertexIndex].z = Float.parseFloat(splitedLine[3]);
						
						// 오른손 좌표계 -> 왼손 좌표계 변경.
						vertices[vertexIndex].z = vertices[vertexIndex].z * -1.0f;
						vertexIndex++;
					}
					// 텍셀좌표
					if(line.charAt(1) == 't') {
						String[] splitedLine = line.split(" ");
						texcoords[texcoordIndex].x = Float.parseFloat(splitedLine[1]);
						texcoords[texcoordIndex].y = Float.parseFloat(splitedLine[2]);
						
						// 오른손 좌표계 -> 왼손 좌표계 변경 (opengl 방식 -> directx 방식)
						texcoords[texcoordIndex].y = 1.0f - texcoords[texcoordIndex].y;
						texcoordIndex++; 
					}
					// 법선 벡터
					if(line.charAt(1) == 'n') {
						String[] splitedLine = line.split(" ");
						normals[normalIndex].x = Float.parseFloat(splitedLine[1]);
						normals[normalIndex].y = Float.parseFloat(splitedLine[2]);
						normals[normalIndex].z = Float.parseFloat(splitedLine[3]);
						
					 	// 오른손 좌표계 -> 왼손 좌표계 변경.
						normals[normalIndex].z = normals[normalIndex].z * -1.0f;
						normalIndex++; 
					}
					
				}
				
				if(line.charAt(0) == 'f') {
					String[] splitedLine = line.split(" ");
					
					// 첫번째
					String[] eachIndex1 = splitedLine[1].split("/");
					faces[faceIndex].vIndex3 = Integer.parseInt(eachIndex1[0]);
					faces[faceIndex].tIndex3 = Integer.parseInt(eachIndex1[1]);
					faces[faceIndex].nIndex3 = Integer.parseInt(eachIndex1[2]);
					
					// 두번째
					String[] eachIndex2 = splitedLine[2].split("/");
					faces[faceIndex].vIndex2 = Integer.parseInt(eachIndex2[0]);
					faces[faceIndex].tIndex2 = Integer.parseInt(eachIndex2[1]);
					faces[faceIndex].nIndex2 = Integer.parseInt(eachIndex2[2]);
					
					// 세번째
					String[] eachIndex3 = splitedLine[3].split("/");
					faces[faceIndex].vIndex1 = Integer.parseInt(eachIndex3[0]);
					faces[faceIndex].tIndex1 = Integer.parseInt(eachIndex3[1]);
					faces[faceIndex].nIndex1 = Integer.parseInt(eachIndex3[2]);
					
					faceIndex++;
				}
			} catch (IndexOutOfBoundsException e) {
				
			}
		}
		
		// ㅠㅏ일 읽기 종료
		br.close();
		
		// 파일 쓰기 시작
		PrintWriter pw = new PrintWriter("./model.txt");
		String data = "Vertex Count: " + (faceCount*3);
		
		pw.println(data);
		pw.println("");
		pw.println("Data:");
		pw.println("");
		
		// 데이터 쓰기 시작
		// IndexOutOfBoundsException 발생가능
		try {
			for(int i=0; i<faceIndex; i++) {
				vIndex = faces[i].vIndex1 - 1;
				tIndex = faces[i].tIndex1 - 1;
				nIndex = faces[i].nIndex1 - 1;
				
				data = vertices[vIndex].x + " " + vertices[vIndex].y + " " + vertices[vIndex].z + " ";
				data = data + texcoords[tIndex].x + " " + texcoords[tIndex].y + " ";
				data = data + normals[nIndex].x + " " + normals[nIndex].y + " " + normals[nIndex].z;
				pw.println(data);
				
				
				vIndex = faces[i].vIndex2 - 1;
				tIndex = faces[i].tIndex2 - 1;
				nIndex = faces[i].nIndex2 - 1;
				
				data = vertices[vIndex].x + " " + vertices[vIndex].y + " " + vertices[vIndex].z + " ";
				data = data + texcoords[tIndex].x + " " + texcoords[tIndex].y + " ";
				data = data + normals[nIndex].x + " " + normals[nIndex].y + " " + normals[nIndex].z;
				pw.println(data);
				
				
				vIndex = faces[i].vIndex3 - 1;
				tIndex = faces[i].tIndex3 - 1;
				nIndex = faces[i].nIndex3 - 1;
				
				data = vertices[vIndex].x + " " + vertices[vIndex].y + " " + vertices[vIndex].z + " ";
				data = data + texcoords[tIndex].x + " " + texcoords[tIndex].y + " ";
				data = data + normals[nIndex].x + " " + normals[nIndex].y + " " + normals[nIndex].z;
				pw.println(data);
			}
		} catch(IndexOutOfBoundsException e) {
			
		}
		
		// 파일 쓰기 종료
		pw.close();
		epw.close();
	}
}