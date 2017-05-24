/*
 * 
 */
package basemap;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.MultiPoint;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Feature;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.Symbol;

// TODO: Auto-generated Javadoc
/**
 * A parser that reads data in <a href="http://geojson.org">GeoJSON</a> format, and returns
 * a collection of {@link Feature} or {@link Geometry}. 
 * 
 * <p>
 * To parse a 
 * <a href="http://geojson.org/geojson-spec.html#feature-collection-objects">FeatureCollection</a> 
 *  use {@link #parseFeatures(String)}; to parse a 
 * <a href="http://geojson.org/geojson-spec.html#feature-collection-objects">GeometryCollection</a>
 *  use {@link #parseGeometries(String)}.
 *
 * <p>
 * Limitations:
 * <ul>
 * <li> No support for the GeoJSON coordinate reference system. All input geometries are
 * assumed to be in CRS84.
 * </ul>
 * @since 10.2.4
 */
public final class GeoJsonParser {

	/** The symbol. */
	// symbology to be used for all the features
	private Symbol symbol = null;

	/** The mapper. */
	// dependency on the Jackson parser library to parse JSON 
	private final ObjectMapper mapper = new ObjectMapper();

	/** The in SR. */
	// geometries in GeoJSON are assumed to be in CRS84 (Esri Wkid = 4326)
	private final SpatialReference inSR = SpatialReference.create(4326);

	/** The out SR. */
	// output CRS can be configured to be different
	private SpatialReference outSR = null;

	/** The Constant FIELD_COORDINATES. */
	// field names defined in the GeoJson spec
	private final static String FIELD_COORDINATES = "coordinates";
	
	/** The Constant FIELD_FEATURE. */
	private final static String FIELD_FEATURE = "Feature";
	
	/** The Constant FIELD_FEATURES. */
	private final static String FIELD_FEATURES = "features";
	
	/** The Constant FIELD_FEATURE_COLLECTION. */
	private final static String FIELD_FEATURE_COLLECTION = "FeatureCollection";
	
	/** The Constant FIELD_GEOMETRY. */
	private final static String FIELD_GEOMETRY = "geometry";
	
	/** The Constant FIELD_GEOMETRIES. */
	private final static String FIELD_GEOMETRIES = "geometries";
	
	/** The Constant FIELD_GEOMETRY_COLLECTION. */
	private final static String FIELD_GEOMETRY_COLLECTION = "GeometryCollection";
	
	/** The Constant FIELD_PROPERTIES. */
	private final static String FIELD_PROPERTIES = "properties";
	
	/** The Constant FIELD_TYPE. */
	private final static String FIELD_TYPE = "type";

	/**
	 * The Enum GeometryType.
	 */
	private enum GeometryType {
		
		/** The point. */
		POINT("Point"),
		
		/** The multi point. */
		MULTI_POINT("MultiPoint"),
		
		/** The line string. */
		LINE_STRING("LineString"),
		
		/** The multi line string. */
		MULTI_LINE_STRING("MultiLineString"),
		
		/** The polygon. */
		POLYGON("Polygon"),
		
		/** The multi polygon. */
		MULTI_POLYGON("MultiPolygon");

		/** The val. */
		private final String val;

		/**
		 * Instantiates a new geometry type.
		 *
		 * @param val the val
		 */
		GeometryType(String val) {
			this.val = val;  
		}

		/**
		 * From string.
		 *
		 * @param val the val
		 * @return the geometry type
		 */
		public static GeometryType fromString(String val) {
			for (GeometryType type : GeometryType.values()) {
				if (type.val.equals(val)) {
					return type;
				}
			}
			return null;
		}
	}

	// ------------------------------------------------------------------------
	// Public methods
	// ------------------------------------------------------------------------

	/**
	 * Sets the symbol.
	 *
	 * @param symbol the symbol
	 * @return the geo json parser
	 */
	public GeoJsonParser setSymbol(Symbol symbol) {
		this.symbol = symbol;
		return this;
	}

	/**
	 * Sets the out spatial reference.
	 *
	 * @param outSR the out SR
	 * @return the geo json parser
	 */
	public GeoJsonParser setOutSpatialReference(SpatialReference outSR) {
		this.outSR = outSR;
		return this;
	}

