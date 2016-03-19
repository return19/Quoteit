package com.example.abhinandan.quoteit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Random;

public class notifyIt extends BroadcastReceiver {

    String quoteContent,quoteAuthor;
    NotificationCompat.Builder notify;
    NotificationManager nMgr;
    String url="http://www.brainyquote.com/quotes_of_the_day.html";

    public notifyIt() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Intent intent1=new Intent(context,MainActivity.class);

        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent1);

        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);
        notify=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher);

        nMgr=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        new qOfTheDay().execute();
    }

    public class qOfTheDay extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Document document= Jsoup.connect(url).get();
                Element quote=document.select("div[class=boxy bqQt bqShare]").first();

                quoteContent=quote.select("span[class=bqQuoteLink]").select("a:eq(0)").text();
                quoteAuthor=quote.select("div[class=bq-aut]").select("a:eq(0)").text();

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notify.setContentTitle("Quote of the day")
                  .setContentText(quoteContent)
                  .setWhen(System.currentTimeMillis());
            nMgr.notify(0,notify.build());
        }
    }
}
