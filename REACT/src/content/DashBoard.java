package content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.esri.map.JMap;
import com.esri.toolkit.JLayerList;

import scenarioDev.DensityControl;
import scenarioDev.FleetTechnology;
import scenarioDev.RunwayEnhancement;
import scenarioDev.TrackFlexibility;

public class DashBoard {
	JMap map;
	JLayerList layerList;
	public DashBoard(){}
	public DashBoard(JMap jMap,JLayerList list){
		this.map = jMap;
		this.layerList = list;
	}
	public JPanel getDashboard(){
		JPanel inputPane = new JPanel();
		inputPane.setLayout(new GridBagLayout());
		inputPane.setBackground(new Color(0,0,0,100));
		inputPane.setDoubleBuffered(true);
		inputPane.setBorder(new LineBorder(Color.BLACK, 5, false));

		JTextField inputTitle = new JTextField();
		inputTitle.setText("Dashboard");
		inputTitle.setEditable(false);
		inputTitle.setEnabled(false);
		inputTitle.setHorizontalAlignment(SwingConstants.CENTER);
		inputTitle.setFont(new Font(inputTitle.getFont().getName(),Font.BOLD,14));
		inputTitle.setPreferredSize(new Dimension(300,50));
		inputTitle.setBackground(new Color(0,0,0,100));
		inputTitle.setForeground(Color.WHITE);
		inputTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));

		JTextField opForecastTitle = new JTextField();
		opForecastTitle.setText("Operations Forecasting Scenarios");
		opForecastTitle.setEditable(false);
		opForecastTitle.setEnabled(false);
		opForecastTitle.setHorizontalAlignment(SwingConstants.CENTER);
		opForecastTitle.setFont(new Font(opForecastTitle.getFont().getName(),Font.PLAIN,14));
		opForecastTitle.setPreferredSize(new Dimension(300,65));
		opForecastTitle.setBackground(new Color(0,0,0,100));
		opForecastTitle.setForeground(Color.WHITE);
		opForecastTitle.setEnabled(false);
		opForecastTitle.setBorder(BorderFactory.createEmptyBorder(15,0,5,0));

		JTextField popForecastTitle = new JTextField();
		popForecastTitle.setText("Population Movement Scenarios");
		popForecastTitle.setEnabled(false);
		popForecastTitle.setEditable(false);
		popForecastTitle.setHorizontalAlignment(SwingConstants.CENTER);
		popForecastTitle.setFont(new Font(popForecastTitle.getFont().getName(),Font.PLAIN,14));
		popForecastTitle.setPreferredSize(new Dimension(300,50));
		popForecastTitle.setBackground(new Color(0,0,0,100));
		popForecastTitle.setForeground(Color.WHITE);
		popForecastTitle.setEnabled(false);
		popForecastTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

		JComboBox<String> opForecast = new JComboBox<>();
		opForecast.setPreferredSize(new Dimension(300,40));
		opForecast.setModel(new DefaultComboBoxModel<>(new String[]{"Below Nominal TAF","Nominal TAF","Above Nominal TAF"}));
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
		WhatifTitle.setBackground(new Color(0,0,0,100));
		WhatifTitle.setForeground(Color.WHITE);
		WhatifTitle.setEnabled(false);
		WhatifTitle.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.BLACK));
		
		RunwayEnhancement run = new RunwayEnhancement();
		DensityControl land = new DensityControl();
		TrackFlexibility track = new TrackFlexibility();
		FleetTechnology fleet = new FleetTechnology();
		
		ScenarioPane sPane = new ScenarioPane(run.getRun(),land.getDensity(),track.getTrack(),fleet.getFleet());
		JTabbedPane sP = sPane.createScenarioPane();
		
		JButton calculate = new JButton();
		calculate.setText("Calculate Noise");
		calculate.setPreferredSize(new Dimension(300,60));
		calculate.setHorizontalAlignment(SwingConstants.CENTER);
		calculate.setFont(new Font(calculate.getFont().getFontName(),Font.BOLD,14));
		
		
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
		hidePane.setBackground(new Color(0,0,0,100));
		hidePane.setDoubleBuffered(true);
		hidePane.setBorder(new LineBorder(Color.BLACK, 1, false));
		//hidePane.add(sbx);
		hidePane.add(sP);
		inputPane.add(hidePane, i);
		
		GridBagConstraints r = new GridBagConstraints();
		r.fill = GridBagConstraints.HORIZONTAL;
		r.anchor = GridBagConstraints.PAGE_START;
		r.weighty = 1;
		r.gridx = 0;
		r.gridy = 7;

		GridBagConstraints o = new GridBagConstraints();
		o.fill = GridBagConstraints.HORIZONTAL;
		o.anchor = GridBagConstraints.PAGE_START;
		o.weighty = 1;
		o.gridx = 0;
		o.gridy = 8;
		inputPane.add(Box.createVerticalGlue(),r);
		//inputPane.add(calculate, o);
		
		return inputPane;
	}
}
