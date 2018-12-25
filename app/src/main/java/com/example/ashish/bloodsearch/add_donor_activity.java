package com.example.ashish.bloodsearch;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
    EditText name,email,mobile,city,state,age;
    private Button add_button;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor_activity);
        spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("registered_donors");
        databaseReference.keepSynced(true);

        name = findViewById(R.id.name_id);
        email = findViewById(R.id.email_id);
        mobile = findViewById(R.id.mobile_id);
        state = findViewById(R.id.state_id);
        city = findViewById(R.id.city_id);
        age = findViewById(R.id.age_id);
        add_button = findViewById(R.id.add_button_id);
        progressBar = findViewById(R.id.progressbar_id);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("As Donor");
        progressDialog.setMessage("Adding Please Wait");
        progressDialog.setCancelable(false);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //progressBar.setVisibility(View.VISIBLE);
                progressDialog.show();
                if (checkDataEntered()) {
                    String donor_id, donor_name, donor_email, donor_mobile, donor_city, donor_state, donor_age, donor_blood;
                    donor_id = firebaseAuth.getCurrentUser().getUid();
                    donor_name = name.getText().toString().trim();
                    donor_name = changeCase(donor_name);
                    donor_name = toTitleCase(donor_name);
                    donor_email = email.getText().toString().trim();
                    donor_email = changeCase(donor_email);
                    donor_mobile = mobile.getText().toString().trim();
                    donor_state = state.getText().toString().trim();
                    donor_state = changeCase(donor_state);
                    donor_state = toTitleCase(donor_state);
                    donor_city = city.getText().toString().trim();
                    donor_city = changeCase(donor_city);
                    donor_city = toTitleCase(donor_city);
                    donor_age = age.getText().toString().trim();
                    donor_blood = spinner.getSelectedItem().toString().trim();

                    firebaseUser = firebaseAuth.getCurrentUser();

                    user_donors userDonors = new user_donors(donor_id, donor_name, donor_email, donor_mobile, donor_city, donor_state, donor_age, donor_blood);
                    databaseReference.child(donor_id).setValue(userDonors).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //progressBar.setVisibility(View.INVISIBLE);
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(add_donor_activity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(add_donor_activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    progressDialog.dismiss();
                    //progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    public String changeCase(String a){
        String result=a.toLowerCase();
        return result;
    }
    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String bloodgroup=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    boolean isEmail(EditText text){
        CharSequence email=text.getText().toString().trim();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    boolean isEmpty(EditText text){
        CharSequence string =text.getText().toString().trim();
        return (TextUtils.isEmpty(string));
    }
    boolean checkDataEntered()
    {
        if(isEmpty(name))
        {
            name.setError("Enter Name");
            name.requestFocus();
            return false;
        }
        if(!isEmail(email))
        {
            email.setError("Incorrect Email");
            email.requestFocus();
            return false;
        }
        else if(isEmpty(mobile)){
            mobile.setError("Enter Mobile Number");
            mobile.requestFocus();
            return false;
        }
        else if(mobile.length()!=10){
            mobile.setError("Must 10 Digits");
            mobile.requestFocus();
            return false;
        }
        else if(isEmpty(state)){
            state.setError("Enter State");
            state.requestFocus();
            return false;
        }
        else if(isEmpty(city)){
            city.setError("Enter City");
            city.requestFocus();
            return false;
        }
        else if(isEmpty(age)){
            age.setError("Enter Age");
            age.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }
}