	/**
	 * Parses the features.
	 *
	 * @param file the file
	 * @return the list
	 */
	public List<Feature> parseFeatures(File file) {
		try {
			JsonParser parser = new JsonFactory().createJsonParser(file);
			return parseFeatures(parser);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Parses the features.
	 *
	 * @param str the str
	 * @return the list
	 */
	public List<Feature> parseFeatures(String str) {
		try {
			JsonParser parser = new JsonFactory().createJsonParser(str);
			return parseFeatures(parser);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Parses the geometries.
	 *
	 * @param file the file
	 * @return the list
	 */
	public List<Geometry> parseGeometries(File file) {
		try {
			JsonParser parser = new JsonFactory().createJsonParser(file);
			return parseGeometries(parser);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Parses the geometries.
	 *
	 * @param str the str
	 * @return the list
	 */
	public List<Geometry> parseGeometries(String str) {
		try {
			JsonParser parser = new JsonFactory().createJsonParser(str);
			return parseGeometries(parser);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	// ------------------------------------------------------------------------
	// Private methods
	// ------------------------------------------------------------------------

	/**
	 * Parses the features.
	 *
	 * @param parser the parser
	 * @return the list
	 */
	private List<Feature> parseFeatures(JsonParser parser) {
		try {
			JsonNode node = mapper.readTree(parser);
			String type = node.path(FIELD_TYPE).getTextValue();
			if (type.equals(FIELD_FEATURE_COLLECTION)) {
				ArrayNode jsonFeatures = (ArrayNode) node.path(FIELD_FEATURES);
				return parseFeatures(jsonFeatures);
			} else if (type.equals(FIELD_GEOMETRY_COLLECTION)) {
				ArrayNode jsonFeatures = (ArrayNode) node.path(FIELD_GEOMETRIES);
				List<Geometry> geometries = parseGeometries(jsonFeatures);
				List<Feature> features = new LinkedList<Feature>();
				for (Geometry g : geometries) {
					features.add(new Graphic(g, symbol));  
				}
				return features;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return Collections.emptyList();
	}

	/**
	 * Parses the features.
	 *
	 * @param jsonFeatures the json features
	 * @return the list
	 */
	private List<Feature> parseFeatures(ArrayNode jsonFeatures) {
		List<Feature> features = new LinkedList<Feature>();
		for (JsonNode jsonFeature : jsonFeatures) {
			String type = jsonFeature.path(FIELD_TYPE).getTextValue();
			if (!FIELD_FEATURE.equals(type)) {
				continue;
			}
			Geometry g = parseGeometry(jsonFeature.path(FIELD_GEOMETRY));
			if (outSR != null && outSR.getID() != 4326) {
				g = GeometryEngine.project(g, inSR, outSR);
			}
			Map<String, Object> attributes = parseProperties(jsonFeature.path(FIELD_PROPERTIES));
			Feature f = new Graphic(g, symbol, attributes);
			features.add(f);
		} 
		return features; 
	}

	/**
	 * Parses the geometries.
	 *
	 * @param parser the parser
	 * @return the list
	 */
	private List<Geometry> parseGeometries(JsonParser parser) {
		try {
			JsonNode node = mapper.readTree(parser);
			String type = node.path(FIELD_TYPE).getTextValue();
			if (type.equals(FIELD_GEOMETRY_COLLECTION)) {
				ArrayNode jsonFeatures = (ArrayNode) node.path(FIELD_GEOMETRIES);
				return parseGeometries(jsonFeatures);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return Collections.emptyList();
	}

	/**
	 * Parses the geometries.
	 *
	 * @param jsonGeometries the json geometries
	 * @return the list
	 */
	private List<Geometry> parseGeometries(ArrayNode jsonGeometries) {
		List<Geometry> geometries = new LinkedList<Geometry>();
		for (JsonNode jsonGeometry : jsonGeometries) {
			Geometry g = parseGeometry(jsonGeometry);
			if (outSR != null && outSR.getID() != 4326) {
				g = GeometryEngine.project(g, inSR, outSR);
			}
			geometries.add(g);
		} 
		return geometries; 
	}

	/**
	 * Parses the properties.
	 *
	 * @param node the node
	 * @return the map
	 */
	private Map<String, Object> parseProperties(JsonNode node) {
		Map<String, Object> properties = new HashMap<String, Object>();
		Iterator<Map.Entry<String, JsonNode>> propertyInterator = node.getFields(); 
		while (propertyInterator.hasNext()) {
			Map.Entry<String, JsonNode> property = propertyInterator.next();
			JsonNode jsonValue = property.getValue();
			if (jsonValue.isInt()) {
				properties.put(property.getKey(), property.getValue().asInt());
			} else if (jsonValue.isDouble()) {
				properties.put(property.getKey(), property.getValue().asDouble());
			} else if (jsonValue.isTextual()) {
				properties.put(property.getKey(), property.getValue().asText());
			}
		}
		return properties;
	}

	/**
	 * { "type": "Point", "coordinates": [100.0, 0.0] }
	 *
	 * @param node the node
	 * @return a geometry.
	 */
	private Geometry parseGeometry(JsonNode node) {
		GeometryType type = GeometryType.fromString(node.path(FIELD_TYPE).getTextValue());
		return parseCoordinates(type, node.path(FIELD_COORDINATES));
	}

	/**
	 * Parses the coordinates.
	 *
	 * @param type the type
	 * @param node the node
	 * @return the geometry
	 */
	private Geometry parseCoordinates(GeometryType type, JsonNode node) {
		Geometry g = null;
		switch (type) {
		default:
		case POINT:
			g = parsePointCoordinates(node);    
			break;
		case MULTI_POINT:
			g = parseMultiPointCoordinates(node);
			break;
		case LINE_STRING:
			g = parseLineStringCoordinates(node);
			break;
		case MULTI_LINE_STRING:
			g = parseMultiLineStringCoordinates(node);
			break;
		case POLYGON:
			g = parsePolygonCoordinates(node);
			break;
		case MULTI_POLYGON:
			g = parseMultiPolygonCoordinates(node);
			break;
		}
		return g;
	}

	/**
	 * Parses a point
	 * Example:
	 * [101.0, 0.0].
	 *
	 * @param node the node
	 * @return a point.
	 */
	private Point parsePointCoordinates(JsonNode node) {
		Point p = new Point();
		p.setXY(node.get(0).asDouble(), node.get(1).asDouble());
		if (node.size() == 3) {
			p.setZ(node.get(2).asDouble());
		}
		return p;
	}

	/**
	 * Parses a multipoint
	 * Example:
	 * [ [100.0, 0.0], [101.0, 1.0] ].
	 *
	 * @param node the node
	 * @return a multipoint.
	 */
	private MultiPoint parseMultiPointCoordinates(JsonNode node) {
		MultiPoint p = new MultiPoint();
		ArrayNode jsonPoints = (ArrayNode) node;
		for (JsonNode jsonPoint : jsonPoints) {
			Point point = parsePointCoordinates(jsonPoint);
			p.add(point);
		}
		return p;
	}

	/**
	 * Parses a line string
	 * Example:
	 * [ [100.0, 0.0], [101.0, 1.0] ].
	 *
	 * @param node the node
	 * @return a polyline.
	 */
	private Polyline parseLineStringCoordinates(JsonNode node) {
		Polyline g = new Polyline();
		boolean first = true;
		ArrayNode points = (ArrayNode) node;
		for (JsonNode point : points) {
			Point p = parsePointCoordinates(point);
			if (first) {
				g.startPath(p);
				first = false;
			} else {
				g.lineTo(p);
			}  
		}
		return g;
	}

	/**
	 * Parses a multi line string
	 * Example:
	 * [
	 *   [ [100.0, 0.0], [101.0, 1.0] ],
	 *   [ [102.0, 2.0], [103.0, 3.0] ]
	 * ]
	 *
	 * @param node the node
	 * @return a polyline
	 */
	private Polyline parseMultiLineStringCoordinates(JsonNode node) {
		Polyline g = new Polyline();
		ArrayNode jsonLines = (ArrayNode) node;
		for (JsonNode jsonLine : jsonLines) {
			Polyline line = parseLineStringCoordinates(jsonLine);
			g.add(line, false);
		}
		return g;
	}

	/**
	 * Example:
	 * [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0] ]
	 *
	 * @param node the node
	 * @return a polygon
	 */
	private Polygon parseSimplePolygonCoordinates(JsonNode node) {
		Polygon g = new Polygon();
		boolean first = true;
		ArrayNode points = (ArrayNode) node;
		for (JsonNode point : points) {
			Point p = parsePointCoordinates(point);
			if (first) {
				g.startPath(p);
				first = false;
			} else {
				g.lineTo(p);
			}  
		}
		g.closeAllPaths();
		return g;
	}

	/**
	 * Parses a polygon string
	 * Example:
	 * without holes:
	 * [
	 *   [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0] ]
	 * ]
	 * 
	 * with holes:
	 * [
	 *   [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0] ],
	 *   [ [100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2] ]
	 * ]
	 *
	 * @param node the node
	 * @return a polygon
	 */
	private Polygon parsePolygonCoordinates(JsonNode node) {
		Polygon g = new Polygon();
		ArrayNode jsonPolygons = (ArrayNode) node;
		for (JsonNode jsonPolygon : jsonPolygons) {
			Polygon simplePolygon = parseSimplePolygonCoordinates(jsonPolygon);
			g.add(simplePolygon, false);
		}
		return g;
	}

	/**
	 * Parses a multi polygon string
	 * Example:
	 *  [
	 *   [[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]],
	 *   [[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]],
	 *    [[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]]
	 *  ]
	 *
	 * @param node the node
	 * @return a polygon
	 */ 
	private Polygon parseMultiPolygonCoordinates(JsonNode node) {
		Polygon g = new Polygon();
		ArrayNode jsonPolygons = (ArrayNode) node;
		for (JsonNode jsonPolygon : jsonPolygons) {
			Polygon simplePolygon = parsePolygonCoordinates(jsonPolygon);
			g.add(simplePolygon, false);
		}
		return g;
	}
}
