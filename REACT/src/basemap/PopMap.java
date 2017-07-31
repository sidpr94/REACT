/*
 * 
 */
package basemap;
//This class creates the population map from the shape file
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;

import com.esri.core.geometry.Geometry;
import com.esri.core.map.Graphic;
import com.esri.core.renderer.ClassBreaksRenderer;
import com.esri.core.symbol.CompositeSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.GraphicsLayer;

import angim.ForecastScenarios;

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
	
	private final static String FIELD_GEOMETRY = "geometry";
	
	private final static String FIELD_FEATURES = "features";
	
	private final static String FIELD_PROPERTIES = "properties";
	
	/** The population blocks saved as a key-value pair of the unique ID and graphic. */
	public static HashMap<Integer,Graphic> graphics = new HashMap<Integer,Graphic>();
	
	/**
	 * Instantiates a new pop map.
	 */
	public PopMap(){};
	
	/**
	 * Creates the population by taking a GeoJSON file of the population census blocks and parsing through them conveting them into graphics.
	 * MCI Specific Need to Fix
	 * @param layer the Editable Population near the airport GraphicLayer
	 * @throws IOException 
	 */
	public void createPopMap(GraphicsLayer layer) throws IOException{
		ForecastScenarios scenario = new ForecastScenarios();
		String airportName = scenario.getAirportName();
		String poppath = "Files/Geojson/"+airportName;
		try{
			List<Graphic> graphics = parseFeatures(poppath+"/PopMapEdit_"+airportName+".geojson");
			for(Graphic f : graphics){
				layer.addGraphic(f);
			}
			List<Graphic> graphics1 = parseFeatures(poppath+"/AirportBoundary_"+airportName+".geojson");
			for(Graphic f : graphics1){
				CompositeSymbol symbol = new CompositeSymbol();
				symbol.add(new SimpleFillSymbol(new Color(255, 0, 0,50)));
				symbol.add(new SimpleLineSymbol(Color.BLACK, 3));
				layer.addGraphic(new Graphic(f.getGeometry(),symbol,f.getAttributes()));
			}
			/*
			ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
			cbrenderer.addBreak(0,919,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(920,1310,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(1311,1769,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(1770,2343,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(2344,3204,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(3205,4838,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(4839,9658,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			*/
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
				PopMap.graphics.put(ids[i], layer.getGraphic(ids[i]));
			}
		}catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public List<Graphic> parseFeatures(String path) throws JsonParseException, IOException{
		List<Graphic> graphics = new ArrayList<Graphic>();
		JsonFactory f = new MappingJsonFactory();
		JsonParser jp = f.createJsonParser(new File(path));
		GeoJsonParser parse = new GeoJsonParser();
		JsonToken current;

		current = jp.nextToken();
		if (current != JsonToken.START_OBJECT) {
			System.out.println("Error: root should be object: quiting.");
			return graphics;
		}
		
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			String fieldName = jp.getCurrentName();
			// move from field name to field value
			current = jp.nextToken();
			if (fieldName.equals(FIELD_FEATURES)) {
				if (current == JsonToken.START_ARRAY) {
					// For each of the records in the array
					while (jp.nextToken() != JsonToken.END_ARRAY) {
						// read the record into a tree model,
						// this moves the parsing position to the end of it
						JsonNode node = jp.readValueAsTree();
						JsonNode jsonGeometries = node.path(FIELD_GEOMETRY);
						Geometry g = parse.parseGeometry(jsonGeometries);
						Map<String, Object> attributes = parse.parseProperties(node.path(FIELD_PROPERTIES));
						graphics.add(new Graphic(g,null,attributes));
						// And now we have random access to everything in the object
					}
				} else {
					jp.skipChildren();
				}
			} else {
				jp.skipChildren();
			}
		}
		return graphics;
	}
}
