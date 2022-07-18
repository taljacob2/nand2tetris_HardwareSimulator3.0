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
import Hack.Controller.*;
import ResolutionUtils.*;
import javafx.application.Platform;
import javafx.scene.layout.Border;
import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class represents the GUI of the controller component.
 */
public class ControllerComponent extends JFrame implements ControllerGUI,
        FilesTypeListener,
        BreakpointsChangedListener {

    // The dimensions of the tool bar.
    protected static final int TOOLBAR_WIDTH = (int) (ResolutionUtils.getInstance().RESOLUTION_WIDTH * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER);//TODO - SETS SIZE!
    protected static final int TOOLBAR_HEIGHT = (int) (55 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER);//TODO - SETS SIZE!

    // The dimensions of this window.
    //TODO: window DIMENSION !?
//    private static final int CONTROLLER_WIDTH = (int) (1024 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER);//TODO - SETS SIZE!
    private static final int CONTROLLER_WIDTH = ResolutionUtils.getInstance().RESOLUTION_WIDTH;
//    private static final int CONTROLLER_HEIGHT = (int) (741 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER);//TODO - SETS SIZE!
    private static final int CONTROLLER_HEIGHT = ResolutionUtils.getInstance().RESOLUTION_HEIGHT;

    // The dimensions of the toolbar's separator.
    protected static final Dimension separatorDimension = new Dimension(3, TOOLBAR_HEIGHT - 5);

    // The vector of listeners to this component.
    private Vector listeners;

    // The fast forward button.
    protected MouseOverJButton ffwdButton;

    // The stop button.
    protected MouseOverJButton stopButton;

    // The rewind button.
    protected MouseOverJButton rewindButton;

    // The load script button.
    protected MouseOverJButton scriptButton;

    // The breakpoints button.
    protected MouseOverJButton breakButton;

    // The single step button.
    protected MouseOverJButton singleStepButton;

    // The load program button.
    protected MouseOverJButton loadProgramButton;

    // Creating the file chooser window & the breakpoint window.
    private FileChooser fileChooser = new FileChooser(); //TODO: SCRIPT FILECHOOSER change test!
//    private JFileChooser fileChooser = new JFileChooser(); //TODO: SCRIPT FILECHOOSER change test!

    public String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();//TODO: MY ADDITION


    private BreakpointWindow breakpointWindow = new BreakpointWindow();

    // Creating the icons for the buttons.
    private ImageIcon rewindIcon = new ImageIcon(Utilities.imagesDir + "vcrrewind.gif");
    private ImageIcon ffwdIcon = new ImageIcon(Utilities.imagesDir + "vcrfastforward.gif");
    private ImageIcon singleStepIcon = new ImageIcon(Utilities.imagesDir + "vcrforward.gif");
    private ImageIcon stopIcon = new ImageIcon(Utilities.imagesDir + "vcrstop.gif");
    private ImageIcon breakIcon = new ImageIcon(Utilities.imagesDir + "redflag.gif");
    private ImageIcon loadProgramIcon = new ImageIcon(Utilities.imagesDir + "opendoc.gif");
    private ImageIcon scriptIcon = new ImageIcon(Utilities.imagesDir + "scroll.gif");

    // The speed slider.
    protected JSlider speedSlider;

    // A combo box which controls the format of all the components.
    protected TitledComboBox formatCombo;

    // A combo box for choosing the additional disply.
    protected TitledComboBox additionalDisplayCombo;

    // A combo box for choosing the animation type.
    protected TitledComboBox animationCombo;

    // The toolbar of the controller.
    protected JToolBar toolBar;

    // The components of the menu
    protected JMenuBar menuBar;
    protected JMenu fileMenu, viewMenu, runMenu, resolutionMenu, themeMenu, helpMenu;//TODO resolution Menu
    protected JMenuItem singleStepMenuItem, ffwdMenuItem, stopMenuItem, rewindMenuItem, exitMenuItem;
    protected JMenuItem usageMenuItem, aboutMenuItem;
    protected JMenu animationSubMenu, numericFormatSubMenu, additionalDisplaySubMenu, resolutionSubMenu, themeSubMenu;
    protected JMenuItem breakpointsMenuItem, scriptMenuItem, programMenuItem;
    protected JRadioButtonMenuItem resolution_1, resolution_2, resolution_3;//TODO: Resolution
    protected JRadioButtonMenuItem classic_v3_Theme, classicTheme, darkTheme;//TODO: Color Theme
    protected JRadioButtonMenuItem decMenuItem, hexaMenuItem, binMenuItem;
    protected JRadioButtonMenuItem scriptDisplayMenuItem, outputMenuItem, compareMenuItem, noAdditionalDisplayMenuItem;
    protected JRadioButtonMenuItem partAnimMenuItem, fullAnimMenuItem, noAnimMenuItem;

    // the message label (status line)
    protected JLabel messageLbl = new JLabel();

    //TODO: we found the script table !!!

    // component for displaying the script, output file and comparison file.
    protected FileDisplayComponent scriptComponent;
    protected FileDisplayComponent outputComponent;
    protected FileDisplayComponent comparisonComponent;

    // HTML viewers for the usage and about windows.
    private HTMLViewFrame usageWindow, aboutWindow;

    /**
     * Constructs a new ControllerComponent.
     */
    public ControllerComponent() {//TODO: I think this is the top buttons Tool Bar JFrame or maybe it's the main JFrame !

        listeners = new Vector();
        formatCombo = new TitledComboBox("Format:", "Numeric display format",
                new String[]{"Decimal", "Hexa", "Binary"}, (int) (75 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER * 1.1));//TODO: changes the top button menu height such as "Animate", "Format", "View".
        additionalDisplayCombo = new TitledComboBox("View:", "View options",
                new String[]{"Script", "Output", "Compare",
                        "Screen"}, (int) (80 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER * 1.1));
        animationCombo = new TitledComboBox("Animate:", "Animtion type",
                new String[]{"Program flow", "Program & data flow",
                        "No animation"}, (int) (135 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));
        scriptComponent = new FileDisplayComponent();
        outputComponent = new FileDisplayComponent();
        comparisonComponent = new FileDisplayComponent();


        init();
        jbInit();


        //TODO: check color:
        // Set all JPanels background colors:
//        formatCombo.setBackground(Colors.getInstance().formatButtonTitleBackground);//TODO: "Format" Top button Color
//        additionalDisplayCombo.setBackground(Colors.getInstance().viewButtonTitleBackground);//TODO: "View" Top button Color
//        animationCombo.setBackground(Colors.getInstance().animateButtonTitleBackground);//TODO: "Animate" Top button Color

//        setBackground(Colors.getInstance().backgroundColor); //TODO: my addition: set background Color !


//        setPreferredSize(new Dimension(1920, 1080));//TODO: check!




        setSize(ResolutionUtils.getInstance().RESOLUTION_DIMENSION);//! TODO: FORCES DIMENSION OF WHOLE WINDOW !!!
        setLocationRelativeTo(null);//TODO: my addition: to start the program in the center of the screen instead of the 0,0 position !



        //TODO: CHECK SET OPAQUE OF JPanels:
        scriptComponent.setOpaque(false);//!TODO: SOLUTION : FORCE OPAQUE --- INVISIBILITY!
        outputComponent.setOpaque(false);//!TODO: SOLUTION : FORCE OPAQUE --- INVISIBILITY!
        comparisonComponent.setOpaque(false);//!TODO: SOLUTION : FORCE OPAQUE --- INVISIBILITY!
    }

    public void setWorkingDir(File file) {//argument not used because I didn't have the energy to change Interface
//        fileChooser.setInitialDirectory(file);
        fileChooser.setInitialDirectory(new File(currentPath));//GLOBALY INITIALIZE
    }

    public void setSimulator(HackSimulatorGUI simulator) {
        ((JComponent) simulator).setLocation(0, TOOLBAR_HEIGHT);
        this.getContentPane().add((JComponent) simulator, null);
        ((JComponent) simulator).revalidate();
        repaint();

        if (simulator.getUsageFileName() != null) {
            usageWindow = new HTMLViewFrame(simulator.getUsageFileName());
            usageWindow.setSize((int) (450 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (420 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));//TODO: Size of 'Help' -> 'Usage' window
        }

        if (simulator.getAboutFileName() != null) {
            aboutWindow = new HTMLViewFrame(simulator.getAboutFileName());
            aboutWindow.setSize((int) (450 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (420 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER));//TODO: Size of 'Help' -> 'About...' window
        }
//        setBackground(Colors.getInstance().backgroundColor);//TODO: set 'About' window color -- DOESN'T DO ANYTHING
    }


    public JComponent getComparisonComponent() {
        return comparisonComponent;
    }

    public JComponent getOutputComponent() {
        return outputComponent;
    }

    public JComponent getScriptComponent() {
        return scriptComponent;
    }

    // Initializes the buttons and speed slider
    protected void init() {
        speedSlider = new JSlider(JSlider.HORIZONTAL, 1, HackController.NUMBER_OF_SPEED_UNITS, 1);
        loadProgramButton = new MouseOverJButton();
        ffwdButton = new MouseOverJButton();
        stopButton = new MouseOverJButton();
        rewindButton = new MouseOverJButton();
        scriptButton = new MouseOverJButton();
        breakButton = new MouseOverJButton();
        singleStepButton = new MouseOverJButton();
    }


    /**
     * Registers the given ControllerEventListener as a listener to this GUI.
     */
    public void addControllerListener(ControllerEventListener listener) {
        listeners.addElement(listener);
    }

    /**
     * Un-registers the given ControllerEventListener from being a listener to this GUI.
     */
    public void removeControllerListener(ControllerEventListener listener) {
        listeners.removeElement(listener);
    }

    /**
     * Notify all the ControllerEventListeners on actions taken in it, by creating a
     * ControllerEvent (with the action and supplied data) and sending it using the
     * actionPerformed method to all the listeners.
     */
    public void notifyControllerListeners(byte action, Object data) {
        ControllerEvent event = new ControllerEvent(this, action, data);
        for (int i = 0; i < listeners.size(); i++)
            ((ControllerEventListener) listeners.elementAt(i)).actionPerformed(event);
    }

    /**
     * Sets the script file name with the given one.
     */
    public void setScriptFile(String fileName) {
        scriptComponent.setContents(fileName);
    }

    /**
     * Sets the output file name with the given one.
     */
    public void setOutputFile(String fileName) {
        outputComponent.setContents(fileName);
    }

    /**
     * Sets the comparison file name with the given one.
     */
    public void setComparisonFile(String fileName) {
        comparisonComponent.setContents(fileName);
    }

    /**
     * Sets the current script line.
     */
    public void setCurrentScriptLine(int line) {
        scriptComponent.setSelectedRow(line);

    }

    /**
     * Sets the current output line.
     */
    public void setCurrentOutputLine(int line) {
        outputComponent.setSelectedRow(line);
    }

    /**
     * Sets the current comparison line.
     */
    public void setCurrentComparisonLine(int line) {
        comparisonComponent.setSelectedRow(line);
    }

    /**
     * Shows the breakpoint panel.
     */
    public void showBreakpoints() {
        breakpointWindow.getTable().clearSelection();
        breakpointWindow.setVisible(true);
        if (breakpointWindow.getState() == Frame.ICONIFIED)
            breakpointWindow.setState(Frame.NORMAL);
    }

    /**
     * Enables the single step action.
     */
    public void enableSingleStep() {
        singleStepButton.setEnabled(true);
        singleStepMenuItem.setEnabled(true);
    }

    /**
     * Disables the single step action.
     */
    public void disableSingleStep() {
        singleStepButton.setEnabled(false);
        singleStepMenuItem.setEnabled(false);
    }

    /**
     * Enables the fast forward action.
     */
    public void enableFastForward() {
        ffwdButton.setEnabled(true);
        ffwdMenuItem.setEnabled(true);
    }

    /**
     * Disables the fast forward action.
     */
    public void disableFastForward() {
        ffwdButton.setEnabled(false);
        ffwdMenuItem.setEnabled(false);
    }

    /**
     * Enables the stop action.
     */
    public void enableStop() {
        stopButton.setEnabled(true);
        stopMenuItem.setEnabled(true);
    }

    /**
     * Disables the stop action.
     */
    public void disableStop() {
        stopButton.setEnabled(false);
        stopMenuItem.setEnabled(false);
    }

    /**
     * Enables the eject action.
     */
    public void enableScript() {
        scriptButton.setEnabled(true);
        scriptMenuItem.setEnabled(true);
    }

    /**
     * Disables the eject action.
     */
    public void disableScript() {
        scriptButton.setEnabled(false);
        scriptMenuItem.setEnabled(false);
    }

    /**
     * Enables the rewind action.
     */
    public void enableRewind() {
        rewindButton.setEnabled(true);
        rewindMenuItem.setEnabled(true);
    }

    /**
     * Disables the rewind action.
     */
    public void disableRewind() {
        rewindButton.setEnabled(false);
        rewindMenuItem.setEnabled(false);
    }

    /**
     * Enables the load program action.
     */
    public void enableLoadProgram() {
        loadProgramButton.setEnabled(true);
    }

    /**
     * Disables the load program action.
     */
    public void disableLoadProgram() {
        loadProgramButton.setEnabled(false);
    }

    /**
     * Disables the speed slider.
     */
    public void disableSpeedSlider() {
        speedSlider.setEnabled(false);
    }

    /**
     * Enables the speed slider.
     */
    public void enableSpeedSlider() {
        speedSlider.setEnabled(true);
    }

    /**
     * Disables the animation mode buttons.
     */
    public void disableAnimationModes() {
        animationCombo.setEnabled(false);
        partAnimMenuItem.setEnabled(false);
        fullAnimMenuItem.setEnabled(false);
        noAnimMenuItem.setEnabled(false);
    }

    /**
     * Enables the animation mode buttons.
     */
    public void enableAnimationModes() {
        animationCombo.setEnabled(true);
        partAnimMenuItem.setEnabled(true);
        fullAnimMenuItem.setEnabled(true);
        noAnimMenuItem.setEnabled(true);
    }

    /**
     * Sets the breakpoints list with the given one.
     */
    public void setBreakpoints(Vector breakpoints) {
        // sending the given Vector to the breakpoint panel.
        breakpointWindow.setBreakpoints(breakpoints);
    }

    /**
     * Sets the speed (int code, between 1 and NUMBER_OF_SPEED_UNTIS)
     */
    public void setSpeed(int speed) {
        speedSlider.setValue(speed);
        repaint();
    }

    /**
     * Sets the list of recognized variables with the given one.
     */
    public void setVariables(String[] newVars) {
        breakpointWindow.setVariables(newVars);
    }

    /**
     * Called when the names of the files were changed.
     * The event contains the three strings representing the names of the
     * files.
     */
    public void filesNamesChanged(FilesTypeEvent event) {
        if (event.getFirstFile() != null) {
            scriptComponent.setContents(event.getFirstFile());
            notifyControllerListeners(ControllerEvent.SCRIPT_CHANGE, event.getFirstFile());
        }
        if (event.getSecondFile() != null) {
            outputComponent.setContents(event.getSecondFile());
        }
        if (event.getThirdFile() != null) {
            comparisonComponent.setContents(event.getThirdFile());
        }
    }

    /**
     * Called when there was a change in the breakpoints vector.
     * The event contains the vector of breakpoints.
     */
    public void breakpointsChanged(BreakpointsChangedEvent event) {
        notifyControllerListeners(ControllerEvent.BREAKPOINTS_CHANGE, event.getBreakpoints());
    }


    /**
     * Called when the output file is updated.
     */
    public void outputFileUpdated() {
        outputComponent.refresh();
    }

    /**
     * Hides the controller.
     */
    public void hideController() {
        setVisible(false);
    }

    /**
     * Shows the controller.
     */
    public void showController() {
        setVisible(true);
    }

    /**
     * Sets the animation mode (int code, out of the possible animation constants in HackController)
     */
    public void setAnimationMode(int mode) {
        if (!animationCombo.isSelectedIndex(mode))
            animationCombo.setSelectedIndex(mode);
    }

    public void setAdditionalDisplay(int display) {
        if (!additionalDisplayCombo.isSelectedIndex(display))
            additionalDisplayCombo.setSelectedIndex(display);
    }

    /**
     * Sets the numeric format with the given code (out of the format constants
     * in HackController).
     */
    public void setNumericFormat(int formatCode) {
        if (!formatCombo.isSelectedIndex(formatCode))
            formatCombo.setSelectedIndex(formatCode);
    }

    public void displayMessage(String message, boolean error) {
        if (error)
            messageLbl.setForeground(Colors.getInstance().statusLineError);//TODO: SETS COLOR OF STATUS LINE ON ERROR !!!
        else
            messageLbl.setForeground(UIManager.getColor("Label.foreground"));
        messageLbl.setText(message);
        messageLbl.setToolTipText(message);
    }

    /**
     * Sets the controller's size according to the size constants.
     */
    protected void setControllerSize() {
        setSize(new Dimension(CONTROLLER_WIDTH, CONTROLLER_HEIGHT));
    }

    /**
     * Adds the controls to the toolbar.
     */
    protected void arrangeToolBar() {
        toolBar.add(loadProgramButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(singleStepButton);
        toolBar.add(ffwdButton);
        toolBar.add(stopButton);
        toolBar.add(rewindButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(scriptButton);
        toolBar.add(breakButton);
        toolBar.addSeparator(separatorDimension);
        toolBar.add(speedSlider);
        toolBar.add(animationCombo);
        toolBar.add(additionalDisplayCombo);
        toolBar.add(formatCombo);
    }

    /**
     * Adds the menu items to the menuber.
     */
    protected void arrangeMenu() {

        // Build the first menu.
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        menuBar.add(viewMenu);

        runMenu = new JMenu("Run");
        runMenu.setMnemonic(KeyEvent.VK_R);
        menuBar.add(runMenu);

        resolutionMenu = new JMenu("Resolution");
        resolutionMenu.setMnemonic(KeyEvent.VK_N);
        menuBar.add(resolutionMenu);

        themeMenu = new JMenu("Theme");
        themeMenu.setMnemonic(KeyEvent.VK_T);
        menuBar.add(themeMenu);

        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(helpMenu);

        //Build the second menu.
        programMenuItem = new JMenuItem("Load Program", KeyEvent.VK_O);
        programMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                programMenuItem_actionPerformed(e);
            }
        });
        fileMenu.add(programMenuItem);

        scriptMenuItem = new JMenuItem("Load Script", KeyEvent.VK_P);
        scriptMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scriptMenuItem_actionPerformed(e);
            }
        });
        fileMenu.add(scriptMenuItem);
        fileMenu.addSeparator();

        exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitMenuItem_actionPerformed(e);
            }
        });
        fileMenu.add(exitMenuItem);

        viewMenu.addSeparator();

        ButtonGroup animationRadioButtons = new ButtonGroup();

        animationSubMenu = new JMenu("Animate");
        animationSubMenu.setMnemonic(KeyEvent.VK_A);
        viewMenu.add(animationSubMenu);

        partAnimMenuItem = new JRadioButtonMenuItem("Program flow");
        partAnimMenuItem.setMnemonic(KeyEvent.VK_P);
        partAnimMenuItem.setSelected(true);
        partAnimMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                partAnimMenuItem_actionPerformed(e);
            }
        });
        animationRadioButtons.add(partAnimMenuItem);
        animationSubMenu.add(partAnimMenuItem);

        fullAnimMenuItem = new JRadioButtonMenuItem("Program & data flow");
        fullAnimMenuItem.setMnemonic(KeyEvent.VK_D);
        fullAnimMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fullAnimMenuItem_actionPerformed(e);
            }
        });
        animationRadioButtons.add(fullAnimMenuItem);
        animationSubMenu.add(fullAnimMenuItem);

        noAnimMenuItem = new JRadioButtonMenuItem("No Animation");
        noAnimMenuItem.setMnemonic(KeyEvent.VK_N);
        noAnimMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                noAnimMenuItem_actionPerformed(e);
            }
        });
        animationRadioButtons.add(noAnimMenuItem);
        animationSubMenu.add(noAnimMenuItem);


        ButtonGroup additionalDisplayRadioButtons = new ButtonGroup();

        additionalDisplaySubMenu = new JMenu("View");
        additionalDisplaySubMenu.setMnemonic(KeyEvent.VK_V);
        viewMenu.add(additionalDisplaySubMenu);

        scriptDisplayMenuItem = new JRadioButtonMenuItem("Script");
        scriptDisplayMenuItem.setMnemonic(KeyEvent.VK_S);
        scriptDisplayMenuItem.setSelected(true);
        scriptDisplayMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scriptDisplayMenuItem_actionPerformed(e);
            }
        });
        additionalDisplayRadioButtons.add(scriptDisplayMenuItem);
        additionalDisplaySubMenu.add(scriptDisplayMenuItem);

        outputMenuItem = new JRadioButtonMenuItem("Output");
        outputMenuItem.setMnemonic(KeyEvent.VK_O);
        outputMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                outputMenuItem_actionPerformed(e);
            }
        });
        additionalDisplayRadioButtons.add(outputMenuItem);
        additionalDisplaySubMenu.add(outputMenuItem);

        compareMenuItem = new JRadioButtonMenuItem("Compare");
        compareMenuItem.setMnemonic(KeyEvent.VK_C);
        compareMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                compareMenuItem_actionPerformed(e);
            }
        });
        additionalDisplayRadioButtons.add(compareMenuItem);
        additionalDisplaySubMenu.add(compareMenuItem);

        noAdditionalDisplayMenuItem = new JRadioButtonMenuItem("Screen");
        noAdditionalDisplayMenuItem.setMnemonic(KeyEvent.VK_N);
        noAdditionalDisplayMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                noAdditionalDisplayMenuItem_actionPerformed(e);
            }
        });
        additionalDisplayRadioButtons.add(noAdditionalDisplayMenuItem);
        additionalDisplaySubMenu.add(noAdditionalDisplayMenuItem);


        ButtonGroup formatRadioButtons = new ButtonGroup();

        numericFormatSubMenu = new JMenu("Format");
        numericFormatSubMenu.setMnemonic(KeyEvent.VK_F);
        viewMenu.add(numericFormatSubMenu);

        decMenuItem = new JRadioButtonMenuItem("Decimal");
        decMenuItem.setMnemonic(KeyEvent.VK_D);
        decMenuItem.setSelected(true);
        decMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                decMenuItem_actionPerformed(e);
            }
        });
        formatRadioButtons.add(decMenuItem);
        numericFormatSubMenu.add(decMenuItem);

        hexaMenuItem = new JRadioButtonMenuItem("Hexadecimal");
        hexaMenuItem.setMnemonic(KeyEvent.VK_H);
        hexaMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hexaMenuItem_actionPerformed(e);
            }
        });
        formatRadioButtons.add(hexaMenuItem);
        numericFormatSubMenu.add(hexaMenuItem);

        binMenuItem = new JRadioButtonMenuItem("Binary");
        binMenuItem.setMnemonic(KeyEvent.VK_B);
        binMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                binMenuItem_actionPerformed(e);
            }
        });
        formatRadioButtons.add(binMenuItem);
        numericFormatSubMenu.add(binMenuItem);

        viewMenu.addSeparator();

        singleStepMenuItem = new JMenuItem("Single Step", KeyEvent.VK_S);
        singleStepMenuItem.setAccelerator(KeyStroke.getKeyStroke("F11"));
        singleStepMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                singleStepMenuItem_actionPerformed(e);
            }
        });
        runMenu.add(singleStepMenuItem);

        ffwdMenuItem = new JMenuItem("Run", KeyEvent.VK_F);
        ffwdMenuItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
        ffwdMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ffwdMenuItem_actionPerformed(e);
            }
        });
        runMenu.add(ffwdMenuItem);

        stopMenuItem = new JMenuItem("Stop", KeyEvent.VK_T);
        stopMenuItem.setAccelerator(KeyStroke.getKeyStroke("shift F5"));
        stopMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopMenuItem_actionPerformed(e);
            }
        });
        runMenu.add(stopMenuItem);


        rewindMenuItem = new JMenuItem("Reset", KeyEvent.VK_R);
        rewindMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rewindMenuItem_actionPerformed(e);
            }
        });
        runMenu.add(rewindMenuItem);

        runMenu.addSeparator();

        breakpointsMenuItem = new JMenuItem("Breakpoints", KeyEvent.VK_B);
        breakpointsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                breakpointsMenuItem_actionPerformed(e);
            }
        });
        runMenu.add(breakpointsMenuItem);


        //TODO: resolution:
        //---------------------------
        ButtonGroup resolutionRadioButtons = new ButtonGroup();

        resolutionSubMenu = new JMenu("Choose Resolution");
        resolutionSubMenu.setMnemonic(KeyEvent.VK_C);
        resolutionMenu.add(resolutionSubMenu);

        resolution_1 = new JRadioButtonMenuItem("Scale 1 - [Classic]");
        resolution_1.setMnemonic(KeyEvent.VK_1);
        resolution_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResolution_1_actionPerformed(e);// perform Action
            }
        });//TODO - add action
        resolutionRadioButtons.add(resolution_1);
        resolutionSubMenu.add(resolution_1);

        resolution_2 = new JRadioButtonMenuItem("Scale 2 - [1280 x 1024]");
        resolution_2.setMnemonic(KeyEvent.VK_2);
        resolution_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResolution_2_actionPerformed(e);// perform Action
            }
        });//TODO - add action
        resolutionRadioButtons.add(resolution_2);
        resolutionSubMenu.add(resolution_2);

        resolution_3 = new JRadioButtonMenuItem("Scale 3 - [1920 x 1080]");
        resolution_3.setMnemonic(KeyEvent.VK_3);
