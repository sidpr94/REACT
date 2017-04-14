package basemap;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.net.URL;

import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.renderer.ClassBreaksRenderer;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.FeatureLayer;

public class PopMapNoEdit {
	public PopMapNoEdit(){}

	public FeatureLayer createPop() throws FileNotFoundException{
		URL url = this.getClass().getClassLoader().getResource("Files/Shapefiles/PopMapNoEdit.shp");
		ShapefileFeatureTable pop = new ShapefileFeatureTable(url.getPath().replace("/", "\\").substring(1));
		FeatureLayer layer = new FeatureLayer(pop);
		ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
		cbrenderer.addBreak(0,14,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(15,47,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(48,108,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(109,235,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(236,488,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(489,1130,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(1131,2362,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		layer.setRenderer(cbrenderer);
		layer.setName("Population Beyond");
		return layer;
	}
}
