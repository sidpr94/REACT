package basemap;
//This class creates the population map from the shape file
import java.awt.Color;
import java.io.FileNotFoundException;
import java.net.URL;

import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.renderer.ClassBreaksRenderer;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.FeatureLayer;

public class PopMap {
	private ShapefileFeatureTable POP10shapefile;
	private FeatureLayer POP10Layer;
	public PopMap(){};
	public FeatureLayer createPopMap(){
		try {
			//captures the relative path for the shape file within Files
			URL url = PopMap.class.getClassLoader().getResource("Files/PopMap.shp");
			//creates the feature table
			POP10shapefile = new ShapefileFeatureTable(url.getPath().replace("/", "\\").substring(1));
			//creates the layer
			POP10Layer = new FeatureLayer(POP10shapefile);
			//adds color to the layer
			ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
			cbrenderer.addBreak(0,14,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(15,47,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(48,108,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(109,235,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(236,488,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(489,1130,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			cbrenderer.addBreak(1131,2362,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
			POP10Layer.setRenderer(cbrenderer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch 
			e.printStackTrace();
		}
		return POP10Layer;
		}
}
