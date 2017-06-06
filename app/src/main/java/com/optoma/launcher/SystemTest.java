package com.optoma.launcher;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import java.util.Calendar;
import java.util.TimeZone;
import android.os.Bundle;
import android.util.Log;

public class SystemTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_test);

        testTimeAndTimeZone();

    }

    private void testTimeAndTimeZone() {
        final String[] ids = TimeZone.getAvailableIDs();
        for(int i=0; i<ids.length; i++) {
            setTimeZone("Asia/Taipei");
            setTime(2017, 6, 5, 12, 0, 0, "Asia/Taipei");
            setTimeZone(ids[i]);
            final Calendar c =
                    Calendar.getInstance(TimeZone.getTimeZone(ids[i]));
            // Log.d("ken", String.format("%s, %s", ids[i], c.getTime()));
            Log.d("ken", String.format("%s", c.getTime()));
        }
    }

    public void setTime(int year,
                               int month,
                               int date,
                               int hourOfDay,
                               int minute,
                               int second, String timeZoneId) {
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone(timeZoneId));
        c.set(year, month - 1, date, hourOfDay, minute, second);
        AlarmManager am =
                (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        am.setTime(c.getTimeInMillis());
    }

    public void setTimeZone(String timeZone) {
        AlarmManager am =
                (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        am.setTimeZone(timeZone);
    }

}
