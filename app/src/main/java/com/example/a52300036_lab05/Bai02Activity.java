package com.example.a52300036_lab05;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.List;

public class Bai02Activity extends AppCompatActivity {

    private ListView lv;
    private EventAdapter adapter;
    private List<Event> data = new ArrayList<>();
    private boolean showAll = true;
    private int editPosition = -1;

    private static final int REQ_ADD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai02);
        setTitle("Bai 02");

        data.add(new Event("Sinh hoat chu nhiem", "C120",
                "09/03/2020", "04:43", true));
        data.add(new Event("Huong dan luan van", "C120",
                "09/03/2020", "04:43", true));

        lv = findViewById(R.id.lvEvent);
        adapter = new EventAdapter(this, data);
        lv.setAdapter(adapter);

        // Đăng ký context menu cho ListView (long click)
        registerForContextMenu(lv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bai02, menu);

        MenuItem swItem = menu.findItem(R.id.action_switch);
        SwitchCompat sw = (SwitchCompat) swItem.getActionView();
        sw.setChecked(true);
        sw.setOnCheckedChangeListener((btn, checked) -> {
            showAll = checked;
            refreshList();
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            editPosition = -1;
            startActivityForResult(new Intent(this, AddEventActivity.class), REQ_ADD);
            return true;

        } else if (id == R.id.action_remove_all) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure to remove all events?")
                    .setPositiveButton("YES", (d, w) -> {
                        data.clear();
                        refreshList();
                    })
                    .setNegativeButton("NO", null)
                    .show();
            return true;

        } else if (id == R.id.action_about) {
            Toast.makeText(this, "Lab05 - Bài 02", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ----- Context menu (long click) -----
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_event, menu);
        menu.setHeaderTitle("Tùy chọn");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = info.position;

        if (item.getItemId() == R.id.ctx_delete) {
            // Xoá đúng event hiển thị
            Event e = (Event) adapter.getItem(pos);
            data.remove(e);
            refreshList();
            return true;

        } else if (item.getItemId() == R.id.ctx_edit) {
            Event e = (Event) adapter.getItem(pos);
            editPosition = data.indexOf(e);
            Intent i = new Intent(this, AddEventActivity.class);
            i.putExtra("name", e.name);
            i.putExtra("place", e.place);
            i.putExtra("date", e.date);
            i.putExtra("time", e.time);
            startActivityForResult(i, REQ_ADD);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int req, int res, Intent d) {
        super.onActivityResult(req, res, d);
        if (req == REQ_ADD && res == RESULT_OK && d != null) {
            Event e = new Event(
                    d.getStringExtra("name"),
                    d.getStringExtra("place"),
                    d.getStringExtra("date"),
                    d.getStringExtra("time"),
                    true
            );
            if (editPosition >= 0 && editPosition < data.size()) {
                data.set(editPosition, e);
            } else {
                data.add(e);
            }
            editPosition = -1;
            refreshList();
        }
    }

    private void refreshList() {
        List<Event> shown;
        if (showAll) {
            shown = data;
        } else {
            shown = new ArrayList<>();
            for (Event e : data) if (e.enabled) shown.add(e);
        }
        adapter = new EventAdapter(this, shown);
        lv.setAdapter(adapter);
    }
}