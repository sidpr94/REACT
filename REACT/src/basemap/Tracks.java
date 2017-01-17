package basemap;

import java.awt.Color;
import java.io.FileNotFoundException;
//import java.net.URL;

import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.renderer.UniqueValueInfo;
import com.esri.core.renderer.UniqueValueRenderer;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.FeatureLayer;

public class Tracks {
	private ShapefileFeatureTable Trackshapefile;
	private FeatureLayer TrackLayer;
	public Tracks(){};
	public FeatureLayer createTracks(){
		try {
			//URL url = Tracks.class.getClassLoader().getResource("/Files/Tracks for AAD_KMCI 260.shp");
			Trackshapefile = new ShapefileFeatureTable("C:\\ARCGIS\\TracksforAAD_KMCI260_1.shp");
			TrackLayer = new FeatureLayer(Trackshapefile);
			UniqueValueRenderer uvrenderer = new UniqueValueRenderer(new SimpleLineSymbol(Color.BLACK,2), "Operation_");
			uvrenderer.addValue(new UniqueValueInfo(new Object[] {"Approach"}, new SimpleLineSymbol(Color.BLUE,2)));
			uvrenderer.addValue(new UniqueValueInfo(new Object[] {"Departure"}, new SimpleLineSymbol(Color.ORANGE,2)));
			TrackLayer.setRenderer(uvrenderer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch 
			e.printStackTrace();
		}
		return TrackLayer;
		}
}
