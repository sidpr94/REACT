package angim;

import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class ForecastOperation {
	JComboBox<String> op;
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
}
