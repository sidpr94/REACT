/*
 * 
 */
package angim;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import scenarioDev.fleet.InsertFleetTechnology;
import scenarioDev.track.InfoOverlayTrack;

// TODO: Auto-generated Javadoc
/**
 * Edits all excel files for ANGIM and sets up the calculations for ANGIM to run.
 * Files edited: CONFIG/CaseList.csv, IN/FlightSchedules/...csv
 * @author Sidharth Prem
 * @see angim.CalculateNoise
 */
public class ForecastScenarios {
	
	/** The ComboBox containing Operation Forecasting Information. */
	JComboBox<String> op;
	
	/** The Original Data before fleet technology is inserted. */
	public static List<String[]> origData = new ArrayList<String[]>();
	
	/**
	 * Instantiates a new forecast operation.
	 *
	 * @param op the op
	 */
	public ForecastScenarios(JComboBox<String> op){
		this.op = op;
	}
	
	public ForecastScenarios(){
		
	}
	
	/**
	 * Ensures ANGIM uses the correct forecast year flight schedule by editing CaseList file
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateOperations() throws IOException{
		File inputFile = new File("CONFIG/CaseList.csv");
		CSVReader reader = new CSVReader(new FileReader(inputFile),',');
		List<String[]> csvBody = reader.readAll();
		String airportName = getAirportName();
		if(op.getModel().getSelectedItem() == "2015 Operations"){
			csvBody.get(1)[1] = airportName+"_2015_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Nominal TAF"){
			csvBody.get(1)[1] = airportName+"_2020_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Below Nominal TAF"){
			csvBody.get(1)[1] = airportName+"_2020L_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Above Nominal TAF"){
			csvBody.get(1)[1] = airportName+"_2020H_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Nominal TAF"){
			csvBody.get(1)[1] = airportName+"_2030_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Below Nominal TAF"){
			csvBody.get(1)[1] = airportName+"_2030L_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Above Nominal TAF"){
			csvBody.get(1)[1] = airportName+"_2030H_REACT";
		}
		reader.close();
		CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.NO_ESCAPE_CHARACTER,System.getProperty("line.separator"));
		writer.writeAll(csvBody);
		writer.flush();
		writer.close();
	}
	
	/**
	 * Insert fleet technology.
	 * Edit flight schedules for the appropriate Track variant inserted
	 * ACKey contains the official aircraft title versus ANGIM recognized aircraft titles (i.e. Boeing 737 - B737).
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void insertFleetTechnology() throws IOException{
		String a = "";
		String airportName = getAirportName();
		if(op.getModel().getSelectedItem() == "2015 Operations"){
			a = airportName+"_2015_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Nominal TAF"){
			a = airportName+"_2020_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Below Nominal TAF"){
			a = airportName+"_2020L_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Above Nominal TAF"){
			a = airportName+"_2020H_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Nominal TAF"){
			a = airportName+"_2030_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Below Nominal TAF"){
			a = airportName+"_2020L_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Above Nominal TAF"){
			a = airportName+"_2020H_REACT";
		}
		File inputFile = new File("IN/FlightSchedules/FlightSchedule_"+a+".csv");
		CSVReader reader = new CSVReader(new FileReader(inputFile),',');
		List<String[]> csvBody = reader.readAll();
		origData = csvBody;
		//fleetChanged contains the aircraft that are currently edited
		HashMap<String,Number> fleetToChange = InsertFleetTechnology.fleetChanged;
		File ackey = new File("Files/ACKey.csv");
		BufferedReader rdr = new BufferedReader(new FileReader(ackey));
		HashMap<String,String> ac = new HashMap<String,String>();
		for(String line = rdr.readLine();line != null; line = rdr.readLine()){
			String[] tokens = line.split(",");
			ac.put(tokens[0], tokens[1]);
		}
		rdr.close();
		HashMap<String,String> ch = InfoOverlayTrack.changed;
		if(!fleetToChange.isEmpty()){
			for(Map.Entry<String,Number> entry : fleetToChange.entrySet()){
				String key = entry.getKey();
				double percent = (double) entry.getValue();
				String code = ac.get(key);
				for(int j = 1; j < csvBody.size()-1;j++){
					//Technology is inserted as a percent change to operations
					if(csvBody.get(j)[0].substring(0,code.length()).equals(code)){
						csvBody.get(j)[2] = String.valueOf((Double.parseDouble(csvBody.get(j)[2])*(1-percent/100)));
						csvBody.get(j)[3] = String.valueOf((Double.parseDouble(csvBody.get(j)[3])*(1-percent/100)));
						csvBody.get(j)[4] = String.valueOf((Double.parseDouble(csvBody.get(j)[4])*(1-percent/100)));
						csvBody.get(j)[5] = String.valueOf((Double.parseDouble(csvBody.get(j)[5])*(1-percent/100)));
					}
				}
			}
		}
		//HERE IS WHERE TRACK CHANGES ARE INSERTED
		if(!ch.isEmpty()){
			for(Map.Entry<String,String> entry : ch.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue();
				for(int j = 1; j < csvBody.size()-1;j++){
					if(csvBody.get(j)[0].contains("_"+key+"_")){
						csvBody.get(j)[0] = csvBody.get(j)[0].replace("_"+key+"_", "_"+value+"_");
						System.out.println(csvBody.get(j)[0]);
					}
				}
			}
		}
		reader.close();
		//This is to make sure the strings aren't in quotes, which ANGIM can't recognize
		CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.NO_ESCAPE_CHARACTER,System.getProperty("line.separator"));
		writer.writeAll(csvBody);
		writer.flush();
		writer.close();
	}
	
	/** 
	 * This method retrieves the airport name 
	 * @throws IOException 
	 */
	public String getAirportName() throws IOException{
		File inputFile = new File("CONFIG/CaseList.csv");
		CSVReader reader = new CSVReader(new FileReader(inputFile),',');
		List<String[]> csvBody = reader.readAll();
		reader.close();
		return csvBody.get(1)[2];
	}
}
