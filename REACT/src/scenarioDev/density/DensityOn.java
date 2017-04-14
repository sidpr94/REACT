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

public class DensityOn implements ActionListener {
	JMap map;
	GraphicsLayer pop;
	FeatureLayer popnoedit;
	HitTestOverlay hitTestOverlay;
	InfoOverlayDensity info;
	InfoOverlayDensity info1;
	HitTestOverlay hitTestOverlay1;
	public DensityOn(JMap jMap,JButton btn){
		this.map = jMap;
		this.pop = (GraphicsLayer) jMap.getLayers().get(1);
		this.popnoedit = (FeatureLayer) jMap.getLayers().get(0);
		this.hitTestOverlay = new HitTestOverlay(pop);
		this.hitTestOverlay1 = new HitTestOverlay(popnoedit);
		this.info = new InfoOverlayDensity(pop,map,btn);
		this.info1 = new InfoOverlayDensity(popnoedit,map);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JToggleButton button = (JToggleButton)arg0.getSource(); 
		if(button.isSelected()){
			map.getLayers().get(0).setOpacity(0.5f);
			map.getLayers().get(2).setOpacity(0.5f);
			map.getLayers().get(3).setOpacity(0.5f);
			ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
			cbrenderer.addBreak(0,14,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(15,47,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(48,108,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(109,235,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(236,488,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(489,1130,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.RED,2)));
			cbrenderer.addBreak(1131,2362,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.RED,2)));
			pop.setRenderer(cbrenderer);
			hitTestOverlay.addHitTestListener(info);
			map.addMapOverlay(hitTestOverlay);
			hitTestOverlay1.addHitTestListener(info1);
			map.addMapOverlay(hitTestOverlay1);
		}else{
			map.getLayers().get(0).setOpacity(1f);
			map.getLayers().get(2).setOpacity(1f);
			map.getLayers().get(3).setOpacity(1f);
			ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
			cbrenderer.addBreak(0,14,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(15,47,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(48,108,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(109,235,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(236,488,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(489,1130,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(1131,2362,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			pop.setRenderer(cbrenderer);
			map.removeMapOverlay(hitTestOverlay);
			map.removeMapOverlay(hitTestOverlay1);
			hitTestOverlay.removeHitTestListener(info);
			hitTestOverlay1.removeHitTestListener(info1);
		}
	}

}
