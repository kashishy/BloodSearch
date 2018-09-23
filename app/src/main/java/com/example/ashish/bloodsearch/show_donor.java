package com.example.ashish.bloodsearch;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class show_donor extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    String [] name={
            "user1",
            "user2",
            "user3",
            "user4",
            "user5",
            "user6",
            "user7",
            "user8",
            "user9",
            "user10",
            "user11"
    };
    String [] city={
            "city1",
            "city2",
            "city3",
            "city4",
            "city5",
            "city6",
            "city7",
            "city8",
            "city9",
            "city10",
            "city11"
    };
    String [] blood={
            "blood1",
            "blood",
            "blood3",
            "blood4",
            "blood5",
            "blood6",
            "blood7",
            "blood8",
            "blood9",
            "blood10",
            "blood11"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_donor);
        toolbar=findViewById(R.id.toolbr_show_donors);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recyclerview_show_donors);
        List<User> sampleuser=new ArrayList<>();
        for(int i=0;i<name.length;i++)
        {
            User user=new User();
            user.username=name[i];
            user.usercity=city[i];
            user.userblood=blood[i];
            sampleuser.add(user);
        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new recycler_adapter(sampleuser));
    }
}
