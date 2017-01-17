package basemap;

import javax.swing.SwingUtilities;

import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.LayerInitializeCompleteEvent;
import com.esri.map.LayerInitializeCompleteListener;
import com.esri.toolkit.JLayerList;

public class CreateMap {
	PopMap pmap = new PopMap();
	Runway rmap = new Runway();
	Tracks tmap = new Tracks();
	NoiseContour nmap = new NoiseContour();
	Layer pop = pmap.createPopMap();
	Layer tracks = tmap.createTracks();
	Layer noise = nmap.createNoiseContour();
	Layer runway = rmap.createRunway();
	JMap map;
	JLayerList list;
	CreateMap(JMap jMap,JLayerList jLayerList){
		this.list = jLayerList;
		this.map = jMap;
	}
	public JMap createMap(){
		map.getLayers().add(pop);
		map.getLayers().add(tracks);
		map.getLayers().add(noise);
		map.getLayers().add(runway);
		return map;
	}
	public JLayerList createList(){
		pop.addLayerInitializeCompleteListener(new EditLayerName());
		tracks.addLayerInitializeCompleteListener(new EditLayerName());
		noise.addLayerInitializeCompleteListener(new EditLayerName());
		runway.addLayerInitializeCompleteListener(new EditLayerName());
		return list;
	}
	class EditLayerName implements LayerInitializeCompleteListener {
	    @Override
	    public void layerInitializeComplete(final LayerInitializeCompleteEvent event) {
	      SwingUtilities.invokeLater(new Runnable()
	      {
	        @Override
	        public void run()
	        {
	          Layer layer = event.getLayer();
	          if(layer.getName() == "TracksforAAD_KMCI260_1"){
	        	  layer.setName("Flight Tracks");
	          }else if(layer.getName() == "AAD_KMCI260_Project1"){
	        	  layer.setName("Noise Contours");
	          }else if(layer.getName() == "MCI_BUFFER_PROJECTION_1"){
	        	  layer.setName("Population Map");
	          }else{
	        	  layer.setName("Runways");
	          }
	          // Update layer list
	          list.refresh();
	        }
	      });
	    }
	  }
}