/*
 * 
 */
package results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupableTableHeader.
 */
@SuppressWarnings("serial")
public class GroupableTableHeader extends JTableHeader {

    /** The Constant uiClassID. */
    @SuppressWarnings("unused")
    private static final String uiClassID = "GroupableTableHeaderUI";

    /** The column groups. */
    protected List<ColumnGroup> columnGroups = new ArrayList<ColumnGroup>();

    /**
     * Instantiates a new groupable table header.
     *
     * @param model the model
     */
    public GroupableTableHeader(TableColumnModel model) {
        super(model);
        setUI(new GroupableTableHeaderUI());
        setReorderingAllowed(false);
        // setDefaultRenderer(new MultiLineHeaderRenderer());
    }

    /* (non-Javadoc)
     * @see javax.swing.table.JTableHeader#updateUI()
     */
    @Override
    public void updateUI() {
        setUI(new GroupableTableHeaderUI());
    }

    /* (non-Javadoc)
     * @see javax.swing.table.JTableHeader#setReorderingAllowed(boolean)
     */
    @Override
    public void setReorderingAllowed(boolean b) {
        super.setReorderingAllowed(false);
    }

    /**
     * Adds the column group.
     *
     * @param g the g
     */
    public void addColumnGroup(ColumnGroup g) {
        columnGroups.add(g);
    }

    /**
     * Gets the column groups.
     *
     * @param col the col
     * @return the column groups
     */
    public List<ColumnGroup> getColumnGroups(TableColumn col) {
        for (ColumnGroup group : columnGroups) {
            List<ColumnGroup> groups = group.getColumnGroups(col);
            if (!groups.isEmpty()) {
                return groups;
            }
        }
        return Collections.emptyList();
    }

    /**
     * Sets the column margin.
     */
    public void setColumnMargin() {
        int columnMargin = getColumnModel().getColumnMargin();
        for (ColumnGroup group : columnGroups) {
            group.setColumnMargin(columnMargin);
        }
    }

}
