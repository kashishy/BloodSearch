package com.example.ashish.bloodsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class show_donor extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_donor);
        toolbar=findViewById(R.id.toolbr_show_donors);
        setSupportActionBar(toolbar);
    }
}
