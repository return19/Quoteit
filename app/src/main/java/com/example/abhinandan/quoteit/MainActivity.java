package com.example.abhinandan.quoteit;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    TextView tvQuote,tvAuthor;
    SharedPreferences pref;
    int alarmSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref=getApplicationContext().getSharedPreferences("Mypref",MODE_PRIVATE);
        SharedPreferences.Editor editor =pref.edit();

        alarmSet=pref.getInt("alarmSet",0);

        if(alarmSet==0)
        {
            editor.putInt("alarmSet",1);
            editor.apply(); // if problem arises change it to commit()
            setAlarm();
            Log.e("Alrmset : " ,"Enterned into alrmset");
        }else{
            Log.e("Not set : ","Didn't Enter");
        }


        tvQuote=(TextView)findViewById(R.id.tvQuote);
        tvAuthor=(TextView)findViewById(R.id.tvAuthor);

        scrapQuote obj=new scrapQuote(this,tvQuote,tvAuthor);
        obj.fetchQuoteOfTheDay();


    }

    void setAlarm(){
        Intent notificationIntent = new Intent(this, notifyIt.class);
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

        AlarmManager am = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        am.cancel(contentIntent);
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),24*60*60*1000, contentIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
