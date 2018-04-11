package com.example.user.jobsearchapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.*;


public class Main2Activity extends AppCompatActivity {

    TextView Update;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Update = findViewById(R.id.UpdateBox);


        Intent act2intent = getIntent();
        ArrayList<String> queryTerms = act2intent.getStringArrayListExtra("TermList");


        Web_Grab AllPosts = new Web_Grab();
        AllPosts.execute(queryTerms);

    }





    private class Web_Grab extends AsyncTask<ArrayList<String>, String, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... queryTerms){


                ArrayList<String> Terms = queryTerms[0];

            onProgressUpdate("Starting to collect job postings");

                ArrayList<String> IndJobs = IndeedGrab(Terms);
                ArrayList<String> MonJobs = MonsterGrab(Terms);

                ArrayList<String> AllJobs = new ArrayList<String>();

               for(int i=0; i< IndJobs.size(); i++){
                    AllJobs.add(IndJobs.get(i));
                }
               for(int i=0; i < MonJobs.size();i++) {
                   AllJobs.add(MonJobs.get(i));
               }



            onProgressUpdate("Jobs Compiled");
            return AllJobs;

            }
        @Override
        protected void onProgressUpdate(String... Step){

            Update.setText(Step[0]);

            try {
                Thread.sleep(200);
            }
            catch (InterruptedException e){}

            Log.e("Step", "Background Progress: "+ (Step[0]));
        }



        protected void onPostExecute(ArrayList<String> Final){

            Update.setText("Creating final list");

            try {
                Thread.sleep(4000);
            }
            catch (InterruptedException e){}

            Intent passJobList = new Intent(getBaseContext(),Main3Activity.class);
            passJobList.putStringArrayListExtra("ALLJOBS", Final);
            startActivity(passJobList);
        }



        public ArrayList<String> MonsterGrab(ArrayList < String > queryTerms) {
            String page = "https://www.monster.ca/jobs/search/?q=Bioinformatics";
            String webpage = getWebpage(page);

            //Pattern to search for all the job links displayed on the page using regex
            Pattern p = Pattern.compile("(\"url\":\")(.*?)(\")"); //([\w\W\s]+?)(","IsFastApply)
            Matcher m = p.matcher(webpage);
            //Push all links into an array
            ArrayList<String> MonJobLinks = new ArrayList<String>();

            //While loop to grab just the links in the webpage
            String tempLink = "";

            publishProgress("Started Grabbing Monster Links");

            while (m.find()) {
                tempLink = m.group(2);

                    //tempLink=Html.fromHtml(tempLink).toString();
                    Pattern HTML = Pattern.compile("(&#8217;|&#39;|#39;|’)"); //|â\?\?
                    tempLink = HTML.matcher(tempLink).replaceAll("'");

                    Pattern HTML2 = Pattern.compile("&amp;");
                    tempLink = HTML2.matcher(tempLink).replaceAll("&");

                    Matcher HTML3 = Pattern.compile("president").matcher(tempLink);

                    MonJobLinks.add(tempLink);
                    publishProgress("Matched Monster Link");


            }

            publishProgress(" Collected all Monster links");

            //Pattern to search for all the job titles displayed on the page using regex
            Pattern p2 = Pattern.compile("(rel=\"nofollow\">)([^<][\\w\\W]+?)(</a>)|(&#39;\\)\">)([\\w\\W ]+?)([ <]/a>)");
            m = p2.matcher(webpage);
            //Push all links into an array
            ArrayList<String> MonJobTitle = new ArrayList<String>();

            //While loop to grab just the titles in the webpage
            while (m.find()) {
                MonJobTitle.add(m.group(2));
            }

            publishProgress("Grabbed Monster Job titles ");

            //Pattern to search for all the job post dates displayed on the page using regex
            Pattern p3 = Pattern.compile("(<time datetime=\"[\\W\\w]{16}\">)([\\w\\W]+?)(</time>)");
            m = p3.matcher(webpage);
            //Push all links into an array
            ArrayList<String> MonJobPDate = new ArrayList<String>();

            //While loop to grab just the titles in the webpage
            while (m.find()) {
                MonJobPDate.add(m.group(2));
            }

            publishProgress("Grabbed Monster Post Dates");

            //Create an iterator to continue through the arraylist
            Iterator<String> itr = MonJobLinks.iterator();

            ArrayList<String> MonBodies = new ArrayList<String>();

            //Display all links
            while (itr.hasNext()) {
                //Adds the link to job
                String jobLink = itr.next();

                String webPage2 = getWebpage(jobLink);
                //String webPage2 = getWebpage(Html.escapeHtml(jobLink));
                //Grabs just the job desc from the webpage
                MonBodies.add(parseJob(MonsterDesc(webPage2)));
            }

            publishProgress("Organized Monster Descriptions");

            //Choose specific links that contain the keywords
            ArrayList<Integer> MonQualified = chooseLinks(MonBodies, queryTerms);

            ArrayList<String> QualMonster = new ArrayList<String>();

            for(int i=0; i < MonQualified.size(); i++){
                QualMonster.add(MonJobTitle.get(MonQualified.get(i)));
                QualMonster.add("Monster");
                QualMonster.add(MonJobPDate.get(MonQualified.get(i)));
                QualMonster.add(MonJobLinks.get(MonQualified.get(i)));
                QualMonster.add(MonBodies.get(MonQualified.get(i)));
            }


            publishProgress("Finished compiling Monster Jobs");

            return QualMonster;

        }

        public ArrayList<String> IndeedGrab(ArrayList < String > queryTerms) {
            String page = "https://www.indeed.ca/m/jobs?q=Bioinformatics&l=";
            String webpage = getWebpage(page);

            publishProgress("Started collecting Indeed Jobs");

            //Pattern to search for all the job links displayed on the page using regex
            Pattern p = Pattern.compile("(data-)(jk=[\\w\\W]+?)( rel=nofollow)");  //(<h2 class="jobTitle"><a rel="nofollow" href=")([\w\W]+?)(">)
            Matcher m = p.matcher(webpage);
            //Push all links into an array
            ArrayList<String> IndJobLinks = new ArrayList<String>();

            //While loop to grab just the links in the webpage
            while (m.find()) {
                IndJobLinks.add("https://ca.indeed.com/m/viewjob?"+m.group(2));
                publishProgress("Matched Indeed Link");
            }

            publishProgress("Grabbed all Indeed Links");

            //Pattern to search for all the job titles displayed on the page using regex
            Pattern p2 = Pattern.compile("(jobTitle-color-purple\">)([\\w\\W]+?)(</h2></div>)");
            m = p2.matcher(webpage);
            //Push all links into an array
            ArrayList<String> IndJobTitles = new ArrayList<String>();

            //While loop to grab just the job titles in the webpage
            while (m.find()) {
                IndJobTitles.add(m.group(2));
            }

            publishProgress("Grabbed Indeed job titles");

            //Pattern to search for all the job post dates displayed on the page using regex
            Pattern p3 = Pattern.compile("(<span class=\"date\">)([\\w\\W]+?)(</span>)");
            m = p3.matcher(webpage);
            //Push all links into an array
            ArrayList<String> IndJobPDate = new ArrayList<String>();

            //While loop to grab just the job post dates in the webpage
            while (m.find()) {
                IndJobPDate.add(m.group(2));
            }

            publishProgress("Collected Indeed post dates");

            //Create an iterator to continue through the arraylist
            Iterator<String> itr = IndJobLinks.iterator();
            ArrayList<String> IndBodies = new ArrayList<String>();

            //Display all links
            while (itr.hasNext()) {
                //Adds the link to job
                String ijobLink = itr.next();

                String webPage2 = getWebpage(ijobLink);
               // String webPage2 = getWebpage(Html.escapeHtml(jobLink));

                //Grabs just the job desc from the webpage
                IndBodies.add(parseJob(IndeedDesc(webPage2)));
            }

            publishProgress("Collected Indeed job descriptions");

            //Choose specific links that contain the keywords
            ArrayList<Integer> IndQualified = chooseLinks(IndBodies, queryTerms);

            ArrayList<String> QualIndeed = new ArrayList<String>();

            for(int i=0; i < IndQualified.size(); i++){
                QualIndeed.add(IndJobTitles.get(i));
                QualIndeed.add("Indeed");
                QualIndeed.add(IndJobPDate.get(i));
                QualIndeed.add(IndJobLinks.get(i));
                QualIndeed.add(IndBodies.get(i));
            }

            publishProgress("Finished selecting indeed jobs");

            return QualIndeed;
        }

        private String getWebpage(String page){
            String webpage = "";

            try{
                //Grab URL and assign all source code to the variable in
                URL oracle = new URL(page);
                BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

                //Push all source code into a string webpage for easier parsing
                String inputLine;
                int count=0;
                while ((inputLine = in.readLine()) != null){
                        webpage += inputLine;
                        count++;
                }

                if(webpage.length() > 0) {
                    publishProgress("Grabbed webPage - # of lines: " + count);
                    //publishProgress(webpage);
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

        //This method receives the source code for the job listing and gets the job description
        private String IndeedDesc(String webPage2) {
            //Remove all new lines
            Pattern p = Pattern.compile("[\n]");
            webPage2 = p.matcher(webPage2).replaceAll("");

            //Grab just the job description from the webpage
            Pattern p21 = Pattern.compile("(\"jobsearch-JobComponent-description icl-u-xs-mt--md\">)([\\w\\W\\s]+?)(</div><div class)");
            Matcher m = p21.matcher(webPage2);

            //Place just the body into a variable called body
            String body = "";
            //ArrayList<String> descIndeed = new ArrayList<String>();
            while (m.find()) {
                body = m.group(2);
            }
            return body;
        }
        private String MonsterDesc(String webPage2) {
            //Remove all new lines
            Pattern p = Pattern.compile("[\n]");
            webPage2 = p.matcher(webPage2).replaceAll("");

            //Grab just the job description from the webpage
            Pattern p21 = Pattern.compile("(\\{\"title\":\")([\\w\\W\\s]+?)(</div>\",\")");
            Matcher m = p21.matcher(webPage2);

            //Place just the body into a variable called body
            String body = "";
            //ArrayList<String> descMonster = new ArrayList<String>();
            while (m.find()) {
                body += m.group(2);
                //publishProgress("Grabbed description: " + body);

            }
            publishProgress("Figured out what the job is!");
            return body;
        }
        //This method parses the job description
        private String parseJob(String body){
            //Stuff to parse out of body
            Pattern p1 = Pattern.compile("<div [^>]+>");
            body = p1.matcher(body).replaceAll("");

            Pattern p2 = Pattern.compile("<span [^>]+>|<i [^>]+>|<span>|<img [^>]+>|<li [^>]+>|<b [^>]+>|<h\\d [^>]+>");
            body = p2.matcher(body).replaceAll("");

            Pattern p3 = Pattern.compile("<p[^>]+>|</p>|<br>|<p>|<tr>|<ul>|<ol>|</li>");
            body = p3.matcher(body).replaceAll("\n");

            Pattern p4 = Pattern.compile("<div>|</div>|<strong>|</strong>|<em>|</em>|<li>|</ul>|</ol>|<i>|</i>|</span>|<table>|<tbody>|</table>|</tbody>|</tr>|<td>|<b>|</b>|<hr>");
            body = p4.matcher(body).replaceAll("");

            Pattern p4a = Pattern.compile("<h\\d>|</h\\d>");
            body = p4a.matcher(body).replaceAll("");

            Pattern p5 = Pattern.compile("</td>");
            body = p5.matcher(body).replaceAll("		  ");

            publishProgress("Collecting the important data");
            return body;
        }

        //Picks weblinks which contain the keywords specified by the user
        private ArrayList<Integer> chooseLinks(ArrayList<String> bodies, ArrayList<String> QLevel){

            //New list to return qualified list

            //ArrayList<String> qualified = new ArrayList<String>();
            ArrayList<Integer> MatchPos = new ArrayList<Integer>();

            //Iterators to go through the array lists
            Iterator<String> bodyItr = bodies.iterator();

            int i;
            Integer count =0;
            while(bodyItr.hasNext()) { //Loop through bodies
                String body = bodyItr.next();

                if(QLevel != null) {
                    for (i = 0; i < QLevel.size(); i++) { //Loop through Qualification levels

                        String level = QLevel.get(i);

                        Pattern p = Pattern.compile(level);
                        Matcher m = p.matcher(body);

                        if (m.find()) {
                            //qualified.add(body);
                            MatchPos.add(count);

                            break;
                        }
                    } //End QLevel for
                }
                count++;
            } //End Body while
            publishProgress("Filtered jobs for desired qualities");
            return MatchPos;
        } //End chooseLinks
    }// End of Async
}// End of Main Activity







