package action;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.Layer.LayerStatus;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;
import com.esri.map.MapTip;
import com.esri.toolkit.JLayerList;

// TODO: Auto-generated Javadoc
/**
 *
 * 
 * The Class EditLayerName.
 * 
 * This class edits the layer name for each layer within the JMap.
 * This is important as names are set as "" unless specified.
 * Layer names are set only when layers are initialized in the map.
 * 
 * This class also adds Map Tips to Track and Runway to show pertinent information. 
 * 
 * @author Sidharth Prem
 * @see basemap.CreateMap
 */

public class EditLayerVisuals implements LayerInitializeCompleteListener {

	/** This is the map used in the main Map Pane (airport information) */
	JMap mainMap;

	/** The main map layer list. */
	JLayerList mainLayerlist;

	/**
	 * Instantiates a new EditLayerName.
	 *
	 * @param map the main map
	 * @param jLayerList the main layer list
	 */
	public EditLayerVisuals(JMap map, JLayerList jLayerList){
		this.mainMap = map;
		this.mainLayerlist = jLayerList;
	}

	/* (non-Javadoc)
	 * @see com.esri.map.LayerInitializeCompleteListener#layerInitializeComplete(com.esri.map.LayerInitializeCompleteEvent)
	 */
	@Override
	//Ensures the map has initialized the layers
	public void layerInitializeComplete(final LayerInitializeCompleteEvent event) {
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				Layer layer = event.getLayer();
				if(layer.getName() == mainMap.getLayers().get(0).getName()){ //checks layer names 
					layer.setName("Population Beyond");
					
				}else if(layer.getName() == mainMap.getLayers().get(2).getName()){
					// Adds map tips to show track information
					LinkedHashMap<String, String> displayFields = new LinkedHashMap<String, String>();
					displayFields.put("Operation","Operation Type: ");
					displayFields.put("TrackName", "Track Name: ");
					displayFields.put("Runway", "Runway: ");
					MapTip maptip = new MapTip(displayFields);
					maptip.setBackground(Color.WHITE);
					maptip.setForeground(Color.BLACK);
					maptip.setFont(new Font("Dialog",Font.BOLD,14));
					maptip.setBorder(BorderFactory.createLineBorder(Color.BLACK));

					((GraphicsLayer) layer).setMapTip(maptip);
					layer.setName("Flight Tracks");	
					
				}else if(layer.getName() == mainMap.getLayers().get(3).getName()){
					layer.setName("Noise Contour");
					
				}else if(layer.getName() == mainMap.getLayers().get(4).getName()){
					layer.setName("Runways");
					if (event.getLayer().getStatus() == LayerStatus.INITIALIZED) {
						// Adds map tips to show track information
						LinkedHashMap<String, String> displayFields = new LinkedHashMap<String, String>();
						displayFields.put("Runway Name","Runway End Names: ");
						displayFields.put("Start", "Runway Start: ");
						displayFields.put("End", "Runway End: ");

						MapTip maptip = new MapTip(displayFields);
						maptip.setBackground(Color.WHITE);
						maptip.setForeground(Color.BLACK);
						maptip.setFont(new Font("Dialog",Font.BOLD,14));
						maptip.setBorder(BorderFactory.createLineBorder(Color.BLACK));

						((GraphicsLayer) layer).setMapTip(maptip);
					}
					
				}else{
					layer.setName("");
				}
				// Update layer list
				mainLayerlist.refresh();
			}
		});
	}
}
