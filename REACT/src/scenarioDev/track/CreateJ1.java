package scenarioDev.track;

import java.io.FileNotFoundException;
import java.net.URL;

import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.map.FeatureLayer;

public class CreateJ1 {
	public CreateJ1 (){}
	public FeatureLayer createJ1flex() throws FileNotFoundException{
		URL url = this.getClass().getClassLoader().getResource("Files/J1flex.shp");
		ShapefileFeatureTable J1 = new ShapefileFeatureTable(url.getPath().replace("/", "\\").substring(1));
		FeatureLayer J1Layer = new FeatureLayer(J1);
		return J1Layer;
	}
	public FeatureLayer createJ1withTracks() throws FileNotFoundException{
		URL url = this.getClass().getClassLoader().getResource("Files/J1v1withTracks.shp");
		ShapefileFeatureTable J1 = new ShapefileFeatureTable(url.getPath().replace("/", "\\").substring(1));
		FeatureLayer J1Layer = new FeatureLayer(J1);
		return J1Layer;
	}
}
