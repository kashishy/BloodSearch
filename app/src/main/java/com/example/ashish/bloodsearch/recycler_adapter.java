package com.example.ashish.bloodsearch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Toast;

import java.util.List;

public class recycler_adapter extends RecyclerView.Adapter<viewHolder> {
    private List<User> users;
    Context mCtx;

    public recycler_adapter(List<User> users, Context mCtx) {
        this.users = users;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_item, parent, false);
        viewHolder userviewHolder = new viewHolder(view);
        return userviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        User sampleuser = users.get(position);
        holder.item_name.setText(sampleuser.name);
        holder.item_city.setText(sampleuser.city);
        holder.item_blood.setText(sampleuser.blood_group);
        final String mobile_number=sampleuser.getMobile();
        /*holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, "Clicked  " + mobile_number, Toast.LENGTH_SHORT).show();
            }
        });*/
        holder.call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx, "Image Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile_number));
                if (ActivityCompat.checkSelfPermission(mCtx.getApplicationContext()      , Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
