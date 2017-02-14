package GUI;

import java.awt.event.MouseEvent;

import javax.swing.JTextArea;

import com.esri.core.geometry.CoordinateConversion;
import com.esri.map.JMap;
import com.esri.map.MapOverlay;

public class MouseMoveOverlay extends MapOverlay {
	JMap jMap;
	JTextArea coordTxt;
	public MouseMoveOverlay(JMap map, JTextArea txt){
		this.jMap = map;
		this.coordTxt = txt;
	}
    private static final long serialVersionUID = 1L;

    public void onMouseMoved(MouseEvent arg0) {
      try {
        if (!jMap.isReady()) {
          return;
        }
        java.awt.Point screenPoint = arg0.getPoint();
        com.esri.core.geometry.Point mapPoint = jMap.toMapPoint(screenPoint.x, screenPoint.y);
        String decimalDegrees = "Decimal Degrees: " 
            + CoordinateConversion.pointToDecimalDegrees(mapPoint, jMap.getSpatialReference(), 2);
        coordTxt.setText(decimalDegrees);

      } finally {
        super.onMouseMoved(arg0);
      }
    }
  }
