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
import Hack.Gates.*;
import Hack.ComputerParts.*;
import Hack.HardwareSimulator.*;
import HackGUI.*;
import ResolutionUtils.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * This class represents the gui of the hardware simulator.
 */
public class HardwareSimulatorComponent extends HackSimulatorComponent implements HardwareSimulatorGUI, GatesPanelGUI {

    // The dimension of this window.
    private static final int WIDTH = (int) (3000 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER);//TODO: SETS SIZE??
    private static final int HEIGHT = (int) (2000 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER);//TODO: SETS SIZE??

    // The input pins of the hardware simulator.
    private PinsComponent inputPins;

    // The output pins of the hardware simulator.
    private PinsComponent outputPins;

    // The internal pins of the hardware simulator.
    private PinsComponent internalPins;

    // The hdl view of the hardware simulator.
    private TextFileComponent hdlView;

    // The part pins of the hardware simulator.
    private PartPinsComponent partPins;

    // The parts of the hardware simulator.
    private PartsComponent parts;

    // The message label
    private JLabel messageLbl = new JLabel();

    // The two optional gates panel (with different layouts)
    private JPanel nullLayoutGatesPanel;
    private JPanel flowLayoutGatesPanel;

    // True if the current layout is flow layout.
    private boolean flowLayout = false;

    // The gate info component of the current gate
    private GateInfoComponent gateInfo;

