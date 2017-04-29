package results;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class ResultsTable {

	public ResultsTable(){
	}

	public JPanel getfinePane(JTable table){
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1200,1000));
		panel.setLayout(new GridBagLayout());

		GridBagConstraints a = new GridBagConstraints();
		a.gridx = 0;
		a.gridy = 0;
		a.fill = GridBagConstraints.HORIZONTAL;
		a.anchor = GridBagConstraints.FIRST_LINE_START;
		a.weightx = 5;
		a.weighty = 0;
		panel.add(getPanel(),a);

		GridBagConstraints b = new GridBagConstraints();
		b.gridx = 0;
		b.gridy = 1;
		b.fill = GridBagConstraints.BOTH;
		b.anchor = GridBagConstraints.FIRST_LINE_END;
		b.weighty = 500;
		panel.add(getPane(table),b);

		return panel;
	}
	
	public JTable getTable(){
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][]{
			{"Baseline","1029","10","1","16.243","5.146","1.967","-","-",'-'}
		},
				new Object[]{"Scenario","55db DNL","60db DNL","65db DNL","55db DNL","60db DNL","65db DNL","Fuel Burn (lb)","CO2 (lb)","NOx (lb)"});
		JTable table = new JTable( dm ) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected JTableHeader createDefaultTableHeader() {
				return new GroupableTableHeader(columnModel);
			}
		};


		TableColumnModel cm = table.getColumnModel();
		ColumnGroup g_name = new ColumnGroup("People Exposed");
		g_name.add(cm.getColumn(1));
		g_name.add(cm.getColumn(2));
		g_name.add(cm.getColumn(3));
		ColumnGroup g_lang = new ColumnGroup("Contour Area (nmi\u00b2)");
		g_lang.add(cm.getColumn(4));
		g_lang.add(cm.getColumn(5));
		g_lang.add(cm.getColumn(6));

		GroupableTableHeader header = (GroupableTableHeader)table.getTableHeader();
		header.addColumnGroup(g_name);
		header.addColumnGroup(g_lang);

		table.getTableHeader().setBackground(new Color(0,37,76));
		table.getTableHeader().setForeground(new Color(238,178,17));
		table.setBackground(new Color(214, 217, 223));
		table.setForeground(new Color(0,37,76));
		table.setFont(new Font("Dialog",Font.BOLD,20));
		table.getTableHeader().setFont(new Font("Dialog",Font.BOLD,20));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.setRowHeight(table.getRowHeight()+table.getFont().getSize());
		for(int column = 0; column < table.getColumnCount(); column++){
			table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
		}
		table.setFillsViewportHeight(true);
		return table;
	}
	public JScrollPane getPane(JTable table){
		JScrollPane scroll = new JScrollPane(table);
		scroll.setViewportView(table);
		scroll.setBackground(new Color(0,37,76));
		return scroll;
	}

	public JPanel getPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(new Color(0,37,76));

		JTextField inputTitle = new JTextField();
		inputTitle.setText("RESULTS");
		inputTitle.setFont(new Font(inputTitle.getFont().getName(),Font.BOLD,38));
		inputTitle.setOpaque(false);
		inputTitle.setDisabledTextColor(new Color(238,178,17));
		inputTitle.setEditable(false);
		inputTitle.setEnabled(false);
		inputTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		inputTitle.setBorder(BorderFactory.createEmptyBorder());

		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 0;
		g.weighty = 0;
		g.insets = new Insets(10, 30, 10, 0);
		panel.add(inputTitle, g);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 5;
		c.weighty = 0;
		c.insets = new Insets(0, 0, 10, 0);
		panel.add(Box.createHorizontalGlue(), c);

		return panel;
	}	


}
