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

		JTextField rnEnh = new JTextField();
		rnEnh.setOpaque(false);
		rnEnh.setDisabledTextColor(Color.WHITE);
		rnEnh.setPreferredSize(new Dimension(250,30));
		rnEnh.setFont(new Font(rnEnh.getFont().getName(),Font.BOLD,14));
		rnEnh.setText("Density Control");
		rnEnh.setHorizontalAlignment(SwingConstants.CENTER);
		rnEnh.setBorder(BorderFactory.createMatteBorder(0,0,0,0,Color.BLACK));
		rnEnh.setEditable(false);
		rnEnh.setEnabled(false);
		dInputs.add(rnEnh);

		JButton perHighlight = new JButton();
		perHighlight.setText("Reset To Census");
		perHighlight.setHorizontalAlignment(SwingConstants.CENTER);
		perHighlight.setFont(new Font(perHighlight.getFont().getName(),Font.BOLD,12));

		JToggleButton perBlock = new JToggleButton();
		perBlock.setText("Control Density per Block");
		perBlock.setHorizontalAlignment(SwingConstants.CENTER);
		perBlock.setFont(new Font(perBlock.getFont().getName(),Font.BOLD,12));
		perBlock.addActionListener(new DensityOn(mainMap,perHighlight));

		dInputs.add(perBlock);
		dInputs.add(perHighlight);

		return dInputs;
	}
}
