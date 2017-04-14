package scenarioDev.track;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import com.esri.map.FeatureLayer;
import com.esri.map.JMap;
import com.esri.toolkit.overlays.HitTestOverlay;


public class TrackOn implements ActionListener {
	JMap map;
	FeatureLayer track;
	HitTestOverlay hitTestOverlay;
	InfoOverlayTrack info;
	public TrackOn(JMap jMap){
		this.map = jMap;
		this.track = (FeatureLayer) jMap.getLayers().get(1);
		this.hitTestOverlay = new HitTestOverlay(track);
		this.info = new InfoOverlayTrack(track,map);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JToggleButton button = (JToggleButton)arg0.getSource(); 
		if(button.isSelected()){
			hitTestOverlay.addHitTestListener(info);
			map.addMapOverlay(hitTestOverlay);
		}else{
			map.removeMapOverlay(hitTestOverlay);
			hitTestOverlay.removeHitTestListener(info);
		}
	}
}
