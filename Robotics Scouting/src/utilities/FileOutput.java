package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
/**
 * A Basic file output system designed to allow the developer to save space in the
 * overall program by not opening buffered writers constantly
 * 
 * @author DaneJensen
 *
 */
public class FileOutput {
	/**
	 * an empty constructor
	 */
	public FileOutput(){}
	/**
	 * 
	 * @param savePath the Save Path to the file the program is outputting to
	 * @param output A String array, each element of the array is considered a new line by the output system
	 */
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
