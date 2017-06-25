/*
 * 
 */
package scenarioDev.track;

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
import com.esri.map.JMap;

import com.esri.toolkit.overlays.HitTestEvent;
import com.esri.toolkit.overlays.HitTestListener;
import com.esri.toolkit.overlays.HitTestOverlay;

// TODO: Auto-generated Javadoc
/**
 * The Class InfoOverlayTrack.
 */
public class InfoOverlayTrack implements HitTestListener{
	
	/** The overlay. */
	HitTestOverlay overlay;
	
	/** The layer. */
	GraphicsLayer layer;
	
	/** The j map. */
	JMap jMap;
	
	/** The name. */
	public static String name = null;
	
	/** The vars. */
	public static List<Graphic> vars = new ArrayList<Graphic>();
	
	/** The vars ID. */
	public static List<Integer> varsID = new ArrayList<Integer>();
	
	/** The changed. */
	public static HashMap<String,String> changed = new HashMap<String,String>();
	
	/**
	 * Instantiates a new info overlay track.
	 *
	 * @param featureLayer the feature layer
	 * @param map the map
	 */
	public InfoOverlayTrack(GraphicsLayer featureLayer, JMap map) {
		this.layer = featureLayer;	
		this.jMap = map;
	}
	
	/* (non-Javadoc)
	 * @see com.esri.toolkit.overlays.HitTestListener#featureHit(com.esri.toolkit.overlays.HitTestEvent)
	 */
	@Override
	public void featureHit(HitTestEvent event) {
		HitTestOverlay overlay = event.getOverlay();
		// get first (top-most) graphic hit by the mouse
		Graphic hitGraphic = (Graphic) overlay.getHitFeatures().get(0);
		if(vars.isEmpty()){
			try {
				createVariant(hitGraphic);
				name = hitGraphic.getAttributeValue("TrackName").toString();
				vars.add(hitGraphic);
				varsID.add(hitGraphic.getUid());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			if(hitGraphic.getAttributeValue("TrackName").toString().contains(name)){
				changed.put(name, hitGraphic.getAttributeValue("TrackName").toString());
				int keep = hitGraphic.getUid();
				layer.updateGraphic(keep, new SimpleLineSymbol(Color.ORANGE,2));
				for(int i = 0; i < vars.size(); i++){
					if(varsID.get(i) != keep){
						layer.removeGraphic(varsID.get(i));
					}
				}
				vars.clear();
				varsID.clear();
				name = null;
			}else{
				vars.clear();
				for(int i = 0; i < varsID.size(); i++){
					layer.removeGraphic(varsID.get(i));
				}
				try {
					createVariant(hitGraphic);
					name = hitGraphic.getAttributeValue("TrackName").toString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}	
	
	/**
	 * Creates the variant.
	 *
	 * @param hit the hit
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createVariant(Graphic hit) throws IOException{
		BufferedReader reader = null;
		SimpleLineSymbol symbol = new SimpleLineSymbol(Color.RED,4);
		String[] urls = getFile(hit);
		for(int i = 0; i < urls.length; i++){
			reader = new BufferedReader(new FileReader(urls[i]));
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
			att.put("TrackName",(hit.getAttributeValue("TrackName").toString())+"_v"+(i+1));
			att.put("Runway",hit.getAttributeValue("Runway").toString());
			att.put("Operation",hit.getAttributeValue("Operation").toString());
			Graphic g = new Graphic(createLine(lon,lat),symbol,att);
			vars.add(g);
			varsID.add(layer.addGraphic(g));
		}
	}
	
	/**
	 * Gets the file.
	 * MCI specific need to fix
	 * @param hit the hit
	 * @return the file
	 */
	private String[] getFile(Graphic hit){
		String name = hit.getAttributeValue("TrackName").toString();
		String[] urls = null;
		if(name.equals("J2") || name.equals("J5") || name.equals("J12")){
			urls = new String[4];
		}else if(name.equals("J14")){
			urls = new String[6];
		}else{
			urls = new String[5];
		}
		for(int i = 0; i < urls.length; i++){
			File newFile = new File("Files/Tracks/"+name+"/"+name+"_v"+(i+1)+".csv");
			urls[i] = newFile.getAbsolutePath();
		}
		return urls;
	}

	/**
	 * Creates the line.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the polyline
	 */
	private Polyline createLine(List<Double> x, List<Double> y){
		Polyline line = new Polyline();
		line.startPath(x.get(0), y.get(0));
		for(int i = 1; i < x.size(); i++){
			line.lineTo(x.get(i), y.get(i));
		}
		return line;
	}
	

}
