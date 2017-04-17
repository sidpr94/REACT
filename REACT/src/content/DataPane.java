package content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import database.ReadFleetData;
import database.TableModel;

public class DataPane {
	String[] colNames = {"Aircraft",
			"Track Name",
			"Runway",
			"Operation",
			"Daily Day-Time Operations",
			"Daily Night-Time Operations",
			"Daily Total Operations"};

	String[] text = {"Aircraft",
			"Track ID",
			"Runway",
			"Operation"};

	JTable table;
	JTextField search = new JTextField();
	JComboBox<String> filters = new JComboBox<>();
	TableRowSorter<TableModel> sorter;

	public DataPane(){}

	public JPanel getfinePane(){
		JPanel panel = new JPanel();
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
		panel.add(getPane(),b);

		return panel;
	}
	public JScrollPane getPane(){
		ReadFleetData rdData = new ReadFleetData();
		Object[][] data = rdData.getData();
		JTable table = new JTable();
		TableModel model = new TableModel(colNames,data);
		table.setModel(model);
		sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		table.setFont(new Font("Dialog",Font.BOLD,20));
		table.getTableHeader().setFont(new Font("Dialog",Font.BOLD,20));
		table.getTableHeader().setBackground(new Color(0,37,76));
		table.getTableHeader().setForeground(new Color(238,178,17));
		table.getTableHeader().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
		table.setRowHeight(table.getRowHeight()+table.getFont().getSize());
		table.setBackground(new Color(214, 217, 223));
		table.setForeground(new Color(0,37,76));
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for(int column = 0; column < table.getColumnCount(); column++){
			table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
		}
		search.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				filter();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				filter();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				filter();
			}

			public void filter(){
				RowFilter<TableModel,Object> rf = null;
				try {
					if((String)filters.getSelectedItem() == text[0]){
						rf = RowFilter.regexFilter(search.getText(), 0);
					}else if((String)filters.getSelectedItem() == text[1]){
						rf = RowFilter.regexFilter(search.getText(), 1);
					}else if((String)filters.getSelectedItem() == text[2]){
						rf = RowFilter.regexFilter(search.getText(), 2);
					}else if((String)filters.getSelectedItem() == text[3]){
						rf = RowFilter.regexFilter(search.getText(), 3);
					}
				} catch (PatternSyntaxException pse) {
					System.err.println("Bad regex pattern");
				}
				sorter.setRowFilter(rf);
			}

		});
		JScrollPane scroll = new JScrollPane();
		scroll.add(table);
		scroll.setViewportView(table);
		table.setFillsViewportHeight(true);
		return scroll;
	}

	public JPanel getPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(new Color(0,37,76));
		search.setPreferredSize(new Dimension(200,30));

		JTextField des = new JTextField();
		des.setText("Filter table by: ");
		des.setFont(new Font(des.getFont().getName(),Font.BOLD,18));
		des.setOpaque(false);
		des.setDisabledTextColor(new Color(238,178,17));
		des.setEditable(false);
		des.setEnabled(false);
		des.setHorizontalAlignment(SwingConstants.RIGHT);
		des.setBorder(BorderFactory.createEmptyBorder());

		filters.setModel(new DefaultComboBoxModel<>(text));
		filters.setFont(new Font(search.getFont().getName(),Font.PLAIN,16));
		filters.setPreferredSize(new Dimension(200,30));

		JTextField inputTitle = new JTextField();
		inputTitle.setText("FLIGHT SCHEDULE");
		inputTitle.setFont(new Font(search.getFont().getName(),Font.BOLD,38));
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

		GridBagConstraints f = new GridBagConstraints();
		f.gridx = 2;
		f.gridy = 0;
		f.weightx = 0;
		f.weighty = 5;
		f.insets = new Insets(10, 0, 10, 10);
		panel.add(des, f);

		GridBagConstraints e = new GridBagConstraints();
		e.gridx = 3;
		e.gridy = 0;
		e.weightx = 0;
		e.weighty = 5;
		e.insets = new Insets(10, 0, 10, 10);
		panel.add(filters, e);

		GridBagConstraints d = new GridBagConstraints();
		d.gridx = 4;
		d.gridy = 0;
		d.weightx = 0;
		d.weighty = 5;
		d.insets = new Insets(10, 0, 10, 10);
		panel.add(search,d);

		return panel;
	}	

}
