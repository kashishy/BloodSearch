package com.example.ashish.bloodsearch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class recycler_adapter_requests extends RecyclerView.Adapter<viewHolder> {
    private List<display_data> users;
    Context mCtx;


    public recycler_adapter_requests(List<display_data> users, Context mCtx) {
        this.users = users;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.recycler_item,parent,false);
        viewHolder userviewHolder=new viewHolder(view);
        return userviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        display_data sampleuser=users.get(position);
        holder.item_name.setText(sampleuser.getName());
        holder.item_city.setText(sampleuser.getCity());
        holder.item_blood.setText(sampleuser.getBlood_group());
        final String mobile_number=sampleuser.getMobile();
        holder.call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(mCtx,"Image Clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.CALL");
                Uri data = Uri.parse("tel:"+ mobile_number );
                intent.setData(data);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
