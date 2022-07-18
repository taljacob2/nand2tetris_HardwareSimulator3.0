package Colors;

import java.awt.*;

/* This class bundles all the Color needs
 * Should be implemented as a Singleton.
 * */
public class Colors {

    // --------------Singleton implementation--------------
    // static variable single_instance of type Colors
    private static Colors single_instance = null;

    // private constructor restricted to this class itself
    private Colors() {
        setTheme(Theme.Classic_v3); // ! Set Default Theme is here !
    }

    // static method to create instance of Colors class
    public static Colors getInstance() {
        if (single_instance == null)
            single_instance = new Colors();

        return single_instance;
    }

    // --------------Fields--------------

    // Define here all the Themes available:
    public enum Theme {Classic, Classic_v3, Dark}

    // for Current Theme hold: to determine what Theme is active:
    public static Theme CURRENT_THEME;

    //---------Main Background---------
    private static final Color dark_backgroundColor = new Color(64, 64, 64);
    private static final Color classic_backgroundColor = null; //TODO: MY GUESS! --- this should be the default color of JFrame !
    private static final Color classic_v3_backgroundColor = Color.white; //TODO: MY GUESS! --- this should be the default color of JFrame !
    //---------Main Background---------
    private static final Color classic_markedDigits = Color.blue;//TODO: original classic was Color.blue - but I changed it on purpose
    private static final Color classic_v3_markedDigits = Color.red;//TODO: original classic was Color.blue - but I changed it on purpose
    private static final Color dark_markedDigits = Color.red;
    private static final Color classic_backgroundColorOfTTextField = SystemColor.info;
    private static final Color dark_backgroundColorOfTextField = new Color(187, 255, 131, 255);
    private static final Color classic_binaryUIBackgroundDisabled = Color.darkGray;
    private static final Color dark_binaryUIBackgroundDisabled = new Color(16, 100, 21, 255);
    private static final Color classic_binaryUIBackgroundEnabled = Color.white;
    private static final Color dark_binaryUIBackgroundEnabled = dark_backgroundColorOfTextField;
    private static final Color classic_disabledTextForeground = Color.black;// default is black
    private static final Color dark_disabledTextForeground = Color.black;
    ;
    private static final Color classic_textForeground = Color.black;//TODO: MY GUESS!
    private static final Color classic_textBackground = Color.white;
    private static final Color dark_textForeground = Color.white;//TODO: MY GUESS!
    private static final Color dark_textBackground = Color.black;
    private static final Color classic_binaryUILineBorder = Color.black;
    private static final Color dark_binaryUILineBorder = Color.white;
    private static final Color classic_screenBackground = Color.white;
    private static final Color dark_screenBackground = Color.black;
    private static final Color classic_hideFlash = Color.blue;//TODO: I DON'T KNOW WHAT THIS IS ! it was Color.white
    private static final Color dark_hideFlash = Color.blue;//TODO: I DON'T KNOW WHAT THIS IS ! it was Color.black
    private static final Color classic_enabledRange = Color.white;
    private static final Color dark_enabledRange = Color.black;
    private static final Color classic_labelText = Color.black;
    private static final Color classic_v3_labelText = new Color(0, 126, 165);
    private static final Color dark_labelText =  new Color(116, 190, 102);
    private static final Color classic_tableHeaderText = Color.black;//PROBABLY UNUSED
    private static final Color dark_tableHeaderText = new Color(154, 255, 133);
    private static final Color classic_tableHeaderBackground = Color.white;//PROBABLY UNUSED
    private static final Color dark_tableHeaderBackground = Color.gray;
    private static final Color classic_statusLineError = Color.red;
    private static final Color dark_statusLineError = new Color(255, 54, 54);
    private static final Color classic_top_ChipName_Time_Foreground = classic_labelText;
    private static final Color classic_v3_top_ChipName_Time_Foreground = classic_v3_labelText;
    private static final Color dark_top_ChipName_Time_Foreground = dark_tableHeaderText;
    private static final Color classic_top_TimeLabel_Foreground = classic_textForeground;
    private static final Color dark_top_TimeLabel_Foreground = dark_textForeground;
    private static final Color classic_comboAnimateFormatView = classic_labelText;
    private static final Color classic_v3_comboAnimateFormatView = classic_v3_labelText;
    private static final Color dark_comboAnimateFormatView = dark_tableHeaderText;
    private static final Color classic_slider_SlowFast = classic_labelText;
    private static final Color classic_v3_slider_SlowFast = classic_v3_labelText;
    private static final Color dark_slider_SlowFast = dark_tableHeaderText;
    private static final Color classic_partPins_ChipLabel = Color.red;
    private static final Color dark_partPins_ChipLabel = dark_statusLineError;


