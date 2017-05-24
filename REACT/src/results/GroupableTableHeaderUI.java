/*
 * 
 */
package results;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupableTableHeaderUI.
 */
public class GroupableTableHeaderUI extends BasicTableHeaderUI {

    /**
     * Gets the header.
     *
     * @return the header
     */
    protected GroupableTableHeader getHeader() {
        return (GroupableTableHeader) header;
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTableHeaderUI#paint(java.awt.Graphics, javax.swing.JComponent)
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        Rectangle clipBounds = g.getClipBounds();
        if (header.getColumnModel().getColumnCount() == 0) {
            return;
        }
        int column = 0;
        Dimension size = header.getSize();
        Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
        Map<ColumnGroup, Rectangle> groupSizeMap = new HashMap<ColumnGroup, Rectangle>();

        for (Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns(); enumeration.hasMoreElements();) {
            cellRect.height = size.height;
            cellRect.y = 0;
            TableColumn aColumn = enumeration.nextElement();
            List<ColumnGroup> groups = getHeader().getColumnGroups(aColumn);
            int groupHeight = 0;
            for (ColumnGroup group : groups) {
                Rectangle groupRect = groupSizeMap.get(group);
                if (groupRect == null) {
                    groupRect = new Rectangle(cellRect);
                    Dimension d = group.getSize(header.getTable());
                    groupRect.width = d.width;
                    groupRect.height = d.height;
                    groupSizeMap.put(group, groupRect);
                }
                paintCell(g, groupRect, group);
                groupHeight += groupRect.height;
                cellRect.height = size.height - groupHeight;
                cellRect.y = groupHeight;
            }
            cellRect.width = aColumn.getWidth();
            if (cellRect.intersects(clipBounds)) {
                paintCell(g, cellRect, column);
            }
            cellRect.x += cellRect.width;
            column++;
        }
    }

    /**
     * Paint cell.
     *
     * @param g the g
     * @param cellRect the cell rect
     * @param columnIndex the column index
     */
    private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
        TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
        TableCellRenderer renderer = aColumn.getHeaderRenderer();
        if (renderer == null) {

            // original
            renderer = getHeader().getDefaultRenderer();

            // modified
        }
        Component c = renderer.getTableCellRendererComponent(header.getTable(), aColumn.getHeaderValue(), false, false,
                -1, columnIndex);

        c.setBackground(new Color(0,37,76));

        rendererPane.paintComponent(g, c, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
    }

    /**
     * Paint cell.
     *
     * @param g the g
     * @param cellRect the cell rect
     * @param cGroup the c group
     */
    private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup) {
        TableCellRenderer renderer = cGroup.getHeaderRenderer();
        if (renderer == null) {
            renderer = getHeader().getDefaultRenderer();
        }

        Component component = renderer.getTableCellRendererComponent(header.getTable(), cGroup.getHeaderValue(), false,
                false, -1, -1);
        rendererPane
                .paintComponent(g, component, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
    }

    /**
     * Gets the header height.
     *
     * @return the header height
     */
    private int getHeaderHeight() {
        int headerHeight = 0;
        TableColumnModel columnModel = header.getColumnModel();
        for (int column = 0; column < columnModel.getColumnCount(); column++) {
            TableColumn aColumn = columnModel.getColumn(column);
            TableCellRenderer renderer = aColumn.getHeaderRenderer();
            if (renderer == null) {
                renderer = getHeader().getDefaultRenderer();
            }

            Component comp = renderer.getTableCellRendererComponent(header.getTable(), aColumn.getHeaderValue(), false,
                    false, -1, column);
            int cHeight = comp.getPreferredSize().height;
            List<ColumnGroup> groups = getHeader().getColumnGroups(aColumn);
            for (ColumnGroup group : groups) {
                cHeight += group.getSize(header.getTable()).height;
            }
            headerHeight = Math.max(headerHeight, cHeight);
        }
        return headerHeight;
    }

    /* (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicTableHeaderUI#getPreferredSize(javax.swing.JComponent)
     */
    @Override
    public Dimension getPreferredSize(JComponent c) {
        int width = 0;
        for (Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns(); enumeration.hasMoreElements();) {
            TableColumn aColumn = enumeration.nextElement();
            width += aColumn.getPreferredWidth();
        }
        return createHeaderSize(width);
    }

    /**
     * Creates the header size.
     *
     * @param width the width
     * @return the dimension
     */
    private Dimension createHeaderSize(int width) {
        TableColumnModel columnModel = header.getColumnModel();
        width += columnModel.getColumnMargin() * columnModel.getColumnCount();
        if (width > Integer.MAX_VALUE) {
            width = Integer.MAX_VALUE;
        }
        return new Dimension(width, getHeaderHeight());
    }

}
