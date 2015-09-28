package gui;

import objects.*;

import java.awt.*;
import java.io.*;
import java.util.*;

import utilities.*;

import javax.swing.*;

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

	private String savePath = "/Users/" + System.getProperty("user.name")
			+ "/Documents/Scouting Info/";

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
	
	public MainScreen(MyActionListener myActionListener) {

		this.myActionListener = myActionListener;
		
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

		f = new JFrame("Team " + config.get("Team Number")
				+ " Scouting Readout" + config.get("Current Competition"));

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		c = f.getContentPane();

		mainPanel = new JPanel(new BorderLayout());

		mainScreen = new JPanel(new BorderLayout());

		mainMenu();

	}

	public JButton getReadButton() {

		return read;

	}

	public TreeMap<String, ArrayList<ArrayList<String>>> getAllTeamInfo() {

		return competingTeamsInfo;

	}

	/*public void sendTeamNumber() {

		if (!competingTeamsInfo.containsKey(teamNumber)) {

			newTeamMenu();

		} else {

			if (true) {//will change eventually

				data = new teamData(team, here, stats, gameYear,
						currentCompetition, x);

			}/*
			 * else if (Mode.equals("Pit")) {
			 * 
			 * @SuppressWarnings("unused") pit_scouting scout = new
			 * pit_scouting(team.get(here), gameYear, currentCompetition);
			 * 
			 * }
			 

			// previous = teamNumber;

		}

	}*/

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
		

	}

	/*public void getTeams() {

		String[] temp = reader.readFile(savePath + config.get("Game Year")
				+ "/" + config.get("Current Competition")
				+ "/Teams Scouted.txt");

		for (int i = 0; i < temp.length; i++) {

			competingTeamsInfo.put(temp[i], new ArrayList<ArrayList<String>>());

		}

		for (String key : competingTeamsInfo.keySet()) {

			String[] teamInfo = reader.readFile(savePath
					+ config.get("Game Year") + "/"
					+ config.get("Current Competition") + "/Team Data/" + key
					+ ".txt");

			try {

				//TODO figure this mess out
				String num;
				while ((num = fr.readLine()) != null) {
					// skipLine = true;
					ArrayList<String> temp = new ArrayList<String>();
					String[] pa = num.split(": ");
					if (!(pa[0].equals("Match Number"))) {
						temp.add(pa[1]);
					}
					for (int i = 0; i < metrics.size(); i++) {
						String line = fr.readLine();
						// String delims = "{: }";
						if (line != null) {
							String[] parts = line.split(": ");
							if (parts[0].equals("Match Number")) {

								i = metrics.size();

							} else {
								temp.add(parts[1]);
							}
						} else {

							i = metrics.size();
							// skipLine = false;

						}

					}
					// if(skipLine){

					// fr.readLine();

					// }
					fr.readLine();
					fr.readLine();
					team.add(temp);
				}
				fr.close();
			} catch (Exception e) {
				System.err.println("Error Alert ----->    " + e.getMessage());
			}
			teams.add(team);
		}

	}*/
	

	private void getInformation() {

		String[] temp = reader.readFile(savePath + "Config.txt", ": ");

		config.put("Team Number", temp[2]);

		config.put("Game Year", temp[1]);

		config.put("Teams Scouted at Once", temp[3]);

		config.put("Admin Password", temp[0]);

		getGameYears();

		readMetrics();

		getCompetitions();

		prepMenuBar();

	}

	private void readMetrics() {
		metrics.clear();

		String[] temp = reader.readFile(savePath + config.get("Game Year")
				+ "/metrics/metrics.txt");

		for (int i = 0; i < temp.length; i++) {

			metrics.add(new Metric(temp[i]));

		}

		for (int i = 0; i < metrics.size(); i++) {

			String[] metricInfo = reader.readFile(
					savePath + config.get("Game Year") + "/metrics/"
							+ metrics.get(i).getName() + ".txt", ": ");

			if (metricInfo.length < 4) {

			} else {

				metrics.get(i).setMag(Integer.parseInt(metricInfo[0]));

				metrics.get(i).setInputType(metricInfo[1]);

				metrics.get(i).setMaxValue(Integer.parseInt(metricInfo[2]));

				metrics.get(i).setMinValue(Integer.parseInt(metricInfo[3]));

				if (metricInfo.length > 4) {// WIP, combo box

					for (int index = 4; index < metricInfo.length; index += 1) {

						metrics.get(i).addOption(metricInfo[index]);

					}

				}

			}

		}

	}

	private void getCompetitions() {

		competitions.clear();

		String[] temp = reader.readFile(savePath + config.get("Game Year")
				+ "/Competitions.txt");

		for (int i = 0; i < temp.length; i++) {

			competitions.add(temp[i]);

		}

		config.put(
				"Current Competition",
				reader.readFile(savePath + config.get("Game Year")
						+ "/currentComp.txt")[0]);

	}

	public JMenuItem[] getFileItems() {

		return fileItems;

	}

	public JMenuItem[] getModeItems() {

		return modeItems;

	}

	public JMenuItem[] getCompetitionItems() {

		return competitionItems;

	}

	public JMenuItem[] getTopStatsItems() {

		return topStatsItems;

	}

	private void prepMenuBar() {

		file = new JMenu("File");

		topStats = new JMenu("Top Stats");

		competition = new JMenu("Select Competition");

		mode = new JMenu("Scouting Mode");

		fileItems = new JMenuItem[6];

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

	@SuppressWarnings("unused")
	public void mainMenu() {

		try {

			utilityFrame.dispose();

		} catch (Exception e) {

		}

		f.setTitle(config.get("Team Number") + " Scouting Readout - "
				+ config.get("Current Competition"));

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

		tempPanel.setBackground(Color.white);

		mainScreen.add(tempPanel, BorderLayout.NORTH);

		mainPanel.add(mainScreen, BorderLayout.CENTER);

		mainPanel.add(mainBar, BorderLayout.NORTH);

		mainPanel.setBackground(Color.pink);

		c.add(mainPanel);

		f.setLocation(0, 0);

		f.setExtendedState(JFrame.MAXIMIZED_BOTH);

		f.validate();

		f.setVisible(true);

	}

	public void metricMenu() {

		utilityFrame = new JFrame("");

		JPanel tempPanel = new JPanel(new BorderLayout());

		JPanel tempPanel2 = new JPanel();

		tempPanel2.add(new JLabel("New Metric Menu"));

		tempPanel2.add(l[3]);

		tempPanel2.add(metricName);

		tempPanel2.add(l[4]);

		tempPanel2.add(metricWeight);

		String[] IisArray = { "Select Input Method Here", "Text Box",
				"Buttons", "Slider", "Radio Buttons" };

		m = new JComboBox(IisArray);

		m.addActionListener(myActionListener);

		tempPanel2.add(m);

		addMetric.addActionListener(myActionListener);

		tempPanel2.add(addMetric);

		tempPanel.add(tempPanel2, BorderLayout.CENTER);

		tempPanel.add(back, BorderLayout.SOUTH);

		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	public JButton getAddMetricButton() {

		return addMetric;

	}

	public JButton getSaveMetricButton() {

		return saveMetric;

	}

	public ArrayList<Metric> getMetrics() {

		return metrics;

	}

	public void saveMetric() {

		if (metrics.size() == 1 && metrics.get(0).getName() == null) {

			metrics.get(0).setName(metricName.getText());

		} else {

			metrics.add(new Metric(metricName.getText()));

		}
		metrics.get(metrics.size() - 1).setInputType(
				(String) m.getSelectedItem());

		metrics.get(metrics.size() - 1).setMag(
				Integer.parseInt(metricWeight.getText()));

		metrics.get(metrics.size() - 1).setMaxValue(100);

		metrics.get(metrics.size() - 1).setMinValue(0);

		utilityFrame.dispose();

		writeMetrics();

		mainMenu();

	}

	private void writeMetrics() {

		String[] temp = new String[metrics.size()];

		for (int i = 0; i < metrics.size(); i++) {

			temp[i] = metrics.get(i).getName();

		}

		writer.writeFile(savePath + config.get("Game Year")
				+ "/metrics/metrics.txt", temp);

		for (int i = 0; i < metrics.size(); i++) {

			writer.writeFile(savePath + config.get("Game Year") + "/metrics/"
					+ metrics.get(i).getName() + ".txt", metrics.get(i)
					.getInfo());

		}

	}

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

		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	public JButton getAddGameYearButton() {

		return addGameYear;

	}

	public JButton getBackButton() {

		return back;

	}

	public void addGameYear() {

		utilityFrame.dispose();

		gameYears.add(gameYearField.getText());

		config.put("Game Year", gameYearField.getText());

		writeGameYears();

		generateGameYearFiles();

		changeGameYear(gameYearField.getText());

		gameYearField.setText("");

	}

	private void writeGameYears() {

		String[] temp = new String[gameYears.size()];

		for (int i = 0; i < temp.length; i++) {

			temp[i] = gameYears.get(i);

		}

		writer.writeFile(savePath + "Years.txt", temp);

	}

	private void changeGameYear(String GameYear) {

		config.put("Game Year", GameYear);

		readMetrics();

		getCompetitions();

		saveConfig();

		prepMenuBar();

		mainMenu();

	}

	private void generateGameYearFiles() {

		String user = System.getProperty("user.name");

		File gameYearFolder = new File("/Users/" + user
				+ "/Documents/Scouting Info/" + config.get("Game Year") + "/");

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

			writer.writeFile(savePath + config.get("Game Year")
					+ "/metrics/metrics.txt", null);

		}

	}

	private void getGameYears() {
		gameYears.clear();

		String[] temp = (reader.readFile(savePath + "Years.txt"));

		for (int i = 0; i < temp.length; i++) {

			gameYears.add(temp[i]);

		}

	}

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

		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

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

	public JButton getAddCompetitionButton() {

		return addCompetition;

	}

	private void generateCompetitionFiles() {
		// Adds the Competition Folder and All Needed Files

		File competitionFolder = new File(savePath + config.get("Game Year")
				+ "/" + config.get("Current Competition") + "/");

		File teamData = new File(savePath + config.get("Game Year") + "/"
				+ config.get("Current Competition") + "/Team Data/");

		try {
			competitionFolder.mkdir();
			teamData.mkdir();
		} catch (SecurityException se) {
			System.out.println(se);
		}

		writer.writeFile(
				savePath + config.get("Game Year") + "/"
						+ config.get("Current Competition")
						+ "/Teams Scouted.txt", null);

		String[] temp = new String[competitions.size()];

		for (int i = 0; i < temp.length; i++) {

			temp[i] = competitions.get(i);

		}

		writer.writeFile(savePath + config.get("Game Year")
				+ "/Competitions.txt", temp);

		String[] temp1 = new String[1];

		temp1[0] = config.get("Current Competition");

		writer.writeFile(savePath + config.get("Game Year")
				+ "/currentComp.txt", temp1);

	}

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

		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	public JComboBox getMetricSelectorMenu() {

		return metricSelectorMenu;

	}

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

		String[] IisArray = { "Select Input Method Here", "Text Box",
				"Buttons", "Slider", "Not an Option, WIP" };// "Radio Buttons"

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

		utilityFrame.add(tempPanel);

		utilityFrame.setLocation(0, f.getHeight() / 3);

		utilityFrame.setSize(f.getWidth(), f.getHeight() * 2 / 3);

		utilityFrame.setUndecorated(true);

		utilityFrame.setVisible(true);

	}

	public void changeMetrics() {

		utilityFrame.dispose();

		writeMetrics();

		mainMenu();

	}

	public JTextField[] getMetricTextFields() {

		return metricTextFields;

	}

	public int getChangeMetricIndex() {

		return changeMetricIndex;

	}

}
