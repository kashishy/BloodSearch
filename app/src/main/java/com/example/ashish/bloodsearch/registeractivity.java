package com.example.ashish.bloodsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import static com.example.ashish.bloodsearch.R.array.numbers;

public class registeractivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    Button register1,register2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);
        spinner=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this, R.array.numbers,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        register1=(Button)findViewById(R.id.button);
        register2=(Button)findViewById(R.id.button2);
        /*register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(registeractivity.this,homeactivity.class);
                startActivity(intent);
            }
        });*/
        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(registeractivity.this,homeactivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String bloodgroup=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
