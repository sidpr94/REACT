/*
 * 
 */
package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class MapLayerButton.
 */
public class MapLayerButton {
	
	/** The map panel. */
	JPanel mapPanel;
	
	/** The width. */
	int width;
	
	/** The height. */
	int height;
	
	/**
	 * Instantiates a new map layer button.
	 *
	 * @param mPanel the m panel
	 * @param w the w
	 * @param h the h
	 */
	public MapLayerButton(JPanel mPanel, int w, int h){
		this.mapPanel = mPanel;
		this.width = w;
		this.height = h;
	}
	
	/**
	 * Gets the map button.
	 *
	 * @return the map button
	 */
	public JButton getMapButton(){
	  final JButton btnMapToggle = new JButton("Toggle Map Layer");
	  btnMapToggle.setPreferredSize(new Dimension(265,height/32));
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
