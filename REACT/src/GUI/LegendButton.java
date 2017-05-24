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
 * The Class LegendButton.
 */
public class LegendButton {
	
	/** The legend panel. */
	JPanel legendPanel;
	
	/** The width. */
	int width;
	
	/** The height. */
	int height;
	
	/**
	 * Instantiates a new legend button.
	 *
	 * @param lPanel the l panel
	 * @param w the w
	 * @param h the h
	 */
	public LegendButton(JPanel lPanel, int w, int h){
		this.legendPanel = lPanel;
		this.width = w;
		this.height = h;
	}
	
	/**
	 * Gets the legend button.
	 *
	 * @return the legend button
	 */
	public JButton getLegendButton(){
	  final JButton btnLegendToggle = new JButton("Toggle Legend");
	    btnLegendToggle.setPreferredSize(new Dimension(width/8, height/32));
	    btnLegendToggle.setVisible(true);
	    btnLegendToggle.addActionListener(new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	        legendPanel.setVisible(!legendPanel.isVisible());
	      }
	    });
	    return btnLegendToggle;
	}
}
