package objects;

import java.util.ArrayList;

public class Metric {
	
	private String n, i;
	private int m,max,min;
	private ArrayList<String> options = new ArrayList<String>();
	
	public Metric(String name){
		n = name;
		m = 1;
		i = "JButton";
		max = 100;
		min = 0;
		
	}
	
	public Metric(String name, int mag){
		n = name;
		m = mag;
		i = "JButton";
		max = 100;
		
	}
	
	public Metric(String name, String InputType){
		
		n = name;
		m = 1;
		i = InputType;
		max = 100;
		
	}

	public Metric(String name, int mag, String InputType){
		
		n = name;
		m = mag;
		i = InputType;
		max = 100;
	
	}
	/**
	 * 
	 * @return all the non-name information about the metric
	 */
	public String[] getInfo(){
		
		String[] output = new String[4];
		
		output[0] = "" + getMag();
		
		output[1] = "" + getInputType();
		
		output[2] = "" + getMinValue();
		
		output[3] = "" + getMaxValue();
		
		return output;
		
	}
	
	public String getName(){
		
		return n;
		
	}
	
	public void setName(String s){
		
		n = s;
		
	}
	
	public String getInputType(){
		
		return i;
		
	}
	
	public int getMag(){
		
		return m;
		
	}
	
	public void setMag(int mag){
		
		m = mag;
		
	}
	
	public void setInputType(String s){
		
		i = s;
		
	}
	
	public void setMaxValue(int i){
		
		max = i;
		
	}
	
	public int getMaxValue(){
		
		return max;
		
	}
	
	public void setMinValue(int i){
		
		min = i;
		
	}
	
	public int getMinValue(){
		
		return min;
		
	}
	
	public void addOption(String title){
		
		options.add(title);
		
	}
	
	public String getOption(int i){
		
		return options.get(i);
		
	}
	
	public int getOptionsSize(){
		
		return options.size();
		
	}
	
	public void clearOptions(){
		
		options.clear();
		
	}

}
