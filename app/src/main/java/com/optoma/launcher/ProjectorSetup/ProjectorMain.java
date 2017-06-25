package com.optoma.launcher.ProjectorSetup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.optoma.launcher.R;
import com.optoma.launcher.FocusContainer;
import com.optoma.launcher.SizeChanger;

public class ProjectorMain extends Activity {
    private static final String TAG = "launcherLog";
    public static long animateDuration = 80;
    private FocusContainer buttonRow;

    private static int[] PSButtonID = {
            R.id.projector_button1, // image settings
            R.id.projector_button2, // display adjustment
            R.id.projector_button3, // 3d
            R.id.projector_button4, // signal
            R.id.projector_button5, // setup
            R.id.projector_button6, // device control
            R.id.projector_button7 // source control
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projector_main);
        init();
    }

    private void init() {
        initBtnClick();
        initButtons();
    }

    private void initButtons() {

        buttonRow = new FocusContainer();
        for(int i=0; i<PSButtonID.length; i++) {
            final View view = (View) findViewById(PSButtonID[i]);
            view.setOnFocusChangeListener(buttonFocusChangeListener);
            buttonRow.add(view);
        }
        buttonRow.setFocus(0);

        // Post hack: update the position of the label as the first UI event,
        // ref: http://stackoverflow.com/questions/7733813/how-can-you-tell-when-a-layout-has-been-drawn/7735122#7735122
        final View view = findViewById(R.id.projector_button);
        view.post(new Runnable() {
            @Override
            public void run() {
                buttonRow.getFocusedView().requestFocus();
            }
        });
    }

    private View.OnFocusChangeListener buttonFocusChangeListener =
            new SizeChanger(1.25f, animateDuration);

    public void initBtnClick() {
        for(int i=0; i<PSButtonID.length;i++) {
            layoutClicked(PSButtonID[i]);
        }
    }

    public void layoutClicked(int resourceID) {
        final int rID;
        rID = resourceID;
        View ly = findViewById(resourceID);
        ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent();
                switch (rID) {
                    case R.id.projector_button1:
                        intent.setClassName(getPackageName(), getPackageName() + ".InputSourceActivity");
                        break;
                    case R.id.projector_button2:
                        intent.setClassName(getPackageName(), getPackageName() + ".InputSourceActivity");
                        break;
                    case R.id.projector_button3:
                        intent.setClassName(getPackageName(), getPackageName() + ".Settings.Settings");
                        break;
                    case R.id.projector_button4:
                        intent.setClassName(getPackageName(), getPackageName() + ".ProjectorSetup.ThreeD");
                        break;
                    case R.id.projector_button5:
                        intent.setClassName(getPackageName(), getPackageName() + ".ProjectorSetup.Signal");
                        break;
                    case R.id.projector_button6:
                        intent.setClassName(getPackageName(), getPackageName() + ".ProjectorSetup.ProjectorSetupActivity");
                        break;
                    case R.id.projector_button7:
                        intent.setClassName(getPackageName(), getPackageName() + ".Settings.Settings");
                        break;
                    case R.id.projector_button8:
                        intent.setClassName(getPackageName(), getPackageName() + ".ProjectorSetup.SourceControl");
                        break;
                    default:
                        intent.setClassName(getPackageName(), getPackageName() + ".Settings.Settings");
                        break;
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                buttonRow.focusLeft();
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                buttonRow.focusRight();
                break;
            default:
                break;
        }
        buttonRow.getFocusedView().requestFocus();
        return true;
    }
}
