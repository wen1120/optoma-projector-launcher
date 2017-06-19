package com.optoma.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

        // apps
        List<ApplicationInfo> apps = new ArrayList<>();

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
                model.numCol = 6;
                model.rowShown = 3;
            } else if(model.page == Page.InputSource) {
                model.dataLength = Projector.inputSources.length;
                model.numCol = 6;
                model.rowShown = 3;
            } else if(model.page == Page.Position) {
                model.dataLength = Projector.ProjectionModes.values().length;
                model.numCol = 2;
                model.rowShown = 2;
            } else if(model.page == Page.Language) {
                model.dataLength = Projector.langs.length;
                model.numCol = 7;
                model.rowShown = 4;
            } else {
                model.dataLength = 0;
                model.numCol = 0;
            }
            model.focus = 0; // TODO
            model.startRow = 0;

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

            Intent intent = pm.getLaunchIntentForPackage(ai.packageName);
            if(intent == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent = pm.getLeanbackLaunchIntentForPackage(ai.packageName); // for TV apps
            }
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
        private final @DrawableRes int[] positionIcons = new int[]{
                R.drawable.uf_front,
                R.drawable.uf_rear,
                R.drawable.uf_frontceiling,
                R.drawable.uf_rearceiling
        };
        private final @DrawableRes int[] positionIconsFocusd = new int[]{
                R.drawable.f_front,
                R.drawable.f_rear,
                R.drawable.f_frontceiling,
                R.drawable.f_rearceiling
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

                renderContent();

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
//                    selected(selected);
//                    onClick(v -> {
//                        update(new OpenPage(Page.Position));
//                    });
                });

                space(() -> {
                    size(dip(14), 0);
                });

                imageView(() -> {
                    final boolean selected = model.page == Page.Apps;
                    imageResource(selected ? R.drawable.f_apps : R.drawable.uf_apps);
//                    selected(selected);
//                    onClick(v -> {
//                        update(new OpenPage(Page.Apps));
//                    });
                });

                space(() -> {
                    size(dip(14), 0);
                });

                imageView(() -> {
                    final boolean selected = model.page == Page.InputSource;
                    imageResource(selected ? R.drawable.f_inputsource : R.drawable.uf_inputsource);
//                    selected(selected);
//                    onClick(v -> {
//                        update(new OpenPage(Page.InputSource));
//                    });
                });

                space(() -> {
                    size(dip(14), 0);
                });

                imageView(() -> {
                    final boolean selected = model.page == Page.Language;
                    imageResource(selected ? R.drawable.f_language : R.drawable.uff_language);
//                    selected(selected);
//                    onClick(v -> {
//                        update(new OpenPage(Page.Language));
//                    });
                });
            });
        }

        private void renderContent() {

            if(model.dataLength == 0) {
                textView(() -> {
                    size(MATCH, MATCH);

                    textSize(sip(48));
                    gravity(Gravity.CENTER);
                    text("Empty");
                });
            } else {
                frameLayout(() -> {

                    // backgroundColor(Color.BLUE);

                    if(model.page == Page.Position) {
                        size(WRAP, MATCH);
                        margin(0, dip(52), 0, dip(100));
                        gravity(Gravity.CENTER_HORIZONTAL);

                        UI.layoutTiles(WRAP, WRAP, model.numCol, model.dataLength, dip(38), dip(66),
                                (index) -> {
                                    final boolean focused = model.focus == index;
                                    renderPositions(index, focused);
                                },
                                null,
                                null
                        );
                    } else  {
                        size(MATCH, MATCH);
                        if(model.page == Page.Language) {
                            margin(dip(80), dip(16), dip(80), 0);
                        } else {
                            margin(dip(150), dip(16), dip(150), 0);
                        }

                        final int cellWidth = model.page == Page.Language ? dip(160) : dip(140);
                        final int cellHeight = model.page == Page.Language ? dip(140) : dip(160);

                        UI.layoutTiles(MATCH, MATCH, model.numCol, model.numCol * model.rowShown, -1, dip(20),
                                (index) -> {

                                    final int indexInList = model.startRow * model.numCol + index;
                                    final boolean focused = model.focus == indexInList;

                                    if(indexInList < model.dataLength) {
                                        if (model.page == Page.Apps) {
                                            renderApp(indexInList, focused);
                                        } else if (model.page == Page.InputSource) {
                                            renderInputSource(indexInList, focused);
                                        } else if (model.page == Page.Language) {
                                            renderLanguage(indexInList, focused);
                                        }
                                    } else {
                                        space(() -> {
                                            size(cellWidth, cellHeight);
                                        });
                                    }

                                },
                                null,
                                null
                        );
                    }


                });
            }

        }

        private void renderApp(int index, boolean focused) {
            final ApplicationInfo item = model.apps.get(index);
            renderIconLabel(item.loadIcon(pm), item.loadLabel(pm), focused);
        }

        private void renderInputSource(int index, boolean focused) {
            renderIconLabel(getResources().getDrawable(inputSourceIcons[index]),
                    Projector.inputSources[index], focused);

        }

        private void renderPositions(int index, boolean focused) {
            imageView(() -> {
                size(dip(150), dip(150));
                if(focused) {
                    imageResource(positionIconsFocusd[index]);
                } else {
                    imageResource(positionIcons[index]);
                }
            });
        }

        private void renderLanguage(int index, boolean focused) {
            renderLabelLabel(Projector.langsEng[index], Projector.langs[index], focused);
        }

        private void renderIconLabel(Drawable icon, CharSequence label, boolean focused) {
            frameLayout(() -> {
                size(dip(140), dip(160));
                // backgroundColor(Color.GREEN);

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

        private void renderLabelLabel(CharSequence label1, CharSequence label2, boolean focused) {
            frameLayout(() -> {
                size(dip(160), dip(140));

                linearLayout(() -> {
                    size(MATCH, MATCH);

                    if(focused) {
                        backgroundResource(R.drawable.initial_setup_language_tile_bg_focus); // TODO: rm external dependency
                        scaleX(1);
                        scaleY(1);
                    } else {
                        backgroundResource(R.drawable.initial_setup_language_tile_bg);
                        scaleX(0.8f);
                        scaleY(0.8f);
                    }

                    orientation(LinearLayout.VERTICAL);

                    textView(() -> {
                        size(WRAP, WRAP);
                        margin(dip(12), 0, 0, 0);
                        weight(3);
                        gravity(Gravity.CENTER_VERTICAL);
                        textColor(UI.secondary_gray);
                        textSize(sip(36));
                        typeface(UI.defaultBoldFont);
                        text(label1);
                    });

                    textView(() -> {
                        size(WRAP, WRAP);
                        margin(dip(12), 0, 0, 0);
                        weight(2);
                        gravity(Gravity.CENTER_VERTICAL);
                        textColor(UI.secondary_gray);
                        textSize(sip(20));
                        typeface(UI.defaultBoldFont);
                        text(label2);
                    });

                });
            });


        }
    }


}
