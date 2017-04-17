package results;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTable;

import com.esri.map.JMap;

public class ResultPane {
	JMap map;
	Dimension d;
	JTable table;
	public ResultPane(JMap map,Dimension d,JTable table){
		this.map = map;
		this.d = d;
		this.table = table;
	}
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
