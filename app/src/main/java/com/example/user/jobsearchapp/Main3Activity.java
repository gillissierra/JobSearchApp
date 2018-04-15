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

    // collects and displays job postings that match chosen terms
    // converts string list into a list of jobPost objects
    // uses a recycler view display objects on XML
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

        // interface to recycler view
        jAdapter2 = new JobPostsAdapter(jobList2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(jAdapter2);

        compileJobs2();


        // row click listener
        recyclerView2.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView2, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                JobPost PostList2 = jobList2.get(position);

                String JobBody;
                String titlePick;
                JobBody = PostList2.getDesc();
                titlePick = PostList2.getTitle();
                if(JobBody.length() != 0) {
                    Intent DescDisplay = new Intent(getBaseContext(), Main4Activity.class);
                    DescDisplay.putExtra("BodyOfJob", JobBody);
                    DescDisplay.putExtra("TitlePick", titlePick);
                    startActivity(DescDisplay);
                }else{
                    JobBody = "Sorry bub";
                        Intent DescDisplay = new Intent(getBaseContext(), Main4Activity.class);
                        DescDisplay.putExtra("BodyOfJob", JobBody);
                        startActivity(DescDisplay);
                }
            }
            @Override
            public void onLongClick(View view, int position) {

            }

        }));


    }

        private void compileJobs2(){

            Intent passJobList = getIntent();
            ArrayList<String> AllJobs = passJobList.getStringArrayListExtra("ALLJOBS");


            String[] AllJPosts = new String[AllJobs.size()];

            for(int i=0; i < AllJobs.size(); i++){
                AllJPosts[i] = AllJobs.get(i);
            }

            if(AllJobs.size() == 0){
                JobPost jobPost = new JobPost("Didn't work", "Site", "now", "BackLink", "BackDesc");
                jobList2.add(jobPost);
            }else {

                for (int i = 0; i < AllJobs.size(); i = i + 5) {
                                            //Job title       Site Source         Post Date
                    JobPost jobpost = new JobPost(AllJPosts[i], AllJPosts[i + 1], AllJPosts[i + 2],AllJPosts[i + 3], AllJPosts[i + 4]);
                    jobList2.add(jobpost);
                }
            }
            jAdapter2.notifyDataSetChanged();
        }


}
