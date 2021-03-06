package com.optoma.launcher.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.optoma.launcher.R;
import com.optoma.launcher.ui.ButtonController;
import com.optoma.launcher.ui.MenuGroupWithToggleController;
import com.optoma.launcher.ui.PickerController;
import com.optoma.launcher.ui.ToggleController;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Network extends Activity {

    @BindView(R.id.network_content) LinearLayout content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.network);
        ButterKnife.bind(this);

        final View lan = getLanMenu();
        content.addView(lan);

        final View wifi = getWifiMenu();
        content.addView(wifi);

    }

    private View getWifiMenu() {
        final ButtonController ssid = new ButtonController(this, "SSID", "Optoma-Lab");

        final PickerController mode = new PickerController(this, "Connection Mode", new String[] {
                "Infrastructure",
                "Ad-hoc"
        }, 0);

        final MenuGroupWithToggleController wifiMenu = new MenuGroupWithToggleController(
                this, "Wi-Fi", false
        );
        wifiMenu.addItem(ssid);
        wifiMenu.addItem(mode);

        return wifiMenu.getView();
    }

//    private View getSsid() {
//
//
//        return ssid;
//    }
//
//
    private View getLanMenu() {
        final MenuGroupWithToggleController lanMenu = new MenuGroupWithToggleController(
                this, "Lan", false
        );

        final ToggleController dhcp = new ToggleController(this, "DHCP", false);
        lanMenu.addItem(dhcp);

        final ButtonController ipAddress = new ButtonController(
                this, "IP Address", "192.168.0.2");
        lanMenu.addItem(ipAddress);

        final ButtonController subnetMask = new ButtonController(
                this, "Subnet Mask", "255.255.255.0");
        lanMenu.addItem(subnetMask);

        final ButtonController gateway = new ButtonController(
                this, "Gateway", "192.168.0.254");
        lanMenu.addItem(gateway);

        final ButtonController dns = new ButtonController(
                this, "DNS", "192.168.0.1");
        lanMenu.addItem(dns);

        final ButtonController reset = new ButtonController(this, "Reset");
        lanMenu.addItem(reset);

        return lanMenu.getView();
    }

}