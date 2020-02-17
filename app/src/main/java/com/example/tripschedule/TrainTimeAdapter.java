package com.example.tripschedule;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrainTimeAdapter extends RecyclerView.Adapter<TrainTimeAdapter.TrainTimeViewHolder>{

    private ArrayList<TrainItem> tList;
    private LayoutInflater tInflate;
    private Context tcontext;

    TrainTimeAdapter(Context context, ArrayList<TrainItem> items){
        this.tList = items;
        this.tInflate = LayoutInflater.from(context);
        this.tcontext = context;
    }
    @NonNull
    @Override
    public TrainTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = tInflate.inflate(R.layout.trainitem,parent,false);
        return new TrainTimeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TrainTimeAdapter.TrainTimeViewHolder holder, int position) {
        holder.tv_arrplandtime.setText(tList.get(position).arrplandtime);
        holder.tv_depplandtime.setText(tList.get(position).depplandtime);
        holder.tv_traingradename.setText(tList.get(position).traingradename);
    }
    @Override
    public int getItemCount() {
        return tList.size();
    }

    class TrainTimeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_arrplandtime;
        TextView tv_depplandtime;
        TextView tv_traingradename;
        TrainTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(tcontext,SelectLocation.class);
                    tcontext.startActivity(intent);
                }
            });

            tv_arrplandtime = itemView.findViewById(R.id.tv_arrplandtime);
            tv_depplandtime = itemView.findViewById(R.id.tv_depplandtime);
            tv_traingradename = itemView.findViewById(R.id.tv_traingradenam);

        }
    }

}



