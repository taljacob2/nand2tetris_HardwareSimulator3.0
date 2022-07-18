package ResolutionUtils;

import Colors.Colors;
import Hack.HardwareSimulator.HardwareSimulatorApplication;
import Hack.HardwareSimulator.HardwareSimulatorControllerGUI;
import Hack.HardwareSimulator.HardwareSimulatorGUI;
import HackGUI.Utilities;
import SimulatorsGUI.HardwareSimulatorComponent;
import SimulatorsGUI.HardwareSimulatorControllerComponent;

import javax.swing.*;


/* important: This class enables the program to restart ! */
public class HardwareSimulator_FakeMain{

    //TODO: making it public static Singleton:
    public static HardwareSimulatorApplication_Singleton application;

    public HardwareSimulator_FakeMain(ResolutionUtils.Scale scaleEnum) {
        ResolutionUtils.getInstance().setResolutionScale(scaleEnum); //! Important: sets the Resolution Scale !
        new Utilities();//! Important: re-new all FONTS !
        // After setting the resolution scale, restart main:
        fakeMain();
    }

    public HardwareSimulator_FakeMain(Colors.Theme theme) {
        Colors.getInstance().setTheme(theme); //! Important: sets the Color Theme !
        // After setting the theme, restart main:
        fakeMain();
    }

    public void fakeMain() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // set looks like the systems' look
        } catch (Exception e) {
        }

        HardwareSimulatorGUI simulatorGUI = new HardwareSimulatorComponent();//TODO: extends JPanel
        HardwareSimulatorControllerGUI controllerGUI = new HardwareSimulatorControllerComponent();//TODO: extends JFrame

        application =
                new HardwareSimulatorApplication_Singleton(controllerGUI, simulatorGUI,
                        "bin/scripts/defaultHW.txt",
                        "bin/help/hwUsage.html", "bin/help/hwAbout.html");
    }
}
