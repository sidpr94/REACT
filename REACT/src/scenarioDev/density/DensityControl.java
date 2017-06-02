/*
 * 
 */
package scenarioDev.density;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.esri.map.JMap;

// TODO: Auto-generated Javadoc
/**
 * The Class DensityControl creates the GUI panel for the density control mitigation strategy.
 * The panel contains two buttons that allow for control and resetting of the control.
 * @author Sidharth Prem
 * @see scenarioDev.ScenarioPane
 */
public class DensityControl {
	
	/** The main map. */
	JMap mainMap;
	
	/**
	 * Instantiates a new density control.
	 *
	 * @param jMap the j map
	 */
	public DensityControl(JMap jMap){
		mainMap = jMap;
	}
	
	/**
	 * Gets the density control buttons and adds action listeners to them.
	 *
	 * @return the density control panel
	 */
	public JPanel getDensity(){
		JPanel dInputs = new JPanel();
		GridLayout gLayout = new GridLayout(3,1);
		gLayout.setVgap(3);
		dInputs.setBackground(new Color(51,81,112));
		dInputs.setLayout(gLayout);
		dInputs.setBorder(BorderFactory.createEmptyBorder());

		JTextField dTitle = new JTextField();
		dTitle.setOpaque(false);
		dTitle.setDisabledTextColor(Color.WHITE);
		dTitle.setPreferredSize(new Dimension(250,30));
		dTitle.setFont(new Font(dTitle.getFont().getName(),Font.BOLD,14));
		dTitle.setText("Density Control");
		dTitle.setHorizontalAlignment(SwingConstants.CENTER);
		dTitle.setBorder(BorderFactory.createMatteBorder(0,0,0,0,Color.BLACK));
		dTitle.setEditable(false);
		dTitle.setEnabled(false);
		dInputs.add(dTitle);

		JButton resetToCensus = new JButton();
		resetToCensus.setText("Reset To Census");
		resetToCensus.setHorizontalAlignment(SwingConstants.CENTER);
		resetToCensus.setFont(new Font(resetToCensus.getFont().getName(),Font.BOLD,12));

		JToggleButton perBlock = new JToggleButton();
		perBlock.setText("Control Density per Block");
		perBlock.setHorizontalAlignment(SwingConstants.CENTER);
		perBlock.setFont(new Font(perBlock.getFont().getName(),Font.BOLD,12));
		perBlock.addActionListener(new DensityOn(mainMap,resetToCensus));

		dInputs.add(perBlock);
		dInputs.add(resetToCensus);

		return dInputs;
	}
}
