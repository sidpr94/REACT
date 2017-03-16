package scenarioDev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class DensityControl {
	public DensityControl(){
		
	}
	public JPanel getDensity(){
		JPanel dInputs = new JPanel();
		GridLayout gLayout = new GridLayout(3,1);
		gLayout.setVgap(3);
		dInputs.setLayout(gLayout);
		dInputs.setBackground(new Color(0,0,0,100));
		dInputs.setBorder(BorderFactory.createEmptyBorder());
		
		JTextField rnEnh = new JTextField();
		rnEnh.setBackground(new Color(0,0,0,100));
		rnEnh.setPreferredSize(new Dimension(250,30));
		rnEnh.setFont(new Font(rnEnh.getFont().getName(),Font.BOLD,12));
		rnEnh.setText("Density Control");
		rnEnh.setHorizontalAlignment(SwingConstants.CENTER);
		rnEnh.setBorder(BorderFactory.createMatteBorder(0, 0,3,0,Color.BLACK));
		rnEnh.setEditable(false);
		rnEnh.setEnabled(false);
		dInputs.add(rnEnh);
		
		ButtonGroup group = new ButtonGroup(){
			private static final long serialVersionUID = 1L;
			public void setSelected(ButtonModel model, boolean selected){
				if (selected){
					super.setSelected(model,selected);
				}else{
					clearSelection();
				}
			}
		};
		JToggleButton perBlock = new JToggleButton();
		perBlock.setText("Control Density per Block");
		perBlock.setHorizontalAlignment(SwingConstants.CENTER);
		perBlock.setFont(new Font(perBlock.getFont().getName(),Font.BOLD,12));
		
		JToggleButton perHighlight = new JToggleButton();
		perHighlight.setText("Control Density in Selected Region");
		perHighlight.setHorizontalAlignment(SwingConstants.CENTER);
		perHighlight.setFont(new Font(perHighlight.getFont().getName(),Font.BOLD,12));
		
		group.add(perBlock);
		dInputs.add(perBlock);
		group.add(perHighlight);
		dInputs.add(perHighlight);
		return dInputs;
	}
}
