package basemap;
//This class creates the population map from the shape file
import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.util.List;

import com.esri.core.map.Feature;
import com.esri.core.map.Graphic;
import com.esri.core.renderer.ClassBreaksRenderer;
import com.esri.core.symbol.CompositeSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.GraphicsLayer;

public class PopMap {
	public PopMap(){};
	public void createPopMap(GraphicsLayer layer){
		try{
			GeoJsonParser geoJsonParser = new GeoJsonParser();
			URL url = this.getClass().getClassLoader().getResource("Files/Geojson/BlocksAirportBoundary.geojson");
			File PopMap = new File(url.getPath());
			List<Feature> features = geoJsonParser.parseFeatures(PopMap);
			for(Feature f : features){
				layer.addGraphic(new Graphic(f.getGeometry(),null,f.getAttributes()));
			}
			URL url1 = this.getClass().getClassLoader().getResource("Files/Geojson/AirportBoundary.geojson");
			File AirportBoundary = new File(url1.getPath());
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
		}catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
