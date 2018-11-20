package com.example.ashish.bloodsearch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class homeactivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    private CardView add_donor_cardview,donor_cardview,add_request_cardview,active_request_cardview,blood_banks,share;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    CircleImageView user_image;
    TextView display_name,display_email;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Intent shareIntent;
    String sharebody="Share my app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        add_donor_cardview=findViewById(R.id.add_donor_id);
        donor_cardview=findViewById(R.id.donor_id);
        add_request_cardview=findViewById(R.id.add_request_id);
        active_request_cardview=findViewById(R.id.active_request_id);
        //blood_banks=findViewById(R.id.blood_bank_id);
        share=findViewById(R.id.share_id);
        databaseReference= FirebaseDatabase.getInstance().getReference("users_data");

        toolbar =  findViewById(R.id.toolbr);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawerid);
        navigationView=findViewById(R.id.navigation_view);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        View navHeader=navigationView.getHeaderView(0);
        display_name=navHeader.findViewById(R.id.user_display_name_id);
        display_email=navHeader.findViewById(R.id.user_display_email_id);
        user_image=navHeader.findViewById(R.id.user_display_dp_id);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null)
        {
            String name=firebaseUser.getDisplayName();
            String email=firebaseUser.getEmail();
            display_name.setText(name);
            display_email.setText(email);
            databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists() && dataSnapshot.hasChild("image"))
                    {
                        String Userimage=dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(Userimage).into(user_image);
                    }
                    else
                    {
                        Picasso.get().load(R.drawable.drawerimageicon).into(user_image);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        navigationView.setNavigationItemSelectedListener(this);

        add_donor_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(homeactivity.this,add_donor_activity.class);
                startActivity(intent);
            }
        });
        donor_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(homeactivity.this,show_donor.class);
                startActivity(intent);
            }
        });
        add_request_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(homeactivity.this,add_blood_request.class);
                startActivity(intent);
            }
        });
        active_request_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(homeactivity.this,active_request_activity.class);
                startActivity(intent);
            }
        });
        /*blood_banks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(homeactivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });*/
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"My App");
                shareIntent.putExtra(Intent.EXTRA_TEXT,sharebody);
                startActivity(Intent.createChooser(shareIntent,"Share via"));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.profile_id :
                Intent intent=new Intent(homeactivity.this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_id :{
                firebaseAuth.signOut();
                finish();
                Toast.makeText(homeactivity.this, "Signout Successful", Toast.LENGTH_SHORT).show();
                Intent it=new Intent(homeactivity.this,loginacivity.class);
                startActivity(it);
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
