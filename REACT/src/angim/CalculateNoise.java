/*
 * 
 */
package angim;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.esri.map.JMap;

// TODO: Auto-generated Javadoc
/**
 * This is the action listener connected to the Calculate button.
 * This action listener commences the calculation of Noise, running of ANGIM, updating of input files, and output files.
 * This is the center of the noise calculations.
 * A new thread is created to run ANGIM, this is necessary to avoid issues with the main frame/thread
 * @author Sidharth Prem
 * @see angim.RunANGIM
 * @see content.DashBoard
 */
public class CalculateNoise implements ActionListener {
	
	/** The stop button, halts all calculations. */
	JButton stop = new JButton("Stop");
	
	/** The start button begins the calculations for noise.
	 * @see content.DashBoard */
	JButton start;
	
	/** The reset button resets the map and configuration to baseline configuration. 
	 * @see content.DashBoard */
	JButton reset;
	
	/** The status label shows the current status of the calculations. */
	final JLabel statusLabel = new JLabel("Status: ", JLabel.CENTER); 
    
    /** The progress bar as ANGIM runs. */
    JProgressBar bar = new JProgressBar();
    
    /** The frame that contains the stop button and the progress bar. */
    JFrame frame;
    
    /** The combo box containing the operational forecasting information. 
     * @see content.DashBoard */
    JComboBox<String> op;
    
    /** The combo box containing the population forecasting information.
     * @see content.DashBoard */
    JComboBox<String> pop;
    
    /** The pane containing the scenario development items.
     * @see content.DashBoard */
    JTabbedPane scenarioPanes;
    
    /** The main map containing airport info.
     * @see basemap.CreateMainMap */
    JMap map;
    
    /** The JMap that compares the noise contours.
     * @see results.CompareContourMap  */
    JMap compare;
    
    /** The table that contains all results.
     * @see results.ResultsTable */
    JTable table;
    
    /** The reset tech button resets technology insertions.
     * @see scenarioDev.track.TrackFlexibility  */
    JButton resetTech;
	
	/**
	 * Instantiates a new calculate noise.
	 *
	 * @param button the calculate button
	 * @param op the operational forecasting ComboBox
	 * @param pop the population forecasting ComboBox
	 * @param sp the scenarioPane from the Dashboard
	 * @param map the main map
	 * @param compare the compare contour map
	 * @param table the results table
	 * @param reset the reset button
	 * @param resetTech the reset tech button
	 */
	public CalculateNoise(JButton button,JComboBox<String> op, JComboBox<String> pop,JTabbedPane sp,JMap map,JMap compare,JTable table,JButton reset,JButton resetTech){
		this.start = button;
		this.op = op;
		this.pop = pop;
		this.scenarioPanes = sp;
		this.map = map;
		this.compare = compare;
		this.table = table;
		this.reset = reset;
		this.resetTech = resetTech;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	/**
	 * Edits the ANGIM input files when Calculate button is clicked.
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		btn.setEnabled(false);
		op.setEnabled(false);
		pop.setEnabled(false);
		scenarioPanes.setEnabled(false);
		stop.setEnabled(true);
		reset.setEnabled(false);
		ForecastScenarios changeOps = new ForecastScenarios(op);
		try {
			changeOps.updateOperations();
			changeOps.insertFleetTechnology();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		createFrame cf = new createFrame();
		//creates a new thread to run ANGIM
		Thread t = new Thread(cf);
		t.start();		
	}
	
	/**
	 * Create Frame creates the new JPanel with the progress bar as well as runs ANGIM.
	 * A new thread is created to do this.
	 */
	public class createFrame implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			frame = new JFrame("Swing Worker Example");
	        frame.setUndecorated(true);
	        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	        JPanel panel = new JPanel();
	        panel.setBorder(
	            BorderFactory.createEmptyBorder(5, 5, 5, 5));
	        panel.setLayout(new BorderLayout(5, 5));
	        final JLabel statusLabel = new JLabel("Status: ", SwingConstants.CENTER); 
	        final JLabel title = new JLabel("Calculating Noise...", SwingConstants.CENTER); 
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(stop);
	        buttonPanel.add(bar);
	        panel.add(title, BorderLayout.CENTER);
	        panel.add(statusLabel, BorderLayout.CENTER);
	        panel.add(buttonPanel, BorderLayout.PAGE_END);
	        frame.setContentPane(panel);
	        frame.pack();
	        frame.setVisible(true);
	        frame.setAlwaysOnTop(true);
	        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	        RunANGIM angim = new RunANGIM(statusLabel,start,stop,bar,frame,op,pop,scenarioPanes,map,compare,table,reset,resetTech);
	        angim.execute();
	        stop.addActionListener(new StopCalculation(angim));
	        bar.setIndeterminate(true);
	        
		}
		
	}
	
	/**
	 * The stop button calls this action listener.
	 * Cancels ANGIM process, and enables all buttons that were disabled.
	 */
	public class StopCalculation implements ActionListener{
		
		/** The angim runner. */
		RunANGIM angim;
		
		/**
		 * Instantiates a new stop calculation.
		 *
		 * @param a the angim runner
		 */
		StopCalculation(RunANGIM a){
			this.angim = a;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			angim.stopProcess();
			angim.cancel(true);
			angim.done();
		}
		
	}
}
