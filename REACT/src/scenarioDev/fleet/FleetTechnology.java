/*
 * 
 */
package scenarioDev.fleet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import GUI.AutoCompletion;
import database.ReadFleetData;

// TODO: Auto-generated Javadoc
/**
 * The Class FleetTechnology creates the GUI containing the auto completion combobox for all unique aircraft at MCI,
 * the text box to input percent noise reduction, and reset technology buttons.
 * @author Sidharth Prem
 * @see GUI.AutoCompletion
 * @see scenarioDev.ScenarioPane
 */
public class FleetTechnology {
	
	/** The reset button that allows users to reset all technology insertions. */
	JButton reset;
	
	/**
	 * Instantiates a new fleet technology.
	 *
	 * @param reset the reset
	 */
	public FleetTechnology(JButton reset){
		this.reset = reset;
	}

	/**
	 * Gets the fleet technology panel containing all GUI elements.
	 * MCI specific need to fix
	 * @return the fleet panel
	 */
	public JPanel getFleet(){
		ReadFleetData rd = new ReadFleetData();
		Object[][] data = rd.getData();
		String[] aircraft = new String[data.length];
		for(int i = 0; i < data.length; i++){
			aircraft[i] = (String) data[i][0];
		}
		String[] unAC = Arrays.stream(aircraft).toArray(String[]::new);

		JPanel fInputs = new JPanel();
		GridLayout gLayout = new GridLayout(7,1);
		gLayout.setVgap(3);
		fInputs.setLayout(gLayout);
		fInputs.setBackground(new Color(51,81,112));
		fInputs.setBorder(BorderFactory.createEmptyBorder());
		
		//Ensures that all aircraft are unique
		String[] un = new String[22];
		for(int i = 0; i<22;i++){
			un[i] = unAC[i];
		}
		JTextField fleetTitle = new JTextField();
		fleetTitle.setOpaque(false);
		fleetTitle.setDisabledTextColor(Color.WHITE);
		fleetTitle.setPreferredSize(new Dimension(250,30));
		fleetTitle.setFont(new Font(fleetTitle.getFont().getName(),Font.BOLD,14));
		fleetTitle.setText("Fleet Technology Insertion");
		fleetTitle.setHorizontalAlignment(SwingConstants.CENTER);
		fleetTitle.setBorder(BorderFactory.createMatteBorder(0, 0,1,0,Color.WHITE));
		fleetTitle.setEditable(false);
		fleetTitle.setEnabled(false);
		fInputs.add(fleetTitle);

		JTextField fleet = new JTextField();
		fleet.setOpaque(false);
		fleet.setDisabledTextColor(Color.WHITE);
		fleet.setPreferredSize(new Dimension(250,30));
		fleet.setFont(new Font(fleet.getFont().getName(),Font.BOLD,12));
		fleet.setText("List of Aircraft (* indicates technology insertion)");
		fleet.setHorizontalAlignment(SwingConstants.CENTER);
		fleet.setBorder(BorderFactory.createMatteBorder(0, 0,0,0,Color.BLACK));
		fleet.setEditable(false);
		fleet.setEnabled(false);
		fInputs.add(fleet);

		JComboBox<String> fleetData = new JComboBox<>();
		fleetData.setModel(new DefaultComboBoxModel<>(un));
		AutoCompletion.enable(fleetData);
		fInputs.add(fleetData);

		JTextField perRed = new JTextField();
		perRed.setOpaque(false);
		perRed.setDisabledTextColor(Color.WHITE);
		perRed.setPreferredSize(new Dimension(250,30));
		perRed.setFont(new Font(perRed.getFont().getName(),Font.BOLD,12));
		perRed.setText("Noise Percent Reduction");
		perRed.setHorizontalAlignment(SwingConstants.CENTER);
		perRed.setBorder(BorderFactory.createMatteBorder(0, 0,0,0,Color.BLACK));
		perRed.setEditable(false);
		perRed.setEnabled(false);
		fInputs.add(perRed);

		JTextField percent = new JTextField();
		percent.setFont(new Font(percent.getFont().getName(),Font.PLAIN,12));
		fInputs.add(percent);
		
		JButton noiseReduce = new JButton();
		noiseReduce.setText("Add Noise Reduction");
		noiseReduce.setHorizontalAlignment(SwingConstants.CENTER);
		noiseReduce.setFont(new Font(noiseReduce.getFont().getName(),Font.BOLD,12));
		noiseReduce.addActionListener(new InsertFleetTechnology(noiseReduce,reset,fleetData,percent));
		fInputs.add(noiseReduce);
		
		reset.setText("Reset Fleet Insertion");
		reset.setHorizontalAlignment(SwingConstants.CENTER);
		reset.setFont(new Font(reset.getFont().getName(),Font.BOLD,12));
		reset.addActionListener(new InsertFleetTechnology(noiseReduce,reset,fleetData,percent));
		fInputs.add(reset);

		return fInputs;
	}
}
