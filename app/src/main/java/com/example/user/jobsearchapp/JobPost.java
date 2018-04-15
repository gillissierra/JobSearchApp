package com.example.user.jobsearchapp;


public class JobPost {
    private String title, site, PostDate, JLink, Desc;

    public JobPost() {
    }
    // creates an object that contains all the information collected for a particular job post
    public JobPost(String title, String site, String PostDate, String JLink, String Desc) {
        this.title = title;
        this.PostDate = PostDate;
        this.site = site;
        this.JLink = JLink;
        this.Desc = Desc;
    }

    // series of methods to set and obtain specific information on the object
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

    public String getJLink(){return JLink;}

    public void setJLink(){ this.JLink = JLink; }

    public String getDesc() { return Desc; }

    public void setDesc(String Desc) { this.Desc = Desc; }

}
