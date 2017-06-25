/*
 * 
 */
package angim;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;

import com.esri.core.geometry.Polygon;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.opencsv.CSVReader;

// TODO: Auto-generated Javadoc
/**
 * Updates or resets the contour in accordance to ANGIM output
 * @author Sidharth Prem
 * 
 * @see content.RunANGIM
 */
public class UpdateContour {
	
	/** The main map containing airport information. */
	JMap map;
	
	/** The ComboBox that contains operation forecasting information. */
	JComboBox<String> op;
	
	/** The state that describes whether contour is being reset or updated. */
	String state;
	
	/** The longitudinal reference of runway 01L. */
	static double long_ref = 0;
	
	/** The latitude reference of runway 01L. */
	static double lat_ref = 0;
	
	/** The radius of earth in nmi. */
	static double R_earth = 3440.069517;
	
	/** The rotational degree of 01L. */
	static double rot_deg = 0;
	
	/** The dnl55 color value for tte contour. */
	Color DNL55 = new Color(56,168,0);
	
	/** The dnl60 color value for tte contour. */
	Color DNL60 = new Color(0,92,230);
	
	/** The dnl65 color value for tte contour. */
	Color DNL65 = new Color(255,0,0);
	
	/** List of scenario noise contours to be associated with the scenario summary. */
	public static List<List<Graphic>> scenarioGraphics = new ArrayList<List<Graphic>>();
	
	/**
	 * Instantiates a new update contour.
	 *
	 * @param map the map
	 * @param op the op
	 * @param s the s
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public UpdateContour(JMap map, JComboBox<String> op, String s) throws IOException{
		this.map = map;
		this.op = op;
		this.state = s;
		ForecastScenarios scenario = new ForecastScenarios();
		String airportName = scenario.getAirportName();
		File file = new File("Files/RunwayInformation.csv");
		CSVReader reader = new CSVReader(new FileReader(file),',');
		List<String[]> csvBody = reader.readAll();
		for(int i = 0; i < csvBody.size(); i++){
			if(csvBody.get(i)[0].equals(airportName)){
				rot_deg = Double.parseDouble(csvBody.get(i)[1]);
				lat_ref = Double.parseDouble(csvBody.get(i)[2]);
				long_ref = Double.parseDouble(csvBody.get(i)[3]);
			}
		}
		reader.close();
	}
	
	/**
	 * Instantiates a new update contour.
	 *
	 * @param map the main map
	 */
	public UpdateContour(JMap map){
		this.map = map;
	}
	
