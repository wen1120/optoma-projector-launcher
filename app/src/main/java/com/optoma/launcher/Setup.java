package com.optoma.launcher;

import android.app.Activity;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class Setup extends Activity {

    @BindView(R.id.shortcut_area)
    LinearLayout shortcutArea;

    @BindView(R.id.menu_area)
    ViewGroup menuArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
        ButterKnife.bind(this);
        buildViews();

    }

    private void buildViews() {
        final ShortcutController imageSettings = new ShortcutController(
                this, R.drawable.image_settings, getResources().getString(R.string.projector_image_settings));
        shortcutArea.addView(imageSettings.getView());
        imageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuArea.removeAllViews();
                menuArea.addView(createImageSettingsMenu(menuArea));
            }
        });

        addSpace();

        final ShortcutController displayAdjustment = new ShortcutController(
                this, R.drawable.display_adjustment, getResources().getString(R.string.projector_display_adjustment));
        displayAdjustment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuArea.removeAllViews();
                menuArea.addView(createDisplayAdjustment(menuArea));
            }
        });
        shortcutArea.addView(displayAdjustment.getView());

        addSpace();

        final ShortcutController pipPdb = new ShortcutController(
                this, R.drawable.pip_pbp, getResources().getString(R.string.projector_pip_pbp));
        shortcutArea.addView(pipPdb.getView());

        addSpace();

        final ShortcutController threeD = new ShortcutController(
                this, R.drawable.e_3d, getResources().getString(R.string.projector_3d));
        shortcutArea.addView(threeD.getView());

        addSpace();

        final ShortcutController signal = new ShortcutController(
                this, R.drawable.signal, getResources().getString(R.string.projector_signal));
        shortcutArea.addView(signal.getView());

        addSpace();

        final ShortcutController installation = new ShortcutController(
                this, R.drawable.installation, getResources().getString(R.string.projector_installation));
        shortcutArea.addView(installation.getView());

        addSpace();

        final ShortcutController componentsControl = new ShortcutController(
                this, R.drawable.components_control, getResources().getString(R.string.projector_components_control));
        shortcutArea.addView(componentsControl.getView());

        addSpace();

        final ShortcutController sourceControl = new ShortcutController(
                this, R.drawable.source_control, getResources().getString(R.string.projector_source_control));
        shortcutArea.addView(sourceControl.getView());
    }

    private void addSpace() {
        final Space space = new Space(this);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 0);
        lp.weight = 1;
        space.setLayoutParams(lp);
        shortcutArea.addView(space);
    }

    private View createImageSettingsMenu(final ViewGroup parent) { // TODO: use menu here?

        final MenuController menu = new MenuController(this, 1, 3);


        final PickerController displayMode = new PickerController(
                this, "Display Mode", Projector.displayModes, 0);
        menu.addItem(displayMode.getView());


        final PickerController wallColor = new PickerController(
                this, "Wall Color", Projector.wallColors, 0);
        menu.addItem(wallColor.getView());

        final SeekBarController brightnessValue = new SeekBarController(this, "Value", 0, -50, 50, 1);

        final PickerController brightnessMode = new PickerController(
                this, "Brightness Mode", Projector.brightnessModes, 0);

        final MenuGroupController brightness = new MenuGroupController(this, "Brightness");
        brightness.addItem(brightnessValue.getView());
        brightness.addItem(brightnessMode.getView());
        menu.addItem(brightness.getView());

        final SeekBarController contrast = new SeekBarController(this, "Contrast", 0, -50, 50, 1);
        menu.addItem(contrast.getView());

        final SeekBarController sharpness = new SeekBarController(this, "Sharpness", 1, 1, 15, 1);
        menu.addItem(sharpness.getView());

        final SeekBarController color = new SeekBarController(this, "Color", 0, -50, 50, 1);
        menu.addItem(color.getView());

        final SeekBarController tint = new SeekBarController(this, "Tint", 0, -50, 50, 1);
        menu.addItem(tint.getView());

        final PickerController gammaMode = new PickerController(
                this, "Gamma Mode", Projector.gammaModes, 3);
        menu.addItem(gammaMode.getView());


        final ButtonController colorSettings = new ButtonController(
                this, "Color Settings", 0, R.drawable.expand431);
        colorSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(Setup.this, "hello?", Toast.LENGTH_SHORT).show();
                menu.setChild(createColorSettingsMenu(menu));
            }
        });
        menu.addItem(colorSettings.getView());

        final ButtonController pureEngine = new ButtonController(
                this, "PureEngine", 0, R.drawable.expand431);
        menu.addItem(pureEngine.getView());

        final ButtonController darbeeSettings = new ButtonController(
                this, "Darbee Settings", 0, R.drawable.expand431);
        menu.addItem(darbeeSettings.getView());

        final ButtonController back = new ButtonController(
                this, "Back to Projector Setup", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.removeAllViews();
            }
        });
        menu.addItem(back.getView());

        return menu.getView();
    }

    private View createColorSettingsMenu(final MenuController parent) {
        final MenuController menu = new MenuController(this, 1, 2);

        final ButtonController back = new ButtonController(
                this, "Back to Image Settings", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.setChild(null);
            }
        });
        menu.addItem(back.getView());

        final SeekBarController brilliantColor = new SeekBarController(this, "BrilliantColor™", 1, 1, 10, 1);
        menu.addItem(brilliantColor.getView());

        final PickerController colorTemperature = new PickerController(
                this, "Color Temperature", Projector.colorTemperatures, 0);
        menu.addItem(colorTemperature.getView());

        final ButtonController colorMatching = new ButtonController(
                this, "Color Matching", -1, R.drawable.expand431);
        colorMatching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setChild(createColorMatchingMenu(menu));
            }
        });
        menu.addItem(colorMatching.getView());

        return menu.getView();
    }

    private View createColorMatchingMenu(final MenuController parent) {
        final MenuController menu = new MenuController(this, 1, 1);
        menu.setChild(null);

        final ButtonController back = new ButtonController(
                this, "Back to Color Settings", R.drawable.backtotop_white, -1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.setChild(null);
            }
        });
        menu.addItem(back.getView());

        final PickerController color = new PickerController(
                this, "Color", Projector.colorSettingsColors, 0);
        menu.addItem(color.getView());

        final SeekBarController saturation = new SeekBarController(this, "Saturation", 0, -50, 50, 1);
        menu.addItem(saturation.getView());

        final SeekBarController hue = new SeekBarController(this, "Hue", 0, -50, 50, 1);
        menu.addItem(hue.getView());

        final SeekBarController gain= new SeekBarController(this, "Gain", 0, -50, 50, 1);
        menu.addItem(gain.getView());

        return menu.getView();
    }

    private View createDisplayAdjustment(final ViewGroup parent) {

        final MenuController menu = new MenuController(this, 1, 3);

        final PickerController aspectRatio = new PickerController(
                this, "Aspect Ratio", Projector.aspectRatios, 0);
        menu.addItem(aspectRatio.getView());

        return menu.getView();
    }
}
