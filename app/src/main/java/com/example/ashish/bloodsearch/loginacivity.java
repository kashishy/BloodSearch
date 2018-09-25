package com.example.ashish.bloodsearch;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class loginacivity extends AppCompatActivity {

    EditText login_email,login_password;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    Button loginbutton,registerbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginacivity);

        login_email=findViewById(R.id.login_email_id);
        login_password=findViewById(R.id.login_password_id);
        loginbutton=findViewById(R.id.login_button_id);
        registerbutton=findViewById(R.id.register_button_id);
        progressBar=findViewById(R.id.progress_id);
        firebaseAuth = FirebaseAuth.getInstance();

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean datacheck=checkDataEntered();
                progressBar.setVisibility(View.VISIBLE);
                if(datacheck) {
                    //progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(login_email.getText().toString().trim(),login_password.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           progressBar.setVisibility(View.INVISIBLE);
                            if(task.isSuccessful()){
                                finish();
                                Toast.makeText(loginacivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(loginacivity.this,homeactivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }else{
                                Toast.makeText(loginacivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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
         /*if(!isEmail(login_email))
         {
             login_email.setError("Incorrect Email");
             return false;
         }
         else if(isEmpty(login_password))
         {
             login_password.setError("Enter Password");
             return false;
         }
         else if(login_password.length()<6){
             login_password.setError("Minimum length of password is 6");
             return  false;
         }
         else*/
         {
             return true;
         }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            Intent intent=new Intent(loginacivity.this,homeactivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