    //Table Script:
    private static final Color classic_scriptTableBackgroundSelectedRow = Color.yellow;
    private static final Color dark_scriptTableBackgroundSelectedRow = new Color(16, 110, 0);
    private static final Color classic_scriptTableBackgroundUnselectedRow = Color.white;
    private static final Color dark_scriptTableBackgroundUnselectedRow = dark_backgroundColor;
    private static final Color classic_scriptTableTextForeground = classic_textForeground;
    private static final Color dark_scriptTableTextForeground = dark_textForeground;

    //Table Part Pins:
    private static final Color classic_partPinsTable_Partpin_and_Gatepin_ColumnsBackgroundRow = Color.white;
    private static final Color dark_partPinsTable_Partpin_and_Gatepin_ColumnsBackgroundRow = dark_backgroundColor;
    private static final Color classic_partPinsTable_Value_ColumnBackgroundRow = Color.white;//TODO: original classic was Color.white - but I changed it just for beauty
    private static final Color classic_v3_partPinsTable_Value_ColumnBackgroundRow = new Color(172, 221, 255);//TODO: original classic was Color.white - but I changed it just for beauty
    private static final Color dark_partPinsTable_Value_ColumnBackgroundRow = new Color(107, 177, 88);
    private static final Color classic_partPinsTable_textForeground = classic_textForeground;
    private static final Color dark_partPinsTable_textForeground = dark_textForeground;

    //Tables: Input Pins & Output Pins:
    private static final Color classic_inputOutputPinsTables_Name_ColumnBackgroundRow = Color.white;
    private static final Color dark_inputOutputPinsTables_Name_ColumnBackgroundRow = dark_backgroundColor;
    private static final Color classic_inputOutputPinsTables_Value_ColumnBackgroundRow = classic_partPinsTable_Value_ColumnBackgroundRow;
    private static final Color classic_v3_inputOutputPinsTables_Value_ColumnBackgroundRow = classic_v3_partPinsTable_Value_ColumnBackgroundRow;
    private static final Color dark_inputOutputPinsTables_Value_ColumnBackgroundRow = dark_partPinsTable_Value_ColumnBackgroundRow;
    private static final Color classic_inputOutputPinsTables_TextForeground = classic_textForeground;
    private static final Color dark_inputOutputPinsTables_TextForeground = dark_textForeground;

    //Table 'HDL':
    private static final Color classic_hdlTableBackgroundRow = Color.white;
    private static final Color dark_hdlTableBackgroundRow = dark_backgroundColor;
    private static final Color classic_hdlTableTextForeground = classic_textForeground;
    private static final Color dark_hdlTableTextForeground = dark_textForeground;

//    //3 Top buttons:
//    private static final Color classic_viewButtonTitleBackground = SystemColor.LIGHT_GRAY;//TODO: checking
//    private static final Color classic_formatButtonTitleBackground = classic_backgroundColorOfTTextField;//TODO: checking
//    private static final Color classic_animateButtonTitleBackground = classic_backgroundColorOfTTextField;//TODO: checking
//    private static final Color dark_viewButtonTitle = SystemColor.LIGHT_GRAY;//TODO: checking
//    private static final Color dark_formatButtonTitle = Color.lightGray;//TODO: checking
//    private static final Color dark_animateButtonTitle = Color.lightGray;//TODO: checking


