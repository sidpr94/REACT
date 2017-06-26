package trackflex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.text.BadLocationException;

import com.esri.map.JMap;


/**
 * This class creates the user interface tab for track flexibility
 * @author Dylan Monteiro
 */
public class TrackFlexPane {

	/** The screen size. */
	Dimension screenSize;
	
	/** The contour comparison map. */
	JMap trackMap;
	
	// Need to add the map with tracks and the table
	
	public TrackFlexPane(JMap map, Dimension d){
		this.trackMap = map;
		this.screenSize = d;
		//this.resultsTable = table;
	}

	public JPanel createPane() throws IOException, BadLocationException{
		JPanel TrackFlexPane = new JPanel();
		//CompareContourMap con = new CompareContourMap(compare,screenSize);
		TrackFlexPane.setLayout(new BorderLayout(0,0));
		//TrackFlexPane.add(con.createMapPane(), BorderLayout.CENTER);
		//ResultsTable crash = new ResultsTable(compare);
		//TrackFlexPane.add(crash.getfinePane(resultsTable), BorderLayout.WEST);
	
		TrackFlexPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 300;
		c.weighty = 100; 
		c.fill = GridBagConstraints.BOTH;
		//TrackFlexPane.add(crash.getfinePane(resultsTable), c);
		
		GridBagConstraints d = new GridBagConstraints();
		d.gridx = 1;
		d.gridy = 0;
		d.anchor = GridBagConstraints.PAGE_START;
		d.weightx = 50;
		d.weighty = 100; 
		d.fill = GridBagConstraints.BOTH;
		//TrackFlexPane.add(con.createMapPane(), d);
		return TrackFlexPane;
	}
	
}
