package com.optoma.launcher001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {
    private static final String TAG = "LauncherLog";
    private Button bIS,bSetting,bDS;
    private View mView;
    private FocusView mFocusView;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mView = findViewById(android.R.id.content);
        initView();
    }

    private void initView() {
        //mView.clearFocus();
        bIS = (Button) findViewById(R.id.home_is_button);
        bSetting = (Button) findViewById(R.id.home_settings_button);
        bDS = (Button) findViewById(R.id.home_ds_button);

        //bIS.setOnFocusChangeListener(mOnFocusChangeListener);
        //bSetting.setOnFocusChangeListener(mOnFocusChangeListener);
        //bDS.setOnFocusChangeListener(mOnFocusChangeListener);
        bIS.setOnClickListener(bISOnClick);
        bSetting.setOnClickListener(bSettingOnClick);
        bDS.setOnClickListener(bDSOnClick);
        //tv.setOnFocusChangeListener(mOnFocusChangeListener);
        bIS.setNextFocusRightId(R.id.home_settings_button);
        bSetting.setNextFocusRightId(R.id.home_ds_button);
        bDS.setNextFocusRightId(R.id.home_is_button);
        bIS.requestFocus();
    }

    private View.OnClickListener bISOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Button Input Source clicked");
            intent.setClassName(getPackageName(), getPackageName() + ".InputSource");
            startActivity(intent);
        }
    };

    private View.OnClickListener bSettingOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Button Settings clicked");
        }
    };

    private View.OnClickListener bDSOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Button Display Settings clicked");
        }
    };

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