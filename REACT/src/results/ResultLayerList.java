package results;

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

public class ResultLayerList {
	
	public ResultLayerList(){}
	
	public JPanel getMapLayer(JLayerList jLayerlist){
		//Creates a text field for the title of the control panel
		JTextField txtTitle = new JTextField();
		txtTitle.setText("Map Layers");
		txtTitle.setEnabled(false);
		txtTitle.setEditable(false);
		txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitle.setFont(new Font(txtTitle.getFont().getName(),Font.BOLD,16));
		//txtTitle.setPreferredSize(new Dimension(260,30));
		txtTitle.setBackground(new Color(38,38,38));
		txtTitle.setDisabledTextColor(Color.WHITE);
		txtTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

		//creates the control panel of the proper size and background to visualize the layers in
		final JPanel controlPanel = new JPanel();
		BoxLayout boxLayout1 = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
		controlPanel.setLayout(boxLayout1);
		controlPanel.setPreferredSize(new Dimension(265,230));
		controlPanel.setOpaque(false);
		controlPanel.setBorder(new LineBorder(Color.BLACK,5));
		controlPanel.add(txtTitle);
		controlPanel.add(jLayerlist);
		return controlPanel;
	}
}
