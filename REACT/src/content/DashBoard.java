package content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.esri.map.JMap;
import com.esri.toolkit.JLayerList;

import angim.CalculateNoise;
import angim.ResetToBaseline;
import scenarioDev.density.DensityControl;
import scenarioDev.fleet.FleetTechnology;
import scenarioDev.runway.RunwayEnhancement;
import scenarioDev.track.TrackFlexibility;

public class DashBoard {
	JMap map;
	JMap compare;
	JLayerList layerList;
	JTable table;
	public DashBoard(){}
	public DashBoard(JMap jMap,JLayerList list,JMap compare,JTable table){
		this.map = jMap;
		this.layerList = list;
		this.compare = compare;
		this.table = table;
	}
	public JPanel getDashboard() throws IOException{
		JPanel inputPane = new JPanel();
		inputPane.setLayout(new GridBagLayout());
		inputPane.setBackground(new Color(0,37,76));
		inputPane.setDoubleBuffered(true);
		inputPane.setBorder(new LineBorder(Color.BLACK, 5, false));

		JTextField inputTitle = new JTextField();
		inputTitle.setText("Dashboard");
		inputTitle.setFont(new Font(inputTitle.getFont().getName(),Font.BOLD,14));
		inputTitle.setPreferredSize(new Dimension(300,50));
		inputTitle.setOpaque(false);
		inputTitle.setForeground(new Color(238,178,17));
		inputTitle.setDisabledTextColor(new Color(238,178,17));
		inputTitle.setEditable(false);
		inputTitle.setEnabled(false);
		inputTitle.setHorizontalAlignment(SwingConstants.CENTER);
		inputTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));

		JTextField opForecastTitle = new JTextField();
		opForecastTitle.setText("Operations Forecasting Scenarios");
		opForecastTitle.setEditable(false);
		opForecastTitle.setEnabled(false);
		opForecastTitle.setHorizontalAlignment(SwingConstants.CENTER);
		opForecastTitle.setFont(new Font(opForecastTitle.getFont().getName(),Font.PLAIN,14));
		opForecastTitle.setPreferredSize(new Dimension(300,65));
		opForecastTitle.setOpaque(false);
		opForecastTitle.setDisabledTextColor(new Color(238,178,17));
		opForecastTitle.setBorder(BorderFactory.createEmptyBorder(15,0,5,0));

		JTextField popForecastTitle = new JTextField();
		popForecastTitle.setText("Population Movement Scenarios");
		popForecastTitle.setEnabled(false);
		popForecastTitle.setEditable(false);
		popForecastTitle.setHorizontalAlignment(SwingConstants.CENTER);
		popForecastTitle.setFont(new Font(popForecastTitle.getFont().getName(),Font.PLAIN,14));
		popForecastTitle.setPreferredSize(new Dimension(300,50));
		popForecastTitle.setOpaque(false);
		popForecastTitle.setDisabledTextColor(new Color(238,178,17));
		popForecastTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

		JComboBox<String> opForecast = new JComboBox<>();
		opForecast.setPreferredSize(new Dimension(300,40));
		opForecast.setModel(new DefaultComboBoxModel<>(new String[]{"2015 Operations","2020 Below Nominal TAF","2020 Nominal TAF","2020 Above Nominal TAF","2030 Below Nominal TAF","2030 Nominal TAF","2030 Above Nominal TAF"}));
		((JLabel)opForecast.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		JComboBox<String> popForecast = new JComboBox<>();
		popForecast.setPreferredSize(new Dimension(300,40));
		((JLabel)popForecast.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		popForecast.setModel(new DefaultComboBoxModel<>(new String[]{"2010 Year", "2020 Year","2030 Year"}));

		JTextField WhatifTitle = new JTextField();
		WhatifTitle.setText("Scenario Development");
		WhatifTitle.setEnabled(false);
		WhatifTitle.setEditable(false);
		WhatifTitle.setHorizontalAlignment(SwingConstants.CENTER);
		WhatifTitle.setFont(new Font(WhatifTitle.getFont().getName(),Font.BOLD,14));
		WhatifTitle.setPreferredSize(new Dimension(300,55));
		WhatifTitle.setOpaque(false);
		WhatifTitle.setDisabledTextColor(new Color(238,178,17));
		WhatifTitle.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.BLACK));	
		
		JButton resetTech = new JButton();

		RunwayEnhancement run = new RunwayEnhancement();
		DensityControl land = new DensityControl(map);
		TrackFlexibility track = new TrackFlexibility(map);
		FleetTechnology fleet = new FleetTechnology(resetTech);

		ScenarioPane sPane = new ScenarioPane(run.getRun(),land.getDensity(),track.getTrack(),fleet.getFleet());
		JTabbedPane sP = sPane.createScenarioPane();

		JButton reset = new JButton("Reset to Baseline");
		reset.setPreferredSize(new Dimension(300,50));
		reset.setHorizontalAlignment(SwingConstants.CENTER);
		reset.setFont(new Font(reset.getFont().getName(),Font.BOLD,14));
		reset.addActionListener(new ResetToBaseline(opForecast,map));
		
		JButton calculate = new JButton();
		calculate.setText("Calculate Noise");
		calculate.setPreferredSize(new Dimension(300,50));
		calculate.setHorizontalAlignment(SwingConstants.CENTER);
		calculate.setFont(new Font(calculate.getFont().getFontName(),Font.BOLD,14));
		calculate.addActionListener(new CalculateNoise(calculate,opForecast,popForecast,sP,map,compare,table,reset,resetTech));

		URL url = this.getClass().getClassLoader().getResource("Files/Logos/ASDLlogo.png");
		BufferedImage myPicture = ImageIO.read(new File(url.getPath()));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture.getScaledInstance(300, 50, Image.SCALE_FAST)));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		inputPane.add(inputTitle, c);

		GridBagConstraints d = new GridBagConstraints();
		d.fill = GridBagConstraints.HORIZONTAL;
		d.anchor = GridBagConstraints.PAGE_START;
		d.weighty = 0;
		d.gridx = 0;
		d.gridy = 1;
		inputPane.add(popForecastTitle, d);

		GridBagConstraints e = new GridBagConstraints();
		e.fill = GridBagConstraints.HORIZONTAL;
		e.anchor = GridBagConstraints.PAGE_START;
		e.weighty = 0;
		e.gridx = 0;
		e.gridy = 2;
		inputPane.add(popForecast, e);

		GridBagConstraints f = new GridBagConstraints();
		f.fill = GridBagConstraints.HORIZONTAL;
		f.anchor = GridBagConstraints.PAGE_START;
		f.weighty = 0;
		f.gridx = 0;
		f.gridy = 3;
		inputPane.add(opForecastTitle, f);

		GridBagConstraints g = new GridBagConstraints();
		g.fill = GridBagConstraints.HORIZONTAL;
		g.anchor = GridBagConstraints.PAGE_START;
		g.weighty = 0;
		g.gridx = 0;
		g.gridy = 4;
		inputPane.add(opForecast, g);

		GridBagConstraints h = new GridBagConstraints();
		h.fill = GridBagConstraints.HORIZONTAL;
		h.anchor = GridBagConstraints.PAGE_START;
		h.weighty = 0;
		h.gridx = 0;
		h.gridy = 5;
		inputPane.add(WhatifTitle, h);

		GridBagConstraints i = new GridBagConstraints();
		i.fill = GridBagConstraints.HORIZONTAL;
		i.anchor = GridBagConstraints.PAGE_START;
		i.weighty = 0;
		i.gridx = 0;
		i.gridy = 6;
		JPanel hidePane = new JPanel();
		hidePane.setLayout(new BoxLayout(hidePane, BoxLayout.Y_AXIS));
		hidePane.setOpaque(false);
		hidePane.setDoubleBuffered(true);
		hidePane.setBorder(new LineBorder(Color.BLACK, 1, false));
		//hidePane.add(sbx);
		hidePane.add(sP);
		inputPane.add(hidePane, i);
		
		GridBagConstraints o = new GridBagConstraints();
		o.fill = GridBagConstraints.HORIZONTAL;
		o.anchor = GridBagConstraints.PAGE_START;
		o.weighty = 1;
		o.gridx = 0;
		o.gridy = 7;
		o.insets = new Insets(5, 0, 0, 0);
		inputPane.add(calculate, o);
		
		GridBagConstraints oo = new GridBagConstraints();
		oo.fill = GridBagConstraints.HORIZONTAL;
		oo.anchor = GridBagConstraints.PAGE_START;
		oo.weighty = 50;
		oo.gridx = 0;
		oo.gridy = 8;
		oo.insets = new Insets(0, 0, 0, 0);
		inputPane.add(reset, oo);

		GridBagConstraints r = new GridBagConstraints();
		r.fill = GridBagConstraints.HORIZONTAL;
		r.anchor = GridBagConstraints.PAGE_END;
		r.weighty = 1;
		r.gridx = 0;
		r.gridy = 9;
		inputPane.add(picLabel,r);
		inputPane.add(Box.createVerticalGlue(),r);

		return inputPane;
	}
}
