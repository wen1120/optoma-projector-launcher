package com.optoma.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.GridView;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.optoma.launcher.ui.ButtonController;
import com.optoma.launcher.ui.MenuController;
import com.optoma.launcher.ui.MenuGroupController;
import com.optoma.launcher.ui.MenuGroupWithToggleController;
import com.optoma.launcher.ui.PickerController;
import com.optoma.launcher.ui.SeekBarController;
import com.optoma.launcher.ui.ShortcutController;
import com.optoma.launcher.ui.ToggleController;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;

public class SettingsHome extends Activity {

    GridView settingsList;
    ArrayList<GridShortcut> settingsItem=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ButterKnife.bind(this);

        settingsList = (GridView) findViewById(R.id.gridview);
        settingsItem.add(new GridShortcut(R.drawable.power,"Power"));
        settingsItem.add(new GridShortcut(R.drawable.account,"Account"));
        settingsItem.add(new GridShortcut(R.drawable.general,"General"));
        settingsItem.add(new GridShortcut(R.drawable.network,"Network"));
        settingsItem.add(new GridShortcut(R.drawable.information,"Information"));
        settingsItem.add(new GridShortcut(R.drawable.bluetooth,"Bluetooth"));
        settingsItem.add(new GridShortcut(R.drawable.system,"System"));
        settingsItem.add(new GridShortcut(R.drawable.audio,"Audio"));
        settingsItem.add(new GridShortcut(R.drawable.control,"Control"));

        MyAdapter myAdapter=new MyAdapter(this,R.layout.shortcut,settingsItem);
        settingsList.setAdapter(myAdapter);
    }

}