	/**
	 * Update contour graphic to the resulting ANGIM outputs.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void updateContourGraphic() throws IOException{
		GraphicsLayer noise = (GraphicsLayer) map.getLayers().get(3);	
		BufferedReader[] readers = new BufferedReader[3];
		readers[0] = new BufferedReader(new FileReader(getFile()[0]));
		readers[1] = new BufferedReader(new FileReader(getFile()[1]));
		readers[2] = new BufferedReader(new FileReader(getFile()[2]));
		int[] id = noise.getGraphicIDs();
		for(int i = 0; i < getFile().length; i++){
			String line = readers[i].readLine();
			List<Double> lon = new ArrayList<>();
			List<Double> lat = new ArrayList<>();
			int DNL = 0;
			int island = 0;
			for(line = readers[i].readLine(); line != null; line = readers[i].readLine()){
				String[] tokens = line.split(",");
				double xnmi = Double.parseDouble(tokens[0]);
				double ynmi = Double.parseDouble(tokens[1]);
				if(xnmi == 55 || xnmi == 60 || xnmi == 65){
					DNL = (int) xnmi;
					island = island+1;
				}else if(island < 2){
					double[] longlat = changeCoordSystem(xnmi,ynmi);
					lon.add(longlat[0]);
					lat.add(longlat[1]);
				}
			}
			Map<String,Object> att = new HashMap<String,Object>();
			att.put("Contour", DNL);
			SimpleFillSymbol symbol;
			if(DNL == 55){
				symbol = new SimpleFillSymbol(DNL55,new SimpleLineSymbol(DNL55,5), SimpleFillSymbol.Style.NULL);
			}else if (DNL == 60){
				symbol = new SimpleFillSymbol(DNL55,new SimpleLineSymbol(DNL60,5), SimpleFillSymbol.Style.NULL);
			}else {
				symbol = new SimpleFillSymbol(DNL65,new SimpleLineSymbol(DNL65,5), SimpleFillSymbol.Style.NULL);
			}
			Graphic g = new Graphic(createPoly(lon,lat),symbol,att);
			noise.updateGraphic(id[i], g);
		}
	}
	
	/**
	 * Adds the scenario graphic to the compare map for the Results Pane.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void addScenarioGraphic() throws IOException{
		GraphicsLayer noise = (GraphicsLayer) map.getLayers().get(3);	
		BufferedReader[] readers = new BufferedReader[3];
		readers[0] = new BufferedReader(new FileReader(getFile()[0]));
		readers[1] = new BufferedReader(new FileReader(getFile()[1]));
		readers[2] = new BufferedReader(new FileReader(getFile()[2]));
		int[] id = noise.getGraphicIDs();
		List<Graphic> listOfGraphics = new ArrayList<Graphic>();
		for(int i = 0; i < getFile().length; i++){
			String line = readers[i].readLine();
			List<Double> lon = new ArrayList<>();
			List<Double> lat = new ArrayList<>();
			int DNL = 0;
			int island = 0;
			for(line = readers[i].readLine(); line != null; line = readers[i].readLine()){
				String[] tokens = line.split(",");
				double xnmi = Double.parseDouble(tokens[0]);
				double ynmi = Double.parseDouble(tokens[1]);
				if(xnmi == 55 || xnmi == 60 || xnmi == 65){
					DNL = (int) xnmi;
					island = island+1;
				}else if(island < 2){
					double[] longlat = changeCoordSystem(xnmi,ynmi);
					lon.add(longlat[0]);
					lat.add(longlat[1]);
				}
			}
			Map<String,Object> att = new HashMap<String,Object>();
			att.put("Contour", DNL);
			SimpleFillSymbol symbol;
			if(DNL == 55){
				symbol = new SimpleFillSymbol(DNL55,new SimpleLineSymbol(DNL55,5,SimpleLineSymbol.Style.DASH), SimpleFillSymbol.Style.NULL);
			}else if (DNL == 60){
				symbol = new SimpleFillSymbol(DNL60,new SimpleLineSymbol(DNL60,5,SimpleLineSymbol.Style.DASH), SimpleFillSymbol.Style.NULL);
			}else {
				symbol = new SimpleFillSymbol(DNL65,new SimpleLineSymbol(DNL65,5,SimpleLineSymbol.Style.DASH), SimpleFillSymbol.Style.NULL);
			}
			Graphic g = new Graphic(createPoly(lon,lat),symbol,att);
			listOfGraphics.add(g);
			noise.updateGraphic(id[i], g);
		}
		scenarioGraphics.add(listOfGraphics);
	}
	
	/**
	 * Gets the file path that contains the noise contour information.
	 * MCI Specific need to fix
	 * @return the strings of path names for each Contour level
	 * @throws IOException 
	 */
	private String[] getFile() throws IOException{
		String[] urls = new String[3];
		String a = "";
		ForecastScenarios scenario = new ForecastScenarios();
		String airportName = scenario.getAirportName();
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
			a = airportName+"_2030L_REACT";
		}else if(op.getModel().getSelectedItem() == "2030 Above Nominal TAF"){
			a = airportName+"_2030H_REACT";
		}
		if(state == "update"){
			File file1 = new File("OUT/Contours/Contours_"+a+"_"+airportName.substring(1,airportName.length())+"_55.csv");
			urls[0] = file1.getAbsolutePath();
			File file2 = new File("OUT/Contours/Contours_"+a+"_"+airportName.substring(1,airportName.length())+"_60.csv");
			urls[1] = file2.getAbsolutePath();
			File file3 = new File("OUT/Contours/Contours_"+a+"_"+airportName.substring(1,airportName.length())+"_65.csv");
			urls[2]= file3.getAbsolutePath();
		} else if (state == "reset"){
			File file1 = new File("Files/Contours_"+a+"_"+airportName.substring(1,airportName.length())+"_55.csv");
			urls[0] = file1.getAbsolutePath();
			File file2 = new File("Files/Contours_"+a+"_"+airportName.substring(1,airportName.length())+"_60.csv");
			urls[1] = file2.getAbsolutePath();
			File file3 = new File("Files/Contours_"+a+"_"+airportName.substring(1,airportName.length())+"_65.csv");
			urls[2]= file3.getAbsolutePath();
		}
		return urls;
	}

	/**
	 * Change coord system from nmi to degrees.
	 *
	 * @param xnmi the ANGIM x coordinate in nmi
	 * @param ynmi the ANGIM y coordinate in nmi
	 * @return the double[] containing the longitude and latitude information 
	 */
	private double[] changeCoordSystem(double xnmi, double ynmi){
		double xnmirot = xnmi*Math.cos(rot_deg)-ynmi*Math.sin(rot_deg);
		double ynmirot = ynmi*Math.cos(rot_deg)+xnmi*Math.sin(rot_deg);
		double bearingX = 0;
		double bearingY = 0;
		if(xnmirot < 0){
			bearingX = 1.5*Math.PI;
		}else{
			bearingX = 0.5*Math.PI;
		}
		if(ynmirot < 0){
			bearingY = Math.PI;
		}else{
			bearingY = 0;
		}
		double[] longlat = new double[2];
		longlat[0] = (180/Math.PI)*(long_ref + Math.atan2(Math.sin(Math.abs(xnmirot)/R_earth)*Math.cos(lat_ref)*Math.sin(bearingX),Math.cos(Math.abs(xnmirot)/R_earth)-Math.sin(lat_ref)*Math.sin(lat_ref)));
		longlat[1] = (180/Math.PI)*(Math.asin(Math.sin(lat_ref)*Math.cos(Math.abs(ynmirot)/R_earth)+Math.cos(lat_ref)*Math.sin(Math.abs(ynmirot)/R_earth)*Math.cos(bearingY)));
		return longlat;
	}
	
	/**
	 * Creates the polygon from a set of points.
	 *
	 * @param x the list of longitudinal points
	 * @param y the list of latitude points
	 * @return the polygon to be created
	 */
	private Polygon createPoly(List<Double> x, List<Double> y){
		Polygon poly = new Polygon();
		poly.startPath(x.get(0), y.get(0));
		for(int i = 1; i < x.size(); i++){
			poly.lineTo(x.get(i), y.get(i));
		}
		return poly;
	}
}
