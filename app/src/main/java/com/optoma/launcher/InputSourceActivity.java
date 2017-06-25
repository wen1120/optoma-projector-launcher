package com.optoma.launcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class InputSourceActivity extends Activity {
    private static final String TAG = "launcherLog";
    private static final int ssCount = 3;
    private Button bSetShortcut;
    private String[] sslist = {" ", " ", " "};
    private ArrayAdapter<String> adapter;
    private boolean bFocus = false;
    private int iPosition = 0;
    private int[] ssIndex = {0,1,2};
    private ArrayList<String> ssArrayList = new ArrayList<String>();
    private TextView[] ssTextView  = new TextView[ssCount];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputsource);
        //bSetShortcut = (Button) findViewById(R.id.is_set_shortcut_button);
        bSetShortcut = (Button) findViewById(R.id.is_set_shortcut_button);
        bSetShortcut.setOnClickListener(bSSOnClick);
        for (String s : getResources().getStringArray(
                R.array.ssArray)) {
            ssArrayList.add(s);
        }
    }

    private View.OnClickListener bSSOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Button Set Shortcut clicked");
            iPosition = 0;
            final View convertView = LayoutInflater.from(InputSourceActivity.this).inflate(R.layout.setshort_layout, null);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(InputSourceActivity.this, R.style.CustomAlertDialog);
            alertDialog.setView(convertView);
            ListView lv = (ListView) convertView.findViewById(R.id.ss_list_view);
            adapter = new ArrayAdapter<String>(InputSourceActivity.this ,R.layout.is_listview, sslist);
            lv.setAdapter(adapter);
            ssTextView[0] = (TextView) convertView.findViewById(R.id.ss_source01);
            ssTextView[1] = (TextView) convertView.findViewById(R.id.ss_source02);
            ssTextView[2] = (TextView) convertView.findViewById(R.id.ss_source03);
            ssTextView[0].setText(ssArrayList.get(ssIndex[0]));
            ssTextView[1].setText(ssArrayList.get(ssIndex[1]));
            ssTextView[2].setText(ssArrayList.get(ssIndex[2]));
            alertDialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (event.getAction()!=KeyEvent.ACTION_DOWN)
                        return true;
                    if (true == bFocus) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_BACK:
                                dialog.dismiss();
                                break;
                            case KeyEvent.KEYCODE_DPAD_UP:
                                if(iPosition > 0)iPosition--;
                                Log.d(TAG, "onKey:UP: iPosition =" + iPosition + ",bFocus = " +bFocus);
                                break;
                            case KeyEvent.KEYCODE_DPAD_DOWN:
                                if(iPosition < ssCount - 1)iPosition++;
                                Log.d(TAG, "onKey:Down: iPosition =" + iPosition + ",bFocus = " +bFocus);
                                break;
                            case KeyEvent.KEYCODE_DPAD_LEFT:
                                if(ssIndex[iPosition] > 0) {
                                    ssIndex[iPosition]--;
                                    ssTextView[iPosition].setText(ssArrayList.get(ssIndex[iPosition]));
                                }
                                break;
                            case KeyEvent.KEYCODE_DPAD_RIGHT:
                                if(ssIndex[iPosition] < 8) {
                                    ssIndex[iPosition]++;
                                    ssTextView[iPosition].setText(ssArrayList.get(ssIndex[iPosition]));
                                }
                                break;
                        }
                    }
                    //victor Log.d(TAG, "onKey:keyCode = " + keyCode);
                    return false;
                }
            });

            alertDialog.show();
            lv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Log.d(TAG, "Focus change: hasFocus = " + hasFocus);
                    bFocus = hasFocus;
                    if(hasFocus == false) iPosition=0;
                }
            });

        }
    };

}
