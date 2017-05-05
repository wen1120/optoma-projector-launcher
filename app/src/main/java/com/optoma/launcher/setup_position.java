package com.optoma.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by linweiting on 2017/4/20.
 */

public class setup_position extends Activity {
    private static final int[] positionIds = new int[] {
            R.id.front,
            R.id.rear,
            R.id.front_celling,
            R.id.rear_ceiling
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_position);

        findViewById(R.id.position).requestFocus();

        for(int i=0; i<positionIds.length; i++) {
            final View v = findViewById(positionIds[i]);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(setup_position.this, home.class);
                    startActivity(intent);
                }
            });
        }
    }
}
