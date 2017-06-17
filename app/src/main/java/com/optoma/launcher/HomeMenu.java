package com.optoma.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.optoma.launcher.ui.UI;

import java.util.ArrayList;
import java.util.List;

import trikita.anvil.Anvil;
import trikita.anvil.RenderableView;

import static trikita.anvil.DSL.*;
import static trikita.anvil.DSL.view;

public class HomeMenu extends Activity {

    // model
    private static class Model {
        // general
        Page page = Page.Dummy;
        int numCol = 6;
        int focus = 0;

        // apps
        List<ApplicationInfo> apps = new ArrayList<>();

        int startRow = 0;
        int rowShown = 3;
        int dataLength = 0;

        public int getCurrentRow() {
            return focus / numCol;
        }

        public int getRowCount() {
            return dataLength / numCol +
                    ((dataLength % numCol != 0) ? 1 : 0);
        }

    }
    public enum Page {
        Position,
        Apps,
        InputSource,
        Language,
        Dummy
    }

    private Model model = new Model();

    // messages
    static abstract class Action {
        abstract void perform(Model model);
    }

    class OpenPage extends Action {
        private final Page page;
        public OpenPage(Page page) {
            this.page = page;
        }

        @Override
        void perform(Model model) {
            model.page = page;
            if(model.page == Page.Apps) {
                if(pm == null) pm = getPackageManager(); // TODO
                model.apps.clear();
                List<ApplicationInfo> ais = pm.getInstalledApplications(PackageManager.GET_META_DATA);
                for(ApplicationInfo ai : ais) {
                    final Intent intent = pm.getLaunchIntentForPackage(ai.packageName);
                    if(intent!=null) model.apps.add(ai);
                }
                model.dataLength = model.apps.size();
            } else if(model.page == Page.InputSource) {
                model.dataLength = Projector.inputSources.length;
            } else {
                model.dataLength = 0;
            }
            model.focus = 0; // TODO

        }
    }
    class OpenApp extends Action {
        private final ApplicationInfo ai;
        public OpenApp(ApplicationInfo ai) {
            this.ai = ai;
        }

        @Override
        void perform(Model model) {
            if(pm == null) pm = getPackageManager(); // TODO
            showToast("Opening "+ai.loadLabel(pm)+ " ...");
            final Intent intent = pm.getLaunchIntentForPackage(ai.packageName);
            startActivity(intent);
        }
    }
    class Up extends Action {
        @Override
        void perform(Model model) {
            if(model.focus >= model.numCol) {
                model.focus -= model.numCol;
                updateScroll();
            }
        }
    }
    class Down extends Action {
        @Override
        void perform(Model model) {
            if(model.focus < model.dataLength - model.numCol) {
                model.focus += model.numCol;
                updateScroll();
            } else if(model.getCurrentRow() < model.getRowCount() - 1) {
                model.focus = model.dataLength - 1;
                updateScroll();
            }
        }
    }
    class Left extends Action {
        @Override
        void perform(Model model) {
            if(model.focus > 0) {
                model.focus--;
                updateScroll();
            }
        }
    }
    class Right extends Action {
        @Override
        void perform(Model model) {
            if(model.focus < model.dataLength - 1) { // TODO
                model.focus++;
                updateScroll();
            }
        }
    }

    class OK extends Action {
        @Override
        void perform(Model model) {
            switch(model.page) {
                case Apps:
                    update(new OpenApp(model.apps.get(model.focus)));
                    break;
                case InputSource:
                    showToast("Select input source "+Projector.inputSources[model.focus]);
                    break;
                default:
                    break;
            }
        }
    }
    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    private PackageManager pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(new Renderer(this));

