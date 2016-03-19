package com.example.abhinandan.quoteit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by ABHINANDAN on 3/19/2016.
 */
public class scrapQuote {
    String url="http://www.brainyquote.com/quotes_of_the_day.html";
    Context myContext;
    String quoteContent,quoteAuthor;
    ProgressDialog mProgress;
    TextView tvContent,tvAuthor;

    scrapQuote(){}
    scrapQuote(Context context,TextView textView1,TextView textView2){
        myContext=context;
        tvContent=textView1;
        tvAuthor=textView2;
    }

    void fetchQuoteOfTheDay(){
        new qOfTheDay().execute();
    }

    public class qOfTheDay extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress=new ProgressDialog(myContext);
            mProgress.setTitle("Quote of the day");
            mProgress.setMessage("Loading...");
            mProgress.setIndeterminate(false);
            mProgress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Document document= Jsoup.connect(url).get();
                Element quote=document.select("div[class=boxy bqQt bqShare]").first();

                quoteContent=quote.select("span[class=bqQuoteLink]").select("a:eq(0)").text();
                quoteAuthor=quote.select("div[class=bq-aut]").select("a:eq(0)").text();
//               Log.e("content website : ", quote.toString());
//               Log.e("RESULT :::: ",quoteContent +"-----> "+quoteAuthor);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgress.dismiss();
            tvContent.setText(quoteContent);
            tvAuthor.setText(quoteAuthor);
        }
    }
}
