package GUI;
//creates the JPanel containing the legend for the layers on the map
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class LegendPanel {
	Color bg;
	String text;
	int width;
	int height;
	public LegendPanel(){}
	public LegendPanel(int w, int h){
		this.width = w;
		this.height = h;
	};
	//creates the final legend calling the get methods
	public JPanel getLegend(){
		final JPanel legendPanel = new JPanel();
	    BoxLayout boxLayout = new BoxLayout(legendPanel, BoxLayout.Y_AXIS);
	    legendPanel.setLayout(boxLayout);
	    legendPanel.setSize((int)width/8, (int) ((int) height/1.65));
	    legendPanel.setLocation(width-legendPanel.getWidth()-30,15+(int) (height/32));
	    legendPanel.setBackground(new Color(0,0,0,100));
	    legendPanel.setDoubleBuffered(true);
	    legendPanel.setBorder(new LineBorder(Color.BLACK, 2, false));
	    legendPanel.add(getTitle());
	    legendPanel.add(getNoiseLegend());
	    legendPanel.add(getTrackLegend());
	    legendPanel.add(getPopLegend());
		return legendPanel;
	}
	//gets the legend title
	public JTextField getTitle(){
		JTextField txtTitle = new JTextField();
		txtTitle.setText("Legend");
		txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitle.setFont(new Font(txtTitle.getFont().getName(),Font.BOLD,14));
		txtTitle.setMaximumSize(new Dimension((int)width/8,40));
		txtTitle.setBackground(new Color(0,0,0,100));
		txtTitle.setForeground(Color.WHITE);
		txtTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		return txtTitle;
	}
	//gets the noise part of the legend
	public JPanel getNoiseLegend(){
		final JPanel noisePanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(noisePanel, BoxLayout.Y_AXIS);
		noisePanel.setLayout(boxLayout);
		
		JTextField ContourTitle = new JTextField();
		ContourTitle.setText("Noise Contour");
		ContourTitle.setHorizontalAlignment(SwingConstants.CENTER);
		ContourTitle.setFont(new Font(ContourTitle.getFont().getName(),Font.BOLD,12));
		ContourTitle.setMaximumSize(new Dimension((int)width/8,40));
		ContourTitle.setBackground(Color.WHITE);
		ContourTitle.setForeground(Color.BLACK);
		ContourTitle.setBorder(BorderFactory.createMatteBorder(0,3,0, 3, Color.BLACK));
		
	    final JPanel colorPanel = new JPanel();
	    GridLayout gridLayout = new GridLayout(3,2);
	    gridLayout.setVgap(3);
	    colorPanel.setLayout(gridLayout);
	    colorPanel.setBackground(new Color(255, 255, 255, 255));
	    colorPanel.setDoubleBuffered(true);
	    colorPanel.setBorder(BorderFactory.createMatteBorder(0,3,2, 3, Color.BLACK));
	    
		for(int i = 55; i < 70; i = i + 5){
			JPanel DNLColorBox = new JPanel();
			DNLColorBox.setBorder(new LineBorder(Color.WHITE,5,false));
			DNLColorBox.setMaximumSize(new Dimension((int)width/16,20));
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
		    DNLDescription.setHorizontalAlignment(SwingConstants.CENTER);
		    DNLDescription.setFont(new Font(DNLDescription.getFont().getName(),Font.PLAIN,12));
		    DNLDescription.setMaximumSize(new Dimension((int)width/16,20));
		    DNLDescription.setBackground(Color.WHITE);
		    DNLDescription.setForeground(Color.BLACK);
		    DNLDescription.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		    colorPanel.add(DNLDescription);
		}
		noisePanel.add(ContourTitle);
		noisePanel.add(colorPanel);
		return noisePanel;
		
	}
	//gets the track panel for the legend
	public JPanel getTrackLegend(){
		final JPanel trackPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(trackPanel, BoxLayout.Y_AXIS);
		trackPanel.setLayout(boxLayout);
		
	    JTextField TrackTitle = new JTextField();
	    TrackTitle.setText("Flight Tracks");
	    TrackTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    TrackTitle.setFont(new Font(TrackTitle.getFont().getName(),Font.BOLD,12));
	    TrackTitle.setMaximumSize(new Dimension((int)width/8,40));
	    TrackTitle.setBackground(Color.WHITE);
	    TrackTitle.setForeground(Color.BLACK);
	    TrackTitle.setBorder(BorderFactory.createMatteBorder(0,3,0, 3, Color.BLACK));
	    
	    final JPanel colorPanel = new JPanel();
	    GridLayout gridLayout = new GridLayout(2,2);
	    gridLayout.setVgap(3);
	    colorPanel.setLayout(gridLayout);
	    colorPanel.setBackground(new Color(255, 255, 255, 255));
	    colorPanel.setDoubleBuffered(true);
	    colorPanel.setBorder(BorderFactory.createMatteBorder(0,3,2, 3, Color.BLACK));
	    
	    for(int i = 1; i < 3; i++){
	    	JPanel colorBox = new JPanel();
	    	colorBox.setBorder(new LineBorder(Color.WHITE, 5, false));
	    	colorBox.setMaximumSize(new Dimension((int)width/16,20));
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
	        Description.setHorizontalAlignment(SwingConstants.CENTER);
	        Description.setFont(new Font(Description.getFont().getName(),Font.PLAIN,12));
	        Description.setMaximumSize(new Dimension((int)width/16,20));
	        Description.setBackground(Color.WHITE);
	        Description.setForeground(Color.BLACK);
	        Description.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
	        colorPanel.add(Description);
	    }
	    	
	    trackPanel.add(TrackTitle);
	    trackPanel.add(colorPanel);
	    return trackPanel;
	}
	//gets the population panel for the legend
	public JPanel getPopLegend(){
		final JPanel popPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(popPanel, BoxLayout.Y_AXIS);
		popPanel.setLayout(boxLayout);
		
	    JTextField PopTitle = new JTextField();
	    PopTitle.setText("2010 Population");
	    PopTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    PopTitle.setFont(new Font(PopTitle.getFont().getName(),Font.BOLD,12));
	    PopTitle.setMaximumSize(new Dimension((int)width/8,40));
	    PopTitle.setBackground(Color.WHITE);
	    PopTitle.setForeground(Color.BLACK);
	    PopTitle.setBorder(BorderFactory.createMatteBorder(0,3,0, 3, Color.BLACK));
	
	    final JPanel colorPanel = new JPanel();
	    GridLayout gridLayout = new GridLayout(7,2);
	    gridLayout.setVgap(3);
	    colorPanel.setLayout(gridLayout);
	    colorPanel.setBackground(new Color(255, 255, 255, 255));
	    colorPanel.setDoubleBuffered(true);
	    colorPanel.setBorder(BorderFactory.createMatteBorder(0,3,0, 3, Color.BLACK));
	    
	    for(int i = 1; i<8; i++){
	    	JPanel colorBox = new JPanel();
	    	colorBox.setBorder(new LineBorder(Color.WHITE,5,false));
	    	colorBox.setMaximumSize(new Dimension((int)width/16,20));
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
	        Description.setHorizontalAlignment(SwingConstants.CENTER);
	        Description.setFont(new Font(Description.getFont().getName(),Font.PLAIN,12));
	        Description.setMaximumSize(new Dimension((int)width/16,20));
	        Description.setBackground(Color.WHITE);
	        Description.setForeground(Color.BLACK);
	        Description.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
	        colorPanel.add(Description);
	    }
	    popPanel.add(PopTitle);
	    popPanel.add(colorPanel);
	    return popPanel;
	}
}
