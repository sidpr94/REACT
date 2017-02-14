package basemap;
//This class creates the NoiseContour map from the shapefile within Files
import java.awt.Color;
import java.io.FileNotFoundException;
import java.net.URL;

import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.renderer.UniqueValueInfo;
import com.esri.core.renderer.UniqueValueRenderer;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleFillSymbol.Style;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.FeatureLayer;

public class NoiseContour {
	private ShapefileFeatureTable Contourshapefile;
	private FeatureLayer ContourLayer;
	public NoiseContour(){};
	public FeatureLayer createNoiseContour(){
		try {
			//captures the relative path for the shape file
			URL url = NoiseContour.class.getClassLoader().getResource("Files/Noise.shp");
			//creates the feature table from the shape file
			Contourshapefile = new ShapefileFeatureTable(url.getPath().replace("/", "\\").substring(1));
			//creates the layer using the feature table
			ContourLayer = new FeatureLayer(Contourshapefile);
			//adds a render to color the features properly
			UniqueValueRenderer srenderer = new UniqueValueRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,3),Style.NULL),"Contour");
			Color DNL55 = new Color(56,168,0);
			Color DNL60 = new Color(0,92,230);
			Color DNL65 = new Color(255,0,0);
			srenderer.addValue(new UniqueValueInfo(new Object[] {55},new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(DNL55,5),Style.NULL)));
			srenderer.addValue(new UniqueValueInfo(new Object[] {60},new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(DNL60,5),Style.NULL)));
			srenderer.addValue(new UniqueValueInfo(new Object[] {65},new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(DNL65,5),Style.NULL)));
			ContourLayer.setRenderer(srenderer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch 
			e.printStackTrace();
		}
		return ContourLayer;
		}
}
