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

public class active_request_activity extends AppCompatActivity {
    Toolbar toolbar;
    recycler_adapter_requests recyclerAdapterRequests;
    RecyclerView recyclerView;
    List<display_data> users;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_request_activity);

        toolbar=findViewById(R.id.toolbr_show_donors);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerview_active_requests);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("blood_requests");
        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    for(DataSnapshot donorsSnapShot : dataSnapshot.getChildren()){
                        display_data donorData=donorsSnapShot.getValue(display_data.class);
                        users.add(donorData);

                    }
                    recyclerAdapterRequests=new recycler_adapter_requests(users,active_request_activity.this);
                    recyclerView.setAdapter(recyclerAdapterRequests);
                   // Toast.makeText(active_request_activity.this,"Loaded",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(active_request_activity.this,"Error",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
