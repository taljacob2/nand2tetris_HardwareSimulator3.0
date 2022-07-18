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
import Hack.Controller.ControllerEvent;
import HackGUI.*;
import Hack.HardwareSimulator.*;
import ResolutionUtils.*;
import javafx.application.Platform;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;

//TODO: check if it is possible to change FileChooser to javaFX

/**
 * The GUI Component of the Hardware Simulator.
 */
public class HardwareSimulatorControllerComponent extends ControllerComponent implements HardwareSimulatorControllerGUI/*, ChipNameListener */ {

    // The buttons of this component.
    private MouseOverJButton loadChipButton;
    private MouseOverJButton tickTockButton;
    private MouseOverJButton evalButton;

    // The icons of the buttons.
    private ImageIcon loadChipIcon;
    private ImageIcon tickTockIcon;
    private ImageIcon evalIcon;

    // The settings window and chip loading window.
    private ChipLoaderFileChooser settingsWindow;

    // The menu items of this component.
    private JMenuItem loadChipMenuItem, evalMenuItem, tickTockMenuItem/*, folderSettingsMenuItem*/;

    // The chip file chooser
//    private JFileChooser chipFileChooser; //TODO: LOAD CHIP FILE CHOOSER !!!! CHANGE TO JavaFX
    private FileChooser chipFileChooser; //TODO: LOAD CHIP FILE CHOOSER !!!! CHANGE TO JavaFX

    public String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();//TODO: MY ADDITION

    /**
     * Constructs a new HardwareSimulatorControllerComponent.
     */
    public HardwareSimulatorControllerComponent() {//TODO: changes the sizes of "View" -> "Script" window and "Output" and "Compare" windows!
        //! TODO:  THESE ARE THE JPanels dedicated for the file Text  FOUND!!!!!!
//        scriptComponent.updateSize((int) (516 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (592 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        //TODO: FORCE SET OF SCRIPT TABLE'S HEIGHT
        if(ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._3) {
            scriptComponent.updateSize(2000, (int) (BoundaryUtils.calc(BoundaryUtils.Height.scriptTextTable) * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER) - 7);//! TODO FORCE SIZE !!!
            outputComponent.updateSize(2000,(int) (BoundaryUtils.calc(BoundaryUtils.Height.scriptTextTable) * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER) - 7);//! TODO FORCE SIZE !!!
            comparisonComponent.updateSize(2000,(int) (BoundaryUtils.calc(BoundaryUtils.Height.scriptTextTable) * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER) - 7);//! TODO FORCE SIZE !!!
        }
        else if(ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._2){
            scriptComponent.updateSize(2000, (int) (BoundaryUtils.calc(BoundaryUtils.Height.scriptTextTable) * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER) + 100);//! TODO FORCE SIZE !!!
            outputComponent.updateSize(2000, (int) (BoundaryUtils.calc(BoundaryUtils.Height.scriptTextTable) * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER) + 100);
            comparisonComponent.updateSize(2000, (int) (BoundaryUtils.calc(BoundaryUtils.Height.scriptTextTable) * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER) + 100);
        }
        else if (ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._1) {
            scriptComponent.updateSize(2000, BoundaryUtils.calc(BoundaryUtils.Height.scriptTextTable));//! TODO FORCE SIZE !!!
            outputComponent.updateSize(2000,BoundaryUtils.calc(BoundaryUtils.Height.scriptTextTable));//! TODO FORCE SIZE !!!
            comparisonComponent.updateSize(2000,BoundaryUtils.calc(BoundaryUtils.Height.scriptTextTable));//! TODO FORCE SIZE !!!
        }

    }

    public void disableEval() {
        evalButton.setEnabled(false);
        evalMenuItem.setEnabled(false);
    }

    public void enableEval() {
        evalButton.setEnabled(true);
        evalMenuItem.setEnabled(true);
    }

