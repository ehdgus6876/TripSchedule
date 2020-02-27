package com.example.tripschedule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bustime,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeedAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(dataList.get(position).getStartTerminal()+"->"+dataList.get(position).getDestTerminal());
        holder.tv_time.setText(dataList.get(position).getWasteTime());
        holder.tv_cost.setText(dataList.get(position).getFare());
        holder.tv_stime.setText(dataList.get(position).getSchedule());
        holder.btn_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(),SelectLocation.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null !=dataList?dataList.size():0);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_time,tv_cost,tv_stime;
        Button btn_sel;
        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.tv_title=itemView.findViewById(R.id.tv_title);
            this.tv_time=itemView.findViewById(R.id.tv_time);
            this.tv_cost=itemView.findViewById(R.id.tv_cost);
            this.tv_stime=itemView.findViewById(R.id.tv_stime);
            this.btn_sel=itemView.findViewById(R.id.btn_sel);

        }

    }
}
