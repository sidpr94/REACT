/*
 * 
 */
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;

import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;

import angim.RunANGIM;
import angim.UpdateContour;

// TODO: Auto-generated Javadoc
/**
 * The Class ResultsTable creates the table containing the results from ANGIM including:
 * population exposed, contour area, fuel and emissions information.
 * The table is created similar to the database table, it contains a title block and the table itself.
 * @author Sidharth Prem
 * @see results.ResultPane
 */
public class ResultsTable {
	
	JMap compare;
	
	ScenarioSummary summary = new ScenarioSummary();
	/**
	 * Instantiates a new results table.
	 */
	public ResultsTable(JMap map){
		this.compare = map;
	}

	/**
	 * Gets the final pane containing both the table and the title block.
	 *
	 * @param table the results table
	 * @return the final pane
	 * @throws BadLocationException 
	 */
	public JPanel getfinePane(JTable table) throws BadLocationException{
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
		panel.add(getPanel("RESULTS"),a);

		GridBagConstraints b = new GridBagConstraints();
		b.gridx = 0;
		b.gridy = 1;
		b.fill = GridBagConstraints.HORIZONTAL;
		b.anchor = GridBagConstraints.FIRST_LINE_END;
		b.weighty = 100;
		panel.add(getPane(table),b);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 5;
		c.weighty = 50000;
		panel.add(getPanel("SCENARIO SUMMARY"),c);
		
		ScenarioSummary sum = new ScenarioSummary();
		
		GridBagConstraints d = new GridBagConstraints();
		d.gridx = 0;
		d.gridy = 3;
		d.fill = GridBagConstraints.BOTH;
		d.anchor = GridBagConstraints.FIRST_LINE_START;
		d.weightx = 5;
		d.weighty = 10000000;
		panel.add(sum.getScenarioSummary(),d);
		
		/*
		GridBagConstraints e = new GridBagConstraints();
		e.gridx = 0;
		e.gridy = 4;
		e.fill = GridBagConstraints.HORIZONTAL;
		e.anchor = GridBagConstraints.FIRST_LINE_START;
		e.weightx = 0;
		e.weighty = 100;
		panel.add(Box.createVerticalGlue(),d);
		*/


		return panel;
	}
	
	/**
	 * Gets the table.
	 * MCI specific need to fix
	 * @return the table
	 */
	public JTable getTable(){
		DefaultTableModel dm = new DefaultTableModel(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
			
		};
		dm.setDataVector(new Object[][]{
			//{"Baseline","1966","27","4","16.620","5.261","1.996","-","-",'-'}
			{"Baseline","283698","52527","1","68.117","29.955","11.112","-","-",'-'}
		},
				new Object[]{"Scenario","55db DNL","60db DNL","65db DNL","55db DNL","60db DNL","65db DNL","Fuel Burn (lb)","CO2 (lb)","NOx (lb)"});
		JTable table = new JTable( dm ) {
			/**
			 * Allows the table to have groupable columns
			 */
			private static final long serialVersionUID = 1L;

			@Override
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
		RunANGIM addBase = new RunANGIM();
		addBase.addBaseline();
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				if(!arg0.getValueIsAdjusting()){
					try {
						if(compare.getLayers().size() == 4){
							GraphicsLayer noise = (GraphicsLayer) compare.getLayers().get(3);
							int[] ids = noise.getGraphicIDs();
							for(int i = 0; i < ids.length; i++){
								if(table.getSelectedRow() != 0){
									noise.updateGraphic(ids[i], UpdateContour.scenarioGraphics.get(table.getSelectedRow()-1).get(i));
							
								}
							}
						}
						summary.showSummary(table.getValueAt(table.getSelectedRow(), 0).toString(),table.getSelectedRow());
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		});
		return table;
	}
	
	/**
	 * Gets the scroll pane containing the table
	 *
	 * @param table the results table
	 * @return the scroll pane
	 */
	public JScrollPane getPane(JTable table){
		JScrollPane scroll = new JScrollPane(table);
		scroll.setViewportView(table);
		scroll.setBackground(new Color(0,37,76));
		return scroll;
	}

	/**
	 * Gets the title block.
	 *
	 * @return the title block
	 */
	public JPanel getPanel(String text){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(new Color(0,37,76));

		JTextField inputTitle = new JTextField();
		inputTitle.setText(text);
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
