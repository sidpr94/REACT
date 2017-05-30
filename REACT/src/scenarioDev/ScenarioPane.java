/*
 * 
 */
package scenarioDev;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenarioPane.
 */
public class ScenarioPane {
	
	/** The run. */
	JPanel run;
	
	/** The land. */
	JPanel land;
	
	/** The track. */
	JPanel track;
	
	/** The fleet. */
	JPanel fleet;
	
	/**
	 * Instantiates a new scenario pane.
	 *
	 * @param r the r
	 * @param s the s
	 * @param t the t
	 * @param q the q
	 */
	public ScenarioPane(JPanel r,JPanel s, JPanel t, JPanel q){
		this.run = r;
		this.land = s;
		this.track = t;
		this.fleet = q;
	}
	
	/**
	 * Creates the scenario pane.
	 *
	 * @return the j tabbed pane
	 */
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
