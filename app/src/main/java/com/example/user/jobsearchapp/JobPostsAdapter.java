package com.example.user.jobsearchapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class JobPostsAdapter extends RecyclerView.Adapter<JobPostsAdapter.MyViewHolder> {

    private List<JobPost> postingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, site, PostDate;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            PostDate = (TextView) view.findViewById(R.id.PostDate);
            site = (TextView) view.findViewById(R.id.site);
        }
    }


    public JobPostsAdapter(List<JobPost> postingList) {
        this.postingList = postingList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_post_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        JobPost movie = postingList.get(position);
        holder.title.setText(movie.getTitle());
        holder.site.setText(movie.getSite());
        holder.PostDate.setText(movie.getPostDate());
    }

    @Override
    public int getItemCount() {
        return postingList.size();
    }



}
