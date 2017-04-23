package com.optoma.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        firstBtn = (Button) findViewById(R.id.shortcut_is1);
        firstBtn.requestFocus();
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
                nextDownId = R.id.shortcut_is1;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.menu_apps:
                nextRightId = R.id.menu_is;
                nextLeftId = R.id.menu_position;
                nextDownId = R.id.shortcut_is1;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.menu_is:
                nextRightId = R.id.menu_lang;
                nextLeftId = R.id.menu_apps;
                nextDownId = R.id.shortcut_is1;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.menu_lang:
                //nextRightId = R.id.menu_position;
                nextLeftId = R.id.menu_is;
                nextDownId = R.id.shortcut_is1;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            /*
            * shortcut buttons
             */
            case R.id.shortcut_is1:
                nextRightId = R.id.shortcut_app4;
                nextLeftId = R.id.shortcut_is2;
                nextUpId = R.id.menu_apps;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.shortcut_is2:
                nextRightId = R.id.shortcut_setup;
                nextLeftId = R.id.shortcut_is1;
                nextUpId = R.id.menu_apps;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.shortcut_setup:
                nextRightId = R.id.shortcut_psetup;
                nextLeftId = R.id.shortcut_is2;
                nextUpId = R.id.menu_apps;
                intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
                break;
            case R.id.shortcut_psetup:
                //nextRightId = R.id.shortcut_is1; // disable loop behavior
                nextLeftId = R.id.shortcut_setup;
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