package basemap;

import java.io.IOException;

import javax.swing.JTextArea;

import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.MapEvent;
import com.esri.map.MapEventListenerAdapter;
import com.esri.toolkit.JLayerList;

import action.EditLayerName;
import action.MouseMoveOverlay;

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
	public JMap getMap() throws IOException{
		PopMap pmap = new PopMap();
		Runway rmap = new Runway();
		Tracks tmap = new Tracks();
		PopMapNoEdit popm = new PopMapNoEdit();
		NoiseContour nmap = new NoiseContour();
		GraphicsLayer pop = new GraphicsLayer();
		Layer tracks = tmap.createTracks();
		Layer noise = nmap.createNoiseContour();
		Layer runway = rmap.createRunway();
		Layer popnoedit = popm.createPop();
		jMap.getLayers().add(0,popnoedit);
		jMap.getLayers().add(1,pop);
		jMap.getLayers().add(2,tracks);
		jMap.getLayers().add(3,noise);
		jMap.getLayers().add(4,runway);
		jMap.addMapEventListener(new MapEventListenerAdapter(){
			public void mapReady(final MapEvent arg0) {
				pmap.createPopMap(pop);
			}
		});
		jMap.addMapOverlay(new MouseMoveOverlay(jMap,coordTxt));
		popnoedit.addLayerInitializeCompleteListener(new EditLayerName(jMap, jLayerList));
		tracks.addLayerInitializeCompleteListener(new EditLayerName(jMap, jLayerList));
		noise.addLayerInitializeCompleteListener(new EditLayerName(jMap, jLayerList));
		runway.addLayerInitializeCompleteListener(new EditLayerName(jMap, jLayerList));;
		return jMap;
	}
}

