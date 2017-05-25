package com.optoma.launcher;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.optoma.launcher.Projector;
import com.optoma.launcher.R;
import com.optoma.launcher.ui.UI;

import java.util.function.Consumer;

import trikita.anvil.RenderableView;

import static trikita.anvil.DSL.*;
import static trikita.anvil.DSL.view;

public class HomeMenu extends Activity {

    private static final Drawable shortcutUnfocused = UI.createRectangle(UI.getColor(UI.primary_black, 0.7f), 0, 0, 10);

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

                    space(() -> {
                        size(0, dip(8));
                    });

                    UI.createHorizontalBar(dip(254), getResources().getColor(R.color.primary_white));

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

                    UI.layoutTiles(WRAP, MATCH, 2, icons.length, dip(38), dip(66),
                            (Integer index) -> {
                                UI.createIconTile(dip(150), dip(150), icons[index]);
                            }, (Integer index) -> {
                                space(() -> {
                                    size(dip(150), dip(150));
                                });
                            }, null);
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

                    UI.layoutTiles(MATCH, MATCH, 6, icons.length, -1, dip(56), (Integer index) -> {
                        UI.createIconLabelTile(
                                dip(112), dip(128), icons[index], 0.7f, labels[index], 24,
                                shortcutUnfocused);
                    }, (Integer index) -> {
                        space(() -> {
                            size(dip(112), dip(128));
                        });
                    }, null);
                });

            }

            private void createLanguages() {
                scrollView(() -> {
                    size(MATCH, MATCH);
                    margin(dip(80), dip(28), dip(80), dip(80));

                    UI.layoutTiles(MATCH, MATCH, 7, Projector.langs.length, -1, dip(32), (Integer index) -> {
                        UI.createLabelLabelTile(Projector.langsEng[index], Projector.langs[index]);
                    }, (Integer index) -> {
                        // no-op
                    }, null);
                });

            }



        });
    }


}
