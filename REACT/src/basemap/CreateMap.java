package basemap;

import javax.swing.JTextArea;

import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.toolkit.JLayerList;

import GUI.EditLayerName;
import GUI.MouseMoveOverlay;

public class CreateMap {
	JMap jMap;
	JLayerList jLayerList;
	JTextArea coordTxt;
	public CreateMap(){}
	public CreateMap(JMap map, JLayerList list, JTextArea txt){
		this.jMap = map;
		this.jLayerList = list;
		this.coordTxt = txt;
	}
	public JMap getMap(){
	 	jMap.addMapOverlay(new MouseMoveOverlay(jMap,coordTxt));
		PopMap pmap = new PopMap();
		Runway rmap = new Runway();
		Tracks tmap = new Tracks();
		NoiseContour nmap = new NoiseContour();
		Layer pop = pmap.createPopMap();
		Layer tracks = tmap.createTracks();
		Layer noise = nmap.createNoiseContour();
		Layer runway = rmap.createRunway();
		jMap.getLayers().add(pop);
		jMap.getLayers().add(tracks);
		jMap.getLayers().add(noise);
		jMap.getLayers().add(runway);
		pop.addLayerInitializeCompleteListener(new EditLayerName(jMap, jLayerList));
		tracks.addLayerInitializeCompleteListener(new EditLayerName(jMap, jLayerList));
		noise.addLayerInitializeCompleteListener(new EditLayerName(jMap, jLayerList));
		runway.addLayerInitializeCompleteListener(new EditLayerName(jMap, jLayerList));
		return jMap;
	}
}

