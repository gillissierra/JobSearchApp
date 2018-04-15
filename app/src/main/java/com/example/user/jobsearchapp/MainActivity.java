package com.example.user.jobsearchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //creates array list to store selected search terms
    ArrayList<String> queryTerms = new ArrayList<String>();
    // method run when check boxes are selected/deselected
    public void onCheckboxClicked(android.view.View view) {

        // Checks if checkbox is checked
        boolean checked = ((android.widget.CheckBox) view).isChecked();

        // checkbox on click
        //Education row
        switch(view.getId()) {
            //PhD
            case R.id.checkBoxPhD:
                //on check
                if (checked) {
                    queryTerms.add("PhD ");
                    queryTerms.add("Ph.D");
                    queryTerms.add("Doctorate");
                }
                //on uncheck
                if (!checked) {
                    queryTerms.remove("PhD ");
                    queryTerms.remove("Ph.D");
                    queryTerms.remove("Doctorate");
                }
                break;
            // MSc
            case R.id.checkBoxMSc:
                if (checked) {
                    queryTerms.add("Masters");
                    queryTerms.add("Master's");
                    queryTerms.add("M.Sc");
                    queryTerms.add("MD");
                    queryTerms.add("M.D");
                    queryTerms.add("MSc");
                }
                if (!checked){
                    queryTerms.remove("Masters");
                queryTerms.remove("Master's");
                queryTerms.remove("M.Sc");
                queryTerms.remove("MD");
                queryTerms.remove("M.D");
                queryTerms.remove("MSc");
        } break;
            // BSc
            case R.id.checkBoxBSc:
                if (checked) {
                    queryTerms.add("B.Sc.");
                    queryTerms.add("BSc");
                    queryTerms.add("Bachelor");
                    queryTerms.add("undergrad");
                    queryTerms.add("BA");
                    queryTerms.add("BS");
                }if (!checked) {
                queryTerms.remove("B.Sc.");
                queryTerms.remove("BSc");
                queryTerms.remove("Bachelor");
                queryTerms.remove("undergrad");
                queryTerms.remove("BA");
                queryTerms.remove("BS");
            }break;
                }
        // experience lvl
            //Senior
        switch(view.getId()) {
            case R.id.checkBoxSenior:
                if (checked)
                    queryTerms.add("Senior");
                if(!checked)
                    queryTerms.remove("Senior");
                break;
            // Associate
            case R.id.checkBoxAssoc:
                if (checked)
                    queryTerms.add("Associate");
                if(!checked)
                    queryTerms.remove("Associate");
                break;
        }
        // grabs all jobs
        switch(view.getId()) {
            case R.id.checkBoxALL:
                if (checked)
                    queryTerms.add("the");
                if(!checked)
                    queryTerms.remove("the");
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    } // starts xml for main activity

    public void sendSearch(View view){
        // on search button click, starts new intent
        // passes generated array list for query terms
        Intent act2intent = new Intent(this, Main2Activity.class);
        act2intent.putStringArrayListExtra("TermList", queryTerms);
        startActivity(act2intent);
    }
}
