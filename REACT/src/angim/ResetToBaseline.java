/*
 * 
 */
package angim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;

import com.esri.map.JMap;

// TODO: Auto-generated Javadoc
/**
 * Actionlistner that allows one to reset to baseline scenario conditions:
 * 2015 Forecast & Population, Original Tracks, Original Population, Original Contours 
 * @author Sidharth Prem
 * @see content.DashBoard
 */
public class ResetToBaseline implements ActionListener {
	
	/** The main map containing airport information. */
	JMap mainMap;
	
	/** The ComboBox containing operation forecasting information. */
	JComboBox<String> op;
	
	/**
	 * Instantiates a new reset to baseline.
	 *
	 * @param op the operational forecasting ComboBox
	 * @param map the main map
	 */
	public ResetToBaseline(JComboBox<String> op, JMap map){
		this.mainMap = map;
		this.op = op;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	/**
	 * Resets contours, fleet technology, and tracks.
	 */
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		op.getModel().setSelectedItem(op.getModel().getElementAt(0));
		try {
			UpdateContour reset = new UpdateContour(mainMap, op, "reset");
			reset.updateContourGraphic();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ADD TRACKS HERE 
		
	}

}
