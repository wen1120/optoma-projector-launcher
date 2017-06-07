package com.optoma.launcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_setup);

        // final View tabBar = (View) findViewById(R.id.initial_setup_tabbar);
        final View positionTab = (View) findViewById(R.id.position);
        positionTab.setSelected(true);

        final View languageTab = (View) findViewById(R.id.language);
        final ViewFlipper flipper = (ViewFlipper) findViewById(R.id.initial_setup_flipper);
        final ViewGroup grid = (ViewGroup) flipper.findViewById(R.id.lang_content);

        for(int i=0; i<positionIds.length; i++) {
            final View v = findViewById(positionIds[i]);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: change position
                    Toast.makeText(InitialSetup.this,
                            "Setting position to " + v.getContentDescription(), Toast.LENGTH_SHORT).show();
                    positionTab.setSelected(false);
                    languageTab.setSelected(true);
                    flipper.setDisplayedChild(1);
                }
            });
        }

        addLanguages(grid);

        flipper.requestFocus();

    }

    private void addLanguages(ViewGroup grid) {
        final LayoutInflater inflater = getLayoutInflater();
        final int numRow = 4;
        final int numCol = 7;

        for(int rowIndex=0; rowIndex < numRow; rowIndex++) {
            final ViewGroup row = (ViewGroup) inflater.inflate(R.layout.initial_setup_language_row, grid, false);

            for(int colIndex=0; colIndex < numCol; colIndex++) {
                final View tile = inflater.inflate(
                        R.layout.initial_setup_language_tile, row, false);

                tile.setAlpha(0.7f);

                final int index = numCol * rowIndex + colIndex;

                final TextView originalName = (TextView) (((ViewGroup) tile).getChildAt(1));
                originalName.setText(Projector.langs[index]);

                final TextView englishName = (TextView)  (((ViewGroup) tile).getChildAt(0));
                englishName.setText(Projector.langsEng[index]);

                tile.setContentDescription(Projector.langsEng[index]);

                tile.setOnFocusChangeListener(new SizeChanger(1.25f, Home.animateDuration) {
                     @Override
                     public void onFocusChange(View v, boolean hasFocus) {
                         super.onFocusChange(v, hasFocus);
                         if(hasFocus) {
                             originalName.setTextColor(Color.parseColor("#2E2A25"));
                             englishName.setTextColor(Color.parseColor("#2E2A25"));
                             v.setAlpha(1.0f);
                         } else {
                             originalName.setTextColor(Color.parseColor("#8A8D8F"));
                             englishName.setTextColor(Color.parseColor("#8A8D8F"));
                             v.setAlpha(0.7f);
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
                final Space space = new Space(this);
                space.setLayoutParams(new ViewGroup.LayoutParams(0, Util.dp(this, 36)));
                grid.addView(space);
            }
        }
    }
}
