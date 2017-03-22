package basemap.popmap;
import java.util.Map;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Feature;
import com.esri.core.symbol.Symbol;

public class PopMapFeature implements Feature {
	Feature feat;
	String Area = "Area";
	String BLOCK = "BLOCKCE";
	String BLOCKID = "BLOCKID10";
	String BUFF = "BUFF_DIST";
	String COUNTYFP10 = "COUNTYFP10";
	String FID = "FID";
	String XYMCI = "FID_XYMCI";
	String pophu = "FID_pophu";
	String HOUSING10 = "HOUSING10";
	String OBJECTID	= "OBJECTID";
	String ORIG = "ORIG_FID";
	String PARTFLG = "PARTFLG";
	String POP10 = "POP10";
	String STATEFP10 = "STATEFP10";
	String ShapeArea = "Shape_Area";
	String ShapeLeng = "Shape_Leng";
	String TRACTCE10 = "TRACTCE10";
	String XLONG = "XLONG";
	String YLAT = "YLAT";
	
	public PopMapFeature(Feature f){
		this.feat = f;
	}

	@Override
	public Object getAttributeValue(String arg0) {
		// TODO Auto-generated method stub
			return feat.getAttributeValue(arg0);
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return feat.getAttributes();
	}

	@Override
	public Geometry getGeometry() {
		// TODO Auto-generated method stub
		return feat.getGeometry();
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return feat.getId();
	}

	@Override
	public SpatialReference getSpatialReference() {
		// TODO Auto-generated method stub
		return feat.getSpatialReference();
	}

	@Override
	public Symbol getSymbol() {
		// TODO Auto-generated method stub
		return feat.getSymbol();
	}
	public String toString(){
		return "PopMapFeature [geometry="+getGeometry()+", attributes="+getAttributes()+", table=FeatureTable, oid="+getId()+"]";
	}

}
