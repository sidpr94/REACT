package angim;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import com.opencsv.CSVReader;

public class AngimOut {
	int row;
	public AngimOut(int row){
		this.row = row;
	}
	
	public Object[] getAngimInfo() throws IOException{
		Object[] data = new Object[10];
		data[0] = row;
		File inputFile = new File(getFile()[1]);
		CSVReader reader = new CSVReader(new FileReader(inputFile),',');
		List<String[]> csvBody = reader.readAll();
		Double pop55 = Double.parseDouble(csvBody.get(1)[1]);
		data[1] = Math.ceil(pop55);
		Double pop60 = Double.parseDouble(csvBody.get(1)[2]);
		data[2] = Math.ceil(pop60);
		Double pop65 = Double.parseDouble(csvBody.get(1)[3]);
		data[3] = Math.ceil(pop65);
		reader.close();
		inputFile = new File(getFile()[0]);
		reader = new CSVReader(new FileReader(inputFile),',');
		csvBody = reader.readAll();
		DecimalFormat df = new DecimalFormat("0.000");
		data[4] = df.format(Double.parseDouble(csvBody.get(1)[1]));
		data[5] = df.format(Double.parseDouble(csvBody.get(1)[2]));
		data[6] = df.format(Double.parseDouble(csvBody.get(1)[3]));
		data[7] = "-";
		data[8] = "-";
		data[9] = "-";
		reader.close();
		return data;
	}
	
	public String[] getFile(){
		String[] urls = new String[2];
		File file = new File("OUT/Areas.csv");
		urls[0] = file.getAbsolutePath();
		File file1 = new File("OUT/PopulationOUT.csv");
		urls[1] = file1.getAbsolutePath();
		return urls;
	}
}
