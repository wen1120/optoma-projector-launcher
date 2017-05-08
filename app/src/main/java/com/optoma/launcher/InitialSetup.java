package com.optoma.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

/**
 * Created by linweiting on 2017/4/20.
 */

public class InitialSetup extends Activity {

    private static final int[] positionIds = new int[] {
            R.id.front,
            R.id.rear,
            R.id.front_celling,
            R.id.rear_ceiling
    };

    private static final String[] langsEng = new String[] {
            "ar",
            "cs",
            "da",
            "de",
            "el",
            "en",
            "es",
            "fa",
            "fi",
            "fr",
            "hu",
            "id",
            "it",
            "ja",
            "ko",
            "nl",
            "no",
            "pl",
            "pt",
            "ro",
            "ru",
            "sk",
            "sv",
            "th",
            "tr",
            "vi",
            "zh",
            "zh"
    };

    private static final String[] langs = new String[] {
            "عربي",
            "Čeština",
            "Dansk",
            "Deutsch",
            "ελληνικά",
            "English",
            "Español",
            "فارسی",
            "Suomi",
            "Français",
            "Magyar",
            "Bahasa Indonesia",
            "Italiano",
            "日本語",
            "한국어",
            "Nederlands",
            "Norsk",
            "Polski",
            "Português",
            "Română",
            "Русский",
            "Slovak",
            "Svenska",
            "ไทย",
            "Türkçe",
            "Tiếng Việt",
            "繁體中文",
            "簡体中文"
    };

    private int position;
    private int language;
    private TabBarController tabBarController = null;

    private class TabBarController implements View.OnKeyListener, View.OnFocusChangeListener {
        private int current = 0;
        final View tabBar;
        final ViewFlipper viewFlipper;
        final View[] tabs;

        public TabBarController(View tabBar, ViewFlipper flipper, View... tabs) {
            this.tabBar = tabBar;
            this.viewFlipper = flipper;
            this.tabs = tabs;

            this.tabBar.setOnKeyListener(this);
            this.tabBar.setOnFocusChangeListener(this);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                Log.d("ken", "focusing on tab bar");
            } else {
                Log.d("ken", "leave tab bar");
            }
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(event.getAction() != KeyEvent.ACTION_DOWN) return true;

            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    current--;
                    if(current < 0) current = tabs.length - 1;
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    current++;
                    if(current >= tabs.length) current = 0;
                    break;
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    flipper.requestFocus();
                    break;
            }
            for(int i=0; i<tabs.length; i++) {
                tabs[i].setSelected(false);
            }
            viewFlipper.setDisplayedChild(current);
            tabs[current].setSelected(true);

            return true; // consume the event
        }
    }

    ViewFlipper flipper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_setup);

        final View tabBar = (View) findViewById(R.id.initial_setup_tabbar);
        final View positionTab = (View) findViewById(R.id.position);
        final View languageTab = (View) findViewById(R.id.language);
        flipper = (ViewFlipper) findViewById(R.id.initial_setup_flipper);
        tabBarController = new TabBarController(tabBar, flipper, positionTab, languageTab);

        final LinearLayout grid = (LinearLayout) flipper.getChildAt(1);

        for(int i=0; i<positionIds.length; i++) {
            final View v = findViewById(positionIds[i]);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: change position
                    Toast.makeText(InitialSetup.this,
                            "Setting position to " + v.getContentDescription(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        language = PreferenceManager.getDefaultSharedPreferences(this).getInt("lang", 0);

        position = PreferenceManager.getDefaultSharedPreferences(this).getInt("pos", 0);

        addLanguages(grid);

        tabBar.requestFocus();

    }

    private void addLanguages(LinearLayout grid) {
        final LayoutInflater inflater = getLayoutInflater();
        final int numRow = 4;
        final int numCol = 7;

        for(int rowIndex=0; rowIndex < numRow; rowIndex++) {
            final ViewGroup row = (ViewGroup) inflater.inflate(R.layout.initial_setup_language_row, grid, false);

            for(int colIndex=0; colIndex < numCol; colIndex++) {
                final View tile = inflater.inflate(
                        R.layout.initial_setup_language_tile, row, false);

                final int index = numCol * rowIndex + colIndex;

                final TextView originalName = (TextView) (((ViewGroup) tile).getChildAt(1));
                originalName.setText(langs[index]);

                final TextView englishName = (TextView)  (((ViewGroup) tile).getChildAt(0));
                englishName.setText(langsEng[index]);

                tile.setContentDescription(langsEng[index]);

                tile.setOnFocusChangeListener(new SizeChanger(1.25f, Home.animateDuration) {
                     @Override
                     public void onFocusChange(View v, boolean hasFocus) {
                         super.onFocusChange(v, hasFocus);
                         if(hasFocus) {
                             originalName.setTextColor(Color.parseColor("#2E2A25"));
                             englishName.setTextColor(Color.parseColor("#2E2A25"));
                         } else {
                             originalName.setTextColor(Color.parseColor("#8A8D8F"));
                             englishName.setTextColor(Color.parseColor("#8A8D8F"));
                         }
                     }
                });

                tile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: change language setting
                        Toast.makeText(InitialSetup.this,
                                "Setting language to " + v.getContentDescription(), Toast.LENGTH_SHORT).show();

                        final Intent intent = new Intent(InitialSetup.this, Home.class);
                        startActivity(intent);
                    }
                });

                row.addView(tile);

                if (colIndex != numCol-1) {
                    inflater.inflate(R.layout.space, row);
                }
            }

            grid.addView(row);
            if (rowIndex != numRow - 1 ) {
                inflater.inflate(R.layout.space, grid);
            }
        }
    }
}
