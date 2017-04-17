package angim;

import java.io.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import com.esri.map.JMap;

import results.ContourMap;

public class RunANGIM extends SwingWorker<Integer,String>{
	private int status = 1;
	JLabel statusLabel;
	JButton start;
	JButton stop;
	JProgressBar bar;
	JFrame frame;
	JComboBox<String> op;
	JComboBox<String> pop;
	JTabbedPane sp;
	JMap map;
	JMap compare;
	Process p;
	JTable table;
	JButton reset;
	public RunANGIM(JLabel label, JButton start, JButton stop, JProgressBar bar,JFrame frame,JComboBox<String> op, JComboBox<String> pop,JTabbedPane sp,JMap map,JMap compare,JTable table,JButton reset) {
		this.start = start;
		this.stop = stop;
		this.bar = bar;
		this.frame = frame;
		this.statusLabel = label;
		this.op = op;
		this.pop = pop;
		this.sp = sp;
		this.map = map;
		this.compare = compare;
		this.table = table;
		this.reset = reset;
		statusLabel.setText("Calculating Noise...");
	}
	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {
			ProcessBuilder pb = new ProcessBuilder("RANGG_V10.exe");
			pb.redirectErrorStream(true);
			p = pb.start();
			String s;
			BufferedReader stdout = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
			while ((s = stdout.readLine()) != null && !isCancelled()) {
				publish(s);
			}
			if (!isCancelled()) {
				status = p.waitFor();
			}
			p.getInputStream().close();
			p.getOutputStream().close();
			p.getErrorStream().close();
			p.destroy();
		} catch (IOException | InterruptedException ex) {
			ex.printStackTrace(System.err);
		}
		return status;
	}

	protected void done() {
		statusLabel.setText((this.getState()).toString());
		System.out.println(status);
		if(status == 0){
			try {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				AngimOut Data = new AngimOut(model.getRowCount());
				model.addRow(Data.getAngimInfo());
				UpdateContour contour = new UpdateContour(map,op,"update");
				ContourMap c = new ContourMap(compare,op,"update");
				contour.updateContourGraphic();
				if(compare.getLayers().size() == 3){
					c.addComparison(3);
				}else if (compare.getLayers().size() == 4){
					c.addComparison(4);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		stop.setEnabled(false);
		start.setEnabled(true);
		op.setEnabled(true);
		pop.setEnabled(true);
		sp.setEnabled(true);
		reset.setEnabled(true);
		bar.setIndeterminate(false);
		if(p != null){
			try {
				p.getInputStream().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				p.getOutputStream().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				p.getErrorStream().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.destroy();
			frame.dispose();
		}
	}

}