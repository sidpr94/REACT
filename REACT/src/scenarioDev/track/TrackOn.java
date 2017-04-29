package scenarioDev.track;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.toolkit.overlays.HitTestOverlay;


public class TrackOn implements ActionListener {
	JMap map;
	GraphicsLayer track;
	HitTestOverlay hitTestOverlay;
	InfoOverlayTrack info;
	public TrackOn(JMap jMap){
		this.map = jMap;
		this.track = (GraphicsLayer) jMap.getLayers().get(2);
		this.hitTestOverlay = new HitTestOverlay(track);
		this.info = new InfoOverlayTrack(track,map);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JToggleButton button = (JToggleButton)arg0.getSource(); 
		if(button.isSelected()){
			map.getLayers().get(0).setOpacity(0.3f);
			map.getLayers().get(1).setOpacity(0.3f);
			map.getLayers().get(3).setOpacity(0.3f);
			int[] ids = track.getGraphicIDs();
			for(int i = 0; i < ids.length; i++){
				if(track.getGraphic(ids[i]).getAttributeValue("TrackName").toString().startsWith("A")){
					track.updateGraphic(ids[i], new SimpleLineSymbol(Color.BLUE,0));
				}
			}
			hitTestOverlay.addHitTestListener(info);
			map.addMapOverlay(hitTestOverlay);
		}else{
			int[] ids = track.getGraphicIDs();
			for(int i = 0; i < ids.length; i++){
				if(track.getGraphic(ids[i]).getAttributeValue("TrackName").toString().startsWith("A")){
					track.updateGraphic(ids[i], new SimpleLineSymbol(Color.BLUE,2));
				}
			}
			map.getLayers().get(0).setOpacity(1f);
			map.getLayers().get(1).setOpacity(1f);
			map.getLayers().get(3).setOpacity(1f);
			map.removeMapOverlay(hitTestOverlay);
			hitTestOverlay.removeHitTestListener(info);
		}
	}
}
