package com.example.a52300036_lab05;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Bai01Activity extends AppCompatActivity {

    private ListView lv;
    private Bai01Adapter adapter;
    private List<Phone> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai01);
        setTitle("Bài 01");

        data.add(new Phone("Apple", true));
        data.add(new Phone("Samsung", true));
        data.add(new Phone("Nokia", false));
        data.add(new Phone("Oppo", false));

        lv = findViewById(R.id.lvPhone);
        adapter = new Bai01Adapter(this, data);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bai01, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_check_all) {
            boolean anyUnchecked = false;
            for (Phone p : data) {
                if (!p.checked) { anyUnchecked = true; break; }
            }
            boolean target = anyUnchecked;
            for (Phone p : data) p.checked = target;
            adapter.notifyDataSetChanged();

        } else if (id == R.id.action_delete_selected) {
            confirmDialog("Xóa các mục đã chọn?", () -> {
                List<Phone> remain = new ArrayList<>();
                for (Phone p : data) if (!p.checked) remain.add(p);
                data.clear();
                data.addAll(remain);
                adapter.notifyDataSetChanged();
            });

        } else if (id == R.id.action_delete_all) {
            confirmDialog("Xóa tất cả?", () -> {
                data.clear();
                adapter.notifyDataSetChanged();
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDialog(String msg, Runnable onYes) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton("YES", (d, w) -> onYes.run())
                .setNegativeButton("NO", null)
                .show();
    }
}