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
		String[] names = getTrackNames();
		String[] runway = getRunway();
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
			att.put("TrackName",names[i]);
			att.put("Runway",runway[i]);
			if(i < 18){
				att.put("Operation", "Departure");
				symbol = new SimpleLineSymbol(Color.ORANGE,2);
			}else{
				att.put("Operation", "Approach");
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
	 *
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
	 *
	 * @return the track names
	 */
	public String[] getTrackNames(){
		String[] names = new String[36];
		for(int i = 0; i < 17; i++){
			names[i] = "J"+(i+1);
		}
		names[17] = "J4A";
		for(int j = 18; j < 24; j++){
			names[j] = "A"+(j+1-18);
		}
		for(int k = 24; k < 30; k++){
			names[k] = "A"+(k+1-24)+"A";
		}
		for(int l = 30; l < 36; l++){
			names[l] = "A"+(l+1-30)+"B";
		}
		return names;
	}

	/**
	 * Gets the runway names for each track.
	 *
	 * @return the runway names for each track to be added as an attribute
	 */
	private String[] getRunway(){
		String[] tracks = getTrackNames();
		String[] names = new String[36];
		for(int i = 0; i < 36; i++){
			if(tracks[i].equals("A2") || tracks[i].equals("A2A") || tracks[i].equals("A2B") || tracks[i].equals("J6") || tracks[i].equals("J7") || tracks[i].equals("J8") || tracks[i].equals("J9")){
				names[i] = "01L";
			}
			else if (tracks[i].equals("A6") || tracks[i].equals("A6A")|| tracks[i].equals("A6B") || tracks[i].equals("J15") || tracks[i].equals("J16") || tracks[i].equals("J17")){
				names[i] = "01R";
			}
			else if (tracks[i].equals("A3") || tracks[i].equals("A3A")|| tracks[i].equals("A3B") || tracks[i].equals("J10") || tracks[i].equals("J11") || tracks[i].equals("J12") || tracks[i].equals("J13")){
				names[i] = "09";
			}
			else if (tracks[i].equals("A5") || tracks[i].equals("A5A") || tracks[i].equals("A5B") || tracks[i].equals("J4") || tracks[i].equals("J5") || tracks[i].equals("J4A")){
				names[i] = "19L";
			}
			else if (tracks[i].equals("A1") || tracks[i].equals("A1A") || tracks[i].equals("A1B") || tracks[i].equals("J1") || tracks[i].equals("J2")|| tracks[i].equals("J3")){
				names[i] = "19R";
			}
			else if (tracks[i].equals("A4") || tracks[i].equals("A4A") || tracks[i].equals("A4B") || tracks[i].equals("J14")){
				names[i] = "27";
			}
		}
		return names;
	}
}
