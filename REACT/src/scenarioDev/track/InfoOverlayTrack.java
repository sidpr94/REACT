package scenarioDev.track;

import java.awt.Color;
import java.awt.Point;
import java.io.FileNotFoundException;

import javax.swing.JComponent;

import com.esri.core.map.Feature;
import com.esri.core.renderer.SimpleRenderer;
import com.esri.core.renderer.UniqueValueInfo;
import com.esri.core.renderer.UniqueValueRenderer;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.FeatureLayer;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.popup.PopupDialog;
import com.esri.map.popup.PopupView;
import com.esri.map.popup.PopupViewEvent;
import com.esri.map.popup.PopupViewListener;
import com.esri.toolkit.overlays.HitTestEvent;
import com.esri.toolkit.overlays.HitTestListener;
import com.esri.toolkit.overlays.HitTestOverlay;

public class InfoOverlayTrack implements HitTestListener{
	HitTestOverlay overlay;
	FeatureLayer layer;
	JMap jMap;
	public InfoOverlayTrack(FeatureLayer featureLayer, JMap map) {
		this.layer = featureLayer;	
		this.jMap = map;
	}
	@Override
	public void featureHit(HitTestEvent event) {
		
	}

}
