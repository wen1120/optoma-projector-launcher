package com.optoma.launcher;

import android.content.Context;

import com.optoma.launcher.achieve.CmdManager;

/**
 * Created by ken.chou on 5/16/2017.
 */

public class Projector {
    private CmdManager mCmdManager;
    public Projector(Context context) {
        mCmdManager = new CmdManager(context);
    }

    public static final String[] displayModes = new String[]{
            "[None]",
            "Presentation",
            "sRGB",
            "DICOM SIM.",
            "Bright",
            "Game",
            "User",
            "3D",
            "Cinema",
            "Reference",
            "Vivid",
            "Film",
            "User (3D)",
            "ISF Day",
            "ISF Night",
            "ISF 3D"
    };

    public static final String[] wallColors = new String[]{
            "Off",
            "BlackBoard",
            "Light Yellow",
            "Light Green",
            "Light Blue",
            "Pink",
            "Gray"
    };

    public static final String[] brightnessModes = new String[]{
            "Bright",
            "Eco+",
            "Dynamic",
            "Eco",
            "Constant Power",
            "Constant Luminace"
    };

    public static final String[] gammaModes = new String[]{
            "Film",
            "Video",
            "Graphics",
            "Standard(2.2)",
            "1.8",
            "2.0",
            "2.4",
            "2.6"
    };

    public static final String[] colorTemperatures = new String[] {
            "Warm",
            "Standard",
            "Cool",
            "Cold",
            "D55",
            "D65",
            "D75",
            "D83",
            "D93",
            "Native (Bright)",
            "Reset"
    };

    public static final String[] colorSettingsColors = new String[] {
            "R",
            "G",
            "B",
            "C",
            "Y",
            "M",
            "W"
    };

    public static final String[] colorGamuts = new String[] {
            "Native",
            "REC2020",
            "ADOBE",
            "DLP-C",
            "HDTV",
            "EBU",
            "SMPTE-C",
            "Presentation",
            "Cinema",
            "Game"
    };


    public static final String[] aspectRatios = new String[]{
            "[None]",
            "4:3",
            "16:9",
            "16:10",
            "LBX",
            "Superwide",
            "Native",
            "Auto",
            "Auto235",
            "Auto235_Subtitle",
            "Auto 3D"
    };

    public static final String[] pipPbpScreens = new String[] {
            "Off",
            "PIP",
            "PBP"
    };

    public static final String[] pipPbpLocations = new String[] {
            "Top Left",
            "Top Right",
            "Bottom Left",
            "Bottom Right",
            "PBP, Main Left",
            "PBP, Main Top",
            "PBP, Main Right",
            "PBP, Main Bottom"

    };

    public static final String[] pipPdpSizes = new String[] {
            "Large",
            "Medium",
            "Small"
    };

    public static final String[] mainSources = new String[] {
            "[no signal]",
            "HDMI1/MHL",
            "HDMI2",
            "HDMI2/MHL",
            "HDMI3"
    };

    public static final String[] darbeeModes = new String[]{
            "Hi-Def",
            "Gaming",
            "Full Pop",
            "Off"
    };

    public static final String[] demoModes = new String[] {
            "Off",
            "Split Screen",
            "Swipe Screen"
    };


    public static final String[] lampModes = new String[] {
            "Dual",
            "Relay",
            "Lamp 1",
            "Lamp 2"
    };

    public static final String[] filterReminders = new String[] {
            "Off",
            "300hr",
            "500hr",
            "800hr",
            "1000hr"
    };

    public static final String[] lensFunctions = new String[]{
            "Lock",
            "Unlock"
    };

    public static final String[] lensShifts = new String[]{
            "Up",
            "Down",
            "Left",
            "Right"
    };


    public static final String[] rgbChannels = new String[] {
            "Normal",
            "Red",
            "Green",
            "Blue"
    };

    public static final String[] ultraDetails = new String[] {
            "HD+",
            "User",
            "1",
            "2",
            "3",
    };

