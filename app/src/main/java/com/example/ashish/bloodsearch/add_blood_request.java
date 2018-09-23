package com.example.ashish.bloodsearch;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_blood_request extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private EditText name,email,mobile,state,city,blood;
    private Button request_button;
    private ProgressBar progressBar;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood_request);

        spinner=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this, R.array.numbers,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("blood_requests");

        name=findViewById(R.id.Rname_id);
        email=findViewById(R.id.Remail_id);
        mobile=findViewById(R.id.Rmobile_id);
        state=findViewById(R.id.Rstate_id);
        city=findViewById(R.id.Rcity_id);
        progressBar=findViewById(R.id.Rprogressbar_id);
        request_button=findViewById(R.id.request_button_id);

        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                String request_id,request_name,request_email,request_mobile,request_city,request_state,request_blood;
                request_id=firebaseAuth.getCurrentUser().getUid();
                request_name=name.getText().toString().trim();
                request_email=email.getText().toString().trim();
                request_mobile=mobile.getText().toString().trim();
                request_state=state.getText().toString().trim();
                request_city=city.getText().toString().trim();
                request_blood=spinner.getSelectedItem().toString().trim();

                firebaseUser=firebaseAuth.getCurrentUser();

                user_request userRequest=new user_request(request_id,request_name,request_email,request_mobile,request_city,request_state,request_blood);
                databaseReference.child(request_id).setValue(userRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        progressBar.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            Toast.makeText(add_blood_request.this, "Request Successfully Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(add_blood_request.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
