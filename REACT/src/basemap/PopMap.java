/*
 * 
 */
package basemap;
//This class creates the population map from the shape file
import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.esri.core.map.Feature;
import com.esri.core.map.Graphic;
import com.esri.core.renderer.ClassBreaksRenderer;
import com.esri.core.symbol.CompositeSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.GraphicsLayer;

// TODO: Auto-generated Javadoc
/**
 * Creates the editable area for population map based on 2010 Census Population Data.
 * Currently there are too many blocks to allow for every block to be editable.
 * It might be solvable by breaking up the population map into a set of smaller population maps
 * A computer memory issue.
 * @author Sidharth Prem 
 * @see basemap.CreateMainMap
 */
public class PopMap {
	
	/** The population blocks saved as a key-value pair of the unique ID and graphic. */
	public static HashMap<Integer,Graphic> graphics = new HashMap<Integer,Graphic>();
	
	/**
	 * Instantiates a new pop map.
	 */
	public PopMap(){};
	
	/**
	 * Creates the population by taking a GeoJSON file of the population census blocks and parsing through them conveting them into graphics.
	 *
	 * @param layer the Editable Population near the airport GraphicLayer
	 */
	public void createPopMap(GraphicsLayer layer){
		try{
			GeoJsonParser geoJsonParser = new GeoJsonParser();
			File PopMap = new File("Files/Geojson/BlocksAirportBoundary.geojson");
			List<Feature> features = geoJsonParser.parseFeatures(PopMap);
			for(Feature f : features){
				layer.addGraphic(new Graphic(f.getGeometry(),null,f.getAttributes()));
			}
			File AirportBoundary = new File("Files/Geojson/AirportBoundary.geojson");
			List<Feature> features1 = geoJsonParser.parseFeatures(AirportBoundary);
			for(Feature f : features1){
				CompositeSymbol symbol = new CompositeSymbol();
				symbol.add(new SimpleFillSymbol(new Color(255, 0, 0,50)));
				symbol.add(new SimpleLineSymbol(Color.BLACK, 3));
				layer.addGraphic(new Graphic(f.getGeometry(),symbol,f.getAttributes()));
			}
			ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
			cbrenderer.addBreak(0,14,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(15,47,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(48,108,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(109,235,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(236,488,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(489,1130,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(1131,2362,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			layer.setRenderer(cbrenderer);
			layer.setName("Population Near Airport");
			int[] ids = layer.getGraphicIDs();
			for(int i = 0; i < ids.length; i++){
				graphics.put(ids[i], layer.getGraphic(ids[i]));
			}
		}catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
