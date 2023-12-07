package com.example.badmintonscore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    public Context context;
    public Activity activity;
    public ArrayList<String> name1, name2, name3, name4;

    CustomAdapter(Context context, ArrayList<String> name1, ArrayList<String> name2, ArrayList<String> name3,
                  ArrayList<String> name4) {
        this.context = context;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.name4 = name4;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.player1.setText(name1.get(position));
        holder.player2.setText(name2.get(position));
        holder.player3.setText(name3.get(position));
        holder.player4.setText(name4.get(position));

    }

    @Override
    public int getItemCount() {
        return name1.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView player1, player2, player3, player4;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            player1 = itemView.findViewById(R.id.player1);
            player2 = itemView.findViewById(R.id.player2);
            player3 = itemView.findViewById(R.id.player3);
            player4 = itemView.findViewById(R.id.player4);

            //Animate Recyclerview

        }

    }
}

