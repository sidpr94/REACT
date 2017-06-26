package trackflex;

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

import results.ContourMap;
import results.ResultLayerList;
import results.ResultsLegend;

/**
 * The Class CreateTrackMap adds the track map, legend, and layer list in the Track Pane.
 * This is done by using an invisible panel for the GUI components over the map (similar to main map).
 * @author Dylan Monteiro
 */

public class CreateTrackMap {

	/** The contour comparison map. */
	JMap comparison;
	
	/** The screen size. */
	Dimension screnSize;
	
	/**
	 * Instantiates a new compare contour map.
	 *
	 * @param map the map
	 * @param d the d
	 */
	public CreateTrackMap(JMap map,Dimension d){
		this.comparison = map;
		this.screnSize = d;
	}
	
	/**
	 * Creates the map panel containing the legend, layer list, and comparison map.
	 *
	 * @return the map panel
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JPanel createMapPane() throws IOException{
		JPanel mapPane = new JPanel();
		JLayerList list = new JLayerList(comparison);
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
		
		ResultsLegend legend = new ResultsLegend(screnSize.width, screnSize.height);
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
		ContourMap maps = new ContourMap(comparison,list);
		mapPane.add(maps.createMap());
		
		return mapPane;
	}

	
}
