/*
 * 
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class CompareContourMap.
 */
public class CompareContourMap {
	
	/** The map. */
	JMap map;
	
	/** The d. */
	Dimension d;
	
	/**
	 * Instantiates a new compare contour map.
	 *
	 * @param map the map
	 * @param d the d
	 */
	public CompareContourMap(JMap map,Dimension d){
		this.map = map;
		this.d = d;
	}
	
	/**
	 * Creates the map pane.
	 *
	 * @return the j panel
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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
