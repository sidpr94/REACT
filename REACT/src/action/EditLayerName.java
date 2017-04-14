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

public class EditLayerName implements LayerInitializeCompleteListener {
	JMap jMap;
	JLayerList jLayerlist;
	public EditLayerName(JMap map, JLayerList jLayerList){
		this.jMap = map;
		this.jLayerlist = jLayerList;
	}
	@Override
	public void layerInitializeComplete(final LayerInitializeCompleteEvent event) {
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				Layer layer = event.getLayer();
				if(layer.getName() == jMap.getLayers().get(0).getName()){
					layer.setName("Population Beyond");
				}else if(layer.getName() == jMap.getLayers().get(2).getName()){
					layer.setName("Flight Tracks");	
				}else if(layer.getName() == jMap.getLayers().get(3).getName()){
					layer.setName("Noise Contour");
				}else if(layer.getName() == jMap.getLayers().get(4).getName()){
					layer.setName("Runways");
					if (event.getLayer().getStatus() == LayerStatus.INITIALIZED) {
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
				jLayerlist.refresh();
			}
		});
	}
}
