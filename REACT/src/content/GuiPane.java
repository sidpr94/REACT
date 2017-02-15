package content;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.esri.toolkit.JLayerList;

import GUI.LegendButton;
import GUI.LegendPanel;
import GUI.MapLayer;
import GUI.MapLayerButton;
import GUI.mapCoordPanel;

public class GuiPane {
	JLayerList jLayerList;
	JTextArea coordTxt;
	Dimension d;
	public GuiPane(){}
	
	public GuiPane(JLayerList list, JTextArea txt, Dimension screen){
		this.jLayerList = list;
		this.coordTxt = txt;
		this.d = screen;
	}
	
	public JPanel getGUIpane(){
		LegendPanel lPanel = new LegendPanel(d.width,d.height);
		JPanel legendPanel = lPanel.getLegend();
		LegendButton lgndbtn = new LegendButton(legendPanel,d.width,d.height);	
		
		mapCoordPanel mPanel = new mapCoordPanel(d.width,d.height);
		
		MapLayer lyrPanel = new MapLayer(d.width,d.height);
		JPanel layerPanel = lyrPanel.getMapLayer(jLayerList); 
		MapLayerButton mbtn = new MapLayerButton(layerPanel,d.width,d.height);
		
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
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 1;
		g.insets = new Insets(0,10,10,0);
		guiPane.add(mPanel.getCoordPanel(coordTxt),g);
		
		GridBagConstraints de = new GridBagConstraints();
		de.fill = GridBagConstraints.BOTH;
		de.gridx = 1;
		de.gridy = 2;
		guiPane.add(Box.createGlue(),de);
		
		return guiPane;
	}
}
