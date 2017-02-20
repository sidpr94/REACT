package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.toolkit.JLayerList;

public class PressedActionRunway implements ActionListener {
	JMap map;
	JLayerList list;
	public PressedActionRunway(JMap jMap, JLayerList jLayerList){
		this.map = jMap;
		this.list = jLayerList;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Layer runway = map.getLayers().get(3);
		//runway.
	}

}
