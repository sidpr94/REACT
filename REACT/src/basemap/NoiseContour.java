/*
 * 
 */
package basemap;
//This class creates the NoiseContour map from the shapefile within Files
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esri.core.geometry.Polygon;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.GraphicsLayer;
import com.opencsv.CSVReader;

import angim.ForecastScenarios;

// TODO: Auto-generated Javadoc
/**
 * Creates a GraphicsLayer composed of 3 graphics: Noise Contours at 55,60,65 DNL.
 * The graphic is created as polygons with no fill.
 * Reference values are based on runway 01L as this is the reference for ANGIM coordinate system.
 * Conversion between nmi to degrees is necessary as ANGIM coordinates are in nmi.
 * @author Sidharth Prem
 * @see basemap.createMainMap
 */
public class NoiseContour {

	/** The longitudinal reference for runway 01L. */
	static double long_ref = 0;
	
	/** The latitude reference for ruwnay 01L. */
	static double lat_ref = 0;
	
	/** The radius of the earth. */
	static double R_earth = 3440.069517;
	
	/** The rotational angle for runway 01L. */
	static double rot_deg = 0;
	
	/** The dnl55 color value. */
	Color DNL55 = new Color(56,168,0);
	
	/** The dnl60 color value. */
	Color DNL60 = new Color(0,92,230);
	
	/** The dnl65 color value. */
	Color DNL65 = new Color(255,0,0);
	
	/**
	 * Instantiates a new noise contour.
	 * @throws IOException 
	 */
	public NoiseContour() throws IOException{
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
	};

	/**
	 * Creates the noise contour for each DNL level
	 *
	 * @return the noise contour graphics layer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public GraphicsLayer createNoiseContour() throws IOException{
		BufferedReader[] readers = new BufferedReader[3];
		readers[0] = new BufferedReader(new FileReader(getFile()[0]));
		readers[1] = new BufferedReader(new FileReader(getFile()[1]));
		readers[2] = new BufferedReader(new FileReader(getFile()[2]));
		GraphicsLayer noiseLayer = new GraphicsLayer();
		for(int i = 0; i < getFile().length; i++){
			String line = readers[i].readLine();
			List<Double> lon = new ArrayList<>();
			List<Double> lat = new ArrayList<>();
			int DNL = 0;
			for(line = readers[i].readLine(); line != null; line = readers[i].readLine()){
				String[] tokens = line.split(",");
				double xnmi = Double.parseDouble(tokens[0]);
				double ynmi = Double.parseDouble(tokens[1]);
				if(xnmi == 55 || xnmi == 60 || xnmi == 65){
					DNL = (int) xnmi;
				}else{
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
				symbol = new SimpleFillSymbol(DNL60,new SimpleLineSymbol(DNL60,5), SimpleFillSymbol.Style.NULL);
			}else {
				symbol = new SimpleFillSymbol(DNL65,new SimpleLineSymbol(DNL65,5), SimpleFillSymbol.Style.NULL);
			}
			Graphic g = new Graphic(createPoly(lon,lat),symbol,att);
			noiseLayer.addGraphic(g);
		}
		return noiseLayer;
	}

	/**
	 * Gets the files that contain the point values for the noise contours
	 * MCI Specific Need to Fix
	 * @return the file path names
	 * @throws IOException 
	 */
	private String[] getFile() throws IOException{
		ForecastScenarios scenario = new ForecastScenarios();
		String airportName = scenario.getAirportName();
		String[] urls = new String[3];
		File file1 = new File("Files/Contours_"+airportName+"_2015_REACT_"+airportName.substring(1,airportName.length())+"_55.csv");
		urls[0] = file1.getAbsolutePath();
		File file2 = new File("Files/Contours_"+airportName+"_2015_REACT_"+airportName.substring(1,airportName.length())+"_60.csv");
		urls[1] = file2.getAbsolutePath();
		File file3 = new File("Files/Contours_"+airportName+"_2015_REACT_"+airportName.substring(1,airportName.length())+"_65.csv");
		urls[2]= file3.getAbsolutePath();
		return urls;
	}

	/**
	 * Change coord system from xnmi ANGIM coordinates to longtiude and latitude WSG84
	 *
	 * @param xnmi the x ANGIM coordinate in nmi
	 * @param ynmi the y ANGIM coordinate in nmi
	 * @return the double[] containing longitude and latitude point
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
	 * Creates the polygon.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the polygon
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
