package angim;

import java.io.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import com.esri.map.JMap;

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
     public RunANGIM(JLabel label, JButton start, JButton stop, JProgressBar bar,JFrame frame,JComboBox<String> op, JComboBox<String> pop,JTabbedPane sp,JMap map) {
    	 this.start = start;
    	 this.stop = stop;
    	 this.bar = bar;
    	 this.frame = frame;
    	 this.statusLabel = label;
    	 this.op = op;
    	 this.pop = pop;
    	 this.sp = sp;
    	 this.map = map;
         statusLabel.setText("Calculating Noise...");
     }
	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub
		
		try {
			ProcessBuilder pb = new ProcessBuilder("RANGG_V10.exe");
			pb.redirectErrorStream(true);
			Process p = pb.start();
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
        stop.setEnabled(false);
        start.setEnabled(true);
        op.setEnabled(true);
		pop.setEnabled(true);
		sp.setEnabled(true);
        bar.setIndeterminate(false);
        frame.dispose();
        if(status == 0){
        	try {
				UpdateContour contour = new UpdateContour(map);
				contour.updateContourGraphic();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

}