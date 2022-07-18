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

package SimulatorsGUI;

import Colors.Colors;
import HackGUI.*;
import Hack.HardwareSimulator.*;
import ResolutionUtils.*;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the GUI of a gate info.
 */
public class GateInfoComponent extends JPanel implements GateInfoGUI {

    // creating labels
    private JLabel chipNameLbl;
    private JLabel timeLbl;

    // creating text fields
    private JTextField chipNameTxt;
    private JTextField timeTxt;


    // true if the clock is currently up
    private boolean clockUp;

    // the name of the chip
    private String chipName;

    /**
     * Constructs a new GateInfoComponent.
     */
    public GateInfoComponent() {
        chipNameLbl = new JLabel();
        timeLbl = new JLabel();

        chipNameTxt = new JTextField();
        timeTxt = new JTextField();

        jbInit();
    }

    public void setChip(String chipName) {
        this.chipName = chipName;
        chipNameTxt.setText(chipName);
    }

    public void setClock(boolean up) {
        clockUp = up;
        if (up)
            timeTxt.setText(timeTxt.getText() + "+");
    }

    public void setClocked(boolean clocked) {
        if (clocked)
            chipNameTxt.setText(chipName + " (Clocked) ");
        else
            chipNameTxt.setText(chipName);
    }


    public void setTime(int time) {
        if (clockUp)
            timeTxt.setText(String.valueOf(time) + "+");
        else
            timeTxt.setText(String.valueOf(time));
    }


    public void reset() {
        chipNameTxt.setText("");
        timeTxt.setText("0");
    }

    public void enableTime() {
        timeLbl.setEnabled(true);
        timeTxt.setEnabled(true);
    }

    public void disableTime() {
        timeLbl.setEnabled(false);
        timeTxt.setEnabled(false);
    }

    // Initializes this component.
    private void jbInit() {

        this.setLayout(null);
        //Labels:
        chipNameLbl.setText("Chip Name :");
        chipNameLbl.setFont(Utilities.thinLabelsFont);//TODO: my addition set Font
        chipNameLbl.setForeground(Colors.getInstance().top_ChipNameLabel_Foreground);//TODO: my addition set Color
        chipNameLbl.setBounds(new Rectangle(BoundaryUtils.calc(BoundaryUtils.X.firstTitle), BoundaryUtils.calc(BoundaryUtils.Y.top_ChipName_Time_bar_LABEL), (int) (74 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), BoundaryUtils.calc(BoundaryUtils.Height.top_ChipName_Time_bar)));//TODO: sets text of Chip Name LABEL / Title - in the center

        timeLbl.setText("Time :");
        timeLbl.setFont(Utilities.thinLabelsFont);//TODO: my addition set Font
        timeLbl.setForeground(Colors.getInstance().top_TimeLabel_Foreground);//TODO: my addition set Color
        timeLbl.setBounds(new Rectangle((int) (341 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER),  BoundaryUtils.calc(BoundaryUtils.Y.top_ChipName_Time_bar_LABEL), (int) (42 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), BoundaryUtils.calc(BoundaryUtils.Height.top_ChipName_Time_bar)));//TODO: sets text of Time LABEL / Title - in the center

        //Text fields:
        //Chip Name
        chipNameTxt.setBackground(Colors.getInstance().backgroundColorOfTextField);//TODO: CHIP NAME FIELD COLOR !
        chipNameTxt.setFont(Utilities.thinBigLabelsFont);
        chipNameTxt.setEditable(false);
        chipNameTxt.setHorizontalAlignment(SwingConstants.LEFT);
        //TODO: sets text of Chip Name TEXT SECTION - in the center
        chipNameTxt.setBounds(new Rectangle(chipNameLbl.getWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES * 2, BoundaryUtils.calc(BoundaryUtils.Y.top_ChipName_Time_bar), (int) (231 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), BoundaryUtils.calc(BoundaryUtils.Height.top_ChipName_Time_bar_TEXTSECTION)));//TODO: sets text of Chip Name TEXT SECTION - in the center
        //Time
        timeTxt.setBackground(Colors.getInstance().backgroundColorOfTextField);//TODO: TIME FIELD COLOR !
        timeTxt.setFont(Utilities.thinBigLabelsFont);
        timeTxt.setEditable(false);
        //TODO: sets text of Time TEXT SECTION - in the center
        timeTxt.setBounds(new Rectangle( timeLbl.getX() + timeLbl.getWidth() ,BoundaryUtils.calc(BoundaryUtils.Y.top_ChipName_Time_bar), (int) (69 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER),BoundaryUtils.calc(BoundaryUtils.Height.top_ChipName_Time_bar_TEXTSECTION)));//TODO: sets text of Chip Name TEXT SECTION - in the center

        //add:
        this.add(chipNameTxt, null);
        this.add(chipNameLbl, null);
        this.add(timeLbl, null);
        this.add(timeTxt, null);

        setSize((int) (483 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (37 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));//TODO: sets the size of boundary.
        setBorder(BorderFactory.createEtchedBorder());
    }
}
