package com.example.user.jobsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent DescDisplay = getIntent();
        String JobBody = DescDisplay.getStringExtra("BodyOfJob");
        String JobTitle = DescDisplay.getStringExtra("TitlePick");

        TextView Description;
        Description = findViewById(R.id.DescBox);
        Description.setMovementMethod(new ScrollingMovementMethod());
        Description.setText(Html.fromHtml(JobBody));

        TextView Title;
        Title = findViewById(R.id.jobTitle);
        Title.setText(JobTitle);

    }
}
