/*
 * 
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

// TODO: Auto-generated Javadoc
/**
 * The Class mapCoordPanel creates the coordinate panel that contains information about the location of the mnouse
 * as a coordinate location.
 * @author Sidharth Prem
 * @see content.GuiPane
 */
public class mapCoordPanel {
	/**
	 * Instantiates a new map coord panel.
	 */
	public mapCoordPanel(){}
	/**
	 * Gets the coord panel containing text about the location on the map as the mouse moves.
	 *
	 * @param coordTxt the coord txt
	 * @return the coord panel
	 */
	public JPanel getCoordPanel(JTextArea coordTxt){
		JTextField coordTitle = new JTextField();
		coordTitle.setText("Map Coordinates");
		coordTitle.setEditable(false);
		coordTitle.setEnabled(false);
		coordTitle.setHorizontalAlignment(SwingConstants.CENTER);
		coordTitle.setFont(new Font(coordTitle.getFont().getName(),Font.BOLD,16));
		coordTitle.setBackground(new Color(38,38,38));
		coordTitle.setDisabledTextColor((Color.WHITE));
		coordTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

		final JPanel coordPanel = new JPanel();
		BoxLayout boxLayout2 = new BoxLayout(coordPanel, BoxLayout.Y_AXIS);
		coordPanel.setLayout(boxLayout2);
		coordPanel.setPreferredSize(new Dimension(260,70));
		coordPanel.setOpaque(false);
		coordPanel.setBorder(new LineBorder(Color.BLACK,5));
		coordPanel.add(coordTitle);
		coordPanel.add(coordTxt);

		return coordPanel;
	}

	/**
	 * Initializes the coord txt.
	 *
	 * @return the coord txt
	 */
	public JTextArea getCoordTxt(){
		JTextArea coordTxt = new JTextArea(1,1);
		coordTxt.setLineWrap(true);
		coordTxt.setWrapStyleWord(true);
		coordTxt.setAlignmentX(SwingConstants.CENTER);
		coordTxt.setAlignmentY(SwingConstants.CENTER);
		coordTxt.setFont(new Font(coordTxt.getFont().getName(), coordTxt.getFont().getStyle(), 14));
		coordTxt.setBackground(new Color(0,0,0,255));
		coordTxt.setForeground(Color.WHITE);
		coordTxt.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		coordTxt.setEditable(false);
		coordTxt.setText("");

		return coordTxt;
	}
}
