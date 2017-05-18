package com.optoma.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;

import com.optoma.launcher.ui.ButtonController;
import com.optoma.launcher.ui.MenuController;
import com.optoma.launcher.ui.MenuGroupController;
import com.optoma.launcher.ui.PickerController;
import com.optoma.launcher.ui.SeekBarController;
import com.optoma.launcher.ui.ShortcutController;
import com.optoma.launcher.ui.ToggleController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Setup extends Activity {

    @BindView(R.id.content)
    ViewGroup content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
        ButterKnife.bind(this);
        content.addView(createMainMenu());
    }

    private View createMainMenu() {

        final MenuController menu = new MenuController(this, R.layout.setup_main, null);

        final ShortcutController imageSettings = new ShortcutController(
                this, R.drawable.image_settings, getResources().getString(R.string.projector_image_settings));
        imageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setContent(createImageSettings(menu), imageSettings);
            }
        });
        menu.addItem(imageSettings);

        menu.addDummyItem(createSpace());

        final ShortcutController displayAdjustment = new ShortcutController(
                this, R.drawable.display_adjustment, getResources().getString(R.string.projector_display_adjustment));
        displayAdjustment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setContent(createDisplayAdjustment(menu), displayAdjustment);
            }
        });
        menu.addItem(displayAdjustment);

        menu.addDummyItem(createSpace());

