package results;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import com.esri.map.JMap;
import com.esri.toolkit.JLayerList;

public class CompareContourMap {
	JMap map;
	Dimension d;
	public CompareContourMap(JMap map,Dimension d){
		this.map = map;
		this.d = d;
	}
	public JPanel createMapPane() throws IOException{
		JPanel mapPane = new JPanel();
		JLayerList list = new JLayerList(map);
		mapPane.setLayout(new OverlayLayout(mapPane));
		
		JPanel guiPane = new JPanel();
		guiPane.setLayout(new GridBagLayout());  
		guiPane.setOpaque(false);
		
		ResultLayerList layerPane = new ResultLayerList();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 1;
		c.weighty = 100;
		c.insets = new Insets(10, 10, 0, 0);
		guiPane.add(layerPane.getMapLayer(list), c);
		
		ResultsLegend legend = new ResultsLegend(d.width, d.height);
		GridBagConstraints d = new GridBagConstraints();
		d.gridx = 1;
		d.gridy = 0;
		d.anchor = GridBagConstraints.FIRST_LINE_END;
		d.weightx = 1;
		d.insets = new Insets(10,0,0,10);
		d.weighty = 100;
		guiPane.add(legend.createLegend(), d);
		
		guiPane.add(Box.createGlue());
		
		mapPane.add(guiPane);
		ContourMap maps = new ContourMap(map,list);
		mapPane.add(maps.createMap());
		
		return mapPane;
	}
}
