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

public class ForecastOperation {
	JComboBox<String> op;
	public static List<String[]> origData = new ArrayList<String[]>();
	public ForecastOperation(JComboBox<String> op){
		this.op = op;
	}
	public void updateOperations() throws IOException{
		File inputFile = new File("CONFIG/CaseList.csv");
		CSVReader reader = new CSVReader(new FileReader(inputFile),',');
		List<String[]> csvBody = reader.readAll();
		if(op.getModel().getSelectedItem() == "2015 Operations"){
			csvBody.get(1)[1] = "KMCI_2015_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Nominal TAF"){
			csvBody.get(1)[1] = "KMCI_2020_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Below Nominal TAF"){
			csvBody.get(1)[1] = "KMCI_2020L_REACT";
		}else if(op.getModel().getSelectedItem() == "2020 Above Nominal TAF"){
			csvBody.get(1)[1] = "KMCI_2020H_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Nominal TAF"){
			csvBody.get(1)[1] = "KMCI_2030_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Below Nominal TAF"){
			csvBody.get(1)[1] = "KMCI_2020L_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Above Nominal TAF"){
			csvBody.get(1)[1] = "KMCI_2020H_REACT";
		}
		reader.close();
		CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.NO_ESCAPE_CHARACTER,System.getProperty("line.separator"));
		writer.writeAll(csvBody);
		writer.flush();
		writer.close();
	}
	public void insertFleetTechnology() throws IOException{
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
		origData = csvBody;
		HashMap<String,Number> fleetToChange = InsertFleetTechnology.fleetChanged;
		File ackey = new File(this.getClass().getClassLoader().getResource("Files/ACKey.csv").getPath());
		BufferedReader rdr = new BufferedReader(new FileReader(ackey));
		HashMap<String,String> ac = new HashMap<String,String>();
		for(String line = rdr.readLine();line != null; line = rdr.readLine()){
			String[] tokens = line.split(",");
			ac.put(tokens[0], tokens[1]);
		}
		rdr.close();
		if(!fleetToChange.isEmpty()){
			for(Map.Entry<String,Number> entry : fleetToChange.entrySet()){
				String key = entry.getKey();
				double percent = (double) entry.getValue();
				String code = ac.get(key);
				for(int j = 1; j < csvBody.size()-1;j++){
					if(csvBody.get(j)[0].substring(0,code.length()).equals(code)){
						csvBody.get(j)[2] = String.valueOf((Double.parseDouble(csvBody.get(j)[2])*(1-percent/100)));
						csvBody.get(j)[3] = String.valueOf((Double.parseDouble(csvBody.get(j)[3])*(1-percent/100)));
						csvBody.get(j)[4] = String.valueOf((Double.parseDouble(csvBody.get(j)[4])*(1-percent/100)));
						csvBody.get(j)[5] = String.valueOf((Double.parseDouble(csvBody.get(j)[5])*(1-percent/100)));
					}
				}
			}
		}
		reader.close();
		CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.NO_ESCAPE_CHARACTER,System.getProperty("line.separator"));
		writer.writeAll(csvBody);
		writer.flush();
		writer.close();
	}
}