    public static final String[] lensTypes = new String[] {
            "WT1",
            "WT2",
            "ST1",
            "TZ1",
            "TZ2"
    };

    public static final String[] anamorphicLensTypes = new String[] {
            "None",
            "Fixed",
            "Movable"
    };

    public static final String[] irFunctions = new String[] {
            "Off All",
            "On All",
            "Front",
            "Top",
            "Front Off",
            "Front On",
            "Top Off",
            "Top On",
            "Back"
    };
    public static final String[] langsEng = new String[] {
            "ar",
            "cs",
            "da",
            "de",
            "el",
            "en",
            "es",
            "fa",
            "fi",
            "fr",
            "hu",
            "id",
            "it",
            "ja",
            "ko",
            "nl",
            "no",
            "pl",
            "pt",
            "ro",
            "ru",
            "sk",
            "sv",
            "th",
            "tr",
            "vi",
            "zh",
            "zh"
    };
    public static final String[] langs = new String[] {
            "عربي",
            "Čeština",
            "Dansk",
            "Deutsch",
            "ελληνικά",
            "English",
            "Español",
            "فارسی",
            "Suomi",
            "Français",
            "Magyar",
            "Bahasa Indonesia",
            "Italiano",
            "日本語",
            "한국어",
            "Nederlands",
            "Norsk",
            "Polski",
            "Português",
            "Română",
            "Русский",
            "Slovak",
            "Svenska",
            "ไทย",
            "Türkçe",
            "Tiếng Việt",
            "繁體中文",
            "簡体中文"
    };
    public static String[] threeDModes = new String[] {
            "Off",
            "DLP-Link",
            "IR"
    };
    public static String[] threeDTwoD = new String[] {
            "3D",
            "L",
            "R"
    };
    public static String[] threeDFormats = new String[] {
            "Auto",
            "SBS",
            "Top and Bottom",
            "Frame Sequentia",
            "Frame Packing",
            "Off"
    };
    public static String[] twoDThreeD = new String[] {
            "Off",
            "1",
            "2",
            "3"
    };
    public static String[] colorSpaces = new String[] {
            "Auto",
            "RGB",
            "YUV",
            "RGB(0~255)",
            "RGB(16~235)",
            "Rec. 709",
            "Rec. 601"
    };
    public static String[] ire = new String[] {
            "0",
            "7.5"
    };
    public static String[] powerOnLink = new String[] {
            "Mutual",
            "PJ --> Device",
            "Device --> PJ"
    };
    public static String[] testPatterns = new String[] {
            "Off",
            "Green Grid",
            "Magenta Grid",
            "White Grid",
            "White",
            "Red",
            "Green",
            "Blue",
            "Yellow",
            "Magenta",
            "Cyan",
            "Black"
    };

    public static final String[] inputSources = new String[]{
            "Miracast",
            "VGA",
            "HDMI 1",
            "HDMI 2",
            "HDMI 3",
            "Display Port",
            "USB 1",
            "USB 2",
            "S-video",
            "Set Shortcut"
    };


    //value: 0 ~ 80
    public void SetKeystone(int value) {
        mCmdManager.setKeystone(value);
    }

    public int GetKeystone() {
        return mCmdManager.getKeystone();
    }

    //index: 0:rear 1:rear_ceiling 2:front 3:front_ceiling
    public void SetProjectMode(int index) {
        mCmdManager.setProjectionMode(index);
    }

    //index: 0: (16:9)  2: (16:10) 3: (4:3)
    public void SetScreenScaleMode(int index) {
        mCmdManager.setScreenScaleMode(index);
    }

    //index: 50~100
    public void SetTiSuperFocus(int index) {
        mCmdManager.setTiSuperFocus(index);
    }

    //index:50~100
    public void SetTiHorizontalAspect(int index) {
        mCmdManager.setTiHorizontalAspect(index);
    }

    //index:50~100
    public void SetTiVerticalAspect(int index) {
        mCmdManager.setTiVerticalAspect(index);
    }
}
