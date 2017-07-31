/*
 * 
 */
package scenarioDev.fleet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import angim.ForecastScenarios;

// TODO: Auto-generated Javadoc
/**
 * The Class InsertFleetTechnology controls the logic for how fleet technology is inserted as well as how it is removed.
 * @author Sidharth Prem
 * @see scenarioDev.fleet.FleetTechnology
 */
public class InsertFleetTechnology implements ActionListener{
	
	/** The unique aircraft fleet combobox. */
	JComboBox<String> fleet;
	
	/** The operational forecasting information to know which flight schedule from ANGIM to use. */
	JComboBox<String> op;
	
	/** The percentage number to reduce the noise by. */
	JTextField percent;
	
	/** The button that inserts a fleet technology to a unique aircraft. */
	JButton insert;
	
	/** The button that resets all fleet technology insertions. */
	JButton reset;
	
	/** The list of all fleet that changed and the percentage reduction corresponding to it. */
	public static HashMap<String,Number> fleetChanged = new HashMap<String,Number>();
	
	/**
	 * Instantiates a new insert fleet technology.
	 *
	 * @param start the start
	 * @param stop the stop
	 * @param fleet the fleet
	 * @param text the text
	 */
	public InsertFleetTechnology(JButton start,JButton stop,JComboBox<String> fleet,JTextField text){
		this.fleet = fleet;
		this.percent = text;
		this.insert = start;
		this.reset = stop;
	}
	
	/**
	 * Instantiates a new insert fleet technology.
	 *
	 * @param resetTech the reset tech
	 * @param op the op
	 */
	public InsertFleetTechnology(JButton resetTech, JComboBox<String> op){
		this.reset = resetTech;
		this.op = op;
	};
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	/**
	 * The action listener first checks to see if the button clicked is inserting a noise reduction or removing all technology insertions.
	 * If the button is adding noise reduction, it checks to see if the specific aircraft already has a technology added or not.
	 * If a technology is already added it simply changes the percentage value, otehrwise a new aircraft is placed into fleetChanged.
	 * If the button is to reset, the fleetChanged list is cleared and all technology inserted aircraft are replaced with originals.
	 * 
	 * All technology insertions are deonted by a '*' in the combobox.
	 */
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JButton source = (JButton) arg0.getSource();
		if(source.equals(insert)){
			if(percent.getText() != ""){
				if(!((String)fleet.getSelectedItem()).contains("*")){
					fleetChanged.put((String) fleet.getSelectedItem(), Double.parseDouble(percent.getText()));
				}else{
					fleetChanged.put(fleet.getSelectedItem().toString().substring(0,fleet.getSelectedItem().toString().length()-1), Double.parseDouble(percent.getText()));
				}
				String[] un = new String[fleet.getItemCount()];
				int selected = fleet.getSelectedIndex();
				for(int i = 0; i < fleet.getItemCount(); i++){
					if(i != fleet.getSelectedIndex()){
						un[i] = fleet.getItemAt(i);
					} else{
						if(!fleet.getItemAt(i).contains("*")){
							un[i] = fleet.getItemAt(i)+"*";
						}else{
							un[i] = fleet.getItemAt(i);
						}
					}
				}
				fleet.setModel(new JComboBox<>(un).getModel());
				fleet.setSelectedIndex(selected);
			}
		}
		else if(source.equals(reset)){
			fleetChanged.clear();
			String[] un = new String[fleet.getItemCount()];
			int selected = fleet.getSelectedIndex();
			for(int i = 0; i < fleet.getItemCount(); i++){
				if(fleet.getItemAt(i).contains("*")){
					un[i] = fleet.getItemAt(i).replace("*", "");
				}else{
					un[i] = fleet.getItemAt(i);
				}
			}
			fleet.setModel(new JComboBox<>(un).getModel());
			fleet.setSelectedIndex(selected);
		}
	}
	
	/**
	 * Empty fleet is used to simulate the button click of the reset button. It is used once calculations with ANGIM are done and technology insertions must be reset.
	 * @see angim.RunANGIM
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void emptyFleet() throws IOException{
		reset.doClick();
		List<String[]> data = ForecastScenarios.origData;
		String a = "";
		if(op.getModel().getSelectedItem() == "2015 Operations"){
			a = "KMCI_2015_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Nominal TAF"){
			a = "KMCI_2020_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Below Nominal TAF"){
			a = "KMCI_2020L_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Above Nominal TAF"){
			a = "KMCI_2020H_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Nominal TAF"){
			a = "KMCI_2030_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Below Nominal TAF"){
			a = "KMCI_2020L_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Above Nominal TAF"){
			a = "KMCI_2020H_REACT";
		}
		File inputFile = new File("IN/FlightSchedules/FlightSchedule_"+a+".csv");
		CSVReader reader = new CSVReader(new FileReader(inputFile),',');
		List<String[]> csvBody = reader.readAll();
		csvBody = data;
		reader.close();
		CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.NO_ESCAPE_CHARACTER,System.getProperty("line.separator"));
		writer.writeAll(csvBody);
		writer.flush();
		writer.close();
	}

}
