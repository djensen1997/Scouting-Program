package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import utilities.*;
import objects.*;
/**
 * The Frame that holds the Match Sheet for a team
 * of the User's choosing
 * 
 * @author DaneJensen
 *
 */
public class MatchScout implements ChangeListener{
    private JButton saveMatch = new JButton("Submit Stats");
    private JFrame f;
    private JPanel p3;
    private JButton[][] mb;
    private JSlider[] ms;
    private JTextField[] mtf;
    private ArrayList<JLabel> l = new ArrayList<JLabel>();
    private JLabel[] ml;
    private int xloc;
    private JLabel[] tl;
    private ArrayList<Metric> metrics;
    private GridBagConstraints g = new GridBagConstraints();
    private MyActionListener myActionListener;
    private String teamNumber;
    private TreeMap<String, ArrayList<ArrayList<String>>> teams = new TreeMap<String, ArrayList<ArrayList<String>>>();
    private TreeMap<String, String> config = new TreeMap<String, String>();
    private String savePath = "/C:/Users/" + System.getProperty("user.name")
			+ "/Documents/Scouting Info/";
    private FileOutput writer = new FileOutput();
    private JFrame mainFrame;
    /**
     * 
     * @param teams The TreeMap containing all of the Teams competing in the current Competition
     * @param key The specific team the user wants to scout
     * @param metrics The ArrayList that holds all Metric information
     * @param config The current config the program is running off of
     * @param xloc The X- Location of the frame, used mainly when the user is scouting multiple teams
     * @param myActionListener The Action Listener for the program
     * @param mainFrame The Main JFrame for the program, only used to acquire the dimensions of the screen
     */
	public MatchScout(TreeMap<String, ArrayList<ArrayList<String>>> teams,String key,ArrayList<Metric> metrics,TreeMap<String, String> config,int xloc, MyActionListener myActionListener,
					  JFrame mainFrame){
        
        this.metrics = metrics;
        
        this.xloc = xloc;
        
        this.myActionListener = myActionListener;
        
        this.teams = teams;
        
        this.config = config;
        
        this.mainFrame = mainFrame;
        
        teamNumber = key;
        
        l.add(new JLabel(teamNumber + " Match Sheet"));
        
        mb = new JButton[metrics.size()][2];
        
        ms = new JSlider[metrics.size()];
        
        mtf = new JTextField[metrics.size()];
        
        ml = new JLabel[metrics.size()];
        
        tl = new JLabel[metrics.size()];
        
        f = new JFrame("Team " + key + " Scout Sheet");
        
        p3 = new JPanel(new GridBagLayout());
        
        addStatsScreen();
       
    }
	
	/**
	 * 
	 * @return Returns the team number the user is currently scouting
	 */
	public String getTeamNumber(){
		
		return teamNumber;
		
	}
	
	/**
	 * 
	 * @return Returns the Button the user clicks when the match is over
	 */
	public JButton getSaveMatchButton(){
		
		return saveMatch;
		
	}
	
	/**
	 * 
	 * @return Returns the JButtons that show up as + and - on the GUI
	 */
	public JButton[][] getDuelMetricButtons(){
		
		return mb;
		
	}
	
	/**
	 * 
	 * @return Returns the Text Fields that the user is entering information into
	 */
	public JTextField[] getMetricTextFields(){
		
		return mtf;
		
	}
	
	/**
	 * 
	 * @return Returns any JSliders the user might be using for things like a Defence Rating
	 */
	public JSlider[] getMetricSliders(){
		
		return ms;
		
	}
	
	/**
	 * These Labels hold the actual information about what a team did for that metric
	 * 
	 * @return Returns the JLabels next to the input Components
	 */
	public JLabel[] getMetricLabels(){
		
		return ml;
		
	}
	
