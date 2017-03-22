package basemap;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.map.Feature;
import com.esri.core.table.TableException;

import basemap.popmap.EditableFeatureTable;

public class TryThings {
	ShapefileFeatureTable shape;
	List<Feature> list = new ArrayList<Feature>();
	List<Feature> l = new ArrayList<Feature>();
	public static void main(String[] args) throws TableException, FileNotFoundException {
		TryThings t = new TryThings();
		System.out.println(t.getTable());
		System.out.println(t.getFetaTable());
	}
	public ShapefileFeatureTable getTable() throws TableException, FileNotFoundException{
		URL url = PopMap.class.getClassLoader().getResource("Files/MCI_Runways.shp");
		//creates the feature table
		shape = new ShapefileFeatureTable(url.getPath().replace("/", "\\").substring(1));
		return shape;
	}
	public EditableFeatureTable getFetaTable() throws FileNotFoundException, TableException{
		EditableFeatureTable feat = new EditableFeatureTable(getTable());
		return feat;
	}
	
}
