/*
 * 
 */
package results;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

// TODO: Auto-generated Javadoc
/**
 * The Class ResultsLegend creates the legend for the results pane using a similar technique to the main map.
 * @author Sidharth Prem
 * @see results.CompareContourMap
 */
public class ResultsLegend {
	
	/** The width of the screen. */
	int width;
	
	/** The height of the screen. */
	int height;
	
	/** The color of each legend entry. */
	Color bg;
	
	/** The text information for each legend entry. */
	String text;
	
	/**
	 * Instantiates a new results legend.
	 *
	 * @param w the w
	 * @param h the h
	 */
	public ResultsLegend(int w, int h){
		this.width = w;
		this.height = h;
	}
	
	/**
	 * Creates the legend for the results tab.
	 *
	 * @return the j panel
	 */
	public JPanel createLegend(){
		final JPanel legendPanel = new JPanel(new GridLayout(9,1){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension preferredLayoutSize(Container target){
				Dimension sd = super.preferredLayoutSize(target);
				sd.width = width/8;
				return sd;
			}
		});
		legendPanel.setBorder(new LineBorder(Color.BLACK,5,false));
		legendPanel.setOpaque(false);
		legendPanel.setEnabled(false);
		
		JTextField txtTitle = new JTextField();
		txtTitle.setText("Legend");
		txtTitle.setEnabled(false);
		txtTitle.setEditable(false);
		txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitle.setFont(new Font(txtTitle.getFont().getName(),Font.BOLD,16));
		txtTitle.setBackground(new Color(38,38,38));
		txtTitle.setDisabledTextColor(Color.WHITE);
		txtTitle.setEnabled(false);
		txtTitle.setHighlighter(null);
		txtTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
		legendPanel.add(txtTitle);
		
		JTextField ContourTitle = new JTextField();
		ContourTitle.setText("Baseline Noise Contour");
		ContourTitle.setEditable(false);
		ContourTitle.setHorizontalAlignment(SwingConstants.CENTER);
		ContourTitle.setFont(new Font(ContourTitle.getFont().getName(),Font.BOLD,14));
		ContourTitle.setBackground(Color.WHITE);
		ContourTitle.setForeground(Color.BLACK);
		ContourTitle.setBorder(BorderFactory.createEmptyBorder());
		legendPanel.add(ContourTitle);
		
		for(int i = 55; i < 70; i = i + 5){
			JPanel colorPanel = new JPanel();
			colorPanel.setLayout(new GridLayout(1,2));
			JPanel dashedBox = new JPanel(new GridLayout(1,3));
			JPanel DNLColorBox = new JPanel();
			DNLColorBox.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
			if(i == 55){
				bg = new Color(56,168,0);
			} else if (i == 60){
				bg = new Color(0,92,230);
			}else if( i == 65){
				bg = new Color(255,0,0);
			}
			DNLColorBox.setBackground(bg);
			dashedBox.add(DNLColorBox);
			colorPanel.add(dashedBox);

			JTextField DNLDescription = new JTextField();
			DNLDescription.setText(i+" DNL");
			DNLDescription.setEditable(false);
			DNLDescription.setHorizontalAlignment(SwingConstants.CENTER);
			DNLDescription.setFont(new Font(DNLDescription.getFont().getName(),Font.PLAIN,14));
			DNLDescription.setBackground(Color.WHITE);
			DNLDescription.setForeground(Color.BLACK);
			DNLDescription.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
			colorPanel.add(DNLDescription);
			legendPanel.add(colorPanel);
		}
		
		JTextField ContourTitle1 = new JTextField();
		ContourTitle1.setText("Scenario Noise Contour");
		ContourTitle1.setEditable(false);
		ContourTitle1.setHorizontalAlignment(SwingConstants.CENTER);
		ContourTitle1.setFont(new Font(ContourTitle.getFont().getName(),Font.BOLD,14));
		ContourTitle1.setBackground(Color.WHITE);
		ContourTitle1.setForeground(Color.BLACK);
		ContourTitle1.setBorder(BorderFactory.createEmptyBorder());
		legendPanel.add(ContourTitle1);
		
		for(int i = 55; i < 70; i = i + 5){
			JPanel colorPanel = new JPanel();
			colorPanel.setLayout(new GridLayout(1,2));
			JPanel dashedBox = new JPanel(new GridLayout(1,3));
			JPanel DNLColorBox = new JPanel();
			DNLColorBox.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 5, Color.WHITE));
			JPanel DNLColorBox1 = new JPanel();
			DNLColorBox1.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 5, Color.WHITE));
			JPanel DNLColorBox2 = new JPanel();
			DNLColorBox2.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 5, Color.WHITE));
			if(i == 55){
				bg = new Color(56,168,0);
			} else if (i == 60){
				bg = new Color(0,92,230);
			}else if( i == 65){
				bg = new Color(255,0,0);
			}
			DNLColorBox.setBackground(bg);
			DNLColorBox1.setBackground(bg);
			DNLColorBox2.setBackground(bg);
			dashedBox.add(DNLColorBox);
			dashedBox.add(DNLColorBox1);
			dashedBox.add(DNLColorBox2);
			colorPanel.add(dashedBox);

			JTextField DNLDescription = new JTextField();
			DNLDescription.setText(i+" DNL");
			DNLDescription.setEditable(false);
			DNLDescription.setHorizontalAlignment(SwingConstants.CENTER);
			DNLDescription.setFont(new Font(DNLDescription.getFont().getName(),Font.PLAIN,14));
			DNLDescription.setBackground(Color.WHITE);
			DNLDescription.setForeground(Color.BLACK);
			DNLDescription.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
			colorPanel.add(DNLDescription);
			legendPanel.add(colorPanel);
		}
			
			return legendPanel;
	}
}
