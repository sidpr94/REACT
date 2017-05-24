/*
 * 
 */
package results;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTable;

import com.esri.map.JMap;

// TODO: Auto-generated Javadoc
/**
 * The Class ResultPane.
 */
public class ResultPane {
	
	/** The map. */
	JMap map;
	
	/** The d. */
	Dimension d;
	
	/** The table. */
	JTable table;
	
	/**
	 * Instantiates a new result pane.
	 *
	 * @param map the map
	 * @param d the d
	 * @param table the table
	 */
	public ResultPane(JMap map,Dimension d,JTable table){
		this.map = map;
		this.d = d;
		this.table = table;
	}
	
	/**
	 * Creates the pane.
	 *
	 * @return the j panel
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JPanel createPane() throws IOException{
		JPanel resultsPane = new JPanel();
		CompareContourMap con = new CompareContourMap(map,d);
		resultsPane.setLayout(new BorderLayout(0,0));
		resultsPane.add(con.createMapPane(), BorderLayout.CENTER);
		ResultsTable crash = new ResultsTable();
		resultsPane.add(crash.getfinePane(table), BorderLayout.WEST);
		return resultsPane;
	}
}
