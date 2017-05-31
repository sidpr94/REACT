/*
 * 
 */
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

import basemap.PopMap;

// TODO: Auto-generated Javadoc
/**
 * The Class InfoOverlayDensity is the control center for how users interact with population blocks.
 * It first checks to see if the user is clicking on an editable or non-editable population block.
 * If the population block is non-editable then the information displayed will show only Population Count.
 * If the population block is editable then the information displayed will show Current Population Count, Original Count,
 * and a text field to change the population count. The user can accept the change for the population block and visually see the change.
 * 
 * Information is statically stored in a list to ensure the program knows which graphics were edited, and then those are reset to baseline scenarios.
 * 
 * @author Sidharth Prem
 * @see scenarioDev.density.DensityOn
 */
public class InfoOverlayDensity implements HitTestListener{
	
	/** The overlay that allows the user to interact with population block. */
	HitTestOverlay overlay;
	
	/** The layer. */
	GraphicsLayer editLayer;
	
	/** The flayer. */
	FeatureLayer noEditLayer;
	
	/** The button that allows the user to reset the graphics to original attribute values. */
	JButton resetToCensus;
	
	/** The list. */
	private static Map<Integer,Graphic> changedBlocks = new HashMap<Integer,Graphic>();
	
	/** The main map. */
	JMap mainMap;
	
	/**
	 * Instantiates a new info overlay density if the block clicked is editable.
	 *
	 * @param featureLayer the feature layer
	 * @param map the map
	 * @param btn the btn
	 */
	public InfoOverlayDensity(GraphicsLayer featureLayer, JMap map, JButton btn) {
		this.editLayer = featureLayer;	
		this.mainMap = map;
		this.resetToCensus = btn;
	}

	/**
	 * Instantiates a new info overlay density if the block clicked is not editable.
	 *
	 * @param featureLayer the feature layer
	 * @param map the map
	 */
	public InfoOverlayDensity(FeatureLayer featureLayer, JMap map) {
		this.noEditLayer = featureLayer;	
		this.mainMap = map;
	}	
	
	/* (non-Javadoc)
	 * @see com.esri.toolkit.overlays.HitTestListener#featureHit(com.esri.toolkit.overlays.HitTestEvent)
	 */
	@Override
	public void featureHit(HitTestEvent event) {
		// TODO Auto-generated method stub
		if(noEditLayer != null){
			HitTestOverlay overlay = event.getOverlay();
			// get first (top-most) graphic hit by the mouse
			Feature hitGraphic = overlay.getHitFeatures().get(0);
			// create a popup in edit view
			PopupView contentPanel = PopupView.createAttributesView("", hitGraphic);
			contentPanel.setFeature(mainMap, hitGraphic);
			final PopupDialog popup = mainMap.createPopup(new JComponent[]{contentPanel}, hitGraphic);
			popup.setTitle("Population Information");
			popup.setVisible(true);
		}
		else if(editLayer != null){
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

				final PopupDialog popup = mainMap.createPopup(new JComponent[]{panel}, hitGraphic);
				popup.setTitle("Land Planning Simulation");
				popup.setVisible(true);

				btn.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						Map<String,Object> att = new HashMap<String,Object>();
						att.put("POP10", spinner.getValue());
						changedBlocks.put(hitGraphic.getUid(), hitGraphic);
						PopMap.graphics.replace(hitGraphic.getUid(), hitGraphic);
						Graphic replace = new Graphic(hitGraphic.getGeometry(),null,att);
						editLayer.updateGraphic(hitGraphic.getUid(), replace);
						popup.close();
					}

				});

				resetToCensus.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(changedBlocks.size()!= 0){
							for(Map.Entry<Integer, Graphic> entry:changedBlocks.entrySet()){
								Graphic g = entry.getValue();
								Graphic reset = new Graphic(g.getGeometry(),null,g.getAttributes());
								editLayer.updateGraphic(entry.getKey(), reset);
								PopMap.graphics.replace(entry.getKey(), reset);
							}
							changedBlocks.clear();
						}
					}

				});

			}
		}
	}

}
