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
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_donor_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private  EditText name,email,mobile,city,state,age;
    private Button add_button;
    private ProgressBar progressBar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor_activity);
        spinner=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this, R.array.numbers,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("registered_donors");

        name=findViewById(R.id.name_id);
        email=findViewById(R.id.email_id);
        mobile=findViewById(R.id.mobile_id);
        state=findViewById(R.id.state_id);
        city=findViewById(R.id.city_id);
        age=findViewById(R.id.age_id);
        add_button=findViewById(R.id.add_button_id);
        progressBar=findViewById(R.id.progressbar_id);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                String donor_id,donor_name,donor_email,donor_mobile,donor_city,donor_state,donor_age,donor_blood;
                donor_id=firebaseAuth.getCurrentUser().getUid();
                donor_name=name.getText().toString().trim();
                donor_email=email.getText().toString().trim();
                donor_mobile=mobile.getText().toString().trim();
                donor_state=state.getText().toString().trim();
                donor_city=city.getText().toString().trim();
                donor_age=age.getText().toString().trim();
                donor_blood=spinner.getSelectedItem().toString().trim();

                firebaseUser=firebaseAuth.getCurrentUser();

                user_donors userDonors=new user_donors(donor_id,donor_name,donor_email,donor_mobile,donor_city,donor_state,donor_age,donor_blood);
                databaseReference.child(donor_id).setValue(userDonors).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            Toast.makeText(add_donor_activity.this,"Added Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(add_donor_activity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
