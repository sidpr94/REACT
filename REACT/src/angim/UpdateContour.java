package angim;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;

public class UpdateContour {
	JMap map;
	static double long_ref = -1.653338445;
	static double lat_ref = 0.685798105;
	static double R_earth = 3440.069517;
	static double rot_deg = 1.346084905;
	Color DNL55 = new Color(56,168,0);
	Color DNL60 = new Color(0,92,230);
	Color DNL65 = new Color(255,0,0);
	public UpdateContour(JMap map) throws IOException{
		this.map = map;
		
	}
	public void updateContourGraphic() throws IOException{
		GraphicsLayer noise = (GraphicsLayer) map.getLayers().get(3);	
		BufferedReader[] readers = new BufferedReader[3];
		readers[0] = new BufferedReader(new FileReader(getFile()[0]));
		readers[1] = new BufferedReader(new FileReader(getFile()[1]));
		readers[2] = new BufferedReader(new FileReader(getFile()[2]));
		int[] id = noise.getGraphicIDs();
		for(int i = 0; i < getFile().length; i++){
			String line = readers[i].readLine();
			List<Double> lon = new ArrayList<>();
			List<Double> lat = new ArrayList<>();
			int DNL = 0;
			for(line = readers[i].readLine(); line != null; line = readers[i].readLine()){
				String[] tokens = line.split(",");
				double xnmi = Double.parseDouble(tokens[0]);
				double ynmi = Double.parseDouble(tokens[1]);
				if(xnmi == 55 || xnmi == 60 || xnmi == 65){
					DNL = (int) xnmi;
				}else{
					double[] longlat = changeCoordSystem(xnmi,ynmi);
					lon.add(longlat[0]);
					lat.add(longlat[1]);
				}
			}
			Map<String,Object> att = new HashMap<String,Object>();
			att.put("Contour", DNL);
			SimpleLineSymbol symbol;
			if(DNL == 55){
				symbol = new SimpleLineSymbol(DNL55,5);
			}else if (DNL == 60){
				symbol = new SimpleLineSymbol(DNL60,5);
			}else {
				symbol = new SimpleLineSymbol(DNL65,5);
			}
			Graphic g = new Graphic(createLine(lon,lat),symbol,att);
			
			noise.updateGraphic(id[i], g);
		}
		
	}
	
	private String[] getFile(){
		String[] urls = new String[3];
		File file1 = new File("OUT/Contours/Contours_KMCI_2015_REACT_MCI_55.csv");
		urls[0] = file1.getAbsolutePath();
		File file2 = new File("OUT/Contours/Contours_KMCI_2015_REACT_MCI_60.csv");
		urls[1] = file2.getAbsolutePath();
		File file3 = new File("OUT/Contours/Contours_KMCI_2015_REACT_MCI_65.csv");
		urls[2]= file3.getAbsolutePath();
		return urls;
	}

	private double[] changeCoordSystem(double xnmi, double ynmi){
		double xnmirot = xnmi*Math.cos(rot_deg)-ynmi*Math.sin(rot_deg);
		double ynmirot = ynmi*Math.cos(rot_deg)+xnmi*Math.sin(rot_deg);
		double bearingX = 0;
		double bearingY = 0;
		if(xnmirot < 0){
			bearingX = 1.5*Math.PI;
		}else{
			bearingX = 0.5*Math.PI;
		}
		if(ynmirot < 0){
			bearingY = Math.PI;
		}else{
			bearingY = 0;
		}
		double[] longlat = new double[2];
		longlat[0] = (180/Math.PI)*(long_ref + Math.atan2(Math.sin(Math.abs(xnmirot)/R_earth)*Math.cos(lat_ref)*Math.sin(bearingX),Math.cos(Math.abs(xnmirot)/R_earth)-Math.sin(lat_ref)*Math.sin(lat_ref)));
		longlat[1] = (180/Math.PI)*(Math.asin(Math.sin(lat_ref)*Math.cos(Math.abs(ynmirot)/R_earth)+Math.cos(lat_ref)*Math.sin(Math.abs(ynmirot)/R_earth)*Math.cos(bearingY)));
		return longlat;
	}
	
	private Polyline createLine(List<Double> x, List<Double> y){
		Polyline line = new Polyline();
		line.startPath(x.get(0), y.get(0));
		for(int i = 1; i < x.size(); i++){
			line.lineTo(x.get(i), y.get(i));
		}
		return line;
	}
}
