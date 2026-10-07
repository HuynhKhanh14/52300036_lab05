package com.example.a52300036_lab05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends BaseAdapter {
    private Context ctx;
    private List<Event> list;

    public EventAdapter(Context ctx, List<Event> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override public int getCount() { return list.size(); }
    @Override public Object getItem(int i) { return list.get(i); }
    @Override public long getItemId(int i) { return i; }

    @Override
    public View getView(int pos, View v, ViewGroup parent) {
        if (v == null) {
            v = LayoutInflater.from(ctx).inflate(R.layout.item_event, parent, false);
        }
        Event e = list.get(pos);
        TextView tvName = v.findViewById(R.id.tvName);
        TextView tvPlace = v.findViewById(R.id.tvPlace);
        TextView tvDT = v.findViewById(R.id.tvDateTime);
        Switch sw = v.findViewById(R.id.swEnable);

        tvName.setText(e.name);
        tvPlace.setText(e.place);
        tvDT.setText(e.date + " " + e.time);

        // Reset listener trước khi setChecked để tránh trigger sai
        sw.setOnCheckedChangeListener(null);
        sw.setChecked(e.enabled);
        sw.setOnCheckedChangeListener((btn, checked) -> e.enabled = checked);

        return v;
    }
}