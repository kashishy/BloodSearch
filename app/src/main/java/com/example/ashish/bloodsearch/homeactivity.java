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
import com.google.firebase.database.DatabaseReference;

public class homeactivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    private CardView add_donor_cardview,donor_cardview,add_request_cardview,active_request_cardview,blood_banks,share;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView display_name,display_email;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        add_donor_cardview=findViewById(R.id.add_donor_id);
        donor_cardview=findViewById(R.id.donor_id);
        add_request_cardview=findViewById(R.id.add_request_id);
        active_request_cardview=findViewById(R.id.active_request_id);
        blood_banks=findViewById(R.id.blood_bank_id);
        share=findViewById(R.id.share_id);

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

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null)
        {
            String name=firebaseUser.getDisplayName();
            String email=firebaseUser.getEmail();
            display_name.setText(name);
            display_email.setText(email);
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
        blood_banks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(homeactivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.profile_id :
                break;
            case R.id.setting_id :
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
