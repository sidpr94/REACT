package basemap;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.net.URL;

import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.renderer.SimpleRenderer;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.FeatureLayer;

public class Runway{
	private ShapefileFeatureTable Runwayshapefile;
	private FeatureLayer RunwayLayer;
	public Runway(){};
	public FeatureLayer createRunway(){
		try {
			URL url = Runway.class.getClassLoader().getResource("Files/MCI_Runways.shp");
			Runwayshapefile = new ShapefileFeatureTable(url.getPath().replace("/", "\\").substring(1));
			RunwayLayer = new FeatureLayer(Runwayshapefile);
			SimpleRenderer srenderer = new SimpleRenderer(new SimpleLineSymbol(Color.BLACK,5));
			RunwayLayer.setRenderer(srenderer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch 
			e.printStackTrace();
		}
		return RunwayLayer;
		}
}
