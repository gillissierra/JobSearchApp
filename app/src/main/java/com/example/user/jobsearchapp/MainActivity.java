package com.example.user.jobsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_QUERY = "com.example.user.jobsearchapp.QUERY";
    ArrayList<String> queryTerms = new ArrayList<String>();

    public void onCheckboxClicked(android.view.View view) {
        //Array to store filters

        //Checking if it is checked
        boolean checked = ((android.widget.CheckBox) view).isChecked();

        // Check which checkbox is clicked
        //Education
        switch(view.getId()) {
            case R.id.checkBoxPhD:
                if (checked)
                    queryTerms.add("PhD");
                    break;
            case R.id.checkBoxMSc:
                if (checked)
                    queryTerms.add("Masters");
                    queryTerms.add("Master's");
                    break;
            case R.id.checkBoxBSc:
                if (checked)
                    queryTerms.add("B.Sc.");
                    queryTerms.add("BSc");
                    break;
        }
        switch(view.getId()) {
            case R.id.checkBoxSenior:
                if (checked)
                    queryTerms.add("Senior");
                    break;
            case R.id.checkBoxAssoc:
                if (checked)
                    queryTerms.add("Associate");
                    break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//To DoL show all
        //Grab the website source when search is clicked

    public void sendSearch(View view){
        //Go to the next activity so the user cant press anything
        //Requires building an intent
        android.content.Intent intent = new Intent(this, Main2Activity.class);
        //An Intent can carry data types as key-value pairs called extras
        intent.putStringArrayListExtra(EXTRA_QUERY, queryTerms);
        startActivity(intent);
    }
}
