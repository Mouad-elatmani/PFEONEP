package com.example.testproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class CenterActivity extends AppCompatActivity {
ImageView tempreel,data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        tempreel = findViewById(R.id.tempreel);
        data=findViewById(R.id.datast);

        tempreel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CenterActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}