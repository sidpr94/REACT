package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class DashBoard {
	public DashBoard(){}
	public JPanel getDashboard(){
		JTextField inputTitle = new JTextField();
		inputTitle.setText("Dashboard");
		inputTitle.setHorizontalAlignment(SwingConstants.CENTER);
		inputTitle.setFont(new Font(inputTitle.getFont().getName(),Font.BOLD,14));
		inputTitle.setMaximumSize(new Dimension(250,30));
		inputTitle.setBackground(new Color(0,0,0,100));
		inputTitle.setEditable(false);
		inputTitle.setEnabled(false);
		inputTitle.setForeground(Color.WHITE);
		inputTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));

		JTextField opForecastTitle = new JTextField();
		opForecastTitle.setText("Operations Forecasting Scenarios");
		opForecastTitle.setHorizontalAlignment(SwingConstants.CENTER);
		opForecastTitle.setFont(new Font(opForecastTitle.getFont().getName(),Font.PLAIN,14));
		opForecastTitle.setMaximumSize(new Dimension(250,45));
		opForecastTitle.setEditable(false);
		opForecastTitle.setEnabled(false);
		opForecastTitle.setBackground(new Color(0,0,0,100));
		opForecastTitle.setForeground(Color.WHITE);
		opForecastTitle.setBorder(BorderFactory.createEmptyBorder(15,0,5,0));

		JTextField popForecastTitle = new JTextField();
		popForecastTitle.setText("Population Forecasting Scenarios");
		popForecastTitle.setHorizontalAlignment(SwingConstants.CENTER);
		popForecastTitle.setFont(new Font(popForecastTitle.getFont().getName(),Font.PLAIN,14));
		popForecastTitle.setMaximumSize(new Dimension(250,30));
		popForecastTitle.setEditable(false);
		popForecastTitle.setEnabled(false);
		popForecastTitle.setBackground(new Color(0,0,0,100));
		popForecastTitle.setForeground(Color.WHITE);
		popForecastTitle.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

		JComboBox<String> opForecast = new JComboBox<>();
		opForecast.setMaximumSize(new Dimension(250,20));
		opForecast.setModel(new DefaultComboBoxModel<>(new String[]{"Below Nominal TAF","Nominal TAF","Above Nominal TAF"}));
		JComboBox<String> popForecast = new JComboBox<>();
		popForecast.setMaximumSize(new Dimension(250,20));
		popForecast.setModel(new DefaultComboBoxModel<>(new String[]{"2020 Forecast","2030 Forecast"}));

		JTextField WhatifTitle = new JTextField();
		WhatifTitle.setText("What-If Scenarios");
		WhatifTitle.setHorizontalAlignment(SwingConstants.CENTER);
		WhatifTitle.setFont(new Font(WhatifTitle.getFont().getName(),Font.BOLD,14));
		WhatifTitle.setMaximumSize(new Dimension(250,45));
		WhatifTitle.setBackground(new Color(0,0,0,100));
		WhatifTitle.setEditable(false);
		WhatifTitle.setEnabled(false);
		WhatifTitle.setForeground(Color.WHITE);
		WhatifTitle.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.BLACK));

		SwitchBox sbx = new SwitchBox("On","Off");

		JPanel inputPane = new JPanel();
		BoxLayout boxLayout = new BoxLayout(inputPane, BoxLayout.Y_AXIS);
		inputPane.setLayout(boxLayout);
		//inputPane.setPreferredSize(new Dimension(250,300));
		inputPane.setSize(250,300);
		inputPane.setBackground(new Color(0,0,0,100));
		inputPane.setDoubleBuffered(true);
		inputPane.setBorder(new LineBorder(Color.BLACK, 5, false));
		inputPane.add(inputTitle);
		inputPane.add(popForecastTitle);
		inputPane.add(popForecast);
		inputPane.add(opForecastTitle);
		inputPane.add(opForecast);
		inputPane.add(WhatifTitle);
		inputPane.add(sbx);
		return inputPane;
	}
}
