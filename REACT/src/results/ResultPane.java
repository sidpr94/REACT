/*
 * 
 */
package results;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.text.BadLocationException;

import com.esri.map.JMap;

// TODO: Auto-generated Javadoc
/**
 * The Class ResultPane contains the results table to the west and the contour comparison map in the center.
 * @author Sidharth Prem
 * @see results.CompareContourMap
 * @see results.ResultsTable
 */
public class ResultPane {
	
	/** The contour comparison map. */
	JMap compare;
	
	/** The screen size. */
	Dimension screenSize;
	
	/** The results table. */
	JTable resultsTable;
	
	/**
	 * Instantiates a new result pane.
	 *
	 * @param map the map
	 * @param d the d
	 * @param table the table
	 */
	public ResultPane(JMap map,Dimension d,JTable table){
		this.compare = map;
		this.screenSize = d;
		this.resultsTable = table;
	}
	
	/**
	 * Creates the results pane containing the map and table.
	 *
	 * @return the result panel
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BadLocationException 
	 */
	public JPanel createPane() throws IOException, BadLocationException{
		JPanel resultsPane = new JPanel();
		CompareContourMap con = new CompareContourMap(compare,screenSize);
		ResultsTable crash = new ResultsTable(compare);
		/*
		resultsPane.setLayout(new BorderLayout(0,0));
		resultsPane.add(con.createMapPane(), BorderLayout.WEST);
		ResultsTable crash = new ResultsTable(compare);
		resultsPane.add(crash.getfinePane(resultsTable), BorderLayout.CENTER);
		*/
		resultsPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 300;
		c.weighty = 100; 
		c.fill = GridBagConstraints.BOTH;
		resultsPane.add(crash.getfinePane(resultsTable), c);
		
		GridBagConstraints d = new GridBagConstraints();
		d.gridx = 1;
		d.gridy = 0;
		d.anchor = GridBagConstraints.PAGE_START;
		d.weightx = 50;
		d.weighty = 100; 
		d.fill = GridBagConstraints.BOTH;
		resultsPane.add(con.createMapPane(), d);
		return resultsPane;
	}
}
