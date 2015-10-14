package gui;
import objects.*;
import java.util.*;

public class TopStats {
	
	private static TreeMap<String, ArrayList<ArrayList<String>>> teams;
	
	private static ArrayList<Metric> metrics;
	
	public TopStats(){
		
	}
	
	public static void setTeamMapRefrence(TreeMap<String, ArrayList<ArrayList<String>>> competingTeams){
		
		teams = competingTeams;
		
	}
	
	public static void setMetricsRefrence(ArrayList<Metric> Metrics){
		
		metrics = Metrics;
		
	}
	
	public static int getAverageScore(String teamNumber, int stat) {

		int s = 0;//stat, a temporary value

		int averageStat = 0;


		for (int i = 0; i < teams.get(teamNumber).size(); i++) {

			s += Integer.parseInt(teams.get(teamNumber).get(i).get(stat));

		}

		averageStat = (s / teams.get(teamNumber).size());

		return averageStat;

	}

	private static int[][] compileStats(Integer number) {

		int[][] desiredStats = new int[teams.size()][2];
		
		int index = 0;
		
		for (String teamNumber: teams.keySet()) {
			
			if (teams.get(teamNumber).size() > 0) {
				
				int s = 0;

				for (int i = 0; i < teams.get(teamNumber).size(); i++) {

					s += Integer.parseInt(teams.get(teamNumber).get(i).get(number));

				}

				desiredStats[index][0] = (s / teams.get(teamNumber).size());

				desiredStats[index][1] = Integer.parseInt(teamNumber);

				s = 0;

			}
			
			index+=1;

		}

		int max;

		int where = 0;
		for (index = 0; index < 20 && index < (desiredStats.length) / 2; index++) {

			int tempTeam = desiredStats[index][1];
			max = desiredStats[index][0];

			for (int i = (index + 1); i < desiredStats.length; i++) {

				if (max < desiredStats[i][0]) {

					max = desiredStats[i][0];
					where = i;
					tempTeam = desiredStats[i][1];

				}

			}

			desiredStats[where][0] = desiredStats[index][0];
			desiredStats[where][1] = desiredStats[index][1];
			desiredStats[index][0] = max;
			desiredStats[index][1] = tempTeam;

		}
		
		return desiredStats;

	}

	public static int[][] getAverageStats(String StatWanted) {
		
		int stat = 0;
		for (int i = 0; i < metrics.size(); i++) {

			if (metrics.get(i).getName().equals(StatWanted)) {

				stat = i;

				i = metrics.size();

			}

		}
		

		return compileStats(stat);

	}

	public static int[][] teamRankings() {

		int[][] desiredStats = new int[teams.size()][2];

		int totalScore = 0;
		
		int i = 0;

		for (String key: teams.keySet()) {
			
			desiredStats[i][1] = Integer.parseInt(key);

			totalScore = 0;

			for (int a = 0; a < teams.get(key).size(); a++) {

				for (int b = 0; b < metrics.size(); b++) {

					totalScore = totalScore
							+ (Integer.parseInt(teams.get(key).get(a).get(b)) * metrics
									.get(b).getMag());

				}

			}

			desiredStats[i][0] = totalScore;

			i += 1;
			
		}

		int max;

		int where = 0;

		for (int index = 0; index < 30 && index < (desiredStats.length) / 2; index++) {

			int tempTeam = desiredStats[index][1];

			max = desiredStats[index][0];

			for (int a = (index + 1); a < desiredStats.length; a++) {

				if (max < desiredStats[a][0]) {

					max = desiredStats[a][0];
					where = a;
					tempTeam = desiredStats[a][1];

				}

			}

			desiredStats[where][0] = desiredStats[index][0];
			desiredStats[where][1] = desiredStats[index][1];
			desiredStats[index][0] = max;
			desiredStats[index][1] = tempTeam;

		}

		return desiredStats;

	}

}
