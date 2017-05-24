package com.optoma.launcher.ui;

import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.optoma.launcher.R;

import java.util.function.Consumer;

import trikita.anvil.Anvil;

import static trikita.anvil.DSL.*;


public class UI {
    public static void layoutTiles(int width, int height,
                             int col, int len, int colSpacing, int rowSpacing,
                             Consumer<Integer> createTile,
                             Consumer<Integer> createDummyTile) {
        linearLayout(() -> {
            size(width, height);
            // backgroundColor(Color.RED);
            orientation(LinearLayout.VERTICAL);

            final int row = len / col + (len % col > 0 ? 1 : 0);
            for (int i = 0; i < row; i++) {

                final int rowIndex = i;
                final Anvil.Renderable rowTiles = () -> {
                    size(colSpacing < 0 ? MATCH : WRAP, WRAP);

                    for (int j = 0; j < col; j++) {

                        final int index = rowIndex * col + j;
                        if (index < len) {
                            createTile.accept(index);
                        } else {
                            // dummy tile
                            createDummyTile.accept(index);
                        }

                        // col spacing
                        if (colSpacing != 0 && j < col - 1) {
                            space(() -> {
                                if(colSpacing < 0) {
                                    weight(1);
                                } else {
                                    size(colSpacing, 0);
                                }
                            });
                        }
                    }
                };

                if(col == 1) {
                    rowTiles.view();
                } else {
                    linearLayout(rowTiles);
                }

                // row spacing
                if(rowSpacing != 0 && i < row - 1) {
                    space(() -> {
                        if(rowSpacing < 0) {
                            weight(1);
                        } else {
                            size(0, rowSpacing);
                        }
                    });

                }
            }
        });

    }

    public static  void createIconLabelTile(int width, int height, @DrawableRes int icon, String label,
                                     @DrawableRes int background) {
        linearLayout(() -> {
            size(width, height);
            orientation(LinearLayout.VERTICAL);
            gravity(Gravity.CENTER);
            // backgroundColor(Color.parseColor("#2e2a25"));
            backgroundResource(background);
            focusable(true);
            focusableInTouchMode(true);
            clickable(true);

            imageView(() -> {
                size(dip(64), dip(64));
                weight(11);
                imageResource(icon);
            });

            textView(() -> {
                size(WRAP, WRAP);
                textSize(24);
                weight(5);
                text(label);
            });

        });
    }

    public static  void createIconTile(int width, int height, @DrawableRes int icon) {
        imageView(() -> {
            size(width, height);
            imageResource(icon);
            focusable(true);
            focusableInTouchMode(true);
            clickable(true);
            // backgroundColor(Color.BLUE);
        });
    }

    public static  void createLabelLabelTile(String label1, String label2) {
        xml(R.layout.initial_setup_language_tile, () -> { // TODO: remove xml
            withId(R.id.language_name_eng, () -> {
                text(label1);
            });

            withId(R.id.language_name, () -> {
                text(label2);
            });
        });
    }
}
