package com.example.ashish.bloodsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class loginacivity extends AppCompatActivity {

    Button loginbutton,registerbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginacivity);
        loginbutton=(Button)findViewById(R.id.loginid);
        registerbutton=(Button)findViewById(R.id.registerid);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginacivity.this,homeactivity.class);
                startActivity(intent);
            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginacivity.this,registeractivity.class);
                startActivity(intent);
            }
        });
    }
}
