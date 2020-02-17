package com.example.tripschedule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SpeedAdapter extends RecyclerView.Adapter<SpeedAdapter.ViewHolder> {
    private ArrayList<SpeedBus> dataList;


    SpeedAdapter(ArrayList<SpeedBus> items){
        this.dataList=items;


    }
    @NonNull
    @Override
    public SpeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_speedbus,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeedAdapter.ViewHolder holder, int position) {
        holder.tv_startTerminal.setText(dataList.get(position).getStartTerminal());
        holder.tv_destTerminal.setText(dataList.get(position).getDestTerminal());
        holder.tv_wasteTime.setText(dataList.get(position).getWasteTime());
        holder.tv_fare.setText(dataList.get(position).getFare());
        holder.tv_schedule.setText(dataList.get(position).getSchedule());



    }

    @Override
    public int getItemCount() {
        return (null !=dataList?dataList.size():0);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_startTerminal;
        TextView tv_destTerminal;
        TextView tv_wasteTime;
        TextView tv_fare;
        TextView tv_schedule;
        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.tv_startTerminal=itemView.findViewById(R.id.tv_startTerminal);
            this.tv_destTerminal=itemView.findViewById(R.id.tv_destTerminal);
            this.tv_wasteTime=itemView.findViewById(R.id.tv_wasteTime);
            this.tv_fare=itemView.findViewById(R.id.tv_fare);
            this.tv_schedule=itemView.findViewById(R.id.tv_schedule);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),SelectLocation.class);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }
}
