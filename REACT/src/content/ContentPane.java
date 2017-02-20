package content;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.esri.map.JMap;
import com.esri.toolkit.JLayerList;

public class ContentPane {
	JMap jMap;
	JLayerList jLayerList;
	Dimension d;
	public ContentPane(){}
	
	public ContentPane(JMap map, JLayerList list, Dimension screen){
		this.jMap = map;
		this.jLayerList = list;
		this.d = screen;
	}
	public JPanel getContentPane(){
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0,0));
		DashBoard dPanel = new DashBoard(jMap,jLayerList);
		
		MapPane mapPane = new MapPane(jMap,jLayerList,d);
		
		contentPane.add(mapPane.getmapPane(),BorderLayout.CENTER);
		contentPane.add(dPanel.getDashboard(), BorderLayout.WEST);
		return contentPane;
		
	}
}
