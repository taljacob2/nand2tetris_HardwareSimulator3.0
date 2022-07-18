package ResolutionUtils;

import java.awt.*;

/*
    this class groups together the static scales available for resolution.
    meant to be used internally, and accessed only for ResolutionUtils requirements.
    Thus all access modifiers are default.
 */
class ResolutionScale {

    // for RESOLUTION_SCALE_PARAMETER:
    static final float _1F = 1F; // multiply by 1, means to do nothing
    static final float _1_2F = 1.2F;
    static final float _1_5F = 1.5F;

    // for RESOLUTION_DECREASE_FONT_SCALE_PARAMETER:
    static final float _FONT_DECREASE_1F = 1F; // multiply by 1, means to do nothing
    static final float _FONT_DECREASE_1_2F = 0.9F;
    static final float _FONT_DECREASE_1_5F = 0.8F;

    // for RESOLUTION_DECREASE_TABLE_SCALE_PARAMETER:
    static final float _TABLE_DECREASE_1F = 1F; // multiply by 1, means to do nothing
    static final float _TABLE_DECREASE_1_2F = 35;
    static final float _TABLE_DECREASE_1_5F = 70;

    // for RESOLUTION_DIMENSION + RESOLUTION_WIDTH + RESOLUTION_HEIGHT:
    static final int WIDTH_1F = 1100;//Standard size ---- I THINK THAT THE ORIGINAL CLASSIC RESOLUTION WAS 1024 X 741
    static final int WIDTH_1_2F = 1280;
    static final int WIDTH_1_5F = 1920;
    static final int HEIGHT_1F = 800;//Standard size
    static final int HEIGHT_1_2F = 1024;
    static final int HEIGHT_1_5F = 1080;

    static final Dimension _DIMENSION_1F_1024_1024 = new Dimension(WIDTH_1F, HEIGHT_1F); //TODO: I guess
    static final Dimension _DIMENSION_1_2F_1280_720 = new Dimension(WIDTH_1_2F, HEIGHT_1_2F);;
    static final Dimension _DIMENSION_1_5F_1920_1080 = new Dimension(WIDTH_1_5F, HEIGHT_1_5F);;

    // this method converts enum scale to float scale:
    static float scale(ResolutionUtils.Scale scaleEnum) {

        if (scaleEnum == ResolutionUtils.Scale._1) {
            return ResolutionScale._1F;
        }
        else if (scaleEnum == ResolutionUtils.Scale._2) {
            return ResolutionScale._1_2F;
        }
        else if (scaleEnum == ResolutionUtils.Scale._3) {
            return ResolutionScale._1_5F;
        }
        else return ResolutionScale._1F; // default value
    }
}
