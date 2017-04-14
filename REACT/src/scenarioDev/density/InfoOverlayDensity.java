package scenarioDev.density;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.esri.core.map.Feature;
import com.esri.core.map.Graphic;
import com.esri.map.FeatureLayer;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.map.popup.PopupDialog;
import com.esri.map.popup.PopupView;
import com.esri.toolkit.overlays.HitTestEvent;
import com.esri.toolkit.overlays.HitTestListener;
import com.esri.toolkit.overlays.HitTestOverlay;

public class InfoOverlayDensity implements HitTestListener{
	HitTestOverlay overlay;
	GraphicsLayer layer;
	FeatureLayer flayer;
	JButton button;
	private static Map<Integer,Graphic> list = new HashMap<Integer,Graphic>();
	JMap jMap;
	int x = 0;
	int y = 0;
	public InfoOverlayDensity(GraphicsLayer featureLayer, JMap map, JButton btn) {
		this.layer = featureLayer;	
		this.jMap = map;
		this.button = btn;
	}

	public InfoOverlayDensity(FeatureLayer featureLayer, JMap map) {
		this.flayer = featureLayer;	
		this.jMap = map;
	}	
	@Override
	public void featureHit(HitTestEvent event) {
		// TODO Auto-generated method stub
		if(flayer != null){
			HitTestOverlay overlay = event.getOverlay();
			// get first (top-most) graphic hit by the mouse
			Feature hitGraphic = overlay.getHitFeatures().get(0);
			// create a popup in edit view
			PopupView contentPanel = PopupView.createAttributesView("", hitGraphic);
			contentPanel.setFeature(jMap, hitGraphic);
			final PopupDialog popup = jMap.createPopup(new JComponent[]{contentPanel}, hitGraphic);
			popup.setTitle("Population Information");
			popup.setVisible(true);
		}
		else if(layer != null){
			HitTestOverlay overlay = event.getOverlay();
			// get first (top-most) graphic hit by the mouse
			Graphic hitGraphic = (Graphic)overlay.getHitFeatures().get(0);
			if(hitGraphic.getUid() != 738){
				JPanel panel = new JPanel(new GridBagLayout());
				panel.setBackground(Color.WHITE);

				JLabel title = new JLabel("Population Information");
				title.setOpaque(false);
				title.setForeground(Color.BLACK);

				JPanel content = new JPanel(new GridLayout(2,2));

				JTextField des = new JTextField();
				des.setText("Current Population Count:\t");
				des.setBackground(Color.WHITE);
				des.setEditable(false);
				des.setEnabled(false);
				des.setDisabledTextColor(Color.BLACK);
				des.setBorder(BorderFactory.createEmptyBorder());

				JTextField pop = new JTextField();
				pop.setText(""+hitGraphic.getAttributeValue("POP10"));
				pop.setBackground(Color.WHITE);
				pop.setEditable(false);
				pop.setEnabled(false);
				pop.setDisabledTextColor(Color.BLACK);
				pop.setFont(new Font(pop.getFont().getName(),Font.BOLD,12));
				pop.setBorder(BorderFactory.createEmptyBorder());

				JTextField des1 = new JTextField();
				des1.setText("Zoned Population: ");
				des1.setBackground(Color.WHITE);
				des1.setEditable(false);
				des1.setEnabled(false);
				des1.setDisabledTextColor(Color.BLACK);
				des1.setBorder(BorderFactory.createEmptyBorder());

				JSpinner spinner = new JSpinner();
				SpinnerModel mdel = new SpinnerNumberModel((Number) hitGraphic.getAttributeValue("POP10"),0,3000,1);
				spinner.setModel(mdel);

				JButton btn = new JButton("Commit Change");

				content.add(des);
				content.add(pop);
				content.add(des1);
				content.add(spinner);

				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				c.gridy = 0;
				c.weightx = 1;
				panel.add(title,c);

				GridBagConstraints e = new GridBagConstraints();
				e.gridx = 0;
				e.gridy = 1;
				e.weighty = 1;
				panel.add(content,e);

				GridBagConstraints f = new GridBagConstraints();
				f.gridx = 0;
				f.gridy = 2;
				f.weighty = 1;
				panel.add(btn,f);

				final PopupDialog popup = jMap.createPopup(new JComponent[]{panel}, hitGraphic);
				popup.setTitle("Land Planning Simulation");
				popup.setVisible(true);

				btn.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						Map<String,Object> att = new HashMap<String,Object>();
						att.put("POP10", spinner.getValue());
						list.put(hitGraphic.getUid(), hitGraphic);
						Graphic replace = new Graphic(hitGraphic.getGeometry(),null,att);
						layer.updateGraphic(hitGraphic.getUid(), replace);
						popup.close();
					}

				});

				button.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(list.size()!= 0){
							for(Map.Entry<Integer, Graphic> entry:list.entrySet()){
								Graphic g = entry.getValue();
								Graphic reset = new Graphic(g.getGeometry(),null,g.getAttributes());
								layer.updateGraphic(entry.getKey(), reset);

							}
							list.clear();
						}
					}

				});

			}
		}
	}

}
