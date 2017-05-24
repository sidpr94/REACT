/*
 * 
 */
package content;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;

import com.esri.map.JMap;
import com.esri.toolkit.JLayerList;

import GUI.mapCoordPanel;
import basemap.CreateMainMap;

// TODO: Auto-generated Javadoc
/**
 * The Class MapPane.
 */
public class MapPane {
	
	/** The j map. */
	JMap jMap;
	
	/** The j layer list. */
	JLayerList jLayerList;
	
	/** The d. */
	Dimension d;
	
	/**
	 * Instantiates a new map pane.
	 */
	public MapPane(){}
	
	/**
	 * Instantiates a new map pane.
	 *
	 * @param map the map
	 * @param list the list
	 * @param screen the screen
	 */
	public MapPane(JMap map, JLayerList list, Dimension screen){
		this.jMap = map;
		this.jLayerList = list;
		this.d = screen;
	}
	
	/**
	 * Gets the map pane.
	 *
	 * @return the map pane
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JPanel getmapPane() throws IOException{
		JPanel mapPane = new JPanel();
		mapPane.setLayout(new OverlayLayout(mapPane));
		mapCoordPanel mPanel = new mapCoordPanel(d.width,d.height);
		JTextArea coordTxt = mPanel.getCoordTxt();
		GuiPane gui = new GuiPane(jLayerList,coordTxt,d);
		CreateMainMap mapCreator = new CreateMainMap(jMap,jLayerList,coordTxt);
		mapPane.add(gui.getGUIpane());
		mapPane.add(mapCreator.getMap());
		return mapPane;
	}
}
