package com.example.ashish.bloodsearch;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class loginacivity extends AppCompatActivity {

    private EditText login_email,login_password;
    private TextView newuser,forgot;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private Button loginbutton,registerbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginacivity);

        login_email=findViewById(R.id.login_email_id);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login In Please Wait");
        progressDialog.setCancelable(false);
        newuser=findViewById(R.id.new_id);
        forgot=findViewById(R.id.forgot_id);
        login_password=findViewById(R.id.login_password_id);
        loginbutton=findViewById(R.id.login_button_id);
        registerbutton=findViewById(R.id.register_button_id);
        progressBar=findViewById(R.id.progress_id);
        firebaseAuth = FirebaseAuth.getInstance();

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean datacheck=checkDataEntered();
                //progressBar.setVisibility(View.VISIBLE);
                if(datacheck) {
                    //progressBar.setVisibility(View.VISIBLE);
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(login_email.getText().toString().trim(),login_password.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           //progressBar.setVisibility(View.INVISIBLE);
                           progressDialog.dismiss();
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
                }else{
                    //progressBar.setVisibility(View.INVISIBLE);
                    progressDialog.dismiss();
                }
            }
        });
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginacivity.this,registeractivity.class);
                startActivity(intent);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginacivity.this,password_reset.class);
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
         if(!isEmail(login_email))
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
         else
         {
             return true;
         }
    }
}
