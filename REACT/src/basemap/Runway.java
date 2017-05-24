/*
 * 
 */
package basemap;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.GraphicsLayer;

// TODO: Auto-generated Javadoc
/**
 * The Class Runway creates the runways for Kansas City International Airport: 01L-19R, 01R-19L, 09-27.
 */
public class Runway{
	
	/**
	 * Instantiates a new runway.
	 */
	public Runway(){};
	
	/**
	 * Creates the runways based on coordinates of the runway ends.
	 *
	 * @return the runway graphics layer 
	 */
	public GraphicsLayer createRunway(){

		Polyline O1L19R = new Polyline();
		O1L19R.startPath(-94.7293, 39.29334);
		O1L19R.lineTo(-94.7208, 39.32224);
		Map<String,Object> O1L19Rattr = new HashMap<String, Object>();
		O1L19Rattr.put("Start", "39.32224 N, 94.7208 W");
		O1L19Rattr.put("End", "39.29334 N, 94.7293 W");
		O1L19Rattr.put("Runway Name", "01L-19R");
		Graphic O1L19Rrunway = new Graphic(O1L19R,new SimpleLineSymbol(Color.BLACK,5),O1L19Rattr);

		Polyline O1R19L = new Polyline();
		O1R19L.startPath(-94.709,39.28146);
		O1R19L.lineTo(-94.7015, 39.30687);
		Map<String,Object> O1R19Lattr = new HashMap<String, Object>();
		O1R19Lattr.put("Start", "39.30687 N, 94.7015 W");
		O1R19Lattr.put("End", "39.28146 N, 94.709 W");
		O1R19Lattr.put("Runway Name", "01R-19L");
		Graphic O1R19Lrunway = new Graphic(O1R19L,new SimpleLineSymbol(Color.BLACK,5),O1R19Lattr);

		Polyline O927 = new Polyline();
		O927.startPath(-94.7266, 39.29086);
		O927.lineTo(-94.6932, 39.28808);
		Map<String,Object> O927attr = new HashMap<String, Object>();
		O927attr.put("Start", "39.28808 N, 94.6932 W");
		O927attr.put("End", "39.29086 N, 94.7266 W");
		O927attr.put("Runway Name", "09-27");
		Graphic O927runway = new Graphic(O927,new SimpleLineSymbol(Color.BLACK,5),O927attr);

		GraphicsLayer runway = new GraphicsLayer();
		runway.addGraphic(O1L19Rrunway);
		runway.addGraphic(O1R19Lrunway);
		runway.addGraphic(O927runway);

		return runway;
	}
}
