package com.optoma.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
* gradle debug on command line on Mac
* $ brew install gradle
* $ gradle compileDebugSources --stacktrace --debug
 */
public class home extends Activity {
    private static final String TAG = "LauncherLog";

    //private Button firstBtn;
    //private View mView;
    private FocusView mFocusView;
    private Intent intent = new Intent();
    private TextView nowTime; // time
    private TextView nowDay; // day
    private FocusContainer homeRows;
    public static long animateDuration = 80;

    private static int[] menuID = {
            R.id.menu_position,
            R.id.menu_apps,
            R.id.menu_is,
            R.id.menu_lang
    };
    private static int[] shortcutID = {
            R.id.shortcut_app1,
            R.id.shortcut_app2,
            R.id.shortcut_app3,
            R.id.shortcut_app4,
            R.id.shortcut_app5,
            R.id.shortcut_app6,
            R.id.shortcut_app7,
            R.id.shortcut_app8
    };

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
        // initShortcutImageSize();
        initBtnClick();
        // displayCurrentDateTime();
        initButtons();
    }

    private void initButtons() {
        final FocusContainer menuRow = new FocusContainer();
        for(int i=0; i<menuID.length; i++) {
            final View view = (View) findViewById(menuID[i]);
            view.setOnFocusChangeListener(menuFocusChangeListener);
            menuRow.add(view);
        }
        menuRow.setFocus(menuID.length / 2);

        final FocusContainer shortcutRow = new FocusContainer();
        for(int i=0; i<shortcutID.length; i++) {
            final View view = (View) findViewById(shortcutID[i]);
            view.setOnFocusChangeListener(shortcutFocusChangeListener);
            shortcutRow.add(view);
        }
        shortcutRow.setFocus(shortcutID.length / 2);

        homeRows = new FocusContainer();
        homeRows.setOrientation(FocusContainer.ORIENTATION_VERTICAL);
        homeRows.add(menuRow);
        homeRows.add(shortcutRow);

        // Post hack: update the position of the label as the first UI event,
        // ref: http://stackoverflow.com/questions/7733813/how-can-you-tell-when-a-layout-has-been-drawn/7735122#7735122
        final View view = findViewById(R.id.menu);
        view.post(new Runnable() {
            @Override
            public void run() {
                homeRows.getFocusedView().requestFocus();
            }
        });
    }

    private View.OnFocusChangeListener shortcutFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                v.animate().scaleX(1.25f).setDuration(animateDuration);
                v.animate().scaleY(1.25f).setDuration(animateDuration);
            } else {
                v.animate().scaleX(1f).setDuration(animateDuration);
                v.animate().scaleY(1f).setDuration(animateDuration);
            }
        }
    };

    private View.OnFocusChangeListener menuFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            final TextView hint = (TextView) findViewById(R.id.menu_hint);
            if(hasFocus) {
                hint.setText(v.getContentDescription());
                hint.measure(0, 0);
                final int[] location = new int[2];
                v.getLocationInWindow(location);
                hint.setX(location[0] + v.getWidth() / 2 - hint.getMeasuredWidth() / 2);
                hint.setVisibility(View.VISIBLE);
            } else {
                hint.setVisibility(View.INVISIBLE);
            }
        }
    };

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
        // nowTime.removeCallbacks(runnable);
        // nowDay.removeCallbacks(runnable);
    }

    public void initBtnClick() {

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
        View ly = (View)findViewById(resourceID);
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
        ImageView btn = (ImageView)findViewById(resourceID);
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
//    public void initShortcutImageSize() {
//        for(int i=0;i<shortcutID.length; i++) {
//            resizeImage(shortcutID[i], 0.7);
//        }
//    }
    /*
    * 1. resize larger(focus) image size to smaller(70%)(non-focus) image and assign to the image src
    *
     */
//    public void resizeImage(int imageID, double ratio) {
//        final View view = (View) findViewById(imageID);
//        Double height = new Double(view.getHeight() * ratio);
//        Double width = new Double(view.getWidth() * ratio);
//        GridLayout.LayoutParams params = new GridLayout.LayoutParams(width.intValue(), height.intValue());
//        view.setLayoutParams(params);
//        //view.requestLayout();
//    }

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                homeRows.focusLeft();
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                homeRows.focusDown();
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                homeRows.focusRight();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                homeRows.focusUp();
                break;
            default:
                break;
        }
        homeRows.getFocusedView().requestFocus();
        return true;
    }
}