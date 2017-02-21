package action;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
	
public class PressedActionRunway implements ActionListener {
	
	JPanel panel;
	JToggleButton button;
	
	public PressedActionRunway(JPanel panel, JToggleButton button){
		this.panel = panel;
		this.button = button;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JPanel runInputs = new JPanel();
		runInputs.setBackground(new Color(0,0,0,100));
		runInputs.setDoubleBuffered(true);
		GridLayout gridLayout = new GridLayout(7,1);
		gridLayout.setVgap(3);
		runInputs.setLayout(gridLayout);
		if(button.isSelected()){
			
			JTextField rnEnh = new JTextField();
			rnEnh.setBackground(new Color(0,0,0,100));
			rnEnh.setText("Runway Enhancement");
			rnEnh.setHorizontalAlignment(SwingConstants.CENTER);
			rnEnh.setBorder(BorderFactory.createMatteBorder(3, 0,3,0,Color.BLACK));
			rnEnh.setEditable(false);
			rnEnh.setEnabled(false);
			runInputs.add(rnEnh);
			
			JTextField rnName = new JTextField();
			rnName.setBackground(new Color(0,0,0,100));
			rnName.setText("Runway Name");
			rnName.setEditable(false);
			rnName.setEnabled(false);
			runInputs.add(rnName);
			
			JTextField runName = new JTextField();
			runInputs.add(runName);
			
			JTextField rnStrt = new JTextField();
			rnStrt.setBackground(new Color(0,0,0,100));
			rnStrt.setText("Runway Start:");
			rnStrt.setEditable(false);
			rnStrt.setEnabled(false);
			runInputs.add(rnStrt);
			
			JPanel runSt = new JPanel();
			runSt.setBackground(new Color(0,0,0,100));
			runSt.setDoubleBuffered(true);
			GridLayout gridLayoutSt = new GridLayout(2,2);
			gridLayout.setHgap(2);
			runSt.setLayout(gridLayoutSt);
			
			JTextField rnStrtLt = new JTextField();
			rnStrtLt.setBackground(new Color(0,0,0,100));
			rnStrtLt.setText("Runway Start Lat:");
			rnStrtLt.setEditable(false);
			rnStrtLt.setEnabled(false);
			runSt.add(rnStrtLt);
			
			JTextField rnStrtLng = new JTextField();
			rnStrtLng.setBackground(new Color(0,0,0,100));
			rnStrtLng.setText("Runway Start Long:");
			rnStrtLng.setEditable(false);
			rnStrtLng.setEnabled(false);
			runSt.add(rnStrtLng);
			
			JFormattedTextField runStartLat = new JFormattedTextField(NumberFormat.getNumberInstance());
			runSt.add(runStartLat);
			
			JFormattedTextField runStartLong = new JFormattedTextField(NumberFormat.getNumberInstance());
			runSt.add(runStartLong);
			
			runInputs.add(runSt);
			
			JTextField rnEnd = new JTextField();
			rnEnd.setBackground(new Color(0,0,0,100));
			rnEnd.setText("Runway End:");
			rnEnd.setEditable(false);
			rnEnd.setEnabled(false);
			runInputs.add(rnEnd);
			
			JPanel runEd = new JPanel();
			runEd.setBackground(new Color(0,0,0,100));
			runEd.setDoubleBuffered(true);
			GridLayout gridLayoutEd = new GridLayout(2,2);
			gridLayout.setHgap(2);
			runEd.setLayout(gridLayoutEd);
			
			JTextField rnEdLt = new JTextField();
			rnEdLt.setBackground(new Color(0,0,0,100));
			rnEdLt.setText("Runway End Lat:");
			rnEdLt.setEditable(false);
			rnEdLt.setEnabled(false);
			runEd.add(rnEdLt);
			
			JTextField rnEdLng = new JTextField();
			rnEdLng.setBackground(new Color(0,0,0,100));
			rnEdLng.setText("Runway End Long:");
			rnEdLng.setEditable(false);
			rnEdLng.setEnabled(false);
			runEd.add(rnEdLng);
			
			JFormattedTextField runEndLat = new JFormattedTextField(NumberFormat.getNumberInstance());
			runEd.add(runEndLat);
			
			JFormattedTextField runEndLong = new JFormattedTextField(NumberFormat.getNumberInstance());
			runEd.add(runEndLong);
			
			runInputs.add(runEd);
			
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 8;
			c.weighty = 0;
			c.anchor = GridBagConstraints.PAGE_START;
			panel.add(runInputs, c);	
		}else{
			panel.remove(runInputs);
		}
	}

}
