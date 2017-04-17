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

public class FleetTechnology {
	public FleetTechnology(){}

	public JPanel getFleet(){
		ReadFleetData rd = new ReadFleetData();
		Object[][] data = rd.getData();
		String[] aircraft = new String[data.length];
		for(int i = 0; i < data.length; i++){
			aircraft[i] = (String) data[i][0];
		}
		String[] unAC = Arrays.stream(aircraft).toArray(String[]::new);

		JPanel dInputs = new JPanel();
		GridLayout gLayout = new GridLayout(6,1);
		gLayout.setVgap(3);
		dInputs.setLayout(gLayout);
		dInputs.setBackground(new Color(51,81,112));
		dInputs.setBorder(BorderFactory.createEmptyBorder());

		String[] un = new String[22];
		for(int i = 0; i<22;i++){
			un[i] = unAC[i];
		}
		JTextField rnEnh = new JTextField();
		rnEnh.setOpaque(false);
		rnEnh.setDisabledTextColor(Color.WHITE);
		rnEnh.setPreferredSize(new Dimension(250,30));
		rnEnh.setFont(new Font(rnEnh.getFont().getName(),Font.BOLD,14));
		rnEnh.setText("Fleet Technology Insertion");
		rnEnh.setHorizontalAlignment(SwingConstants.CENTER);
		rnEnh.setBorder(BorderFactory.createMatteBorder(0, 0,1,0,Color.WHITE));
		rnEnh.setEditable(false);
		rnEnh.setEnabled(false);
		dInputs.add(rnEnh);

		JTextField fleet = new JTextField();
		fleet.setOpaque(false);
		fleet.setDisabledTextColor(Color.WHITE);
		fleet.setPreferredSize(new Dimension(250,30));
		fleet.setFont(new Font(fleet.getFont().getName(),Font.BOLD,12));
		fleet.setText("List of Aircraft");
		fleet.setHorizontalAlignment(SwingConstants.CENTER);
		fleet.setBorder(BorderFactory.createMatteBorder(0, 0,0,0,Color.BLACK));
		fleet.setEditable(false);
		fleet.setEnabled(false);
		dInputs.add(fleet);

		JComboBox<String> fleetData = new JComboBox<>();
		fleetData.setModel(new DefaultComboBoxModel<>(un));
		AutoCompletion.enable(fleetData);
		dInputs.add(fleetData);

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
		dInputs.add(perRed);

		JTextField percent = new JTextField();
		percent.setFont(new Font(percent.getFont().getName(),Font.PLAIN,12));
		dInputs.add(percent);
		
		JButton perHighlight = new JButton();
		perHighlight.setText("Add Noise Reduction");
		perHighlight.setHorizontalAlignment(SwingConstants.CENTER);
		perHighlight.setFont(new Font(perHighlight.getFont().getName(),Font.BOLD,12));
		dInputs.add(perHighlight);

		return dInputs;
	}
}