//        final ShortcutController pipPdb = new ShortcutController(
//                this, R.drawable.pip_pbp, getResources().getString(R.string.projector_pip_pbp));
//        pipPdb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                menuArea.removeAllViews();
//                menuArea.addView(createPipPbp(menuArea));
//            }
//        });
//        shortcutArea.addView(pipPdb.getView());
//
//        addSpace();

        final ShortcutController threeD = new ShortcutController(
                this, R.drawable.e_3d, getResources().getString(R.string.projector_3d));
        menu.addItem(threeD);

        menu.addDummyItem(createSpace());

        final ShortcutController signal = new ShortcutController(
                this, R.drawable.signal, getResources().getString(R.string.projector_signal));
        menu.addItem(signal);

        menu.addDummyItem(createSpace());

        final ShortcutController installation = new ShortcutController(
                this, R.drawable.installation, getResources().getString(R.string.projector_installation));
        menu.addItem(installation);

        menu.addDummyItem(createSpace());

        final ShortcutController devicesControl = new ShortcutController(
                this, R.drawable.components_control, getResources().getString(R.string.projector_components_control));
        devicesControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               menu.setContent(createDevicesControl(menu), devicesControl);
            }
        });
        menu.addItem(devicesControl);

        menu.addDummyItem(createSpace());

        final ShortcutController sourceControl = new ShortcutController(
                this, R.drawable.source_control, getResources().getString(R.string.projector_source_control));
        menu.addItem(sourceControl);

        return menu.getView();
    }

    private View createSpace() {
        final Space space = new Space(this);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 0);
        lp.weight = 1;
        space.setLayoutParams(lp);
        return space;
    }

    private View createImageSettings(final MenuController from) {

        final MenuController menu = new MenuController(this, R.layout.menu_panel, from);


        final PickerController displayMode = new PickerController(
                this, "Display Mode", Projector.displayModes, 0);
        menu.addItem(displayMode);


        final PickerController wallColor = new PickerController(
                this, "Wall Color", Projector.wallColors, 0);
        menu.addItem(wallColor);

        final SeekBarController brightness = new SeekBarController(this, "Brightness", 0, -50, 50, 1);
        menu.addItem(brightness);

        final SeekBarController contrast = new SeekBarController(this, "Contrast", 0, -50, 50, 1);
        menu.addItem(contrast);

        final SeekBarController sharpness = new SeekBarController(this, "Sharpness", 1, 1, 15, 1);
        menu.addItem(sharpness);

        final SeekBarController color = new SeekBarController(this, "Color", 0, -50, 50, 1);
        menu.addItem(color);

        final SeekBarController tint = new SeekBarController(this, "Tint", 0, -50, 50, 1);
        menu.addItem(tint);

        final PickerController brightnessMode = new PickerController(
                this, "Brightness Mode", Projector.brightnessModes, 0);
        menu.addItem(brightnessMode);

        final PickerController gammaMode = new PickerController(
                this, "Gamma Mode", Projector.gammaModes, 3);
        menu.addItem(gammaMode);


        final ButtonController colorSettings = new ButtonController(
                this, "Color Settings", 0, R.drawable.expand431);
        colorSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(Setup.this, "hello?", Toast.LENGTH_SHORT).show();
                menu.setContent(createColorSettings(menu), colorSettings);
            }
        });
        menu.addItem(colorSettings);

        final ButtonController pureEngine = new ButtonController(
                this, "PureEngine", 0, R.drawable.expand431);
        pureEngine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setContent(createPureEngine(menu), pureEngine);
            }
        });
        menu.addItem(pureEngine);

        final ButtonController darbeeSettings = new ButtonController(
                this, "Darbee Settings", 0, R.drawable.expand431);
        darbeeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setContent(createDarbeeSettings(menu), darbeeSettings);
            }
        });
        menu.addItem(darbeeSettings);

        final ButtonController back = new ButtonController(
                this, "Back to Projector Setup", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        return menu.getView();
    }

    private View createColorSettings(final MenuController parent) {
        final MenuController menu = new MenuController(this, R.layout.menu_panel_level2, parent);

        final ButtonController back = new ButtonController(
                this, "Back to Image Settings", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        final SeekBarController brilliantColor = new SeekBarController(this, "BrilliantColor™", 1, 1, 10, 1);
        menu.addItem(brilliantColor);

        final PickerController colorTemperature = new PickerController(
                this, "Color Temperature", Projector.colorTemperatures, 0);
        menu.addItem(colorTemperature);

        final ButtonController colorMatching = new ButtonController(
                this, "Color Matching", -1, R.drawable.expand431);
        colorMatching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setContent(createColorMatching(menu), colorMatching);
            }
        });
        menu.addItem(colorMatching);

        final PickerController colorGamut = new PickerController(
                this, "Color Gamut", Projector.colorGamuts, 0);
        menu.addItem(colorGamut);

        final ButtonController cms = new ButtonController(
                this, "CMS", -1, R.drawable.expand431);
        cms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setContent(createCMS(menu), cms);
            }
        });
        menu.addItem(cms);

        final ButtonController rgbGainBias = new ButtonController(
                this, "RGB Gain/Bias", -1, R.drawable.expand431);
        rgbGainBias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setContent(createRGBGainBias(menu), rgbGainBias);
            }
        });
        menu.addItem(rgbGainBias);

        final PickerController rgbChannel = new PickerController(
                this, "RGB Channel", Projector.rgbChannels, 0);
        menu.addItem(rgbChannel);

        return menu.getView();
    }

    private View createPureEngine(final MenuController parent) {
        final MenuController menu = new MenuController(this, R.layout.menu_panel_level2, parent);

        final ButtonController back = new ButtonController(
                this, "Back to Image Settings", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        final ToggleController contrast = new ToggleController(
                this, "PureContrast", false);
        menu.addItem(contrast);

        final PickerController color = new PickerController(
                this, "PureColor", new String[]{"Off", "1", "2", "3", "4", "5"}, 0);
        menu.addItem(color);

        final PickerController motion = new PickerController(
                this, "PureMotion", new String[]{"Off", "1", "2", "3"}, 0);
        menu.addItem(motion);

        final PickerController pureMotionDemo = new PickerController(
                this, "PureMotion Demo", new String[]{"Off", "H Split", "V Split"}, 0);
        menu.addItem(pureMotionDemo);

        return menu.getView();
    }

    private View createDarbeeSettings(final MenuController parent) {
        final MenuController menu = new MenuController(this, R.layout.menu_panel_level2, parent);

        final ButtonController back = new ButtonController(
                this, "Back to Image Settings", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        final PickerController mode = new PickerController(
                this, "Mode", Projector.darbeeModes, 0);
        menu.addItem(mode);

        final SeekBarController level = new SeekBarController(this, "Level", 0, 0, 120, 1);
        menu.addItem(level);

        final PickerController demoMode = new PickerController(
                this, "Demo Mode", Projector.demoModes, 0);
        menu.addItem(demoMode);

        return menu.getView();
    }

    private View createColorMatching(final MenuController parent) {
        final MenuController menu = new MenuController(this, R.layout.menu_panel_level3, parent);

        final ButtonController back = new ButtonController(
                this, "Back to Color Settings", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        final PickerController color = new PickerController(
                this, "Color", Projector.colorSettingsColors, 0);
        menu.addItem(color);

        final SeekBarController saturation = new SeekBarController(this, "Saturation", 0, -50, 50, 1);
        menu.addItem(saturation);

        final SeekBarController hue = new SeekBarController(this, "Hue", 0, -50, 50, 1);
        menu.addItem(hue);

        final SeekBarController gain = new SeekBarController(this, "Gain", 0, -50, 50, 1);
        menu.addItem(gain);

        final ButtonController reset = new ButtonController(
                this, "Reset", -1, -1);
        menu.addItem(reset);

        return menu.getView();
    }

    private View createCMS(final MenuController parent) {

        final MenuController menu = new MenuController(
                this, R.layout.menu_panel_level3, parent);

        final ButtonController back = new ButtonController(
                this, "Back to Color Settings", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        final PickerController color = new PickerController(
                this, "Color", Projector.colorSettingsColors, 0);
        menu.addItem(color);

        final SeekBarController xOffset= new SeekBarController(this, "X Offset", 0, -50, 50, 1);
        menu.addItem(xOffset);

        final SeekBarController yOffset = new SeekBarController(this, "Y Offset", 0, -50, 50, 1);
        menu.addItem(yOffset);

        final SeekBarController brightness = new SeekBarController(this, "Brightness", 0, -50, 50, 1);
        menu.addItem(brightness);

        return menu.getView();
    }

    private View createRGBGainBias(final MenuController parent) {
        final MenuController menu = new MenuController(
                this, R.layout.menu_panel_level3, parent);

        final ButtonController back = new ButtonController(
                this, "Back to Color Settings", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        final SeekBarController redGain = new SeekBarController(this, "Red Gain", 0, -50, 50, 1);
        menu.addItem(redGain);

        final SeekBarController greenGain = new SeekBarController(this, "Green Gain", 0, -50, 50, 1);
        menu.addItem(greenGain);

        final SeekBarController blueGain = new SeekBarController(this, "Blue Gain", 0, -50, 50, 1);
        menu.addItem(blueGain);

        final SeekBarController redBias = new SeekBarController(this, "Red Bias", 0, -50, 50, 1);
        menu.addItem(redBias);

        final SeekBarController greenBias = new SeekBarController(this, "Green Bias", 0, -50, 50, 1);
        menu.addItem(greenBias);

        final SeekBarController blueBias = new SeekBarController(this, "Blue Bias", 0, -50, 50, 1);
        menu.addItem(blueBias);

        final ButtonController reset = new ButtonController(this, "Reset");
        menu.addItem(reset);

        return menu.getView();
    }

    private View createDisplayAdjustment(final MenuController parent) {

        final MenuController menu = new MenuController(this, R.layout.menu_panel, parent);

        final PickerController aspectRatio = new PickerController(
                this, "Aspect Ratio", Projector.aspectRatios, 0);
        menu.addItem(aspectRatio);

        final SeekBarController edgeMask = new SeekBarController(this, "Edge Mask", 0, 0, 10, 1);
        menu.addItem(edgeMask);

        final SeekBarController zoom = new SeekBarController(this, "Zoom", 0, -5, 25, 1);
        menu.addItem(zoom);

        final MenuGroupController imageShift = new MenuGroupController(this, "Image Shift");
        {
            final SeekBarController h = new SeekBarController(this, "H", 0, -100, 100, 1);
            imageShift.addItem(h.getView());

            final SeekBarController v = new SeekBarController(this, "V", 0, -100, 100, 1);
            imageShift.addItem(v.getView());
        }
        menu.addItem(imageShift);

        final MenuGroupController geometricCorrection = new MenuGroupController(this, "Geometric Correction");
        {
            final SeekBarController hArc = new SeekBarController(this, "H Arc", 0, -10, 10, 1);
            geometricCorrection.addItem(hArc.getView());

            final SeekBarController vArc = new SeekBarController(this, "V Arc", 0, -10, 10, 1);
            geometricCorrection.addItem(vArc.getView());

            final SeekBarController hKeystone = new SeekBarController(this, "H Keystone", 0, -40, 40, 1);
            geometricCorrection.addItem(hKeystone.getView());

            final SeekBarController vKeystone = new SeekBarController(this, "V Keystone", 0, -40, 40, 1);
            geometricCorrection.addItem(vKeystone.getView());

            final ToggleController autoKeystone = new ToggleController(this, "Auto Keystone", false);
            geometricCorrection.addItem(autoKeystone.getView());

        }
        menu.addItem(geometricCorrection);

        final ButtonController back = new ButtonController(
                this, "Back to Projector Setup", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        return menu.getView();
    }

    private View createPipPbp(final MenuController parent) {
        final MenuController menu = new MenuController(
                this, R.layout.menu_panel, parent);

        final PickerController screen = new PickerController(
                this, "Screen", Projector.pipPbpScreens, 0);
        menu.addItem(screen);

        final PickerController location = new PickerController(
                this, "Location", Projector.pipPbpLocations, 0);
        menu.addItem(location);

        final PickerController size = new PickerController(
                this, "Size", Projector.pipPdpSizes, 0);
        menu.addItem(size);

        final MenuGroupController source = new MenuGroupController(this, "Source");
        {
            final PickerController mainSource = new PickerController(
                    this, "Main Source", Projector.mainSources, 0);
            source.addItem(mainSource.getView());

            final PickerController subSource = new PickerController(
                    this, "Sub Source", Projector.mainSources, 0);
            source.addItem(subSource.getView());
        }
        menu.addItem(source);

        final ButtonController back = new ButtonController(
                this, "Back to Projector Setup", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        return menu.getView();
    }

    private View createDevicesControl(final MenuController parent) {

        final MenuController menu = new MenuController(this, R.layout.menu_panel, parent);

        final ButtonController lampSettings = new ButtonController(
                this, "Light Source", 0, R.drawable.expand431);
        lampSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setContent(createLightSource(menu), lampSettings);
            }
        });
        menu.addItem(lampSettings);

        final ButtonController filterSettings = new ButtonController(
                this, "Filter Settings", 0, R.drawable.expand431);
        filterSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setContent(createFilterSettings(menu), filterSettings);
            }
        });
        menu.addItem(filterSettings);

        final ButtonController lensSettings = new ButtonController(
                this, "Lens Settings", 0, R.drawable.expand431);
        menu.addItem(lensSettings);

        final PickerController anamorphicLens = new PickerController(
                this, "Anamorphic Lens", Projector.anamorphicLensType, 0
        );
        menu.addItem(anamorphicLens);

        final ButtonController remoteSettings = new ButtonController(
                this, "Remote Settings", 0, R.drawable.expand431);
        menu.addItem(remoteSettings);

        final ToggleController twelveVTrigger = new ToggleController(
                this, "12V Trigger", false
        );
        menu.addItem(twelveVTrigger);

        final ButtonController twelveVTriggerB = new ButtonController(
                this, "12V Trigger B", 0, R.drawable.expand431);
        menu.addItem(twelveVTriggerB);

        final ToggleController highAltitude = new ToggleController(
                this, "High Altitude", false
        );
        menu.addItem(highAltitude);

        final ButtonController back = new ButtonController(
                this, "Back to Projector Setup", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        return menu.getView();

    }

    private View createLightSource(final MenuController parent) {

        final MenuController menu = new MenuController(this, R.layout.menu_panel_level2, parent);

        final ButtonController back = new ButtonController(
                this, "Back to Components Control", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        final ToggleController lampReminder = new ToggleController(this, "Lamp Reminder", false);
        menu.addItem(lampReminder);

        final ButtonController lampReset = new ButtonController(this, "Lamp Reset");
        menu.addItem(lampReset);

        return menu.getView();
    }

    private View createFilterSettings(final MenuController parent) {

        final MenuController menu = new MenuController(this, R.layout.menu_panel_level2, parent);

        final ButtonController back = new ButtonController(
                this, "Back to Components Control", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        final ToggleController optionalFilterInstalled = new ToggleController(
                this, "Optional Filter Installed", false
        );
        menu.addItem(optionalFilterInstalled);

        final PickerController filterReminder = new PickerController(
                this, "Filter Reminder", Projector.filterReminders, 0
        );
        menu.addItem(filterReminder);

        final ButtonController filterReset = new ButtonController(
                this, "Filter Reset"
        );
        menu.addItem(filterReset);

        return menu.getView();

    }

    private View createLensSettings(final MenuController parent) {

        final MenuController menu = new MenuController(this, R.layout.menu_panel_level2, parent);

        final ButtonController back = new ButtonController(
                this, "Back to Components Control", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.dismiss();
            }
        });
        menu.addItem(back);

        final PickerController lensFunction = new PickerController(
                this, "Lens Function", Projector.lensFunctions, 0
        );
        menu.addItem(lensFunction);

        final PickerController lensShift = new PickerController(
                this, "Lens Shift", Projector.lensShifts, 0
        );
        menu.addItem(lensShift);

        final ButtonController lensCalibration = new ButtonController(
                this, "Lens Calibration", -1, R.drawable.expand431
        );
        menu.addItem(lensCalibration);

        return menu.getView();

    }
}