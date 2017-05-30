/*
 * 
 */
package content;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTable;

import com.esri.map.JMap;
import com.esri.toolkit.JLayerList;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentPane creates the content seen in the main Map Pane.
 * It contains the dashboard to the west, map in the center, with GUI elements overlayed.
 * The content panel is a BorderLayout and is the highest-level for all content in the Map Pane.
 * All other panels are contained within this JPanel.
 * @author Sidharth Prem 
 * @see basemap.REACT
 */
public class ContentPane {
	
	/** The main map. */
	JMap mainMap;
	
	/** The noise contour comparison map. */
	JMap compare;
	
	/** The main layer list. */
	JLayerList mainLayerList;
	
	/** The screen size. */
	Dimension screenSize;
	
	/** The table containing ANGIM's results. */
	JTable resultsTable;
	
	/**
	 * Instantiates a new content pane.
	 */
	public ContentPane(){}

	/**
	 * Instantiates a new content pane.
	 *
	 * @param map the map
	 * @param list the list
	 * @param screen the screen
	 * @param compare the compare
	 * @param table the table
	 */
	public ContentPane(JMap map, JLayerList list, Dimension screen,JMap compare,JTable table){
		this.mainMap = map;
		this.mainLayerList = list;
		this.screenSize = screen;
		this.compare = compare;
		this.resultsTable = table;
	}
	
	/**
	 * Gets the content pane.
	 *
	 * @return the content pane
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JPanel getContentPane() throws IOException{
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0,0));
		DashBoard dPanel = new DashBoard(mainMap,mainLayerList,compare,resultsTable);

		MapPane mapPane = new MapPane(mainMap,mainLayerList,screenSize);

		contentPane.add(mapPane.getmapPane(),BorderLayout.CENTER);
		contentPane.add(dPanel.getDashboard(), BorderLayout.WEST);
		return contentPane;

	}
}
