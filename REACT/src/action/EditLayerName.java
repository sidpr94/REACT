package action;

import javax.swing.SwingUtilities;

import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;
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
        	  layer.setName("Population Map");
          }else if(layer.getName() == jMap.getLayers().get(1).getName()){
        	  layer.setName("Flight Tracks");
          }else if(layer.getName() == jMap.getLayers().get(2).getName()){
        	  layer.setName("Noise Contour");
          }else{
        	  layer.setName("Runways");
          }
          // Update layer list
          jLayerlist.refresh();
        }
      });
    }
}
