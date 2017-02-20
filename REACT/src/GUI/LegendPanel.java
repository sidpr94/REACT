package GUI;
//creates the JPanel containing the legend for the layers on the map
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

public class LegendPanel {
	Color bg;
	String text;
	int width;
	int height;
	Dimension d = new Dimension((int)width/8,(int)height/32);
	Dimension e = new Dimension((int)width/16,(int)height/32);
	public LegendPanel(){}
	public LegendPanel(int w, int h){
		this.width = w;
		this.height = h;
	};
	//creates the final legend calling the get methods
	public JPanel getLegend(){
		final JPanel legendPanel = new JPanel(new GridLayout(16,1){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Dimension preferredLayoutSize(Container target){
				Dimension sd = super.preferredLayoutSize(target);
				sd.width = (int)width/8;
				return sd;
			}
		});
		legendPanel.setBorder(new LineBorder(Color.BLACK,5,false));
		legendPanel.setBackground(new Color(0,0,0,100));
		legendPanel.setEnabled(false);
		
		JTextField txtTitle = new JTextField();
		txtTitle.setText("Legend");
		txtTitle.setEnabled(false);
		txtTitle.setEditable(false);
		txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitle.setFont(new Font(txtTitle.getFont().getName(),Font.BOLD,16));
		txtTitle.setBackground(new Color(0,0,0,100));
		txtTitle.setForeground(Color.WHITE);
		txtTitle.setEnabled(false);
		txtTitle.setHighlighter(null);
		txtTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
		legendPanel.add(txtTitle);
		
		JTextField ContourTitle = new JTextField();
		ContourTitle.setText("Noise Contour");
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
			JPanel DNLColorBox = new JPanel();
			DNLColorBox.setBorder(new LineBorder(Color.WHITE,5,false));
			if(i == 55){
				bg = new Color(56,168,0);
			} else if (i == 60){
				bg = new Color(0,92,230);
			}else if( i == 65){
				bg = new Color(255,0,0);
			}
			DNLColorBox.setBackground(bg);
			colorPanel.add(DNLColorBox);

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
		
		JTextField TrackTitle = new JTextField();
		TrackTitle.setText("Flight Tracks");
		TrackTitle.setEditable(false);
		TrackTitle.setHorizontalAlignment(SwingConstants.CENTER);
		TrackTitle.setFont(new Font(TrackTitle.getFont().getName(),Font.BOLD,14));
		TrackTitle.setBackground(Color.WHITE);
		TrackTitle.setForeground(Color.BLACK);
		TrackTitle.setBorder(BorderFactory.createMatteBorder(3, 0,0, 0, Color.BLACK));
		legendPanel.add(TrackTitle);
		
		for(int i = 1; i < 3; i++){
			JPanel colorPanel = new JPanel();
			colorPanel.setLayout(new GridLayout(1,2));
			JPanel colorBox = new JPanel();
			colorBox.setBorder(new LineBorder(Color.WHITE, 5, false));
			if(i == 1){
				bg = Color.BLUE;
				text = "Approach";
			}else{
				bg = Color.ORANGE;
				text = "Departure";
			}

			colorBox.setBackground(bg);
			colorPanel.add(colorBox);

			JTextField Description = new JTextField();
			Description.setText(text);
			Description.setEditable(false);
			Description.setHorizontalAlignment(SwingConstants.CENTER);
			Description.setFont(new Font(Description.getFont().getName(),Font.PLAIN,14));
			Description.setBackground(Color.WHITE);
			Description.setForeground(Color.BLACK);
			Description.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
			colorPanel.add(Description);
			legendPanel.add(colorPanel);
		}
		
		JTextField PopTitle = new JTextField();
		PopTitle.setText("2010 Population");
		PopTitle.setEditable(false);
		PopTitle.setHorizontalAlignment(SwingConstants.CENTER);
		PopTitle.setFont(new Font(PopTitle.getFont().getName(),Font.BOLD,14));
		PopTitle.setBackground(Color.WHITE);
		PopTitle.setForeground(Color.BLACK);
		PopTitle.setBorder(BorderFactory.createMatteBorder(3, 0,0, 0, Color.BLACK));
		legendPanel.add(PopTitle);
		
		for(int i = 1; i<8; i++){
			JPanel colorPanel = new JPanel();
			colorPanel.setLayout(new GridLayout(1,2));
			JPanel colorBox = new JPanel();
			colorBox.setBorder(new LineBorder(Color.WHITE,5,false));
			colorBox.setPreferredSize(e);
			switch(i){
			case 1: text = "10-14";
			bg = Color.getHSBColor(0,0,1);
			break;
			case 2: text = "15-47";
			bg = Color.getHSBColor(0,0,0.83f);
			break;
			case 3: text = "48-108";
			bg = Color.getHSBColor(0,0,0.66f);
			break;
			case 4: text = "109-235";
			bg = Color.getHSBColor(0,0,0.49f);
			break;
			case 5: text = "236-488";
			bg = Color.getHSBColor(0,0,0.33f);
			break;
			case 6: text = "489-1130";
			bg = Color.getHSBColor(0,0,0.16f);
			break;
			case 7: text = "1131-2362";
			bg = Color.getHSBColor(0,0,0);
			break;
			}
			colorBox.setBackground(bg);
			colorPanel.add(colorBox);

			JTextField Description = new JTextField();
			Description.setText(text);
			Description.setEditable(false);
			Description.setPreferredSize(e);
			Description.setHorizontalAlignment(SwingConstants.CENTER);
			Description.setFont(new Font(Description.getFont().getName(),Font.PLAIN,14));
			Description.setBackground(Color.WHITE);
			Description.setForeground(Color.BLACK);
			Description.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
			colorPanel.add(Description);
			legendPanel.add(colorPanel);
		}
		return legendPanel;
	}
}