    public static Color backgroundColorOfTextField;
    public static Color binaryUIBackgroundDisabled;
    public static Color binaryUIBackgroundEnabled;
    public static Color markedDigits;
    public static Color disabledTextForeground;
    public static Color binaryUILineBorder;
    public static Color screenBackground;
    public static Color textForeground;
    public static Color textBackground;
    public static Color hideFlash;
    public static Color enabledRange;
    public static Color viewButtonTitleBackground;
    public static Color formatButtonTitleBackground;
    public static Color animateButtonTitleBackground;
    public static Color scriptTableBackgroundSelectedRow;
    public static Color scriptTableBackgroundUnselectedRow;
    public static Color scriptTableTextForeground;
    public static Color partPinsTable_Partpin_and_Gatepin_ColumnsBackgroundRow;
    public static Color partPinsTable_Value_ColumnBackgroundRow;
    public static Color partPinsTable_textForeground;
    public static Color inputOutputPinsTables_Name_ColumnBackgroundRow;
    public static Color inputOutputPinsTables_Value_ColumnBackgroundRow;
    public static Color inputOutputPinsTables_TextForeground;
    public static Color hdlTableBackgroundRow;
    public static Color hdlTableTextForeground;
    public static Color labelText;
    public static Color tableHeaderText;
    public static Color tableHeaderBackground;
    public static Color statusLineError;
    public static Color top_ChipNameLabel_Foreground;
    public static Color top_TimeLabel_Foreground;
    public static Color comboAnimateFormatView;
    public static Color slider_SlowFast;
    public static Color partPins_ChipLabel;

    //---------Main Background---------
    public static Color backgroundColor;
    //---------Main Background---------

    // --------------Methods--------------

