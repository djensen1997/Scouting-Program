package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FileInput {

	public FileInput(){}
	
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
