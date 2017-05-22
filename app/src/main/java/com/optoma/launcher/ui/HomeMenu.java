package com.optoma.launcher.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.optoma.launcher.Projector;
import com.optoma.launcher.R;

import java.util.function.Consumer;

import trikita.anvil.RenderableView;

import static trikita.anvil.DSL.*;
import static trikita.anvil.DSL.view;

public class HomeMenu extends Activity {

    public enum Page {
        Position,
        Apps,
        InputSource,
        Language
    }

    private static class Model {
        Page page = Page.Language;
    }

    private Model model = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        model.page = (Page) getIntent().getExtras().get("page");

        setContentView(new RenderableView(this) {
            @Override
            public void view() {
                linearLayout(() -> {
                    size(MATCH, MATCH);
                    orientation(LinearLayout.VERTICAL);
                    gravity(Gravity.CENTER);

                    backgroundResource(R.drawable.backgroundhome);

                    createMenu();

                    // horizontal bar
                    frameLayout(() -> {
                        size(dip(254), dip(1));
                        margin(0, dip(8), 0, 0);
                        backgroundColor(Color.WHITE);
                    });

                    switch (model.page) {
                        case Position:
                            createPositions();
                            break;
                        case Apps:
                            space(() -> {
                                size(MATCH, MATCH);
                            });
                            break;
                        case InputSource:
                            createInputSources();
                            break;
                        case Language:
                            createLanguages();
                            break;
                        default:
                            throw new RuntimeException();
                    }

                });

            }

            private void createMenu() {
                linearLayout(() -> {

                    size(MATCH, WRAP);

                    margin(0, dip(20), 0, 0);

                    // backgroundColor(Color.BLUE);

                    gravity(Gravity.CENTER);

                    imageView(() -> {
                        final boolean selected = model.page == Page.Position;
                        imageResource(selected ? R.drawable.ff_position : R.drawable.uf_position);
                        selected(selected);

                        onClick(v -> {
                            model.page = Page.Position;
                        });
                    });

                    space(() -> {
                        size(dip(14), 0);
                    });

                    imageView(() -> {
                        final boolean selected = model.page == Page.Apps;
                        imageResource(selected ? R.drawable.f_apps : R.drawable.uf_apps);
                        selected(selected);
                        onClick(v -> {
                            model.page = Page.Apps;
                        });
                    });

                    space(() -> {
                        size(dip(14), 0);
                    });

                    imageView(() -> {
                        final boolean selected = model.page == Page.InputSource;
                        imageResource(selected ? R.drawable.f_inputsource : R.drawable.uf_inputsource);
                        selected(selected);
                        onClick(v -> {
                            model.page = Page.InputSource;
                        });
                    });

                    space(() -> {
                        size(dip(14), 0);
                    });

                    imageView(() -> {
                        final boolean selected = model.page == Page.Language;
                        imageResource(selected ? R.drawable.f_language : R.drawable.uff_language);
                        selected(selected);
                        onClick(v -> {
                            model.page = Page.Language;
                        });
                    });
                });
            }

            private void createPositions() {
                final @DrawableRes int[] icons = new int[]{
                        R.drawable.initial_setup_position_front,
                        R.drawable.initial_setup_position_rear,
                        R.drawable.initial_setup_position_front_ceiling,
                        R.drawable.initial_setup_position_rear_ceiling
                };


                linearLayout(() -> {
                    size(MATCH, MATCH);
                    gravity(Gravity.CENTER);
                    orientation(LinearLayout.VERTICAL);
                    // backgroundColor(Color.GREEN);
                    margin(dip(150), dip(52), dip(150), dip(100));

                    layoutTiles(WRAP, MATCH, 2, icons.length, dip(38), dip(66),
                            (Integer index) -> {
                                createIconTile(dip(150), dip(150), icons[index]);
                            }, (Integer index) -> {
                                space(() -> {
                                    size(dip(150), dip(150));
                                });
                            });
                });


            }

            private void createInputSources() {

                final @DrawableRes int[] icons = new int[]{
                        R.drawable.miracast,
                        R.drawable.vga,
                        R.drawable.hdmi,
                        R.drawable.hdmi,
                        R.drawable.hdmi,
                        R.drawable.displayport,
                        R.drawable.usb,
                        R.drawable.usb,
                        R.drawable.s_video,
                        R.drawable.addshortcut
                };

                final String[] labels = new String[]{
                        "Miracast",
                        "VGA",
                        "HDMI 1",
                        "HDMI 2",
                        "HDMI 3",
                        "Display Port",
                        "USB 1",
                        "USB 2",
                        "S-video",
                        "Set Shortcut"
                };

                frameLayout(() -> {
                    size(MATCH, MATCH);
                    margin(dip(150), dip(52), dip(150), dip(100));

                    layoutTiles(MATCH, MATCH, 6, icons.length, -1, dip(56), (Integer index) -> {
                        createIconLabelTile(dip(112), dip(128), icons[index], labels[index], R.drawable.initial_setup_language_tile_bg);
                    }, (Integer index) -> {
                        space(() -> {
                            size(dip(112), dip(128));
                        });
                    });
                });

            }

            private void createLanguages() {
                scrollView(() -> {
                    size(MATCH, MATCH);
                    margin(dip(80), dip(28), dip(80), dip(80));

                    layoutTiles(MATCH, MATCH, 7, Projector.langs.length, -1, dip(32), (Integer index) -> {
                        createLabelLabelTile(Projector.langsEng[index], Projector.langs[index]);
                    }, (Integer index) -> {
                        // noop
                    });
                });

            }


            private void layoutTiles(int width, int height,
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
                        linearLayout(() -> {
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
                                if (j < col - 1) {
                                    space(() -> {
                                        if(colSpacing < 0) {
                                            weight(1);
                                        } else {
                                            size(colSpacing, 0);
                                        }
                                    });
                                }
                            }
                        });

                        // row spacing
                        space(() -> {
                            size(0, rowSpacing);
                        });
                    }
                });

            }

            private void createIconLabelTile(int width, int height, @DrawableRes int icon, String label,
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

            private void createIconTile(int width, int height, @DrawableRes int icon) {
                imageView(() -> {
                    size(width, height);
                    imageResource(icon);
                    focusable(true);
                    focusableInTouchMode(true);
                    clickable(true);
                    // backgroundColor(Color.BLUE);
                });
            }

            private void createLabelLabelTile(String label1, String label2) {
                xml(R.layout.initial_setup_language_tile, () -> { // TODO: remove xml
                    withId(R.id.language_name_eng, () -> {
                       text(label1);
                    });

                    withId(R.id.language_name, () -> {
                        text(label2);
                    });
                });
            }
        });
    }


}
