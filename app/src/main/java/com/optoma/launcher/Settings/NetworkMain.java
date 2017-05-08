package com.optoma.launcher.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.optoma.launcher.R;


public class NetworkMain extends Activity {
    private static final String TAG = "LauncherLog";
    private int xPosition, yPosition, iConnectionMode;
    private final int yLimit = 10;
    private boolean[] bNetworkItems = {false, true, true};
    private ImageView[] ivNetworkOnOff = new ImageView[2];
    private TextView[] tvNetwork = new TextView[5];
    private TextView tvConnectionMode;

    private static int[] NetworkOnOffID = {
            R.id.network_wifi_iv,
            R.id.network_lan_iv,
            R.id.network_dhcp_iv
    };
    private static int[] NetworkTVID = {
            R.id.network_ssid_value_tv,
            R.id.network_ip_address_value_tv,
            R.id.network_subnet_mask_value_tv,
            R.id.network_gateway_value_tv,
            R.id.network_dns_value_tv
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network);

        tvConnectionMode = (TextView) this.findViewById(R.id.network_connection_mode_content_tv);
        for(int i=0;i< tvNetwork.length; i++) {
            tvNetwork[i] = (TextView) this.findViewById(NetworkTVID[i]);
            if(i<ivNetworkOnOff.length)
                ivNetworkOnOff[i] = (ImageView) this.findViewById(NetworkOnOffID[i]);
        }
        xPosition = yPosition = iConnectionMode = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int ImageSource = 0;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                switch (yPosition) {

                    default:
                        if(yPosition > 0) {
                            yPosition--;
                        }
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                switch (yPosition) {
                    default:
                        if (yPosition < yLimit - 1) {
                            yPosition++;
                        }
                        break;
                }
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}