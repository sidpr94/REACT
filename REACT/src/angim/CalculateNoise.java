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

import com.esri.map.JMap;

public class CalculateNoise implements ActionListener {
	JButton stop = new JButton("Stop");
	JButton start;
	JButton reset;
	final JLabel statusLabel = new JLabel("Status: ", JLabel.CENTER); 
    JProgressBar bar = new JProgressBar();
    RunANGIM angim;
    JFrame frame;
    JComboBox<String> op;
    JComboBox<String> pop;
    JTabbedPane sp;
    JMap map;
    JMap compare;
    JTable table;
	public CalculateNoise(JButton button,JComboBox<String> op, JComboBox<String> pop,JTabbedPane sp,JMap map,JMap compare,JTable table,JButton reset){
		this.start = button;
		this.op = op;
		this.pop = pop;
		this.sp = sp;
		this.map = map;
		this.compare = compare;
		this.table = table;
		this.reset = reset;
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		btn.setEnabled(false);
		op.setEnabled(false);
		pop.setEnabled(false);
		sp.setEnabled(false);
		stop.setEnabled(true);
		reset.setEnabled(false);
		ForecastOperation changeOps = new ForecastOperation(op);
		try {
			changeOps.updateOperations();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		createFrame cf = new createFrame();
		Thread t = new Thread(cf);
		t.start();		
	}
	
	public class createFrame implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			frame = new JFrame("Swing Worker Example");
	        frame.setUndecorated(true);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        JPanel panel = new JPanel();
	        panel.setBorder(
	            BorderFactory.createEmptyBorder(5, 5, 5, 5));
	        panel.setLayout(new BorderLayout(5, 5));
	        final JLabel statusLabel = new JLabel("Status: ", JLabel.CENTER); 
	        final JLabel title = new JLabel("Calculating Noise...", JLabel.CENTER); 
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(stop);
	        stop.addActionListener(new StopCalculation());
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
	        angim = new RunANGIM(statusLabel,start,stop,bar,frame,op,pop,sp,map,compare,table,reset);
	        angim.execute();
	        bar.setIndeterminate(true);
	        
		}
		
	}
	
	public class StopCalculation implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			angim.cancel(true);
			angim.done();
			frame.dispose();
		}
		
	}
}
