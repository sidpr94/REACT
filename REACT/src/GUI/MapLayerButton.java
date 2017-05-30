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
 * The Class MapLayerButton allows the user to toggle the visibility of the map layer panel.
 * @author Sidharth Prem
 * @see GUI.MapLayer
 */
public class MapLayerButton {
	
	/** The map layer panel. */
	JPanel mapPanel;
	
	/** The width of the screen. */
	int width;
	
	/** The height of the screen. */
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
	 * Gets the map button and adds an actionlistener that changes the visibility of the panel.
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
