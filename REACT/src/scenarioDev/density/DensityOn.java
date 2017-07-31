/*
 * 
 */
package scenarioDev.density;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;

import com.esri.core.renderer.ClassBreaksRenderer;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.FeatureLayer;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.toolkit.overlays.HitTestOverlay;

// TODO: Auto-generated Javadoc
/**
 * The Class DensityOn allows the density control button to create a hit test listener to monitor graphic attributes and color.
 * This action listener creates the hit test, changes visibility of layers to make the visuals more noticeable, and checks to see
 * if the button is toggled on or off.
 * @author Sidharth Prem
 * @see scenarioDev.density.InfoOverlayDensity
 * @see scenarioDev.density.DensityControl
 */
public class DensityOn implements ActionListener {
	
	/** The main map. */
	JMap mainMap;
	
	/** The editable population layer. */
	GraphicsLayer pop;
	
	/** The non-editable population layer. */
	FeatureLayer popnoedit;
	
	/** Creates the interface that allows a user to click on the editable population layer and receive information. */
	HitTestOverlay hitTestOverlayEdit;
	
	/** The overlay creator for the editable population block. */
	InfoOverlayDensity infoEdit;
	
	/** The overlay creator for the non-editable population block. */
	InfoOverlayDensity infoNoEdit;
	
	/** The hit test overlay for the non-editable population block. */
	HitTestOverlay hitTestOverlayNoEdit;
	
	/**
	 * Instantiates a new density on.
	 *
	 * @param jMap the j map
	 * @param btn the btn
	 */
	public DensityOn(JMap jMap,JButton btn){
		this.mainMap = jMap;
		this.pop = (GraphicsLayer) jMap.getLayers().get(1);
		this.popnoedit = (FeatureLayer) jMap.getLayers().get(0);
		this.hitTestOverlayEdit = new HitTestOverlay(pop);
		this.hitTestOverlayNoEdit = new HitTestOverlay(popnoedit);
		this.infoEdit = new InfoOverlayDensity(pop,mainMap,btn);
		this.infoNoEdit = new InfoOverlayDensity(popnoedit,mainMap);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	/**
	 * This action listener checks to see if the button is selected (toggled on) or off.
	 * If the button is active it changes visiblity of other layers and makes the editable population more prominent.
	 * It also creates the hit test feature which allows users to click on graphics/features of the population map and see attribute info.
	 * If the population block is editable, then the user can change the attribute for that graphic and automatically change the color as well.
	 * 
	 * If the button is un-selected, the map view returns to normal and the hit test is removed.
	 */
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JToggleButton button = (JToggleButton)arg0.getSource(); 
		if(button.isSelected()){
			mainMap.getLayers().get(0).setOpacity(0.5f);
			mainMap.getLayers().get(2).setOpacity(0.5f);
			mainMap.getLayers().get(3).setOpacity(0.5f);
			/*
			ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
			cbrenderer.addBreak(0,919,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(920,1310,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(1311,1769,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(1770,2343,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(2344,3204,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(3205,4838,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(4839,9658,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.RED,2)));
			*/
			ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
			cbrenderer.addBreak(0,14,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(15,47,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(48,108,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(109,235,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(236,488,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(489,1130,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(1131,2362,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.RED,2)));
			
			pop.setRenderer(cbrenderer);
			hitTestOverlayEdit.addHitTestListener(infoEdit);
			mainMap.addMapOverlay(hitTestOverlayEdit);
			hitTestOverlayNoEdit.addHitTestListener(infoNoEdit);
			mainMap.addMapOverlay(hitTestOverlayNoEdit);
		}else{
			mainMap.getLayers().get(0).setOpacity(1f);
			mainMap.getLayers().get(2).setOpacity(1f);
			mainMap.getLayers().get(3).setOpacity(1f);
			/*
			ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
			cbrenderer.addBreak(0,919,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(920,1310,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(1311,1769,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(1770,2343,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(2344,3204,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(3205,4838,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(4839,9658,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			*/
			ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
			cbrenderer.addBreak(0,14,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(15,47,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(48,108,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(109,235,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(236,488,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(489,1130,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(1131,2362,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			
			pop.setRenderer(cbrenderer);
			mainMap.removeMapOverlay(hitTestOverlayEdit);
			mainMap.removeMapOverlay(hitTestOverlayNoEdit);
			hitTestOverlayEdit.removeHitTestListener(infoEdit);
			hitTestOverlayNoEdit.removeHitTestListener(infoNoEdit);
		}
	}

}
