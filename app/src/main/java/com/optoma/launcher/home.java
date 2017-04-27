package com.optoma.launcher;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.support.v4.util.ArrayMap;

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
    private TextView nowTime; // time
    private TextView nowDay; // day

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        //mView = findViewById(android.R.id.content);
        // shortcut input source 1 will be the default focus button
    //    firstBtn = (Button) findViewById(R.id.shortcut_is1_img);
    //    firstBtn.requestFocus();
        init();
    }


    private void init() {
        initShortcutImageSize();
        initBtnClick();
        displayCurrentDateTime();
    }
    /*
    * home_clock: 10:09
    * home_day: FRI 30 DEC
     */
    public void displayCurrentDateTime() {
        nowTime = (TextView) findViewById(R.id.home_clock);
        nowDay = (TextView) findViewById(R.id.home_day);
        nowTime.postDelayed(runnable, 1000);
        nowDay.postDelayed(runnable, 1000);

    }

    /*
    * update the day and time continually
    * for the Calendar.getInstance(), as we didn't assign the timezone, it will use the default timezone and language of system
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            nowTime.setText(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
            nowDay.setText(new SimpleDateFormat("EEE dd MMM").format(Calendar.getInstance().getTime()));
            nowTime.postDelayed(runnable,1000);
            nowDay.postDelayed(runnable,1000);
        }
    };

    /*
    * remove day & time update on the pause status
     */
    @Override
    protected void onPause() {
        super.onPause();
        nowTime.removeCallbacks(runnable);
        nowDay.removeCallbacks(runnable);
    }

    public void initBtnClick() {
        int[] menuID = {
                R.id.menu_position,
                R.id.menu_apps,
                R.id.menu_is,
                R.id.menu_lang
        };
        int[] shortcutID = {
                R.id.shortcut_app5,
                R.id.shortcut_app6,
                R.id.shortcut_app7,
                R.id.shortcut_app8
        };
        for(int i=0; i<shortcutID.length;i++) {
            layoutClicked(shortcutID[i]);
        }
        for(int i=0; i<menuID.length;i++) {
            menuClicked(menuID[i]);
        }
    }

    /*
    * used for linearlayout to be clickable
     */
    public void layoutClicked(int resourceID) {
        String myClass;
        final int rID;
        rID = resourceID;
        LinearLayout ly = (LinearLayout)findViewById(resourceID);
        ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rID) {
                    case R.id.shortcut_app5: //input source
                        intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                        break;
                    case R.id.shortcut_app6: //input source
                        intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                        break;
                    case R.id.shortcut_app7: //settings
                        intent.setClassName(getPackageName(), getPackageName() + ".Settings.Settings");
                        break;
                    case R.id.shortcut_app8: // projector settings
                        intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                        break;
                    default:
                        intent.setClassName(getPackageName(), getPackageName() + ".Settings.Settings");
                        break;
                }
                startActivity(intent);
            }
        });
    }

    /*
    * used for AppCompatImageView to be clickable
     */
    public void menuClicked(int resourceID) {
        final int rID;
        rID = resourceID;
        AppCompatImageView btn = (AppCompatImageView)findViewById(resourceID);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rID) {
                    case R.id.menu_position:
                        intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                        break;
                    case R.id.menu_apps:
                        intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                        break;
                    case R.id.menu_is:
                        intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                        break;
                    case R.id.menu_lang:
                        intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                        break;
                    default:
                        intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                        break;
                }
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
        AppCompatImageView view = (AppCompatImageView) findViewById(imageID);
        Double height = new Double(view.getDrawable().getIntrinsicHeight() * ratio);
        Double width = new Double(view.getDrawable().getIntrinsicWidth() * ratio);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width.intValue(), height.intValue(), 11f);
        view.setLayoutParams(params);
        //view.requestLayout();
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