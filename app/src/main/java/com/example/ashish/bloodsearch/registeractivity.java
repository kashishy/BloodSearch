package com.example.ashish.bloodsearch;

import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.ashish.bloodsearch.R.array.numbers;

public class registeractivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    Button register_button;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;
    String email_text,password_text;
    EditText name,email,password,mobile_number,age,state,city,blood_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);
        spinner=findViewById(R.id.spinner_id);
        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this, R.array.numbers,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name_edittext_id);
        databaseReference= FirebaseDatabase.getInstance().getReference("users_data");
        email = findViewById(R.id.email_id);
        mobile_number=findViewById(R.id.mobilenumber_edittext_id);
        password = findViewById(R.id.password_edittext_id);
        city = findViewById(R.id.city_edittext_id);
        state = findViewById(R.id.state_edittext_id);
        age = findViewById(R.id.age_edittext_id);
        progressBar = findViewById(R.id.progress_bar);
        email_text = email.getText().toString();
        password_text = password.getText().toString();
        register_button = findViewById(R.id.register_button_id);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                 progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()){

                                    String user_id,user_name,user_email,user_password,user_mobile,user_city,user_state,user_age,user_blood;
                                    user_id=firebaseAuth.getCurrentUser().getUid();
                                    user_name=name.getText().toString().trim();
                                    user_email=email.getText().toString().trim();
                                    user_mobile=mobile_number.getText().toString().trim();
                                    user_password=password.getText().toString().trim();
                                    user_city=city.getText().toString().trim();
                                    user_state=state.getText().toString().trim();
                                    user_age=age.getText().toString().trim();
                                    user_blood=spinner.getSelectedItem().toString().trim();
                                    user_name=toTitleCase(user_name);
                                    user_city=toTitleCase(user_city);
                                    user_state=toTitleCase(user_state);

                                    firebaseUser=firebaseAuth.getCurrentUser();
                                    user_data userData=new user_data(user_id,user_name,user_email,user_mobile,user_password,user_city,user_state,user_age,user_blood);
                                    databaseReference.child(user_id).setValue(userData);
                                    UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name.getText().toString().trim())
                                            .build();
                                    firebaseUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(registeractivity.this,"Upload Successfull",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    finish();
                                    Toast.makeText(registeractivity.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(registeractivity.this,loginacivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(registeractivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
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
    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
