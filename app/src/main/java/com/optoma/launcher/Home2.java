package com.optoma.launcher;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.optoma.launcher.ui.UI;

import java.util.Arrays;
import java.util.List;

import trikita.anvil.Anvil;
import trikita.anvil.RenderableView;
import static trikita.anvil.DSL.*;

public class Home2 extends Activity {

    static class Model {
        int root = 0;
        int[] children = new int[2];

        @Override
        public String toString() {
            return String.format("%d (%d, %d)", root, children[0], children[1]);
        }
    }

    private Model model = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new RenderableView(this) {
            @Override
            public void view() {

                linearLayout(() -> {
                    orientation(LinearLayout.VERTICAL);

                    createTitleBar();

                    space(() -> {
                        size(MATCH, 0);
                        weight(1);
                    });

                    createShortcuts();
                });

            }
        });
    }

    private void createTitleBar() {
        linearLayout(() -> {
            orientation(LinearLayout.HORIZONTAL);
            backgroundColor(Color.GREEN);
            size(MATCH, WRAP);
            margin(dip(48), dip(18), dip(48), 0);

            createLogo();

            space(() -> {
                weight(1);
            });

            linearLayout(() -> {
                orientation(LinearLayout.VERTICAL);
                gravity(Gravity.CENTER);
                backgroundColor(Color.RED);
                size(WRAP, WRAP);

                createTime();

                // TODO: fix width
                UI.createHorizontalBar(dip(110), UI.primary_white);

                createDate();

            });
        });
    }

    private Drawable shortcutUnfocused = UI.createRectangle(UI.getColor(UI.primary_black, 0.7f), 0, 0, 10);
    private Drawable shortcutFocused = UI.createRectangle(UI.primary_black, 1, UI.secondary_yellow, 10);

    private void createShortcuts() {
        frameLayout(() -> {
            size(MATCH, dip(200));
            backgroundResource(R.drawable.backgroundhome);
            gravity(Gravity.CENTER);

            final int labelSizeUnfocused = 16;
            final int labelSizeFocused = 20;

            final List<UI.Consumer<Boolean>> shortcuts = Arrays.asList(
                    (f) -> {
                        space(() -> {
                            size(dip(112), dip(128));
                        });
                    },
                    (f) -> {
                        space(() -> {
                            size(dip(112), dip(128));
                        });
                    },
                    (f) -> {
                        space(() -> {
                            size(dip(112), dip(128));
                        });
                    },
                    (f) -> {
                        space(() -> {
                            size(dip(112), dip(128));
                        });
                    },
                    (f) -> {
                        createShortcut(() -> {
                            UI.createIconLabelTile(dip(112), dip(128),
                                    getResources().getDrawable(R.drawable.hdmi),
                                    0.7f, "HDMI", labelSizeUnfocused, shortcutUnfocused);
                        });
                    },
                    (f) -> {
                        if(f) {
                            createShortcut(() -> {
                                UI.createIconLabelTile(dip(140), dip(160),
                                        getResources().getDrawable(R.drawable.vga),
                                        1f, "VGA", labelSizeFocused, shortcutFocused);
                            });
                        } else {
                            createShortcut(() -> {
                                UI.createIconLabelTile(dip(112), dip(128),
                                        getResources().getDrawable(R.drawable.vga),
                                        0.7f, "VGA", labelSizeUnfocused, shortcutUnfocused);
                            });
                        }
                    },
                    (f) -> {
                        createShortcut(() -> {
                            UI.createIconLabelTile(dip(112), dip(128),
                                    getResources().getDrawable(R.drawable.settings),
                                    0.7f, "Settings", labelSizeUnfocused, shortcutUnfocused);
                        });
                    },
                    (f) -> {
                        createShortcut(() -> {
                            UI.createIconLabelTile(dip(112), dip(128),
                                    getResources().getDrawable(R.drawable.projectorsetup),
                                    0.7f, "Projector\nSetup", labelSizeUnfocused, shortcutUnfocused);
                        });
                    }
            );

            UI.layoutTiles(MATCH, MATCH, 8, 8, -1, 0, (index) -> {
                if(model.root == 1 && model.children[model.root] == index) {
                    shortcuts.get(index).accept(true);
                }  else {
                    shortcuts.get(index).accept(false);
                }
            }, (index) -> {
                // no-op
            }, () -> {
                backgroundColor(Color.BLUE);
                margin(dip(66), dip(20), dip(66), dip(20));
            });
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // return super.onKeyDown(keyCode, event);
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                model.root = (model.root == 1) ? 0 : 1;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                model.root = (model.root == 0) ? 1 : 0;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(model.children[model.root] > 0)
                    model.children[model.root] -= 1;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if(model.children[model.root] < 7)
                    model.children[model.root] += 1;
                break;
        }
        Anvil.render();
        Log.d("ken", model.toString());
        return true;
    }

    private void createLogo() {
        imageView(() -> {
            size(WRAP, dip(39));
            margin(0, dip(9), 0, 0);
            imageResource(R.drawable.optomalogo);
            backgroundColor(Color.BLUE);
        });
    }

    private void createTime() {
        textClock(() -> {
            size(WRAP, dip(48));
            format12Hour("HH:mm");
            textSize(42);
            backgroundColor(Color.BLUE);
            textColor(UI.secondary_light_gray);
            gravity(Gravity.CENTER);
        });
    }

    private void createDate() {
        textClock(() -> {
            format12Hour("EE d MMM");
            textSize(20);
            backgroundColor(Color.BLUE);
            textColor(UI.primary_white);
            gravity(Gravity.CENTER);
        });
    }

    private void createShortcut(Anvil.Renderable r) {
        linearLayout(() -> {
            size(dip(140), dip(160));
            gravity(Gravity.CENTER);
            backgroundColor(Color.GREEN);

            r.view();
        });

    }

}