//        resolution_3.setSelected(true); // default selection
        resolution_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResolution_3_actionPerformed(e);// perform Action
            }
        });//TODO - add action
        resolutionRadioButtons.add(resolution_3);
        resolutionSubMenu.add(resolution_3);
        //---------------------------
        //TODO: Color Theme:

        ButtonGroup colorThemeRadioButtons = new ButtonGroup();

        themeSubMenu = new JMenu("Choose Theme");
        themeSubMenu.setMnemonic(KeyEvent.VK_C);
        themeMenu.add(themeSubMenu);

        classic_v3_Theme = new JRadioButtonMenuItem("Classic (3.0)");
        classic_v3_Theme.setMnemonic(KeyEvent.VK_1);
        classic_v3_Theme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTheme_Classic_v3_actionPerformed(e);// perform Action
            }
        });//TODO - add action
        colorThemeRadioButtons.add(classic_v3_Theme);
        themeSubMenu.add(classic_v3_Theme);

        classicTheme = new JRadioButtonMenuItem("Classic");
        classicTheme.setMnemonic(KeyEvent.VK_1);
        classicTheme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTheme_Classic_actionPerformed(e);// perform Action
            }
        });//TODO - add action
        colorThemeRadioButtons.add(classicTheme);
        themeSubMenu.add(classicTheme);

        darkTheme = new JRadioButtonMenuItem("Dark");
        darkTheme.setMnemonic(KeyEvent.VK_2);
        darkTheme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTheme_Dark_actionPerformed(e);// perform Action
            }
        });//TODO - add action
        colorThemeRadioButtons.add(darkTheme);
        themeSubMenu.add(darkTheme);

        //---------------------------


        usageMenuItem = new JMenuItem("Usage", KeyEvent.VK_U);
        usageMenuItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
        usageMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usageMenuItem_actionPerformed(e);
            }
        });
        helpMenu.add(usageMenuItem);

        aboutMenuItem = new JMenuItem("About ...", KeyEvent.VK_A);
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aboutMenuItem_actionPerformed(e);
            }
        });
        helpMenu.add(aboutMenuItem);

    }

    // called when the load script button is pressed.
    private void scriptPressed() {

        Platform.runLater(new Runnable() {//! TODO: WORKS: SCRIPT FILECHOOSER ACTION !!!
            @Override
            public void run() {
                fileChooser.setTitle("Load Script");//set title

                File file = fileChooser.showOpenDialog(null);
                //TODO: my edit
                notifyControllerListeners(ControllerEvent.SCRIPT_CHANGE, file.getAbsoluteFile());
                scriptComponent.setContents(file.getAbsolutePath());
            }
        });


        //OLD CODE
//        int returnVal = fileChooser.showDialog(this, "Load Script");


//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            notifyControllerListeners(ControllerEvent.SCRIPT_CHANGE, fileChooser.getSelectedFile().getAbsoluteFile());
//            scriptComponent.setContents(fileChooser.getSelectedFile().getAbsolutePath());
//        }
    }

    // Initializes this component.
    private void jbInit() {
//        fileChooser.setFileFilter(new ScriptFileFilter());
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Script File", "*.tst"));//TODO test instead // extension filter ! SHOW ONLY .tst Files FOR SCRIPT FILES


        this.getContentPane().setLayout(null);

        Hashtable labelTable = new Hashtable();

        JLabel slowLabel = new JLabel("Slow");
        slowLabel.setForeground(Colors.getInstance().slider_SlowFast);//TODO: My addition: modify here the Colors for Slider Labels
        slowLabel.setFont(Utilities.thinLabelsFont);
        JLabel fastLabel = new JLabel("Fast");
        fastLabel.setFont(Utilities.thinLabelsFont);
        fastLabel.setForeground(Colors.getInstance().slider_SlowFast);//TODO: My addition: modify here the Colors for Slider Labels
        labelTable.put(new Integer(1), slowLabel);
        labelTable.put(new Integer(5), fastLabel);

        speedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SpeedSlider_stateChanged(e);
            }
        });//TODO: Speed Slider settings
        speedSlider.setLabelTable(labelTable);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        speedSlider.setPreferredSize(new Dimension((int) (95 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (50 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        speedSlider.setMinimumSize(new Dimension((int) (95 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (50 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        speedSlider.setToolTipText("Speed");
        speedSlider.setMaximumSize(new Dimension((int) (95 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (50 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));

        loadProgramButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadProgramButton_actionPerformed(e);
            }
        });//TODO: All Top buttons settings
        loadProgramButton.setMaximumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        loadProgramButton.setMinimumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        loadProgramButton.setPreferredSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        loadProgramButton.setSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        loadProgramButton.setToolTipText("Load Program");
        loadProgramButton.setIcon(loadProgramIcon);

        ffwdButton.setMaximumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        ffwdButton.setMinimumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        ffwdButton.setPreferredSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        ffwdButton.setToolTipText("Run");
        ffwdButton.setIcon(ffwdIcon);
        ffwdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ffwdButton_actionPerformed(e);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopButton_actionPerformed(e);
            }
        });
        stopButton.setMaximumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        stopButton.setMinimumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        stopButton.setPreferredSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        stopButton.setToolTipText("Stop");
        stopButton.setIcon(stopIcon);

        rewindButton.setMaximumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        rewindButton.setMinimumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        rewindButton.setPreferredSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        rewindButton.setToolTipText("Reset");
        rewindButton.setIcon(rewindIcon);
        rewindButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rewindButton_actionPerformed(e);
            }
        });

        scriptButton.setMaximumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        scriptButton.setMinimumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        scriptButton.setPreferredSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        scriptButton.setToolTipText("Load Script");
        scriptButton.setIcon(scriptIcon);
        scriptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scriptButton_actionPerformed(e);
            }
        });

        breakButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                breakButton_actionPerformed(e);
            }
        });
        breakButton.setMaximumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        breakButton.setMinimumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        breakButton.setPreferredSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        breakButton.setToolTipText("Open breakpoint panel");
        breakButton.setIcon(breakIcon);

        breakpointWindow.addBreakpointListener(this);

        singleStepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                singleStepButton_actionPerformed(e);
            }
        });
        singleStepButton.setMaximumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        singleStepButton.setMinimumSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        singleStepButton.setPreferredSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        singleStepButton.setSize(new Dimension((int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (39 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));
        singleStepButton.setToolTipText("Single Step");
        singleStepButton.setIcon(singleStepIcon);

        animationCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                animationCombo_actionPerformed(e);
            }
        });

        formatCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formatCombo_actionPerformed(e);
            }
        });

        additionalDisplayCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                additionalDisplayCombo_actionPerformed(e);
            }
        });

        messageLbl.setFont(Utilities.statusLineFont);//TODO: fix font size - need to increase
        messageLbl.setBorder(BorderFactory.createLoweredBevelBorder());

        if(ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._1){
            messageLbl.setBounds(new Rectangle(0,700, (int)(ResolutionUtils.getInstance().RESOLUTION_WIDTH * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (21 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));//! TODO: BOUNDS OF STATUS LINE BAR
        }
        else if(ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._2){
            messageLbl.setBounds(new Rectangle(0, 920, (int)(ResolutionUtils.getInstance().RESOLUTION_WIDTH * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (21 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));//! TODO: BOUNDS OF STATUS LINE BAR
        }
        else if(ResolutionUtils.getInstance().CURRENT_SCALE == ResolutionUtils.Scale._3){
            messageLbl.setBounds(new Rectangle(0, (int)((648 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER * 0.87) + 130), (int)(ResolutionUtils.getInstance().RESOLUTION_WIDTH * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER), (int) (21 * ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER)));//! TODO: BOUNDS OF STATUS LINE BAR
        }




        toolBar = new JToolBar();
        toolBar.setSize(new Dimension(TOOLBAR_WIDTH, TOOLBAR_HEIGHT));
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 0));
        toolBar.setFloatable(false);
        toolBar.setLocation(0, 0);
        toolBar.setBorder(BorderFactory.createEtchedBorder());
        arrangeToolBar();
        this.getContentPane().add(toolBar, null);
        toolBar.revalidate();
        toolBar.repaint();
        repaint();

        // Creating the menu bar
        menuBar = new JMenuBar();
        arrangeMenu();
        setJMenuBar(menuBar);

        this.setDefaultCloseOperation(3);
        this.getContentPane().add(messageLbl, null);

        setControllerSize();

        // sets the frame to be visible.
        setVisible(true);

    }

    /**
     * Called when a choice was made in the additional display combo box.
     */
    public void additionalDisplayCombo_actionPerformed(ActionEvent e) {
        int selectedIndex = additionalDisplayCombo.getSelectedIndex();
        switch (selectedIndex) {
            case HackController.SCRIPT_ADDITIONAL_DISPLAY:
                if (!scriptMenuItem.isSelected())
                    scriptMenuItem.setSelected(true);
                break;

            case HackController.OUTPUT_ADDITIONAL_DISPLAY:
                if (!outputMenuItem.isSelected())
                    outputMenuItem.setSelected(true);
                break;

            case HackController.COMPARISON_ADDITIONAL_DISPLAY:
                if (!compareMenuItem.isSelected())
                    compareMenuItem.setSelected(true);
                break;

            case HackController.NO_ADDITIONAL_DISPLAY:
                if (!noAdditionalDisplayMenuItem.isSelected())
                    noAdditionalDisplayMenuItem.setSelected(true);
                break;
        }

        notifyControllerListeners(ControllerEvent.ADDITIONAL_DISPLAY_CHANGE, new Integer(selectedIndex));
    }

    /**
     * Called when the load program button was pressed.
     */
    public void loadProgramButton_actionPerformed(ActionEvent e) {
        notifyControllerListeners(ControllerEvent.LOAD_PROGRAM, null);
    }

    /**
     * Called when the fast forward button was pressed.
     */
    public void ffwdButton_actionPerformed(ActionEvent e) {
        notifyControllerListeners(ControllerEvent.FAST_FORWARD, null);
    }

    /**
     * Called when the rewind button was pressed.
     */
    public void rewindButton_actionPerformed(ActionEvent e) {
        notifyControllerListeners(ControllerEvent.REWIND, null);
    }

    /**
     * Called when the load script button was pressed.
     */
    public void scriptButton_actionPerformed(ActionEvent e) {
        scriptPressed();
    }

    /**
     * Called when the breakpoints button was pressed.
     */
    public void breakButton_actionPerformed(ActionEvent e) {
        showBreakpoints();
    }

    /**
     * Called when the single step button was pressed.
     */
    public void singleStepButton_actionPerformed(ActionEvent e) {
        notifyControllerListeners(ControllerEvent.SINGLE_STEP, null);
    }

    /**
     * Called when the stop button was pressed.
     */
    public void stopButton_actionPerformed(ActionEvent e) {
        notifyControllerListeners(ControllerEvent.STOP, null);
    }

    /**
     * Called when the speed slider was moved.
     */
    public void SpeedSlider_stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            int speed = (int) source.getValue();
            notifyControllerListeners(ControllerEvent.SPEED_CHANGE, new Integer(speed));
        }
    }

    /**
     * Called when a choice was made in the animation type combo box.
     */
    public void animationCombo_actionPerformed(ActionEvent e) {
        int selectedIndex = animationCombo.getSelectedIndex();
        switch (selectedIndex) {
            case HackController.DISPLAY_CHANGES:
                if (!partAnimMenuItem.isSelected())
                    partAnimMenuItem.setSelected(true);
                break;

            case HackController.ANIMATION:
                if (!fullAnimMenuItem.isSelected())
                    fullAnimMenuItem.setSelected(true);
                break;

            case HackController.NO_DISPLAY_CHANGES:
                if (!noAnimMenuItem.isSelected())
                    noAnimMenuItem.setSelected(true);
                break;
        }

        notifyControllerListeners(ControllerEvent.ANIMATION_MODE_CHANGE, new Integer(selectedIndex));
    }

    /**
     * Called when a choice was made in the numeric format combo box.
     */
    public void formatCombo_actionPerformed(ActionEvent e) {
        int selectedIndex = formatCombo.getSelectedIndex();
        switch (selectedIndex) {
            case HackController.DECIMAL_FORMAT:
                if (!decMenuItem.isSelected())
                    decMenuItem.setSelected(true);
                break;

            case HackController.HEXA_FORMAT:
                if (!hexaMenuItem.isSelected())
                    hexaMenuItem.setSelected(true);
                break;

            case HackController.BINARY_FORMAT:
                if (!binMenuItem.isSelected())
                    binMenuItem.setSelected(true);
                break;
        }

        notifyControllerListeners(ControllerEvent.NUMERIC_FORMAT_CHANGE, new Integer(selectedIndex));
    }

    /**
     * Called when the load program menu item was selected.
     */
    public void programMenuItem_actionPerformed(ActionEvent e) {
        notifyControllerListeners(ControllerEvent.LOAD_PROGRAM, null);
    }

    /**
     * Called when the load script menu item was selected.
     */
    public void scriptMenuItem_actionPerformed(ActionEvent e) {
        scriptPressed();
    }

    /**
     * Called when the exit menu item was selected.
     */
    public void exitMenuItem_actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    /**
     * Called when the animation's "display changes" menu item was selected.
     */
    public void partAnimMenuItem_actionPerformed(ActionEvent e) {
        if (!animationCombo.isSelectedIndex(HackController.DISPLAY_CHANGES))
            animationCombo.setSelectedIndex(HackController.DISPLAY_CHANGES);
    }

    /**
     * Called when the animation's "animate" menu item was selected.
     */
    public void fullAnimMenuItem_actionPerformed(ActionEvent e) {
        if (!animationCombo.isSelectedIndex(HackController.ANIMATION))
            animationCombo.setSelectedIndex(HackController.ANIMATION);
    }

    /**
     * Called when the animation's "no animation" menu item was selected.
     */
    public void noAnimMenuItem_actionPerformed(ActionEvent e) {
        if (!animationCombo.isSelectedIndex(HackController.NO_DISPLAY_CHANGES))
            animationCombo.setSelectedIndex(HackController.NO_DISPLAY_CHANGES);
    }

    /**
     * Called when the numeric format's "decimal" menu item was selected.
     */
    public void decMenuItem_actionPerformed(ActionEvent e) {
        if (!formatCombo.isSelectedIndex(HackController.DECIMAL_FORMAT))
            formatCombo.setSelectedIndex(HackController.DECIMAL_FORMAT);
    }

    /**
     * Called when the numeric format's "hexa" menu item was selected.
     */
    public void hexaMenuItem_actionPerformed(ActionEvent e) {
        if (!formatCombo.isSelectedIndex(HackController.HEXA_FORMAT))
            formatCombo.setSelectedIndex(HackController.HEXA_FORMAT);
    }

    /**
     * Called when the numeric format's "binary" menu item was selected.
     */
    public void binMenuItem_actionPerformed(ActionEvent e) {
        if (!formatCombo.isSelectedIndex(HackController.BINARY_FORMAT))
            formatCombo.setSelectedIndex(HackController.BINARY_FORMAT);
    }

    /**
     * Called when the additonal display's "script" menu item was selected.
     */
    public void scriptDisplayMenuItem_actionPerformed(ActionEvent e) {
        if (!additionalDisplayCombo.isSelectedIndex(HackController.SCRIPT_ADDITIONAL_DISPLAY))
            additionalDisplayCombo.setSelectedIndex(HackController.SCRIPT_ADDITIONAL_DISPLAY);
    }

    /**
     * Called when the additonal display's "output" menu item was selected.
     */
    public void outputMenuItem_actionPerformed(ActionEvent e) {
        if (!additionalDisplayCombo.isSelectedIndex(HackController.OUTPUT_ADDITIONAL_DISPLAY))
            additionalDisplayCombo.setSelectedIndex(HackController.OUTPUT_ADDITIONAL_DISPLAY);
    }

    /**
     * Called when the additonal display's "comparison" menu item was selected.
     */
    public void compareMenuItem_actionPerformed(ActionEvent e) {
        if (!additionalDisplayCombo.isSelectedIndex(HackController.COMPARISON_ADDITIONAL_DISPLAY))
            additionalDisplayCombo.setSelectedIndex(HackController.COMPARISON_ADDITIONAL_DISPLAY);
    }

    /**
     * Called when the additonal display's "no display" menu item was selected.
     */
    public void noAdditionalDisplayMenuItem_actionPerformed(ActionEvent e) {
        if (!additionalDisplayCombo.isSelectedIndex(HackController.NO_ADDITIONAL_DISPLAY))
            additionalDisplayCombo.setSelectedIndex(HackController.NO_ADDITIONAL_DISPLAY);
    }

    /**
     * Called when the single step menu item was selected.
     */
    public void singleStepMenuItem_actionPerformed(ActionEvent e) {
        notifyControllerListeners(ControllerEvent.SINGLE_STEP, null);
    }

    /**
     * Called when the fast forward menu item was selected.
     */
    public void ffwdMenuItem_actionPerformed(ActionEvent e) {
        notifyControllerListeners(ControllerEvent.FAST_FORWARD, null);
    }

    /**
     * Called when the stop menu item was selected.
     */
    public void stopMenuItem_actionPerformed(ActionEvent e) {
        notifyControllerListeners(ControllerEvent.STOP, null);
    }

    /**
     * Called when the rewind menu item was selected.
     */
    public void rewindMenuItem_actionPerformed(ActionEvent e) {
        notifyControllerListeners(ControllerEvent.REWIND, null);
    }

    /**
     * Called when the breakpoints menu item was selected.
     */
    public void breakpointsMenuItem_actionPerformed(ActionEvent e) {
        showBreakpoints();
    }

    //-----------------------------------
    //TODO: resolution radio

    /**
     * Called when the resolution "Scale 1" menu item was selected.
     */
    public void setResolution_1_actionPerformed(ActionEvent e) {
        // set Scale:

//        ResolutionUtils.getInstance().setResolutionScale(ResolutionUtils.Scale._1);
        // remove old JPanels/JFrames and add new JPanels/JFrames instead --- RESTART GUI:
//        HardwareSimulatorApplication_Singleton.remake();

        dispose(); // destroy current display
        new HardwareSimulator_FakeMain(ResolutionUtils.Scale._1); // restart main with new resolution Scale
    }

    /**
     * Called when the resolution "Scale 2" menu item was selected.
     */
    public void setResolution_2_actionPerformed(ActionEvent e) {
        // set Scale:
//        ResolutionUtils.getInstance().setResolutionScale(ResolutionUtils.Scale._2);
        // remove old JPanels/JFrames and add new JPanels/JFrames instead --- RESTART GUI:
//        HardwareSimulatorApplication_Singleton.remake();

        dispose(); // destroy current display
        new HardwareSimulator_FakeMain(ResolutionUtils.Scale._2); // restart main with new resolution Scale
    }

    /**
     * Called when the resolution "Scale 3" menu item was selected.
     */
    public void setResolution_3_actionPerformed(ActionEvent e) {
        // set Scale:
//        ResolutionUtils.getInstance().setResolutionScale(ResolutionUtils.Scale._3);
        // remove old JPanels/JFrames and add new JPanels/JFrames instead --- RESTART GUI:
//        HardwareSimulatorApplication_Singleton.remake();

        dispose(); // destroy current display
        new HardwareSimulator_FakeMain(ResolutionUtils.Scale._3); // restart main with new resolution Scale
    }
    //-----------------------------------

    //-----------------------------------
    //TODO: Color Theme

    /**
     * Called when the resolution "Classic (3.0)" menu item was selected.
     */
    public void setTheme_Classic_v3_actionPerformed(ActionEvent e) {

        dispose(); // destroy current display
        new HardwareSimulator_FakeMain(Colors.Theme.Classic_v3); // restart main with new Color Theme
    }

    /**
     * Called when the resolution "Classic" menu item was selected.
     */
    public void setTheme_Classic_actionPerformed(ActionEvent e) {

        dispose(); // destroy current display
        new HardwareSimulator_FakeMain(Colors.Theme.Classic); // restart main with new Color Theme
    }

    /**
     * Called when the resolution "Dark" menu item was selected.
     */
    public void setTheme_Dark_actionPerformed(ActionEvent e) {

        dispose(); // destroy current display
        new HardwareSimulator_FakeMain(Colors.Theme.Dark); // restart main with new Color Theme
    }

    //-----------------------------------


    /**
     * Called when the usage window menu item was selected.
     */
    public void usageMenuItem_actionPerformed(ActionEvent e) {
        if (usageWindow != null)
            usageWindow.setVisible(true);
    }

    /**
     * Called when the about window menu item was selected.
     */
    public void aboutMenuItem_actionPerformed(ActionEvent e) {
        if (aboutWindow != null)
            aboutWindow.setVisible(true);
    }
}
