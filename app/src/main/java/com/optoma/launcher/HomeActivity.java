package com.optoma.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.optoma.launcher.Settings.Settings;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* gradle debug on command line on Mac
* $ brew install gradle
* $ gradle compileDebugSources --stacktrace --debug
 */
public class HomeActivity extends Activity {
    private static final String TAG = "launcherLog";
    public static long animateDuration = 80;
    private FocusContainer homeRows;
    @BindView(R.id.menu_wrapper) View menu;
    @BindView(R.id.menu_hint) View menuHint;

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
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initBtnClick();
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

        // ip address
//        final WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
//        final String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
//        final TextView ipAddr = (TextView) findViewById(R.id.ipaddr);
//        ipAddr.setText(ip);
    }

    private View.OnFocusChangeListener shortcutFocusChangeListener =
            new SizeChanger(1f, 0.8f, animateDuration) {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    super.onFocusChange(v, hasFocus);
                    v.setAlpha(hasFocus ? 1f : 0.7f);
                }
            };

    private View.OnFocusChangeListener menuFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            adjustHint(v, hasFocus);
        }
    };

    private void adjustHint(View v, boolean hasFocus) {
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

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            setFullscreen();
//        }
//    }

    private void setFullscreen() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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
        final int rID;
        rID = resourceID;
        View ly = findViewById(resourceID);
        ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (rID) {

                    case R.id.shortcut_app4: {
                        final Intent intent = new Intent();
                        intent.setClass(HomeActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    }

                    case R.id.shortcut_app5: {
                        new Projector(HomeActivity.this).startHmdi();
                    }
                    case R.id.shortcut_app6:
                        break;
                    case R.id.shortcut_app7: {
                        final Intent intent = new Intent();
                        intent.setClass(HomeActivity.this, Settings.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.shortcut_app8: {
                        final Intent intent = new Intent();
                        intent.setClass(HomeActivity.this, ProjectorSetupActivity.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                        break;
                }

            }
        });
    }


    private static final int MENU = 0;

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
                final Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
                switch (rID) {
                    case R.id.menu_position:
                        intent.putExtra("page", MenuActivity.Page.Position);
                        break;
                    case R.id.menu_apps:
                        intent.putExtra("page", MenuActivity.Page.Apps);
                        break;
                    case R.id.menu_is:
                        intent.putExtra("page", MenuActivity.Page.InputSource);
                        break;
                    case R.id.menu_lang:
                        intent.putExtra("page", MenuActivity.Page.Language);
                        break;
                    default:
                        break;
                }
                menu.setAlpha(0);
                menuHint.setAlpha(0);
                startActivityForResult(intent, MENU);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MENU) {
            menu.setAlpha(1);
            menuHint.setAlpha(1);
        }
    }

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
