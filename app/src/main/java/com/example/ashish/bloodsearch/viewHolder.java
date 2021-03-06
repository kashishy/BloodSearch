package com.example.ashish.bloodsearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class viewHolder extends RecyclerView.ViewHolder {

    TextView item_name,item_city,item_blood;
    RelativeLayout relativeLayout;
    ImageView call_image;

    public viewHolder(View itemView) {
        super(itemView);
        item_name=itemView.findViewById(R.id.recycler_item_name_id);
        item_city=itemView.findViewById(R.id.recycler_item_city_id);
        item_blood=itemView.findViewById(R.id.recycler_item_blood_id);
        relativeLayout=itemView.findViewById(R.id.linear_outside_id);
        call_image=itemView.findViewById(R.id.call_image_id);
    }
}
