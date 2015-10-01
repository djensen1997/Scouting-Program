package utilities;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import gui.*;
/**
 * The Action Listener for the entire Program
 * 
 * @author DaneJensen
 *
 */
public class MyActionListener implements ActionListener {

	private Config currentConfig;

	private MainScreen main;

	private ArrayList<MatchScout> matches = new ArrayList<MatchScout>();
	/**
	 * An empty constructor
	 */
	public MyActionListener() {
	}
	/**
	 * A constructor that sets the current config to the config class for the program
	 * 
	 * @param currentConfig The Config Class for the program
	 */
	public MyActionListener(Config currentConfig) {

		this.currentConfig = currentConfig;

	}

	/**
	 * Allows the user to set the main screen refrence so the action listener can call on objects
	 * in the main screen class
	 * 
	 * @param mainScreen the main screen class for the program
	 */
	public void setMainScreenRefrence(MainScreen mainScreen) {

		main = mainScreen;

	}

	/**
	 * Allows the user to add a new match scout class to the action listener, adds that
	 * match to an ArrayList of MatchScouts because the user can have multiple open
	 * at once
	 * 
	 * @param match the MatchScout class being added to the action listener
	 */
	public void addNewMatchScout(MatchScout match) {

		matches.add(match);

	}

	/**
	 * The Action Performed method for the entire program, handles Action Events from all classes
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == currentConfig.getButton() || e.getSource() == currentConfig.getT1()) {

			currentConfig.setTeamNumber(currentConfig.getT().getText());

			currentConfig.setGameYear(currentConfig.getT1().getText());

			currentConfig.setPassword(currentConfig.getAdminPasswordField().getText());

			currentConfig.writeConfig();

			currentConfig.launchProgram();

		}

		if (e.getSource() == main.getAddMetricButton())
			main.saveMetric();

		if (e.getSource() == main.getFileItems()[3])
			main.metricMenu();

		if (e.getSource() == main.getFileItems()[1])
			main.newGameYearMenu();

		if (e.getSource() == main.getAddGameYearButton())
			main.addGameYear();

		if (e.getSource() == main.getBackButton())
			main.mainMenu();

		if (e.getSource() == main.getFileItems()[2])
			main.newCompetitionMenu();

		if (e.getSource() == main.getAddCompetitionButton())
			main.addCompetition();

		if (e.getSource() == main.getMetricSelectorMenu())
			main.changeMetricMenu(main.getMetricSelectorMenu().getSelectedIndex());

		if (e.getSource() == main.getFileItems()[5])
			main.metricSelector();

		for(int i = 0; i < main.getMetrics().size(); i++){
			
			if(e.getSource() == main.getTopStatsItems()[i]){
				
				main.topTeams(main.getMetrics().get(i).getName());
				
			}
			
		}
		
		if(e.getSource() == main.getTopStatsItems()[main.getMetrics().size()]){
			
			main.topTeams();
			
		}
		
		if (e.getSource() == main.getSaveMetricButton()) {
			JTextField[] metricTextFields = main.getMetricTextFields();
			if (metricTextFields[0].getText().equals("")) {

			} else {

				main.getMetrics().get(main.getChangeMetricIndex()).setName(metricTextFields[0].getText()); // Set
																											// Name

			}

			if (metricTextFields[1].getText().equals("")) {

			} else {

				main.getMetrics().get(main.getChangeMetricIndex())
						.setMag(Integer.parseInt(metricTextFields[1].getText())); // Set
				// Magnitude

			}

			if (metricTextFields[2].getText().equals("")) {

			} else {

				main.getMetrics().get(main.getChangeMetricIndex())
						.setMinValue(Integer.parseInt(metricTextFields[2].getText())); // Set
				// Minimum
				// Value

			}

			if (metricTextFields[3].getText().equals("")) {

			} else {

				main.getMetrics().get(main.getChangeMetricIndex())
						.setMaxValue(Integer.parseInt(metricTextFields[3].getText())); // Set
																						// Max
				// Value

			}

			main.changeMetrics();

		}

		if (e.getSource() == main.getAddTeamButton()) {

			main.addTeam(main.getTeamNumberInputFields()[0].getText());

		}

		if (e.getSource() == main.getDoNotAddTeamButton())
			main.mainMenu();

		if (e.getSource() == main.getReadButton()) {

			for (int i = 0; i < main.getTeamNumberInputFields().length; i++) {

				main.sendTeamNumber(main.getTeamNumberInputFields()[i].getText());

			}

		}

		if (matches.size() > 0) {

			for (int i = 0; i < matches.size(); i++) {

				if (e.getSource() == matches.get(i).getSaveMatchButton()) {

					matches.get(i).hideWindow();

					ArrayList<String> temp = new ArrayList<String>();

					for (int index = 0; index < main.getMetrics().size(); index++) {

						if (main.getMetrics().get(i).getInputType().equals("Buttons")) {

							temp.add(matches.get(i).getMetricLabels()[index].getText());

						}

						if (main.getMetrics().get(index).getInputType().equals("Slider")) {

							temp.add(matches.get(i).getMetricLabels()[index].getText());

						}

						if (main.getMetrics().get(index).getInputType().equals("Text Box")) {

							temp.add(matches.get(i).getMetricTextFields()[index].getText());

						}

					}

					main.getAllTeamInfo().get(matches.get(i).getTeamNumber()).add(temp);

					matches.get(i).rewrite();
					
					matches.remove(i);
					break;

				}

				for (int index = 0; index < matches.get(i).getDuelMetricButtons().length; index++) {

					if (e.getSource() == matches.get(i).getDuelMetricButtons()[index][0]) {

						int temp = Integer.parseInt(matches.get(i).getMetricLabels()[index].getText());

						if (!(temp == main.getMetrics().get(index).getMinValue())) {

							temp = temp - 1;

						}

						matches.get(i).getMetricLabels()[i].setText("" + temp);

					}

					if (e.getSource() == matches.get(i).getDuelMetricButtons()[i][1]) {

						int temp = Integer.parseInt(matches.get(i).getMetricLabels()[i].getText());

						if (!(temp == main.getMetrics().get(i).getMaxValue())) {

							temp = temp + 1;

						}

						matches.get(i).getMetricLabels()[i].setText("" + temp);
					}

				}

			}

		}

	}

}
