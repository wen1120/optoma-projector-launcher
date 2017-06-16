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
import android.util.TimingLogger;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.optoma.launcher.ui.UI;

import java.util.ArrayList;
import java.util.List;

import trikita.anvil.Anvil;
import trikita.anvil.BaseDSL;
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
        List<Drawable> icons = new ArrayList<>();
        int startRow = 0;
        int rowShown = 3;

        public int getCurrentRow() {
            return focus / numCol;
        }

        public int getRowCount() {
            return apps.size() / numCol +
                    ((apps.size() % numCol != 0) ? 1 : 0);
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
            }
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
            Toast.makeText(getApplicationContext(), "opening "+ai.packageName + " ...", Toast.LENGTH_SHORT).show();
            final Intent intent = pm.getLaunchIntentForPackage(ai.packageName);
            startActivity(intent);
        }
    }
    class FocusUp extends Action {
        @Override
        void perform(Model model) {
            if(model.focus >= model.numCol) {
                model.focus -= model.numCol;
                updateScroll();
            }
        }
    }
    class FocusDown extends Action {
        @Override
        void perform(Model model) {
            if(model.focus < model.apps.size() - model.numCol) { // TODO
                model.focus += model.numCol;
                updateScroll();
            } else if(model.getCurrentRow() < model.getRowCount() - 1) {
                model.focus = model.apps.size() - 1;
                updateScroll();
            }
        }
    }
    class FocusLeft extends Action {
        @Override
        void perform(Model model) {
            if(model.focus > 0) {
                model.focus--;
                updateScroll();
            }
        }
    }
    class FocusRight extends Action {
        @Override
        void perform(Model model) {
            if(model.focus < model.apps.size() - 1) { // TODO
                model.focus++;
                updateScroll();
            }
        }
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

        // Log.d("ken", String.format("focus: %d, # apps: %d", model.focus, model.apps.size()));

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
                update(new FocusUp());
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                update(new FocusDown());
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                update(new FocusLeft());
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                update(new FocusRight());
                break;
            case KeyEvent.KEYCODE_ENTER:
                update(new OpenApp(model.apps.get(model.focus)));
                break;
        }

        return true;
    }

    private class Renderer extends RenderableView {

        private final Drawable shortcutUnfocused = UI.createRectangle(UI.getColor(UI.primary_gray, 0.7f), 0, 0, Util.dp(HomeMenu.this, 10));
        private final Drawable shortcutFocused = UI.createRectangle(UI.primary_gray, Util.dp(HomeMenu.this, 1), UI.secondary_yellow, Util.dp(HomeMenu.this, 10));

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
                        renderApps();
                        break;
                    case InputSource:
                        renderInputSources();
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

        private void renderInputSources() {

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
                            dip(112), dip(128), getResources().getDrawable(icons[index]), 0.7f, labels[index], 24,
                            shortcutUnfocused);
                }, (Integer index) -> {
                    space(() -> {
                        size(dip(112), dip(128));
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

        private void renderApps() {

            if(model.apps.isEmpty()) {
                textView(() -> {
                    size(MATCH, MATCH);

                    textSize(sip(48));
                    gravity(Gravity.CENTER);
                    text("Empty");
                });
            } else {
                TimingLogger logger = new TimingLogger("ken", "start");
                frameLayout(() -> {
                    size(MATCH, MATCH);
                    margin(dip(150), 0, dip(150), 0);

                    UI.layoutTiles(MATCH, MATCH, model.numCol, model.numCol * model.rowShown, -1, dip(20),
                            (index) -> {
                                final int indexInList = model.startRow * model.numCol + index;
                                if(indexInList < model.apps.size()) {
                                    renderAppIcon(indexInList, model.apps.get(indexInList));
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
                logger.dumpToLog();
            }

        }

        private void renderAppIcon(int index, ApplicationInfo item) {
            frameLayout(() -> {
                size(dip(140), dip(160));

                linearLayout(() -> {

                    orientation(LinearLayout.VERTICAL);
                    gravity(Gravity.CENTER);
                    layoutGravity(Gravity.CENTER);
//                focusable(true);
//                focusableInTouchMode(true);
//                clickable(true);

                    BaseDSL.init(() -> {
                        if(index==0) {
                            requestFocus();
                        }
                    });
                    // weight(1);


                    final CharSequence label = item.loadLabel(pm);

                    // Log.d("ken", String.format("focus %d - %d", model.focus, index));
                    if(model.focus == index) {
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

                        imageDrawable(item.loadIcon(pm));
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
                        if(model.focus == index) {
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
