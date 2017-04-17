package content;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTable;

import com.esri.map.JMap;
import com.esri.toolkit.JLayerList;

public class ContentPane {
	JMap jMap;
	JMap compare;
	JLayerList jLayerList;
	Dimension d;
	JTable table;
	public ContentPane(){}

	public ContentPane(JMap map, JLayerList list, Dimension screen,JMap compare,JTable table){
		this.jMap = map;
		this.jLayerList = list;
		this.d = screen;
		this.compare = compare;
		this.table = table;
	}
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
