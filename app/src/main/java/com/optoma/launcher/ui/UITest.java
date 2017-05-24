package com.optoma.launcher.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.optoma.launcher.R;
import java.util.function.Consumer;
import trikita.anvil.RenderableView;
import static trikita.anvil.DSL.*;

public class UITest extends Activity {

    final int[] colors = new int[]{
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            Color.CYAN
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(new RenderableView(this) {
            @Override
            public void view() {
                margin(0, dip(24), 0, 0);
                backgroundColor(Color.BLACK);

                testTiles();

            }
        });
    }

    private void testTiles() {
        final Consumer<Integer> createTile = (index) -> {
            linearLayout(() -> {
                size(WRAP, WRAP);
                orientation(LinearLayout.VERTICAL);
                size(dip(150), dip(100));
                backgroundColor(colors[index]);
                gravity(Gravity.CENTER);

                imageView(() -> {
                    size(WRAP, WRAP);
                    imageResource(R.drawable.hdmi);
                });

                textView(() -> {
                    size(WRAP, WRAP);
                    text(String.valueOf(index));
                });
            });

        };

        final Consumer<Integer> createDummyTile = (index) -> {
            space(() -> {
                size(150, 100);
            });
        };

        UI.layoutTiles(MATCH, MATCH, 2, 5, -1, dip(12),
                createTile, createDummyTile);
    }

}
