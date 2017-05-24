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
 * The Class ContentPane.
 */
public class ContentPane {
	
	/** The j map. */
	JMap jMap;
	
	/** The compare. */
	JMap compare;
	
	/** The j layer list. */
	JLayerList jLayerList;
	
	/** The d. */
	Dimension d;
	
	/** The table. */
	JTable table;
	
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
		this.jMap = map;
		this.jLayerList = list;
		this.d = screen;
		this.compare = compare;
		this.table = table;
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
		DashBoard dPanel = new DashBoard(jMap,jLayerList,compare,table);

		MapPane mapPane = new MapPane(jMap,jLayerList,d);

		contentPane.add(mapPane.getmapPane(),BorderLayout.CENTER);
		contentPane.add(dPanel.getDashboard(), BorderLayout.WEST);
		return contentPane;

	}
}
