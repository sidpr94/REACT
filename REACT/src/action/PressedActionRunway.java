package action;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
	
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
		if(button.isSelected()){
			JPanel runInputs = new JPanel();
			runInputs.setBackground(new Color(0,0,0,100));
			runInputs.setDoubleBuffered(true);
			runInputs.setLayout(new GridLayout(5,1));
			JTextField rnStrt = new JTextField();
			rnStrt.setBackground(new Color(0,0,0,100));
			rnStrt.setText("Runway Start");
			JFormattedTextField runStart = new JFormattedTextField(NumberFormat.getNumberInstance());
			runStart.setPreferredSize(new Dimension(250,40));
			
		}
	}

}
