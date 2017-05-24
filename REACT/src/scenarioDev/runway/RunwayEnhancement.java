/*
 * 
 */
package scenarioDev.runway;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class RunwayEnhancement.
 */
public class RunwayEnhancement {

	/**
	 * Instantiates a new runway enhancement.
	 */
	public RunwayEnhancement(){}

	/**
	 * Gets the run.
	 *
	 * @return the run
	 */
	public JPanel getRun(){
		JPanel runInputs = new JPanel();
		runInputs.setBackground(new Color(51,81,112));
		runInputs.setDoubleBuffered(true);
		GridLayout gridLayout = new GridLayout(7,1);
		gridLayout.setVgap(3);
		runInputs.setLayout(gridLayout);
		runInputs.setBorder(BorderFactory.createEmptyBorder());

		JTextField rnEnh = new JTextField();
		rnEnh.setOpaque(false);
		rnEnh.setDisabledTextColor(Color.WHITE);
		rnEnh.setPreferredSize(new Dimension(250,30));
		rnEnh.setFont(new Font(rnEnh.getFont().getName(),Font.BOLD,14));
		rnEnh.setText("Runway Enhancement");
		rnEnh.setHorizontalAlignment(SwingConstants.CENTER);
		rnEnh.setBorder(BorderFactory.createMatteBorder(0, 0,1,0,Color.WHITE));
		rnEnh.setEditable(false);
		rnEnh.setEnabled(false);
		runInputs.add(rnEnh);

		JTextField rnName = new JTextField();
		rnName.setOpaque(false);
		rnName.setDisabledTextColor(Color.WHITE);
		rnName.setText("Runway Name:");
		rnName.setFont(new Font(rnName.getFont().getName(),Font.PLAIN,12));
		rnName.setEditable(false);
		rnName.setEnabled(false);
		rnName.setBorder(BorderFactory.createEmptyBorder());
		runInputs.add(rnName);

		JTextField runName = new JTextField();
		runName.setFont(new Font(rnName.getFont().getName(),Font.PLAIN,12));
		runInputs.add(runName);

		JPanel runSt = new JPanel();
		runSt.setBackground(new Color(51,81,112));
		runSt.setDoubleBuffered(true);
		GridLayout gridLayoutSt = new GridLayout(1,2);
		gridLayout.setHgap(2);
		runSt.setLayout(gridLayoutSt);

		JTextField rnStrtLt = new JTextField();
		rnStrtLt.setOpaque(false);
		rnStrtLt.setDisabledTextColor(Color.WHITE);
		rnStrtLt.setText("Runway Start Lat:");
		rnStrtLt.setFont(new Font(rnStrtLt.getFont().getName(),Font.PLAIN,12));
		rnStrtLt.setEditable(false);
		rnStrtLt.setEnabled(false);
		runSt.add(rnStrtLt);

		JTextField rnStrtLng = new JTextField();
		rnStrtLng.setOpaque(false);
		rnStrtLng.setDisabledTextColor(Color.WHITE);
		rnStrtLng.setText("Runway Start Long:");
		rnStrtLng.setFont(new Font(rnStrtLng.getFont().getName(),Font.PLAIN,12));
		rnStrtLng.setEditable(false);
		rnStrtLng.setEnabled(false);
		runSt.add(rnStrtLng);

		runInputs.add(runSt);

		JPanel runStTxt = new JPanel();
		runStTxt.setBackground(new Color(51,81,112));
		runStTxt.setDoubleBuffered(true);
		GridLayout gridLayoutStTxt = new GridLayout(1,2);
		gridLayout.setHgap(2);
		runStTxt.setLayout(gridLayoutStTxt);

		JFormattedTextField runStartLat = new JFormattedTextField(NumberFormat.getNumberInstance());
		runStartLat.setFont(new Font(rnName.getFont().getName(),Font.PLAIN,12));
		runStTxt.add(runStartLat);

		JFormattedTextField runStartLong = new JFormattedTextField(NumberFormat.getNumberInstance());
		runStartLong.setFont(new Font(rnName.getFont().getName(),Font.PLAIN,12));
		runStTxt.add(runStartLong);

		runInputs.add(runStTxt);

		JPanel runEd = new JPanel();
		runEd.setBackground(new Color(51,81,112));
		runEd.setDoubleBuffered(true);
		GridLayout gridLayoutEd = new GridLayout(1,2);
		gridLayout.setHgap(2);
		runEd.setLayout(gridLayoutEd);

		JTextField rnEdLt = new JTextField();
		rnEdLt.setOpaque(false);
		rnEdLt.setDisabledTextColor(Color.WHITE);
		rnEdLt.setText("Length (ft)");
		rnEdLt.setFont(new Font(rnName.getFont().getName(),Font.PLAIN,12));
		rnEdLt.setEditable(false);
		rnEdLt.setEnabled(false);
		runEd.add(rnEdLt);

		JTextField rnEdLng = new JTextField();
		rnEdLng.setOpaque(false);
		rnEdLng.setDisabledTextColor(Color.WHITE);
		rnEdLng.setFont(new Font(rnName.getFont().getName(),Font.PLAIN,12));
		rnEdLng.setText("Heading (degs)");
		rnEdLng.setEditable(false);
		rnEdLng.setEnabled(false);
		runEd.add(rnEdLng);

		runInputs.add(runEd);

		JPanel runEdTxt = new JPanel();
		runEdTxt.setBackground(new Color(51,81,112));
		runEdTxt.setDoubleBuffered(true);
		GridLayout gridLayoutEdTxt = new GridLayout(1,2);
		gridLayout.setHgap(2);
		runEdTxt.setLayout(gridLayoutEdTxt);

		JFormattedTextField runEndLat = new JFormattedTextField(NumberFormat.getNumberInstance());
		runEndLat.setFont(new Font(rnName.getFont().getName(),Font.PLAIN,12));
		runEdTxt.add(runEndLat);

		JFormattedTextField runEndLong = new JFormattedTextField(NumberFormat.getNumberInstance());
		runEndLat.setFont(new Font(rnName.getFont().getName(),Font.PLAIN,12));
		runEdTxt.add(runEndLong);

		runInputs.add(runEdTxt);

		return runInputs;
	}
}
