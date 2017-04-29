package scenarioDev.track;

import java.awt.Color;
import java.io.BufferedReader;
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

import basemap.NoiseContour;

public class InfoOverlayTrack implements HitTestListener{
	HitTestOverlay overlay;
	GraphicsLayer layer;
	JMap jMap;
	public static String name = null;
	public static List<Graphic> vars = new ArrayList<Graphic>();
	public static List<Integer> varsID = new ArrayList<Integer>();
	public static HashMap<String,String> changed = new HashMap<String,String>();
	public InfoOverlayTrack(GraphicsLayer featureLayer, JMap map) {
		this.layer = featureLayer;	
		this.jMap = map;
	}
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
		/*if(origG != null){
			hitName = origG.getAttributeValue("TrackName").toString();
		}
		if(vars.isEmpty()){
			try {
				createVariant(hitGraphic);
				origG = hitGraphic;
				System.out.println(origG.getAttributeValue("TrackName").toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}else{
			if(hitGraphic.getAttributeValue("TrackName").toString().subSequence(0, hitName.length()).equals(hitName)){
				if(hitGraphic.getAttributeValue("TrackName").toString().equals(hitName)){
					for(int j = 0; j < vars.size(); j++){
						layer.removeGraphic(varsID.get(j));
					}
				}else{
					int keep = hitGraphic.getUid();
					layer.updateGraphic(keep, new SimpleLineSymbol(Color.ORANGE,2));
					for(int j = 0; j < vars.size(); j++){
						if(varsID.get(j) != keep){
							layer.removeGraphic(varsID.get(j));	
						}
					}
					layer.removeGraphic(origG.getUid());
				}
				vars.clear();
				varsID.clear();
				origG = null;
			}else{
				vars.clear();
				for(int i = 0; i < varsID.size(); i++){
					layer.removeGraphic(varsID.get(i));
				}
				try {
					createVariant(hitGraphic);
					origG = hitGraphic;
					System.out.println(origG.getAttributeValue("TrackName").toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
	}	
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
			urls[i] = NoiseContour.class.getClassLoader().getResource("Files/Tracks/"+name+"/"+name+"_v"+(i+1)+".csv").getPath();
		}
		return urls;
	}

	private Polyline createLine(List<Double> x, List<Double> y){
		Polyline line = new Polyline();
		line.startPath(x.get(0), y.get(0));
		for(int i = 1; i < x.size(); i++){
			line.lineTo(x.get(i), y.get(i));
		}
		return line;
	}

}
