/*
 * 
 */
package basemap;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.renderer.ClassBreaksRenderer;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.FeatureLayer;

import angim.ForecastScenarios;

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
	 * @throws IOException 
	 */
	public FeatureLayer createPop() throws IOException{
		ForecastScenarios scenario = new ForecastScenarios();
		String airportName = scenario.getAirportName();
		File file = new File("Files/Shapefiles/"+airportName+"/"+"PopMapNoEdit_"+airportName+".shp");
		ShapefileFeatureTable pop = new ShapefileFeatureTable(file.getAbsolutePath());
		FeatureLayer layer = new FeatureLayer(pop);
		ClassBreaksRenderer cbrenderer = new ClassBreaksRenderer(new SimpleFillSymbol(Color.WHITE,new SimpleLineSymbol(Color.BLACK,1)),"POP10");
		cbrenderer.addBreak(0,919,new SimpleFillSymbol(Color.getHSBColor(0, 0, 1),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(920,1310,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.83f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(1311,1769,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.66f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(1770,2343,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.49f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(2344,3204,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.33f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(3205,4838,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0.16f),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));
		cbrenderer.addBreak(4839,9658,new SimpleFillSymbol(Color.getHSBColor(0, 0, 0),new SimpleLineSymbol(Color.getHSBColor(0,0,0.43f),0.3f)));

		layer.setRenderer(cbrenderer);
		layer.setName("Population Beyond");
		return layer;
	}
}
