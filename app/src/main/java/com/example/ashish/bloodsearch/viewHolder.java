package com.example.ashish.bloodsearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class viewHolder extends RecyclerView.ViewHolder {
    ImageView call_image;
    TextView item_name,item_city,item_blood;
    public viewHolder(View itemView) {
        super(itemView);
        call_image=itemView.findViewById(R.id.call_image_id);
        item_name=itemView.findViewById(R.id.recycler_item_name_id);
        item_city=itemView.findViewById(R.id.recycler_item_city_id);
        item_blood=itemView.findViewById(R.id.recycler_item_blood_id);
    }
}