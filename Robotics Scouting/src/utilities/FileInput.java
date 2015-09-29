package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
/**
 * A basic File Input System for the Program, Used this instead of constantly calling
 * a new buffered file reader throughout the program, better to have it in one place
 * 
 * @author DaneJensen
 *
 */
public class FileInput {
	/**
	 * an empty constructor
	 */
	public FileInput(){}
	/**
	 * 
	 * @param path the path to the file the program is trying to read
	 * @return Returns the content of the File requested in the form of a String array
	 */
	public String[] readFile(String path){
		
		ArrayList<String> input = new ArrayList<String>();
		
		try{
			
			BufferedReader fr = new BufferedReader(new FileReader(path));
			
			String temp;
			
			while((temp = fr.readLine()) != null){
				
				input.add(temp);
				
			}
			
			fr.close();
			
		}catch (Exception e){
			
			
			
		}
		
		if(input.size() == 0){
			
			input.add(null);
			
		}
		
		String[] output = new String[input.size()];
		
		for(int i = 0; i < input.size(); i++){
			
			output[i] = input.get(i);
			
		}
		
		return output;
		
	}
	
	/**
	 * An alternative to the basic File Reader, this one splits each line based on a deliminator and
	 * returns all files to the right of the deliminator
	 * 
	 * @param path the file path to the file the program is trying to read
	 * @param Deliminator the string that the program wants to cut the string from
	 * @return Returns the strings to the right of the deliminator
	 */
	public String[] readFile(String path, String Deliminator){
		
		ArrayList<String> input = new ArrayList<String>();
		
		try{
			
			BufferedReader fr = new BufferedReader(new FileReader(path));
			
			String temp;
			
			while((temp = fr.readLine()) != null){
				
				String[] parts = temp.split(Deliminator);
				
				if(parts.length == 2){
					
					input.add(parts[1]);
					
				}
				
			}
			
			fr.close();
			
		}catch (Exception e){
			
			
			
		}
		
		String[] output = new String[input.size()];
		
		for(int i = 0; i < input.size(); i++){
			
			output[i] = input.get(i);
			
		}
		
		return output;
		
	}
	
	
	
}
