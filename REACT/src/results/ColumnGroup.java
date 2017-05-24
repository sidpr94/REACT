/*
 * 
 */
package results;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

// TODO: Auto-generated Javadoc
/**
 * The Class ColumnGroup.
 */
public class ColumnGroup {

    /** The renderer. */
    protected TableCellRenderer renderer;

    /** The columns. */
    protected List<TableColumn> columns;
    
    /** The groups. */
    protected List<ColumnGroup> groups;

    /** The text. */
    protected String text;
    
    /** The margin. */
    protected int margin = 0;

    /**
     * Instantiates a new column group.
     *
     * @param text the text
     */
    public ColumnGroup(String text) {
        this(text, null);
    }

    /**
     * Instantiates a new column group.
     *
     * @param text the text
     * @param renderer the renderer
     */
    public ColumnGroup(String text, TableCellRenderer renderer) {
        this.text = text;
        this.renderer = renderer;
        this.columns = new ArrayList<TableColumn>();
        this.groups = new ArrayList<ColumnGroup>();
    }

    /**
     * Adds the.
     *
     * @param column the column
     */
    public void add(TableColumn column) {
        columns.add(column);
    }

    /**
     * Adds the.
     *
     * @param group the group
     */
    public void add(ColumnGroup group) {
        groups.add(group);
    }

    /**
     * Gets the column groups.
     *
     * @param column            TableColumn
     * @return the column groups
     */
    public List<ColumnGroup> getColumnGroups(TableColumn column) {
        if (!contains(column)) {
            return Collections.emptyList();
        }
        List<ColumnGroup> result = new ArrayList<ColumnGroup>();
        result.add(this);
        if (columns.contains(column)) {
            return result;
        }
        for (ColumnGroup columnGroup : groups) {
            result.addAll(columnGroup.getColumnGroups(column));
        }
        return result;
    }

    /**
     * Contains.
     *
     * @param column the column
     * @return true, if successful
     */
    private boolean contains(TableColumn column) {
        if (columns.contains(column)) {
            return true;
        }
        for (ColumnGroup group : groups) {
            if (group.contains(column)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the header renderer.
     *
     * @return the header renderer
     */
    public TableCellRenderer getHeaderRenderer() {
        return renderer;
    }

    /**
     * Sets the header renderer.
     *
     * @param renderer the new header renderer
     */
    public void setHeaderRenderer(TableCellRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Gets the header value.
     *
     * @return the header value
     */
    public String getHeaderValue() {
        return text;
    }

    /**
     * Gets the size.
     *
     * @param table the table
     * @return the size
     */
    public Dimension getSize(JTable table) {
        TableCellRenderer renderer = this.renderer;
        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(table, getHeaderValue() == null || getHeaderValue().trim().isEmpty() ? " "
                : getHeaderValue(), false, false, -1, -1);
        int height = comp.getPreferredSize().height;
        int width = 0;
        for (ColumnGroup columnGroup : groups) {
            width += columnGroup.getSize(table).width;
        }
        for (TableColumn tableColumn : columns) {
            width += tableColumn.getWidth();
            width += margin;
        }
        return new Dimension(width, height);
    }

    /**
     * Sets the column margin.
     *
     * @param margin the new column margin
     */
    public void setColumnMargin(int margin) {
        this.margin = margin;
        for (ColumnGroup columnGroup : groups) {
            columnGroup.setColumnMargin(margin);
        }
    }

}
