/*
 * 
 */
package scenarioDev.track;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.esri.map.JMap;

// TODO: Auto-generated Javadoc
/**
 * The Class TrackFlexibility.
 */
public class TrackFlexibility {
	
	/** The map. */
	JMap map;
	
	/**
	 * Instantiates a new track flexibility.
	 *
	 * @param jMap the j map
	 */
	public TrackFlexibility(JMap jMap){
		this.map = jMap;
	}
	
	/**
	 * Gets the track.
	 *
	 * @return the track
	 */
	public JPanel getTrack(){
		JPanel dInputs = new JPanel();
		GridLayout gLayout = new GridLayout(3,1);
		gLayout.setVgap(3);
		dInputs.setLayout(gLayout);
		dInputs.setBackground(new Color(51,81,112));
		dInputs.setBorder(BorderFactory.createEmptyBorder());
		
		JTextField rnEnh = new JTextField();
		rnEnh.setOpaque(false);
		rnEnh.setDisabledTextColor(Color.WHITE);
		rnEnh.setPreferredSize(new Dimension(250,30));
		rnEnh.setFont(new Font(rnEnh.getFont().getName(),Font.BOLD,14));
		rnEnh.setText("Track Flexibility");
		rnEnh.setHorizontalAlignment(SwingConstants.CENTER);
		rnEnh.setBorder(BorderFactory.createMatteBorder(0, 0,0,0,Color.BLACK));
		rnEnh.setEditable(false);
		rnEnh.setEnabled(false);
		dInputs.add(rnEnh);
		
		JButton perHighlight = new JButton();
		perHighlight.setText("Reset Tracks");
		perHighlight.setHorizontalAlignment(SwingConstants.CENTER);
		perHighlight.setFont(new Font(perHighlight.getFont().getName(),Font.BOLD,12));
		
		JToggleButton perBlock = new JToggleButton();
		perBlock.setText("Interact with Tracks");
		perBlock.setHorizontalAlignment(SwingConstants.CENTER);
		perBlock.setFont(new Font(perBlock.getFont().getName(),Font.BOLD,12));
		dInputs.add(perBlock);
		dInputs.add(perHighlight);
		perBlock.addActionListener(new TrackOn(map));
		
		return dInputs;
	}
}
