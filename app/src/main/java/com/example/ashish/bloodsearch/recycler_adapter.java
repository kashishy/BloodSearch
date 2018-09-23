package com.example.ashish.bloodsearch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class recycler_adapter extends RecyclerView.Adapter<viewHolder> {
    private List<User> users;

    public recycler_adapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User sampleuser=users.get(position);
        holder.item_name.setText(sampleuser.username);
        holder.item_city.setText(sampleuser.usercity);
        holder.item_blood.setText(sampleuser.userblood);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
