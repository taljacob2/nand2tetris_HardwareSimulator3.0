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

import Colors.Colors;
import Hack.Controller.*;
import Hack.HardwareSimulator.*;
import ResolutionUtils.HardwareSimulatorApplication_Singleton;
import SimulatorsGUI.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;


/**
 * The Hardware Simulator.
 */
public class HardwareSimulatorMain extends Application {//TODO: CHANGE TO JAVAFX
    /**
     * The command line Hardware Simulator program.
     */

    //XTODO: making it public static Singleton:
    public static HardwareSimulatorApplication_Singleton application;

//    public static void main(String[] args) {
//
//        launch(args);
//    }


    @Override
    public void start(Stage primaryStage) throws Exception {

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

