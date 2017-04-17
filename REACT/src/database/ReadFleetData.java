package database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReadFleetData {
	public ReadFleetData(){}

	public Object[][] getData(){
		BufferedReader br = null;
		String line = "";
		ArrayList<String[]> data = new ArrayList<String[]>();
		URL url = this.getClass().getClassLoader().getResource("Files/FleetData.csv");
		try {
			br = new BufferedReader(new FileReader(url.getPath()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while((line = br.readLine()) != null){
				String[] c = line.split(",");
				data.add(c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int r = data.size();
		int c = data.get(0).length;
		Object[][] dt = new Object[r-1][c];
		DecimalFormat df = new DecimalFormat("0.00000");
		for(int i = 0; i < r-1;i++){
			String ac = data.get(i+1)[0];
			String trackID = data.get(i+1)[1];
			String rnwy = data.get(i+1)[2];
			String op = data.get(i+1)[3];
			String dOp = df.format(Double.valueOf(data.get(i+1)[4]));
			String nOp = df.format(Double.valueOf(data.get(i+1)[5]));
			String noOp = df.format(Double.valueOf(data.get(i+1)[6]));
			dt[i][0] = ac;
			dt[i][1] = trackID;
			dt[i][2] = rnwy;
			dt[i][3] = op;
			dt[i][4] = dOp;
			dt[i][5] = nOp;
			dt[i][6] = noOp;
		}
		return dt;
	}
}
