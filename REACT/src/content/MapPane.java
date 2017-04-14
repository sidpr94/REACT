package content;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;

import com.esri.map.JMap;
import com.esri.toolkit.JLayerList;

import GUI.mapCoordPanel;
import basemap.CreateMap;

public class MapPane {
	JMap jMap;
	JLayerList jLayerList;
	Dimension d;
	public MapPane(){}
	public MapPane(JMap map, JLayerList list, Dimension screen){
		this.jMap = map;
		this.jLayerList = list;
		this.d = screen;
	}
	public JPanel getmapPane() throws IOException{
		JPanel mapPane = new JPanel();
		mapPane.setLayout(new OverlayLayout(mapPane));
		mapCoordPanel mPanel = new mapCoordPanel(d.width,d.height);
		JTextArea coordTxt = mPanel.getCoordTxt();
		GuiPane gui = new GuiPane(jLayerList,coordTxt,d);
		CreateMap mapCreator = new CreateMap(jMap,jLayerList,coordTxt);
		mapPane.add(gui.getGUIpane());
		mapPane.add(mapCreator.getMap());
		return mapPane;
	}
}
