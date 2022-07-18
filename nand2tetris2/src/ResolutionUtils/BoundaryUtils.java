package ResolutionUtils;

/* this class bundles all the boundaries settings for the Components, with the dependency
 * of the current Resolution picked.
 * */

public class BoundaryUtils {

    public static class X{
        public static int firstTitle = 11;
//        public static int table_inputPins_OutputPins = 500;
        public static int SPACE_BETWEEN_TABLES = 5;
//        public static int endLocationOfOutputTable;
        public static int scriptTextTable_Scale_1 = 0;
        public static int scriptTextTable_Scale_2 = 210;
        public static int scriptTextTable_Scale_3 = 600;
    }
    public static class Y {
        public static int top_ChipName_Time_bar = 9;
        public static int top_ChipName_Time_bar_LABEL = 11;
        public static int table_inputPins_OutputPins = 45;
        public static int table_HDL_InternalPins_PartPins_Parts = 332;
        public static int statusLine_Scale_2 = 332;
    }
    public static class Width {

    }
    public static class Height {
        public static int top_ChipName_Time_bar = 21;
        public static int top_ChipName_Time_bar_TEXTSECTION = 28;
        public static int scriptTextTable = 590;
        public static int table_HDL_DOWNSCALE_PARAMETER_Scale_1 = 2;
        public static int table_HDL_DOWNSCALE_PARAMETER_Scale_2 = 0;
        public static int table_HDL_DOWNSCALE_PARAMETER_Scale_3 = 11;

        public static int table_InternalPins_PartPins_Parts_PARAMETER_Scale_1 = - 6 * ResolutionUtils.getInstance().ROW_HEIGHT;
        public static int table_InternalPins_PartPins_Parts_PARAMETER_Scale_2 = 10 * ResolutionUtils.getInstance().ROW_HEIGHT;
        public static int table_InternalPins_PartPins_Parts_PARAMETER_Scale_3 = 1 * ResolutionUtils.getInstance().ROW_HEIGHT;
    }

    /* ! you must use the 'calc' method in order to get the correct Boundary that depends on the Resolution Scale: !*/
    public static int calc(int whatToGet) {
        return whatToGet * (int) (ResolutionUtils.getInstance().RESOLUTION_SCALE_PARAMETER);
    }
}
