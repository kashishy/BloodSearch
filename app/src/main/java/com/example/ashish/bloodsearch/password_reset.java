package com.example.ashish.bloodsearch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class password_reset extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText reset_email;
    Button reset_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        firebaseAuth=FirebaseAuth.getInstance();
        reset_email=findViewById(R.id.reset_email_id);
        reset_button=findViewById(R.id.reset_button_id);

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkDataEntered());
                {
                    String email=reset_email.getText().toString().trim();

                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(password_reset.this, "Please check your Email Account...If you want to reset Password!!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(password_reset.this,loginacivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(password_reset.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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
        if(!isEmail(reset_email))
        {
            reset_email.setError("Incorrect Email");
            return false;
        }
        else
        {
            return true;
        }
    }
}
