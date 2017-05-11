package com.optoma.launcher.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.optoma.launcher.R;
import com.optoma.launcher.ui.OMenuItem;
import com.optoma.launcher.ui.OMenu;
import com.optoma.launcher.ui.OMenuItemWithText;
import com.optoma.launcher.ui.OMenuItemWithToggle;
import com.optoma.launcher.ui.OPicker;
import com.optoma.launcher.ui.OUtil;


public class Network extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.network);

        final LinearLayout content = (LinearLayout) findViewById(R.id.network_content);

        final OMenu wifi = getWifiMenu();
        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO: enable/disable wifi
            }
        });
        content.addView(wifi);

        final OMenu lan = getLanMenu();
        lan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO: enable/disable lan
            }
        });
        content.addView(lan);

    }

    private OMenu getWifiMenu() {
        final OMenuItem ssid = getSsid();

        final OPicker mode = getMode();

        final OMenu wifiMenu = new OMenu(this);
        wifiMenu.setTitle("Wi-Fi");
        wifiMenu.addItem(ssid);
        wifiMenu.addItem(mode);

        final LinearLayout.LayoutParams menuLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        wifiMenu.setLayoutParams(menuLayoutParams);

        return wifiMenu;
    }

    private OMenuItem getSsid() {
        final OMenuItemWithText ssid = new OMenuItemWithText(this);
        ssid.setText("SSID");
        ssid.setValue("Optoma-Lab"); // TODO
        ssid.setIndent(24);
        ssid.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, OUtil.dpToPixel(this, 48)));
        return ssid;
    }

    private OPicker getMode() {
        final OPicker picker = new OPicker(this);
        picker.addOption("Infrastructure");
        picker.addOption("Ad-hoc");
        picker.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, OUtil.dpToPixel(this, 48)));
        return picker;
    }

    private OMenu getLanMenu() {
        final OMenu lan = new OMenu(this);
        lan.setTitle("LAN");
        lan.addItem(getDhcp());
        lan.addItem(getIpAddress());
        lan.addItem(getSubnetMask());
        lan.addItem(getGateway());
        lan.addItem(getDns());

        final LinearLayout.LayoutParams menuLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        return lan;
    }

    private OMenuItemWithToggle getDhcp() {
        final OMenuItemWithToggle dhcp = new OMenuItemWithToggle(this);
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, OUtil.dpToPixel(this, 48));
        dhcp.setLayoutParams(layoutParams);
        dhcp.setIndent(24);
        dhcp.setText("DHCP");
        return dhcp;
    }

    private OMenuItemWithText getIpAddress() {
        final OMenuItemWithText m = new OMenuItemWithText(this);
        m.setText("IP Address");
        m.setValue("192.168.0.2"); //: TODO
        m.setIndent(24);

        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, OUtil.dpToPixel(this, 48));
        m.setLayoutParams(layoutParams);
        return m;
    }

    private OMenuItemWithText getSubnetMask() {
        final OMenuItemWithText m = new OMenuItemWithText(this);
        m.setText("Subnet Mask");
        m.setValue("255.255.255.0"); //: TODO
        m.setIndent(24);
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, OUtil.dpToPixel(this, 48));
        m.setLayoutParams(layoutParams);
        return m;
    }

    private OMenuItemWithText getGateway() {
        final OMenuItemWithText m = new OMenuItemWithText(this);
        m.setText("Gateway");
        m.setValue("192.168.0.254"); //: TODO
        m.setIndent(24);

        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, OUtil.dpToPixel(this, 48));
        m.setLayoutParams(layoutParams);
        return m;
    }

    private OMenuItemWithText getDns() {
        final OMenuItemWithText m = new OMenuItemWithText(this);
        m.setText("DNS");
        m.setValue("192.168.0.1"); //: TODO
        m.setIndent(24);

        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, OUtil.dpToPixel(this, 48));
        m.setLayoutParams(layoutParams);
        return m;
    }
}