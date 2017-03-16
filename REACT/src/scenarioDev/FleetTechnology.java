package scenarioDev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FleetTechnology {
	public FleetTechnology(){}
	
	public JPanel getFleet(){
		JPanel dInputs = new JPanel();
		GridLayout gLayout = new GridLayout(5,1);
		gLayout.setVgap(3);
		dInputs.setLayout(gLayout);
		dInputs.setBackground(new Color(0,0,0,100));
		dInputs.setBorder(BorderFactory.createEmptyBorder());
		
		JTextField rnEnh = new JTextField();
		rnEnh.setBackground(new Color(0,0,0,100));
		rnEnh.setPreferredSize(new Dimension(250,30));
		rnEnh.setFont(new Font(rnEnh.getFont().getName(),Font.BOLD,12));
		rnEnh.setText("Fleet Technology Insertion");
		rnEnh.setHorizontalAlignment(SwingConstants.CENTER);
		rnEnh.setBorder(BorderFactory.createMatteBorder(0, 0,3,0,Color.BLACK));
		rnEnh.setEditable(false);
		rnEnh.setEnabled(false);
		dInputs.add(rnEnh);
		
		JTextField fleet = new JTextField();
		fleet.setBackground(new Color(0,0,0,100));
		fleet.setPreferredSize(new Dimension(250,30));
		fleet.setFont(new Font(fleet.getFont().getName(),Font.BOLD,12));
		fleet.setText("List of Aircraft");
		fleet.setHorizontalAlignment(SwingConstants.CENTER);
		fleet.setBorder(BorderFactory.createMatteBorder(0, 0,3,0,Color.BLACK));
		fleet.setEditable(false);
		fleet.setEnabled(false);
		dInputs.add(fleet);
		
		JComboBox<String> fleetData = new JComboBox<>();
		fleetData.setModel(new DefaultComboBoxModel<>(new String[]{}));
		dInputs.add(fleetData);
		
		JTextField perRed = new JTextField();
		perRed.setBackground(new Color(0,0,0,100));
		perRed.setPreferredSize(new Dimension(250,30));
		perRed.setFont(new Font(perRed.getFont().getName(),Font.BOLD,12));
		perRed.setText("Noise Percent Reduction");
		perRed.setHorizontalAlignment(SwingConstants.CENTER);
		perRed.setBorder(BorderFactory.createMatteBorder(0, 0,3,0,Color.BLACK));
		perRed.setEditable(false);
		perRed.setEnabled(false);
		dInputs.add(perRed);
		
		JTextField percent = new JTextField();
		percent.setFont(new Font(percent.getFont().getName(),Font.PLAIN,12));
		dInputs.add(percent);
		
		return dInputs;
	}
}
