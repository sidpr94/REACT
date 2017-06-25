/*
 * 
 */
package basemap;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.GraphicsLayer;
import com.opencsv.CSVReader;

import angim.ForecastScenarios;

// TODO: Auto-generated Javadoc
/**
 * The Class Runway creates the runways for Kansas City International Airport: 01L-19R, 01R-19L, 09-27.
 * @author Sidharth Prem
 * @see basemap.CreateMainMap
 */
public class Runway{
	
	/**
	 * Instantiates a new runway.
	 */
	public Runway(){};
	
	/**
	 * Creates the runways based on coordinates of the runway ends.
	 * MCI Specific need to fix
	 * @return the runway graphics layer 
	 * @throws IOException 
	 */
	public GraphicsLayer createRunway() throws IOException{
		File file = new File("Files/RunwayInformation.csv");
		CSVReader reader = new CSVReader(new FileReader(file), ',');
		GraphicsLayer runways = new GraphicsLayer();
		List<String[]> csvBody = reader.readAll();
		reader.close();
		ForecastScenarios scenario = new ForecastScenarios();
		String airportName = scenario.getAirportName();
		for(int j = 0; j < csvBody.size(); j++){
			if(csvBody.get(j)[0].equals(airportName)){
				for(int i = 4; i < 19; i=i+2){
					if(!csvBody.get(j)[i].equals("")){
						Polyline runway = new Polyline();
						runway.startPath(Double.parseDouble(csvBody.get(j)[i+32]),Double.parseDouble(csvBody.get(j)[i+16]));
						runway.lineTo(Double.parseDouble(csvBody.get(j)[i+33]),Double.parseDouble(csvBody.get(j)[i+17]));
						Map<String,Object> attr = new HashMap<String, Object>();
						if(Double.parseDouble(csvBody.get(j)[i+32]) < 0){
							if(Double.parseDouble(csvBody.get(j)[i+16]) < 0){
								attr.put("Start", csvBody.get(j)[i+32]+" S, "+csvBody.get(j)[i+16]+" W");
							}else{
								attr.put("Start", csvBody.get(j)[i+32]+" S, "+csvBody.get(j)[i+16]+" E");
							}
						}else{
							if(Double.parseDouble(csvBody.get(j)[i+16]) < 0){
								attr.put("Start", csvBody.get(j)[i+32]+" N, "+csvBody.get(j)[i+16]+" W");
							}else{
								attr.put("Start", csvBody.get(j)[i+32]+" N, "+csvBody.get(j)[i+16]+" E");
							}
						}
						if(Double.parseDouble(csvBody.get(j)[i+33]) < 0){
							if(Double.parseDouble(csvBody.get(j)[i+17]) < 0){
								attr.put("End", csvBody.get(j)[i+33]+" S, "+csvBody.get(j)[i+17]+" W");
							}else{
								attr.put("End", csvBody.get(j)[i+33]+" S, "+csvBody.get(j)[i+17]+" E");
							}
						}else{
							if(Double.parseDouble(csvBody.get(j)[i+17]) < 0){
								attr.put("End", csvBody.get(j)[i+33]+" N, "+csvBody.get(j)[i+17]+" W");
							}else{
								attr.put("End", csvBody.get(j)[i+33]+" N, "+csvBody.get(j)[i+17]+" E");
							}
						}
						attr.put("Runway Name", csvBody.get(j)[i]+"-"+csvBody.get(j)[i+1]);
						Graphic runwayGraphic = new Graphic(runway,new SimpleLineSymbol(Color.BLACK,5),attr);
						runways.addGraphic(runwayGraphic);
					}
				}
			}
		}
		return runways;
	}
}
