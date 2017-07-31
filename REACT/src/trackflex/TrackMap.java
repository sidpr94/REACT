package trackflex;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JComboBox;

import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.MapEvent;
import com.esri.map.MapEventListenerAdapter;
import com.esri.toolkit.JLayerList;

import action.EditLayerVisuals;
import basemap.NoiseContour;
import basemap.PopMap;
import basemap.PopMapNoEdit;
import basemap.Runway;
import basemap.Tracks;

// TODO: Auto-generated Javadoc
/**
 * The Class TrackMap creates the contour map with the alternate tracks.
 * It will update a new track once the user creates it
 * @author Dylan Monteiro
 */

public class TrackMap {
	
		/** The contour comparison map. */
		JMap trackMap;
		
		/** The layer list. */
		JLayerList trackLayerList;
		
		/** The operational forecasting scenario information. */
		JComboBox<String> op;
		
		/** The state of the contour used in UpdateContour class. */
		String s;
		
		/**
		 * Instantiates a new contour map.
		 *
		 * @param map the map
		 * @param list the list
		 */
		public TrackMap(JMap map,JLayerList list){
			this.trackMap = map;
			this.trackLayerList = list;
		}
		
		
		/**
		 * Creates the comparison map.
		 *
		 * @return the comparison map
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public JMap createMap() throws IOException{
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
			trackMap.getLayers().add(0,popnoedit);
			trackMap.getLayers().add(1,pop);
			trackMap.getLayers().add(2,tracks);
			trackMap.getLayers().add(3,noise);
			trackMap.getLayers().add(4,runway);
			
			//This is necessary in how the GeoJSonParser works, it needs to know the map is ready before it can place the population map in it
			trackMap.addMapEventListener(new MapEventListenerAdapter(){
				@Override
				public void mapReady(final MapEvent arg0) {
					try {
						pmap.createPopMap(pop);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			//Add all visual information and overlays
			popnoedit.addLayerInitializeCompleteListener(new EditLayerVisuals(trackMap, trackLayerList));
			tracks.addLayerInitializeCompleteListener(new EditLayerVisuals(trackMap, trackLayerList));
			noise.addLayerInitializeCompleteListener(new EditLayerVisuals(trackMap, trackLayerList));
			runway.addLayerInitializeCompleteListener(new EditLayerVisuals(trackMap, trackLayerList));;
			return trackMap;
		}
		

}
