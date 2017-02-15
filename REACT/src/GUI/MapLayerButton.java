package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MapLayerButton {
	JPanel mapPanel;
	int width;
	int height;
	public MapLayerButton(JPanel mPanel, int w, int h){
		this.mapPanel = mPanel;
		this.width = w;
		this.height = h;
	}
	public JButton getMapButton(){
	  final JButton btnMapToggle = new JButton("Toggle Map Layer");
	  btnMapToggle.setPreferredSize(new Dimension(260,(int) (height/32)));
	  btnMapToggle.setVisible(true);
	  btnMapToggle.addActionListener(new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	        mapPanel.setVisible(!mapPanel.isVisible());
	      }
	    });
	    return btnMapToggle;
	}
}
