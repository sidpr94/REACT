package action;

import java.awt.event.MouseEvent;

import javax.swing.JTextArea;

import com.esri.core.geometry.CoordinateConversion;
import com.esri.map.JMap;
import com.esri.map.MapOverlay;

// TODO: Auto-generated Javadoc
/**
 * Shows the coordinate on the map by using the current mouse position.
 * @author Sidharth Prem
 * @see GUI.mapCoordPanel
 */
public class MouseMoveOverlay extends MapOverlay {
	
	/** Main map JMap containing airport information. */
	JMap mainMap;
	
	/** Text Area that displays the coordinate information. */
	JTextArea coordTxt;
	
	/**
	 * Instantiates a new mouse move overlay.
	 *
	 * @param map the main map
	 * @param txt the coordinate information
	 */
	public MouseMoveOverlay(JMap map, JTextArea txt){
		this.mainMap = map;
		this.coordTxt = txt;
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.esri.map.MapOverlay#onMouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void onMouseMoved(MouseEvent arg0) {
		try {
			if (!mainMap.isReady()) {
				return;
			}
			java.awt.Point screenPoint = arg0.getPoint();
			com.esri.core.geometry.Point mapPoint = mainMap.toMapPoint(screenPoint.x, screenPoint.y);
			String decimalDegrees = "Decimal Degrees: " 
					+ CoordinateConversion.pointToDecimalDegrees(mapPoint, mainMap.getSpatialReference(), 2);
			coordTxt.setText(decimalDegrees);
		} finally {
			super.onMouseMoved(arg0);
		}
	}
}
