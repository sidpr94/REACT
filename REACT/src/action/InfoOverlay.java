package action;

import javax.swing.JComponent;

import com.esri.core.map.Feature;
import com.esri.map.FeatureLayer;
import com.esri.map.JMap;
import com.esri.map.popup.PopupDialog;
import com.esri.map.popup.PopupView;
import com.esri.map.popup.PopupViewEvent;
import com.esri.map.popup.PopupViewListener;
import com.esri.toolkit.overlays.HitTestEvent;
import com.esri.toolkit.overlays.HitTestListener;
import com.esri.toolkit.overlays.HitTestOverlay;

public class InfoOverlay implements HitTestListener{
	HitTestOverlay overlay;
	FeatureLayer layer;
	JMap jMap;
	public InfoOverlay(FeatureLayer featureLayer, JMap map) {
		this.layer = featureLayer;	
		this.jMap = map;
	}
	@Override
	public void featureHit(HitTestEvent event) {
		HitTestOverlay overlay = event.getOverlay();
		// get first (top-most) graphic hit by the mouse
		Feature hitGraphic = overlay.getHitFeatures().get(0);
		// create a popup in edit view
		PopupView contentPanel = PopupView.createEditView("Edit Attributes",layer);
		// you can optionally set a preferred size for the PopupView
		//contentPanel.setPreferredSize(new Dimension(300, 300));
		contentPanel.setFeature(jMap, hitGraphic);
		final PopupDialog popup = jMap.createPopup(new JComponent[]{contentPanel}, hitGraphic);
		popup.setTitle("Edit Attributes");
		popup.setVisible(true);
		contentPanel.addPopupViewListener(new PopupViewListener() {

			@Override
			public void onCommitEdit(PopupViewEvent popupViewEvent, Feature feature) {
				popup.close();
			}

			@Override
			public void onCancelEdit(PopupViewEvent popupViewEvent, Feature feature) {
				popup.close();
			}

		});
		// handle any error here as desired
	}

}
