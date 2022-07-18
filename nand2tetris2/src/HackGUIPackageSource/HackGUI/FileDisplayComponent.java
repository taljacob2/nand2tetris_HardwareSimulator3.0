/********************************************************************************
 * The contents of this file are subject to the GNU General Public License      *
 * (GPL) Version 2 or later (the "License"); you may not use this file except   *
 * in compliance with the License. You may obtain a copy of the License at      *
 * http://www.gnu.org/copyleft/gpl.html                                         *
 *                                                                              *
 * Software distributed under the License is distributed on an "AS IS" basis,   *
 * without warranty of any kind, either expressed or implied. See the License   *
 * for the specific language governing rights and limitations under the         *
 * License.                                                                     *
 *                                                                              *
 * This file was originally developed as part of the software suite that        *
 * supports the book "The Elements of Computing Systems" by Nisan and Schocken, *
 * MIT Press 2005. If you modify the contents of this file, please document and *
 * mark your changes clearly, for the benefit of others.                        *
 ********************************************************************************/

package HackGUI;

import Colors.Colors;
import ResolutionUtils.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.*;

/**
 * A component for displaying the contents of a text file in a table format.
 */
public class FileDisplayComponent extends JPanel {

    // The rows of the text file.
    private String[] rows;

    // The table for displaying the rows of the file
    private WideTable fileDisplayTable;

    // The scroll pane for this component.
    private JScrollPane scrollPane;

    // The selected row.
    private int selectedRow = -1;

    // The name of the displayed text file.
    private String fileName;

    /**
     * Constructs a new FileDisplayComponent.
     */
    public FileDisplayComponent() {
        setOpaque(false);//!TODO: SOLUTION : FORCE OPAQUE --- INVISIBILITY!
        rows = new String[0];
        jbInit();
    }

    /**
     * Sets the selected row.
     */
    public void setSelectedRow(int row) {
        selectedRow = row;
        if (selectedRow >= 0)
            Utilities.tableCenterScroll(this, fileDisplayTable, selectedRow);
        repaint();
    }

    /**
     * Deletes the displayed file (from view only).
     */
    public void deleteContent() {
        rows = new String[0];
        fileDisplayTable.revalidate();
        repaint();
    }

    /**
     * Refreshes the display.
     */
    public void refresh() {
        setContents(fileName);
    }

    /**
     * Sets the text file to be displayed.
     */
    public synchronized void setContents(String fileName) {
        this.fileName = fileName;
        BufferedReader reader;
        Vector rowsVector = new Vector();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                rowsVector.addElement(line);
            }
            reader.close();
        } catch (IOException ioe) {
        }
        rows = new String[rowsVector.size()];
        rowsVector.toArray(rows);
        fileDisplayTable.clearSelection();
        fileDisplayTable.revalidate();
        try {
            wait(50);
        } catch (InterruptedException ie) {
        }

        repaint();
    }

    /**
     * Sets the size of this component.
     */
    public void updateSize(int width, int height) {
        setSize(width, height);
        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setSize(width, height);
    }

    // The initialization of this component.
    private void jbInit() {//TODO: sets the Script Table:
        setLayout(null);
        fileDisplayTable = new WideTable(new FileDisplayTableModel(), (int)(1000 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));//TODO: DOESNT DO ANYTHING
        fileDisplayTable.setTableHeader(null);
        fileDisplayTable.setDefaultRenderer(fileDisplayTable.getColumnClass(0), new FileDisplayTableCellRenderer());
        scrollPane = new JScrollPane(fileDisplayTable);

        fileDisplayTable.setRowSelectionAllowed(false);
        fileDisplayTable.setShowHorizontalLines(false);
        fileDisplayTable.setShowVerticalLines(false);

        fileDisplayTable.setFont(Utilities.valueFont);

//        setBorder(BorderFactory.createEtchedBorder());//TODO: check NO BORDER

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//TODO: FILE DISPLAY - DOESN'T DO ANYTHING
        scrollPane.getHorizontalScrollBar().setUnitIncrement(scrollPane.getHorizontalScrollBar().getBlockIncrement());

        //! TODO: SETS THE LOCATION Super JPanel Hosting the File's TEXT !!
        if(ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._1){
            scrollPane.setLocation(BoundaryUtils.X.scriptTextTable_Scale_1, 0);//! TODO: START POSITION OF THE SCRIPT TEXT TABLE. Original values is 0,0
        }
        else if(ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._2){
            scrollPane.setLocation(BoundaryUtils.X.scriptTextTable_Scale_2, 0);//! TODO: START POSITION OF THE SCRIPT TEXT TABLE. Original values is 0,0
        }
        else if(ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._3){
            scrollPane.setLocation(BoundaryUtils.X.scriptTextTable_Scale_3, 0);//! TODO: START POSITION OF THE SCRIPT TEXT TABLE. Original values is 0,0
        }

        scrollPane.setPreferredSize(new Dimension((int)(516 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int)(260 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER))); //TODO  - DOESN'T DO ANYTHING
        scrollPane.setSize((int)(516 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int)(260 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)); //TODO  - DOESN'T DO ANYTHING


        this.add(scrollPane, null);
    }

    // A model for the displayed table
    class FileDisplayTableModel extends AbstractTableModel {

        /**
         * Returns the number of columns.
         */
        public int getColumnCount() {
            return 1;
        }

        /**
         * Returns the number of rows.
         */
        public int getRowCount() {
            return rows.length;
        }

        /**
         * Returns the names of the columns.
         */
        public String getColumnName(int col) {
            return "";
        }

        /**
         * Returns the value at a specific row and column.
         */
        public Object getValueAt(int row, int col) {
            return rows[row];
        }

        /**
         * Returns true of this table cells are editable, false -
         * otherwise.
         */
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }

    //! TODO: THE SCRIPT VIEW !!!

    // A cell renderer for the displayed table.
    class FileDisplayTableCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent
                (JTable table, Object value, boolean selected, boolean focused, int row, int column) {

            setForeground(Colors.getInstance().scriptTableTextForeground);//TODO: CHECK
            setBackground(null);//TODO: DOESN'T DO ANYTHING

            setRenderer(row, column);
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);

            table.setRowHeight(ResolutionUtils.getInstance().ROW_HEIGHT);//! TODO: FORCE SET HERE EACH ROW HEIGHT IN THE SCRIPT TABLE !!!

            setFont(Utilities.statusLineFont);//! TODO: FORCE SET HERE FONT SCRIPT TABLE !!!
            return this;
        }

        public void setRenderer(int row, int column) {

            if (row == selectedRow)
                setBackground(Colors.getInstance().scriptTableBackgroundSelectedRow);//TODO: SETS THE BACKGROUND COLOR OF THE SELECTED ROW IN THE SCRIPT VIEW !!!
            else
                setBackground(Colors.getInstance().scriptTableBackgroundUnselectedRow);//TODO: SETS THE BACKGROUND COLOR OF THE UN-SELECTED ROWS IN THE SCRIPT VIEW !!!
        }
    }
}
