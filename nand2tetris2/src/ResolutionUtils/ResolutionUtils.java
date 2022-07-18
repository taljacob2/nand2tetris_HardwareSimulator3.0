package ResolutionUtils;

import java.awt.*;

/* This class should be implemented as a Singleton */
public class ResolutionUtils {

    // --------------Singleton implementation--------------
    // static variable single_instance of type ResolutionUtils
    private static ResolutionUtils single_instance = null;

    // private constructor restricted to this class itself
    private ResolutionUtils() {
        setResolutionScale(Scale._3); // ! Default value is scale 3
    }

    // static method to create instance of ResolutionUtils class
    public static ResolutionUtils getInstance() {
        if (single_instance == null)
            single_instance = new ResolutionUtils();

        return single_instance;
    }

//    public ResolutionUtils(float resolutionScale) {
//        setResolutionScale(resolutionScale);
//    }

    // --------------Fields--------------

    // for Scale translation:
    public enum Scale {_1, _2, _3}

    // parameter that modifies the current resolution up-scale:
    public float RESOLUTION_SCALE_PARAMETER;

    // this secondary parameter is used as a fixer, for large scale resolutions. To fix things like font being too large than intended.
    private float RESOLUTION_DECREASE_FONT_SCALE_PARAMETER;

    // this third parameter is used as a fixer, for large scale resolutions. To fix things like table height being too large than intended.
    private float RESOLUTION_DECREASE_TABLE_SCALE_PARAMETER = 70;

    // for RESOLUTION_FONT_SCALE_PARAMETER:
    public float RESOLUTION_FONT_SCALE_PARAMETER;

    // for RESOLUTION_TABLE_SCALE_PARAMETER:
    public float RESOLUTION_TABLE_SCALE_PARAMETER;

    // for RESOLUTION_DIMENSION:
    public Dimension RESOLUTION_DIMENSION;

    // for RESOLUTION_WIDTH + RESOLUTION_HEIGHT:
    public int RESOLUTION_WIDTH;
    public int RESOLUTION_HEIGHT;

    // for Current Scale hold:
    public Scale CURRENT_SCALE;

    // for modify each Row Height :
    public byte ROW_HEIGHT;

    // --------------Methods--------------


    // Sets:

    /* this method calculates RESOLUTION_FINAL_SCALE_PARAMETER */
    private void calcResolutionTOTALScaleParameters() {
        RESOLUTION_FONT_SCALE_PARAMETER = RESOLUTION_SCALE_PARAMETER * RESOLUTION_DECREASE_FONT_SCALE_PARAMETER;
        RESOLUTION_TABLE_SCALE_PARAMETER = RESOLUTION_SCALE_PARAMETER * RESOLUTION_DECREASE_TABLE_SCALE_PARAMETER;
        ROW_HEIGHT = (byte)(20 * RESOLUTION_FONT_SCALE_PARAMETER);
    }

    /* set resolutionDecrease_scale */
    private void setResolutionDecreaseScaleParameter() {
        // set the DECREASE scale:
        if (RESOLUTION_SCALE_PARAMETER == ResolutionScale._1F) {// set program resolution as it was on version 2.5
            CURRENT_SCALE = Scale._1;
            RESOLUTION_DECREASE_FONT_SCALE_PARAMETER = ResolutionScale._FONT_DECREASE_1F;
            RESOLUTION_DECREASE_TABLE_SCALE_PARAMETER = ResolutionScale._TABLE_DECREASE_1F;
            RESOLUTION_WIDTH = ResolutionScale.WIDTH_1F;
            RESOLUTION_HEIGHT = ResolutionScale.HEIGHT_1F;
            RESOLUTION_DIMENSION = ResolutionScale._DIMENSION_1F_1024_1024;

        }
        else if (RESOLUTION_SCALE_PARAMETER == ResolutionScale._1_2F) {
            CURRENT_SCALE = Scale._2;
            RESOLUTION_DECREASE_FONT_SCALE_PARAMETER = ResolutionScale._FONT_DECREASE_1_2F;
            RESOLUTION_DECREASE_TABLE_SCALE_PARAMETER = ResolutionScale._TABLE_DECREASE_1_2F;
            RESOLUTION_WIDTH = ResolutionScale.WIDTH_1_2F;
            RESOLUTION_HEIGHT = ResolutionScale.HEIGHT_1_2F;
            RESOLUTION_DIMENSION = ResolutionScale._DIMENSION_1_2F_1280_720;
        }
        else if (RESOLUTION_SCALE_PARAMETER == ResolutionScale._1_5F) {
            CURRENT_SCALE = Scale._3;
            RESOLUTION_DECREASE_FONT_SCALE_PARAMETER = ResolutionScale._FONT_DECREASE_1_5F;
            RESOLUTION_DECREASE_TABLE_SCALE_PARAMETER = ResolutionScale._TABLE_DECREASE_1_5F;
            RESOLUTION_WIDTH = ResolutionScale.WIDTH_1_5F;
            RESOLUTION_HEIGHT = ResolutionScale.HEIGHT_1_5F;
            RESOLUTION_DIMENSION = ResolutionScale._DIMENSION_1_5F_1920_1080;
        }
    }

    /* user should use one of the ResolutionScale parameters as an argument here: */
    public void setResolutionScale(Scale resolutionScale) {

        RESOLUTION_SCALE_PARAMETER = ResolutionScale.scale(resolutionScale); // set the scale:

        setResolutionDecreaseScaleParameter(); // set the DECREASE scale:
        calcResolutionTOTALScaleParameters(); //calculate FINAL_SCALE
    }

}
