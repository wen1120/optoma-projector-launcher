package com.optoma.launcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
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

    private static final String[] languagesEng = new String[] {
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

    private static final String[] languages = new String[] {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_setup);

        final View positionButton = (View) findViewById(R.id.position);
        final View languageButton = (View) findViewById(R.id.language);
        final ViewFlipper flipper = (ViewFlipper) findViewById(R.id.initial_setup_flipper);

        final LinearLayout grid = (LinearLayout) flipper.getChildAt(1);

        final LayoutInflater inflater = getLayoutInflater();
        int index = 0;

        for(int i=0; i<4; i++) {
            final ViewGroup row = (ViewGroup) inflater.inflate(R.layout.initial_setup_language_row, grid, false);

            for(int j=0; j<7; j++) {
                final View element = inflater.inflate(
                        R.layout.initial_setup_language_element, row, false);

                final TextView languageName = (TextView) (((ViewGroup) element).getChildAt(1));
                languageName.setText(languages[index]);

                final TextView languageNameEnglish = (TextView)  (((ViewGroup) element).getChildAt(0));
                languageNameEnglish.setText(languagesEng[index]);

                element.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus) {
                            languageName.setTextColor(Color.parseColor("#2E2A25"));
                            languageNameEnglish.setTextColor(Color.parseColor("#2E2A25"));

                            v.animate().scaleX(1.25f).setDuration(home.animateDuration);
                            v.animate().scaleY(1.25f).setDuration(home.animateDuration);
                        } else {
                            languageName.setTextColor(Color.parseColor("#8A8D8F"));
                            languageNameEnglish.setTextColor(Color.parseColor("#8A8D8F"));

                            v.animate().scaleX(1f).setDuration(home.animateDuration);
                            v.animate().scaleY(1f).setDuration(home.animateDuration);
                        }
                    }
                });

                element.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(InitialSetup.this, home.class);
                        startActivity(intent);
                    }
                });

                row.addView(element);

                if (j != 6) {
                    inflater.inflate(R.layout.space, row);
                }

                index++;
            }

            grid.addView(row);
            if (i !=3 ) {
                inflater.inflate(R.layout.space, grid);
            }
        }

        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.setDisplayedChild(0);
            }
        });

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.setDisplayedChild(1);
            }
        });

        for(int i=0; i<positionIds.length; i++) {
            final View v = findViewById(positionIds[i]);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(InitialSetup.this, "Setting position...", Toast.LENGTH_LONG);
                }
            });
        }

        positionButton.requestFocus();

    }
}
