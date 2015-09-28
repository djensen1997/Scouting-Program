package utilities;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import gui.*;

public class MyActionListener implements ActionListener {

	private Config currentConfig;

	private MainScreen main;

	private ArrayList<MatchScout> matches = new ArrayList<MatchScout>();

	public MyActionListener() {
	}

	public MyActionListener(Config currentConfig) {

		this.currentConfig = currentConfig;

	}

	public void setMainScreenRefrence(MainScreen mainScreen) {

		main = mainScreen;

	}

	public void addNewMatchScout(MatchScout match) {

		matches.add(match);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == currentConfig.getButton()
				|| e.getSource() == currentConfig.getT1()) {

			currentConfig.setTeamNumber(currentConfig.getT().getText());

			currentConfig.setGameYear(currentConfig.getT1().getText());

			currentConfig.setPassword(currentConfig.getAdminPasswordField()
					.getText());

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
			main.changeMetricMenu(main.getMetricSelectorMenu()
					.getSelectedIndex());

		if (e.getSource() == main.getFileItems()[5])
			main.metricSelector();

		if (e.getSource() == main.getSaveMetricButton()) {
			JTextField[] metricTextFields = main.getMetricTextFields();
			if (metricTextFields[0].getText().equals("")) {

			} else {

				main.getMetrics().get(main.getChangeMetricIndex())
						.setName(metricTextFields[0].getText()); // Set Name

			}

			if (metricTextFields[1].getText().equals("")) {

			} else {

				main.getMetrics()
						.get(main.getChangeMetricIndex())
						.setMag(Integer.parseInt(metricTextFields[1].getText())); // Set
				// Magnitude

			}

			if (metricTextFields[2].getText().equals("")) {

			} else {

				main.getMetrics()
						.get(main.getChangeMetricIndex())
						.setMinValue(
								Integer.parseInt(metricTextFields[2].getText())); // Set
				// Minimum
				// Value

			}

			if (metricTextFields[3].getText().equals("")) {

			} else {

				main.getMetrics()
						.get(main.getChangeMetricIndex())
						.setMaxValue(
								Integer.parseInt(metricTextFields[3].getText())); // Set
																					// Max
				// Value

			}

			main.changeMetrics();

		}

		if (e.getSource() == main.getReadButton()) {

			// main.sendTeamNumber();

		}
		//TODO Start Here
		if (matches.size() > 0) {
			
			for(int i = 0; i < matches.size(); i++){
			
			if (e.getSource() == matches.get(i).getSaveMatchButton()) {

				f.dispose();

				ArrayList<String> temp = new ArrayList<String>();

				for (int i = 0; i < main.getMetrics().size(); i++) {

					if (main.getMetrics().get(i).getInputType().equals("Buttons")) {

						temp.add(ml[i].getText());

					}

					if (metrics.get(i).getInputType().equals("Slider")) {

						temp.add(ml[i].getText());

					}

					if (metrics.get(i).getInputType().equals("Text Box")) {

						temp.add(mtf[i].getText());

					}

				}

				teams.get(teamNumber).add(temp);

				rewrite();
			}

			for (int i = 0; i < mb.length; i++) {

				if (e.getSource() == mb[i][0]) {

					int temp = Integer.parseInt(ml[i].getText());

					if (!(temp == metrics.get(i).getMinValue())) {

						temp = temp - 1;

					}

					ml[i].setText("" + temp);

				}

				if (e.getSource() == mb[i][1]) {

					int temp = Integer.parseInt(ml[i].getText());

					if (!(temp == metrics.get(i).getMaxValue())) {

						temp = temp + 1;

					}

					ml[i].setText("" + temp);
				}

			}
			
			}

		}

	}

}
