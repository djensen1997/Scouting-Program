package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileOutput {

	public FileOutput(){}
	
	public void writeFile(String savePath, String[] output){
		//"/Users/" + System.getProperty("user.name") +  "/Documents/Scouting Info/Config.txt"
		try {
			FileWriter fs = new FileWriter(savePath);
			
			BufferedWriter writer = new BufferedWriter(fs);
			
			for(int i = 0; i < output.length; i++){
				
				writer.write(output[i]);
				
				writer.newLine();
				
			}
			
			writer.close();
		} catch(Exception e) {
			
			System.err.println("Error: " + e.getMessage());
			
		}
	
	}
	
}
