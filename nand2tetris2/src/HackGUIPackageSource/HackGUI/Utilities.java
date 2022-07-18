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

import ResolutionUtils.ResolutionUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

/**
 * A class of utility methods.
 */
public class Utilities {

    /**
     * The directory of the images.
     */
    public static final String imagesDir = "bin/images/";

    // The name of the font for values
    private static final String VALUE_FONT_NAME = "Monospaced";

    // The name of the font for labels
    private static final String LABEL_FONT_NAME = "Dialog";

    //TODO: HERE YOU CHANGE THE FONT SIZES AS YOU WISH:


    /**
     * The regular font for values.
     */
    public static Font valueFont = new FontUIResource(VALUE_FONT_NAME, 0, (int) (12 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));//TODO: sets font of ALL MAIN TEXT inside the Text fields such as: Rows, Columns, File contents...

    /**
     * The bold font for values.
     */
    public static Font boldValueFont = new FontUIResource(VALUE_FONT_NAME, Font.BOLD, (int) (12 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

    /**
     * The big font for values.
     */
    public static Font bigBoldValueFont = new FontUIResource(VALUE_FONT_NAME, Font.BOLD, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

    /**
     * The regular font for labels.
     */
    public static Font labelsFont = new FontUIResource(LABEL_FONT_NAME, Font.BOLD, (int) (12 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

    /**
     * The thin font for labels.
     */
    public static Font thinLabelsFont = new FontUIResource(LABEL_FONT_NAME, 0, (int) (12 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER)); //! TODO: note: for some reason this font is un-resizable !!!!!

    /**
     * The small font for labels.
     */
    public static Font smallLabelsFont = new FontUIResource(LABEL_FONT_NAME, 0, (int) (11 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

    /**
     * The big font for labels.
     */
    public static Font bigLabelsFont = new FontUIResource(LABEL_FONT_NAME, Font.BOLD, (int) (14 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

    /**
     * The thin big font for labels.
     */
    public static Font thinBigLabelsFont = new FontUIResource(LABEL_FONT_NAME, 0, (int) (14 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

    /**
     * The font of the status line.
     */
    public static Font statusLineFont = new FontUIResource(LABEL_FONT_NAME, Font.BOLD, (int) (16 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));//TODO: font for 'Status' line


    //TODO: I added initialization: so this class could reset itself:
    // important the constructor would be public:
    public Utilities() {

        /**
         * The regular font for values.
         */
        valueFont = new FontUIResource(VALUE_FONT_NAME, 0, (int) (12 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));//TODO: sets font of ALL MAIN TEXT inside the Text fields such as: Rows, Columns, File contents...

        /**
         * The bold font for values.
         */
        boldValueFont = new FontUIResource(VALUE_FONT_NAME, Font.BOLD, (int) (12 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

        /**
         * The big font for values.
         */
        bigBoldValueFont = new FontUIResource(VALUE_FONT_NAME, Font.BOLD, (int) (13 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

        /**
         * The regular font for labels.
         */
        labelsFont = new FontUIResource(LABEL_FONT_NAME, Font.BOLD, (int) (12 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

        /**
         * The thin font for labels.
         */
        thinLabelsFont = new FontUIResource(LABEL_FONT_NAME, 0, (int) (12 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER)); //! TODO: note: for some reason this font is un-resizable !!!!!

        /**
         * The small font for labels.
         */
        smallLabelsFont = new FontUIResource(LABEL_FONT_NAME, 0, (int) (11 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

        /**
         * The big font for labels.
         */
        bigLabelsFont = new FontUIResource(LABEL_FONT_NAME, Font.BOLD, (int) (14 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

        /**
         * The thin big font for labels.
         */
        thinBigLabelsFont = new FontUIResource(LABEL_FONT_NAME, 0, (int) (14 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));

        /**
         * The font of the status line.
         */
        statusLineFont = new FontUIResource(LABEL_FONT_NAME, Font.BOLD, (int) (16 * ResolutionUtils.getInstance().RESOLUTION_FONT_SCALE_PARAMETER));//TODO: font for 'Status' line
    }


    /**
     * Returns the location of the given bottom component relative to its given top level ancestor.
     */
    public static Point getTopLevelLocation(Component top, Component bottom) {
        Point point = new Point();
        Component c = bottom;
        while (c.getParent() != null && c != top) {
            point.x += c.getLocation().getX();
            point.y += c.getLocation().getY();
            c = c.getParent();
        }
        return point;
    }

    /**
     * Scrolls the given table such that the given row will be centered.
     * Also required are the containing panel and the number of visible rows in the table.
     */
    public static void tableCenterScroll(JPanel panel, JTable table, int row) {
        JScrollPane scrollPane = (JScrollPane) table.getParent().getParent();
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        int beforeScrollValue = bar.getValue();
        Rectangle r = table.getCellRect(row, 0, true);//TODO: sets ROW SIZES in Text Table !? - DOESN'T DO ANYTHING
        table.scrollRectToVisible(r);
        panel.repaint();
        int afterScrollValue = bar.getValue();
        double visibleRowsCount = computeVisibleRowsCount(table);

        // The scroller moved down
        if (afterScrollValue > beforeScrollValue) {
            Rectangle newRectangle = table.getCellRect((int) (Math.min(row + visibleRowsCount / 2, table.getRowCount() - 1)), 0, true);
            table.scrollRectToVisible(newRectangle);
            panel.repaint();
        }
        // The scroller moved up.
        else if (afterScrollValue < beforeScrollValue) {
            Rectangle newRectangle = table.getCellRect((int) (Math.max(row - visibleRowsCount / 2, 0)), 0, true);
            table.scrollRectToVisible(newRectangle);
            panel.repaint();
        }
    }

    /**
     * Returns the number of visible rows in the given table.
     */
    public static double computeVisibleRowsCount(JTable table) {
        return table.getParent().getBounds().getHeight() / table.getRowHeight();
    }
}
