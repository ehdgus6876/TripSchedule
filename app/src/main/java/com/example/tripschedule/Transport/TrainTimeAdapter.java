package com.example.tripschedule.Transport;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripschedule.Calendar.CalendarActivity;
import com.example.tripschedule.R;
import com.example.tripschedule.SelectLocation.SelectLocationActivity;

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
    public void onBindViewHolder(@NonNull TrainTimeAdapter.TrainTimeViewHolder holder, final int position) {
        holder.tv_ttitle.setText(dataList.get(position).getStartStationID()+"->"+dataList.get(position).getEndStationID());
        holder.tv_tclass.setText(dataList.get(position).getTrainClass());
        holder.tv_tstime.setText(dataList.get(position).getDepartureTime());
        holder.tv_tdtime.setText(dataList.get(position).getArrivalTime());
        holder.tv_twtime.setText(dataList.get(position).getWasteTime());
        holder.tv_tcost.setText(dataList.get(position).getFare());

        holder.btn_tsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarActivity.arrivaltime=dataList.get(position).getArrivalTime();
                Intent intent= new Intent(v.getContext(), SelectLocationActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class TrainTimeViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ttitle,tv_tclass,tv_tstime,tv_tdtime,tv_twtime,tv_tcost;
        Button btn_tsel;

        public TrainTimeViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_ttitle=itemView.findViewById(R.id.tv_ttitle);
            tv_tclass=itemView.findViewById(R.id.tv_tclass);
            tv_tstime=itemView.findViewById(R.id.tv_tstime);
            tv_tdtime=itemView.findViewById(R.id.tv_tdtime);
            tv_twtime=itemView.findViewById(R.id.tv_twtime);
            tv_tcost=itemView.findViewById(R.id.tv_tcost);
            btn_tsel=itemView.findViewById(R.id.btn_tsel);




        }
    }

}



