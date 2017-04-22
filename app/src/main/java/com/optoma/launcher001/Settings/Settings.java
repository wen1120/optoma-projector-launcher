package com.optoma.launcher001.Settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.optoma.launcher.R;

import java.util.ArrayList;

public class Settings extends Activity {
    private static final String TAG = "LauncherLog";
    private static final int xLimit = 3, yLimit = 3;
    private int xPosition, yPosition, xFocus, yFocus;
    private TextView SettingTV;
    private TextView[] SystemTitleTV  = new TextView[3];
    private TextView[] SystemContentTV  = new TextView[3];
    private ArrayList<String> aSystemTitle = new ArrayList<String>();
    private ArrayList<String> aSystemContent = new ArrayList<String>();
    private Button bAudio,bInfo;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        xPosition = yPosition = xFocus = yFocus = 0;
        SettingTV = (TextView) findViewById(R.id.setting_textview);
        SystemTitleTV[0] = (TextView) findViewById(R.id.setting_tv_upper_title);
        SystemTitleTV[1] = (TextView) findViewById(R.id.setting_tv_middle_title);
        SystemTitleTV[2] = (TextView) findViewById(R.id.setting_tv_lower_title);
        SystemContentTV[0] = (TextView) findViewById(R.id.setting_tv_upper_content);
        SystemContentTV[1] = (TextView) findViewById(R.id.setting_tv_middle_content);
        SystemContentTV[2] = (TextView) findViewById(R.id.setting_tv_lower_content);
        for (String s : getResources().getStringArray(
                R.array.settingTitle)) {
            aSystemTitle.add(s);
        }
        for (String s : getResources().getStringArray(
                R.array.settingContent)) {
            aSystemContent.add(s);
        }
        bAudio = (Button) findViewById(R.id.setting_audio_button);
        bAudio.setOnClickListener(bAudioOnClick);
        bInfo = (Button) findViewById(R.id.setting_information_button);
        bInfo.setOnClickListener(bInfoOnClick);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetSettingTV(true);
    }

    private View.OnClickListener bAudioOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Button Audio clicked");
            SetSettingTV(false);
            intent.setClassName(getPackageName(), getPackageName() + ".Settings.Audio");
            startActivity(intent);
        }
    };

    private View.OnClickListener bInfoOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Button Information clicked");
            SetSettingTV(false);
            intent.setClassName(getPackageName(), getPackageName() + ".Settings.Information");
            startActivity(intent);
        }
    };

    private void SetSettingTV(boolean show) {
        int iShow = show ? View.VISIBLE : View.INVISIBLE;
        SettingTV.setVisibility(iShow);
        SystemTitleTV[yFocus].setVisibility(iShow);
        SystemContentTV[yFocus].setVisibility(iShow);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //if (true == bFocus) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_UP:
                    if(yPosition > 0) {
                        yPosition--;
                        SetTitleContent(xPosition,yPosition);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if(yPosition < yLimit - 1) {
                        yPosition++;
                        SetTitleContent(xPosition,yPosition);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if(xPosition > 0) {
                        xPosition--;
                        SetTitleContent(xPosition,yPosition);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if(xPosition < xLimit - 1) {
                        xPosition++;
                        SetTitleContent(xPosition,yPosition);
                    }
                    break;
            }


        Log.d(TAG, "activity onKeyDown: ");
        return super.onKeyDown(keyCode, event);
    }

    public void SetTitleContent(int x, int y) {
        for(int i=0;i<3;i++) {
            if(i != y) {
                SystemTitleTV[i].setVisibility(View.INVISIBLE);
                SystemContentTV[i].setVisibility(View.INVISIBLE);
            } else {
                SystemTitleTV[i].setText(aSystemTitle.get(3*y + x));
                SystemContentTV[i].setText(aSystemContent.get(3*y + x));
                SystemTitleTV[i].setVisibility(View.VISIBLE);
                SystemContentTV[i].setVisibility(View.VISIBLE);
                xFocus = x;
                yFocus = y;
            }
        }
    }
}