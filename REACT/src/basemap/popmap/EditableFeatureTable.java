package basemap.popmap;
import java.util.List;
import java.util.concurrent.Future;

import com.esri.core.geodatabase.ShapefileFeatureTable;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.Feature;
import com.esri.core.map.FeatureResult;
import com.esri.core.map.Field;
import com.esri.core.table.FeatureTable;
import com.esri.core.table.TableException;
import com.esri.core.tasks.query.QueryParameters;

public class EditableFeatureTable extends FeatureTable {
	List<Feature> list;
	long[] IdList;
	Geometry g;
	String copy;
	Envelope extent;
	public EditableFeatureTable(){}
	public EditableFeatureTable(ShapefileFeatureTable shape){
		ShapefileFeatureTable shapefile = shape;
		for(int i = 1; i < shapefile.getNumberOfFeatures();i++){
			try {
				list.add(i,shapefile.getFeature(i));
			} catch (TableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	@Override
	public long addFeature(Feature arg0) throws TableException {
		// TODO Auto-generated method stub
		list.add(arg0);
		return (long) list.size();
	}

	@Override
	public long[] addFeatures(List<Feature> arg0) throws TableException {
		// TODO Auto-generated method stub
		list.addAll(arg0);
		IdList = new long[arg0.size()];
		for(int i =0; i < arg0.size(); i++){
			IdList[i] = arg0.get(i).getId();
		}
		return IdList;
	}

	@Override
	public void deleteFeature(long arg0) throws TableException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFeatures(long[] arg0) throws TableException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCopyright() {
		// TODO Auto-generated method stub
		return shapefile.getCopyright();
	}

	@Override
	public Envelope getExtent() {
		// TODO Auto-generated method stub
		return shapefile.getExtent();
	}

	@Override
	public Feature getFeature(long arg0) throws TableException {
		// TODO Auto-generated method stub
		return list.get((int) arg0);
	}

	@Override
	public FeatureResult getFeatures(long[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Field getField(String arg0) {
		// TODO Auto-generated method stub
		return shapefile.getField(arg0);
	}

	@Override
	public List<Field> getFields() {
		// TODO Auto-generated method stub
		return shapefile.getFields();
	}

	@Override
	public long getNumberOfFeatures() {
		// TODO Auto-generated method stub
		return (long) list.size();
	}

	@Override
	public SpatialReference getSpatialReference() {
		// TODO Auto-generated method stub
		return shapefile.getSpatialReference();
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return shapefile.getTableName();
	}

	@Override
	public boolean hasGeometry() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Future<FeatureResult> queryFeatures(QueryParameters arg0, CallbackListener<FeatureResult> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<long[]> queryIds(QueryParameters arg0, CallbackListener<long[]> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateFeature(long arg0, Feature arg1) throws TableException {
		list.set((int)arg0,arg1);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFeatures(long[] arg0, List<Feature> arg1) throws TableException {
		for(int i = 0; i< arg1.size(); i++){
			list.set((int)arg0[i], arg1.get(i));
		}
		// TODO Auto-generated method stub
		
	}
	public Geometry.Type getGeometryType(){
		return shapefile.getGeometryType();
	}
	public long getHandle(){
		
		return shapefile.getHandle();
	}
	public String getObjectIdField(){
		return shapefile.getObjectIdField();
	}
	public String toString(){
		return "[getTableName()="+getTableName();
	}
}
