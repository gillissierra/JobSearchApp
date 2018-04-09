package com.example.user.jobsearchapp;


public class JobPost {
    private String title, site, PostDate;

    public JobPost() {
    }

    public JobPost(String title, String site, String PostDate) {
        this.title = title;
        this.PostDate = PostDate;
        this.site = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getPostDate() {
        return PostDate;
    }

    public void setPostDate(String PostDate) {
        this.PostDate = PostDate;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

}
