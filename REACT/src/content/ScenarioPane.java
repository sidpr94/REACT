package content;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ScenarioPane {
	JPanel run;
	JPanel land;
	JPanel track;
	JPanel fleet;
	public ScenarioPane(JPanel r,JPanel s, JPanel t, JPanel q){
		this.run = r;
		this.land = s;
		this.track = t;
		this.fleet = q;
	}
	public JTabbedPane createScenarioPane(){
		JTabbedPane scenarioPane = new JTabbedPane();
		scenarioPane.setOpaque(false);
		scenarioPane.setBorder(BorderFactory.createEmptyBorder());
		scenarioPane.setFont(new Font("Dialog",Font.BOLD,12));
		scenarioPane.addTab("Runway    ", run);
		scenarioPane.addTab("Density    ", land);
		scenarioPane.addTab("Track    ", track);
		scenarioPane.addTab("Fleet    ", fleet);
		return scenarioPane;
	}
}
