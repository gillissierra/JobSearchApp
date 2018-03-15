package com.example.user.jobsearchapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    

    public void onCheckboxClicked(android.view.View view) {
        //Array to store filters

        //Checking if it is checked
        boolean checked = ((android.widget.CheckBox) view).isChecked();

        // Check which checkbox is clicked
        //Education
        switch(view.getId()) {
            case R.id.checkBoxPhD:
                if (checked)
                    //Send PhD into query terms

                    break;
            case R.id.checkBoxMSc:
                if (checked)
                    //Send Masters into query terms
                    break;
            case R.id.checkBoxBSc:
                if (checked)
                    //Send Bachelor's into query terms
                    break;
        }
        switch(view.getId()) {
            case R.id.checkBoxSenior:
                if (checked)
                    //Send Senior into query terms
                    break;
            case R.id.checkBoxAssoc:
                if (checked)
                    //Send Assistant into query terms
                    break;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Grab the website source
}
