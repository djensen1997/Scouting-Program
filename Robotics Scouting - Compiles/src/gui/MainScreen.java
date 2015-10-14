package gui;
//TODO go to every menu and have them set teamLogoFrame.setAlwaysOnTop(false);
import objects.*;
import gui.pictures.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import utilities.*;
import javax.swing.*;

/**
 * This is the Main Class for the Program. It controls the main Frame for the
 * gui and has direct access to all aspects of the program.
 * 
 * @author DaneJensen
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MainScreen {

	private JFrame f, utilityFrame;

	private JPanel mainPanel, mainScreen;// mainPanel is always showing and its
											// contents change
											// mainScreen is the Entry Screen
											// and Database interface screen

	private JTextField[] t;

	private JButton read;

	private JMenuBar mainBar;

	private Container c;

	private JMenu file, topStats, competition, mode;

	private JMenuItem[] fileItems, topStatsItems, competitionItems, modeItems;

	private String savePath = "/Users/" + System.getProperty("user.name") + "/Documents/Scouting Info/";

	private JLabel[] l = new JLabel[7];

	private TreeMap<String, String> config = new TreeMap<String, String>();

	private FileInput reader = new FileInput();

	private FileOutput writer = new FileOutput();

	private ArrayList<Metric> metrics = new ArrayList<Metric>();

	private ArrayList<String> competitions = new ArrayList<String>();

	private ArrayList<String> gameYears = new ArrayList<String>();

	private MyActionListener myActionListener;

	private GridBagConstraints g = new GridBagConstraints();

	private JComboBox m;

	private JButton back = new JButton("Back");

	private JButton addMetric = new JButton("Add Metric");

	private JButton saveMetric = new JButton("Save Metric");

	private JButton addGameYear = new JButton("Add Game Year");

	private JButton addCompetition = new JButton("Add Competition");

	private JTextField competitionNameField = new JTextField(20);

	private JTextField metricName = new JTextField(15);

	private JTextField metricWeight = new JTextField(2);

	private JTextField gameYearField = new JTextField(4);

	private JTextField[] metricTextFields = new JTextField[4];

	private int changeMetricIndex;// this is the index in the metric array of
									// the metric that needs to change

	private JComboBox metricSelectorMenu;

	private TreeMap<String, ArrayList<ArrayList<String>>> competingTeamsInfo = new TreeMap<String, ArrayList<ArrayList<String>>>();

	private JButton addTeam = new JButton("Yes");

	private JButton doNotAddTeam = new JButton("No");

	private SponserImage sponserPanel = new SponserImage("2472.png");
	
	private JFrame teamLogoFrame = new JFrame();
	
	/**
	 * The main constructor for the class, requires an action listener to be
	 * Referenced for the rest of the program
	 * 
	 * @param myActionListener
	 *            The Action Listener for the program
	 */
	public MainScreen(MyActionListener myActionListener) {
		
		//sponserPanel.setSize(300, 300);
		
		this.myActionListener = myActionListener;
		
		myActionListener.setMainScreenRefrence(this);

		addTeam.addActionListener(this.myActionListener);

		doNotAddTeam.addActionListener(this.myActionListener);

		getInformation();
		
		for (int i = 0; i < l.length; i++) {

			l[i] = new JLabel("");
		}

		l[0].setFont(new Font("Impact", Font.BOLD, 72));

		l[0].setText("FRC Team 2472 Scouting Info");

		l[1].setText("Enter Team Number Here");

		l[2].setText("Team not in Files, Would you Like to add it?");

		l[3].setText("Input Metric Name Here");

		l[4].setText("Metric Scale");

		l[5].setText("Game Year Here Please");

		l[6].setText("Event Name Here");

		back.addActionListener(this.myActionListener);

		addGameYear.addActionListener(this.myActionListener);

		addCompetition.addActionListener(this.myActionListener);

		read = new JButton("Get Team Info");

		read.addActionListener(myActionListener);

		f = new JFrame("Team " + config.get("Team Number") + " Scouting Readout" + config.get("Current Competition"));

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		c = f.getContentPane();

		mainPanel = new JPanel(new BorderLayout());

		mainScreen = new JPanel(new BorderLayout());

		TopStats.setTeamMapRefrence(competingTeamsInfo);

		TopStats.setMetricsRefrence(metrics);

		mainMenu();
		
	}

	/**
	 * This Button is used to give the number of a team to the data base, which
	 * then opens up a new frame that the user can input data on pre-defined
	 * Metrics
	 * 
	 * @return Returns the JButton on the main GUI
	 */
	public JButton getReadButton() {

		return read;

	}

	/**
	 * The map works in 3 levels -Level 1 is the Main Key (the team number)
	 * -Level 2 is the Match Number (starting at 0 of course) -Level 3 is the
	 * Specific Metric Information
	 * 
	 * @return Returns the map of all the teams
	 */
	public TreeMap<String, ArrayList<ArrayList<String>>> getAllTeamInfo() {

		return competingTeamsInfo;

	}

	/**
	 * 
	 * @return Returns the Array of Input Text Fields that have the team numbers
	 *         in them
	 */
	public JTextField[] getTeamNumberInputFields() {

		return t;

	}

	/**
	 * Adds the inputed team to the data base and immediately updates the Teams
	 * Scouted.txt file to reflect the addition
	 * 
	 * @param teamNumber
	 *            The team Number to be added to the database
	 */
	public void addTeam(String teamNumber) {
		
		for(int i = 0; i < t.length; i++){

			if(!competingTeamsInfo.containsKey(t[i].getText())){
			
				competingTeamsInfo.put(t[i].getText(), new ArrayList<ArrayList<String>>());
			
			}else{}
		
			t[i].setText("");
			
		}

		saveTeamInfo();

		mainMenu();

	}

	/**
	 * "Sends" the specified team number to the Match Scout class for the user
	 * to input a new match for this team into the database
	 * 
	 * If a match in the database is not found, the program prompts the user to
	 * add it to the database
	 * 
	 * @param teamNumber
	 *            The team number being scouted
	 */
	public void sendTeamNumber(String teamNumber) {

		if (!competingTeamsInfo.containsKey(teamNumber)) {

			newTeamMenu();

		} else {

			if (true) {// will change eventually
				
				for(int i = 0; i < t.length; i++){
					
					t[i].setText("");
					
				}
				
				teamLogoFrame.setAlwaysOnTop(false);

				MatchScout data = new MatchScout(competingTeamsInfo, teamNumber, metrics, config, 0, myActionListener,
						f);

				myActionListener.addNewMatchScout(data);

			} /*
				 * else if (Mode.equals("Pit")) {
				 * 
				 * @SuppressWarnings("unused") pit_scouting scout = new
				 * pit_scouting(team.get(here), gameYear, currentCompetition);
				 * 
				 * }
				 */

		}

	}

	/**
	 * Creates the menu that prompts the user to add an unrecognized team to the
	 * database
	 */
	public void newTeamMenu() {

		utilityFrame = new JFrame("");

		JPanel tempPanel = new JPanel(new BorderLayout());

		JPanel tempPanel2 = new JPanel();

		tempPanel2.add(l[0]);

		tempPanel2.add(l[2]);

		tempPanel2.add(addTeam);

		tempPanel2.add(doNotAddTeam);

		tempPanel.add(tempPanel2, BorderLayout.CENTER);

		tempPanel.add(back, BorderLayout.SOUTH);
		
		teamLogoFrame.setAlwaysOnTop(false);

		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	/**
	 * Updates the Teams Scouted.txt file to accurately reflect which teams are
	 * in the data base for a specific event
	 */
	private void saveTeamInfo() {

		String[] teams = new String[competingTeamsInfo.size()];

		int i = 0;

		for (String key : competingTeamsInfo.keySet()) {

			teams[i] = key;

			// TODO Have Program Output Current Info on Teams (Might be
			// Unnessisary)

			i += 1;

		}

		writer.writeFile(
				savePath + config.get("Game Year") + "/" + config.get("Current Competition") + "/Teams Scouted.txt",
				teams);

	}

	/**
	 * Gets the Team Info for an Event
	 */
	private void getTeams() {

		String[] temp = reader.readFile(
				savePath + config.get("Game Year") + "/" + config.get("Current Competition") + "/Teams Scouted.txt");

		for (int i = 0; i < temp.length; i++) {

			if (temp[i] != null)
				competingTeamsInfo.put(temp[i], new ArrayList<ArrayList<String>>());

		}

		for (String key : competingTeamsInfo.keySet()) {

			String[] teamInfo = reader.readFile(savePath + config.get("Game Year") + "/"
					+ config.get("Current Competition") + "/Team Data/" + key + ".txt");

			if (teamInfo[0] != null) {
				for (int i = 0; i < teamInfo.length; i++) {

					ArrayList<String> matchInfo = new ArrayList<String>();

					String[] pa = teamInfo[i].split(": ");
					if (!(pa[0].equals("Match Number"))) {
						matchInfo.add(pa[1]);
					} else {

						i += 1;

					}
					for (int index = 0; index < metrics.size(); index++) {

						String[] parts = teamInfo[i].split(": ");

						if (parts[0].equals("Match Number")) {

							System.err.println(
									"Unexpected Value ---> Match Number <--- most likely caused by out of date match");
							System.err.println("Skipping Team" + key);
							break;

						} else {

							matchInfo.add(parts[1]);

						}

					}

					competingTeamsInfo.get(key).add(matchInfo);

				}
			}
		}

	}

	/**
	 * 
	 * @return Returns the "Yes I would like to add that team" button
	 */
	public JButton getAddTeamButton() {

		return addTeam;

	}

	/**
	 * 
	 * @return Returns the "NO, i would not like to add that team" button
	 */
	public JButton getDoNotAddTeamButton() {

		return doNotAddTeam;

	}

	/**
	 * Called When the program is first Opened, reads the config, game year,
	 * competitions and teams scouted files to get all the information from the
	 * database
	 */
	private void getInformation() {

		String[] temp = reader.readFile(savePath + "Config.txt", ": ");

		config.put("Team Number", temp[2]);

		config.put("Game Year", temp[1]);

		config.put("Teams Scouted at Once", temp[3]);

		config.put("Admin Password", temp[0]);

		getGameYears();

		readMetrics();

		getCompetitions();

		getTeams();

		prepMenuBar();

	}

	/**
	 * Reads the metric in the metrics.txt file for the current game year
	 */
	private void readMetrics() {
		metrics.clear();
		String[] temp = reader.readFile(savePath + config.get("Game Year") + "/metrics/metrics.txt");

		for (int i = 0; i < temp.length; i++) {

			metrics.add(new Metric(temp[i]));

		}

		for (int i = 0; i < metrics.size(); i++) {

			String[] metricInfo = reader.readFile(
					savePath + config.get("Game Year") + "/metrics/" + metrics.get(i).getName() + ".txt", ": ");

			if (metricInfo.length < 4) {

			} else {

				metrics.get(i).setMag(Integer.parseInt(metricInfo[0]));

				metrics.get(i).setInputType(metricInfo[1]);

				metrics.get(i).setMaxValue(Integer.parseInt(metricInfo[3]));

				metrics.get(i).setMinValue(Integer.parseInt(metricInfo[2]));

				if (metricInfo.length > 4) {// WIP, combo box

					for (int index = 4; index < metricInfo.length; index += 1) {

						metrics.get(i).addOption(metricInfo[index]);

					}

				}

			}

		}

	}

	/**
	 * Reads the Competitions.txt file to get all known competitions for the
	 * current game year
	 */
	private void getCompetitions() {

		competitions.clear();

		String[] temp = reader.readFile(savePath + config.get("Game Year") + "/Competitions.txt");

		for (int i = 0; i < temp.length; i++) {

			competitions.add(temp[i]);

		}

		config.put("Current Competition", reader.readFile(savePath + config.get("Game Year") + "/currentComp.txt")[0]);

	}

	/**
	 * 
	 * @return Returns the File Drop-down Menu Items
	 */
	public JMenuItem[] getFileItems() {

		return fileItems;

	}

	/**
	 * 
	 * @return Returns the Scouting Mode Drop-Down Meny Items
	 */
	public JMenuItem[] getModeItems() {

		return modeItems;

	}

	/**
	 * 
	 * @return Returns the Competition Drop-Down Menu Items
	 */
	public JMenuItem[] getCompetitionItems() {

		return competitionItems;

	}

	/**
	 * 
	 * @return Returns the Top Stat Drop - Down Menu Items
	 */
	public JMenuItem[] getTopStatsItems() {

		return topStatsItems;

	}

	/**
	 * Prepares the Menu Bar that appears at the top of the GUI
	 */
	private void prepMenuBar() {

		file = new JMenu("File");

		topStats = new JMenu("Top Stats");

		competition = new JMenu("Select Competition");

		mode = new JMenu("Scouting Mode");

		fileItems = new JMenuItem[7];

		modeItems = new JMenuItem[2];

		topStatsItems = new JMenuItem[metrics.size() + 1];

		competitionItems = new JMenuItem[competitions.size()];

		for (int i = 0; i < fileItems.length; i++) {

			fileItems[i] = new JMenuItem();

			fileItems[i].addActionListener(myActionListener);

		}

		for (int i = 0; i < metrics.size(); i++) {

			topStatsItems[i] = new JMenuItem();

			topStatsItems[i].addActionListener(myActionListener);

			topStatsItems[i].setText(metrics.get(i).getName());

		}

		topStatsItems[metrics.size()] = new JMenuItem();

		topStatsItems[metrics.size()].addActionListener(myActionListener);

		topStatsItems[metrics.size()].setText("Overall Top Teams");

		for (int i = 0; i < modeItems.length; i++) {

			modeItems[i] = new JMenuItem("");

			modeItems[i].addActionListener(myActionListener);

			mode.add(modeItems[i]);

		}

		modeItems[0].setText("Match Scouting");

		modeItems[1].setText("Pit Scouting");

		for (int i = 0; i < competitionItems.length; i++) {

			competitionItems[i] = new JMenuItem();

			competitionItems[i].addActionListener(myActionListener);

			competitionItems[i].setText(competitions.get(i));

			competition.add(competitionItems[i]);

		}

		fileItems[0].setText("Settings");

		fileItems[1].setText("New Game");

		fileItems[2].setText("New Competition");

		fileItems[3].setText("Add Metric");

		fileItems[4].setText("Compile Scouting Data");

		fileItems[5].setText("Edit Metrics");
		
		for (int i = 0; i < fileItems.length; i++) {

			file.add(fileItems[i]);

		}

		for (int i = 0; i < topStatsItems.length; i++) {

			topStats.add(topStatsItems[i]);

		}

		mainBar = new JMenuBar();

		mainBar.add(file);

		mainBar.add(topStats);

		mainBar.add(competition);

		mainBar.add(mode);

	}

	/**
	 * Returns the GUI to the "Main Menu" or the initial Screen when the Program
	 * is normally run
	 */
	@SuppressWarnings("unused")
	public void mainMenu() {

		try {

			utilityFrame.dispose();

		} catch (Exception e) {

		}

		f.setTitle(config.get("Team Number") + " Scouting Readout - " + config.get("Current Competition"));

		prepMenuBar();

		if (true) {

			if (config.get("Teams Scouted at Once").equals("Alliance")) {

				t = new JTextField[3];

			} else {

				t = new JTextField[1];

			}

		} else {

			t = new JTextField[1];

		}

		mainPanel.removeAll();

		mainScreen.removeAll();

		JPanel tempPanel = new JPanel(new GridBagLayout());

		g.weightx = 0.5;

		g.gridx = 0;

		g.gridy = 0;

		g.gridheight = 4;

		g.gridwidth = 20;

		g.insets = new Insets(10, 10, 10, 10);

		tempPanel.add(l[0], g);

		g.gridy = 40;

		g.gridheight = 1;

		g.gridwidth = 1;

		g.gridx = 0;

		tempPanel.add(l[1], g);

		g.gridheight = 1;

		g.gridwidth = 1;

		for (int i = 0; i < t.length; i++) {

			g.insets = new Insets(1, 1, 1, 1);

			t[i] = new JTextField(4);

			t[i].addActionListener(myActionListener);

			t[i].setText("");

			g.gridx = ((i + 1) * 4);

			tempPanel.add(t[i], g);

		}

		g.gridx = g.gridx + 1;

		tempPanel.add(read, g);
		g.gridwidth = 10;
		g.gridx = 0;
		
		g.gridy+=10;
		
		//tempPanel.add(sponserPanel);
		
		//g.gridx = 10;

		tempPanel.add(sponserPanel,g);
		
		teamLogoFrame.add(sponserPanel);
		
		teamLogoFrame.setSize(sponserPanel.getImageWidth(), sponserPanel.getImageHeight());
		
		mainScreen.add(tempPanel, BorderLayout.NORTH);

		mainPanel.add(mainScreen, BorderLayout.CENTER);

		mainPanel.add(mainBar, BorderLayout.NORTH);

		mainPanel.setBackground(Color.pink);

		c.add(mainPanel);

		f.setLocation(0, 0);

		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		f.validate();

		f.setVisible(true);
		
		try{Thread.sleep(1000);}catch(Exception e){}
		
		teamLogoFrame.setLocation(0, f.getHeight() - sponserPanel.getImageHeight());
		
		teamLogoFrame.setUndecorated(true);
		
		teamLogoFrame.setAlwaysOnTop(true);
		
		teamLogoFrame.setVisible(true);

	}

	/**
	 * Prepares the GUI that allows the User to see and change the Metric
	 * Information.
	 */
	public void metricMenu() {

		utilityFrame = new JFrame("");

		JPanel tempPanel = new JPanel(new BorderLayout());

		JPanel tempPanel2 = new JPanel();

		tempPanel2.add(new JLabel("New Metric Menu"));

		tempPanel2.add(l[3]);

		tempPanel2.add(metricName);

		tempPanel2.add(l[4]);

		tempPanel2.add(metricWeight);

		String[] IisArray = { "Select Input Method Here", "Text Box", "Buttons", "Slider", "Radio Buttons" };

		m = new JComboBox(IisArray);

		m.addActionListener(myActionListener);

		tempPanel2.add(m);

		addMetric.addActionListener(myActionListener);

		tempPanel2.add(addMetric);

		tempPanel.add(tempPanel2, BorderLayout.CENTER);

		tempPanel.add(back, BorderLayout.SOUTH);

		teamLogoFrame.setAlwaysOnTop(false);
		
		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	/**
	 * Pressed in the "New Metric Menu" and is used to add new Metrics
	 * 
	 * @return Returns the Add Metric JButton
	 */
	public JButton getAddMetricButton() {

		return addMetric;

	}

	/**
	 * Pressed in the "Metric Menu" and is used to change Metric Settings
	 * 
	 * @return Returns the Save Metric Button
	 */
	public JButton getSaveMetricButton() {

		return saveMetric;

	}

	/**
	 * This ArrayList holds all the information on the Metrics, or data points
	 * being Scouted for
	 * 
	 * @return Returns the ArrayList of Metrics
	 */
	public ArrayList<Metric> getMetrics() {

		return metrics;

	}

	/**
	 * The Method Saves the Metric the User is adding to the database
	 */
	public void saveMetric() {

		if (metrics.size() == 1 && metrics.get(0).getName() == null) {

			metrics.get(0).setName(metricName.getText());

		} else {

			metrics.add(new Metric(metricName.getText()));

		}
		metrics.get(metrics.size() - 1).setInputType((String) m.getSelectedItem());

		metrics.get(metrics.size() - 1).setMag(Integer.parseInt(metricWeight.getText()));

		metrics.get(metrics.size() - 1).setMaxValue(100);

		metrics.get(metrics.size() - 1).setMinValue(0);

		utilityFrame.dispose();

		writeMetrics();

		mainMenu();

	}

	/**
	 * Outputs the Contents of the Metric ArrayList to the metrics.txt file
	 */
	private void writeMetrics() {

		String[] temp = new String[metrics.size()];

		for (int i = 0; i < metrics.size(); i++) {

			temp[i] = metrics.get(i).getName();

		}

		writer.writeFile(savePath + config.get("Game Year") + "/metrics/metrics.txt", temp);

		for (int i = 0; i < metrics.size(); i++) {

			writer.writeFile(savePath + config.get("Game Year") + "/metrics/" + metrics.get(i).getName() + ".txt",
					metrics.get(i).getInfo());

		}

	}

	/**
	 * Prepares and shows the Menu for adding a new game year to the database
	 */
	public void newGameYearMenu() {

		utilityFrame = new JFrame("");

		JPanel tempPanel = new JPanel(new BorderLayout());

		JPanel tempPanel2 = new JPanel();

		tempPanel2.add(l[0]);

		tempPanel2.add(l[5]);

		tempPanel2.add(gameYearField);

		tempPanel2.add(addGameYear);

		tempPanel.add(tempPanel2, BorderLayout.CENTER);

		tempPanel.add(back, BorderLayout.SOUTH);
		
		teamLogoFrame.setAlwaysOnTop(false);

		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	/**
	 * The Add Game Year Button is used when the user has entered in a new game
	 * year and wants to add it to the database.
	 * 
	 * @return Returns the Add Game Year JButton
	 */
	public JButton getAddGameYearButton() {

		return addGameYear;

	}

	/**
	 * Shows up in almost all Menus, when pressed the program resets to the
	 * Initial state when normally opened
	 * 
	 * @return Returns the back Button
	 */
	public JButton getBackButton() {

		return back;

	}

	/**
	 * Adds a new game Year to the data base, and generates all files required
	 * for a game year Paths Generated: /Scouting Info/game year /Scouting
	 * Info/game year/metrics /Scouting Info/game year/competitions.txt
	 * /Scouting Info/game year/current competiton.txt /Scouting Info/game
	 * year/metrics/metrics.txt
	 */
	public void addGameYear() {

		utilityFrame.dispose();

		gameYears.add(gameYearField.getText());

		config.put("Game Year", gameYearField.getText());

		writeGameYears();

		generateGameYearFiles();

		changeGameYear(gameYearField.getText());

		gameYearField.setText("");

	}

	/**
	 * Updates Years.txt to reflect all game years in the database
	 */
	private void writeGameYears() {

		String[] temp = new String[gameYears.size()];

		for (int i = 0; i < temp.length; i++) {

			temp[i] = gameYears.get(i);

		}

		writer.writeFile(savePath + "Years.txt", temp);

	}

	/**
	 * Changes the game Year to a different Year
	 * 
	 * @param GameYear
	 *            the year of the game the user wants to switch to
	 */
	private void changeGameYear(String GameYear) {

		config.put("Game Year", GameYear);

		readMetrics();

		getCompetitions();

		saveConfig();

		getTeams();

		prepMenuBar();

		mainMenu();

	}

	/**
	 * Does the actual Generation of all game year files
	 */
	private void generateGameYearFiles() {

		String user = System.getProperty("user.name");

		File gameYearFolder = new File("/Users/" + user + "/Documents/Scouting Info/" + config.get("Game Year") + "/");

		try {
			gameYearFolder.mkdir();
		} catch (SecurityException se) {
			System.out.println(se);
		}

		File metric = new File(savePath + config.get("Game Year") + "/metrics/");

		try {

			metric.mkdir();

		} catch (Exception e) {

		}

		if (!metric.exists()) {

			writer.writeFile(savePath + config.get("Game Year") + "/metrics/metrics.txt", null);

		}

	}

	/**
	 * The program Reads the Years.txt file to know what games have existed and
	 * stores them in the program
	 */
	private void getGameYears() {
		gameYears.clear();

		String[] temp = (reader.readFile(savePath + "Years.txt"));

		for (int i = 0; i < temp.length; i++) {

			gameYears.add(temp[i]);

		}

	}

	/**
	 * Updates the config.txt file to reflect changes to the config settings
	 */
	private void saveConfig() {

		String[] temp = new String[4];

		int i = 0;

		for (String Key : config.keySet()) {

			if (!Key.equals("Current Competition")) {

				temp[i] = Key + ": " + (String) config.get(Key);
				i += 1;
			}

		}

		writer.writeFile(savePath + "Config.txt", temp);

	}

	/**
	 * Prepares and shows the menu the User inputs new competitions into
	 */
	public void newCompetitionMenu() {

		utilityFrame = new JFrame("");

		JPanel tempPanel = new JPanel(new BorderLayout());

		JPanel tempPanel2 = new JPanel();

		tempPanel2.add(l[0]);

		tempPanel2.add(l[6]);

		tempPanel2.add(competitionNameField);

		tempPanel2.add(back);

		tempPanel2.add(addCompetition);

		tempPanel.add(tempPanel2, BorderLayout.CENTER);
		
		teamLogoFrame.setAlwaysOnTop(false);

		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	/**
	 * Adds the User inputed competition to the data base and creates its files
	 */
	public void addCompetition() {

		config.put("Current Competition", competitionNameField.getText());

		if (competitions.size() == 1 && competitions.get(0) == null) {

			competitions.clear();

			competitions.add(competitionNameField.getText());

		} else {

			competitions.add(competitionNameField.getText());

		}

		competitionNameField.setText("");

		generateCompetitionFiles();

		mainMenu();

	}

	/**
	 * The add competition button is pressed when the user has inputed a new
	 * competition into the new competition menu
	 * 
	 * @return Returns the Add Competition Button
	 */
	public JButton getAddCompetitionButton() {

		return addCompetition;

	}

	/**
	 * Generates the files for a new competiton
	 * 
	 * Files Generated: /Scouting Info/game year/competition/ /Scouting
	 * Info/game year/competition/team data/ /Scouting Info/game
	 * year/competition/Teams Scouted.txt
	 */
	private void generateCompetitionFiles() {
		// Adds the Competition Folder and All Needed Files

		File competitionFolder = new File(
				savePath + config.get("Game Year") + "/" + config.get("Current Competition") + "/");

		File teamData = new File(
				savePath + config.get("Game Year") + "/" + config.get("Current Competition") + "/Team Data/");

		try {
			competitionFolder.mkdir();
			teamData.mkdir();
		} catch (SecurityException se) {
			System.out.println(se);
		}

		writer.writeFile(
				savePath + config.get("Game Year") + "/" + config.get("Current Competition") + "/Teams Scouted.txt",
				null);

		String[] temp = new String[competitions.size()];

		for (int i = 0; i < temp.length; i++) {

			temp[i] = competitions.get(i);

		}

		writer.writeFile(savePath + config.get("Game Year") + "/Competitions.txt", temp);

		String[] temp1 = new String[1];

		temp1[0] = config.get("Current Competition");

		writer.writeFile(savePath + config.get("Game Year") + "/currentComp.txt", temp1);

	}

	/**
	 * A small menu that the user specifies a metric to edit
	 */
	public void metricSelector() {

		utilityFrame = new JFrame("");

		JPanel tempPanel = new JPanel(new BorderLayout());

		JPanel tempPanel2 = new JPanel();

		metricSelectorMenu = new JComboBox();

		metricSelectorMenu.addItem("Please Select a Metric");

		metricSelectorMenu.addActionListener(myActionListener);

		for (int i = 0; i < metrics.size(); i++) {

			metricSelectorMenu.addItem(metrics.get(i).getName());

		}

		tempPanel2.add(metricSelectorMenu);

		tempPanel.add(back, BorderLayout.SOUTH);

		tempPanel.add(tempPanel2, BorderLayout.CENTER);
		
		teamLogoFrame.setAlwaysOnTop(false);

		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	/**
	 * 
	 * @return Returns the menu of Metrics the User can choose from
	 */
	public JComboBox getMetricSelectorMenu() {

		return metricSelectorMenu;

	}

	/**
	 * Prepares the menu to edit a User Specified Metric
	 * 
	 * @param index
	 *            The Index of the Metric the user wants to edit
	 */
	public void changeMetricMenu(int index) {

		utilityFrame = new JFrame("");

		index -= 1;

		changeMetricIndex = index;

		Metric temp = metrics.get(index);

		JPanel tempPanel = new JPanel(new BorderLayout());

		JPanel tempPanel2 = new JPanel(new GridBagLayout());

		JPanel tempPanel3 = new JPanel(new BorderLayout());

		// g.insets = new Insets(0,0,0,0);
		g.gridx = 0;
		g.gridy = 0;
		JLabel[] labels = new JLabel[6];
		for (int i = 0; i < 5; i++) {

			labels[i] = new JLabel("");

		}

		metricTextFields[0] = new JTextField(15); // Set Name

		labels[0].setText("Set Name");

		metricTextFields[1] = new JTextField(2); // Set Magnitude

		labels[1].setText("Set Magnitude");

		metricTextFields[2] = new JTextField(2); // Set Minimum Value

		labels[2].setText("Set Minimum Value");

		metricTextFields[3] = new JTextField(2); // Set Max Value

		labels[3].setText("Set Maximum Value");

		labels[4].setText("Change Input Type");

		metricTextFields[0].setText(metrics.get(index).getName());

		metricTextFields[1].setText("" + metrics.get(index).getMag());

		metricTextFields[2].setText("" + metrics.get(index).getMinValue());

		metricTextFields[3].setText("" + metrics.get(index).getMaxValue());

		JLabel title = new JLabel("Change " + temp.getName() + " Menu");

		title.setFont(new Font("Arial", Font.BOLD, 30));

		tempPanel2.add(title, g);

		String[] IisArray = { "Select Input Method Here", "Text Box", "Buttons", "Slider", "Not an Option, WIP" };// "Radio
																													// Buttons"

		m = new JComboBox(IisArray);

		m.setSelectedItem(temp.getInputType());

		/*
		 * boxMe = new JComboBox();
		 * 
		 * if (temp.getInputType() == "WIP") {// "Radio Buttons"
		 * 
		 * labels[5].setText("Add new Button");
		 * 
		 * metricTextFields[4] = new JTextField(15);
		 * 
		 * for (int i = 0; i < temp.getOptionsSize(); i++) {
		 * 
		 * boxMe.addItem(temp.getOption(i));
		 * 
		 * }
		 * 
		 * }
		 */

		g.gridx = 0;

		g.gridy = 60;

		for (int i = 0; i < metricTextFields.length; i++) {

			g.gridy = 100 + i * 50;

			g.gridx = 0;

			tempPanel2.add(labels[i], g);

			g.gridx = 20;

			tempPanel2.add(metricTextFields[i], g);

		}

		g.gridy = 500;

		g.gridx = 0;

		tempPanel2.add(labels[4], g);

		g.gridx = 20;

		tempPanel2.add(m, g);

		tempPanel.add(tempPanel2, BorderLayout.NORTH);

		saveMetric.addActionListener(myActionListener);

		tempPanel3.add(saveMetric, BorderLayout.WEST);

		tempPanel3.add(back, BorderLayout.EAST);

		tempPanel.add(tempPanel3, BorderLayout.SOUTH);
		
		teamLogoFrame.setAlwaysOnTop(false);

		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	/**
	 * Saves the changes the user made to the metrics
	 */
	public void changeMetrics() {

		utilityFrame.dispose();

		writeMetrics();

		mainMenu();

	}

	/**
	 * 
	 * @return Returns the Text Fields used on the Change Metric Menu
	 */
	public JTextField[] getMetricTextFields() {

		return metricTextFields;

	}

	/**
	 * 
	 * @return Returns the index of the Metric the user wants to change
	 */
	public int getChangeMetricIndex() {

		return changeMetricIndex;

	}

	// All Labels assossiated with this are in the q array
	public void topTeams(String Stat) {
		teamLogoFrame.setAlwaysOnTop(false);
		g.insets = new Insets(1, 1, 1, 1);
		int x = 0;
		int y = 10;
		g.gridx = x;
		g.gridy = y;
		g.gridwidth = 4;
		JPanel tempPanel = new JPanel(new BorderLayout());
		JPanel tempPanel2 = new JPanel(new GridBagLayout());
		utilityFrame = new JFrame("");
		
		int[][] topTeams = TopStats.getAverageStats(Stat);
		
		JLabel[] q = new JLabel[31];
		
		for(int i = 0; i < q.length; i++){
			
			q[i] = new JLabel("");
			
		}

		q[30].setFont(new Font("Impact", Font.BOLD, 40));

		q[30].setText("Top " + Stat);
		tempPanel2.add(q[30], g);
		g.gridwidth = 1;
		x = 4;
		y = 50;
		int bound = (topTeams.length / 2);
		if (bound > 20) {
			
			bound = 20;

		}

		for (int i = 0; i < (bound); i++) {

			if (x == 1) {

				x = 4;

			} else if (x == 4) {

				x = 1;
				y = (y + 1);

			}
			g.gridx = x;
			g.gridy = y;
			q[i].setText("Rank " + (i + 1) + ": " + topTeams[i][1]);
			tempPanel2.add(q[i], g);

		}
		tempPanel.add(tempPanel2, BorderLayout.NORTH);
		tempPanel.add(back, BorderLayout.SOUTH);
		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	public void topTeams() {
		teamLogoFrame.setAlwaysOnTop(false);
		g.insets = new Insets(1, 1, 1, 1);
		int x = 0;
		int y = 10;
		g.gridx = x;
		g.gridy = y;
		g.gridwidth = 4;
		JPanel tempPanel = new JPanel(new BorderLayout());
		JPanel tempPanel2 = new JPanel(new GridBagLayout());
		utilityFrame = new JFrame("");
		int[][] topTeams = TopStats.teamRankings();

		JLabel[] q = new JLabel[31];
		
		for(int i = 0; i < q.length; i++){
			
			q[i] = new JLabel("");
			
		}
		
		q[30].setFont(new Font("Impact", Font.BOLD, 40));

		q[30].setText("Top Teams");
		tempPanel2.add(q[30], g);
		g.gridwidth = 1;
		x = 4;
		y = 50;
		int bound = (topTeams.length / 2);
		if (bound > 30) {

			bound = 30;

		}

		for (int i = 0; i < (bound); i++) {

			if (x == 1) {

				x = 4;

			} else if (x == 4) {

				x = 1;
				y = (y + 1);

			}
			
			g.gridx = x;
			
			g.gridy = y;
			
			q[i].setText("Rank " + (i + 1) + ": " + topTeams[i][1]);
			
			tempPanel2.add(q[i], g);

		}
		
		tempPanel.add(tempPanel2, BorderLayout.NORTH);
		
		tempPanel.add(back, BorderLayout.SOUTH);
		
		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}
	
}
