package com.optoma.launcher;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

import com.optoma.launcher.ui.ButtonController;
import com.optoma.launcher.ui.FlatPickerController;
import com.optoma.launcher.ui.OUtil;
import com.optoma.launcher.ui.PickerController;
import com.optoma.launcher.ui.SeekBarController;
import com.optoma.launcher.ui.ShortcutController;
import com.optoma.launcher.ui.ToggleController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Setup extends Activity {

    @BindView(R.id.shortcut_area)
    LinearLayout shortcutArea;

    @BindView(R.id.menu_area)
    LinearLayout menuArea;

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
                createImageSettingsPanel();
            }
        });

        addSpace();

        final ShortcutController displayAdjustment = new ShortcutController(
                this, R.drawable.display_adjustment, getResources().getString(R.string.projector_display_adjustment));
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

    private void createImageSettingsPanel() {
        final View panel = View.inflate(this, R.layout.setup_menu_panel, null);

        final ViewGroup content = (ViewGroup) panel.findViewById(R.id.setup_menu_panel_content);

        final String[] displayModes = new String[]{
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
                "ISF 3D",
                "2D High Speed",
                "Blending"
        };
        final PickerController displayMode = new PickerController(
                this, "Display Mode", displayModes, 0);
        content.addView(displayMode.getView());


        final String[] wallColors = new String[]{
                "Off",
                "BlackBoard",
                "Light Yellow",
                "Light Green",
                "Light Blue",
                "Pink",
                "Gray"
        };
        final PickerController wallColor = new PickerController(
                this, "Wall Color", wallColors, 0);
        content.addView(wallColor.getView());

        final SeekBarController brightness = new SeekBarController(this, "Brightness", 0, -50, 50, 1);
        content.addView(brightness.getView());

        String[] brightnessModes = new String[]{
                "Bright",
                "Eco+",
                "Dynamic",
                "Eco",
                "Constant Power",
                "Constant Luminace"
        };
        final PickerController brightnessMode = new PickerController(
                this, "Brightness Mode", brightnessModes, 0);
        content.addView(brightnessMode.getView());


        final SeekBarController contrast = new SeekBarController(this, "Contrast", 0, -50, 50, 1);
        content.addView(contrast.getView());

        final SeekBarController sharpness = new SeekBarController(this, "Sharpness", 1, 1, 15, 1);
        content.addView(sharpness.getView());

        final SeekBarController color = new SeekBarController(this, "Color", 0, -50, 50, 1);
        content.addView(color.getView());

        final SeekBarController tint = new SeekBarController(this, "Tint", 0, -50, 50, 1);
        content.addView(tint.getView());


        final String[] gammaModes = new String[]{
                "Film",
                "Video",
                "Graphics",
                "Standard(2.2)",
                "1.8",
                "2.0",
                "2.4",
                "2.6",
                "3D",
                "Blackboard",
                "DICOM SIM.",
                "Bright",
                "CRT",
                "User"
        };
        final PickerController gammaMode = new PickerController(
                this, "Gamma Mode", gammaModes, 3);
        content.addView(gammaMode.getView());


        final ButtonController colorSettings = new ButtonController(
                this, "Color Settings", 0, R.drawable.expand431);
        content.addView(colorSettings.getView());

        final ButtonController pureEngine = new ButtonController(
                this, "PureEngine", 0, R.drawable.expand431);
        content.addView(pureEngine.getView());

        final ButtonController darbeeSettings = new ButtonController(
                this, "Darbee Settings", 0, R.drawable.expand431);
        content.addView(darbeeSettings.getView());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;

        if(menuArea.getChildCount() >= 1) {
            final Space gap = new Space(this);
            gap.setLayoutParams(new ViewGroup.LayoutParams(OUtil.dpToPixel(this, 5), 1));
            menuArea.addView(gap);
        }

        menuArea.addView(panel, lp);
    }

    public void push(View v) {

    }
}
