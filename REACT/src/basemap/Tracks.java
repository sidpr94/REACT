/*
 * 
 */
package basemap;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.GraphicsLayer;
import com.opencsv.CSVReader;

// TODO: Auto-generated Javadoc
/**
 * The Class Tracks creates the tracks based on csv files containing points that make up the polyline.
 * @author Sidharth Prem
 * @see basemap.CreateMainMap
 */
public class Tracks {
	
	/**
	 * Instantiates a new tracks.
	 */
	public Tracks(){};
	
	/**
	 * Creates the tracks.
	 *
	 * @return the tracks graphics layer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public GraphicsLayer createTracks() throws IOException{
		GraphicsLayer trackLayer = new GraphicsLayer();
		SimpleLineSymbol symbol = null;
		List<String> names = getTrackNames();
		List<String> runway = getRunway();
		List<String> opType = getOpType();
		BufferedReader reader = null;
		for(int i = 0; i < 36; i++){
			reader = new BufferedReader(new FileReader(getFile()[i]));
			String line = reader.readLine();
			List<Double> lon = new ArrayList<>();
			List<Double> lat = new ArrayList<>();
			for(line = reader.readLine(); line != null; line = reader.readLine()){
				String[] tokens = line.split(",");
				double xnmi = Double.parseDouble(tokens[0]);
				double ynmi = Double.parseDouble(tokens[1]);
				lon.add(xnmi);
				lat.add(ynmi);
			}
			Map<String,Object> att = new HashMap<String,Object>();
			att.put("TrackName",names.get(i));
			att.put("Runway",runway.get(i));
			att.put("Operation", opType.get(i));
			if(opType.get(i).equals("Approach")){
				symbol = new SimpleLineSymbol(Color.ORANGE,2);
			}else{
				symbol = new SimpleLineSymbol(Color.BLUE,2);
			}
			Graphic g = new Graphic(createLine(lon,lat),symbol,att);
			trackLayer.addGraphic(g);
		}
		reader.close();
		return trackLayer;
	}

	/**
	 * Gets the file path names.
	 * MCI Specific need to fix
	 * @return the file path strings for each track
	 */
	private String[] getFile(){
		String[] urls = new String[36];
		for(int i = 0; i < 17; i++){
			File newFile = new File("Files/Tracks/J"+(i+1)+"/J"+(i+1)+".csv");
			urls[i] = newFile.getAbsolutePath();
		}
		File newFile = new File("Files/Tracks/J4A/J4A.csv");
		urls[17] = newFile.getAbsolutePath();
		for(int j = 18; j < 24; j++){
			newFile = new File("Files/Tracks/Arrivals/A"+(j+1-18)+".csv");
			urls[j] = newFile.getAbsolutePath();
		}
		for(int k = 24; k < 30; k++){
			newFile = new File("Files/Tracks/Arrivals/A"+(k+1-24)+"A.csv");
			urls[k] = newFile.getAbsolutePath();
		}
		for(int k = 30; k < 36; k++){
			newFile = new File("Files/Tracks/Arrivals/A"+(k+1-30)+"B.csv");
			urls[k] = newFile.getAbsolutePath();
		}
		return urls;
	}

	/**
	 * Creates the polyline for the track based on coordinate points.
	 * 
	 * @param x the longitudinal coordinate
	 * @param y the latitude coordinate
	 * @return the track line
	 */
	private Polyline createLine(List<Double> x, List<Double> y){
		Polyline line = new Polyline();
		line.startPath(x.get(0), y.get(0));
		for(int i = 1; i < x.size(); i++){
			line.lineTo(x.get(i), y.get(i));
		}
		return line;
	}

	/**
	 * Gets the track names for each track to be added as an attribute for hte graphic.
	 * MCI Specific need to fix
	 * @return the track names
	 * @throws IOException 
	 */
	public List<String> getTrackNames() throws IOException{
		File file = new File("Files/TrackInformation.csv");
		CSVReader reader = new CSVReader(new FileReader(file),',');
		List<String[]> csvBody = reader.readAll();
		reader.close();
		List<String> names = new ArrayList<String>();
		for(int i = 1; i < csvBody.get(0).length; i++){
			names.add(csvBody.get(0)[i]);
		}
		return names;
	}

	/**
	 * Gets the runway names for each track.
	 * MCI Specific need to fix
	 * @return the runway names for each track to be added as an attribute
	 * @throws IOException 
	 */
	private List<String> getRunway() throws IOException{
		File file = new File("Files/TrackInformation.csv");
		List<String> names = new ArrayList<String>();
		CSVReader reader = new CSVReader(new FileReader(file),',');
		List<String[]> csvBody = reader.readAll();
		reader.close();
		for(int i = 1; i < csvBody.get(1).length; i++){
			names.add(csvBody.get(1)[i]);
		}
		return names;
	}
	
	private List<String> getOpType() throws IOException{
		File file = new File("Files/TrackInformation.csv");
		List<String> names = new ArrayList<String>();
		CSVReader reader = new CSVReader(new FileReader(file),',');
		List<String[]> csvBody = reader.readAll();
		reader.close();
		for(int i = 1; i < csvBody.get(2).length; i++){
			names.add(csvBody.get(2)[i]);
		}
		return names;
	}
}
