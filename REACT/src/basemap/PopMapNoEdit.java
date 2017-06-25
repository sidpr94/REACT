/*
 * 
 */
package basemap;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;

import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.renderer.ClassBreaksRenderer;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.FeatureLayer;

// TODO: Auto-generated Javadoc
/**
 * The population blocks outside of the airport proximity that is not editable for land zoning.
 * This was done to conserve computer memory.
 * @author Sidharth Prem
 * @see basemap.CreateMainMap
 */
public class PopMapNoEdit {
	
	/**
	 * Instantiates a new pop map no edit.
	 */
	public PopMapNoEdit(){}

	/**
	 * Creates the population blocks as a shape file.
	 * MCI Specific Need to Fix
	 * @return the shapefile feature layer for noneditable population block.
	 * @throws FileNotFoundException the file not found exception
	 */
	public FeatureLayer createPop() throws FileNotFoundException{
		File file = new File("Files/Shapefiles/PopMapNoEdit.shp");
		ShapefileFeatureTable pop = new ShapefileFeatureTable(file.getAbsolutePath());
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
