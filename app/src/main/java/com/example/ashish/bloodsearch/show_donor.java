package com.example.ashish.bloodsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class show_donor extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    recycler_adapter recyclerAdapter;
    List<User> users;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_donor);

        toolbar=findViewById(R.id.toolbr_show_donors);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerview_show_donors);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users=new ArrayList<>();

        /*users.add(new User(

        ));*/

        databaseReference= FirebaseDatabase.getInstance().getReference("registered_donors");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    for(DataSnapshot donorsSnapShot : dataSnapshot.getChildren()){
                        User donorData=donorsSnapShot.getValue(User.class);
                        users.add(donorData);

                    }
                    recyclerAdapter=new recycler_adapter(users,show_donor.this);
                    recyclerView.setAdapter(recyclerAdapter);
                    Toast.makeText(show_donor.this,"Loaded",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                 Toast.makeText(show_donor.this,"Error",Toast.LENGTH_SHORT).show();

            }
        });
        
    }
}
