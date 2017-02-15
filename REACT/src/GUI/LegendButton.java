package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LegendButton {
	JPanel legendPanel;
	int width;
	int height;
	public LegendButton(JPanel lPanel, int w, int h){
		this.legendPanel = lPanel;
		this.width = w;
		this.height = h;
	}
	public JButton getLegendButton(){
	  final JButton btnLegendToggle = new JButton("Toggle Legend");
	    btnLegendToggle.setPreferredSize(new Dimension((int) (width/8), (int) (height/32)));
	    btnLegendToggle.setVisible(true);
	    btnLegendToggle.addActionListener(new ActionListener() {
	      @Override
	      public void actionPerformed(ActionEvent e) {
	        legendPanel.setVisible(!legendPanel.isVisible());
	      }
	    });
	    return btnLegendToggle;
	}
}