    /**
     * Constructs a new HardwareSimulatorComponent.
     */
    public HardwareSimulatorComponent() {
        nullLayoutGatesPanel = new JPanel();
        flowLayoutGatesPanel = new JPanel();
        nullLayoutGatesPanel.setLayout(null);
        flowLayoutGatesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));


        //! TODO: HERE YOU CAN SEE WHICH TABLE DEFINED ON WHICH FILE !!!:
        inputPins = new PinsComponent();
        inputPins.setPinsName("Input pins");
        outputPins = new PinsComponent();
        outputPins.setPinsName("Output pins");
        internalPins = new PinsComponent();
        internalPins.setPinsName("Internal pins");
        partPins = new PartPinsComponent();
        partPins.setPinsName("Part pins");
        parts = new PartsComponent();
        parts.setName("Internal Parts");
        hdlView = new TextFileComponent();
        gateInfo = new GateInfoComponent();

        jbInit();

        //TODO: SETS THE LOCATION OF THE TABLES!
        inputPins.setTopLevelLocation(this);
        outputPins.setTopLevelLocation(this);
        internalPins.setTopLevelLocation(this);
        partPins.setTopLevelLocation(this);
        hdlView.setName("HDL");

        //-----------------------
        //! TODO: IMPORTANT : I Manually set the Location of the Tables ::: !!!
        // OUTPUT PINS LOCATION:
        outputPins.setLocation(inputPins.getWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES, BoundaryUtils.Y.table_inputPins_OutputPins);//TODO WORKS!!!!!


        //-----------------------
        //! TODO: IMPORTANT : I Manually set the Color of the Tables ::: !!!

        gateInfo.setBackground(Colors.getInstance().backgroundColor);//TODO: Sets the color of the Chip name / Time ToolBar !
        inputPins.setBackground(Colors.getInstance().backgroundColor);//TODO: Sets the color
        outputPins.setBackground(Colors.getInstance().backgroundColor);//TODO: Sets the color
        internalPins.setBackground(Colors.getInstance().backgroundColor);//TODO: Sets the color
        partPins.setBackground(Colors.getInstance().backgroundColor);//TODO: Sets the color of
        hdlView.setBackground(Colors.getInstance().backgroundColor);//TODO: Sets the color of

        //! TODO: IMPORTANT : y forcing change of width and height of 'gateInfo': --- TO FORCE FAST COLOR !!!!
        gateInfo.setSize(new Dimension(2560, 1440));

    }

    public void loadProgram() {
    }

    /**
     * Returns the Gates panel.
     */
    public GatesPanelGUI getGatesPanel() {
        return this;
    }

    /**
     * Returns the HDLView.
     */
    public TextFileGUI getHDLView() {
        return hdlView;
    }

    /**
     * Returns the input pins table.
     */
    public PinsGUI getInputPins() {
        return inputPins;
    }

    /**
     * Returns the output pins table.
     */
    public PinsGUI getOutputPins() {
        return outputPins;
    }

    /**
     * Returns the internal pins table.
     */
    public PinsGUI getInternalPins() {
        return internalPins;
    }

    public GateInfoGUI getGateInfo() {
        return gateInfo;
    }

    /**
     * Returns the part pins table.
     */
    public PartPinsGUI getPartPins() {
        return partPins;
    }

    /**
     * Returns the parts table.
     */
    public PartsGUI getParts() {
        return parts;
    }

    /**
     * Displays the Internal pins table.
     */
    public void showInternalPins() {
        internalPins.setVisible(true);
    }

    /**
     * Hides the Internal pins table.
     */
    public void hideInternalPins() {
        internalPins.setVisible(false);
    }

    /**
     * Displays the Part pins table.
     */
    public void showPartPins() {
        partPins.setVisible(true);
    }

    /**
     * Hides the Part pins table.
     */
    public void hidePartPins() {
        partPins.setVisible(false);
    }

    /**
     * Displays the Parts table.
     */
    public void showParts() {
        parts.setVisible(true);
    }

    /**
     * Hides the Parts table.
     */
    public void hideParts() {
        parts.setVisible(false);
    }

    /**
     * Displays the given message. The display color is chosen according to
     * the 'error' parameter.
     */
    public void displayMessage(String message, boolean error) {
        if (error)
            messageLbl.setForeground(Colors.getInstance().statusLineError);
        else
            messageLbl.setForeground(UIManager.getColor("Label.foreground"));
        messageLbl.setText(message);
    }

    public Point getAdditionalDisplayLocation() {
        return new Point(496, 13);
    }

    public void setWorkingDir(File file) {
    }

    /**
     * Adds the given gate component to the gates panel.
     */
    public void addGateComponent(Component gateComponent) {
        if (flowLayout) {
            flowLayoutGatesPanel.add(gateComponent);
            flowLayoutGatesPanel.revalidate();
            flowLayoutGatesPanel.repaint();
        }
        else {
            Component[] components = nullLayoutGatesPanel.getComponents();
            for (int i = 0; i < components.length; i++) {
                Rectangle componentBounds = components[i].getBounds();
                int x1 = (int) componentBounds.getX();
                int y1 = (int) componentBounds.getY();
                int x2 = (int) (componentBounds.getX() + componentBounds.getWidth() - 1);
                int y2 = (int) (componentBounds.getY() + componentBounds.getHeight() - 1);
                if (!(gateComponent.getY() > y2 || gateComponent.getX() > x2 ||
                        gateComponent.getY() + gateComponent.getHeight() - 1 < y1 ||
                        gateComponent.getX() + gateComponent.getWidth() - 1 < x1)) {

                    flowLayout = true;
                    if (currentAdditionalDisplay == null) {
                        nullLayoutGatesPanel.setVisible(false);
                        flowLayoutGatesPanel.setVisible(true);
                    }

                    for (i = 0; i < components.length; i++)
                        flowLayoutGatesPanel.add(components[i]);

                    flowLayoutGatesPanel.add(gateComponent);

                    break;
                }
            }
            if (!flowLayout) {
                nullLayoutGatesPanel.add(gateComponent);
                nullLayoutGatesPanel.revalidate();
                nullLayoutGatesPanel.repaint();
            }
        }
    }

    /**
     * Removes the given gate component from the gates panel.
     */
    public void removeGateComponent(Component gateComponent) {
        nullLayoutGatesPanel.remove(gateComponent);
        flowLayoutGatesPanel.remove(gateComponent);
        nullLayoutGatesPanel.revalidate();
        flowLayoutGatesPanel.revalidate();
        nullLayoutGatesPanel.repaint();
        flowLayoutGatesPanel.repaint();
    }

    /**
     * Removes all the gate components from the gates panel.
     */
    public void removeAllGateComponents() {
        nullLayoutGatesPanel.removeAll();
        flowLayoutGatesPanel.removeAll();
        nullLayoutGatesPanel.revalidate();
        flowLayoutGatesPanel.revalidate();
        nullLayoutGatesPanel.repaint();
        flowLayoutGatesPanel.repaint();

        flowLayout = false;
        if (currentAdditionalDisplay == null) {
            nullLayoutGatesPanel.setVisible(true);
            flowLayoutGatesPanel.setVisible(false);
        }
    }

    //! TODO: IMPORTANT changes TABLE BOUNDARIES
    // Initialization of this component.
    private void jbInit() {
//
//        //-----------------COPY VALUES OF X TO GLOBAL BoundaryUtils class, to be used in JPanel of Script File:-----------------
//        BoundaryUtils.X.endLocationOfOutputTable = outputPins.getTableWidth() + outputPins.getX();
//        //------------------

        this.setLayout(null);

        //Top Chip Name / Time bar:
        gateInfo.setBounds(0, 0, (int) (gateInfo.getWidth() * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), gateInfo.getHeight());//TODO: changes some kind of border of 'Input/Output pins' Table

        //Input Pins Table:
        if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._3) {
            inputPins.setVisibleRows((int) (7 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._2) {
            inputPins.setVisibleRows((int) (10 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._1) {
            inputPins.setVisibleRows((int) (12 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        }
        inputPins.setBounds(0, BoundaryUtils.Y.table_inputPins_OutputPins, inputPins.getWidth(), inputPins.getHeight());

        //Output Pins Table:
        if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._3) {
            outputPins.setVisibleRows((int) (7 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._2) {
            outputPins.setVisibleRows((int) (10 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._1) {
            outputPins.setVisibleRows((int) (12 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        }
        outputPins.setBounds(inputPins.getTableWidth(), BoundaryUtils.Y.table_inputPins_OutputPins, outputPins.getWidth(), outputPins.getHeight());//TODO: REPLACEMENT - Y is the HDL Table's Y Minus 2 Rows


        //Intertal Pins Table:
        if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._3) {
            internalPins.setBounds(hdlView.getTableWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES, outputPins.getY()+outputPins.getHeight(), outputPins.getWidth() + 2, internalPins.getHeight());
            internalPins.setVisibleRows((int) (15 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._2) {
            internalPins.setBounds(hdlView.getTableWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES, outputPins.getY()+outputPins.getHeight(), outputPins.getWidth() + 2, internalPins.getHeight());
            internalPins.setVisibleRows((int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._1) {
            internalPins.setBounds(hdlView.getTableWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES, outputPins.getY()+outputPins.getHeight(), outputPins.getWidth() + 2, internalPins.getHeight() - 45);
            internalPins.setVisibleRows((int) (13 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        }
        internalPins.setVisible(false);

        //HDL Table
        if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._1) {
            hdlView.setVisibleRows((int) (12 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER) + 1);
            hdlView.setBounds(0, inputPins.getY()+inputPins.getHeight(), hdlView.getWidth(), hdlView.getHeight());
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._2) {
            hdlView.setVisibleRows((int) (15 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER) + 1);
            hdlView.setBounds(0, inputPins.getY()+inputPins.getHeight(), hdlView.getWidth(), hdlView.getHeight());
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._3) {
            hdlView.setVisibleRows((int) (10 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
            hdlView.setBounds(0, inputPins.getY()+inputPins.getHeight(), hdlView.getWidth(), hdlView.getHeight());
        }

        //Part Pins Table:
        partPins.setVisibleRows((int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._3) {
            partPins.setBounds(hdlView.getTableWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES, outputPins.getY()+outputPins.getHeight(), outputPins.getWidth(), internalPins.getHeight());
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._2) {
            partPins.setBounds(hdlView.getTableWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES, outputPins.getY()+outputPins.getHeight(), outputPins.getWidth(), internalPins.getHeight());
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._1) {
            partPins.setBounds(hdlView.getTableWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES, outputPins.getY()+outputPins.getHeight(), outputPins.getWidth(), internalPins.getHeight());
        }
        partPins.setOpaque(false);//TODO: SET OPAQUE
        partPins.setVisible(false);

        //Parts Table:
        parts.setVisibleRows((int) (15 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._3) {
            parts.setBounds(hdlView.getTableWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES, outputPins.getY()+outputPins.getHeight(), parts.getWidth(), internalPins.getHeight());
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._2) {
            parts.setBounds(hdlView.getTableWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES, outputPins.getY()+outputPins.getHeight(), parts.getWidth(), internalPins.getHeight());
            parts.setVisibleRows((int) (19 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._1) {
            parts.setBounds(hdlView.getTableWidth() + BoundaryUtils.X.SPACE_BETWEEN_TABLES, outputPins.getY()+outputPins.getHeight(), parts.getWidth(), internalPins.getHeight());
        }
        parts.setVisible(false);

//        nullLayoutGatesPanel.setBorder(BorderFactory.createEtchedBorder());//TODO: DON'T SET BORDER;
        nullLayoutGatesPanel.setOpaque(false);//TODO: SET OPAQUE

        nullLayoutGatesPanel.setBounds(new Rectangle(492, 10, (int) (524 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 592));

//        flowLayoutGatesPanel.setBorder(BorderFactory.createEtchedBorder());//TODO: DON'T SET BORDER;
        flowLayoutGatesPanel.setOpaque(false);//TODO: SET OPAQUE
        flowLayoutGatesPanel.setBounds(new Rectangle(492, 10, (int) (524 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), 592));
        flowLayoutGatesPanel.setVisible(false);

        messageLbl.setBorder(BorderFactory.createLoweredBevelBorder());
        messageLbl.setBounds(new Rectangle(0, 694, WIDTH - 8, 20));

        this.add(partPins, null);
        this.add(hdlView, null);
        this.add(inputPins, null);
        this.add(outputPins, null);
        this.add(internalPins, null);
        this.add(parts, null);
        this.add(messageLbl, null);
        this.add(gateInfo, null);
        this.add(nullLayoutGatesPanel, null);
        this.add(flowLayoutGatesPanel, null);

        setSize(WIDTH, HEIGHT);
    }

    public void setAdditionalDisplay(JComponent additionalComponent) {
        if (currentAdditionalDisplay == null && additionalComponent != null) {
            if (flowLayout)
                flowLayoutGatesPanel.setVisible(false);
            else
                nullLayoutGatesPanel.setVisible(false);
        }
        else if (currentAdditionalDisplay != null && additionalComponent == null) {
            if (flowLayout)
                flowLayoutGatesPanel.setVisible(true);
            else
                nullLayoutGatesPanel.setVisible(true);
        }

        super.setAdditionalDisplay(additionalComponent);
    }
}
