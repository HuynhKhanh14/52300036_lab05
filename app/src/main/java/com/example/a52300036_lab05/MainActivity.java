package com.example.a52300036_lab05;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = findViewById(R.id.btnBai1);
        Button b2 = findViewById(R.id.btnBai2);

        b1.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, Bai01Activity.class)));

        b2.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, Bai02Activity.class)));
    }
}