    private void extractBasicTheme(Theme theme) {
        // Set theme:
        if (theme == Theme.Classic) {// set all colors to Classic theme:
            CURRENT_THEME = Theme.Classic;
            backgroundColorOfTextField = classic_backgroundColorOfTTextField;
            binaryUIBackgroundDisabled = classic_binaryUIBackgroundDisabled;
            binaryUIBackgroundEnabled = classic_binaryUIBackgroundEnabled;
            markedDigits = classic_markedDigits;
            disabledTextForeground = classic_disabledTextForeground;
            textBackground = classic_textBackground;
            binaryUILineBorder = classic_binaryUILineBorder;
            screenBackground = classic_screenBackground;
            hideFlash = classic_hideFlash;
            enabledRange = classic_enabledRange;
            backgroundColor = classic_backgroundColor;
//            viewButtonTitleBackground = classic_viewButtonTitleBackground;
//            formatButtonTitleBackground = classic_formatButtonTitleBackground;
//            animateButtonTitleBackground = classic_animateButtonTitleBackground;
            scriptTableBackgroundSelectedRow = classic_scriptTableBackgroundSelectedRow;
            scriptTableTextForeground = classic_scriptTableTextForeground;
            scriptTableBackgroundUnselectedRow = classic_scriptTableBackgroundUnselectedRow;
            partPinsTable_Partpin_and_Gatepin_ColumnsBackgroundRow = classic_partPinsTable_Partpin_and_Gatepin_ColumnsBackgroundRow;
            partPinsTable_Value_ColumnBackgroundRow = classic_partPinsTable_Value_ColumnBackgroundRow;
            partPinsTable_textForeground = classic_partPinsTable_textForeground;
            inputOutputPinsTables_Name_ColumnBackgroundRow = classic_inputOutputPinsTables_Name_ColumnBackgroundRow;
            inputOutputPinsTables_Value_ColumnBackgroundRow = classic_inputOutputPinsTables_Value_ColumnBackgroundRow;
            inputOutputPinsTables_TextForeground = classic_inputOutputPinsTables_TextForeground;
            hdlTableBackgroundRow = classic_hdlTableBackgroundRow;
            hdlTableTextForeground = classic_hdlTableTextForeground;
            labelText = classic_labelText;
            tableHeaderText = classic_tableHeaderText;
            tableHeaderBackground = classic_tableHeaderBackground;
            statusLineError = classic_statusLineError;
            top_ChipNameLabel_Foreground = classic_top_ChipName_Time_Foreground;
            top_TimeLabel_Foreground = classic_top_TimeLabel_Foreground;
            comboAnimateFormatView = classic_comboAnimateFormatView;
            slider_SlowFast = classic_slider_SlowFast;
            partPins_ChipLabel = classic_partPins_ChipLabel;
        }
        else if (theme == Theme.Dark) {// set all colors to Dark theme:
            CURRENT_THEME = Theme.Dark;
            backgroundColorOfTextField = dark_backgroundColorOfTextField;
            binaryUIBackgroundDisabled = dark_binaryUIBackgroundDisabled;
            binaryUIBackgroundEnabled = dark_binaryUIBackgroundEnabled;
            markedDigits = dark_markedDigits;
            disabledTextForeground = dark_disabledTextForeground;
            textBackground = dark_textBackground;
            binaryUILineBorder = dark_binaryUILineBorder;
            screenBackground = dark_screenBackground;
            hideFlash = dark_hideFlash;
            enabledRange = dark_enabledRange;
            backgroundColor = dark_backgroundColor;
//            viewButtonTitleBackground = dark_viewButtonTitle;
//            formatButtonTitleBackground = dark_formatButtonTitle;
//            animateButtonTitleBackground = dark_animateButtonTitle;
            scriptTableBackgroundSelectedRow = dark_scriptTableBackgroundSelectedRow;
            scriptTableTextForeground = dark_scriptTableTextForeground;
            scriptTableBackgroundUnselectedRow = dark_scriptTableBackgroundUnselectedRow;
            partPinsTable_Partpin_and_Gatepin_ColumnsBackgroundRow = dark_partPinsTable_Partpin_and_Gatepin_ColumnsBackgroundRow;
            partPinsTable_Value_ColumnBackgroundRow = dark_partPinsTable_Value_ColumnBackgroundRow;
            partPinsTable_textForeground = dark_partPinsTable_textForeground;
            inputOutputPinsTables_Name_ColumnBackgroundRow = dark_inputOutputPinsTables_Name_ColumnBackgroundRow;
            inputOutputPinsTables_Value_ColumnBackgroundRow = dark_inputOutputPinsTables_Value_ColumnBackgroundRow;
            inputOutputPinsTables_TextForeground = dark_inputOutputPinsTables_TextForeground;
            hdlTableBackgroundRow = dark_hdlTableBackgroundRow;
            hdlTableTextForeground = dark_hdlTableTextForeground;
            labelText = dark_labelText;
            tableHeaderText = dark_tableHeaderText;
            tableHeaderBackground = dark_tableHeaderBackground;
            statusLineError = dark_statusLineError;
            top_ChipNameLabel_Foreground = dark_top_ChipName_Time_Foreground;
            top_TimeLabel_Foreground = dark_top_TimeLabel_Foreground;
            comboAnimateFormatView = dark_comboAnimateFormatView;
            slider_SlowFast = dark_slider_SlowFast;
            partPins_ChipLabel = dark_partPins_ChipLabel;
        }
    }

    public void setTheme(Theme theme) {
        if (theme == Theme.Classic || theme == Theme.Classic_v3) {
            extractBasicTheme(Theme.Classic);

            if (theme == Theme.Classic_v3) {//iterate only if this is not Original Classic
                CURRENT_THEME = Theme.Classic_v3;
                backgroundColor = classic_v3_backgroundColor;
                partPinsTable_Value_ColumnBackgroundRow = classic_v3_partPinsTable_Value_ColumnBackgroundRow;
                inputOutputPinsTables_Value_ColumnBackgroundRow = classic_v3_inputOutputPinsTables_Value_ColumnBackgroundRow;
                labelText = classic_v3_labelText;
                markedDigits = classic_v3_markedDigits;
                top_ChipNameLabel_Foreground = classic_v3_top_ChipName_Time_Foreground;
                slider_SlowFast = classic_v3_slider_SlowFast;
                comboAnimateFormatView = classic_v3_comboAnimateFormatView;
            }

        }

        else if (theme == Theme.Dark) {// IF YOU WANT TO ADD MORE DARK THEMES : DO IT HERE !
            extractBasicTheme(Theme.Dark);
        }
    }

}