package com.optoma.launcher;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.optoma.launcher.ui.UI;

import java.util.ArrayList;
import java.util.List;

import trikita.anvil.Anvil;
import trikita.anvil.BaseDSL;
import trikita.anvil.RenderableAdapter;
import trikita.anvil.RenderableView;

import static trikita.anvil.DSL.*;

public class HomeMenu extends Activity {

    // model
    private static class Model {
        // general
        Page page = Page.Apps;
        int numCol = 3;
        int focus = 0;

        // apps
        List<ApplicationInfo> packages = new ArrayList<>();
        boolean loadingApps = false;
        int startRow = 0;
        int rowShown = 2;

        public int getCurrentRow() {
            return focus / numCol;
        }

        public int getRowCount() {
            return packages.size() / numCol +
                    ((packages.size() % numCol != 0) ? 1 : 0);
        }

    }
    public enum Page {
        Position,
        Apps,
        InputSource,
        Language
    }

    private Model model = new Model();

    // messages
    static class Msg {}
    static class LoadPackage extends Msg {}
    static class PackageLoadedMsg extends Msg {
        private final List<ApplicationInfo> apps;
        public PackageLoadedMsg(List<ApplicationInfo> apps) {
            this.apps = apps;
        }
    }
    static class OpenPage extends Msg {
        private final Page page;
        public OpenPage(Page page) {
            this.page = page;
        }
    }
    static class OpenApp extends Msg {
        private final ApplicationInfo ai;
        public OpenApp(ApplicationInfo ai) {
            this.ai = ai;
        }
    }
    static class FocusUp extends Msg {}
    static class FocusDown extends Msg {}
    static class FocusLeft extends Msg {}
    static class FocusRight extends Msg {}


    private PackageManager pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        init();

        setContentView(new Renderer(this));

    }

    private void update(Msg msg) {
        if(msg instanceof LoadPackage) {
            final AsyncTask<Void, Integer, List<ApplicationInfo>> loadPackages =
                    new AsyncTask<Void, Integer, List<ApplicationInfo>>() {
                        @Override
                        protected void onPreExecute() {
                            model.loadingApps = true;
                            Log.d("ken", "start loading apps.");
                            Anvil.render();
                        }

                        @Override
                        protected List<ApplicationInfo> doInBackground(Void[] params) {
                            if(pm == null) pm = getPackageManager(); // TODO

                            return pm.getInstalledApplications(PackageManager.GET_META_DATA);

                        }

                        @Override
                        protected void onPostExecute(List<ApplicationInfo> apps) {
                            model.loadingApps = false;
                            update(new PackageLoadedMsg(apps));
                        }
                    };
            loadPackages.execute();

        } else if(msg instanceof PackageLoadedMsg) {
            final List<ApplicationInfo> apps = ((PackageLoadedMsg) msg).apps;

            model.packages.clear();
            for(ApplicationInfo ai : apps) {
                if(pm.getLaunchIntentForPackage(ai.packageName) != null) {
                    model.packages.add(ai);
                }
            }
            Log.d("ken", "done loading apps. # of packages: "+model.packages.size());

        } else if(msg instanceof OpenPage) {
            final OpenPage openPage = (OpenPage) msg;
            model.page = openPage.page;
            if(model.page == Page.Apps) {
                update(new LoadPackage());
            }

        } else if(msg instanceof OpenApp) {
            final OpenApp openApp = (OpenApp) msg;
            Toast.makeText(this, "opening "+openApp.ai.packageName + "...", Toast.LENGTH_SHORT).show();
            final Intent intent = pm.getLaunchIntentForPackage(openApp.ai.packageName);
            startActivity(intent);

        } else if(msg instanceof FocusUp) {
            if(model.focus >= model.numCol) {
                model.focus -= model.numCol;
                updateScroll();
            }
        } else if(msg instanceof FocusDown) {
            if(model.focus < model.packages.size() - model.numCol) { // TODO
                model.focus += model.numCol;
                updateScroll();
            } else if(model.getCurrentRow() < model.getRowCount() - 1) {
                model.focus = model.packages.size() - 1;
                updateScroll();
            }

        } else if(msg instanceof FocusLeft) {
            if(model.focus > 0) {
                model.focus--;
                updateScroll();
            }
        } else if(msg instanceof FocusRight) {
            if(model.focus < model.packages.size() - 1) { // TODO
                model.focus++;
                updateScroll();
            }
        } else {}

        Log.d("ken", String.format("focus: %d, # apps: %d", model.focus, model.packages.size()));
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
                update(new OpenApp(model.packages.get(model.focus)));
                break;
        }

        return true;
    }

    private class Renderer extends RenderableView {

        private final Drawable shortcutUnfocused = UI.createRectangle(UI.getColor(UI.primary_gray, 0.7f), 0, 0, 10);
        private final Drawable shortcutFocused = UI.createRectangle(UI.primary_gray, 1, UI.secondary_yellow, 10);

        public Renderer(Context context) {
            super(context);
        }

        @Override
        public void view() {
            linearLayout(() -> {
                size(MATCH, MATCH);
                orientation(LinearLayout.VERTICAL);
                // backgroundColor(Color.BLACK);
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

            if(model.loadingApps) {

                textView(() -> {
                    textSize(sip(48));
                    text("Loading...");
                });

            } else {

                if(model.packages.isEmpty()) {
                    textView(() -> {
                        textSize(sip(48));
                        text("Empty");
                    });
                } else {
                    UI.layoutTiles(MATCH, MATCH, model.numCol, model.numCol*model.rowShown, -1, dip(36),
                            (index) -> {
                                final int newIndex = model.startRow * model.numCol + index;
                                if(newIndex < model.packages.size()) {
                                    renderAppIcon(newIndex, model.packages.get(newIndex));
                                }
                            },
                            null,
                            null
                    );

//                    gridView(() -> {
//                        size(MATCH, MATCH);
//                        columnWidth(dip(112));
//                        numColumns(model.numCol);
//                        verticalSpacing(dip(36));
//                        margin(0, 0, 0, dip(200));
//
//                        // so that we can focus on our element instead of the wrapping gridview created
//                        descendantFocusability(GridView.FOCUS_AFTER_DESCENDANTS);
//
//                        stretchMode(GridView.STRETCH_SPACING_UNIFORM);
//                        // backgroundColor(Color.BLUE);
//
//                        layoutGravity(Gravity.CENTER);
//                        adapter(RenderableAdapter.withItems(model.packages, Renderer.this::renderAppIcon));
//
//                    });

                }
            }


        }

        private void renderAppIcon(int index, ApplicationInfo item) {
            linearLayout(() -> {
                size(dip(112), dip(128));
                orientation(LinearLayout.VERTICAL);
                gravity(Gravity.CENTER);
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

                Log.d("ken", String.format("focus %d - %d", model.focus, index));
                if(model.focus == index) {
                    background(shortcutFocused);
                    // backgroundColor(Color.RED);

                } else {
                    background(shortcutUnfocused);

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

                    textSize(sip(16));
                    weight(5);
                    gravity(Gravity.CENTER);

                    text(label);
                    // text(item.loadLabel(pm));
                });


    //            onFocusChange((view, hasFocus) -> {
    //                Log.d("ken", label + " has focus: "+hasFocus);
    //                view.setSelected(hasFocus);
    //            });

            });
        }
    }


}
