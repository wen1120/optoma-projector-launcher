package com.optoma.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

/*
* gradle debug on command line on Mac
* $ brew install gradle
* $ gradle compileDebugSources --stacktrace --debug
 */
public class home extends Activity {
    private static final String TAG = "LauncherLog";

    private Button firstBtn;
    //private View mView;
    private FocusView mFocusView;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        //mView = findViewById(android.R.id.content);
        // shortcut input source 1 will be the default focus button
    //    firstBtn = (Button) findViewById(R.id.shortcut_is1_img);
    //    firstBtn.requestFocus();
        initShortcutImageSize();
        initShortcutClick();
    }


    public void initShortcutClick() {
        int[] shortcutID = {
                R.id.shortcut_app5,
                R.id.shortcut_app6,
                R.id.shortcut_app7,
                R.id.shortcut_app8
        };
        for(int i=0; i<shortcutID.length;i++) {
            layoutClicked(shortcutID[i]);
        }
    }

    public void layoutClicked(int resourceID) {
        LinearLayout ly = (LinearLayout)findViewById(resourceID);
        ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                startActivity(intent);
            }
        });
    }

    /* resize all image of shortcut to smaller(70%) size when app is launched
     */
    public void initShortcutImageSize() {
        int[] shortcutID = {
                R.id.shortcut_is1_img,
                R.id.shortcut_is2_img,
                R.id.shortcut_setup_img,
                R.id.shortcut_psetup_img
        };
        for(int i=0;i<shortcutID.length; i++) {
            resizeImage(shortcutID[i], 0.7);
        }
    }
    /*
    * 1. resize larger(focus) image size to smaller(70%)(non-focus) image and assign to the image src
    *
     */
    public void resizeImage(int imageID, double ratio) {
        //double width, height;
        AppCompatImageView view = (AppCompatImageView) findViewById(imageID);
        Double height = new Double(view.getDrawable().getIntrinsicHeight() * ratio);
        Double width = new Double(view.getDrawable().getIntrinsicWidth() * ratio);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width.intValue(), height.intValue(), 11f);
        view.setLayoutParams(params);
        //view.requestLayout();
    }
    /*
    * enable clickable on layout
    * set the onClick value on layout to "buttonClicked"
     */
    public void buttonClicked(View view) {
        Button thisBtn = (Button) findViewById(view.getId());
        int nextRightId, nextLeftId, nextUpId, nextDownId;

        switch (view.getId()) {
            /*
            * menu buttons
             */
            case R.id.menu_position:
                nextRightId = R.id.menu_apps;
                //nextLeftId = R.id.menu_lang; // turn off loop behavior
                nextDownId = R.id.shortcut_app5;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.menu_apps:
                nextRightId = R.id.menu_is;
                nextLeftId = R.id.menu_position;
                nextDownId = R.id.shortcut_app5;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.menu_is:
                nextRightId = R.id.menu_lang;
                nextLeftId = R.id.menu_apps;
                nextDownId = R.id.shortcut_app5;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.menu_lang:
                //nextRightId = R.id.menu_position;
                nextLeftId = R.id.menu_is;
                nextDownId = R.id.shortcut_app5;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            /*
            * shortcut buttons
             */
            /*case R.id.shortcut_app5:
                nextRightId = R.id.shortcut_app4;
                nextLeftId = R.id.shortcut_app6;
                nextUpId = R.id.menu_apps;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;*/
            case R.id.shortcut_app6:
                nextRightId = R.id.shortcut_app7;
                nextLeftId = R.id.shortcut_is1;
                nextUpId = R.id.menu_apps;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.shortcut_app7:
                nextRightId = R.id.shortcut_app8;
                nextLeftId = R.id.shortcut_app6;
                nextUpId = R.id.menu_apps;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.shortcut_app8:
                //nextRightId = R.id.shortcut_is1; // disable loop behavior
                nextLeftId = R.id.shortcut_app7;
                nextUpId = R.id.menu_apps;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            default:
                Log.d(TAG, "unknow R.id=" + view.getId());
                break;
        }
        Log.d(TAG, "get R.id=" + view.getId());
        startActivity(intent);
    }

    private View mCurrentView;
    public View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                mCurrentView = v;
                //enlargeAnim(v);
                mFocusView.setVisibility(View.VISIBLE);
                mFocusView.setAnchorView(v);
            } else {
                //reduceAnim(v);
            }
        }
    };
}