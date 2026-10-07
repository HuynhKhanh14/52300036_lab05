package com.example.a52300036_lab05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class Bai01Adapter extends BaseAdapter {
    private Context ctx;
    private List<Phone> list;

    public Bai01Adapter(Context ctx, List<Phone> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override public int getCount() { return list.size(); }
    @Override public Object getItem(int i) { return list.get(i); }
    @Override public long getItemId(int i) { return i; }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.item_phone, parent, false);
        }
        Phone p = list.get(pos);
        TextView tv = convertView.findViewById(R.id.tvName);
        CheckBox cb = convertView.findViewById(R.id.cbCheck);

        tv.setText(p.name);
        cb.setChecked(p.checked);

        // Click cả dòng để toggle checkbox
        convertView.setOnClickListener(v -> {
            p.checked = !p.checked;
            cb.setChecked(p.checked);
        });

        return convertView;
    }
}