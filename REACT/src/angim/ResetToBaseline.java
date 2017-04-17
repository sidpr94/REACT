package angim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;

import com.esri.map.JMap;

public class ResetToBaseline implements ActionListener {
	JMap map;
	JComboBox<String> op;
	public ResetToBaseline(JComboBox<String> op, JMap map){
		this.map = map;
		this.op = op;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		op.getModel().setSelectedItem(op.getModel().getElementAt(0));
		try {
			UpdateContour reset = new UpdateContour(map, op, "reset");
			reset.updateContourGraphic();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ADD TRACKS HERE 
		
	}

}
