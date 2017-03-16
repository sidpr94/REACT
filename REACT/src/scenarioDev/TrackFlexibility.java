package scenarioDev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class TrackFlexibility {
	public TrackFlexibility(){}
	
	public JPanel getTrack(){
		JPanel dInputs = new JPanel();
		GridLayout gLayout = new GridLayout(3,1);
		gLayout.setVgap(3);
		dInputs.setLayout(gLayout);
		dInputs.setBackground(new Color(0,0,0,100));
		dInputs.setBorder(BorderFactory.createEmptyBorder());
		
		JTextField rnEnh = new JTextField();
		rnEnh.setBackground(new Color(0,0,0,100));
		rnEnh.setPreferredSize(new Dimension(250,30));
		rnEnh.setFont(new Font(rnEnh.getFont().getName(),Font.BOLD,12));
		rnEnh.setText("Track Flexibility");
		rnEnh.setHorizontalAlignment(SwingConstants.CENTER);
		rnEnh.setBorder(BorderFactory.createMatteBorder(0, 0,3,0,Color.BLACK));
		rnEnh.setEditable(false);
		rnEnh.setEnabled(false);
		dInputs.add(rnEnh);
		
		JToggleButton perBlock = new JToggleButton();
		perBlock.setText("Interact with Tracks");
		perBlock.setHorizontalAlignment(SwingConstants.CENTER);
		perBlock.setFont(new Font(perBlock.getFont().getName(),Font.BOLD,12));
		dInputs.add(perBlock);
		
		dInputs.add(Box.createVerticalGlue());
		
		return dInputs;
	}
}
