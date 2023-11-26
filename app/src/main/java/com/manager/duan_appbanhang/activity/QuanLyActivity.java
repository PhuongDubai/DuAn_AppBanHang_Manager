package com.manager.duan_appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.manager.duan_appbanhang.R;

import soup.neumorphism.NeumorphButton;
import soup.neumorphism.NeumorphCardView;

public class QuanLyActivity extends AppCompatActivity {
NeumorphCardView themsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        initView();
        initControl();
    }

    private void initControl() {
        themsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThemSPActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        themsp = findViewById(R.id.neu_themsanpham);
    }
}