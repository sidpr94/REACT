/*
 * 
 */
package results;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JComboBox;

import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.map.Layer;
import com.esri.map.MapEvent;
import com.esri.map.MapEventListenerAdapter;
import com.esri.toolkit.JLayerList;

import angim.UpdateContour;
import basemap.NoiseContour;
import basemap.PopMap;
import basemap.PopMapNoEdit;

// TODO: Auto-generated Javadoc
/**
 * The Class ContourMap creates the contour comparison map found in the results pane.
 * The ContourMap contains population counts, noise contour for hte baseline.
 * The scenario contour is generated once noise is calculated.
 * @author Sidharth Prem
 * @see results.CompareContourMap
 */
public class ContourMap {
	
	/** The contour comparison map. */
	JMap comparison;
	
	/** The comparsion layer list. */
	JLayerList compLayerList;
	
	/** The operational forecasting scenario information. */
	JComboBox<String> op;
	
	/** The state of the contour used in UpdateContour class. */
	String s;
	
	/**
	 * Instantiates a new contour map.
	 *
	 * @param map the map
	 * @param list the list
	 */
	public ContourMap(JMap map,JLayerList list){
		this.comparison = map;
		this.compLayerList = list;
	}
	
	/**
	 * Instantiates a new contour map.
	 *
	 * @param map the map
	 * @param op the op
	 * @param s the s
	 */
	public ContourMap(JMap map,JComboBox<String> op, String s){
		this.comparison = map;
		this.op = op;
		this.s = s;
	}
	
	/**
	 * Creates the comparison map.
	 *
	 * @return the comparison map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JMap createMap() throws IOException{
		PopMap pmap = new PopMap();
		comparison.setBackground(new Color(214, 217, 223));
		NoiseContour nmap = new NoiseContour();
		PopMapNoEdit popm = new PopMapNoEdit();
		GraphicsLayer pop = new GraphicsLayer();
		Layer popnoedit = popm.createPop();
		Layer noise = nmap.createNoiseContour();
		noise.setName("Baseline Noise Contour");
		comparison.getLayers().add(0,popnoedit);
		comparison.getLayers().add(1,pop);
		comparison.getLayers().add(2,noise);
		comparison.getLayers().get(0).setVisible(false);
		comparison.getLayers().get(1).setVisible(false);
		comparison.addMapEventListener(new MapEventListenerAdapter(){
			@Override
			public void mapReady(final MapEvent arg0) {
				try {
					pmap.createPopMap(pop);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		return comparison;
	}
	
	/**
	 * Adds the scenario noise contour once noise calculations are complete.
	 * Size denotes whether or not there is a scenario contained in the map
	 *
	 * @param size the number of layers in the comparison map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void addComparison(int size) throws IOException{
		UpdateContour c = new UpdateContour(comparison,op,s);
		NoiseContour nmap = new NoiseContour();
		if(size == 3){// no scenario noise contour
			Layer noise = nmap.createNoiseContour();
			comparison.getLayers().add(noise);
			comparison.getLayers().get(3).setName("Scenario Noise Contour");
			c.addScenarioGraphic();
		}else if (size == 4){
			c.addScenarioGraphic();
		}
	}
}