	/**
	 * Prepares and shows the screen the User uses to input data into the database
	 */
	public void addStatsScreen(){
    	//Getting the Content in Order
    	for(int i = 0; i < metrics.size(); i++){
    		tl[i] = new JLabel();
    		tl[i].setText(metrics.get(i).getName());
    		
    		if(metrics.get(i).getInputType().equals("Buttons")){
    			JButton a = new JButton("+");
    	    	JButton b = new JButton("-");
    			
    			JLabel tl = new JLabel("0");
    			mb[i][0] = b;
    			mb[i][0].addActionListener(myActionListener);
    			mb[i][1] = a;
    			mb[i][1].addActionListener(myActionListener);
    			ml[i] = (tl);
    			
    		}
    		if(metrics.get(i).getInputType().equals("Text Box")){
    			
    			JTextField tt = new JTextField(3);
    			mtf[i] = tt;
    			
    		}
    		
    		if(metrics.get(i).getInputType().equals("Slider")){
    			
    			JLabel tl = new JLabel("0");
    			ml[i] = tl;
    			JSlider ts = new JSlider();
    			ts.setMaximum(metrics.get(i).getMaxValue());
    			ts.setMinimum(metrics.get(i).getMinValue());
    			ts.setValue(0);
    			ts.setMinorTickSpacing(1);
    			ts.setMajorTickSpacing(metrics.get(i).getMaxValue()/10);
    			ts.setSnapToTicks(true);
    			
    			ts.addChangeListener(this);
    			ms[i] = ts;
    			
    		}
    	}
    	//Actually Making the Menu
    	g.insets = new Insets(1,1,1,1);
    	p3.removeAll();
    	g.gridx = 0;
    	g.gridy = 0;
    	g.gridwidth = 5;
    	p3.add(l.get(0),g);
    	g.gridwidth = 1;
    	int y = 0;
    	for(int i = 0; i < metrics.size(); i++){
    		y = y + 3;
    		if(metrics.get(i).getInputType().equals("Buttons")){
    			g.gridx = 0;
    	    	g.gridy = y;
    			p3.add(tl[i],g);
    			g.gridx = 1;
    	    	g.gridy = y;
    			p3.add(mb[i][0],g);
    			g.gridx = 2;
    	    	g.gridy = y;
    			p3.add(ml[i],g);
    			g.gridx = 3;
    	    	g.gridy = y;
    			p3.add(mb[i][1],g);
    			
    	    	
    		}
    		if(metrics.get(i).getInputType().equals("Text Box")){
    			g.gridx = 0;
    	    	g.gridy = y;
    			p3.add(tl[i],g);
    			g.gridx = 1;
    	    	g.gridy = y;
    	    	g.gridwidth = 2;
    			p3.add(mtf[i],g);
    			g.gridwidth = 1;
    			
    	    	
    		}
    		
    		if(metrics.get(i).getInputType().equals("Slider")){
    			g.gridx = 0;
    	    	g.gridy = y;
    			p3.add(tl[i],g);
    			g.gridx = 1;
    	    	g.gridy = y;
    	    	g.gridwidth = 3;
    			p3.add(ms[i],g);
    			g.gridwidth = 1;
    			g.gridx = 4;
    	    	g.gridy = y;
    			p3.add(ml[i],g);
    			
    		}
    		
    	}
    	
    	
    	saveMatch.addActionListener(myActionListener);
    	
    	g.gridx = 2;
    	
    	g.gridy = y + 50;
    	
    	JPanel tempPanel = new JPanel(new BorderLayout());
    	
    	tempPanel.add(saveMatch,BorderLayout.SOUTH);
    	
    	tempPanel.add(p3, BorderLayout.NORTH);
    	
    	f.add(tempPanel);
    	
    	f.validate();
    	
    	f.setSize(mainFrame.getWidth() / 3, mainFrame.getHeight() * 2/3);
    	
    	f.setLocation(xloc,mainFrame.getHeight() / 3);
    	
    	f.setUndecorated(true);
    	
    	f.setVisible(true);
    	
    }
  
	/**
	 * Updates the file for the team currently being scouted by adding
	 * the match informations the user just scouted
	 */
    public void rewrite(){
        
        ArrayList<String> output = new ArrayList<String>();
    	
    	for(int i = 0; i < teams.get(teamNumber).get(teams.get(teamNumber).size() - 1).size(); i++){
    		
    		output.add("Match Number: " + (i + 1));
            for(int index = 0; index < metrics.size(); index++){
            	
            	if(index < (teams.get(teamNumber).get(i).size())){
            		output.add(metrics.get(index).getName() + ": " + teams.get(teamNumber).get(i).get(index));
            		
            	}else{
            		
            		output.add(metrics.get(index).getName() + ": " + 0);
            		
            	}
            	
            }
            /*writer.write("X Coored: " + x);
    		//System.out.println(x);
    		writer.newLine();

    		writer.write("Y Coored: " + y);
    		//System.out.println(y);*/
    		
    	}
    	
    	String[] toFile = new String[output.size()];
    	
    	for(int i = 0; i < toFile.length; i++){
    		
    		toFile[i] = output.get(i);
    		
    	}
    	
    	writer.writeFile(savePath + config.get("Game Year") + "/" + config.get("Current Competition") 
    			+ "/Team Data/" + teamNumber + ".txt", toFile);
      
    }

    /**
     * Called when the user slides a JSlider and changes the Corresponding JLabel
     * to reflect the value of the JSlider
     */
	public void stateChanged(ChangeEvent e) {
		
		for(int i = 0; i < ms.length; i++){
			
			if(e.getSource() == ms[i]){
				
				ml[i].setText("" + ms[i].getValue());
				
			}
			
		}
		
	}
	
	/**
	 * Closed the Scouting Screen when the user is done with the match
	 */
	public void hideWindow(){
		
		f.dispose();
		
	}
	
	
  
}