        init();

    }

    private void update(Action action) {
        action.perform(model);

        Log.d("ken", String.format("focus: %d, len: %d, current row: %d, row count: %d", model.focus, model.dataLength, model.getCurrentRow(), model.getRowCount()));

        Anvil.render();
    }

    private void updateScroll() {
        final int rowIndex = model.getCurrentRow();
        final int numRow = model.getRowCount();
        if(rowIndex >= model.startRow + model.rowShown && model.startRow+model.rowShown <= numRow) {
            model.startRow++;
        }
        if(rowIndex < model.startRow) {
            model.startRow--;
        }
    }

    private void init() {
        final Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            final Page page = (Page) bundle.get("page");
            update(new OpenPage(page));
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);

        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                update(new Up());
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                update(new Down());
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                update(new Left());
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                update(new Right());
                break;
            case KeyEvent.KEYCODE_ENTER:
                update(new OK());
                break;
        }

        return true;
    }

    private class Renderer extends RenderableView {

        private final Drawable shortcutUnfocused = UI.createRectangle(UI.getColor(UI.primary_gray, 0.7f), 0, 0, Util.dp(HomeMenu.this, 10));
        private final Drawable shortcutFocused = UI.createRectangle(UI.primary_gray, Util.dp(HomeMenu.this, 1), UI.secondary_yellow, Util.dp(HomeMenu.this, 10));
        private final @DrawableRes int[] inputSourceIcons = new int[]{
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

        public Renderer(Context context) {
            super(context);
        }

        @Override
        public void view() {
            linearLayout(() -> {
                size(MATCH, MATCH);
                orientation(LinearLayout.VERTICAL);
                backgroundColor(UI.secondary_gray_80);
                gravity(Gravity.CENTER);

                renderMenu();

                space(() -> {
                    size(0, dip(8));
                });

                UI.createHorizontalBar(dip(254), Color.WHITE);

                switch (model.page) {
                    case Position:
                        renderPositions();
                        break;
                    case Apps:
                    case InputSource:
                        renderAppsOrInputSources();
                        break;
                    case Language:
                        renderLanguages();
                        break;
                    case Dummy:
                        break;
                    default:
                        throw new RuntimeException();
                }

            });

        }

        private void renderMenu() {
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
                        update(new OpenPage(Page.Position));
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
                        update(new OpenPage(Page.Apps));
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
                        update(new OpenPage(Page.InputSource));
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
                        update(new OpenPage(Page.Language));
                    });
                });
            });
        }

        private void renderPositions() {
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

        private void renderLanguages() {
            scrollView(() -> {
                size(MATCH, MATCH);
                margin(dip(80), dip(28), dip(80), dip(200));

                UI.layoutTiles(MATCH, MATCH, 7, Projector.langs.length, -1, dip(32), (Integer index) -> {
                    UI.createLabelLabelTile(Projector.langsEng[index], Projector.langs[index]);
                }, (Integer index) -> {
                    // no-op
                }, null);
            });

        }

        private void renderAppsOrInputSources() {

            if(model.dataLength == 0) {
                textView(() -> {
                    size(MATCH, MATCH);

                    textSize(sip(48));
                    gravity(Gravity.CENTER);
                    text("Empty");
                });
            } else {
                frameLayout(() -> {
                    size(MATCH, MATCH);
                    margin(dip(150), 0, dip(150), 0);

                    UI.layoutTiles(MATCH, MATCH, model.numCol, model.numCol * model.rowShown, -1, dip(20),
                            (index) -> {
                                final int indexInList = model.startRow * model.numCol + index;
                                if(indexInList < model.dataLength) {
                                    final boolean focused = model.focus == indexInList;
                                    if(model.page == Page.Apps) {
                                        final ApplicationInfo item = model.apps.get(indexInList);
                                        renderIconLabel(item.loadIcon(pm), item.loadLabel(pm), focused);
                                    } else {
                                        renderIconLabel(getResources().getDrawable(inputSourceIcons[indexInList]),
                                                Projector.inputSources[indexInList], focused);
                                    }

                                } else {
                                    space(() -> {
                                        size(dip(140), dip(160));
                                    });
                                }
                            },
                            null,
                            null
                    );
                });
            }

        }

        private void renderIconLabel(Drawable icon, CharSequence label, boolean focused) {
            frameLayout(() -> {
                size(dip(140), dip(160));

                linearLayout(() -> {

                    orientation(LinearLayout.VERTICAL);
                    gravity(Gravity.CENTER);
                    layoutGravity(Gravity.CENTER);
//                focusable(true);
//                focusableInTouchMode(true);
//                clickable(true);

                    // weight(1);

                    // Log.d("ken", String.format("focus %d - %d", model.focus, index));
                    if(focused) {
                        background(shortcutFocused);
                        scaleX(1f);
                        scaleY(1f);
                        // backgroundColor(Color.RED);

                    } else {
                        background(shortcutUnfocused);
                        scaleX(0.8f);
                        scaleY(0.8f);
                        // backgroundColor(Color.GREEN);
                    }

                    imageView(() -> {
                        size(dip(86), dip(86));
                        weight(11);

                        focusable(false);
                        focusableInTouchMode(false);
                        clickable(false);

                        imageDrawable(icon);
                        // imageResource(R.drawable.hdmi);
                    });

                    textView(() -> {
                        size(WRAP, WRAP);
                        maxLines(1);
                        ellipsize(TextUtils.TruncateAt.END);
                        focusable(false);
                        focusableInTouchMode(false);
                        clickable(false);

                        textSize(sip(20));
                        if(focused) {
                            typeface(UI.defaultBoldFont);
                        } else {
                            typeface(UI.defaultFont);
                        }
                        weight(5);
                        gravity(Gravity.CENTER);

                        text(label);
                        // text(item.loadLabel(pm));
                    });

                });
            });

        }
    }


}
