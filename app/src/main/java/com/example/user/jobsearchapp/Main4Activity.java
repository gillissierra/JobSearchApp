package com.example.user.jobsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent DescDisplay = getIntent();
        String JobBody = DescDisplay.getStringExtra("BodyOfJob");

        TextView Description;
        Description = findViewById(R.id.DescBox);
        Description.setMovementMethod(new ScrollingMovementMethod());
        Description.setText(JobBody);

    }
}
