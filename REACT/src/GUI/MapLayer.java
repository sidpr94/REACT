package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.esri.toolkit.JLayerList;

public class MapLayer {
	int width;
	int height;
	public MapLayer(int w, int h){
		this.width = w;
		this.height = h;
	}

	public JPanel getMapLayer(JLayerList jLayerlist){
		//Creates a text field for the title of the control panel
		JTextField txtTitle = new JTextField();
		txtTitle.setText("Map Layers");
		txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitle.setFont(new Font(txtTitle.getFont().getName(),Font.BOLD,16));
		txtTitle.setPreferredSize(new Dimension(260,30));
		txtTitle.setBackground(new Color(0,0,0,100));
		txtTitle.setForeground(Color.WHITE);
		txtTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

		//creates the control panel of the proper size and background to visualize the layers in
		final JPanel controlPanel = new JPanel();
		BoxLayout boxLayout1 = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
		controlPanel.setLayout(boxLayout1);
		//controlPanel.setLocation(240,15+(int) (height/32));
		controlPanel.setPreferredSize(new Dimension(260,225));
		controlPanel.setBackground(new Color(0,0,0,100));
		controlPanel.setBorder(new LineBorder(Color.BLACK,3));
		controlPanel.add(txtTitle);
		controlPanel.add(jLayerlist);
		return controlPanel;
	}
}
