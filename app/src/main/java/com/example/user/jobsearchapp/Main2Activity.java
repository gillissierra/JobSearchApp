package com.example.user.jobsearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.*;

public class Main2Activity extends AppCompatActivity {

    Intent intent=getIntent();
    ArrayList<String> queryTerms = intent.getStringArrayListExtra("EXTRA_QUERY");


        public static ArrayList<String> hasLinks (ArrayList < String > queryTerms) {
            String page = "https://www.monster.ca/jobs/search/?q=bioinformatics";
            String webpage = getWebpage(page);

            //Pattern to search for all the job links displayed on the page using regex
            Pattern p = Pattern.compile("(\"url\":\")(.*?)(\"})");
            Matcher m = p.matcher(webpage);
            //Push all links into an array
            ArrayList<String> jobLinks = new ArrayList<String>();

            //While loop to grab just the links in the webpage
            while (m.find()) {
                jobLinks.add(m.group(2));
            }

            //Create an iterator to continue through the arraylist
            Iterator<String> itr = jobLinks.iterator();

            ArrayList<String> bodies = new ArrayList<String>();

            //Display all links
            while (itr.hasNext()) {

                //Adds the link to job
                String jobLink = itr.next();
                String webPage2 = getWebpage(jobLink);

                //Grabs just the job desc from the webpage
                bodies.add(parseJob(webPage2));
            }

            //Choose specific links that contain the keywords
            ArrayList<String> qualifiedLinks = chooseLinks(bodies, queryTerms);

            return qualifiedLinks;
        }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            //Display results of Search
            TextView stringTextView = (TextView)findViewById(R.id.textView1);
            ArrayList<String> qualJobLinks =  hasLinks(queryTerms);

            //Printing string array list values on screen.

            for(int i=0; i < qualJobLinks.size(); i++){
                stringTextView.setText(stringTextView.getText() + qualJobLinks.get(i) + " , ");
            }

        }

    public static String getWebpage(String page){
        String webpage = "";

        try{
            //Grab URL and assign all source code to the variable in
            URL oracle = new URL(page);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            //Push all source code into a string webpage for easier parsing
            String inputLine;

            while ((inputLine = in.readLine()) != null){
                webpage += inputLine;
            }

            in.close(); //Close stream since it is not needed anymore
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException f){
            f.printStackTrace();
        }

        return webpage;
    }

    //This method revieves the source code for the job listing and parses the job description
    public static String parseJob(String webPage2){

        //Remove all new lines
        Pattern p = Pattern.compile("[\n]");
        webPage2 = p.matcher(webPage2).replaceAll("");

        //Grab just the job description from the webpage
        Pattern p21 = Pattern.compile("<div class=\"details-content .*?</div>?");
        Matcher m = p21.matcher(webPage2);

        //Place just the body into a variable called body
        String body = "";
        while(m.find()){
            body += m.group();
        }

        //Stuff to parse out of body
        Pattern p1 = Pattern.compile("<div [^>]+>");
        body = p1.matcher(body).replaceAll("");

        Pattern p2 = Pattern.compile("<span [^>]+>|<i [^>]+>|<span>|<img [^>]+>|<li [^>]+>|<b [^>]+>");
        body = p2.matcher(body).replaceAll("");

        Pattern p3 = Pattern.compile("<p[^>]+>|</p>|<br>|<p>|<tr>|<ul>|<ol>|</li>");
        body = p3.matcher(body).replaceAll("\n");

        Pattern p4 = Pattern.compile("<div>|</div>|<strong>|</strong>|<em>|</em>|<li>|</ul>|</ol>|<i>|</i>|</span>|<table>|<tbody>|</table>|</tbody>|</tr>|<td>|<b>|</b>|<hr>");
        body = p4.matcher(body).replaceAll("");

        Pattern p4a = Pattern.compile("<h\\d>|</h\\d>");
        body = p4a.matcher(body).replaceAll("");

        Pattern p5 = Pattern.compile("</td>");
        body = p5.matcher(body).replaceAll("		  ");

        return body;
    }

    //Picks weblinks which contain the keywords specified by the user
    public static ArrayList<String> chooseLinks(ArrayList<String> bodies, ArrayList<String> QLevel){

        //New list to return qualified list
        ArrayList<String> qualified = new ArrayList<String>();

        //Iterators to go through the array lists
        Iterator<String> bodyItr = bodies.iterator();
        Iterator<String> QLevelItr = QLevel.iterator();

        while(bodyItr.hasNext()) { //Loop through bodies
            String body = bodyItr.next();
            while (QLevelItr.hasNext()) { //Loop through Qualification levels

                String level = QLevelItr.next();

                Pattern p = Pattern.compile(level);
                Matcher m = p.matcher(body);

                if (m.find()) {
                    qualified.add(body);
                    break;
                }

            } //End QLevel while
        } //End Body while

        return qualified;
    } //End chooseLinks


} //End Main2Activity
