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
 * The Class ContourMap.
 */
public class ContourMap {
	
	/** The map. */
	JMap map;
	
	/** The list. */
	JLayerList list;
	
	/** The op. */
	JComboBox<String> op;
	
	/** The s. */
	String s;
	
	/**
	 * Instantiates a new contour map.
	 *
	 * @param map the map
	 * @param list the list
	 */
	public ContourMap(JMap map,JLayerList list){
		this.map = map;
		this.list = list;
	}
	
	/**
	 * Instantiates a new contour map.
	 *
	 * @param map the map
	 * @param op the op
	 * @param s the s
	 */
	public ContourMap(JMap map,JComboBox<String> op, String s){
		this.map = map;
		this.op = op;
		this.s = s;
	}
	
	/**
	 * Creates the map.
	 *
	 * @return the j map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public JMap createMap() throws IOException{
		PopMap pmap = new PopMap();
		map.setBackground(new Color(214, 217, 223));
		NoiseContour nmap = new NoiseContour();
		PopMapNoEdit popm = new PopMapNoEdit();
		GraphicsLayer pop = new GraphicsLayer();
		Layer popnoedit = popm.createPop();
		Layer noise = nmap.createNoiseContour();
		noise.setName("Baseline Noise Contour");
		map.getLayers().add(0,popnoedit);
		map.getLayers().add(1,pop);
		map.getLayers().add(2,noise);
		map.getLayers().get(0).setVisible(false);
		map.getLayers().get(1).setVisible(false);
		map.addMapEventListener(new MapEventListenerAdapter(){
			@Override
			public void mapReady(final MapEvent arg0) {
				pmap.createPopMap(pop);
			}
		});
		
		return map;
	}
	
	/**
	 * Adds the comparison.
	 *
	 * @param size the size
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void addComparison(int size) throws IOException{
		UpdateContour c = new UpdateContour(map,op,s);
		NoiseContour nmap = new NoiseContour();
		if(size == 3){
			Layer noise = nmap.createNoiseContour();
			map.getLayers().add(noise);
			map.getLayers().get(3).setName("Scenario Noise Contour");
			c.addScenarioGraphic();
		}else if (size == 4){
			c.addScenarioGraphic();
		}
	}
}
