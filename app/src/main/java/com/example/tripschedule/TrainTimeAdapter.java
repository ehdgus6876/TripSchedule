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

import com.facebook.FacebookSdk;

import java.util.ArrayList;

public class TrainTimeAdapter extends RecyclerView.Adapter<TrainTimeAdapter.TrainTimeViewHolder>{

    private ArrayList<TrainItem> dataList;
    private int itemLayout;

    public TrainTimeAdapter(ArrayList<TrainItem> items,int itemLayout){
        this.dataList=items;
        this.itemLayout=itemLayout;

    }
    @NonNull
    @Override
    public TrainTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout,parent,false);
        return new TrainTimeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TrainTimeAdapter.TrainTimeViewHolder holder, int position) {
        holder.startStationID.setText(dataList.get(position).getStartStationID());
        holder.endStationID.setText(dataList.get(position).getEndStationID());
        holder.trainClass.setText(dataList.get(position).getTrainClass());
        holder.departureTime.setText(dataList.get(position).getDepartureTime());
        holder.arrivalTime.setText(dataList.get(position).getArrivalTime());
        holder.wasteTime.setText(dataList.get(position).getWasteTime());
        holder.Fare.setText(dataList.get(position).getFare());
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class TrainTimeViewHolder extends RecyclerView.ViewHolder {

        public TextView startStationID;
        public TextView endStationID;
        public TextView trainClass;
        public TextView departureTime;
        public TextView arrivalTime;
        public TextView wasteTime;
        public TextView Fare;

        public TrainTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),SelectLocation.class);
                    v.getContext().startActivity(intent);
                }
            });

            startStationID = itemView.findViewById(R.id.tv_startStation);
            endStationID = itemView.findViewById(R.id.tv_endStation);
            trainClass = itemView.findViewById(R.id.tv_trainClass);
            departureTime = itemView.findViewById(R.id.tv_departureTime);
            arrivalTime = itemView.findViewById(R.id.tv_arrivalTime);
            wasteTime = itemView.findViewById(R.id.tv_wasteTime);
            Fare = itemView.findViewById(R.id.tv_fare);

        }
    }

}



