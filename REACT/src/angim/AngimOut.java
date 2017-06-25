
package angim;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.map.Graphic;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.opencsv.CSVReader;

import basemap.PopMap;

// TODO: Auto-generated Javadoc
/**
 * Gathers all information output from ANGIM to be placed in the Results Table Pane.
 * ANGIM outputs are found in OUT folder.
 * This class is only used anytime ANGIM completes a run successfully.
 * @author Sidharth Prem
 * @see angim.CalculateNoise
 */
public class AngimOut {
	
	/** The row to be added. */
	int row;
	
	/** The main map. */
	JMap mainMap;
	
	/**
	 * Instantiates a new angim out.
	 *
	 * @param row the row to be added to the Results Table
	 * @param map the main map to calculate interesection information
	 */
	public AngimOut(int row, JMap map){
		this.row = row;
		this.mainMap = map;
	}
	
	/**
	 * Gets the angim info.
	 * Gathers, number of people exposed, contour areas, and fuel and emissions for the table once a scenario is run.
	 * @return the angim information for Results Table
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Object[] getAngimInfo() throws IOException{
		Object[] data = new Object[10];
		data[0] = row;
		File inputFile = new File(getFile()[1]);
		List<Double> d = populationExposed();
		CSVReader reader = new CSVReader(new FileReader(inputFile),',');
		List<String[]> csvBody = reader.readAll();
		data[1] = Math.ceil(d.get(0));
		data[2] = Math.ceil(d.get(1));
		data[3] = Math.ceil(d.get(2));
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
	
	/**
	 * Gets the path for the files that contain the Contour Area and Population exposure metrics.
	 *
	 * @return the file path names to be used for a BufferedReader
	 */
	public String[] getFile(){
		String[] urls = new String[2];
		File file = new File("OUT/Areas.csv");
		urls[0] = file.getAbsolutePath();
		File file1 = new File("OUT/PopulationOUT.csv");
		urls[1] = file1.getAbsolutePath();
		return urls;
	}
	
	/**
	 * Checks to see if the two geometries intersect.
	 *
	 * @param g the first graphic
	 * @param h the intersecting graphic
	 * @return true, if intersection exists
	 */
	public boolean doesIntersect(Graphic g, Graphic h){
		return GeometryEngine.intersects(g.getGeometry(), h.getGeometry(), mainMap.getSpatialReference());	
	}
	
	/**
	 * Calculates a ratio based population exposure metric.
	 * This is done by intersecting each population block to each noise contour.
	 * It creates the new geometry of intersection and calculates the area,
	 * from there the population exposed is calculated as an area ratio multiplied by the blocks population count.
	 *
	 * @return the list containing the population exposed at 55, 60, and 65 DNL
	 */
	public List<Double> populationExposed(){
		List<Double> pops = new ArrayList<Double>();
		GraphicsLayer noise = (GraphicsLayer) mainMap.getLayers().get(3);
		int[] nids = noise.getGraphicIDs();
		for(int i = 0; i < noise.getNumberOfGraphics(); i++){
			List<Double> temp = new ArrayList<Double>();
			for(Map.Entry<Integer, Graphic> entry:PopMap.graphics.entrySet()){
				if(doesIntersect(noise.getGraphic(nids[i]),entry.getValue())){
					Geometry geom = GeometryEngine.intersect(noise.getGraphic(nids[i]).getGeometry(),entry.getValue().getGeometry(),mainMap.getSpatialReference());
					double areaint = geom.calculateArea2D();
					double area = entry.getValue().getGeometry().calculateArea2D();
					int pop = (int) entry.getValue().getAttributeValue("POP10");
					double popexpo = pop*(areaint/area);
					temp.add(popexpo);
				}
			}
			double d = 0;
			for(int j = 0; j < temp.size(); j++){
				d = d + temp.get(j);
			}
			pops.add(d);
		}
		return pops;
	}
}
