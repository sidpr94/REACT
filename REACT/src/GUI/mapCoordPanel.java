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

public class mapCoordPanel {
	int width;
	int height;
	public mapCoordPanel(){}
	public mapCoordPanel(int w, int h){
		this.width = w;
		this.height = h;
	}

	public JPanel getCoordPanel(JTextArea coordTxt){
		JTextField coordTitle = new JTextField();
		coordTitle.setText("Map Coordinates");
		coordTitle.setEditable(false);
		coordTitle.setEnabled(false);
		coordTitle.setHorizontalAlignment(SwingConstants.CENTER);
		coordTitle.setFont(new Font(coordTitle.getFont().getName(),Font.BOLD,16));
		//coordTitle.setPreferredSize(new Dimension(170,30));
		coordTitle.setBackground(new Color(38,38,38));
		coordTitle.setDisabledTextColor((Color.WHITE));
		coordTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

		final JPanel coordPanel = new JPanel();
		BoxLayout boxLayout2 = new BoxLayout(coordPanel, BoxLayout.Y_AXIS);
		coordPanel.setLayout(boxLayout2);
		coordPanel.setPreferredSize(new Dimension(260,70));
		//coordPanel.setLocation(240,height-coordPanel.getHeight()-60);
		coordPanel.setOpaque(false);
		coordPanel.setBorder(new LineBorder(Color.BLACK,5));
		coordPanel.add(coordTitle);
		coordPanel.add(coordTxt);

		return coordPanel;
	}

	public JTextArea getCoordTxt(){
		JTextArea coordTxt = new JTextArea(1,1);
		coordTxt.setLineWrap(true);
		coordTxt.setWrapStyleWord(true);
		coordTxt.setAlignmentX(SwingConstants.CENTER);
		coordTxt.setAlignmentY(SwingConstants.CENTER);
		coordTxt.setFont(new Font(coordTxt.getFont().getName(), coordTxt.getFont().getStyle(), 14));
		//coordTxt.setPreferredSize(new Dimension(170,30));
		coordTxt.setBackground(new Color(0,0,0,255));
		coordTxt.setForeground(Color.WHITE);
		coordTxt.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		coordTxt.setEditable(false);
		coordTxt.setText("");

		return coordTxt;
	}
}
