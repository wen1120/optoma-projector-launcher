package com.optoma.launcher.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.annotation.DrawableRes
import android.view.Gravity
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.SeekBar

import com.optoma.launcher.Action
import com.optoma.launcher.ActionReceiver
import com.optoma.launcher.R

import trikita.anvil.Anvil

import trikita.anvil.DSL.*

val primary_red = Color.parseColor("#cf0a2c")
val primary_black = Color.parseColor("#2e2a25")
val primary_black_transparent = Color.parseColor("#992e2a25")
val primary_gray = Color.parseColor("#5b646f")
val primary_white = Color.parseColor("#ffffff")
val secondary_dark_blue = Color.parseColor("#004b87")
val secondary_blue = Color.parseColor("#009cde")
val secondary_light_blue = Color.parseColor("#71c5e8")
val secondary_cyan = Color.parseColor("#00c7b1")
val secondary_yellow = Color.parseColor("#eada24")
val secondary_gray = Color.parseColor("#8a8d8f")
val secondary_gray_80 = Color.parseColor("#808a8d8f")
val secondary_light_gray = Color.parseColor("#d0d3d4")
val secondary_light_brown = Color.parseColor("#9a887b")

// layouts

fun layoutTiles(width: Int, height: Int,
                col: Int, len: Int, colSpacing: Int, rowSpacing: Int,
                createTile: (Int) -> Unit,
                createDummyTile: (Int) -> Unit,
                r: (() -> Unit)?) {
    linearLayout {
        size(width, height)
        // backgroundColor(Color.RED);
        orientation(LinearLayout.VERTICAL)

        val row = len / col + if (len % col > 0) 1 else 0
        for (i in 0..row - 1) {

            val rowIndex = i
            val rowTiles = {
                size(if (colSpacing < 0) MATCH else WRAP, WRAP)

                for (j in 0..col - 1) {

                    val index = rowIndex * col + j
                    if (index < len) {
                        createTile(index)
                    } else {
                        // dummy tile
                        createDummyTile(index)
                    }

                    // col spacing
                    if (colSpacing != 0 && j < col - 1) {
                        space {
                            if (colSpacing < 0) {
                                weight(1f)
                            } else {
                                size(colSpacing, 0)
                            }
                        }
                    }
                }
            }

            if (col == 1) {
                rowTiles()
            } else {
                linearLayout(rowTiles)
            }

            // row spacing
            if (rowSpacing != 0 && i < row - 1) {
                space {
                    if (rowSpacing < 0) {
                        weight(1f)
                    } else {
                        size(0, rowSpacing)
                    }
                }

            }
        }

        if (r != null) r()
    }

}

fun createIconLabelTile(width: Int, height: Int,
                        @DrawableRes icon: Int,
                        iconResizeFactor: Float,
                        label: String,
                        fontSize: Int,
                        background: Drawable) {
    linearLayout {
        size(width, height)
        orientation(LinearLayout.VERTICAL)
        gravity(Gravity.CENTER)
        // backgroundColor(Color.parseColor("#2e2a25"));
        background(background)
        focusable(true)
        focusableInTouchMode(true)
        clickable(true)

        imageView {
            size(dip(Math.round(86 * iconResizeFactor)), dip(Math.round(76 * iconResizeFactor)))
            weight(11f)
            imageResource(icon)
        }

        textView {
            size(WRAP, WRAP)
            textSize(fontSize.toFloat())
            weight(5f)
            gravity(Gravity.CENTER)
            text(label)
        }

    }
}

fun createIconTile(width: Int, height: Int, @DrawableRes icon: Int) {
    imageView {
        size(width, height)
        imageResource(icon)
        focusable(true)
        focusableInTouchMode(true)
        clickable(true)
        // backgroundColor(Color.BLUE);
    }
}

fun createLabelLabelTile(label1: String, label2: String) {
    xml(R.layout.initial_setup_language_tile, {
        // TODO: remove xml
        withId(R.id.language_name_eng, { text(label1) })

        withId(R.id.language_name, { text(label2) })
    })
}

class ToggleAction : Action
class SeekBarChangeAction(val diff: Int) : Action

fun createToggle(label: String, isOn: Boolean, receiver: ActionReceiver) {
    xml(R.layout.menu_toggle, {
        withId(R.id.title, { text(label) })

        withId(R.id.toggle, {
            checked(isOn)

            onCheckedChange { view: CompoundButton, isChecked: Boolean -> receiver.receive(ToggleAction()) }
        })
    })
}

fun createSeekBar(title: String, value: Int, min: Int, max: Int, step: Int,
                  receiver: ActionReceiver) {
    xml(R.layout.menu_seekbar, {

        withId(R.id.seekbar) {
            max(max - min)
            progress(value - min)
        }

        withId(R.id.title, { text(title) })

        withId(R.id.value, { text(value.toString()) })

        withId(R.id.left_arrow) {
            clickable(true)
            onClick { v -> receiver.receive(SeekBarChangeAction(-step)) }
        }

        withId(R.id.right_arrow) {
            clickable(true)
            onClick { v -> receiver.receive(SeekBarChangeAction(step)) }
        }

    })
}

fun createHorizontalBar(width: Int, color: Int) {
    v(View::class.java, {
        size(width, dip(1))
        backgroundColor(color)
    })
}

// drawables

fun createRectangle(bgColor: Int, borderWidth: Int, borderColor: Int, cornerRadius: Int): GradientDrawable {
    return createRectangle(
            bgColor, borderWidth, borderColor, cornerRadius, cornerRadius, cornerRadius, cornerRadius)
}

fun createRectangle(
        bgColor: Int, borderWidth: Int, borderColor: Int, upperLeft: Int,
        upperRight: Int, lowerRight: Int, lowerLeft: Int): GradientDrawable {

    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.cornerRadii = floatArrayOf(upperLeft.toFloat(), upperLeft.toFloat(), upperRight.toFloat(), upperRight.toFloat(), lowerRight.toFloat(), lowerRight.toFloat(), lowerLeft.toFloat(), lowerLeft.toFloat())
    shape.setColor(bgColor)
    shape.setStroke(borderWidth, borderColor)
    return shape
}

// colors

// modified from https://stackoverflow.com/questions/15319635/manipulate-alpha-bytes-of-java-android-color-int
fun getColor(color: Int, alphaFactor: Float): Int {
    val alpha = Math.round(Color.alpha(color) * alphaFactor)
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)
    return Color.argb(alpha, red, green, blue)
}
