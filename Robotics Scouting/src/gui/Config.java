package gui;

import java.awt.*;

import javax.swing.*;

import utilities.*;

import java.io.*;

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
    
    
    
    private void generateConfig(){
        try{
            
            config.mkdir();
            
        }catch(SecurityException se){
            System.out.println(se);
        }

    }
    
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
    
    public void getConfig(){
        
    }
    
    public JButton getButton(){
    	
    	return b;
    	
    }
    
    public JTextField getT(){
    	
    	return t;
    	
    }
    
    public JTextField getT1(){
    	
    	return t1;
    	
    }
    
    public void setTeamNumber(String team){
    	
    	teamN = team;
    	
    }
    
    public void setGameYear(String year){
    	
    	gameYear = year;
    	
    }
    
    public void setPassword(String Password){
    	
    	this.pword = Password;
    	
    }
    
    public JPasswordField getAdminPasswordField(){
    	
    	return this.adminPassword;
    	
    }
    
    public void launchProgram(){
    	
    	f.dispose();
    	
    	MainScreen m = new MainScreen(myActionListener);
    	
    	myActionListener.setMainScreenRefrence(m);
    	
    }

}
