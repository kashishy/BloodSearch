package com.example.ashish.bloodsearch;

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

        final User sampleuser = users.get(position);
        holder.item_name.setText(sampleuser.name);
        holder.item_city.setText(sampleuser.city);
        holder.item_blood.setText(sampleuser.blood_group);
        final String mobile_Number = sampleuser.mobile;
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, "Clicked  " + mobile_Number, Toast.LENGTH_SHORT).show();
            }
        });
        holder.call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_donor swdn=new show_donor();
                Toast.makeText(mCtx, "Image Clicked", Toast.LENGTH_SHORT).show();
                swdn.callNumber(mobile_Number);
               // callFunction(mobile_Number);
                // Intent callIntent = new Intent(Intent.ACTION_CALL);
                // callIntent.setData(Uri.parse("tel" + mobile_Number));
                // mCtx.startActivity(callIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    //@SuppressLint("MissingPermission")
    public void callFunction(String number) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel" + number));
        if (ActivityCompat.checkSelfPermission(mCtx, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mCtx.startActivity(callIntent);

    }
}
