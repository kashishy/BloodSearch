package com.example.ashish.bloodsearch;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CircleImageView user_image;
    private EditText profile_email;
    private EditText profile_name;
    private EditText profile_mobile;
    private EditText profile_state;
    private EditText profile_age;
    private EditText profile_city;
    private Button profile_save_button;
    private static final int Gallery_Pick=1;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;
    private String user_id;
    private ProgressBar progressBar;
    private String blood[]={"A+","A-","B+","B-","AB+","AB-","O+","O-"};
            Spinner profile_blood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Initialiesed();
        databaseReference.keepSynced(true);
        user_id = firebaseAuth.getCurrentUser().getUid();
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_blood.setAdapter(arrayAdapter);
        profile_blood.setOnItemSelectedListener(this);

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,Gallery_Pick);
            }
        });
        profile_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,mobile,city,state,age,blood,email;
                email=profile_email.getText().toString().trim();
                name=profile_name.getText().toString().trim();
                mobile=profile_mobile.getText().toString().trim();
                city=profile_city.getText().toString().trim();
                state=profile_state.getText().toString().trim();
                age=profile_age.getText().toString().trim();
                blood=profile_blood.getSelectedItem().toString().trim();
                name = changeCase(name);
                email=changeCase(email);
                state = changeCase(state);
                city = changeCase(city);
                name = toTitleCase(name);
                state = toTitleCase(state);
                city = toTitleCase(city);

                profile_edit user=new profile_edit(age,blood,city,mobile,name,state);
                databaseReference.child(user_id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ProfileActivity.this,"Profile Updated Successfully.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ProfileActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                }
        });

        RetrieveUserInfo();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null)
        {
            Uri imageUri=data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){

                 Uri resultUri=result.getUri();
                 progressBar.setVisibility(View.VISIBLE);

                 final StorageReference filepath=storageReference.child(user_id +".jpg");
                 filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                         if (task.isSuccessful())
                         {
                             //Toast.makeText(ProfileActivity.this,"Profile image updated",Toast.LENGTH_SHORT).show();
                             filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                 @Override
                                 public void onSuccess(Uri uri) {
                                     databaseReference.child(user_id).child("image").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()) {
                                                 progressBar.setVisibility(View.INVISIBLE);
                                                 Toast.makeText(ProfileActivity.this, "Image stored in database", Toast.LENGTH_SHORT).show();
                                             }
                                             else
                                             {
                                                 progressBar.setVisibility(View.INVISIBLE);
                                                 Toast.makeText(ProfileActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     });
                                 }
                             });

                         }
                         else
                         {
                             progressBar.setVisibility(View.INVISIBLE);
                             Toast.makeText(ProfileActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();

                         }
                     }
                 });

            }

        }
    }

    private void RetrieveUserInfo()
    {
        databaseReference.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if((dataSnapshot.exists())&&(dataSnapshot.hasChild("image")))
                {
                    String Username=dataSnapshot.child("name").getValue().toString();
                    String Usercity=dataSnapshot.child("city").getValue().toString();
                    String Userstate=dataSnapshot.child("state").getValue().toString();
                    String Userage=dataSnapshot.child("age").getValue().toString();
                    String Usermobile=dataSnapshot.child("mobile").getValue().toString();
                    String Userblood=dataSnapshot.child("blood_group").getValue().toString();
                    String Userimage=dataSnapshot.child("image").getValue().toString();
                    String Useremail=dataSnapshot.child("email").getValue().toString();
                    int i;
                    for(i=0;i<8;i++)
                    {
                        if(Userblood.equals(blood[i]))
                        {
                            break;
                        }
                    }
                    profile_age.setText(Userage);
                    profile_city.setText(Usercity);
                    profile_name.setText(Username);
                    profile_mobile.setText(Usermobile);
                    profile_state.setText(Userstate);
                    profile_email.setText(Useremail);
                    int position = getIntent().getIntExtra( Userblood,i);
                    profile_blood.setSelection( position );
                    Picasso.get().load(Userimage).into(user_image);

                }
                else if((dataSnapshot.exists()))
                {
                    String Username=dataSnapshot.child("name").getValue().toString();
                    String Usercity=dataSnapshot.child("city").getValue().toString();
                    String Userstate=dataSnapshot.child("state").getValue().toString();
                    String Userage=dataSnapshot.child("age").getValue().toString();
                    String Usermobile=dataSnapshot.child("mobile").getValue().toString();
                    String Userblood=dataSnapshot.child("blood_group").getValue().toString();
                    String Useremail=dataSnapshot.child("email").getValue().toString();
                    int i;
                    for(i=0;i<8;i++)
                    {
                        if(Userblood.equals(blood[i]))
                        {
                            break;
                        }
                    }
                    profile_age.setText(Userage);
                    profile_city.setText(Usercity);
                    profile_name.setText(Username);
                    profile_mobile.setText(Usermobile);
                    profile_state.setText(Userstate);
                    profile_email.setText(Useremail);
                    int position = getIntent().getIntExtra( Userblood,i);
                    profile_blood.setSelection( position );
                    Picasso.get().load(R.drawable.drawerimageicon).into(user_image);
                }
                else
                {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Initialiesed()
    {
        user_image=findViewById(R.id.profile_image_id);
        profile_name=findViewById(R.id.profile_name_id);
        profile_mobile=findViewById(R.id.profile_mobile_id);
        profile_state=findViewById(R.id.profile_state_id);
        profile_age=findViewById(R.id.profile_age_id);
        profile_city=findViewById(R.id.profile_city_id);
        profile_email=findViewById(R.id.profile_email_id);
        profile_blood=findViewById(R.id.profile_spinner);
        progressBar = new ProgressBar(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("users_data");
        profile_save_button=findViewById(R.id.profile_save_id);
        storageReference=FirebaseStorage.getInstance().getReference().child("Profile_images");
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
        if(isEmpty(profile_name))
        {
            profile_name.setError("Enter Name");
            profile_name.requestFocus();
            return false;
        }
        else if(isEmpty(profile_mobile)){
            profile_mobile.setError("Enter Mobile Number");
            profile_mobile.requestFocus();
            return false;
        }
        else if(profile_mobile.length()!=10){
            profile_mobile.setError("Must 10 Digits");
            profile_mobile.requestFocus();
            return false;
        }

        else if(isEmpty(profile_state)){
            profile_state.setError("Enter State");
            profile_state.requestFocus();
            return false;
        }
        else if(isEmpty(profile_city)){
            profile_city.setError("Enter City");
            profile_city.requestFocus();
            return false;
        }
        else if(isEmpty(profile_age)){
            profile_age.setError("Enter Age");
            profile_age.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String bloodgroup=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
