package com.optoma.launcher;

import android.app.AlarmManager;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ScrollView;

import java.util.Calendar;
import java.util.TimeZone;

public class Util {

    public static int dp(Context context, int px) {
        // from https://developer.android.com/guide/practices/screens_support.html#dips-pels
        // note that the accepted answer on stackoverflow is wrong:
        //   https://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android#
        return (int) (px * context.getResources().getDisplayMetrics().density + 0.5f);
    }


    // ugly finder
    public static <T> T findParent(View v) {
        final ViewParent p = v.getParent();
        if(p instanceof ScrollView) {
            return (T) p;
        } else if(p instanceof View) {
            return Util.<T>findParent((View) p);
        } else {
            return null;
        }
    }

    public static void connectToWifi(Context context, String ssid, String pwd) {
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiConfiguration wc = new WifiConfiguration();
        wc.SSID = String.format("\"%s\"", ssid);
        wc.preSharedKey = String.format("\"%s\"", pwd);
        wc.status = WifiConfiguration.Status.ENABLED;
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

        final int netId = wifiManager.addNetwork(wc);
        wifiManager.enableNetwork(netId, true);
        wifiManager.setWifiEnabled(true);
    }

    public static void setTime(Context context, int year,
                               int month,
                               int date,
                               int hourOfDay,
                               int minute,
                               int second, String timeZoneId) {
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone(timeZoneId));
        c.set(year, month - 1, date, hourOfDay, minute, second);
        AlarmManager am =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setTime(c.getTimeInMillis());
    }

    public static void setTimeZone(Context context, String timeZone) {
        AlarmManager am =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setTimeZone(timeZone);
    }
}
