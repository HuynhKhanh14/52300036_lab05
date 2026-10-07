package com.example.a52300036_lab05;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    private EditText edtName, edtPlace, edtDate, edtTime;

    private final String[] places = {"C201", "C202", "C203", "C204"};

    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        setTitle("Bài 02 - Thêm sự kiện");

        edtName  = findViewById(R.id.edtName);
        edtPlace = findViewById(R.id.edtPlace);
        edtDate  = findViewById(R.id.edtDate);
        edtTime  = findViewById(R.id.edtTime);

        // Nếu là Edit: nạp dữ liệu cũ
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            edtName.setText(i.getStringExtra("name"));
            edtPlace.setText(i.getStringExtra("place"));
            edtDate.setText(i.getStringExtra("date"));
            edtTime.setText(i.getStringExtra("time"));
        }

        // Dialog chọn Place
        edtPlace.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Select place")
                        .setItems(places, (d, w) -> edtPlace.setText(places[w]))
                        .show());

        // Dialog chọn Date
        edtDate.setOnClickListener(v -> showDatePicker());

        // Dialog chọn Time
        edtTime.setOnClickListener(v -> showTimePicker());
    }

    private void showDatePicker() {
        new DatePickerDialog(this, (view, y, m, d) -> {
            calendar.set(y, m, d);
            String s = String.format("%02d/%02d/%04d", d, m + 1, y);
            edtDate.setText(s);
        }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        new TimePickerDialog(this, (view, h, mi) -> {
            calendar.set(Calendar.HOUR_OF_DAY, h);
            calendar.set(Calendar.MINUTE, mi);
            String s = String.format("%02d:%02d", h, mi);
            edtTime.setText(s);
        }, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            if (!validate()) return true;

            Intent data = new Intent();
            data.putExtra("name",  edtName.getText().toString().trim());
            data.putExtra("place", edtPlace.getText().toString().trim());
            data.putExtra("date",  edtDate.getText().toString().trim());
            data.putExtra("time",  edtTime.getText().toString().trim());
            setResult(RESULT_OK, data);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validate() {
        if (edtName.getText().toString().trim().isEmpty()) {
            edtName.setError("Please enter event name");
            return false;
        }
        return true;
    }
}