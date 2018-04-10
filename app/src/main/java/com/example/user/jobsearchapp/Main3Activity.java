package com.example.user.jobsearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    private List<JobPost> jobList2 = new ArrayList<>();
    private RecyclerView recyclerView2;
    private JobPostsAdapter jAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);

        jAdapter2 = new JobPostsAdapter(jobList2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(jAdapter2);


        // row click listener
        recyclerView2.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView2, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                JobPost movie = jobList2.get(position);
                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        compileJobs2();


    }

        private void compileJobs2(){

            Intent passJobList = getIntent();
            ArrayList<String> AllJobs = passJobList.getStringArrayListExtra("ALLJOBS");


            String[] AllJPosts = new String[AllJobs.size()];

            for(int i=0; i < AllJobs.size(); i++){
                AllJPosts[i] = AllJobs.get(i);
            }

            if(AllJobs == null){
                JobPost jobPost = new JobPost("Didn't work", "Site", "now");
                jobList2.add(jobPost);
            }else {

                for (int i = 0; i < AllJobs.size(); i = i + 5) {
                    //Job title     Site Source     Post Date
                    JobPost jobpost = new JobPost(AllJPosts[i], AllJPosts[i + 1], AllJPosts[i + 2]);
                    jobList2.add(jobpost);
                }
            }
            jAdapter2.notifyDataSetChanged();
        }


}
