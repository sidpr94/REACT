package scenarioDev.density;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.esri.map.JMap;

public class DensityControl {
	JMap map;
	public DensityControl(JMap jMap){
		map = jMap;
	}
	public JPanel getDensity(){
		JPanel dInputs = new JPanel();
		GridLayout gLayout = new GridLayout(3,1);
		gLayout.setVgap(3);
		dInputs.setBackground(new Color(51,81,112));
		dInputs.setLayout(gLayout);
		dInputs.setBorder(BorderFactory.createEmptyBorder());

		JTextField rnEnh = new JTextField();
		rnEnh.setOpaque(false);
		rnEnh.setDisabledTextColor(Color.WHITE);
		rnEnh.setPreferredSize(new Dimension(250,30));
		rnEnh.setFont(new Font(rnEnh.getFont().getName(),Font.BOLD,14));
		rnEnh.setText("Density Control");
		rnEnh.setHorizontalAlignment(SwingConstants.CENTER);
		rnEnh.setBorder(BorderFactory.createMatteBorder(0,0,0,0,Color.BLACK));
		rnEnh.setEditable(false);
		rnEnh.setEnabled(false);
		dInputs.add(rnEnh);

		JButton perHighlight = new JButton();
		perHighlight.setText("Reset To Census");
		perHighlight.setHorizontalAlignment(SwingConstants.CENTER);
		perHighlight.setFont(new Font(perHighlight.getFont().getName(),Font.BOLD,12));

		JToggleButton perBlock = new JToggleButton();
		perBlock.setText("Control Density per Block");
		perBlock.setHorizontalAlignment(SwingConstants.CENTER);
		perBlock.setFont(new Font(perBlock.getFont().getName(),Font.BOLD,12));
		perBlock.addActionListener(new DensityOn(map,perHighlight));

		dInputs.add(perBlock);
		dInputs.add(perHighlight);

		return dInputs;
	}
}
