/*
 * 
 */
package content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.esri.toolkit.JLayerList;

import GUI.LegendButton;
import GUI.LegendPanel;
import GUI.MapLayer;
import GUI.MapLayerButton;
import GUI.mapCoordPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiPane creates the GUI elements overlayed on top of the main map pane.
 * The GUI elements are: the map coordinate information, the layer list, the legend, and the warning notification
 * @author Sidharth Prem
 * @see content.MapPane
 */
public class GuiPane {
	
	/** The main layer list. */
	JLayerList mainLayerList;
	
	/** The map coordinate information text. */
	JTextArea coordTxt;
	
	/** The screen size. */
	Dimension screenSize;
	
	/**
	 * Instantiates a new gui pane.
	 */
	public GuiPane(){}

	/**
	 * Instantiates a new gui pane.
	 *
	 * @param list the layer list
	 * @param txt the coordinate text
	 * @param screen the screen size
	 */
	public GuiPane(JLayerList list, JTextArea txt, Dimension screen){
		this.mainLayerList = list;
		this.coordTxt = txt;
		this.screenSize = screen;
	}

	/**
	 * Gets the GUI pane. It uses a transparent pane containing elements at each corner
	 * @see GUI
	 *
	 * @return the GUI pane
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JPanel getGUIpane() throws IOException{
		LegendPanel lPanel = new LegendPanel(screenSize.width,screenSize.height);
		JPanel legendPanel = lPanel.getLegend();
		LegendButton lgndbtn = new LegendButton(legendPanel,screenSize.width,screenSize.height);	

		mapCoordPanel mPanel = new mapCoordPanel();

		MapLayer lyrPanel = new MapLayer(screenSize.width,screenSize.height);
		JPanel layerPanel = lyrPanel.getMapLayer(mainLayerList); 
		MapLayerButton mbtn = new MapLayerButton(layerPanel,screenSize.width,screenSize.height);
		
		JPanel warning = new JPanel();
		warning.setOpaque(false);
		warning.setBackground(Color.WHITE);
		
		JTextArea text = new JTextArea();
		text.setText("Preliminary results, do not cite or quote.");
		text.setForeground(Color.RED);
		text.setFont(new Font(text.getFont().getName(),Font.BOLD,18));
		text.setBackground(Color.WHITE);
		warning.add(text);

		JPanel guiPane = new JPanel();
		guiPane.setLayout(new GridBagLayout());
		guiPane.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,10,0,0);
		guiPane.add(mbtn.getMapButton(), c);

		GridBagConstraints d = new GridBagConstraints();
		d.anchor = GridBagConstraints.FIRST_LINE_START;
		d.gridx = 0;
		d.gridy = 1;
		c.weightx = 1;
		d.weighty = 500;
		d.insets = new Insets(5,10,0,0);
		guiPane.add(layerPanel, d);

		GridBagConstraints e = new GridBagConstraints();
		e.anchor = GridBagConstraints.FIRST_LINE_END;
		e.gridx = 1;
		e.gridy = 0;
		e.weightx = 1;
		e.weighty = 0;
		e.insets = new Insets(10,0,0,10);
		guiPane.add(lgndbtn.getLegendButton(), e);

		GridBagConstraints f = new GridBagConstraints();
		f.anchor = GridBagConstraints.FIRST_LINE_END;
		f.gridx = 1;
		f.gridy = 1;
		f.weightx = 1;
		f.weighty = 500;
		f.insets = new Insets(5,10,0,10);
		guiPane.add(legendPanel,f);

		GridBagConstraints g = new GridBagConstraints();
		g.anchor = GridBagConstraints.LAST_LINE_START;
		g.gridx = 0;
		g.gridy = 2;
		g.weightx = 1;
		g.weighty = 10;
		g.insets = new Insets(0,10,10,0);
		guiPane.add(mPanel.getCoordPanel(coordTxt),g);

		GridBagConstraints n = new GridBagConstraints();
		n.anchor = GridBagConstraints.LAST_LINE_END;
		n.gridx = 1;
		n.gridy = 2;
		n.weightx = 1;
		n.weighty = 1;
		n.insets = new Insets(0, 0, 10, 10);
		guiPane.add(warning, n);

		GridBagConstraints de = new GridBagConstraints();
		de.fill = GridBagConstraints.BOTH;
		de.gridx = 1;
		de.gridy = 2;
		guiPane.add(Box.createGlue(),de);

		return guiPane;
	}
}
