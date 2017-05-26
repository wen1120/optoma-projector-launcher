package com.optoma.launcher

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.widget.LinearLayout

import trikita.anvil.Anvil
import trikita.anvil.RenderableView
import trikita.anvil.DSL.*
import com.optoma.launcher.ui.*

data class Model (
    var root : Int = 0,
    var children: IntArray = IntArray(2)
)

class Home2 : Activity() {
    private var model = Model()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(object : RenderableView(this) {
            override fun view() {

                linearLayout {
                    orientation(LinearLayout.VERTICAL)
                    gravity(Gravity.CENTER)

                    createTitleBar()

                    space {
                        size(MATCH, 0)
                        weight(1f)
                    }

                    createButtons()

                    space {
                        size(0, dip(8))
                    }

                    createHorizontalBar(dip(254), primary_white);

                    space {
                        size(0, dip(24))
                    }

                    createShortcuts()
                }

            }
        })
    }

    private val unfocusedIcons = intArrayOf(
            R.drawable.uf_position,
            R.drawable.uf_apps,
            R.drawable.uf_inputsource,
            R.drawable.uff_language
    )

    private val focusedIcons = intArrayOf(
            R.drawable.ff_position,
            R.drawable.f_apps,
            R.drawable.f_inputsource,
            R.drawable.f_language
    )

    private fun createButtons() {
        layoutTiles(WRAP, WRAP, 4, 4, dip(14), 0, { index ->
            val focused = model.root == 0 && model.children[model.root] == index

            imageView {
                imageResource(if(focused) focusedIcons[index] else unfocusedIcons[index])
            }
        }, {}, {})
    }

    private fun createTitleBar() {
        linearLayout {
            orientation(LinearLayout.HORIZONTAL)
            backgroundColor(Color.GREEN)
            size(MATCH, WRAP)
            margin(dip(48), dip(18), dip(48), 0)

            createLogo()

            space { weight(1f) }

            linearLayout {
                orientation(LinearLayout.VERTICAL)
                gravity(Gravity.CENTER)
                backgroundColor(Color.RED)
                size(WRAP, WRAP)

                createTime()

                // TODO: fix width
                createHorizontalBar(dip(110), primary_white)

                createDate()

            }
        }
    }

    private val shortcutUnfocused = createRectangle(getColor(primary_black, 0.7f), 0, 0, 10)
    private val shortcutFocused = createRectangle(primary_black, 1, secondary_yellow, 10)

    private fun createShortcuts() {
        frameLayout {
            size(MATCH, dip(200))
            backgroundResource(R.drawable.backgroundhome)
            gravity(Gravity.CENTER)

            val labelSizeUnfocused = 16
            val labelSizeFocused = 20

            layoutTiles(MATCH, MATCH, 8, 8, -1, 0, { index ->
                val focused = model.root == 1 && model.children[model.root] == index
                createShortcut(index, focused)
            }, { index ->
                // no-op
            }) {
                backgroundColor(Color.BLUE)
                margin(dip(66), dip(20), dip(66), dip(20))
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // return super.onKeyDown(keyCode, event);
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> model.root = if (model.root == 1) 0 else 1
            KeyEvent.KEYCODE_DPAD_DOWN -> model.root = if (model.root == 0) 1 else 0
            KeyEvent.KEYCODE_DPAD_LEFT -> if (model.children[model.root] > 0)
                model.children[model.root] -= 1
            KeyEvent.KEYCODE_DPAD_RIGHT -> if (model.children[model.root] < 7)
                model.children[model.root] += 1
        }

        Anvil.render()
        Log.d("ken", model.toString())
        return true
    }

    private fun createLogo() {
        imageView {
            size(WRAP, dip(39))
            margin(0, dip(9), 0, 0)
            imageResource(R.drawable.optomalogo)
            backgroundColor(Color.BLUE)
        }
    }

    private fun createTime() {
        textClock {
            size(WRAP, dip(48))
            format12Hour("HH:mm")
            textSize(42f)
            backgroundColor(Color.BLUE)
            textColor(secondary_light_gray)
            gravity(Gravity.CENTER)
        }
    }

    private fun createDate() {
        textClock {
            format12Hour("EE d MMM")
            textSize(20f)
            backgroundColor(Color.BLUE)
            textColor(primary_white)
            gravity(Gravity.CENTER)
        }
    }

    private fun createShortcut(index: Int, focused: Boolean) {
        linearLayout {
            size(dip(140), dip(160))
            gravity(Gravity.CENTER)
            backgroundColor(Color.GREEN)

            if(focused) {
                createIconLabelTile(dip(140), dip(160),
                        R.drawable.hdmi, 1f, "HDMI", 20, shortcutFocused)
            } else {
                createIconLabelTile(dip(112), dip(128),
                        R.drawable.hdmi, 0.7f, "HDMI", 16, shortcutUnfocused)
            }

        }

    }

}
