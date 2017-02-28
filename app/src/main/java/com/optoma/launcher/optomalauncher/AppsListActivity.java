package com.optoma.launcher.optomalauncher;

/**
 * Created by linweiting on 2017/2/26.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.content.pm.PackageManager;
import android.widget.ListView;
import java.util.List;
import java.util.ArrayList;
import android.content.pm.ResolveInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class AppsListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);
        loadApps();
        loadListView();
        addClickListener();
    }

    /**
     * Fetching applications with the category "Intent.CATEGORY_LAUNCHER".
     *
     * Using the queryIntentActivities() of PackageManager class to fetch all the Intent
     * that have a category of Intent.CATEGORY_LAUNCHER.
     * This query returns a list of the applications that can be launched by a launcher.
     * We loop through the results of the query and add each item to a list named apps.
     * @param
     * @return
     * @exception
     */
    private PackageManager manager;
    private List<AppDetail> apps;
    private void loadApps(){
        manager = getPackageManager();
        apps = new ArrayList<AppDetail>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for(ResolveInfo ri:availableActivities){
            AppDetail app = new AppDetail();
            app.label = ri.loadLabel(manager);
            app.name = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(manager);
            apps.add(app);
        }
    }

    /**
     * Displaying the list of applications of "apps".
     *
     * Using ListView class to show the list of applications in the "apps" list.
     * We create a simple ArrayAdapter and override it's getView() to render the list's items.
     * We then associate the ListView with the adapter.
     */
    private ListView list;
    private void loadListView(){
        list = (ListView)findViewById(R.id.apps_list);

        ArrayAdapter<AppDetail> adapter = new ArrayAdapter<AppDetail>(this,
                R.layout.list_item,
                apps) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.list_item, null);
                }

                ImageView appIcon = (ImageView)convertView.findViewById(R.id.item_app_icon);
                appIcon.setImageDrawable(apps.get(position).icon);

                TextView appLabel = (TextView)convertView.findViewById(R.id.item_app_label);
                appLabel.setText(apps.get(position).label);

                TextView appName = (TextView)convertView.findViewById(R.id.item_app_name);
                appName.setText(apps.get(position).name);

                return convertView;
            }
        };

        list.setAdapter(adapter);
    }

    /**
     * Listening for clicks.
     *
     * When the user clicks an item in the ListView, the corresponding application should be launched by our launcher.
     * We use getLaunchIntentForPackage() of PackageManager class to create an Intent with which we start the application.
     */
    private void addClickListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                Intent i = manager.getLaunchIntentForPackage(apps.get(pos).name.toString());
                AppsListActivity.this.startActivity(i);
            }
        });
    }
}

