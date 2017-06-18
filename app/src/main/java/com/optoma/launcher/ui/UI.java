package com.optoma.launcher.ui;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.optoma.launcher.R;

import trikita.anvil.Anvil;

import static trikita.anvil.DSL.*;


public class UI {

    public static final int primary_red = Color.parseColor("#cf0a2c");
    public static final int primary_black = Color.parseColor("#2e2a25");
    public static final int primary_black_transparent = Color.parseColor("#992e2a25");
    public static final int primary_gray = Color.parseColor("#5b646f");
    public static final int primary_white = Color.parseColor("#ffffff");
    public static final int secondary_dark_blue = Color.parseColor("#004b87");
    public static final int secondary_blue = Color.parseColor("#009cde");
    public static final int secondary_light_blue = Color.parseColor("#71c5e8");
    public static final int secondary_cyan = Color.parseColor("#00c7b1");
    public static final int secondary_yellow = Color.parseColor("#eada24");
    public static final int secondary_gray = Color.parseColor("#8a8d8f");
    public static final int secondary_gray_80 = Color.parseColor("#808a8d8f");
    public static final int secondary_light_gray = Color.parseColor("#d0d3d4");
    public static final int secondary_light_brown = Color.parseColor("#9a887b");

    public static final Typeface defaultFont = Typeface.create("sans-serif", Typeface.NORMAL); // roboto
    public static final Typeface defaultBoldFont = Typeface.create("sans-serif", Typeface.BOLD); // roboto bold

    public interface Consumer<T> {
        void accept(T t);
    }

    private static int getRowCount(int len, int numCol) {
        return len / numCol + (len % numCol > 0 ? 1 : 0);
    }

    private static int getIndex(int rowIndex, int numCol, int col) {
        return rowIndex * numCol + col;
    }

    public static void layoutTiles(int width, int height,
                             int numCol, int len, int colSpacing, int rowSpacing,
                             Consumer<Integer> createTile,
                             Consumer<Integer> createDummyTile,
                             Anvil.Renderable r) {
        linearLayout(() -> {
            size(width, height);
            // backgroundColor(Color.RED);
            orientation(LinearLayout.VERTICAL);

            final int numRow = getRowCount(len, numCol);

            for (int row = 0; row < numRow; row++) {

                final int rowIndex = row;

                final Anvil.Renderable rowTiles = () -> {

                    size(colSpacing < 0 ? MATCH : WRAP, WRAP);

                    for (int col = 0; col < numCol; col++) {

                        final int index = getIndex(rowIndex, numCol, col);

                        if (index < len) {
                            createTile.accept(index);
                        } else {
                            // dummy tile
                            if(createDummyTile!=null) createDummyTile.accept(index);
                        }

                        // col spacing
                        if (colSpacing != 0 && col < numCol - 1) {
                            final int colIndex = col;
                            space(() -> {
                                // backgroundColor(Color.RED);

                                if(colSpacing < 0) {
                                    weight(1);
                                    size(0, 0);
                                } else {
                                    weight(0);
                                    size(colSpacing, 1);
                                }
                            });
                        }
                    }
                };

                if(numCol == 1) {
                    rowTiles.view();
                } else {
                    linearLayout(rowTiles);
                }

                // row spacing
                if(rowSpacing != 0 && row < numRow - 1) {
                    space(() -> {
                        if(rowSpacing < 0) {
                            weight(1);
                        } else {
                            size(0, rowSpacing);
                        }
                    });

                }
            }

            if(r!=null) r.view();
        });

    }

    public static  void createIconLabelTile(int width, int height,
                                            @DrawableRes int icon,
                                            float iconResizeFactor,
                                            String label,
                                            int fontSize,
                                            Drawable background) {
        linearLayout(() -> {
            size(width, height);
            orientation(LinearLayout.VERTICAL);
            gravity(Gravity.CENTER);
            // backgroundColor(Color.parseColor("#2e2a25"));
            background(background);
            focusable(true);
            focusableInTouchMode(true);
            clickable(true);

            imageView(() -> {
                size(dip(Math.round(86 * iconResizeFactor)), dip(Math.round(76 * iconResizeFactor)));
                weight(11);
                imageResource(icon);
            });

            textView(() -> {
                size(WRAP, WRAP);
                textSize(fontSize);
                weight(5);
                gravity(Gravity.CENTER);
                text(label);
            });

        });
    }

    public static  void createIconLabelTile(int width, int height,
                                     Drawable icon,
                                     float iconResizeFactor,
                                     String label,
                                     int fontSize,
                                     Drawable background) {
        linearLayout(() -> {
            size(width, height);
            orientation(LinearLayout.VERTICAL);
            gravity(Gravity.CENTER);
            // backgroundColor(Color.parseColor("#2e2a25"));
            background(background);
            focusable(true);
            focusableInTouchMode(true);
            clickable(true);

            imageView(() -> {
                size(dip(Math.round(86 * iconResizeFactor)), dip(Math.round(76 * iconResizeFactor)));
                weight(11);
                imageDrawable(icon);
            });

            textView(() -> {
                size(WRAP, WRAP);
                textSize(fontSize);
                weight(5);
                gravity(Gravity.CENTER);
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

    public static void createHorizontalBar(int width, int color) {
        v(View.class, () -> {
            size(width, dip(1));
            backgroundColor(color);
        });
    }

    public static GradientDrawable createRectangle(int bgColor, int borderWidth, int borderColor, int cornerRadius) {
        return createRectangle(
                bgColor, borderWidth, borderColor, cornerRadius, cornerRadius, cornerRadius, cornerRadius);
    }

    public static GradientDrawable createRectangle(
            int bgColor, int borderWidth, int borderColor, int upperLeft,
            int upperRight, int lowerRight, int lowerLeft) {

        final GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] {
                upperLeft, upperLeft, upperRight, upperRight, lowerRight, lowerRight, lowerLeft, lowerLeft });
        shape.setColor(bgColor);
        shape.setStroke(borderWidth, borderColor);
        return shape;
    }

    // modified from https://stackoverflow.com/questions/15319635/manipulate-alpha-bytes-of-java-android-color-int
    public static int getColor(int color, float alphaFactor) {
        int alpha = Math.round(Color.alpha(color) * alphaFactor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
