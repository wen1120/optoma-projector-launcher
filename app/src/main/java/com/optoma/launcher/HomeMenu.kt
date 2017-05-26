package com.optoma.launcher

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.view.Gravity
import android.widget.LinearLayout

import com.optoma.launcher.Projector
import com.optoma.launcher.R
import com.optoma.launcher.ui.*

import java.util.function.Consumer

import trikita.anvil.RenderableView

import trikita.anvil.DSL.*
import trikita.anvil.DSL.view

class HomeMenu : Activity() {

    enum class Page {
        Position,
        Apps,
        InputSource,
        Language
    }

    private class Model {
        internal var page = Page.Language
    }

    private val model = Model()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        model.page = intent.extras.get("page") as Page

        setContentView(object : RenderableView(this) {
            override fun view() {
                linearLayout {
                    size(MATCH, MATCH)
                    orientation(LinearLayout.VERTICAL)
                    gravity(Gravity.CENTER)

                    backgroundResource(R.drawable.backgroundhome)

                    createMenu()

                    space { size(0, dip(8)) }

                    createHorizontalBar(dip(254), resources.getColor(R.color.primary_white))

                    when (model.page) {
                        HomeMenu.Page.Position -> createPositions()
                        HomeMenu.Page.Apps -> space { size(MATCH, MATCH) }
                        HomeMenu.Page.InputSource -> createInputSources()
                        HomeMenu.Page.Language -> createLanguages()
                        else -> throw RuntimeException()
                    }

                }

            }

            private fun createMenu() {
                linearLayout {

                    size(MATCH, WRAP)

                    margin(0, dip(20), 0, 0)

                    // backgroundColor(Color.BLUE);

                    gravity(Gravity.CENTER)

                    imageView {
                        val selected = model.page == Page.Position
                        imageResource(if (selected) R.drawable.ff_position else R.drawable.uf_position)
                        selected(selected)

                        onClick { v -> model.page = Page.Position }
                    }

                    space { size(dip(14), 0) }

                    imageView {
                        val selected = model.page == Page.Apps
                        imageResource(if (selected) R.drawable.f_apps else R.drawable.uf_apps)
                        selected(selected)
                        onClick { v -> model.page = Page.Apps }
                    }

                    space { size(dip(14), 0) }

                    imageView {
                        val selected = model.page == Page.InputSource
                        imageResource(if (selected) R.drawable.f_inputsource else R.drawable.uf_inputsource)
                        selected(selected)
                        onClick { v -> model.page = Page.InputSource }
                    }

                    space { size(dip(14), 0) }

                    imageView {
                        val selected = model.page == Page.Language
                        imageResource(if (selected) R.drawable.f_language else R.drawable.uff_language)
                        selected(selected)
                        onClick { v -> model.page = Page.Language }
                    }
                }
            }

            private fun createPositions() {
                @DrawableRes val icons = intArrayOf(R.drawable.initial_setup_position_front, R.drawable.initial_setup_position_rear, R.drawable.initial_setup_position_front_ceiling, R.drawable.initial_setup_position_rear_ceiling)


                linearLayout {
                    size(MATCH, MATCH)
                    gravity(Gravity.CENTER)
                    orientation(LinearLayout.VERTICAL)
                    // backgroundColor(Color.GREEN);
                    margin(dip(150), dip(52), dip(150), dip(100))

                    layoutTiles(WRAP, MATCH, 2, icons.size, dip(38), dip(66),
                            { index ->
                                createIconTile(dip(150), dip(150), icons[index]);
                            },
                            { index ->
                                space {
                                    size(dip(150), dip(150));
                                };
                            },
                            null);
                }


            }

            private fun createInputSources() {

                @DrawableRes val icons = intArrayOf(R.drawable.miracast, R.drawable.vga, R.drawable.hdmi, R.drawable.hdmi, R.drawable.hdmi, R.drawable.displayport, R.drawable.usb, R.drawable.usb, R.drawable.s_video, R.drawable.addshortcut)

                val labels = arrayOf("Miracast", "VGA", "HDMI 1", "HDMI 2", "HDMI 3", "Display Port", "USB 1", "USB 2", "S-video", "Set Shortcut")

                frameLayout({
                    size(MATCH, MATCH);
                    margin(dip(150), dip(52), dip(150), dip(100));

                    layoutTiles(MATCH, MATCH, 6, icons.size, -1, dip(56), { index ->
                        createIconLabelTile(
                                dip(112), dip(128), icons[index], 0.7f, labels[index], 24,
                                shortcutUnfocused);
                    }, { index ->
                        space {
                            size(dip(112), dip(128));
                        }
                    }, null)
                });

            }

            private fun createLanguages() {
                scrollView {
                    size(MATCH, MATCH)
                    margin(dip(80), dip(28), dip(80), dip(80))

                    layoutTiles(MATCH, MATCH, 7, Projector.langs.size, -1, dip(32), { index ->
                        createLabelLabelTile(Projector.langsEng[index], Projector.langs[index]);
                    }, { index ->
                        // no-op
                    }, null)
                }

            }


        })
    }

    companion object {

        private val shortcutUnfocused = createRectangle(Color.BLACK, 0, 0, 10)
    }


}
