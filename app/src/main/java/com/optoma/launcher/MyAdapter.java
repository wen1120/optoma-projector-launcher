package com.optoma.launcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<GridShortcut> {

    ArrayList<GridShortcut> settingsItem = new ArrayList<>();

    public MyAdapter(Context context, int textViewResourceId, ArrayList<GridShortcut> objects) {
        super(context, textViewResourceId, objects);
        settingsItem = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.shortcut, null);

        v.setBackgroundResource(R.drawable.setup_shortcut_bg);
        v.setScaleX(0.8f);
        v.setScaleY(0.8f);
        v.setAlpha(0.7f);

        v.setOnFocusChangeListener(new SizeChanger(1, 0.8f, 80){
        @Override
            public void onFocusChange(View v, boolean hasFocus){
            super.onFocusChange(v,hasFocus);
            v.setAlpha(hasFocus ? 1.0f : 0.7f);
            }}
        );

        TextView textView = (TextView) v.findViewById(R.id.label);
        ImageView imageView = (ImageView) v.findViewById(R.id.image);
        textView.setText(settingsItem.get(position).getItemName());
        imageView.setImageResource(settingsItem.get(position).getItemImage());

        return v;

    }

}