/*
 * 
 */
package basemap;

import java.io.IOException;

import javax.swing.JTextArea;

import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.MapEvent;
import com.esri.map.MapEventListenerAdapter;
import com.esri.toolkit.JLayerList;

import action.EditLayerVisuals;
import action.MouseMoveOverlay;

// TODO: Auto-generated Javadoc
/**

 * 
 * This class inserts the layers of the main map: population editable, population non-editable, tracks, noise contours, runways.
 * The class also employs the EditLayerVisuals for adding map tips and changing layer names for the layerlist.
 * The class also adds the map overlay for coordinates on mouse location.
 * 
 * @author Sidharth Prem
 * @see content.MapPane 
 * @see action.EditLayerVisuals 
 * @see action.MouseMoveOverlay
 * 
 */
public class CreateMainMap {
	
	/** The main map containing the airport information. */
	JMap mainMap;
	
	/** The main layer list containing the main map layer visibility information. */
	JLayerList mainLayerList;
	
	/** The coordinate text for the MouseMoveOverlay. */
	JTextArea coordTxt;
	
	/**
	 * Instantiates a new creates the map.
	 */
	public CreateMainMap(){}
	
	/**
	 * Instantiates a new creates the map.
	 *
	 * @param map the main map
	 * @param list the main layerlist
	 * @param txt the coordinate location txt
	 */
	public CreateMainMap(JMap map, JLayerList list, JTextArea txt){
		this.mainMap = map;
		this.mainLayerList = list;
		this.coordTxt = txt;
	}
	
	/**
	 * Creates the different layers into one JMap.
	 * The layers are created in separate classes.
	 * @see basemap.PopMap
	 * @see basemap.PopMapNoEdit
	 * @see basemap.Tracks
	 * @see basemap.NoiseContour
	 * @see basemap.Runway
	 *
	 * @return the main map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JMap getMap() throws IOException{
		PopMap pmap = new PopMap();
		Runway rmap = new Runway();
		Tracks tmap = new Tracks();
		PopMapNoEdit popno = new PopMapNoEdit();
		NoiseContour nmap = new NoiseContour();
		GraphicsLayer pop = new GraphicsLayer();
		Layer tracks = tmap.createTracks();
		Layer noise = nmap.createNoiseContour();
		Layer runway = rmap.createRunway();
		Layer popnoedit = popno.createPop();
		mainMap.getLayers().add(0,popnoedit);
		mainMap.getLayers().add(1,pop);
		mainMap.getLayers().add(2,tracks);
		mainMap.getLayers().add(3,noise);
		mainMap.getLayers().add(4,runway);
		
		//This is necessary in how the GeoJSonParser works, it needs to know the map is ready before it can place the population map in it
		mainMap.addMapEventListener(new MapEventListenerAdapter(){
			@Override
			public void mapReady(final MapEvent arg0) {
				pmap.createPopMap(pop);
			}
		});
		//Add all visual information and overlays
		mainMap.addMapOverlay(new MouseMoveOverlay(mainMap,coordTxt));
		popnoedit.addLayerInitializeCompleteListener(new EditLayerVisuals(mainMap, mainLayerList));
		tracks.addLayerInitializeCompleteListener(new EditLayerVisuals(mainMap, mainLayerList));
		noise.addLayerInitializeCompleteListener(new EditLayerVisuals(mainMap, mainLayerList));
		runway.addLayerInitializeCompleteListener(new EditLayerVisuals(mainMap, mainLayerList));;
		return mainMap;
	}
}

