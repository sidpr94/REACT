/*
 * 
 */
package angim;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import scenarioDev.density.InfoOverlayDensity;
import scenarioDev.fleet.InsertFleetTechnology;
import scenarioDev.track.InfoOverlayTrack;

// TODO: Auto-generated Javadoc
/**
 * RunANGIM creates the SwingWorker and processer for running ANGIM in the doInBackground method
 * Once ANGIM successfully finishes i.e. status of 0, the outputs are visualized in the done() method
 * @author Sidharth Prem
 * @see angim.CalculateNoise
 */
public class RunANGIM extends SwingWorker<Integer,String>{
	
	/** The status that lets the worker know ANGIM is complete successfully or not */
	private int status = 1;
	
	/** The status label that showcases the status on the frame. */
	JLabel statusLabel;
	
	/** The start button which will be reenabled in the done() method. */
	JButton start;
	
	/** The stop button which will be disabled in the done() method. */
	JButton stop;
	
	/** The progress bar for ANGIM process. */
	JProgressBar bar;
	
	/** The frame that contains the progress bar and stop button. */
	JFrame frame;
	
	/** The ComboBox containing the operation forecasting information. */
	JComboBox<String> op;
	
	/** The ComboBox containing the population forecast information. */
	JComboBox<String> pop;
	
	/** The tabbed pane that contains all the scenario development info. */
	JTabbedPane scenarioPane;
	
	/** The main map containing airport visualization. */
	JMap map;
	
	/** The noise contour compare map. */
	JMap compare;
	
	/** The process that is initailized for ANGIM. */
	Process p = null;
	
	/** The table containing the results to be showed in Results Tab. */
	JTable table;
	
	/** The reset button to be enabled once calculations are complete. */
	JButton reset;
	
	/** The reset tech button resets any technology insertions. */
	JButton resetTech;
	
	/** Initialize the processbuilder. */
	ProcessBuilder pb = new ProcessBuilder("RANGG_V10.exe");
	
	public static List<String[]> summaryInfo = new ArrayList<String[]>();
	
	/**
	 * Instantiates a new run ANGIM.
	 *
	 *
	 * @param label the label
	 * @param start the start
	 * @param stop the stop
	 * @param bar the bar
	 * @param frame the frame
	 * @param op the op
	 * @param pop the pop
	 * @param sp the sp
	 * @param map the map
	 * @param compare the compare
	 * @param table the table
	 * @param reset the reset
	 * @param resetTech the reset tech
	 */
	public RunANGIM(JLabel label, JButton start, JButton stop, JProgressBar bar,JFrame frame,JComboBox<String> op, JComboBox<String> pop,JTabbedPane sp,JMap map,JMap compare,JTable table,JButton reset,JButton resetTech) {
		this.start = start;
		this.stop = stop;
		this.bar = bar;
		this.frame = frame;
		this.statusLabel = label;
		this.op = op;
		this.pop = pop;
		this.scenarioPane = sp;
		this.map = map;
		this.compare = compare;
		this.table = table;
		this.reset = reset;
		this.resetTech = resetTech;
		statusLabel.setText("Calculating Noise...");
	}
	
	public RunANGIM() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	/**
	 * doInBackground() runs ANGIM unless cancelled
	 * The Integer output lets the statuslabel know the status of ANGIM.
	 */
	@Override
	protected Integer doInBackground() throws Exception {
		// TODO Auto-generated method stub

		try {
			pb.redirectErrorStream(true);
			p = pb.start();
			String s;
			BufferedReader stdout = new BufferedReader(
					new InputStreamReader(p.getInputStream()));
			while ((s = stdout.readLine()) != null && !isCancelled()) {
				publish(s);
			}
			if (!isCancelled()) {
				//wait for ensures that the process is cancelled properly before destroying the process
				status = p.waitFor();
			}
			p.getInputStream().close();
			p.getOutputStream().close();
			p.getErrorStream().close();
			p.destroy();
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	/**
	 * Once ANGIM is finished running or is cancelled the done() method will execute.
	 * Results will only be populated if ANGIM is successfully complete. 
	 */
	protected void done() {
		statusLabel.setText((this.getState()).toString());
		System.out.println(status);
		//checks to see if ANGIM is successful before populating outputs
		if(status == 0){
			try {
				addSummary();
				UpdateContour contour = new UpdateContour(map,op,"update");
				ContourMap c = new ContourMap(compare,op,"update");
				contour.updateContourGraphic();
				InsertFleetTechnology tech = new InsertFleetTechnology(resetTech,op);
				tech.emptyFleet();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				AngimOut Data = new AngimOut(model.getRowCount(),map);
				model.addRow(Data.getAngimInfo());
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
		p = null;
		stop.setEnabled(false);
		start.setEnabled(true);
		op.setEnabled(true);
		pop.setEnabled(true);
		scenarioPane.setEnabled(true);
		reset.setEnabled(true);
		bar.setIndeterminate(false);
		frame.dispose();
	}
	
	private void addSummary() {
		String opInfo = (String) op.getSelectedItem();
		String popInfo = (String) pop.getSelectedItem();
		String popChanged = "";
		if(InfoOverlayDensity.changedBlocks.size() != 0){
			popChanged = "True";
		}else{
			popChanged = "False";
		}
		String aircraftChanged = "";
		if(InsertFleetTechnology.fleetChanged.size() != 0){
			for(Map.Entry<String,Number> entry: InsertFleetTechnology.fleetChanged.entrySet()){
				if(!aircraftChanged.equals("")){
					aircraftChanged = aircraftChanged+", "+entry.getKey();
				}else{
					aircraftChanged = entry.getKey();
				}
			}
		}else{
			aircraftChanged = "N/A";
		}
		String trackChanged = "";
		if(InfoOverlayTrack.changed.size() != 0){
			for(Map.Entry<String,String> entry: InfoOverlayTrack.changed.entrySet()){
				if(!trackChanged.equals("")){
					trackChanged = trackChanged+", "+entry.getKey();
				}
				else{
					trackChanged = entry.getKey();
				}
			}
		}else{
			trackChanged = "N/A";
		}
		// TODO Auto-generated method stub
		String[] info = new String[5];
		info[0] = opInfo;
		info[1] = popInfo;
		info[2] = popChanged;
		info[3] = aircraftChanged;
		info[4] = trackChanged;	
		
		summaryInfo.add(info);
	}
	
	public void addBaseline(){
		if(summaryInfo.size() == 0){
			String[] info = new String[5];
			info[0] = "2015 Operations";
			info[1] = "2010 Year";
			info[2] = "False";
			info[3] = "N/A";
			info[4] = "N/A";
			summaryInfo.add(info);
		}
	}

	/**
	 * Stop process for ANGIM, causes status to != 0.
	 */
	public void stopProcess(){
		if(p != null){
			p.destroy();
		}
	}

}