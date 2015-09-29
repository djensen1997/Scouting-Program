package objects;

import java.util.ArrayList;
/**
 * The Metric class is used to define what the user is looking for in a team
 * 
 * 
 * @author DaneJensen
 *
 */
public class Metric {
	
	private String n, i;
	private int m,max,min;
	private ArrayList<String> options = new ArrayList<String>();
	/**
	 * 
	 * @param name The name of the Metric
	 */
	public Metric(String name){
		n = name;
		m = 1;
		i = "Buttons";
		max = 100;
		min = 0;
		
	}
	
	/**
	 * 
	 * @param name The name of the Metric
	 * @param mag How much the user wants this metric to be weighted overall
	 */
	public Metric(String name, int mag){
		n = name;
		m = mag;
		i = "Buttons";
		max = 100;
		
	}
	
	/**
	 * 
	 * @param name The name of the Metric
	 * @param InputType the Input type for the metric
	 */
	public Metric(String name, String InputType){
		
		n = name;
		m = 1;
		i = InputType;
		max = 100;
		
	}

	/**
	 * 
	 * @param name The Name of the Metric
	 * @param mag How much the user wants the metric Weighted overall
	 * @param InputType the Input type for the metric
	 */
	public Metric(String name, int mag, String InputType){
		
		n = name;
		m = mag;
		i = InputType;
		max = 100;
	
	}
	/**
	 * the "toString method"
	 * 
	 * @return all the non-name information about the metric
	 */
	public String[] getInfo(){
		
		String[] output = new String[4];
		
		output[0] = "Mag: " + getMag();
		
		output[1] = "Input Type: " + getInputType();
		
		output[2] = "Min: " + getMinValue();
		
		output[3] = "Max: " + getMaxValue();
		
		return output;
		
	}
	
	/**
	 * 
	 * @return Returns the Name of the Metric
	 */
	public String getName(){
		
		return n;
		
	}
	
	/**
	 * 
	 * @param s The name the user wants the metric to have
	 */
	public void setName(String s){
		
		n = s;
		
	}
	
	/**
	 * 
	 * @return Returns the input type for the metric
	 */
	public String getInputType(){
		
		return i;
		
	}
	
	/**
	 * Magnitude = how much the user wants the metric weighted
	 * 
	 * @return Returns the Magnitude of the Metric
	 */
	public int getMag(){
		
		return m;
		
	}
	
	/**
	 * Magnitude = how much the user wants the metric weighted
	 * 
	 * @param mag The new magnitude for the metric
	 */
	public void setMag(int mag){
		
		m = mag;
		
	}
	
	/**
	 * 
	 * @param s The input type the user would like this metric to have
	 */
	public void setInputType(String s){
		
		i = s;
		
	}
	
	/**
	 * 
	 * @param i the new maximum value the metric can have, 100 by default
	 */
	public void setMaxValue(int i){
		
		max = i;
		
	}
	
	/**
	 * 
	 * @return Returns the Maximum value the user wants the metric to have, 100 by default
	 */
	public int getMaxValue(){
		
		return max;
		
	}
	
	/**
	 * 
	 * @param i The new minimum value the user wants the metric to have, 0 by default
	 */
	public void setMinValue(int i){
		
		min = i;
		
	}
	
	/**
	 * 
	 * @return Returns the Minimum Value the user wants the metric to have, 0 by default
	 */
	public int getMinValue(){
		
		return min;
		
	}
	
	/**
	 * 
	 * @param title Adds an option for JComboBox Metrics or for JRadioButton Metrics
	 */
	public void addOption(String title){
		
		options.add(title);
		
	}
	
	/**
	 * 
	 * @param i the index for the option the User is requesting
	 * @return Returns the option requested
	 */
	public String getOption(int i){
		
		return options.get(i);
		
	}
	
	/**
	 * 
	 * @return returns how many options this metric has
	 */
	public int getOptionsSize(){
		
		return options.size();
		
	}
	
	/**
	 * Removes all options in the metric
	 */
	public void clearOptions(){
		
		options.clear();
		
	}

}
