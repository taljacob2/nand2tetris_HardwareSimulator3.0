package ResolutionUtils;

import Hack.HardwareSimulator.HardwareSimulatorApplication;
import Hack.HardwareSimulator.HardwareSimulatorControllerGUI;
import Hack.HardwareSimulator.HardwareSimulatorGUI;
import javafx.stage.Stage;

/*This class should be implemented as a Singleton.
 * This class represents the main JFrame & JPanel created in "HardwareSimulatorMain".
 * Thanks to it being a Singleton we can recall when Scaling the Resolution of the screen, on:
 * 'setResolution_1_actionPerformed'
 * 'setResolution_2_actionPerformed'
 * 'setResolution_3_actionPerformed'
 * */

public class HardwareSimulatorApplication_Singleton extends HardwareSimulatorApplication {

    // Saved parameters for future 'remake' method:
    private static HardwareSimulatorControllerGUI _controllerComponent;
    private static HardwareSimulatorGUI _simulatorComponent;
    private static String _defaultScript;
    private static String _contentsFileName;
    private static String _aboutFileName;

    // --------------Singleton implementation--------------
    // static variable single_instance of type HardwareSimulatorApplication_Singleton
    private static HardwareSimulatorApplication_Singleton single_instance = null;

    /* constructor restricted to this class itself
       originally intended to be private. */
    public HardwareSimulatorApplication_Singleton(HardwareSimulatorControllerGUI controllerComponent,
                                                  HardwareSimulatorGUI simulatorComponent,
                                                  String defaultScript, String contentsFileName,
                                                  String aboutFileName) {


        super(controllerComponent,
                simulatorComponent,
                defaultScript, contentsFileName,
                aboutFileName);

        // Save all the parameters for future 'remake' method:
        _controllerComponent = controllerComponent;
        _simulatorComponent = simulatorComponent;
        _defaultScript = defaultScript;
        _contentsFileName = contentsFileName;
        _aboutFileName = aboutFileName;
    }

    // static method to create instance of HardwareSimulatorApplication_Singleton class
    public static HardwareSimulatorApplication_Singleton getInstance() {

        /*! TAKE CAUTION THAT THE INSTANCE IS NOT BEING CONSTRUCTED BY ITSELF !
          ! THUS YOU MUST CONSTRUCT THE OBJECT FIRST, AND ONLY THEN USE THE 'getInstance' METHOD !
        */

//        if (single_instance == null)
//            single_instance = new HardwareSimulatorApplication_Singleton();

        return single_instance;
    }

    /* remake object */
//    public static void remake() {
//        new HardwareSimulatorApplication_Singleton(_controllerComponent, _simulatorComponent, _defaultScript, _contentsFileName, _aboutFileName);
//    }

    /* destroys object */
    public static void destroy() {

    }

    // Set:

    /* This method sets again the instance of this 'HardwareSimulatorApplication_Singleton'
       with another instance of 'HardwareSimulatorApplication_Singleton' */
//    public static void setInstance(HardwareSimulatorApplication_Singleton other_HardwareSimulatorApplication_Singleton){
//        single_instance = other_HardwareSimulatorApplication_Singleton.single_instance;
//    }


}
