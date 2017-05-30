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
 * The Class MapPane adds the main map and the GUI elements into this panel. The Map pane is underneath the transparent GUI pane.
 * @author Sidharth Prem
 * @see content.ContentPane
 */
public class MapPane {
	
	/** The main map. */
	JMap mainMap;
	
	/** The main layer list. */
	JLayerList mainLayerList;
	
	/** The screen size. */
	Dimension screenSize;
	
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
		this.mainMap = map;
		this.mainLayerList = list;
		this.screenSize = screen;
	}
	
	/**
	 * Gets the map pane.
	 * @see GUI
	 * @return the map pane
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JPanel getmapPane() throws IOException{
		JPanel mapPane = new JPanel();
		mapPane.setLayout(new OverlayLayout(mapPane));
		mapCoordPanel mPanel = new mapCoordPanel();
		JTextArea coordTxt = mPanel.getCoordTxt();
		GuiPane gui = new GuiPane(mainLayerList,coordTxt,screenSize);
		CreateMainMap mapCreator = new CreateMainMap(mainMap,mainLayerList,coordTxt);
		mapPane.add(gui.getGUIpane());
		mapPane.add(mapCreator.getMap());
		return mapPane;
	}
}