    public void disableTickTock() {
        tickTockButton.setEnabled(false);
        tickTockMenuItem.setEnabled(false);
    }

    public void enableTickTock() {
        tickTockButton.setEnabled(true);
        tickTockMenuItem.setEnabled(true);
    }

    /**
     * Initializes this component.
     */
    protected void init() {
        super.init();

        settingsWindow = new ChipLoaderFileChooser();
        settingsWindow.addListener(this);

        chipFileChooser = new FileChooser();//TODO: LOAD CHIP FILE CHOOSER !!!! CHANGE TO JavaFX
//        chipFileChooser.setFileFilter(new HDLFileFilter()); //set file filter
        // set file filter::::
        chipFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HDL File", "*.hdl"));//TODO instead // extension filter ! SHOW ONLY .hdl Files FOR HDL Chip File

        initLoadChipButton();
        initTickTockButton();
        initEvalButton();
    }

    public void setWorkingDir(File file) {
        super.setWorkingDir(file);
        chipFileChooser.setInitialDirectory(new File(currentPath));//set INITIAL DIRECTORY
    }

    /**
     * Arranges the tool bar.
     */
    protected void arrangeToolBar() {
        toolBar.setSize(TOOLBAR_WIDTH, TOOLBAR_HEIGHT);
        toolBar.add(loadChipButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(singleStepButton);
        toolBar.add(ffwdButton);
        toolBar.add(stopButton);
        toolBar.add(rewindButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(evalButton);
        toolBar.add(tickTockButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(scriptButton);
        toolBar.add(breakButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(speedSlider);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(animationCombo);
        toolBar.add(formatCombo);
        toolBar.add(additionalDisplayCombo);


        //! TODO:: FORCE SET OF Tool Bar COLOR !!!!
        toolBar.setBackground(Colors.getInstance().backgroundColor);

        //! TODO: SET ALL OF THE BUTTON COLORS :
        loadChipButton.setBackground(Colors.getInstance().backgroundColor);
        singleStepButton.setBackground(Colors.getInstance().backgroundColor);
        ffwdButton.setBackground(Colors.getInstance().backgroundColor);
        stopButton.setBackground(Colors.getInstance().backgroundColor);
        rewindButton.setBackground(Colors.getInstance().backgroundColor);
        evalButton.setBackground(Colors.getInstance().backgroundColor);
        tickTockButton.setBackground(Colors.getInstance().backgroundColor);
        scriptButton.setBackground(Colors.getInstance().backgroundColor);
        breakButton.setBackground(Colors.getInstance().backgroundColor);
        speedSlider.setBackground(Colors.getInstance().backgroundColor);
        animationCombo.setBackground(Colors.getInstance().backgroundColor);
        formatCombo.setBackground(Colors.getInstance().backgroundColor);
        additionalDisplayCombo.setBackground(Colors.getInstance().backgroundColor);
    }

    /**
     * Arranges the menu bar.
     */
    protected void arrangeMenu() {

        super.arrangeMenu();

        fileMenu.removeAll();

        loadChipMenuItem = new JMenuItem("Load Chip", KeyEvent.VK_L);
        loadChipMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadChipMenuItem_actionPerformed(e);
            }
        });
        fileMenu.add(loadChipMenuItem);


        fileMenu.add(scriptMenuItem);

        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        runMenu.removeAll();
        runMenu.add(singleStepMenuItem);
        runMenu.add(ffwdMenuItem);
        runMenu.add(stopMenuItem);
        runMenu.add(rewindMenuItem);
        runMenu.addSeparator();

        evalMenuItem = new JMenuItem("Eval", KeyEvent.VK_E);
        evalMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                evalMenuItem_actionPerformed(e);
            }
        });
        runMenu.add(evalMenuItem);

        tickTockMenuItem = new JMenuItem("Tick Tock", KeyEvent.VK_C);
        tickTockMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tickTockMenuItem_actionPerformed(e);
            }
        });

        runMenu.add(tickTockMenuItem);
        runMenu.addSeparator();
        runMenu.add(breakpointsMenuItem);
    }

    // Initializing the load chip button.
    private void initLoadChipButton() {
        loadChipIcon = new ImageIcon(Utilities.imagesDir + "chip.gif");
        loadChipButton = new MouseOverJButton();
        loadChipButton.setMaximumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));//TODO: MODIFIES THE SIZE OF Load Chip Top Bar button
        loadChipButton.setMinimumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        loadChipButton.setPreferredSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        loadChipButton.setToolTipText("Load Chip");
        loadChipButton.setIcon(loadChipIcon);
        loadChipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadChipButton_actionPerformed(e);
            }
        });
    }

    // Initializing the tick tock button.
    private void initTickTockButton() {
        tickTockIcon = new ImageIcon(Utilities.imagesDir + "clock2.gif");
        tickTockButton = new MouseOverJButton();
        tickTockButton.setMaximumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        tickTockButton.setMinimumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        tickTockButton.setPreferredSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        tickTockButton.setToolTipText("Tick Tock");
        tickTockButton.setIcon(tickTockIcon);
        tickTockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tickTockButton_actionPerformed(e);
            }
        });
    }


    // Initializing the eval button.
    private void initEvalButton() {
        evalIcon = new ImageIcon(Utilities.imagesDir + "calculator2.gif");
        evalButton = new MouseOverJButton();
        evalButton.setMaximumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        evalButton.setMinimumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        evalButton.setPreferredSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        evalButton.setToolTipText("Eval");
        evalButton.setIcon(evalIcon);
        evalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                evalButton_actionPerformed(e);
            }
        });
    }

    // Called when the load chip button is pressed.//
    private void loadChipPressed() { //TODO: LOAD CHIP FILECHOOSER ACTION !!!


        Platform.runLater(new Runnable() {//! TODO: Load Chip FILECHOOSER ACTION !!!
            @Override
            public void run() {
                chipFileChooser.setTitle("Load Chip");//set title

                File file = chipFileChooser.showOpenDialog(null);
                //TODO: my edit
                notifyControllerListeners(HardwareSimulatorControllerEvent.CHIP_CHANGED, file.getAbsoluteFile());
            }
        });




        //OLD CODE:


//        int returnVal = chipFileChooser.showDialog(this, "Load Chip");
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            notifyControllerListeners(HardwareSimulatorControllerEvent.CHIP_CHANGED, chipFileChooser.getSelectedFile().getAbsoluteFile());
//        }
    }

    /**
     * Implementing the action of pressing the load chip button.
     */
    public void loadChipButton_actionPerformed(ActionEvent e) {
        loadChipPressed();
    }

    /**
     * Implementing the action of pressing the tick tock button.
     */
    public void tickTockButton_actionPerformed(ActionEvent e) {
        notifyControllerListeners(HardwareSimulatorControllerEvent.TICKTOCK_CLICKED, null);
    }

    /**
     * Implementing the action of pressing the eval button.
     */
    public void evalButton_actionPerformed(ActionEvent e) {
        notifyControllerListeners(HardwareSimulatorControllerEvent.EVAL_CLICKED, null);
    }

    /**
     * Implementing the action of choosing the load chip menu item from the menu bar.
     */
    public void loadChipMenuItem_actionPerformed(ActionEvent e) {
        loadChipPressed();
    }

    /**
     * Implementing the action of choosing the eval menu item from the menu bar.
     */
    public void evalMenuItem_actionPerformed(ActionEvent e) {
        notifyControllerListeners(HardwareSimulatorControllerEvent.EVAL_CLICKED, null);
    }

    /**
     * Implementing the action of choosing the tick tock menu item from the menu bar.
     */
    public void tickTockMenuItem_actionPerformed(ActionEvent e) {
        notifyControllerListeners(HardwareSimulatorControllerEvent.TICKTOCK_CLICKED, null);
    }
}
