package com.example.ashish.bloodsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final Intent it=new Intent(start.this,loginacivity.class);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                //here you can start your Activity B.
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(it);
            }
        }, 2000);
    }
}
