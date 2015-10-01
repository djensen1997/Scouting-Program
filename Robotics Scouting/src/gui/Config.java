package gui;

import java.awt.*;

import javax.swing.*;

import utilities.*;

import java.io.*;
/**
 * This Class Handles first time running of the program and
 * Setting up Basic config settings like team Number and Game
 * Year.
 * 
 * 
 * @author DaneJensen
 *
 */
public class Config
{
    private String user = System.getProperty("user.name");

    private File config = new File("/Users/" + user +  "/Documents/Scouting Info/");
    
    private String savePath = "/Users/" + user + "/Documents/Scouting Info/";
    
    private JFrame f;
    
    private JPanel p;
    
    private JTextField t,t1;
    
    private JPasswordField adminPassword;
    
    private JLabel[] l;
    
    private JButton b;
    
    private String teamN,pword;
    
    private String gameYear;
    
    private MyActionListener myActionListener;
    
    private FileOutput out = new FileOutput();
    
    @SuppressWarnings({})
    /**
     * 
     */
	public Config()
    {
        
    	myActionListener = new MyActionListener(this);
    	
        adminPassword = new JPasswordField(15);
        adminPassword.addActionListener(myActionListener);
        
        f = new JFrame("First Time Config Settings");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500,200);
        p = new JPanel();
        t = new JTextField(4);
        //t.addActionListener(this);
        t1 = new JTextField(4);
        //t1.addActionListener(this);
        l = new JLabel[4];
        for(int i = 0; i < l.length; i++){
            l[i] = new JLabel();
        }
        l[0].setText("Scouting Program Settings");
        l[0].setFont(new Font("Impact",Font.BOLD,40));
        l[1].setText("Enter Team Number");
        l[2].setText("Game Year");
        l[3].setText("Admin Password");
        
        b = new JButton("Submit First Time Information");
        b.addActionListener(myActionListener);
        p.add(l[0]);
        p.add(l[1]);
        p.add(t);
        p.add(l[2]);
        p.add(t1);
        t1.addActionListener(myActionListener);
        p.add(l[3]);
        p.add(adminPassword);
        p.add(b);
        f.add(p);
        if(!config.exists()){
            generateConfig();
            f.setVisible(true);
        }else{
            
            launchProgram();
            
        }
        
    }
    
    
    /**
     * Makes the directory to the config File,
     * 
     * 
     */
    private void generateConfig(){
        try{
            
            config.mkdir();
            
        }catch(SecurityException se){
            System.out.println(se);
        }

        try{
        	
        	File imageFolder = new File("/Users/" + System.getProperty("user.name") + "/Documents/Scouting Info/Sponser Images");
        	
        	imageFolder.mkdir();
        	
        }catch(Exception e){
        	
        	System.out.println(e.toString());
        	
        }
    }
    
    /**
     * Writes the config file
     * 
     */
    public void writeConfig(){
    	//"/Users/" + System.getProperty("user.name") +  "/Documents/Scouting Info/Config.txt"
    	
    	String[] cfg = new String[4];
    	
    	cfg[2] = "Team Number: " + teamN;
    	cfg[3] = "Teams Scouted at Once: 1";
    	cfg[1] = "Game Year: " + gameYear;
    	cfg[0] = "Admin Password: " + pword;
    	
    	out.writeFile(savePath + "Config.txt", cfg);
    	
    	String[] yrs = {gameYear};
    	
    	out.writeFile(savePath + "Years.txt", yrs);
        
        File gameYearFolder = new File(savePath + gameYear + "/");
        
        try{
        	
        	gameYearFolder.mkdir();
        	
        }catch(SecurityException se){
        	
        	System.out.println(se);
        	
        }
        
        File metric = new File("/Users/" + System.getProperty("user.name") +  "/Documents/Scouting Info/" + gameYear + "/metrics/");
        
  	  if(!metric.exists()){
  		  
  		  try{
  	            
  	            metric.mkdir();
  	            out.writeFile(savePath + gameYear + "/metrics/metrics.txt", null);
  	          
  	        }catch(Exception e){
  	            System.out.println(e);
  	        }
  		  
  		  
  	  }
  	  
  	  
  	  
    }
    /**
     * 
     * @return Returns the Submit JButton for the Action Listener
     */
    public JButton getButton(){
    	
    	return b;
    	
    }
    
    /**
     * 
     * @return Returns the Teams Number Text Field
     */
    public JTextField getT(){
    	
    	return t;
    	
    }
    
    /**
     * 
     * @return Returns the Game Year Text Field
     */
    public JTextField getT1(){
    	
    	return t1;
    	
    }
    
    /**
     * Sets the Team Number for the using Team
     * 	-Note: This Number is only used for Cosmetic Purposes
     * 
     * @param team the team number for the user (Purly Cosmetic)
     */
    public void setTeamNumber(String team){
    	
    	teamN = team;
    	
    }
    
    /**
     * Sets the Game Year for the current game being scouted for
     *  -Used to sort different games and keep metrics and competitions separate
     * 
     * @param year The year of the Game Being Scouted for
     */
    public void setGameYear(String year){
    	
    	gameYear = year;
    	
    }
    
    /**
     * The Admin Password is a safety net for people accidentally adding a team
     * to the data base or metric to the data base that does not belong
     * 
     * @param Password The Admin Password for the Program
     */
    public void setPassword(String Password){
    	
    	this.pword = Password;
    	
    }
    
    /**
     * 
     * @return Returns the PasswordField that holds the User's admin Password
     */
    public JPasswordField getAdminPasswordField(){
    	
    	return this.adminPassword;
    	
    }
    
    /**
     * Launches the Main Program, last thing to get called in this class,
     * after every config setting has been set
     */
    public void launchProgram(){
    	
    	f.dispose();
    	
    	MainScreen m = new MainScreen(myActionListener);
    	
    	myActionListener.setMainScreenRefrence(m);
    	
    }

}
